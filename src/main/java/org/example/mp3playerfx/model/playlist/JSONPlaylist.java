package org.example.mp3playerfx.model.playlist;
import lombok.Getter;
import org.example.mp3playerfx.SongIterator;
import org.example.mp3playerfx.model.Song;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import java.io.*;
import java.util.ArrayList;
import java.util.List;


@Getter
public class JSONPlaylist extends Playlist {

    private List<Song> songs;
    private int currentIndex;

    public JSONPlaylist() {
        this.songs = new ArrayList<>();
    }

    @Override
    public JSONArray readFromFile(File file) {
        JSONParser parser = new JSONParser();
        JSONArray parsedPlaylist = null;
        try {
            parsedPlaylist = (JSONArray) parser.parse(
                    new InputStreamReader(new FileInputStream(file.getAbsolutePath())));


        } catch (ParseException | IOException e) {
            System.out.println("Error parsing JSON");
            e.printStackTrace();
        }
        return parsedPlaylist;
    }

    @Override
    public void saveToFile(String path) {
        JSONArray jsonPlaylist = new JSONArray();
        for(Song song : songs) {
            JSONObject jsonSong = new JSONObject();
            jsonSong.put("path",song.getFile().getName());
            jsonPlaylist.add(jsonSong);
        }

        FileWriter file;
        try {
            file = new FileWriter(path+"playlist.json");
            file.write(jsonPlaylist.toJSONString());
            file.close();
        } catch (IOException e) {
            System.out.println("Error saving json");
        }
    }

    @Override
    public List<Song> getSongs() {
        return songs;
    }

    @Override
    public void setSongs(List<Song> songs) {
        this.songs = songs;
    }

    @Override
    public void updatePlaylist() {

    }

    @Override
    public void addSong(Song song) {
        songs.add(song);
    }


    @Override
    public boolean hasNext() {
        return true;
    }

    @Override
    public Song next() {
        currentIndex = (currentIndex+1)%songs.size();
        return songs.get(currentIndex);
    }

    @Override
    public SongIterator iterator() {
        return this;
    }

    @Override
    public Song previous() {
        if(currentIndex> 0) {
            currentIndex--;
        }
        else {
            currentIndex= songs.size()-1;
        }
        return songs.get(currentIndex);
    }
}
