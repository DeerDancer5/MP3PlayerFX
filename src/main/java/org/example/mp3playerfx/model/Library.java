package org.example.mp3playerfx.model;
import lombok.Getter;
import lombok.Setter;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class Library {

    private List <Song> songs;
    private File[] files;
    private String path;
    private File directory;

    public Library(String path) {
        this.path = path;

        directory = new File(path);
        files = directory.listFiles();
        songs = new ArrayList<>();

        if(files!=null) {
            for(File file: files) {
                if(file.getName().contains(".mp3")|file.getName().contains("wav")) {
                    songs.add(new Song(file));
                    System.out.println(file);
                }
            }
        }
    }

    public Song getSongByName(String name) {
        for (Song song: songs) {
            if(song.getFile().getName().equals(name)) {
                return song;
            }
        }
        return null;
    }

    public Song getSongByPath(String path) throws FileNotFoundException {
        for (Song song: songs) {
            if(song.getFile().getAbsolutePath().equals(path)) {
                return song;
            }
        }
        return null;
    }
}
