package com.sample.engrisk;

// import first, ask questions later

import javafx.fxml.FXML;
import javafx.scene.control.Button;

// what's the point of "implements Initializable"?
public class GeneralController {
    protected static boolean isVietnamese = false;

    @FXML
    protected Button crudButton;
    @FXML
    protected Button gameButton;
    @FXML
    protected Button speakButton;
    @FXML
    protected Button translationButton;
    @FXML
    protected Button switchLanguageButton;
    @FXML
    protected Button settingsButton;
}
