package org.example.mp3playerfx.controller;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.*;
import org.example.mp3playerfx.event.EventDispatcher;
import org.example.mp3playerfx.event.UpdateLibraryEvent;
import org.example.mp3playerfx.model.AudioPlayerApplication;
import org.example.mp3playerfx.SongCellFactory;
import org.example.mp3playerfx.model.Song;

import java.io.*;
import java.net.URL;
import java.util.*;

public class PlayerController implements Initializable {

    private boolean running;

    private String[] speeds = {"2.0", "1.5", "1.25", "1", "0.75", "0.5", "0.25"};

    private Timer timer;

    private AudioPlayerApplication app;

    private String path = "/home/student/Music";

    @FXML
    private Label songLabel;

    @FXML
    private ComboBox speedBox;

    @FXML
    private Slider volumeSlider;

    @FXML
    private ProgressBar progressBar;

    @FXML
    private Button playButton, pauseButton, resetButton, prevButton, nextButton;

    @FXML
    ListView libraryView;

    @FXML
    ListView playlistView;

    @FXML
    RadioButton nameButton, artistButton, albumButton;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        playButton.setDisable(true);
        pauseButton.setDisable(true);
        resetButton.setDisable(true);
        prevButton.setDisable(true);
        nextButton.setDisable(true);
        pauseButton.setVisible(false);

        nameButton.setSelected(true);
        addVolumeListener();

        for (String speed : speeds) {
            speedBox.getItems().add(speed);
        }

