package com.sample.engrisk;

// import first, ask questions later

import javafx.animation.TranslateTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.media.AudioClip;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.util.Duration;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;



public class GeneralController {

    @FXML
    protected Button gameButton;
    @FXML
    protected ListView<String> wordList;
    @FXML
    protected WebView definitionView;
    @FXML
    protected Button translationButton;
    @FXML
    protected Button speakButton;
    @FXML
    protected Button findWordButton;
    @FXML
    protected Button stfuButton;
    @FXML
    protected Button crudButton;
    @FXML
    protected Button switchToVE;
    @FXML
    protected Button settingsButton;
    @FXML
    protected Label welcomeText;
    @FXML
    protected TextField inputField;
  
@FXML
protected void handleTranslateButtonAction(ActionEvent event) {
    String inputText = inputField.getText();
    try {
        String translatedText = translateAPI.translate(inputText);
    
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Translation Result");
        alert.setHeaderText(null);
        alert.setContentText("Translated text: " + translatedText);
        alert.showAndWait();
    } catch (IOException e) {
        e.printStackTrace();
    
    }
}



}
