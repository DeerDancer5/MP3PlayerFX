package org.example.mp3playerfx.model;
import com.mpatric.mp3agic.*;
import lombok.Getter;
import lombok.Setter;
import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.tag.FieldKey;
import org.jaudiotagger.tag.Tag;
import org.jaudiotagger.tag.TagException;

import java.io.File;
import java.io.IOException;

@Getter
@Setter
public class Song {
    private File file;
    private String title;
    private String artist;
    private String album;

    public Song(File file)  {
        this.file = file;
        parseMetadata();
    }

    private void parseMetadata() {
       if(file.getPath().contains(".mp3")) {
            parseMp3();
        }
       else {
           parseOtherFormat();
       }

       checkParsedData();
    }

    private void parseMp3() {
        try {
            Mp3File mp3File = new Mp3File(file);
            if (mp3File.hasId3v1Tag()) {
                ID3v1 id3v1 = mp3File.getId3v1Tag();
                artist = id3v1.getArtist();
                title = id3v1.getTitle();
                album = id3v1.getAlbum();
            }

            if (mp3File.hasId3v2Tag()) {
                ID3v2 id3v2 = mp3File.getId3v2Tag();
                artist = id3v2.getArtist();
                title = id3v2.getTitle();
                album = id3v2.getAlbum();
            }

        } catch (IOException | UnsupportedTagException | InvalidDataException e) {
            e.printStackTrace();
        }
    }

    private void parseOtherFormat() {
        try {
            File tmp = new File(file.getAbsolutePath());
            AudioFile f = AudioFileIO.read(tmp);
            Tag tag = f.getTag();
            title = tag.getFirst(FieldKey.TITLE);
            artist = tag.getFirst(FieldKey.ARTIST);
            album = tag.getFirst(FieldKey.ALBUM);

        } catch (CannotReadException  | IOException | TagException |
                 ReadOnlyFileException | InvalidAudioFrameException e) {
            e.printStackTrace();
        }
    }

    private void checkParsedData() {
        if(artist==null) {
            artist = " ";
        }
        if(album==null) {
            album = " ";
        }
    }

}
