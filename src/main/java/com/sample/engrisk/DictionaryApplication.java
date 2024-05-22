package com.sample.engrisk;

import com.almasb.fxgl.audio.Sound;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.web.WebView;
import javafx.stage.*;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.Parent;
import javafx.scene.text.Text;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import com.sample.engrisk.GameController; // import GameController class from the same package

import java.io.*;
import java.net.URL;
import java.util.*;

// NOTE: ALL DATA LOADING AND HANDLING HAS BEEN DELEGATED TO DictionaryService AND DictionaryController

public class DictionaryApplication extends Application {

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage primaryStage) {
        try {
            URL url = DictionaryApplication.class.getResource("/com/sample/engrisk/dictionary-mainView.fxml");
            if (url == null) {
                throw new FileNotFoundException("FXML file not found");
            }
            FXMLLoader fxmlLoader = new FXMLLoader(url);
            AnchorPane root = fxmlLoader.load();
            Scene scene = new Scene(root, 800, 600);

            primaryStage.setTitle("Dictionary! A poorly made contraption, born from the unpreparedness of an idiot.");
            primaryStage.setScene(scene);
            primaryStage.setResizable(false);
            primaryStage.show();

            // WARNING: DO NOT MODIFY THIS IMAGE PATH - THIS HAS CAUSED A LOT OF TROUBLE, i.e. InvocationTargetException
            URL imageUrl = getClass().getResource("/images/icon.jpg");
            if (imageUrl == null) {
                System.out.println("Resource not found");
            } else {
                Image icon = new Image(imageUrl.toString());
                primaryStage.getIcons().add(icon);
            }

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Failed to initialize application: " + e.getMessage());
        }
    }
}


