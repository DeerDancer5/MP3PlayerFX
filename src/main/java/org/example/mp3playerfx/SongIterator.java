package org.example.mp3playerfx;
import org.example.mp3playerfx.model.Song;

import java.util.Iterator;


public interface SongIterator  extends Iterator<Song> {
    Song previous();
}
