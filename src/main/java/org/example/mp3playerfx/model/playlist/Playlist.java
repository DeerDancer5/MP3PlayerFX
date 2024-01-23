package org.example.mp3playerfx.model.playlist;

import org.example.mp3playerfx.model.song.SongIterator;
import org.example.mp3playerfx.model.song.Song;
import org.json.simple.JSONArray;

import java.io.File;
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
