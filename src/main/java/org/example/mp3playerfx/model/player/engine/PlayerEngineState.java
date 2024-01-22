package org.example.mp3playerfx.model.player.engine;


public abstract class PlayerEngineState {

    PlayerEngine playerEngine;

    PlayerEngineState(PlayerEngine playerEngine) {
        this.playerEngine = playerEngine;
    }

    public abstract void pressPlay();

    public abstract void pressPause();

    public abstract void pressStop();

    public abstract void pressNext();

    public abstract void pressPrevious();
}
