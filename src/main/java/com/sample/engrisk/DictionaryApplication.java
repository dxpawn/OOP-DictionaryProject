package com.sample.engrisk;

import com.almasb.fxgl.audio.Sound;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebView;
import javafx.stage.*;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import com.sample.engrisk.GameController; // import GameController class from the same package

import java.io.*;
import java.net.URL;
import java.util.*;


public class DictionaryApplication extends Application {
    private static final String SPLITTING_CHARACTERS = "<html>";
    private Map<String, Word> data = new TreeMap<>(); // TreeMap sorts alphabetically

    @FXML
    private ListView<String> wordList;
    @FXML
    private WebView definitionView;

    public static void main(String[] args) {
        launch();
    }

    public void switchToVE(ActionEvent event) {

    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(DictionaryApplication.class.getResource("dictionary-mainView.fxml"));
        AnchorPane root = fxmlLoader.load(); // Load once

        // text to the root
        Text text = new Text(20, 50, "Thanh pho nao vua di da moi?");
        root.getChildren().add(text);

        Scene scene = new Scene(root, 800, 600); // reuse root
        primaryStage.setResizable(false); // cuz AnchorPane
        // WARNING: DO NOT MODIFY THIS IMAGE PATH - THIS HAS CAUSED A LOT OF TROUBLE, i.e. InvocationTargetException
        URL imageUrl = getClass().getResource("/images/icon.jpg");
        if (imageUrl == null) {
            System.out.println("Resource not found");
        } else {
            Image icon = new Image(imageUrl.toString());
            primaryStage.getIcons().add(icon);
        }
        //
        primaryStage.setTitle("Dictionary! A poorly made contraption, born from the unpreparedness of 2 idiots.");
        primaryStage.setScene(scene);
        primaryStage.show();
        // init components
        initComponents(scene);

        // read word list from E_V.txt
        readData();

        // load word list to the wordList
        loadWordList();
    }
    public void initComponents(Scene scene) {
        this.definitionView = (WebView) scene.lookup("#definitionView");
        this.wordList = (ListView<String>) scene.lookup("#wordList");
        DictionaryApplication context = this;

        this.wordList.getSelectionModel().selectedItemProperty().addListener(
                (observableValue, oldValue, newValue) -> { // hopefully no more null pointer exception
                    if (newValue != null) {
                        Word selectedWord = data.get(newValue.trim());
                        if (selectedWord != null) { // if the selectedWord is not null
                            String definition = selectedWord.getDef();
                            // load the definition into WebView
                            context.definitionView.getEngine().loadContent(definition, "text/html");
                        } else { // handle cases where the word is not found in the dictionary
                            context.definitionView.getEngine().loadContent("Definition not found.", "text/html");
                        }
                    } else { // display a default message when no word is selected
                        context.definitionView.getEngine().loadContent("<p>No word is currently selected.</p>", "text/html");
                    }
                }
        );
    }

    public void loadWordList() {
        this.wordList.getItems().addAll(data.keySet());
    }

    public void readData() throws IOException {
        InputStream inputStream = getClass().getResourceAsStream("/data/E_V.txt");
        BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
        String line;
        while ((line = br.readLine()) != null) {
            String[] parts = line.split(SPLITTING_CHARACTERS);
            String word = parts[0];
            String definition = SPLITTING_CHARACTERS + parts[1];
            Word wordObj = new Word(word, definition);
            data.put(word, wordObj);
        }
    }
}

class Word {
    private String word;
    private String def;

    public Word(String word, String def) {
        this.word = word;
        this.def = def;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getDef() {
        return def;
    }

    public void setDef(String def) {
        this.def = def;
    }

}
