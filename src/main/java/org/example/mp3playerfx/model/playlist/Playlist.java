package org.example.mp3playerfx.model.playlist;

import org.example.mp3playerfx.SongIterator;
import org.example.mp3playerfx.model.Song;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public abstract class Playlist implements SongIterator {

    public abstract JSONArray readFromFile(File file);
    public abstract void saveToFile(String path);
    public abstract List<Song> getSongs();
    public abstract void setSongs(List <Song> songs);
    abstract void updatePlaylist();
    public abstract void addSong(Song song);
    public abstract SongIterator iterator();
    public abstract int getCurrentIndex();
}
