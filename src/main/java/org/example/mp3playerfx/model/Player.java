package org.example.mp3playerfx.model;
import java.io.File;

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

    File getCurrentSong();

}
