package org.example.mp3playerfx.model.player.state;

import org.example.mp3playerfx.model.player.Player;

public class PlayingState extends PlayerState {
    PlayingState(Player player) {
        super(player);
        System.out.println("Playing state");
    }

    @Override
    public void pressPlay() {
        System.out.println("Already playing");
    }

    @Override
    public void pressPause() {
        player.pause();
        player.changeState(new ReadyState(player));
    }

    @Override
    public void pressStop() {
        player.stop();
        player.changeState(new ReadyState(player));
    }

    @Override
    public void pressNext() {
        player.next();
    }

    @Override
    public void pressPrevious() {
        player.previous();
    }
}
