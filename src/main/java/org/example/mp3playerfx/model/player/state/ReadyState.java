package org.example.mp3playerfx.model.player.state;
import org.example.mp3playerfx.model.player.Player;

public class ReadyState extends PlayerState {

    ReadyState(Player player) {
        super(player);
        System.out.println("Ready state");
    }

    @Override
    public void pressPlay() {
        player.play();
        player.changeState(new PlayingState(player));
    }

    @Override
    public void pressPause() {
        System.out.println("Not playing");
    }

    @Override
    public void pressStop() {
        player.stop();
        System.out.println("Not playing");
    }

    @Override
    public void pressNext() {
        player.next();
        player.changeState(new PlayingState(player));
    }

    @Override
    public void pressPrevious() {
        player.next();
        player.changeState(new PlayingState(player));
    }
}
