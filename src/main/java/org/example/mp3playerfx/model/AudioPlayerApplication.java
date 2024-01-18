package org.example.mp3playerfx.model;
import lombok.Getter;
import lombok.Setter;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class AudioPlayerApplication {
    private Player player;
    private Library library;
    private List <File> playlist;

    public AudioPlayerApplication(String path) {
        library = new Library(path);
        playlist = generatePlaylist();
        player = new FXPlayer(playlist);
    }

    public List<File> generatePlaylist() {
        playlist = new ArrayList<>();
        try {
            playlist.add(library.getSongByName("Oasis-Supersonic.mp3"));
            playlist.add(library.getSongByName("The Strokes - Welcome to Japan.mp3"));
        }
        catch (FileNotFoundException e) {
            System.out.println("Couldn't find song");
        }
        return playlist;
    }

    public void addSongToPlaylist(File song) {
       playlist.add(song);
    }
}
