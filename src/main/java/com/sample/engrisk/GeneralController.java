package com.sample.engrisk;

// import first, ask questions later

import javafx.fxml.FXML;
import javafx.scene.control.Button;

// what's the point of "implements Initializable"?
public class GeneralController {
    public static final String EV_PATH = "/data/E_V.txt";
    public static final String VE_PATH = "/data/V_E.txt";


    @FXML
    protected Button crudButton;
    @FXML
    protected Button gameButton;
    @FXML
    protected Button speakUSButton;
    @FXML
    protected Button translationButton;
    @FXML
    protected Button speakUKButton;
    @FXML
    protected Button stfuButton;
    @FXML
    protected Button switchLanguageButton;
    @FXML
    protected Button settingsButton;



}
