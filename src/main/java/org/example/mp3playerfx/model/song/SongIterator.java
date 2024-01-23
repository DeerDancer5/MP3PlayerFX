package org.example.mp3playerfx.model.song;

import java.util.Iterator;


public interface SongIterator  extends Iterator<Song> {
    Song previous();
    int getCurrentIndex();
    void setCurrentIndex(int index);
}
