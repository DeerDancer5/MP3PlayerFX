package org.example.mp3playerfx.controller;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import org.example.mp3playerfx.model.AudioPlayerApplication;
import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;

public class PlayerController implements Initializable {

    private boolean running;

    private String [] speeds = {"2.0","1.5","1.25","1","0.75","0.5","0.25"};

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
    private Button playButton,pauseButton, resetButton,prevButton,nextButton;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setButtonsPlaying(false);
        addVolumeListener();

        for(String speed : speeds) {
            speedBox.getItems().add(speed);
        }

        app = new AudioPlayerApplication(path);
        songLabel.setText(app.getPlayer().getCurrentSong().getName());
    }

    @FXML
    protected void onPlayClick() {
        beginTimer();
        setButtonsPlaying(true);
        app.getPlayer().setVolume(volumeSlider.getValue() *0.01);
        app.getPlayer().play();
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
        beginTimer();

        if(running) {
            cancelTimer();
        }

        songLabel.setText(app.getPlayer().getCurrentSong().getName());
    }

    @FXML
    protected void onNextClick() {
        System.out.println("Next");
        setButtonsPlaying(true);
        app.getPlayer().next();
        beginTimer();

        if(running) {
            cancelTimer();
        }

        songLabel.setText(app.getPlayer().getCurrentSong().getName());
    }

    @FXML
    public void changeSpeed() {
        app.getPlayer().setSpeed(Double.parseDouble(speedBox.getValue().toString()));
    }

    public void addVolumeListener() {
       volumeSlider.valueProperty().addListener(new ChangeListener<Number>() {
           @Override
           public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
               app.getPlayer().setVolume(volumeSlider.getValue() *0.01);
           }
       });
    }

    public void beginTimer() {
        timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                running = true;
                double current = app.getPlayer().getCurrentTime();
                double end = app.getPlayer().getDuration();
                progressBar.setProgress(current/end);

                if(current/end == 1) {
                    cancelTimer();
                    onNextClick();
                }

            }
        };
        timer.scheduleAtFixedRate(task,0,1000);
    }

    public void cancelTimer() {
        running = false;
        timer.cancel();
    }

    public void setButtonsPlaying(boolean playing) {
       if(playing) {
           playButton.setDisable(true);
           pauseButton.setDisable(false);
           pauseButton.setVisible(true);
           playButton.setVisible(false);
       }
       else {
           playButton.setDisable(false);
           pauseButton.setDisable(true);
           pauseButton.setVisible(false);
           playButton.setVisible(true);
       }
    }


    @FXML
    void handleFileOverEvent(DragEvent event)
    {
        Dragboard db = event.getDragboard();
        if (db.hasFiles())
        {
            event.acceptTransferModes(TransferMode.COPY);
        }
        else
        {
            event.consume();
        }
    }

    @FXML
    void handleFileDroppedEvent(DragEvent event)
    {
        Dragboard db = event.getDragboard();
        File file = db.getFiles().get(0);

        System.out.printf(file.getAbsolutePath().toString());
        app.addSongToPlaylist(file);
        onNextClick();
    }

}