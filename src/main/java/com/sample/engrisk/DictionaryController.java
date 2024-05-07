package com.sample.engrisk;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.web.WebView;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

import java.util.Objects;

public class DictionaryController extends GeneralController {
    private Map<String, Word> data = new TreeMap<>();

    @FXML
    private RadioButton boleroButton;
    @FXML
    private MediaPlayer mediaPlayer;
    @FXML
    private ListView<String> wordList;
    @FXML
    private TextField searchField;
    @FXML
    private WebView definitionView;

    private DictionaryService dictionaryService = new DictionaryService();

    @FXML
    public void initialize() {
        try { // yes i know loading this media file before the words is just stupid but idc
            Media media = new Media(Objects.requireNonNull(getClass().getResource("/sounds/ThanhPhoBuon.mp3")).toExternalForm());
            mediaPlayer = new MediaPlayer(media);
        } catch (Exception e) {
            System.out.println("Error loading media: " + e.getMessage());
        }
        try {
            dictionaryService.loadData(); // Load data
        } catch (Exception e) {
            e.printStackTrace();
        }
        loadWordList();
        setupSearchField();
    }
    private void setupSearchField() {
        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) { // without this check, it will throw a null pointer exception
                String trimmedValue = newValue.trim();
                filterWordList(trimmedValue);
            } else {
                filterWordList(""); // no more null pointer exception after clearing search field hahahahahah
            }
        });

    }

    private void filterWordList(String query) { // Start of word match, not exact/whole/substring match
        if (query == null || query.isEmpty()) {
            wordList.getItems().clear();
            wordList.getItems().addAll(dictionaryService.getData().keySet());
        } else {
            String lowerCaseQuery = query.toLowerCase(); // convert query to lower case once
            var filtered = dictionaryService.getData().keySet().stream()
                    .filter(word -> word.toLowerCase().startsWith(lowerCaseQuery))
                    .collect(Collectors.toList());
            wordList.getItems().setAll(filtered);
        }
    }

    private void loadWordList() {
        wordList.getItems().clear(); // just to make sure
        wordList.getItems().addAll(dictionaryService.getData().keySet());
    }

    // nhac vang tram cam huhu
    @FXML
    private void handleBoleroAction() {
        if (boleroButton.isSelected()) {
            mediaPlayer.play();
        } else {
            mediaPlayer.pause();
        }
    }
    @FXML // Hangman game
    private void launchHangmanGame() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("game_view.fxml"));
        VBox gameRoot = loader.load();
        GameController gameController = loader.getController();

        Stage gameStage = new Stage();
        gameStage.setScene(new Scene(gameRoot));
        gameStage.setTitle("hangman! - up to 15 letters long");
        gameStage.setResizable(false);
        gameStage.initModality(Modality.APPLICATION_MODAL); // lock main window
        gameStage.show();

        gameController.initializeGame();
    }
    @FXML
    private void changeLanguage() {
        isVietnamese = !isVietnamese; // toggle language
        String translateText = isVietnamese ? "Dịch văn bản" : "Translate something";
        translationButton.setText(translateText);
        // text for other buttons similarly, i do this tomorrow
        try {
            dictionaryService.loadData(); // reload data with the new language
            loadWordList(); // refresh word list
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /* ON HOLD -
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
    */

}



