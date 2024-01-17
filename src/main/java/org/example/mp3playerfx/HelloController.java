package org.example.mp3playerfx;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;
import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;

public class HelloController implements Initializable {

    private File directory;

    private File[] files;

    private ArrayList<File> songs;

    private int songNumber;

    private boolean running;

    private String [] speeds = {"2.0","1.5","1.25","1","0.75","0.5","0.25"};

    private Timer timer;

    private String path = "/home/student/Music";

    private Media media;

    private MediaPlayer mediaPlayer;

    private double speed;

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
        speed = 1;
        pauseButton.setDisable(true);
        pauseButton.setVisible(false);
        addVolumeListener();
        songs = new ArrayList<>();
        directory = new File(path);
        files = directory.listFiles();

        if(files!=null) {
            for(File file: files) {
                songs.add(file);
                System.out.println(file);
            }
        }

        for(String speed : speeds) {
            speedBox.getItems().add(speed);
        }

        media = new Media(songs.get(songNumber).toURI().toString());
        mediaPlayer = new MediaPlayer(media);

        songLabel.setText(songs.get(songNumber).getName());
    }

    @FXML
    protected void onPlayClick() {
        beginTimer();
        playButton.setDisable(true);
        pauseButton.setDisable(false);
        pauseButton.setVisible(true);
        playButton.setVisible(false);
        mediaPlayer.setVolume(volumeSlider.getValue() *0.01);
        System.out.println("Playing "+songs.get(songNumber).getName());
        mediaPlayer.setRate(speed);
        mediaPlayer.play();
    }

    @FXML
    protected void onPauseClick() {
        cancelTimer();
        playButton.setDisable(false);
        pauseButton.setDisable(true);
        pauseButton.setVisible(false);
        playButton.setVisible(true);
        System.out.println("Pause");
        mediaPlayer.pause();
    }

    @FXML
    protected void onResetClick() {
        progressBar.setProgress(0);
        playButton.setDisable(false);
        pauseButton.setDisable(true);
        pauseButton.setVisible(false);
        playButton.setVisible(true);
        System.out.println("Reset");
        mediaPlayer.seek(Duration.seconds(0));
        mediaPlayer.stop();
    }

    @FXML
    protected void onPreviousClick() {
        System.out.println("Previous");
        playButton.setDisable(true);
        pauseButton.setDisable(false);
        pauseButton.setVisible(true);
        playButton.setVisible(false);
        if(songNumber > -1) {
            songNumber--;
            mediaPlayer.stop();

            if(running) {
                cancelTimer();
            }

            media = new Media(songs.get(songNumber).toURI().toString());
            mediaPlayer = new MediaPlayer(media);
            songLabel.setText(songs.get(songNumber).getName());
        }
        onPlayClick();
    }

    @FXML
    protected void onNextClick() {
        System.out.println("Next");
        playButton.setDisable(true);
        pauseButton.setDisable(false);
        pauseButton.setVisible(true);
        playButton.setVisible(false);
        if(songNumber < songs.size()-1) {
            songNumber++;
            mediaPlayer.stop();

            if(running) {
                cancelTimer();
            }
            media = new Media(songs.get(songNumber).toURI().toString());
            mediaPlayer = new MediaPlayer(media);
            songLabel.setText(songs.get(songNumber).getName());
        }
         onPlayClick();
    }

    @FXML
    public void changeSpeed() {
        mediaPlayer.setRate(Double.parseDouble(speedBox.getValue().toString()));
        speed = Double.parseDouble((String) speedBox.getValue());
    }

    public void addVolumeListener() {
       volumeSlider.valueProperty().addListener(new ChangeListener<Number>() {
           @Override
           public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
               mediaPlayer.setVolume(volumeSlider.getValue() *0.01);
           }
       });
    }

    public void beginTimer() {
       timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                running = true;
                double current = mediaPlayer.getCurrentTime().toSeconds();
                double end = media.getDuration().toSeconds();
                progressBar.setProgress(current/end);

                if(current/end == 1) {
                    cancelTimer();
                }

            }
        };
        timer.scheduleAtFixedRate(task,0,1000);
    }

    public void cancelTimer() {
        running = false;
        timer.cancel();
    }


}