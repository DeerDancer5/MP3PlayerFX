package org.example.mp3playerfx.model.library;
import lombok.Getter;
import lombok.Setter;
import org.example.mp3playerfx.model.song.Song;

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
        songs = new ArrayList<>();
        loadLibrary();

    }

    public Song getSongByFileName(String name) {
        for (Song song: songs) {
            if(song.getFile().getName().equals(name)) {
                return song;
            }
        }
        return null;
    }

    public void loadLibrary() {
        directory = new File(path);
        files = directory.listFiles();

        songs.clear();
        List <Song> newSongs = new ArrayList<>();
        if(files!=null) {
            for(File file: files) {
                if(file.getName().contains(".mp3")|file.getName().contains("wav")) {
                    newSongs.add(new Song(file));
                    System.out.println(file);
                }
            }
            songs.addAll(newSongs);
        }
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
