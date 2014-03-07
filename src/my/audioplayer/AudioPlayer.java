/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package my.audioplayer;

/**
 *
 * @author Carol
 */
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
import javazoom.jl.decoder.JavaLayerException;
import my.AudioFilePlayer.AudioFilePlayer;
//import my.musicfileplayer.MusicFilePlayer;

/**
 * Provide basic playing of MP3 files via the javazoom library.
 * See http://www.javazoom.net/
 * 
 * @author David J. Barnes and Michael Kölling
 * @version 2011.07.31
 */
public class AudioPlayer
{
    // The current player. It might be null.
    private AudioFilePlayer player;
    // The current file being played.
    private String filename;
    
    /**
     * Constructor for objects of class MusicPlayer
     */
    public AudioPlayer()
    {
        player = null;
        filename = "";
    }
    
    /**
     * Start playing the given audio file.
     * The method returns once the playing has been started.
     * @param filename The file to be played.
     */
    public void startPlaying(final String filepath)
    {
        try {
            setupPlayer(filepath);
            playFrom(0);
        }
        catch (JavaLayerException ex) {
            reportProblem();
        }
    }
    public boolean isPlaying(){
        return player.isPlaying();
    }
    
    /**
     * Stop playing the current file.
     */
    public void stop()
    {
        killPlayer();
    }
    
    /**
     * Pause the current file.
     */
    public void pause() 
    {
        if(player != null) {
            
                player.pause();
          
        }
    }
    
    /**
     * Resume playing following a pause.
     */
    public void resume()
    {
        if(player != null) {
            Thread playerThread = new Thread() {
                public void run()
                {
                   
                        player.resume();
                    
                }
            };
            playerThread.setPriority(Thread.MIN_PRIORITY);
            playerThread.start();
        }
    //   if (player != null){
    //        player.resume();
    //    }
    }
     /**
     * Resume playing following a pause.
     */
    public void resume2()
    {
        if(player != null) {
                   
                        player.resume();
            
        }
   
    }
    /**
     * Seek to the given position in the current file.
     * The track will be paused as a result of this operation.
     * 
     * @param position What position in the file to move to.
     */
    public void seekTo(int position)
    {
        if (player != null && position >= 0 && position < player.getLength()) {
            // Set the player's position.
        try {   
         player.setPosition(position);
        }
         catch(JavaLayerException e) {
                        reportProblem();
                        killPlayer();
                    }
        }
    }
    
    /**
     * Return the length of the current music file, if any.
     * The length is in 'frames' rather than seconds, for instance.
     * 
     * @return The file length in frames.
     */
    public int getLength()
    {
        if(player != null) {
            return player.getLength();
        }
        else {
            return 0;
        }

    }
    
    public int getFrame()
    {
        if(player != null){
            return player.getFrameNumber();
        }
        else {
            return 0;
        }

    }
    
    /**
     * Set up the player ready to play the given file.
     * @param filename The name of the file to play.
     */
    private void setupPlayer(String filename)
    {
        try {
            if(player != null) {
                killPlayer();
            }
            this.filename = filename;
            player = new AudioFilePlayer(filename);
        }
        catch(JavaLayerException e) {
            System.out.println("Problem setting up player");
            e.printStackTrace();
            reportProblem();
            killPlayer();
        }
    }
    
    /**
     * Play from the given position.
     * @param start The starting position for playing.
     *              Must be within the current file's length.
     */
    private void playFrom(final int start) throws JavaLayerException
    {
       
        Thread playerThread = new Thread() {
            public void run()
            {
                try {
                    player.play(start);
                }
                catch(JavaLayerException e) {
                    reportProblem();
                    killPlayer();
                }
            }
        };
        playerThread.setPriority(Thread.MIN_PRIORITY);
        playerThread.start();
    }

    /**
     * Terminate the player, if there is one.
     */
    private void killPlayer()
    {
        synchronized(this) {
            if(player != null) {
                player.stop();
                player = null;
                filename = "";
            }
        }
    }
    
    /**
     * Report a problem playing the current file.
     */
    private void reportProblem()
    {
        System.out.println("There was a problem playing: " + filename);
    }
    public float getPan(){
        return player.getPan();
    }
    public void setPan(float pan){
        
        player.setPan(pan);
    }
    
    public float getGain(){
        return player.getGain();
    }
    
    public void setGain(float val){
        player.setGain(val);
}
    public int getGainMin(){
        return player.getGainMin();
    }
    
    public int getGainMax(){
        return player.getGainMax();
        
    }
}

