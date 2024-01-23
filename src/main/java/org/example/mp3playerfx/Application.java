package org.example.mp3playerfx;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import org.example.mp3playerfx.controller.PlayerController;
import org.example.mp3playerfx.model.AudioPlayerApplication;
import org.example.mp3playerfx.model.library.Library;
import org.example.mp3playerfx.model.player.ClipPlayer;
import org.example.mp3playerfx.model.player.Player;
import org.example.mp3playerfx.model.playlist.JSONPlaylist;
import org.example.mp3playerfx.model.playlist.Playlist;

import java.io.IOException;


public class Application extends javafx.application.Application {
    @Override
    public void start(Stage stage) throws IOException {

        AudioPlayerApplication app = buildApplication();
        FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("playerView.fxml"));
        fxmlLoader.setControllerFactory(controllerClass -> new PlayerController(app));

        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("MP3 Player FX");
        stage.setScene(scene);
        stage.show();

        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent windowEvent) {
                System.out.println(app.getSettings().getPath());
                Platform.exit();
                System.exit(0);
            }
        });
    }

    public static void main(String[] args) {
        launch();
    }

    public AudioPlayerApplication buildApplication() {
        Settings settings = Settings.getSettingsInstance();
        Playlist playlist = new JSONPlaylist();
        Player player = new ClipPlayer(playlist);

        if(settings.getPath()!=null) {
            Library library = new Library(settings.getPath());
            AudioPlayerApplication app = new AudioPlayerApplicationBuilder()
                    .withSettings(settings)
                    .withPlaylist(playlist)
                    .withPlayer(player)
                    .withLibrary(library).build();
            return app;
        }
        else {
            System.out.println("brak ustawien");
            AudioPlayerApplication app = new AudioPlayerApplicationBuilder()
                    .withSettings(settings)
                    .withPlaylist(playlist)
                    .withPlayer(player).build();
            return app;
        }
    }
}