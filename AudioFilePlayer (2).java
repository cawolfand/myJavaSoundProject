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
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
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
import javazoom.jl.decoder.Bitstream;
import javazoom.jl.decoder.BitstreamException;
import javazoom.jl.decoder.Header;
import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.AudioDevice;

public class AudioFilePlayer {

    private SourceDataLine line;
    private AudioInputStream din;
    private Bitstream bitstream;
    private String filename;
    private boolean playing;
    // The number of frames.
    private int frameCount;
    // The current frame number.
    private int frameNumber;
    // The position to resume, if any.
    private int resumePosition;

    public AudioFilePlayer() {
        playing = false;
    }

    public AudioFilePlayer(String f) throws JavaLayerException {
        this.filename = f;
        playing = false;
        
        openAudio();
        frameCount = getFrameCount(f);
         // Open a fresh bitstream following the frame count.
        openBitstream(filename);
        
        frameNumber = 0;
        resumePosition = -1;  

    }

    public static void main(String[] args) {
        final AudioFilePlayer player = new AudioFilePlayer();
        //  player.play("C:\\Users\\Carol\\Music\\Ana Egge\\River Under the Road\\01 River Under the Road.mp3");
        // player.play("something.ogg");
    }

    public void play() throws JavaLayerException {
        playFrames(0, frameCount);
    }

    /**
     * Plays a number of MPEG audio frames.
     *
     * @param frames The number of frames to play.
     * @return true if the last frame was played, or false if there are more
     * frames.
     */
    public boolean play(int frames) throws JavaLayerException {
        return playFrames(frameNumber, frameNumber + frames);

    }

    /**
     * Plays a range of MPEG audio frames
     *
     * @param start The first frame to play
     * @param end The last frame to play
     * @return true if the last frame was played, or false if there are more
     * frames.
     */
    public boolean play(int start, int end) throws JavaLayerException {
        return playFrames(start, start + end);
    }

    /**
     * Play from the given position to the end.
     *
     * @param start The first frame to play.
     * @return true if the last frame was played, or false if there are more
     * frames.
     */
    public boolean playFrom(int start) throws JavaLayerException {
        return playFrames(start, frameCount);
    }

    /**
     * Get the length of the file (in frames).
     *
     * @return The file length, in frames.
     */
    public int getLength() {
        return frameCount;
    }
    
    /**
     * Count the number of frames in the file.
     * This can be used for positioning.
     * @param filename The file to be measured.
     * @return The number of frames.
     */
    protected int getFrameCount(String filename) throws JavaLayerException
    {
        long c =0;
        openBitstream(filename);
        int count = 0;
        while(skipFrame()) {
            count++;
        }
        bitstream.close();
        return count;
    }

    /**
     * Get the current playing position (in frames).
     *
     * @return The current frame number.
     */
    public int getPosition() {
        return frameNumber;
    }

    /**
     * Set the playing position (in frames). Playing does not start until
     * resume() is called.
     *
     * @param position The playing position.
     */
    public void setPosition(int position) throws JavaLayerException {
        pause();
        resumePosition = position;
    }

    public boolean isPlaying() {
        return playing;
    }

    public void pause() throws JavaLayerException {
        synchronized (this) {
            playing = false;
            resumePosition = frameNumber;
        }
    }

    /**
     * Resume the playing.
     */
    public void resume() throws JavaLayerException {
        if (!playing) {
            int start;
            if (resumePosition >= 0) {
                start = resumePosition;
            } else {
                start = frameNumber;
            }
            resumePosition = -1;
            playFrames(start, frameCount);
        }
    }

    /**
     * Return the current frame number.
     *
     * @return The number of the last frame played, or -1 if nothing played yet.
     */
    public int getFrameNumber() {
        return frameNumber;
    }

    /**
     * Play the number of frames left.
     *
     * @return true If finished for any reason, false if paused.
     */
    private boolean playFrames(int start, int end) throws JavaLayerException {
        // Clear any resumption position.
        resumePosition = -1;

        if (end > frameCount) {
            end = frameCount;
        }

        // Make sure the player is in the correct position in the input.
        synchronized (this) {
            moveTo(start);
            playing = true;
        }

        // Play until finished, paused, or a problem.
        boolean ok = true;
        while (frameNumber < end && playing && ok) {
            ok = decodeFrame();
            if (ok) {
                frameNumber++;
            }
        }

        // Stopped for some reason.
        synchronized (this) {
            playing = false;
            // last frame, ensure all data flushed to the audio device.
            //         AudioDevice out = audio;
            //          if (out != null) {
            //              out.flush();
            //          }
        }
        return ok;
    }

