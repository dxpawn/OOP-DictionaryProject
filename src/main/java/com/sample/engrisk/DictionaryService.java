package com.sample.engrisk;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.TreeMap;

public class DictionaryService extends GeneralController {
    private static DictionaryService instance;
    private Map<String, Word> data = new TreeMap<>();
    public static final String EV_PATH = "/data/E_V.txt";
    public static final String VE_PATH = "/data/V_E.txt";

    private DictionaryService() {}  // Private constructor

    public static DictionaryService getInstance() {
        if (instance == null) {
            instance = new DictionaryService();
        }
        return instance;
    }

    public void loadData() throws Exception {
        data.clear();  // Clear existing data
        String path = isVietnamese ? VE_PATH : EV_PATH;
        InputStream inputStream = getClass().getResourceAsStream(path);
        if (inputStream == null) {
            throw new FileNotFoundException("Resource not found: " + path);
        }
        BufferedReader br = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
        String line;
        while ((line = br.readLine()) != null) {
            String[] parts = line.split("<html>");
            String word = parts[0].trim().toLowerCase(); // Normalize the words
            String definition = "<html>" + parts[1].trim();
            Word wordObj = new Word(word, definition);
            data.put(word, wordObj);
        }
    }

    public Map<String, Word> getData() {
        return data;
    }
}



/* This is the old code before I refactored into Singleton
package com.sample.engrisk;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.TreeMap;

public class DictionaryService extends GeneralController {
    private Map<String, Word> data = new TreeMap<>();
    public static final String EV_PATH = "/data/E_V.txt";
    public static final String VE_PATH = "/data/V_E.txt";

    public void loadData() throws Exception {
        data = new TreeMap<>(); // new treeMap everytime this shit is called
        String path = isVietnamese ? VE_PATH : EV_PATH; // change path based on language chosen
        InputStream inputStream = getClass().getResourceAsStream(path);
        if (inputStream == null) {
            throw new FileNotFoundException("Resource not found: " + path);
        }
        BufferedReader br = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
        String line;
        while ((line = br.readLine()) != null) {
            String[] parts = line.split("<html>");
            String word = parts[0].trim().toLowerCase(); // normalize the words
            System.out.println("Loading word: " + word); // DEBUG OUTPUT
            String definition = "<html>" + parts[1].trim();
            Word wordObj = new Word(word, definition);
            data.put(word, wordObj);
        }
    }

    public Map<String, Word> getData() {
        return data;
    }

}
 */