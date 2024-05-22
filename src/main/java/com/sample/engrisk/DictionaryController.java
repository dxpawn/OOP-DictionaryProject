package com.sample.engrisk;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
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

import static com.sample.engrisk.DictionaryApplication.getDictionaryService;

// It's a shame I didn't have time to learn CSS...

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
    @FXML
    private Button crudButton;
    @FXML
    private Button speakButton;
    @FXML
    private Button sicBoButton;
    @FXML
    private Button infoButton;

    private DictionaryService dictionaryService = new DictionaryService();

    @FXML
    public void initialize() {
        try { // yes i know loading this media file before the words is just stupid but idc
            Media media = new Media(Objects.requireNonNull(getClass().getResource("/sounds/ConDuongXuaEmDi.mp3")).toExternalForm());
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
        setupSelectionListener(); // setup listener for word selection
        crudButton.setOnAction(event -> openCrudOperations()); // open CRUD operations
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

    // Sic Bo Game
    @FXML
    private void launchSicBoGame() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("SicBoGame.fxml"));
        VBox sicBoRoot = loader.load();
        SicBoController sicBoController = loader.getController();

        Stage sicBoStage = new Stage();
        sicBoStage.setScene(new Scene(sicBoRoot));
        sicBoStage.setTitle("Sic Bo - Tai Xiu 888 - Play to Win!");
        sicBoStage.setResizable(false);
        sicBoStage.initModality(Modality.APPLICATION_MODAL); // lock main window
        sicBoStage.show();

        sicBoController.initializeSicBo();
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

    // Translation API
    @FXML
    private void launchTranslation() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("TranslateView.fxml"));
        AnchorPane translateRoot = loader.load();
        GoogleTranslateController googleTranslateController = loader.getController();

        Stage translateStage = new Stage();
        translateStage.setScene(new Scene(translateRoot));
        translateStage.setTitle("Google Translate API Caller");
        translateStage.setResizable(false);
        translateStage.initModality(Modality.APPLICATION_MODAL); // lock main window
        translateStage.show();

        googleTranslateController.initializeTranslation();
    }

    @FXML
    private void changeLanguage() {
        isVietnamese = !isVietnamese; // toggle language
        String translateButtonText = isVietnamese ? "Dịch gì đó maybe" : "Translate something";
        translationButton.setText(translateButtonText);
        String switchLanguageText = isVietnamese ? "Switch to V" : "Switch to E";
        switchLanguageButton.setText(switchLanguageText);
        String crudButtonText = isVietnamese ? "Quản lý từ vựng" : "Manage words";
        crudButton.setText(crudButtonText);
        String gameButtonText = isVietnamese ? "Mèo Béo" : "Die by rope";
        gameButton.setText(gameButtonText);
        String infoButtonText = isVietnamese ? "cre" : "info";
        infoButton.setText(infoButtonText);
        String speakButtonText = isVietnamese ? "Nói" : "Speak";
        speakButton.setText(speakButtonText);
        String sicBoButtonText = isVietnamese ? "TaiXiu" : "Sic Bo";
        sicBoButton.setText(sicBoButtonText);

        try {
            dictionaryService.loadData(); // reload data
            loadWordList();
            refreshWebView();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void refreshWebView() {
        String selectedWordKey = wordList.getSelectionModel().getSelectedItem();
        if (selectedWordKey != null) {
            Word selectedWord = dictionaryService.getData().get(selectedWordKey.trim());
            if (selectedWord != null) {
                definitionView.getEngine().loadContent(selectedWord.getDef(), "text/html");
            } else {
                definitionView.getEngine().loadContent("Definition not found.", "text/html");
            }
        } else {
            definitionView.getEngine().loadContent("<p>No word is currently selected.</p>", "text/html");
        }
    }

    private void setupSelectionListener() {
        wordList.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> {
                    if (newValue != null) {
                        String selectedWordKey = newValue.trim().toLowerCase(); // normalize the key
                        System.out.println("Attempting to access word: " + selectedWordKey); // DEBUG OUTPUT
                        Word selectedWord = dictionaryService.getData().get(selectedWordKey);
                        if (selectedWord != null) {
                            String definition = selectedWord.getDef();
                            definitionView.getEngine().loadContent(definition, "text/html");
                        } else {
                            definitionView.getEngine().loadContent("Definition not found.", "text/html");
                        }
                    } else {
                        definitionView.getEngine().loadContent("<p>No word is currently selected.</p>", "text/html");
                    }
                }
        );
    }

    // SPEAK BUTTON
    @FXML
    private void handleSpeakButton() {
        String selectedWordKey = wordList.getSelectionModel().getSelectedItem();
        if (selectedWordKey != null) {
            System.out.println("Word to speak: " + selectedWordKey); // DEBUG OUTPUT
            // Apparently the Vietnamese diacritics are being removed, probably because I had to normalize them for the dictionary to work?
            speakAPI.AudioPlay(selectedWordKey, isVietnamese ? "vi-VN" : "en-US");
        }
    }

    /* Old code of handleSpeakButton()
    Revert back to this if there's a problem with the Vietnamese part of the speak button
    @FXML
    private void handleSpeakButton() {
        String selectedWordKey = wordList.getSelectionModel().getSelectedItem();
        if (selectedWordKey != null) {
            Word selectedWord = dictionaryService.getData().get(selectedWordKey.trim());
            if (selectedWord != null) {
                String word = selectedWord.getWord();
                speakAPI.AudioPlay(word, isVietnamese ? "vi-VN" : "en-US");
            }
        }
    }
     */

    // CRUD OPERATIONS
    @FXML
    public void openCrudOperations() {
        try {
            // Load the FXML file for CRUD operations
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/sample/engrisk/crudOpView.fxml"));
            Parent root = fxmlLoader.load();

            // Get the controller and set the dictionary service
            CrudController crudController = fxmlLoader.getController();
            crudController.setDictionaryService(dictionaryService); // Assuming dictionaryService is accessible here

            // Configure and show the new stage (window)
            Stage stage = new Stage();
            stage.setTitle("CRUD Operations");
            stage.setScene(new Scene(root));
            stage.initModality(Modality.APPLICATION_MODAL); // Optional: Makes this window modal relative to its parent
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();  // Consider more robust error handling or user feedback
        }
    }


}



