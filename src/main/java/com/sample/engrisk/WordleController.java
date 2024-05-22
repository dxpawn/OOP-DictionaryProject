package com.sample.engrisk;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.*;

public class WordleController {

    @FXML
    private TextField guessInput;
    @FXML
    private Label feedbackLabel, finalMessage, displayedWord;

    private DictionaryService dictionaryService = new DictionaryService();

    private String secretWord;
    private int attempts = 6;

    public void initializeWordle() {
        try {
            dictionaryService.loadData();
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
        Map<String, Word> data = dictionaryService.getData();
        List<String> words = new ArrayList<>(data.keySet());
        words.removeIf(word -> word.length() != 5 || word.contains("-") || word.contains(" ")); // remove words that are not 5 letters long
        Random random = new Random();
        secretWord = words.get(random.nextInt(words.size())).toUpperCase();
        System.out.println("Secret Word initialized: " + secretWord); // DEBUG OUTPUT
    }

    @FXML
    private void handleGuess() {
        String guess = guessInput.getText().toUpperCase();
        guessInput.clear();

        if (guess.length() != secretWord.length()) {
            displayedWord.setText("You stupid or what? Your guess must be " + secretWord.length() + " letters.");
            return;
        }

        if (guess.equals(secretWord)) {
            System.out.println("Win condition triggered."); // DEBUG OUTPUT
            displayWin();
        } else {
            feedbackLabel.setText(generateFeedback(guess));
            attempts--;
            if (attempts > 0) {
                finalMessage.setText("Guesses remaining: " + attempts);
            } else {
                finalMessage.setText("Game Over! The word was " + secretWord);
                guessInput.setDisable(true);
                onLoss();
            }
        }
    }

    private String generateFeedback(String guess) {
        StringBuilder feedback = new StringBuilder();
        for (int i = 0; i < guess.length(); i++) {
            if (guess.charAt(i) == secretWord.charAt(i)) {
                feedback.append(guess.charAt(i)).append(" ");
            } else if (secretWord.contains(guess.charAt(i) + "")) {
                feedback.append("+ "); // correct letter, wrong position
            } else {
                feedback.append("- "); // wrong letter
            }
        }
        System.out.println("Feedback check for " + guess + ": " + feedback); // DEBUG OUTPUT
        return feedback.toString().trim();
    }

    private void displayWin() {
        feedbackLabel.setText("Congratulations! You've guessed the word!");
        // delay 5s before switching to SicBo
        Timeline timeline = new Timeline(new KeyFrame(
                Duration.seconds(3),
                ae -> switchToSicBo()));
        timeline.play();
    }

    private void onLoss() {
        feedbackLabel.setText("Total defeat. Restarting in 2 seconds.");
        Timeline timeline = new Timeline(new KeyFrame(
                Duration.seconds(2),
                ae -> lossRestart()));
        timeline.play();
    }

    private void lossRestart() {
        initializeWordle();
        finalMessage.setText("Win this game... ");
        feedbackLabel.setText("or you will never escape from me.");
        displayedWord.setText("--X--");
        guessInput.setDisable(false);
        attempts = 6;
    }

    private void switchToSicBo() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("SicBoGame.fxml"));
            VBox root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage) feedbackLabel.getScene().getWindow();  // use any component that's part of the current scene
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
