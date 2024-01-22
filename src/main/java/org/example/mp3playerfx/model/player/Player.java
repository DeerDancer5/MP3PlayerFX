package org.example.mp3playerfx.model.player;
import org.example.mp3playerfx.model.player.engine.PlayerEngine;
import org.example.mp3playerfx.model.playlist.Playlist;
import org.example.mp3playerfx.model.Song;


public interface Player {

    void play();

    void pause();

    void stop();

    void previous();

    void next();

    void setVolume(double newVolume);

    void setSpeed(double newSpeed);

    double getCurrentTime();

    double getDuration();

    Song getCurrentSong();

    int getSongNum();

    void setSongNum(int num);

    void setPlaylist(Playlist playlist);
}
