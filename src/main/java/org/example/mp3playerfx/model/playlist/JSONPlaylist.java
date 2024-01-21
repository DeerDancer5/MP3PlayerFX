package org.example.mp3playerfx.model.playlist;
import org.example.mp3playerfx.model.Song;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import java.io.*;
import java.util.ArrayList;
import java.util.List;


public class JSONPlaylist extends Playlist {

    private List<Song> songs;

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
    public void saveToFile() {
        JSONArray jsonPlaylist = new JSONArray();
        for(Song song : songs) {
            JSONObject jsonSong = new JSONObject();
            jsonSong.put("path",song.getFile().getName());
            jsonPlaylist.add(jsonSong);
        }

        FileWriter file;
        try {
            file = new FileWriter("/home/student/Music/playlist.json");
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


}
