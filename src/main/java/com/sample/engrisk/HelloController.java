package com.sample.engrisk;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.web.WebView;


public class HelloController {
    @FXML
    private Button gameButton;
    @FXML
    private TextField searchBar;
    @FXML
    private ListView<String> resultsBar;
    @FXML
    private ScrollBar scrollResultBar;
    @FXML
    private WebView definitionView;
    @FXML
    private Button translationButton;
    @FXML
    private Button speakButton;
    @FXML
    private RadioButton boleroButton;
    @FXML
    private Button findWordButton;
    @FXML
    private Button stfuButton;
    @FXML
    private Button crudButton;
    @FXML
    private Button suicide;
    @FXML
    private Button settingsButton;
    @FXML
    private Label welcomeText;
    @FXML
    private TextField inputField;

    @FXML
    protected void onBoleroButtonClick() {
        welcomeText.setText("Nhung ngay chua nhap ngu anh hay dat em ve...");
    }

    @FXML
    protected void onSuicideButtonClick() {
        welcomeText.setText("Duong thuong dau day ai nhan gian...");
    }

}