package com.sample.engrisk;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("unhello-view.fxml"));
        /* Something does not line up with the online tutorials.
        It is likely that the FXML file loaded above has overridden the scene color
        so adding Color.BLACK to the constructor below doesn't do anything.
         */
        Scene scene = new Scene(fxmlLoader.load(), 800, 600); // W and H
        stage.setResizable(false); // cuz AnchorPane

        /* Redundant lines, for learning purposes:
        stage.setX(int);
        stage.setY(int); // javaFX automatically centers your program, so this is not needed
        stage.setFullScreen(boolean);
        stage.setFullScreenExitHint(String);
        stage.setFullScreenExitKeyCombination(arg);
        */

        Image icon = new Image("icon.png"); // if this doesnt work, rebuild project and invalidate caches
        stage.getIcons().add(icon);
        stage.setTitle("Dictionary! A poorly made contraption, born from the unpreparedness of 2 idiots.");
        stage.setScene(scene);
        stage.show(); // keep this at the end of this method
    }

    public static void main(String[] args) {
        launch();
    }
}