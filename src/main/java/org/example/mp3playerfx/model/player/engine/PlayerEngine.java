package org.example.mp3playerfx.model.player.engine;

public interface PlayerEngine {
    void play();

    void stop();

    void pause();

    void changeVolume(double volume);
}