    /**
     * Set the playing position.
     *
     * @param position (in frames)
     */
    private void moveTo(int position) throws JavaLayerException {
        if (position < frameNumber) {
            synchronized (this) {
                // Already played too far.
                if (din != null) {
                    try {
                        din.close();
                    } catch (Exception ex) {
                    }
                }
                if (line != null) {
                    line.close();
                }
                openAudio();
                //openBitstream(filename);
                frameNumber = 0;
            }
        }

        while (frameNumber < position) {
            skipFrame();
            frameNumber++;
        }
    }

    /**
     * Cloases this player. Any audio currently playing is stopped immediately.
     */
    public void close() {
        synchronized (this) {
            if (line != null) {
                line.flush();
                line.stop();
                try {
                    din.close();
                } catch (IOException ex) {
                }
                din = null;

            }
        }
    }

    /**
     * skips over a single frame
     *
     * @return false if there are no more frames to decode, true otherwise.
     */
    protected boolean skipFrame() throws JavaLayerException {
        Header h = readFrame();
        if (h == null) {
            return false;
        }
        frameNumber++;
       bitstream.closeFrame();
        return true;
    }

    /**
     * Read a frame.
     *
     * @return The frame read.
     */
    protected Header readFrame() throws JavaLayerException {
        if (line != null) {
            return bitstream.readFrame();
        } else {
            return null;
        }
    }

    public boolean decodeFrame() {
        // filePath = "C:\\Users\\Carol\\Music\\Ana Egge\\River Under the Road\\01 River Under the Road.mp3";

        try  {
            synchronized (this) {
                playing = true;

                
                    if (line != null) {
                       
                        line.start();
                        int nBytesRead;
                        // stream(getAudioInputStream(outFormat, in), line);
                        byte[] buffer = new byte[4096];
                        while ( (nBytesRead = din.read(buffer, 0, buffer.length))!=-1) {
                            while (!playing) {
                                if (line.isRunning()) {
                                    line.stop();
                                }
                                try {
                                    this.wait();
                                } catch (InterruptedException e) {
                                }
                            }
                            if (!line.isRunning()){
                                line.start();
                            }
                            line.write(buffer, 0, nBytesRead);
                        }
                        line.drain();
                        line.stop();
                        line.close();
                        din.close();
                        
                    }
                }
              
            
         
        }catch (  IOException e) {
          
        }
        return true;
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
            while (!playing) {
                if (line.isRunning()) {
                    line.stop();
                }
                try {
                    this.wait();
                } catch (InterruptedException e) {
                }
            }
            line.write(buffer, 0, n);
        }
    }

    /**
     * Open an audio device.
     */
    protected void openAudio() throws JavaLayerException {
        final File file = new File(filename);

        try (final AudioInputStream in = getAudioInputStream(file)) {
            final AudioFormat outFormat = getOutFormat(in.getFormat());
            final Info info = new Info(SourceDataLine.class, outFormat);
            din = getAudioInputStream(outFormat, in);
            
                line = (SourceDataLine) AudioSystem.getLine(info);
                if (line != null) {
                    line.open(outFormat);

                }
        }
            catch (UnsupportedAudioFileException | LineUnavailableException | IOException e) {
            }
            
    }
     /**
     * Open a BitStream for the given file.
     * @param filename The file to be opened.
     * @throws IOException If the file cannot be opened.
     */
    protected void openBitstream(String filename)
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

  
    public void stop() {
        close();
    }
}

  //  public void close() {
//        synchronized(this) {
//            if (line != null) {
//                AudioDevice out = audio;
//                audio = null;
//                // this may fail, so ensure object state is set up before
//                // calling this method.
//                out.close();
//                try {
//                    bitstream.close();
//                }
//                catch (BitstreamException ex) {
//                }
//                bitstream = null;
//                decoder = null;
//            }
//        }
//    
//    }
//}
