package org.example.mp3playerfx.model.player;
import lombok.Getter;
import lombok.Setter;
import org.example.mp3playerfx.model.player.engine.ClipPlayerEngine;
import org.example.mp3playerfx.model.player.engine.PlayerEngine;
import org.example.mp3playerfx.model.player.state.EmptyState;
import org.example.mp3playerfx.model.player.state.PlayerState;
import org.example.mp3playerfx.model.playlist.Playlist;
import org.example.mp3playerfx.model.song.Song;

@Getter
@Setter
public class ClipPlayer implements Player {
    private Playlist playlist;
    private PlayerState playerState;
    public int songNumber;
    private double speed;
    private double volume;
    private PlayerEngine playerEngine;

    public ClipPlayer(Playlist playlist) {
        this.playlist = playlist;
        this.playerState = new EmptyState(this);
        volume = 0.5;
        speed = 1;
        playerEngine = createEngine();
    }


    @Override
    public void play() {
        Song currentSong = playlist.getSongs().get(songNumber);
        playerEngine.setCurrentSong(currentSong);
        playerEngine.play();

    }

    @Override
    public void pause() {
       playerEngine.pause();
    }

    @Override
    public void stop() {
       playerEngine.stop();
    }

    @Override
    public void previous() {
        if(songNumber > 0) {
            songNumber--;
        }
        else {
            songNumber = playlist.getSongs().size()-1;
        }
        playerEngine.setCurrentSong(playlist.getSongs().get(songNumber));
        playerEngine.stop();
        playerEngine.play();

    }

    @Override
    public void next() {
        songNumber = (songNumber+1)%playlist.getSongs().size();
        playerEngine.setCurrentSong(playlist.getSongs().get(songNumber));
        playerEngine.stop();
        playerEngine.play();
    }

    @Override
    public void setVolume(double newVolume) {
        playerEngine.changeVolume(newVolume);
        volume = newVolume;
    }

    @Override
    public void setSpeed(double newSpeed) {
        playerEngine.changeSpeed(speed);
        speed = newSpeed;
    }


    @Override
    public double getCurrentTime() {
      return playerEngine.getCurrentTime();
    }

    @Override
    public double getDuration() {
        return playerEngine.getDuration();
    }

    @Override
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

    @Override
    public PlayerEngine createEngine() {
        ClipPlayerEngine clipPlayerEngine = new ClipPlayerEngine();
        clipPlayerEngine.changeVolume(volume);
        clipPlayerEngine.changeSpeed(speed);
        return clipPlayerEngine;
    }

    @Override
    public void changeState(PlayerState playerState) {
        this.playerState = playerState;
    }

}
