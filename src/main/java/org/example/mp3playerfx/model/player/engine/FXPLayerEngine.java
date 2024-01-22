package org.example.mp3playerfx.model.player.engine;

import lombok.Getter;
import lombok.Setter;
import org.example.mp3playerfx.model.Song;

@Getter
@Setter
public class FXPLayerEngine implements PlayerEngine {
    private Song currentSong;

    @Override
    public void play() {

    }

    @Override
    public void stop() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void changeVolume(double volume) {

    }
}
