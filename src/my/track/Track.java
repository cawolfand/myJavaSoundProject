/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package my.track;


import java.io.File;
/**
 *
 * @author Carol
 */
public class Track {
    private String album;
    private String artist;
    private int tracknum;
    private String title;
    private long length;
    private String filename;
    
    public static final String[] FIELDS = {
        "Artist",
        "Album",
        "Title",
        "TrackNum",
        "Length",
        "Filename",
    };
    
    
    public Track(String artist, String album, String title, int trackNum, long length, String filename){
        SetDetails(artist, album, title, trackNum, length, filename);
        
    }
    
    /**
     * Constructor for objects of class Track.
     * It is assumed that the file name cannot be
     * decoded to extract artist and title details.
     * @param filename The track file. 
     */
    public Track(String filename)
    {
        SetDetails("unknown", "unknown", "unknown", 0, 0, filename);
    }
    
    private void SetDetails(String artist, String album, String title, int trackNum, long length, String filename){
        this.artist = artist;
        this.album = album;
        this.title = title;
        this.tracknum = trackNum;
        this.length = length;
        this.filename = filename;
    }
    
    public String getArtist()
    {
        return artist;
    }
    
    public String getTitle(){
        return title;
        
    }
    public String getAlbum(){
        return album;
        
    }
    
    public int getTrackNum(){
        return tracknum;
        
    }
    
    public long getLength(){
        return length;
    }
    
    public String getFilename(){
        return filename;
    }
    
    public String getField(String field)
    {
        if (field.equals("Artist")) {
            return artist;
        }
        else if (field.equals("Title")) {
            return title;
        }
        else if (field.equals("Album")) {
            return album;
        }
        else if (field.equals("TrackNum")) {
            return Integer.toString(tracknum);
        }
        else if (field.equals("Length")) {
            return Integer.toString((int)length/3600);
        }
        else if (field.equals("Filename")){
            return filename;
        }
        else {
            throw new IllegalArgumentException("Unkown field name: " + field);
        }
    }
}
