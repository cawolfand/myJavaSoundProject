package my.musicorganizer;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import my.track.Track;
import my.trackreader.TrackReader;

/**
 * A class to hold details of audio tracks. Individual tracks may be played.
 *
 * @author David J. Barnes and Michael Kölling
 * @version 2011.07.31
 */
public class MusicOrganizer {
    // An ArrayList for storing music tracks.

    private ArrayList<Track> tracks;
    // A reader that can read music files and load them as tracks.
    private TrackReader reader;

//     0. Blues
//  1. Classic Rock
//  2. Country
//  3. Dance
//  4. Disco
//  5. Funk
//  6. Grunge
//  7. Hip-Hop
//  8. Jazz
//  9. Metal
// 10. New Age
// 11. Oldies
// 12. Other
// 13. Pop
// 14. R&B
// 15. Rap
// 16. Reggae
// 17. Rock
// 18. Techno
// 19. Industrial
// 20. Alternative
// 21. Ska
// 22. Death Metal
// 23. Pranks
// 24. Soundtrack
// 25. Euro-Techno
// 26. Ambient
// 27. Trip-Hop
// 28. Vocal
// 29. Jazz+Funk
// 30. Fusion
// 31. Trance
// 32. Classical
// 33. Instrumental
// 34. Acid
// 35. House
// 36. Game
// 37. Sound Clip
// 38. Gospel
// 39. Noise
// 40. AlternRock
// 41. Bass
// 42. Soul
// 43. Punk
// 44. Space
// 45. Meditative
// 46. Instrumental Pop
// 47. Instrumental Rock
// 48. Ethnic
// 49. Gothic
// 50. Darkwave
// 51. Techno-Industrial
// 52. Electronic
// 53. Pop-Folk
// 54. Eurodance
// 55. Dream
// 56. Southern Rock
// 57. Comedy
// 58. Cult
// 59. Gangsta
// 60. Top 40
// 61. Christian Rap
// 62. Pop/Funk
// 63. Jungle
// 64. Native American
// 65. Cabaret
// 66. New Wave
// 67. Psychadelic
// 68. Rave
// 69. Showtunes
// 70. Trailer
// 71. Lo-Fi
// 72. Tribal
// 73. Acid Punk
// 74. Acid Jazz
// 75. Polka
// 76. Retro
// 77. Musical
// 78. Rock & Roll
// 79. Hard Rock
    /**
     * Create a MusicOrganizer.
     *
     * @param folderName The folder of audio files.
     */
    public MusicOrganizer() /**
     * Create a MusicOrganizer.
     *
     * @param folderName The folder of audio files.
     */
    {
        tracks = new ArrayList<Track>();
        reader = new TrackReader();
    }

    public MusicOrganizer(String folderName) {
        tracks = new ArrayList<Track>();
        reader = new TrackReader();
        readLibrary(folderName);
    }

    /**
     * Add a track file to the collection.
     *
     * @param filename The file name of the track to be added.
     */
    public void addFile(String filename) {
        tracks.add(new Track(filename));
    }

    /**
     * Add a track to the collection.
     *
     * @param track The track to be added.
     */
    public void addTrack(Track track) {
        tracks.add(track);
    }

    /**
     * Get a track from the collection.
     *
     * @param index The index of the track.
     * @return The selected track, or null if it does not exist.
     */
    public Track getTrack(int index) {
        if (indexValid(index)) {
            return tracks.get(index);
        } else {
            return null;
        }
    }

    public int getTrack(String filename) {
        int result = 0;
        int index = 0;
        boolean searching = true;
        while (index < getNumberOfTracks() && searching) {
            String f = tracks.get(index).getFilename();
            if (f.contains(filename)) {
                searching = false;
            } else {
                index++;
            }
        }
        if (searching) {
            return -1;
        } else {
            return index;

        }

    }

    /**
     * Return the number of tracks in the collection.
     *
     * @return The number of tracks in the collection.
     */
    public int getNumberOfTracks() {
        return tracks.size();
    }

    /**
     * Return a copy of all the tracks in the collection.
     *
     * @return All the tracks in the collection.
     */
    public List<Track> getAllTracks() {
        return new ArrayList<Track>(tracks);
    }

    /**
     * Return a list of the tracks, sorted by artist.
     *
     * @return The tracks, sorted by artist.
     */
    public List<Track> sortByArtist() {
        return sortByField("Artist");
    }

    /**
     * Return a list of the tracks, sorted by title.
     *
     * @return The tracks, sorted by title.
     */
    public List<Track> sortByTitle() {
        return sortByField("Field");
    }

    /**
     * Return a sorted copy of the track list.
     *
     * @param comparator The comparator for the sort.
     * @return A sorted copy of the list.
     */
    private List<Track> sortBy(Comparator<Track> comparator) {
        List<Track> copy = getAllTracks();
        Collections.sort(copy, comparator);
        return copy;
    }

    /**
     * Return a list of the tracks, sorted by the given field name.
     *
     * @param field The field to sort by; e.g., Artist, Title, etc.
     * @see Track.FIELDS
     * @return The tracks, sorted by the field.
     */
    public List<Track> sortByField(final String field) {
        return sortBy(new Comparator<Track>() {
            public int compare(Track t1, Track t2) {
                return t1.getField(field).compareTo(t2.getField(field));
            }
        });
    }

