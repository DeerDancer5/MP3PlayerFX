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

    private List<File> songs;
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
                songs.add(file);
                System.out.println(file);
            }
        }
    }

    public File getSongByName(String name) throws FileNotFoundException {
        for (File song: songs) {
            if(song.getName().equals(name)) {
                return song;
            }
        }
        throw new FileNotFoundException();
    }
}
