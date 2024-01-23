package org.example.mp3playerfx.model.player;
import org.example.mp3playerfx.model.player.engine.PlayerEngine;
import org.example.mp3playerfx.model.player.state.PlayerState;
import org.example.mp3playerfx.model.playlist.Playlist;
import org.example.mp3playerfx.model.song.Song;
import org.example.mp3playerfx.model.song.SongIterator;


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

    Playlist getPlaylist();

    PlayerEngine getPlayerEngine();

    void setPlaylist(Playlist playlist);

    PlayerEngine createEngine();

    void changeState(PlayerState playerState);

    PlayerState getPlayerState();

    void setIterator(SongIterator iterator);
    SongIterator getIterator();
}