        app = new AudioPlayerApplication();
        loadLibraryView();
        loadPlaylistView();
        addLibraryListener();
        sortLibrary();
    }

    @FXML
    protected void onPlayClick() {
        setButtonsPlaying(true);
        app.getPlayer().play();
        updatePlaylistView();
        beginTimer();
        songLabel.setText(app.getPlayer().getCurrentSong().getFile().getName());
    }

    @FXML
    protected void onPauseClick() {
        cancelTimer();
        setButtonsPlaying(false);
        System.out.println("Pause");
        app.getPlayer().pause();
    }

    @FXML
    protected void onResetClick() {
        progressBar.setProgress(0);
        setButtonsPlaying(false);
        app.getPlayer().stop();
    }

    @FXML
    protected void onPreviousClick() {
        System.out.println("Previous");
        setButtonsPlaying(true);
        app.getPlayer().previous();
        updatePlaylistView();
        beginTimer();

        if (running) {
            cancelTimer();
        }

        songLabel.setText(app.getPlayer().getCurrentSong().getFile().getName());
    }

    @FXML
    protected void onNextClick() {
        System.out.println("Next");
        setButtonsPlaying(true);
        app.getPlayer().next();
        updatePlaylistView();
        beginTimer();

        if (running) {
            cancelTimer();
        }

        songLabel.setText(app.getPlayer().getCurrentSong().getFile().getName());
    }

    @FXML
    public void changeSpeed() {
        app.getPlayer().setSpeed(Double.parseDouble(speedBox.getValue().toString()));
    }

    public void addVolumeListener() {
        volumeSlider.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                app.getPlayer().setVolume(volumeSlider.getValue() * 0.01);
            }
        });
    }

    public void addLibraryListener() {
        EventHandler<UpdateLibraryEvent> customEventHandler = event -> {
            loadPlaylistView();
            loadLibraryView();
            sortLibrary();
        };
        EventDispatcher.setCustomEventHandler(customEventHandler);
    }

    public void beginTimer() {
        timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                running = true;
                double current = app.getPlayer().getCurrentTime();
                double end = app.getPlayer().getDuration();
                if (running) {
                    progressBar.setProgress(current / end);
                }

                if (current / end == 1) {
                    cancelTimer();
                    Platform.runLater(() -> {
                        onNextClick();
                    });
                }

            }
        };
        timer.scheduleAtFixedRate(task, 0, 1000);
    }

    public void cancelTimer() {
        running = false;
        timer.cancel();
    }

    public void setButtonsPlaying(boolean playing) {
        if (playing) {
            playButton.setDisable(true);
            pauseButton.setDisable(false);
            pauseButton.setVisible(true);
            playButton.setVisible(false);
        } else {
            playButton.setDisable(false);
            pauseButton.setDisable(true);
            pauseButton.setVisible(false);
            playButton.setVisible(true);
        }
    }


    @FXML
    void handleFileOverEvent(DragEvent event) {
        Dragboard db = event.getDragboard();
        if (db.hasFiles()) {
            event.acceptTransferModes(TransferMode.COPY);
        } else {
            event.consume();
        }
    }

    @FXML
    void handleFileDroppedEvent(DragEvent event) {
        Dragboard db = event.getDragboard();
        File file = db.getFiles().get(0);

        System.out.printf(file.getAbsolutePath().toString());
        app.addSongToPlaylist(new Song(file));
        loadPlaylistView();
        app.switchSong(app.getPlaylist().getSongs().get(app.getPlaylist().getSongs().size() - 1));
        onResetClick();
        onPlayClick();
    }

    @FXML
    void handleDoubleClickPlaylistEvent(MouseEvent event) {
        if (event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 2) {
            app.switchSong((Song) playlistView.getSelectionModel().getSelectedItem());
            onResetClick();
            onPlayClick();
        }
    }

    @FXML
    void handleDoubleClickLibraryEvent(MouseEvent event) {
        if (event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 2) {
            System.out.println(libraryView.getSelectionModel().getSelectedItem().toString());
            app.addSongToPlaylist((Song) libraryView.getSelectionModel().getSelectedItem());
            loadPlaylistView();
            app.switchSong(app.getPlaylist().getSongs().get(app.getPlaylist().getSongs().size() - 1));
            updatePlaylistView();
            onResetClick();
            onPlayClick();
        }
    }

    @FXML
    private void sortLibrary() {
        List<Song> songs = app.getLibrary().getSongs();
        if (nameButton.isSelected()) {
            Collections.sort(songs, Comparator.comparing(Song::getTitle));
        }

        if (artistButton.isSelected()) {
            Collections.sort(songs, Comparator.comparing(Song::getArtist));
        }

        if (albumButton.isSelected()) {
            Collections.sort(songs, Comparator.comparing(Song::getAlbum));
        }
        app.getLibrary().setSongs(songs);
        loadLibraryView();
    }

    @FXML
    private void initializeDragAndDrop() {
        app.updatePlaylist(playlistView.getItems());
        playlistView.setCellFactory(listView -> {
            SongCellFactory cellFactory = new SongCellFactory();
            cellFactory.setOnDragOver(event -> {
                Dragboard db = event.getDragboard();
                if (db.hasString()) {
                    event.acceptTransferModes(TransferMode.MOVE);
                }
                event.consume();
            });
            cellFactory.setOnDragDropped(event -> {
                Dragboard db = event.getDragboard();
                boolean success = false;
                if (db.hasString()) {
                    if (!db.getString().contains("file")) {
                        int draggedIndex = Integer.parseInt(db.getString());
                        Song draggedSong = app.getPlaylist().getSongs().remove(draggedIndex);
                        app.getPlaylist().addSong(draggedSong);
                        playlistView.getItems().setAll(app.getPlaylist());


                        success = true;
                    }

                }
                event.setDropCompleted(success);
                event.consume();
            });
            return cellFactory;
        });
    }


    private void loadLibraryView() {
        Platform.runLater(()-> {
            List<Song> songs = app.getLibrary().getSongs();
            libraryView.getItems().clear();
            libraryView.getItems().addAll(songs);
            libraryView.setCellFactory(libraryView -> new SongCellFactory());
        });

    }

    private void loadPlaylistView() {
        Platform.runLater(()-> {
            playlistView.getItems().clear();
            playlistView.getSelectionModel().clearSelection();
            playlistView.getItems().addAll(app.getPlaylist().getSongs());
            playlistView.setCellFactory(playlistView -> new SongCellFactory());
        });
    }


    public void updatePlaylistView() {
            playlistView.getSelectionModel().select(app.getPlayer().getSongNum());
    }


    @FXML
    public void savePlaylist() throws IOException {
        List<Song> saved = new ArrayList<>();
        for (int i = 0; i < playlistView.getItems().size(); i++) {
            saved.add((Song) playlistView.getItems().get(i));
        }
        app.getPlaylist().setSongs(saved);
        app.savePlaylist();
    }

    @FXML
    public void readPlaylist(ActionEvent event) {
        app.readPlaylist(event);
        loadPlaylistView();

    }
}