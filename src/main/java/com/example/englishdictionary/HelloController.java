package com.example.englishdictionary;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class HelloController {
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }

    @FXML
    protected void ondieNiggaDieButtonClick() {
        welcomeText.setText("SAYONARA NIGGAAAAA!!!");
    }



}