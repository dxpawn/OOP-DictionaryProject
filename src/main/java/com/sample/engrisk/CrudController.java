package com.sample.engrisk;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.ListView;

public class CrudController {

    @FXML private TextField wordField;
    @FXML private TextField definitionField;
    @FXML private ListView<String> wordList;

    private DictionaryService dictionaryService;

    public CrudController(DictionaryService dictionaryService) {
        this.dictionaryService = dictionaryService;
    }

    @FXML
    private void initialize() {
        refreshWordList();
    }

    @FXML
    private void handleAdd() {
        String word = wordField.getText();
        String definition = definitionField.getText();
        dictionaryService.addWord(word, definition);
        refreshWordList();
    }

    @FXML
    private void handleUpdate() {
        String word = wordField.getText();
        String definition = definitionField.getText();
        dictionaryService.updateWord(word, definition);
        refreshWordList();
    }

    @FXML
    private void handleDelete() {
        String word = wordField.getText();
        dictionaryService.deleteWord(word);
        refreshWordList();
    }

    private void refreshWordList() {
        wordList.getItems().clear();
        wordList.getItems().addAll(dictionaryService.getAllWords().keySet());
    }

    public void setDictionaryService(DictionaryService dictionaryService) {
        this.dictionaryService = dictionaryService;
        refreshWordList();  // refresh list upon setting the service
    }
}