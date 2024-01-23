package org.example.mp3playerfx.model.song;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class ShuffleIterator implements SongIterator {

    private List<Song> songs;
    private List<Song> shuffled;
    private int currentIndex;

    public ShuffleIterator(List<Song> songs) {
        this.songs = songs;
        this.shuffled = new ArrayList<>(songs);
        Collections.shuffle(shuffled,new Random(shuffled.size()));
        for(Song song: shuffled) {
            System.out.println(song.getTitle());
        }
    }


    @Override
    public boolean hasNext() {
        return true;
    }

    @Override
    public Song next() {
        currentIndex = (currentIndex+1)%shuffled.size();
        return shuffled.get(currentIndex);
    }

    @Override
    public Song previous() {
        if(currentIndex> 0) {
            currentIndex--;
        }
        else {
            currentIndex= shuffled.size()-1;
        }
        return shuffled.get(currentIndex);
    }

    public int getCurrentIndex() {
        for(int i=0;i<songs.size();i++) {
            if(songs.get(i).getFile().getName().equals(shuffled.get(currentIndex).getFile().getName())) {
               return i;
            }
        }
        return 0;
    }

    @Override
    public void setCurrentIndex(int index) {
        currentIndex = index;
    }
}
