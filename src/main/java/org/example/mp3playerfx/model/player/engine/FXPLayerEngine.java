package org.example.mp3playerfx.model.player.engine;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;
import lombok.Getter;
import lombok.Setter;
import org.example.mp3playerfx.model.song.Song;


@Getter
@Setter
public class FXPLayerEngine implements PlayerEngine {
    private Song currentSong;
    private MediaPlayer mediaPlayer;
    private Media media;
    private boolean paused;
    private double speed;
    private double volume;

    public FXPLayerEngine() {
        paused = false;
    }

    @Override
    public void play() {
        if(!paused) {
            media = new Media(currentSong.getFile().toURI().toString());
            mediaPlayer = new MediaPlayer(media);
            mediaPlayer.setRate(speed);
            mediaPlayer.setVolume(volume);
        }
        mediaPlayer.play();
        paused = false;
    }

    @Override
    public void stop() {
        if(mediaPlayer!=null) {
            mediaPlayer.seek(Duration.seconds(0));
            mediaPlayer.stop();
        }
    }

    @Override
    public void pause() {
        mediaPlayer.pause();
        paused = true;
    }

    @Override
    public void changeVolume(double newVolume) {
        if(mediaPlayer!=null) {
            mediaPlayer.setVolume(newVolume);
        }
        volume = newVolume;
    }

    @Override
    public void changeSpeed(double newSpeed) {
        if(mediaPlayer!=null) {
            mediaPlayer.setRate(newSpeed);
        }
        speed = newSpeed;
    }

    @Override
    public double getCurrentTime() {
        return mediaPlayer.getCurrentTime().toSeconds();
    }

    @Override
    public double getDuration() {
        return media.getDuration().toSeconds();
    }


}
