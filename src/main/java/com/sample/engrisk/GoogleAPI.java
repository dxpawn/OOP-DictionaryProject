package com.sample.engrisk;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.util.Base64;

public class GoogleAPI {
    public static final String GOOGLE_TRANSLATE_URL = "http://translate.google.com/translate_a/t?";
    private static final SecureRandom secureRandom = new SecureRandom(); // threadsafe
    private static final Base64.Encoder base64Encoder = Base64.getUrlEncoder(); // threadsafe
    private final static String userAgent = "Mozilla/5.0 (iPhone; U; CPU iPhone OS 3_0 like Mac OS X; en-us) AppleWebKit/528.18 (KHTML, like Gecko) Version/4.0 Mobile/7A341 Safari/528.16";

    public static String translate(String query, String srcLang, String destLang) throws IOException {
        // INSERT YOU URL HERE
        String urlStr = generateTranslateURL(srcLang, destLang, query);
        URL url = new URL(urlStr);
        StringBuilder response = new StringBuilder();
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");
        con.setRequestProperty("User-Agent", userAgent);
        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();
        return response.toString().trim().replace("\"", "");
    }

    private static String generateTranslateURL(String sourceLanguage, String targetLanguage, String text)
            throws UnsupportedEncodingException {
        return GOOGLE_TRANSLATE_URL + "client=gtrans" +
                "&sl=" + sourceLanguage +
                "&tl=" + targetLanguage +
                "&hl=" + targetLanguage + // The language of the UI
                "&tk=" + generateNewToken() +
                "&q=" + URLEncoder.encode(text, StandardCharsets.UTF_8);
    }

    private static String generateNewToken() {
        byte[] randomBytes = new byte[24];
        secureRandom.nextBytes(randomBytes);
        return base64Encoder.encodeToString(randomBytes);
    }
}
