package com.sample.engrisk;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.Objects;
import java.util.Random;

public class SicBoController {
    @FXML
    private ImageView diceOne, diceTwo, diceThree;
    @FXML
    private Label resultLabel, creditLabel;
    @FXML
    private Button playWordleButton;

    private Random random = new Random();
    private int credits = 100;

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
            resultLabel.setText("Triple! You lose.");
            credits -= 10;
        } else if ((sum >= 11 && sum <= 17 && betType.equals("BIG")) || (sum >= 4 && sum <= 10 && betType.equals("SMALL"))) {
            resultLabel.setText("You win! Sum: " + sum);
            credits += 10;
        } else {
            resultLabel.setText("You lose. Sum: " + sum);
            credits -= 10;
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

    private void checkCredits() {
        if (credits <= 0) {
            playWordleButton.setVisible(true);
            resultLabel.setText("No money kid. Time to pay using Wordle.");
        } else {
            playWordleButton.setVisible(false);
        }
    }
}