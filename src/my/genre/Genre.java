/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package my.genre;

import java.util.HashMap;

/**
 *
 * @author Carol
 */
public class Genre {
    private  HashMap<Integer,String>  genreTypes;
    
    public Genre()
    {
       genreTypes = new HashMap<>();
       genreTypes.put(BLUES, "Blues");
       genreTypes.put(CLASSIC_ROCK, "Classic Rock");
       genreTypes.put(COUNTRY,"Country");
       genreTypes.put(DANCE,"Dance");
       genreTypes.put(DISCO,"Disco");
       genreTypes.put(FUNK,"Funk");
       genreTypes.put(GRUNGE,"Grunge");
       genreTypes.put(HIP_HOP,"Hip Hop");
       genreTypes.put(JAZZ,"Jazz");
       genreTypes.put(METAL,"Metal");
//       genreTypes.put("New Age",NEW_AGE);
//       genreTypes.put("Oldies",OLDIES);
//       genreTypes.put("Other",OTHER);
//       genreTypes.put("Pop",POP);
//       genreTypes.put("R&B",R_N_B);
//       genreTypes.put("Rap",RAP);
//       genreTypes.put("Reggae",REGGAE);
//       genreTypes.put("Rock",ROCK);
//       genreTypes.put("Techno",TECHNO);
//       genreTypes.put("Industrial",INDUSTRIAL);
//       genreTypes.put("Alternative",ALTERNATIVE);
//       genreTypes.put("Ska",SKA);
//       genreTypes.put("Death Metal",DEATH_METAL);
//       genreTypes.put("Pranks",PRANKS);
//       genreTypes.put("Soundtrack",SOUNDTRACK);
//       genreTypes.put("Euro-Techno",EURO_TECHNO);
//       genreTypes.put("Ambient",AMBIENT);
//       genreTypes.put("Trip-Hop",TRIP_HOP);
//       genreTypes.put("Vocal",VOCAL);
//       genreTypes.put("Jazz+Funk",JAZZ_FUNK);
//       genreTypes.put("Fusion",FUSION);
//       genreTypes.put("Trance",TRANCE);
//       genreTypes.put("Classical",CLASSICAL);
//       genreTypes.put("Instrumental",INSTRUMENTAL);
//       genreTypes.put("Acid",ACID);
//       genreTypes.put("House",HOUSE);
//       genreTypes.put("Game",GAME);
//       genreTypes.put("Sound Clip",SOUND_CLIP);
//       genreTypes.put("Gospel",GOSPEL);
//       genreTypes.put("Noise",NOISE);
//       genreTypes.put("AlternRock",ALTERN_ROCK);
//       genreTypes.put("Bass",BASS);
//       genreTypes.put("Soul",SOUL);
//       genreTypes.put("Punk",PUNK);
//       genreTypes.put("Space",SPACE);
//       genreTypes.put("Meditative",MEDITATIVE); 
//       genreTypes.put("Instrumental Pop",INSTRUMENTAL_POP);
//       genreTypes.put("Instrumental Rock",INSTRUMENTAL_ROCK);
//       genreTypes.put("Ethnic",ETHNIC);
//       genreTypes.put("Gothic",GOTHIC);
//       genreTypes.put("Darkwave",DARKWAVE);
//       genreTypes.put("Techno-Industrial",TECHNO_INDUSTRIAL);
//       genreTypes.put("Electronic",ELECTRONIC);
//       genreTypes.put("Pop-Folk",POP_FOLK);
//       genreTypes.put("Eurodance",EURODANCE);
//       genreTypes.put("Dream",DREAM);
//       genreTypes.put("Southern Rock",SOUTHERN_ROCK);
//       genreTypes.put("Comedy",COMEDY);
//       genreTypes.put("Cult",CULT);
//       genreTypes.put("Gangsta",GANGSTA);
//       genreTypes.put("Top 40",TOP_40);
//       genreTypes.put("Christian Rap",CHRISTIAN_RAP);
//       genreTypes.put("Pop/Funk",POP_FUNK);
//       genreTypes.put("Jungle",JUNGLE);
//       genreTypes.put("Native American",NATIVE_AMERICAN);
//       genreTypes.put("Cabaret",CABARET);
//       genreTypes.put("New Wave",NEW_WAVE); 
//       genreTypes.put("Psychadelic",PSYCHADELIC);
//       genreTypes.put("Rave",RAVE);
//       genreTypes.put("Showtunes",SHOWTUNES);
//       genreTypes.put("Trailer",TRAILER);
//       genreTypes.put("Lo-Fi",LO_FI);
//       genreTypes.put("Tribal",TRIBAL);
//       genreTypes.put("Acid Punk",ACID_PUNK);
//       genreTypes.put("Acid Jazz",ACID_JAZZ);
//       genreTypes.put("Polka",POLKA);
//       genreTypes.put("Retro",RETRO);
//       genreTypes.put("Musical",MUSICAL);
//       genreTypes.put("Rock & Roll",ROCK_N_ROLL);
//       genreTypes.put("Hard Rock",HARD_ROCK);
       
    } 
    public String getGenre(int index){
        return genreTypes.get(index);
    }
 private static final int BLUES = 0;
private static final int CLASSIC_ROCK = 1;
private static final int COUNTRY = 2;
private static final int DANCE = 3;
private static final int DISCO = 4;
private static final int FUNK = 5;
private static final int GRUNGE = 6;
private static final int HIP_HOP = 7;
private static final int JAZZ = 8;
private static final int METAL =  9;
private static final int NEW_AGE = 10;
private static final int OLDIES = 11;
private static final int OTHER = 12;
private static final int POP = 13;
private static final int R_N_B = 14;
private static final int RAP = 15;
private static final int REGGAE = 16;
private static final int ROCK = 17;
private static final int TECHNO = 18;
private static final int INDUSTRIAL = 19;
private static final int ALTERNATIVE = 20;
private static final int SKA = 21;
private static final int DEATH_METAL = 22;
private static final int PRANKS = 23;
private static final int SOUNDTRACK = 24;
private static final int EURO_TECHNO = 25;
private static final int AMBIENT = 26;
private static final int TRIP_HOP = 27;
private static final int VOCAL = 28;
private static final int JAZZ_FUNK = 29;
private static final int FUSION = 30;
private static final int TRANCE = 31;
private static final int CLASSICAL = 32;
private static final int INSTRUMENTAL = 33;
private static final int ACID = 34;
private static final int HOUSE = 35;
private static final int GAME = 36;
private static final int SOUND_CLIP = 37;
private static final int GOSPEL = 38;
private static final int NOISE = 39;
private static final int ALTERN_ROCK = 40;
private static final int BASS = 41;
private static final int SOUL = 42;
private static final int PUNK = 43;
private static final int SPACE = 44;
private static final int MEDITATIVE = 45;
private static final int INSTRUMENTAL_POP = 46;
private static final int INSTRUMENTAL_ROCK = 47;
private static final int ETHNIC = 48;
private static final int GOTHIC = 49;
private static final int DARKWAVE = 50;
private static final int TECHNO_INDUSTRIAL = 51;
private static final int ELECTRONIC = 52;
private static final int POP_FOLK = 53;
private static final int EURODANCE = 54;
private static final int DREAM =55;
private static final int SOUTHERN_ROCK = 56;
private static final int COMEDY = 57;
private static final int CULT = 58;
private static final int GANGSTA = 59;
private static final int TOP_40 = 60;
private static final int CHRISTIAN_RAP = 61;
private static final int POP_FUNK = 62;
private static final int JUNGLE =63;
private static final int NATIVE_AMERICAN = 64;
private static final int CABARET = 65;
private static final int NEW_WAVE = 66;
private static final int PSYCHADELIC = 67;
private static final int RAVE = 68;
private static final int SHOWTUNES = 69;
private static final int TRAILER =70;
private static final int LO_FI = 71;
private static final int TRIBAL = 72;
private static final int ACID_PUNK = 73;
private static final int ACID_JAZZ = 74;
private static final int POLKA = 75;
private static final int RETRO = 76;
private static final int MUSICAL = 77;
private static final int ROCK_N_ROLL = 78;
private static final int HARD_ROCK = 79;
        
    }
    
