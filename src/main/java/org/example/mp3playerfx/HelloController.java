package org.example.mp3playerfx;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;

public class HelloController {
    @FXML
    private Label welcomeText;

    @FXML
    private ComboBox speedBox;

    @FXML
    private Slider volumeSlider;

    @FXML
    private Button playButton,pauseButton, resetButton,prevButton,nextButton;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }

    @FXML
    protected void onPlayClick() {
        System.out.println("Play");
    }

    @FXML
    protected void onPauseClick() {
        System.out.println("Pause");
    }

    @FXML
    protected void onResetClick() {
        System.out.println("Reset");
    }

    @FXML
    protected void onPreviousClick() {
        System.out.println("Previous");
    }

    @FXML
    protected void onNextClick() {
        System.out.println("Next");
    }
}