    /**
     * Remove a track from the collection.
     *
     * @param index The index of the track to be removed.
     */
    public void removeTrack(int index) {
        String title = null;
        if (indexValid(index)) {
            title = tracks.get(index).getTitle();
            tracks.remove(index);
        }
        if (title != null) {
            String DATABASE_URL = "jdbc:sqlite:C:\\Users\\Carol\\Documents\\NetBeansProjects\\myJavaSoundProject2\\mymusicdb.sqlite";

            int i = 0, result;
            String command;
            Connection connection = null;
            Statement statement = null;
            ResultSet resultSet = null;

            try {
                try {
                    Class.forName("org.sqlite.JDBC");
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();

                }
                connection = DriverManager.getConnection(DATABASE_URL);

                statement = connection.createStatement();

                command = "DELETE FROM library WHERE title = '"
                        + title + "'";

                result = statement.executeUpdate(command);

            } catch (SQLException sqlException) {
                sqlException.printStackTrace();
            } finally {
                try {
                    resultSet.close();
                    statement.close();
                    connection.close();
                } catch (Exception exception) {
                    exception.printStackTrace();

                }
            }
        }
    }

    /**
     * Determine whether the given index is valid for the collection. Print an
     * error message if it is not.
     *
     * @param index The index to be checked.
     * @return true if the index is valid, false otherwise.
     */
    private boolean indexValid(int index) {
        // The return value.
        // Set according to whether the index is valid or not.
        boolean valid;

        if (index < 0) {
            System.out.println("Index cannot be negative: " + index);
            valid = false;
        } else if (index >= tracks.size()) {
            System.out.println("Index is too large: " + index);
            valid = false;
        } else {
            valid = true;
        }
        return valid;
    }

    public ArrayList<Track> readLibrary(String folderName) {
        String DATABASE_URL = "jdbc:sqlite:C:\\Users\\Carol\\Documents\\NetBeansProjects\\myJavaSoundProject2\\mymusicdb.sqlite";
        int i = 0, result;
        String command;

        ArrayList<Track> tempTracks = reader.readTracks(folderName, ".mp3");

        // Put all thetracks into the organizer.
        for (Track track : tempTracks) {
            addTrack(track);
        }
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        String[] objData = new String[10];

        try {
            try {
                Class.forName("org.sqlite.JDBC");
            } catch (ClassNotFoundException e) {
                e.printStackTrace();

            }
            connection = DriverManager.getConnection(DATABASE_URL);

            statement = connection.createStatement();

            for (Track track : tempTracks) {

                //command = "SELECT COUNT(*) FROM library WHERE  title = \"" + track.getTitle() + "\"";
                    command = "SELECT COUNT(*) FROM library WHERE album = \"" + track.getAlbum() +  "\" AND artist = \""
                           + track.getArtist() + "\" AND title = \""
                           + track.getTitle() + "\"";
                resultSet = statement.executeQuery(command);
                result = resultSet.getInt("COUNT(*)");
                
               
                //    result = statement.executeUpdate(command);
                if (result==0) {
                    command = "INSERT INTO library (  album, artist, title, tracknum, length, filename, genre) "
                            + "VALUES (\"" + track.getAlbum() + "\", "
                            + "'" + track.getArtist() + "', "
                            + "\"" + track.getTitle() + "\", "
                            + "'" + track.getTrackNum() + "', "
                            + "'" + track.getLength() + "', "
                            + "\"" + track.getFilename() + "\", "
                            + "'" + track.getGenre() + "'"
                            + ") ";

                    result = statement.executeUpdate(command);
                } else {
                    System.out.printf("repeat entry");
                }

            }

        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        } finally {
            try {
                resultSet.close();
                statement.close();
                connection.close();

            } catch (Exception exception) {
                exception.printStackTrace();

            }
        }
        return tempTracks;
    }

    public void initLibraryDB() throws ClassNotFoundException {
        String DATABASE_URL = "jdbc:sqlite:C:\\Users\\Carol\\Documents\\NetBeansProjects\\myJavaSoundProject2\\mymusicdb.sqlite";
        int numberOfTracks = 0;
        int n;
        long l;
        Connection connection = null;

        Statement statement = null;
        ResultSet resultSet = null;
        Track[] newtracks;
        String[] objData = new String[10];
        //Class.forName("org.sqlite.JDBC");
        try {
            try {
                Class.forName("org.sqlite.JDBC");
            } catch (ClassNotFoundException e) {
                e.printStackTrace();

            }
            connection = DriverManager.getConnection(DATABASE_URL);

            statement = connection.createStatement();

            resultSet = statement.executeQuery(
                    "SELECT  * FROM library");

            ResultSetMetaData metaData = resultSet.getMetaData();
            int numberOfColumns = metaData.getColumnCount();


            newtracks = new Track[50];
            //Track t = new Track();
            while (resultSet.next()) {

                for (int i = 1; i <= numberOfColumns; i++) {
                    objData[i - 1] = resultSet.getString(i);
                }
            //    n = Integer.valueOf(objData[4]);
                l = Long.valueOf(objData[6]);
                //newtracks[numberOfTracks] = new Track(objData[2], objData[1], objData[3], objData[4], l, objData[5], objData[7]);
                 Track t = new Track(objData[2], objData[1], objData[3], objData[4], l, objData[5], objData[7]);
                //addTrack(newtracks[numberOfTracks]);
                addTrack(t);

                numberOfTracks++;
            }




        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        } finally {
            try {
                resultSet.close();
                statement.close();
                connection.close();
            } catch (Exception exception) {
                exception.printStackTrace();

            }
        }


    }
}
