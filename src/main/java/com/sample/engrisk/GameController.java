package com.sample.engrisk;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import java.net.URL;

import java.util.*;

public class GameController extends GeneralController {

    @FXML
    private Label wordLabel;
    @FXML
    private ImageView hangmanImage;
    @FXML
    private TextField guessField;
    @FXML
    private Label messageLabel;
    @FXML
    private Label charCountLabel;
    @FXML
    private Label guessedLettersLabel;

    private String wordToGuess;
    private List<Character> guessedLetters;
    private int remainingGuesses;
    private int stage;
    private SoundPlayer soundPlayer = new SoundPlayer();

    public void initializeGame() {
        DictionaryService dictionaryService = new DictionaryService();
        try {
            dictionaryService.loadData();
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
        Map<String, Word> data = dictionaryService.getData();
        List<String> words = new ArrayList<>(data.keySet());
        words.removeIf(word -> word.length() < 5 || word.length() > 15 || word.contains("-")); // remove words that has hyphens, are too short or too long
        Random random = new Random();
        wordToGuess = words.get(random.nextInt(words.size()));

        guessedLetters = new ArrayList<>();
        remainingGuesses = 6;
        stage = 0;
        updateWordDisplay();
        updateHangmanImage();
        messageLabel.setText("");
        updateCharCount();
        resetGuessedLetters();
    }

    @FXML
    private void handleGuess() {
        String guess = guessField.getText().toLowerCase();
        if (guess.length() == 1 && Character.isLetter(guess.charAt(0))) {
            char letter = guess.charAt(0);
            if (guessedLetters.contains(letter)) {
                messageLabel.setText("You already guessed that letter!");
            } else {
                guessedLetters.add(letter);
                // display guessed characters
                StringBuilder guessedLettersDisplay = new StringBuilder();
                for (char c : guessedLetters) {
                    guessedLettersDisplay.append(c).append(" ");
                }
                guessedLettersLabel.setText("Guessed letters: " + guessedLettersDisplay);
                // When you use the + operator to concatenate a String with another object,
                // Java implicitly calls the toString() method on that object to obtain its string representation. Therefore, guessedLettersDisplay.toString() is redundant.
                if (wordToGuess.indexOf(letter) != -1) {
                    updateWordDisplay();
                    if (isWordGuessed()) {
                        messageLabel.setText("You have won, for now...");
                        soundPlayer.playSound("Victory.mp3");
                    }
                } else {
                    remainingGuesses--;
                    stage++;
                    updateHangmanImage();
                    if (remainingGuesses == 0) {
                        messageLabel.setText("You so stupid! The word was: " + wordToGuess);
                        soundPlayer.playSound("Defeat.mp3");
                        // note: this won't stop you from keep playing after you lost
                    }
                }
            }
            guessField.clear();
        } else {
            messageLabel.setText("Invalid input! Enter one letter only!");
        }
    }

    private void updateWordDisplay() {
        StringBuilder display = new StringBuilder();
        for (char c : wordToGuess.toCharArray()) {
            if (guessedLetters.contains(c)) {
                display.append(c);
            } else {
                display.append("_");
            }
            display.append(" ");
        }
        wordLabel.setText(display.toString());
        updateCharCount();
    }

    private void updateHangmanImage() {
        // images must be named hangman0.jpg, hangman1.jpg, ..., hangman6.jpg
        Image image = new Image(getClass().getResourceAsStream("/images/hangman" + stage + ".jpg"));
        hangmanImage.setImage(image);
    }

    private boolean isWordGuessed() {
        for (char c : wordToGuess.toCharArray()) {
            if (!guessedLetters.contains(c)) {
                return false;
            }
        }
        return true;
    }

    private void updateCharCount() {
        int charCount = wordToGuess.replaceAll("[^a-zA-Z]", "").length(); // only count letters
        charCountLabel.setText("Characters: " + charCount);
    }

    private void resetGuessedLetters() {
        guessedLetters.clear();
        guessedLettersLabel.setText("Guessed letters: ");
    }

    @FXML
    private void restartGame() {
        initializeGame(); // call initializeGame method to reset the game
    }
}