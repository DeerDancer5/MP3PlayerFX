package org.example.mp3playerfx.model.player;
import lombok.Getter;
import lombok.Setter;
import org.example.mp3playerfx.model.song.ShuffleIterator;
import org.example.mp3playerfx.model.song.SongIterator;
import org.example.mp3playerfx.model.player.engine.FXPLayerEngine;
import org.example.mp3playerfx.model.player.engine.PlayerEngine;
import org.example.mp3playerfx.model.player.state.EmptyState;
import org.example.mp3playerfx.model.player.state.PlayerState;
import org.example.mp3playerfx.model.playlist.Playlist;
import org.example.mp3playerfx.model.song.Song;

@Getter
@Setter
public class FXPlayer implements Player {
    private PlayerEngine playerEngine;
    private PlayerState playerState;
    private Playlist playlist;
    private SongIterator playlistIterator;
    public int songNumber;
    private double speed;
    private double volume;


    public FXPlayer(Playlist playlist) {
        this.playlist = playlist;
        this.playerState = new EmptyState(this);
        this.playlistIterator = playlist.iterator();
        volume = 0.5;
        speed = 1;
        playerEngine = createEngine();
    }

    public void play() {
        Song current = playlist.getSongs().get(songNumber);
        playerEngine.setCurrentSong(current);
        playerEngine.play();
    }

    public void pause() {
        playerEngine.pause();
    }


    public void stop() {
        playerEngine.stop();
    }

    public void previous() {

        playerEngine.stop();
        playerEngine.setCurrentSong(playlistIterator.previous());
        songNumber = playlistIterator.getCurrentIndex();
        playerEngine.play();
    }

    public void next() {

        playerEngine.stop();
        playerEngine.setCurrentSong(playlistIterator.next());
        songNumber = playlistIterator.getCurrentIndex();
        playerEngine.play();
    }

    public void setVolume(double newVolume) {
        playerEngine.changeVolume(newVolume);
        volume = newVolume;
    }

    public void setSpeed(double newSpeed) {
        playerEngine.changeSpeed(newSpeed);
        speed = newSpeed;
    }

    public double getCurrentTime() {
        return playerEngine.getCurrentTime();
    }

    public double getDuration() {
        return playerEngine.getDuration();
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

    @Override
    public PlayerEngine createEngine() {
        FXPLayerEngine fxpLayerEngine = new FXPLayerEngine();
        fxpLayerEngine.changeSpeed(speed);
        fxpLayerEngine.changeVolume(volume);
        return fxpLayerEngine;
    }

    @Override
    public void changeState(PlayerState playerState) {
        this.playerState = playerState;
    }

    @Override
    public void setIterator(SongIterator iterator) {
        playlistIterator = iterator;
    }

    @Override
    public SongIterator getIterator() {
        return playlistIterator;
    }

}
