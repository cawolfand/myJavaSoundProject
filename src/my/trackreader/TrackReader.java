package my.trackreader;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Carol
 */
import my.track.Track;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;
import myjavasoundproject.myJavaSoundProjectUI;

/**
 * A helper class for our music application. This class can read files from the file system
 * from a given folder with a specified suffix. It will interpret the file name as artist/
 * track title information.
 * 
 * It is expected that file names of music tracks follow a standard format of artist name
 * and track name, separated by a dash. For example: TheBeatles-HereComesTheSun.mp3
 * 
 * @author David J. Barnes and Michael Kölling
 * @version 2011.03.27
 */
public class TrackReader
{
    /**
     * Create the track reader, ready to read tracks from the music library folder.
     */
    public TrackReader()
    {
        // Nothing to do here.
    }
    
    /**
     * Read music files from the given library folder
     * with the given suffix.
     * @param folder The folder to look for files.
     * @param suffix The suffix of the audio type.
     */
    public ArrayList<Track> readTracks(String folder, final String suffix)
    {
        File audioFolder = new File(folder);
        ArrayList<Track> tracks = new ArrayList<Track>();
        File[] audioFiles = audioFolder.listFiles(new FilenameFilter() {
            /**
             * Accept files with matching suffix.
             * @param dir The directory containing the file.
             * @param name The name of the file.
             * @return true if the name ends with the suffix.
             */
            public boolean accept(File dir, String name)
            {
                return name.toLowerCase().endsWith(suffix);
            }
        });
        
        // Put all the matching files into the organizer.
        for(File file : audioFiles) {
            Track trackDetails = decodeDetails(file);
            tracks.add(trackDetails);
        }
        return tracks;
    }

    /**
     * Try to decode details of the artist and the title
     * from the file name.
     * It is assumed that the details are in the form:
     *     artist-title.mp3
     * @param file The track file.
     * @return A Track containing the details.
     */
    private Track decodeDetails(File file)
    {
        // The information needed.
        String artist = "unknown";
        String title = "unknown";
        String album = "unknown";
        String t;
        String tracknum = "";
        long length =0;
        String genre = "";
        
        // Look for artist and title in the name of the file.
        String details = file.getName();
        
            AudioFileFormat baseFileFormat = null;
            AudioFormat baseFormat = null;
            try {
                baseFileFormat = AudioSystem.getAudioFileFormat(file);
            } catch (UnsupportedAudioFileException ex) {
                Logger.getLogger(myJavaSoundProjectUI.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(myJavaSoundProjectUI.class.getName()).log(Level.SEVERE, null, ex);
            }
            Map props =  baseFileFormat.properties();
            if (props.containsKey("title")) title = (String) props.get("title");
            if (props.containsKey("author")) artist = (String) props.get("author");
            if (props.containsKey("album")) album = (String) props.get("album");
        
            if (props.containsKey("mp3.id3tag.track")) tracknum = (String) props.get("mp3.id3tag.track");
            if (props.containsKey("duration"))length = (long) props.get("duration");
            if (props.containsKey("mp3.id3tag.genre")) genre = (String) props.get("mp3.id3tag.genre");
                   
        return new Track(artist, album, title, tracknum, length, file.getAbsolutePath(),genre);
    }
}
