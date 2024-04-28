package com.sample.engrisk;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Map;
import java.util.TreeMap;

public class DictionaryService {
    private Map<String, Word> data = new TreeMap<>();

    public void loadData() throws Exception {
        InputStream inputStream = getClass().getResourceAsStream("/data/E_V.txt");
        BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
        String line;
        while ((line = br.readLine()) != null) {
            String[] parts = line.split("<html>");
            String word = parts[0].trim();
            String definition = "<html>" + parts[1].trim();
            Word wordObj = new Word(word, definition);
            data.put(word, wordObj);
        }
    }

    public Map<String, Word> getData() {
        return data;
    }
}