package org.example.mp3playerfx.model.player.engine;

import org.example.mp3playerfx.model.song.Song;

public interface PlayerEngine {
    void play();

    void stop();

    void pause();

    void changeVolume(double volume);

    void changeSpeed(double speed);

    void setCurrentSong(Song song);

    double getCurrentTime();

    double getDuration();
}
