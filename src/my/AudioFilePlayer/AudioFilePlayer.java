/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package my.AudioFilePlayer;

/**
 *
 * @author Carol
 */
import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine.Info;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.UnsupportedAudioFileException;

import static javax.sound.sampled.AudioSystem.getAudioInputStream;
import static javax.sound.sampled.AudioFormat.Encoding.PCM_SIGNED;
import javax.sound.sampled.Control;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.FloatControl;
import javazoom.jl.decoder.JavaLayerException;

public class AudioFilePlayer {

    private boolean playing;
    private boolean paused;
    private String filename;
    private SourceDataLine line;
    private float gain;
    // The position to resume, if any.
    private int resumePosition;

    public AudioFilePlayer() {
        playing = false;
        paused = false;
    }

    public static void main(String[] args) {
        final AudioFilePlayer player = new AudioFilePlayer();
       // player.play("C:\\Users\\Carol\\Music\\Ana Egge\\River Under the Road\\01 River Under the Road.mp3");
        // player.play("something.ogg");
    }

    public AudioFilePlayer(String f) throws JavaLayerException {
        this.filename = f;
        playing = false;
        paused = false;
//        openAudio();
//        frameCount = getFrameCount(f);
//         // Open a fresh bitstream following the frame count.
//        openBitstream(filename);
//        
//        frameNumber = 0;
//        resumePosition = -1;  

    }

    public boolean isPlaying() {
        return playing;
    }
    
     public void resume()  {
      synchronized (this){  
        playing = true;
        paused = false;
        this.notifyAll();
      }
     }
    public void pause() {
       
        playing = false;
        paused = true;
       
    }

    public void play(int start) throws JavaLayerException {
        synchronized (this) {
            playing = true;
            paused = false;
            this.notifyAll();
            playStream();
        }
    }

    public void playStream() {
        // filePath = "C:\\Users\\Carol\\Music\\Ana Egge\\River Under the Road\\01 River Under the Road.mp3";
        final File file = new File(filename);

        try (final AudioInputStream in = getAudioInputStream(file)) {
            synchronized (this) {

                final AudioFormat outFormat = getOutFormat(in.getFormat());
                final Info info = new Info(SourceDataLine.class, outFormat);
                AudioInputStream din = getAudioInputStream(outFormat,in);
                line =  (SourceDataLine) AudioSystem.getLine(info); 
                    if (line != null) {
                        line.open(outFormat);
                        byte[] data = new byte[4096];
                        line.start();
                        //         stream(getAudioInputStream(outFormat, in), line);
                      
                        int nBytesRead;
                        while ((nBytesRead = din.read(data, 0, data.length)) != -1) {
                            while (paused) {
                                if (line.isRunning()) {
                                    line.stop();
                                }
                                try {
                                    this.wait();
                                } catch (InterruptedException e) {
                                }
                            }

                            if (!line.isRunning()) {
                                line.start();
                            }
                            line.write(data, 0, nBytesRead);
                        }
                        line.drain();
                        line.stop();
                        line.close();
                        din.close();
                    }
                }
        } catch (UnsupportedAudioFileException | LineUnavailableException | IOException e) {
            throw new IllegalStateException(e);
        }
        
    }

    private AudioFormat getOutFormat(AudioFormat inFormat) {
        final int ch = inFormat.getChannels();
        final float rate = inFormat.getSampleRate();
        return new AudioFormat(PCM_SIGNED, rate, 16, ch, ch * 2, rate, false);
    }

    private void stream(AudioInputStream in, SourceDataLine line)
            throws IOException {
        final byte[] buffer = new byte[4096];
        synchronized (this) {
            for (int n = 0; n != -1; n = in.read(buffer, 0, buffer.length)) {

                line.write(buffer, 0, n);
            }
        }
    }

    private SourceDataLine getLine(AudioFormat audioFormat) throws LineUnavailableException {
        SourceDataLine res = null;
        DataLine.Info info = new DataLine.Info(SourceDataLine.class, audioFormat);
        res = (SourceDataLine) AudioSystem.getLine(info);
        res.open(audioFormat);
        return res;
    }
    
    public float getGain(){
        //Control control = line.getControl(Control.Type );
         FloatControl volCtrl = (FloatControl)line.getControl(
                            FloatControl.Type.MASTER_GAIN);
         return volCtrl.getValue();
    }

    public void stop() {
        close();
    }

    public void close() {
         
            if (line != null) {
                
                // this may fail, so ensure object state is set up before
                // calling this method.
                 try {
//                    line.drain();
                    line.stop();
                    line.close();
                 }
                catch (Exception ex) {
                }
                
            }
           }
}
