package org.example.mp3playerfx.model.player.state;
import org.example.mp3playerfx.model.player.Player;

public class EmptyState extends PlayerState{


    public EmptyState(Player player) {
        super(player);
        System.out.println("Empty state");
    }

    @Override
    public void pressPlay() {
        if(player.getPlaylist().getSongs().isEmpty()) {
            System.out.println("No songs in the playlist");
        }
        else {
            player.play();
            player.changeState(new PlayingState(player));
        }
    }

    @Override
    public void pressPause() {
        System.out.println("Not playing");
    }

    @Override
    public void pressStop() {
        System.out.println("Not playing");
    }

    @Override
    public void pressNext() {
        System.out.println("No songs in the playlist");
    }

    @Override
    public void pressPrevious() {
        System.out.println("No songs in the playlist");
    }
}
