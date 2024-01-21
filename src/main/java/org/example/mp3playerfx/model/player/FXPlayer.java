package org.example.mp3playerfx.model.player;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;
import lombok.Getter;
import lombok.Setter;
import org.example.mp3playerfx.model.playlist.Playlist;
import org.example.mp3playerfx.model.Song;

import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
public class FXPlayer implements Player {
    private List <String> extensions;
    private MediaPlayer mediaPlayer;
    private Playlist playlist;
    private Media media;
    public int songNumber;
    private double speed;
    private double volume;
    private boolean paused;


    public FXPlayer(Playlist playlist) {
        extensions = new ArrayList<>();
        extensions.add("mp3");
        this.playlist = playlist;
        songNumber = 0;
        volume = 0.5;
        speed = 1;
        paused = false;
    }

    public void play() {
        if(!paused) {
            media = new Media(playlist.getSongs().get(songNumber).getFile().toURI().toString());
            mediaPlayer = new MediaPlayer(media);
            mediaPlayer.setRate(speed);
            mediaPlayer.setVolume(volume);
        }
        mediaPlayer.play();
        paused = false;
    }

    public void pause() {
       mediaPlayer.pause();
       paused = true;
    }

    public void stop() {
        if(mediaPlayer!=null) {
            mediaPlayer.seek(Duration.seconds(0));
            mediaPlayer.stop();
        }
    }

    public void previous() {
        if(songNumber > 0) {
            songNumber--;
        }
        else {
            songNumber = playlist.getSongs().size()-1;
        }
        stop();
        setMedia(new Media(playlist.getSongs().get(songNumber).getFile().toURI().toString()));
        setMediaPlayer(new MediaPlayer(media));
        play();
    }

    public void next() {
        songNumber = (songNumber+1)%playlist.getSongs().size();
        stop();
        setMedia(new Media(playlist.getSongs().get(songNumber).getFile().toURI().toString()));
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

    public Song getCurrentSong() {
        return playlist.getSongs().get(songNumber);
    }

    @Override
    public int getSongNum() {
       return songNumber;
    }

    @Override
    public void setSongNum(int num) {
        songNumber = num;
    }

    @Override
    public void setPlaylist(Playlist playlist) {
        this.playlist = playlist;
    }

}
