package org.example.mp3playerfx.model;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;
import lombok.Getter;
import lombok.Setter;
import java.io.File;
import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
public class FXPlayer implements Player {
    private List <String> extensions;
    private MediaPlayer mediaPlayer;
    private Media media;
    private List<File> songs;
    private int songNumber;
    private double speed;
    private double volume;


    public FXPlayer(List <File> songs) {
        extensions = new ArrayList<>();
        extensions.add("mp3");
        this.songs = songs;
        songNumber = 0;
        media = new Media(songs.get(songNumber).toURI().toString());
        mediaPlayer = new MediaPlayer(media);
        setVolume(50);
        setSpeed(1);
    }

    public void play() {
        mediaPlayer.setRate(speed);
        mediaPlayer.setVolume(volume);
        mediaPlayer.play();
    }

    public void pause() {
       mediaPlayer.pause();
    }

    public void stop() {
        mediaPlayer.seek(Duration.seconds(0));
        mediaPlayer.stop();
    }

    public void previous() {
        if(songNumber > 0) {
            songNumber--;
        }
        else {
            songNumber = songs.size()-1;
        }
        stop();
        setMedia(new Media(songs.get(songNumber).toURI().toString()));
        setMediaPlayer(new MediaPlayer(media));
        play();
    }

    public void next() {
        songNumber = (songNumber+1)%songs.size();
        stop();
        setMedia(new Media(songs.get(songNumber).toURI().toString()));
        setMediaPlayer(new MediaPlayer(media));
        play();
    }

    public void setVolume(double newVolume) {
        mediaPlayer.setVolume(newVolume);
        volume = newVolume;
    }

    public void setSpeed(double newSpeed) {
        mediaPlayer.setRate(newSpeed);
        speed = newSpeed;
    }

    public double getCurrentTime() {
        return mediaPlayer.getCurrentTime().toSeconds();
    }

    public double getDuration() {
        return media.getDuration().toSeconds();
    }

    public File getCurrentSong() {
        return songs.get(songNumber);
    }

}
