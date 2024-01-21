package org.example.mp3playerfx.model;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.stage.FileChooser;
import javafx.stage.Window;
import lombok.Getter;
import lombok.Setter;
import org.example.mp3playerfx.model.player.ClipPlayer;
import org.example.mp3playerfx.model.player.FXPlayer;
import org.example.mp3playerfx.model.player.Player;
import org.example.mp3playerfx.model.playlist.JSONPlaylist;
import org.example.mp3playerfx.model.playlist.Playlist;
import org.example.mp3playerfx.model.playlist.XMLPlaylist;
import org.example.mp3playerfx.model.playlist.XMLPlaylistAdapter;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

import static java.util.Collections.replaceAll;

@Getter
@Setter
public class AudioPlayerApplication {
    private Player player;
    private Library library;
    private Playlist playlist;

    public AudioPlayerApplication(String path) {
        library = new Library(path);
        playlist = new JSONPlaylist();
        player = new ClipPlayer(playlist);

    }

    public List <Song> checkPlaylist(List<String> names) {
        List <Song> songs = new ArrayList<>();
        for(String name: names) {
            Song song = library.getSongByName(name);
            if(song != null) {
               songs.add(song);
            }
        }
        return songs;
    }

    public void addSongToPlaylist(Song song) {
       playlist.addSong(song);
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
       playlist.saveToFile();
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
                System.out.println(name);
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
        playlist.setSongs(songsUpdated);
        player.setSongNum(getSongIndex(player.getCurrentSong()));
        player.setPlaylist(playlist);
    }

    private int getSongIndex(Song song) {
        for(int i=0;i<playlist.getSongs().size();i++) {
            if(playlist.getSongs().get(i).equals(song)) {
               return i;
            }
        }
        return 0;
    }
}
