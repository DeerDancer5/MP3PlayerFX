package org.example.mp3playerfx.model.player.state;


import org.example.mp3playerfx.model.player.Player;

public abstract class PlayerState {

    Player player;

    PlayerState(Player player) {
        this.player= player;
    }

    public abstract void pressPlay();

    public abstract void pressPause();

    public abstract void pressStop();

    public abstract void pressNext();

    public abstract void pressPrevious();
}
