package org.example.mp3playerfx;

import org.example.mp3playerfx.model.AudioPlayerApplication;
import org.example.mp3playerfx.model.library.Library;
import org.example.mp3playerfx.model.player.Player;
import org.example.mp3playerfx.model.playlist.Playlist;

public class AudioPlayerApplicationBuilder {
    private Settings settings;
    private Library library;
    private Playlist playlist;
    private Player player;

    public AudioPlayerApplicationBuilder withSettings(Settings settings) {
        this.settings = settings;
        return this;
    }

    public AudioPlayerApplicationBuilder withLibrary(Library library) {
        this.library = library;
        return this;
    }

    public AudioPlayerApplicationBuilder withPlaylist(Playlist playlist) {
        this.playlist = playlist;
        return this;
    }

    public AudioPlayerApplicationBuilder withPlayer(Player player) {
        this.player = player;
        return this;
    }

    public AudioPlayerApplication build() {
        if (library == null) {
            library = new Library(null);
        }
        return new AudioPlayerApplication(settings, library, playlist, player);
    }
}

