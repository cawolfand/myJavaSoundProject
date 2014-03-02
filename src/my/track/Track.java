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
    private String tracknum;
    private String title;
    private long length;
    private String filename;
    private String genre;
    
    public static final String[] FIELDS = {
        "Artist",
        "Album",
        "Title",
        "TrackNum",
        "Length",
        "Filename",
        "Genre"
    };
    
    
    public Track(String artist, String album, String title, String trackNum, long length, String filename, String genre){
        SetDetails(artist, album, title, trackNum, length, filename, genre);
        
    }
    
    /**
     * Constructor for objects of class Track.
     * It is assumed that the file name cannot be
     * decoded to extract artist and title details.
     * @param filename The track file. 
     */
    public Track(String filename)
    {
        SetDetails("unknown", "unknown", "unknown", "", 0, filename, genre);
    }
    
    private void SetDetails(String artist, String album, String title, String trackNum, long length, String filename, String genre){
        this.artist = artist;
        this.album = album;
        this.title = title;
        this.tracknum = trackNum;
        this.length = length;
        this.filename = filename;
        this.genre = genre;
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
    
    public String getTrackNum(){
        return tracknum;
        
    }
    
    public long getLength(){
        return length;
    }
    
    public String getFilename(){
        return filename;
    }
    
    public String getGenre(){
        return genre;
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
            return tracknum;
        }
        else if (field.equals("Length")) {
            return Integer.toString((int)length/3600);
        }
        else if (field.equals("Filename")){
            return filename;
        }
        else if (field.equals("Genre")){
            return genre;
        }
        else {
            throw new IllegalArgumentException("Unkown field name: " + field);
        }
    }
}
