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
 
public class AudioFilePlayer {
    private boolean playing;
    
    
    public AudioFilePlayer(){
        playing = false;
    }
 
    public static void main(String[] args) {
        final AudioFilePlayer player = new AudioFilePlayer ();
        player.play("C:\\Users\\Carol\\Music\\Ana Egge\\River Under the Road\\01 River Under the Road.mp3");
       // player.play("something.ogg");
    }
    public boolean isPlaying(){
        return playing;
    }

    public void play(String filePath) {
       // filePath = "C:\\Users\\Carol\\Music\\Ana Egge\\River Under the Road\\01 River Under the Road.mp3";
        final File file = new File(filePath);
 
        try (final AudioInputStream in = getAudioInputStream(file)) {
             
            final AudioFormat outFormat = getOutFormat(in.getFormat());
            final Info info = new Info(SourceDataLine.class, outFormat);
 
            try ( final SourceDataLine line =
                     (SourceDataLine) AudioSystem.getLine(info)) {
 
                if (line != null) {
                    line.open(outFormat);
                    line.start();
                    stream(getAudioInputStream(outFormat, in), line);
                    line.drain();
                    line.stop();
                }
            }
 
        } catch (UnsupportedAudioFileException 
               | LineUnavailableException 
               | IOException e) {
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
        for (int n = 0; n != -1; n = in.read(buffer, 0, buffer.length)) {
            line.write(buffer, 0, n);
        }
    }
    public void stop(){
        close();
    }
    public void close()
    {
        synchronized(this) {
            if (line != null) {
                AudioDevice out = audio;
                audio = null;
                // this may fail, so ensure object state is set up before
                // calling this method.
                out.close();
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
}
