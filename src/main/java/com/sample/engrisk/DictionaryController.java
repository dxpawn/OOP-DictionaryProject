package com.sample.engrisk;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.web.WebView;
import javafx.stage.Stage;


public class DictionaryController {
    @FXML
    private Button gameButton;
    @FXML
    private TextField searchBar;
    @FXML
    private ListView<String> resultsBar;
    @FXML
    private ScrollBar scrollResultBar;
    @FXML
    private WebView definitionView;
    @FXML
    private Button translationButton;
    @FXML
    private Button speakButton;
    @FXML
    private Button findWordButton;
    @FXML
    private Button stfuButton;
    @FXML
    private Button crudButton;
    @FXML
    private Button suicide;
    @FXML
    private Button settingsButton;
    @FXML
    private Label welcomeText;
    @FXML
    private TextField inputField;

    @FXML
    protected void onBoleroButtonClick() {
        welcomeText.setText("Nhung ngay chua nhap ngu anh hay dat em ve...");
    }

    @FXML
    protected void onSuicideButtonClick() {
        welcomeText.setText("Duong thuong dau day ai nhan gian...");
    }

    // nhac vang remix
    @FXML
    private RadioButton boleroButton;
    private MediaPlayer mediaPlayer;

    public void initialize() {
        try {
            Media media = new Media(getClass().getResource("/data/VoNguaTrenDoiCoNon.mp3").toExternalForm());
            mediaPlayer = new MediaPlayer(media);
        } catch (Exception e) {
            System.out.println("Error loading media: " + e.getMessage());
        }
    }

    @FXML
    private void handleBoleroAction() {
        if (boleroButton.isSelected()) {
            mediaPlayer.play();
        } else {
            mediaPlayer.pause();
        }
    }

}