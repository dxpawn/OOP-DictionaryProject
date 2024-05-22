package com.sample.engrisk;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.IOException;
import java.util.*;

public class SicBoController {
    @FXML
    private ImageView diceOne, diceTwo, diceThree;
    @FXML
    private Label resultLabel, creditLabel;
    @FXML
    private Button betSmallButton;
    @FXML
    private Button betBigButton;
    @FXML
    private Button playWordleButton;

    private SoundPlayer soundPlayer = new SoundPlayer();

    private Random random = new Random();
    private int credits = 2201;

    @FXML
    public void handleBetSmall() {
        rollDice("SMALL");
    }

    @FXML
    public void handleBetBig() {
        rollDice("BIG");
    }

    @FXML
    public void initializeSicBo() { // why the fk do i need to have an initialize method in everything
        resetDiceDisplays();
        checkCredits();
    }

    @FXML
    public void rollDice(String betType) {
        int dice1 = rollSingleDie();
        int dice2 = rollSingleDie();
        int dice3 = rollSingleDie();

        diceOne.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/dice" + dice1 + ".jpg"))));
        diceTwo.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/dice" + dice2 + ".jpg"))));
        diceThree.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/dice" + dice3 + ".jpg"))));

        int sum = dice1 + dice2 + dice3;
        boolean isTriple = (dice1 == dice2) && (dice2 == dice3);
        if (isTriple) {
            resultLabel.setText("Triple! Money reset to 1! Your loss, obviously.");
            credits = 1;
        } else if ((sum >= 11 && sum <= 17 && betType.equals("BIG")) || (sum >= 4 && sum <= 10 && betType.equals("SMALL"))) {
            resultLabel.setText("You win, though not for long. Count: " + sum);
            credits += 500;
        } else {
            resultLabel.setText("You lose. Don't ask why the odds are not in your favor. Count: " + sum);
            credits -= 1000;
        }

        creditLabel.setText("Distance from Bankruptcy: " + credits);
        checkCredits();

    }

    private int rollSingleDie() {
        return random.nextInt(6) + 1;  // lol how about we have a die with 0 spots on it
    }

    // bring the code inside this to the initialize method if you don't plan on a restart button
    private void resetDiceDisplays() {
        diceOne.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/defaultDice.jpg"))));
        diceTwo.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/defaultDice.jpg"))));
        diceThree.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/defaultDice.jpg"))));
    }

    private void checkCredits() { // disables betting if credits = 0
        if (credits <= 0) {
            playWordleButton.setVisible(true);
            betSmallButton.setDisable(true);
            betBigButton.setDisable(true);
            resultLabel.setText("No money, kid. Time to pay with Wordle.");
        } else {
            playWordleButton.setVisible(false);
            betSmallButton.setDisable(false);
            betBigButton.setDisable(false);
        }
    }

    @FXML
    private void launchPlayWordle() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("WordleGame.fxml"));
            VBox wordleRoot = loader.load();
            WordleController wordleController = loader.getController();
            Scene scene = new Scene(wordleRoot);
            // apparently this is some scene transition bullshit
            Stage stage = (Stage) resultLabel.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
            wordleController.initializeWordle();
        } catch (IOException e) {
            e.printStackTrace();
            resultLabel.setText("Failed to load the Wordle game.");
        }
    }
}