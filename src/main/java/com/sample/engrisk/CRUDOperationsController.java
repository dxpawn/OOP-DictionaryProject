package com.sample.engrisk;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;

import java.util.*;

public class CRUDOperationsController {
    @FXML
    private TextField wordField;
    @FXML
    private TextField definitionField;

    private DictionaryService dictionaryService = DictionaryService.getInstance(); // Singleton
    private List<WordListObserver> observers = new ArrayList<>();
    
    // They said CRUD is simply put and remove...
    public void initializeCRUD() {
        DictionaryService dictionaryService = DictionaryService.getInstance();
        try {
            dictionaryService.loadData(); // Load the initial data
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addObserver(WordListObserver observer) {
        observers.add(observer);
    }

    private void notifyObservers() {
        for (WordListObserver observer : observers) {
            observer.updateWordList();
        }
    }

    @FXML
    private void handleAdd() {
        String word = wordField.getText().trim().toLowerCase();
        String definition = definitionField.getText().trim();
        if (!word.isEmpty() && !definition.isEmpty()) {
            dictionaryService.getData().put(word, new Word(word, definition));
            showConfirmation("Added", "Word added successfully.");
            clearFields();
            notifyObservers();
        }
        System.out.println("Current data in dictionary: " + DictionaryService.getInstance().getData());
    }

    @FXML
    private void handleUpdate() {
        String word = wordField.getText().trim().toLowerCase();
        String definition = definitionField.getText().trim();
        if (dictionaryService.getData().containsKey(word)) {
            dictionaryService.getData().put(word, new Word(word, definition));
            showConfirmation("Updated", "Word updated successfully.");
            clearFields();
            notifyObservers();
        } else {
            showError("Update Error", "Word does not exist.");
        }
    }

    @FXML
    private void handleDelete() {
        String word = wordField.getText().trim().toLowerCase();
        System.out.println("Attempting to delete word: " + word);
        System.out.println("Word exists before deletion: " + dictionaryService.getData().containsKey(word));
        if (dictionaryService.getData().containsKey(word)) {
            dictionaryService.getData().remove(word);
            System.out.println("Word exists after deletion: " + dictionaryService.getData().containsKey(word));
            showConfirmation("Deleted", "Word deleted successfully.");
            clearFields();
            notifyObservers();
        } else {
            showError("Delete Error", "Word does not exist.");
        }
    }

    private void clearFields() {
        wordField.setText("");
        definitionField.setText("");
    }

    private void showConfirmation(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    private void showError(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
