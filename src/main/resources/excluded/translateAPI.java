package com.sample.engrisk;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Locale;

// Note from Lone Wolf: onAction="#handleTranslateButtonAction" add this to the button once you have fixed this mess
public class translateAPI {

    public static String GOOGLE_TRANSLATE_URL = "https://translate.googleapis.com/translate_a/single";
    public static String GOOGLE_AUDIO_URL = "http://translate.google.com/translate_tts";
    public static String GOOGLE_SEARCH_URL = "https://clients1.google.com/complete/search";

    public static TranslateToHTML translateToHTML = new TranslateToHTML();
    public static SearchToHTML searchToHTML = new SearchToHTML();

    public static String translate(String text) throws IOException {
        return translate("vi", text);
    }

    public static String translate(String targetLanguage, String text) throws IOException {
        return translate("auto", targetLanguage, text);
    }

    public static String translate(String sourceLanguage, String targetLanguage, String text) throws IOException {
        String url = generateTranslateURL(sourceLanguage, targetLanguage, text);
        String result = SendRequest.sendGET(url);
        try {
            return translateToHTML.parserHTML(result);
        } catch (ParseException e) {
            System.out.println("[ERROR]: Error TranslateToHTML.");
        }

        return null;
    }

    public static ArrayList<String> search(String text) throws IOException {
        return search("en", text);
    }

    public static ArrayList<String> search(String sourceLanguage, String text) throws IOException {
        String url = generateSearchURL(sourceLanguage, text);
        String result = SendRequest.sendGET(url);

        result = result.replace("window.google.ac.h(", "");
        result = result.substring(0, result.length() - 1);

        try {
            return searchToHTML.parserHTML(result);
        } catch (ParseException e) {
            System.out.println("[ERROR]: Error SearchToHTML.");
        }

        return new ArrayList<>();
    }

    public static void speak(String text) throws IOException {
        speak("en", text);
    }

    public static void speak(String language, String text) throws IOException {
        String url = generateSpeakURL(language, text);

        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("GET");
        con.setRequestProperty("User-Agent", "Mozilla/5.0");
        int responseCode = con.getResponseCode();

        if (responseCode == HttpURLConnection.HTTP_OK) {
            InputStream audioSrc = con.getInputStream();
            try {
                new Player(new BufferedInputStream(audioSrc)).play();
            } catch (JavaLayerException e) {
                e.printStackTrace();
            }
        }
    }

    private static String generateTranslateURL(String sourceLanguage, String targetLanguage, String text) throws UnsupportedEncodingException {
        String url = GOOGLE_TRANSLATE_URL + "?client=t" +
                "&sl=" + sourceLanguage +
                "&tl=" + targetLanguage +
                "&hl=" + targetLanguage + // The language of the UI
                "&dt=at&dt=bd&dt=ex&dt=ld&dt=md&dt=qca&dt=rw&dt=rm&dt=ss&dt=t&ie=UTF-8&oe=UTF-8&otf=1&ssel=0&tsel=0&kc=1" +
                "&tk=" + generateToken(text) +
                "&q=" + URLEncoder.encode(text, "UTF-8");
        return url;
    }

    private static String generateSearchURL(String sourceLanguage, String text) throws UnsupportedEncodingException {
        String url = GOOGLE_SEARCH_URL + "?" +
                "q=" + URLEncoder.encode(text, "UTF-8") +
                "&client=translate-web&ds=translate" +
                "&hl=" + sourceLanguage;
        return url;
    }

    private static String generateSpeakURL(String language, String text) throws UnsupportedEncodingException {
        String url = GOOGLE_AUDIO_URL + "?ie=UTF-8" +
                "&q=" + URLEncoder.encode(text, "UTF-8") +
                "&tl=" + language +
                "&tk=" + generateToken(text) +
                "&client=t";
        return url;
    }

    private static int[] TKK() {
        return new int[]{0x6337E, 0x217A58DC + 0x5AF91132};
    }

    private static int shr32(int x, int bits) {
        if (x < 0) {
            long x_l = 0xffffffffl + x + 1;
            return (int) (x_l >> bits);
        }
        return x >> bits;
    }

    private static String generateToken(String text) {
        int[] tkk = TKK();
        int b = tkk[0];
        long a = b;

        for (int c = 0; c < text.length(); c++) {
            a += text.charAt(c);
            a = RL(a, "+-a^+6");
        }
        a = RL(a, "+-3^+b+-f");
        a ^= tkk[1];

        if (a < 0) {
            a = (a & 0x7FFFFFFF) + 0x80000000L;
        }

        a %= 1e6;
        return String.format(Locale.US, "%.0f.%.0f", a, a ^ b);
    }

    private static int RL(long a, String b) {
        for (int c = 0; c < b.length() - 2; c += 3) {
            int d = b.charAt(c + 2);
            d = d >= 65 ?
                    d - 87 : d - 48;
            a = b.charAt(c + 1) == '+' ? a >>> d : a << d;
            a &= 4294967295L;
            a = b.charAt(c) == '+' ? a + (int) (b.charAt(c)) : a ^ (int) (b.charAt(c));
        }
        return (int) a;
    }
}
