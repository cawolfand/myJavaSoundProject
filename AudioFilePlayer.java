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
import javax.sound.sampled.DataLine;
import javazoom.jl.decoder.BitstreamException;
import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.AudioDevice;

public class AudioFilePlayer {

    private boolean playing;
    private String filename;

    public AudioFilePlayer() {
        playing = false;
    }
 public AudioFilePlayer(String f) throws JavaLayerException {
        this.filename = f;
        playing = false;
        
//        openAudio();
//        frameCount = getFrameCount(f);
//         // Open a fresh bitstream following the frame count.
//        openBitstream(filename);
//        
//        frameNumber = 0;
//        resumePosition = -1;  

    }
 
    public static void main(String[] args) {
        final AudioFilePlayer player = new AudioFilePlayer();
        player.play("C:\\Users\\Carol\\Music\\Ana Egge\\River Under the Road\\01 River Under the Road.mp3");
        // player.play("something.ogg");
    }

    public boolean isPlaying() {
        return playing;
    }

    public void pause() throws JavaLayerException{
       synchronized(this) {
            playing = false;
          //  resumePosition = frameNumber;
        }
    }

    public void play(String filePath) {
        synchronized (this) {
            playing = true;
            this.notifyAll();
            playStream(filePath);
        }
    }

    public void playStream(String filePath) {
        // filePath = "C:\\Users\\Carol\\Music\\Ana Egge\\River Under the Road\\01 River Under the Road.mp3";
        final File file = new File(filePath);

        try (final AudioInputStream in = getAudioInputStream(file)) {
            synchronized (this) {

                final AudioFormat outFormat = getOutFormat(in.getFormat());
                final Info info = new Info(SourceDataLine.class, outFormat);

                try (final SourceDataLine line =
                        (SourceDataLine) AudioSystem.getLine(info)) {

                    if (line != null) {
                        line.open(outFormat);
                        byte[] data = new byte[4096];
                        line.start();
                        //         stream(getAudioInputStream(outFormat, in), line);
                        AudioInputStream din = getAudioInputStream(outFormat,in);
                        int nBytesRead;
                        while ((nBytesRead = din.read(data, 0, data.length)) != -1) {
                            while (!playing) {
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
                    }
                }
            }
        } catch (UnsupportedAudioFileException | LineUnavailableException | IOException e) {
            throw new IllegalStateException(e);
        }
        finally {
			if(din != null) {
				try { din.close(); } catch(IOException e) { }
			}
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

    public void stop() {
        close();
    }

    public void close() {
         synchronized(this) {
            if (line != null) {
                
                try {
                    bitstream.close();
                }
                catch (BitstreamException ex) {
                }
                bitstream = null;
                decoder = null;
            }
        }
    }
          /**
     * Resume the playing.
     */
        
    public void resume() throws JavaLayerException {
        if (!playing) {
            int start;
//            if (resumePosition >= 0) {
//                start = resumePosition;
//            } else {
//                start = frameNumber;
//            }
//            resumePosition = -1;
//            playFrames(start, frameCount);
        }
    }
    

    }
