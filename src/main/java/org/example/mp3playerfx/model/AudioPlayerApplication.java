package org.example.mp3playerfx.model;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Window;
import lombok.Getter;
import lombok.Setter;
import org.example.mp3playerfx.event.EventDispatcher;
import org.example.mp3playerfx.Settings;
import org.example.mp3playerfx.model.library.DirectoryManager;
import org.example.mp3playerfx.model.library.DirectoryObserver;
import org.example.mp3playerfx.model.library.Library;
import org.example.mp3playerfx.model.player.ClipPlayer;
import org.example.mp3playerfx.model.player.FXPlayer;
import org.example.mp3playerfx.model.player.Player;
import org.example.mp3playerfx.model.player.state.EmptyState;
import org.example.mp3playerfx.model.playlist.JSONPlaylist;
import org.example.mp3playerfx.model.playlist.Playlist;
import org.example.mp3playerfx.model.playlist.XMLPlaylist;
import org.example.mp3playerfx.model.playlist.XMLPlaylistAdapter;
import org.example.mp3playerfx.model.song.ShuffleIterator;
import org.example.mp3playerfx.model.song.Song;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import java.io.*;
import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
public class AudioPlayerApplication implements DirectoryObserver {
    private Player player;
    private Library library;
    private Playlist playlist;
    private Settings settings;
    private DirectoryManager directoryManager;

    public AudioPlayerApplication(Settings settings, Library library, Playlist playlist, Player player) {
        this.settings = settings;
        this.library = library;
        this.playlist = playlist;
        this.player = player;
        if(library.getSongs()!=null) {
            directoryManager = new DirectoryManager(settings.getPath());
            directoryManager.addObserver(this);
        }

    }

    public List <Song> checkPlaylist(List<String> names) {
        List <Song> songs = new ArrayList<>();
        for(String name: names) {
            Song song = library.getSongByFileName(name);
            if(song != null) {
               songs.add(song);
            }
        }
        return songs;
    }

    public void addSongToPlaylist(Song song) {
       playlist.addSong(song);
       if(player.getIterator() instanceof ShuffleIterator) {
           player.setIterator(new ShuffleIterator(player.getPlaylist().getSongs()));
       }
       checkSongsExtensions();
    }

    private void checkSongsExtensions() {

        for(Song song: playlist.getSongs()) {
           if(song.getFile().getName().contains("mp3") && player instanceof ClipPlayer){
               System.out.println("Switching players");
               player.stop();
               int songNum = player.getSongNum();
               player = new FXPlayer(playlist);
               player.setSongNum(songNum);
           }
        }
    }

    public void switchSong(Song song) {
        for(int i = 0;i<playlist.getSongs().size();i++) {
            if(playlist.getSongs().get(i).equals(song)) {
                player.setSongNum(i);
            }
        }
        checkSongsExtensions();
    }

    public void savePlaylist() {
       playlist.saveToFile(settings.getPath());
    }

    public void readPlaylist(ActionEvent event) {
        Window window = ((Node) (event.getSource())).getScene().getWindow();
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("JSON Files","*.json"));
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("XML Files","*.xml"));
        File file = fileChooser.showOpenDialog(window);
        event.consume();

        if(file!=null) {
            List <String> songNames = new ArrayList<>();

            if(file.getName().contains(".json")) {
                playlist = new JSONPlaylist();
            }

            else if(file.getName().contains("xml")) {
                XMLPlaylist xmLplaylist = new XMLPlaylist();
                playlist = new XMLPlaylistAdapter(xmLplaylist);
            }

            JSONArray parsedPlaylist = playlist.readFromFile(file);

            for (Object song : parsedPlaylist) {
                String name = ((JSONObject) song).get("path").toString();
                songNames.add(name);
            }

            playlist.setSongs(checkPlaylist(songNames));
            checkSongsExtensions();
            player.setPlaylist(playlist);
        }
    }

    public void updatePlaylist(ObservableList items) {
        List <Song> songsUpdated = new ArrayList<>();
        for(int i=0;i<items.size();i++) {
            songsUpdated.add((Song) items.get(i));
        }

        Song current = player.getCurrentSong();
        playlist.setSongs(songsUpdated);
        player.setPlaylist(playlist);

        player.setSongNum(getSongIndex(current));
    }

    private int getSongIndex(Song song) {
        for(int i=0;i<playlist.getSongs().size();i++) {
            if(playlist.getSongs().get(i).getFile().getName().equals(song.getFile().getName())) {
               return i;
            }
        }
        return 0;
    }

    @Override
    public void updateFiles() {
        library = new Library(settings.getPath());

        for(int i=0;i<playlist.getSongs().size();i++) {
            if(library.getSongByFileName(playlist.getSongs().get(i).getFile().getName())==null) {
                playlist.getSongs().remove(playlist.getSongs().remove(i));
            }
        }

        if(player.getIterator() instanceof ShuffleIterator) {
            player.setIterator(new ShuffleIterator(player.getPlaylist().getSongs()));
        }
        EventDispatcher.dispatchCustomEvent();
    }

    public void setShuffle(boolean checked) {
        if(checked) {
            player.setIterator(new ShuffleIterator(player.getPlaylist().getSongs()));
        }
        else {
            player.setIterator(playlist.iterator());
            player.getIterator().setCurrentIndex(player.getSongNum());
        }
    }

    public void changeLibraryPath(ActionEvent event) {
        Window window = ((Node) (event.getSource())).getScene().getWindow();
        DirectoryChooser directoryChooser = new DirectoryChooser();
        File file = directoryChooser.showDialog(window);
        event.consume();

        if(file!=null) {
            settings.setPath(file.getAbsolutePath());
            settings.saveSettings();
            library = new Library(settings.getPath());
            player.changeState(new EmptyState(player));
            player.getPlaylist().getSongs().clear();
            directoryManager = new DirectoryManager(settings.getPath());
            EventDispatcher.dispatchCustomEvent();
        }
    }

}
