/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package my.AudioFilePlayer;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine.Info;
import javax.sound.sampled.Line;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.UnsupportedAudioFileException;
import javazoom.jl.decoder.Bitstream;
import javazoom.jl.decoder.JavaLayerException;

/**
 *
 * @author Carol
 */
public class AudioFilePlayer {
        // The file being played.
    private String filename;
    
    private AudioInputStream inputStream;
    private AudioFormat audioFormat;
    private Info info;
    private Line line;
    
    public AudioFilePlayer(String filename) throws JavaLayerException
    {
        this.filename = filename;
        try {
            openAudio();
        } catch (IOException ex) {
            Logger.getLogger(AudioFilePlayer.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedAudioFileException ex) {
            Logger.getLogger(AudioFilePlayer.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        openInputStream();
    }
    protected final void openAudio() throws IOException, UnsupportedAudioFileException
    {
        File file = new File(filename);
        try{
            inputStream = AudioSystem.getAudioInputStream(file);
             audioFormat = inputStream.getFormat();
             info = new Info(SourceDataLine.class, audioFormat);
             try { 
                 line = (SourceDataLine) AudioSystem.getLine(info);
                 if (line != null) {
                     line.open();
                 }
             } catch (LineUnavailableException ex) {
                Logger.getLogger(AudioFilePlayer.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        catch(UnsupportedAudioFileException  
               | IOException ex) {
            throw new IllegalStateException(ex);
        }
        
                
    }
    protected void openInputStream()
        throws JavaLayerException
    {
        try {
            bitstream = new Bitstream(new BufferedInputStream(
                        new FileInputStream(filename)));
     
        }
        catch(java.io.IOException ex) {
            throw new JavaLayerException(ex.getMessage(), ex);
        }
                    
    }
}
