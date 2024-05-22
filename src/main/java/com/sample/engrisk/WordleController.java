package com.sample.engrisk;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import javafx.scene.control.Button;

public class WordleController {

    @FXML
    private TextField guessInput;
    @FXML
    private Label feedbackLabel, finalMessage;
    @FXML
    private Button backToSicBoButton;

    private String secretWord = "APPLE"; // Example secret word
    private int attempts = 6;

    @FXML
    private void handleGuess() {
        String guess = guessInput.getText().toUpperCase();
        if (guess.length() != secretWord.length()) {
            feedbackLabel.setText("Your guess must be " + secretWord.length() + " letters.");
            return;
        }

        if (--attempts <= 0) {
            finalMessage.setText("Game Over! The word was " + secretWord);
            guessInput.setDisable(true);
            backToSicBoButton.setVisible(true);
            return;
        }

        feedbackLabel.setText(generateFeedback(guess));
        guessInput.clear();
    }

    private String generateFeedback(String guess) {
        StringBuilder feedback = new StringBuilder();
        for (int i = 0; i < guess.length(); i++) {
            if (guess.charAt(i) == secretWord.charAt(i)) {
                feedback.append(guess.charAt(i)).append(" ");
            } else if (secretWord.contains(guess.charAt(i) + "")) {
                feedback.append("+ ");
            } else {
                feedback.append("- ");
            }
        }
        return feedback.toString().trim();
    }

    @FXML
    private void handleBackToSicBo() {
        // Implement the logic to switch back to the Sic Bo game
    }
}
