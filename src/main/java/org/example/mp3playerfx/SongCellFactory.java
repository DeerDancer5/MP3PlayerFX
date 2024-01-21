package org.example.mp3playerfx;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.AnchorPane;
import lombok.SneakyThrows;
import org.example.mp3playerfx.model.Song;

import java.io.IOException;

public class SongCellFactory extends ListCell<Song> {

    @FXML
    private Label title;

    @FXML
    private Label artist;

    @FXML
    private Label album;

    @FXML
    private AnchorPane pane;

    private FXMLLoader mLLoader;

    @SneakyThrows
    @Override
    public void updateItem(Song song, boolean empty) {
        super.updateItem(song, empty);
        if (empty || song == null) {
            setText(null);
            setGraphic(null);
            setContentDisplay(ContentDisplay.TEXT_ONLY);
        } else {
            if (mLLoader == null) {
                mLLoader = new FXMLLoader(getClass().getResource("/org/example/mp3playerfx/songCell.fxml"));
                mLLoader.setController(this);
                try {
                    mLLoader.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                setOnDragDetected(event -> {
                    if (getItem() == null) {
                        return;
                    }

                    Dragboard dragboard = startDragAndDrop(TransferMode.MOVE);
                    ClipboardContent content = new ClipboardContent();
                    content.putString(String.valueOf(getIndex()));
                    dragboard.setContent(content);

                    setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
                    event.consume();
                });

                setOnDragOver(event -> {
                    if (event.getGestureSource() != this && event.getDragboard().hasString()) {
                        event.acceptTransferModes(TransferMode.MOVE);
                    }
                    event.consume();
                });

                setOnDragDropped(event -> {
                    Dragboard db = event.getDragboard();
                    boolean success = false;

                    if (db.hasString()) {
                        int draggedIndex = Integer.parseInt(db.getString());
                        int thisIndex = getIndex();

                        getListView().getItems().add(thisIndex, getListView().getItems().remove(draggedIndex));

                        getListView().getSelectionModel().clearSelection();

                        success = true;
                    }

                    event.setDropCompleted(success);
                    event.consume();
                });
            }

            title.setText(song.getTitle());
            artist.setText(song.getArtist());
            album.setText(song.getAlbum());

            setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
            setGraphic(pane);
        }
    }
}
