package com.sample.engrisk;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class SoundPlayer {
    private Map<String, MediaPlayer> mediaPlayers = new HashMap<>();

    public void playSound(String soundFile) {
        MediaPlayer mediaPlayer = mediaPlayers.get(soundFile);
        if (mediaPlayer == null) {
            try {
                URL resource = getClass().getResource("/sounds/" + soundFile);
                if (resource == null) {
                    throw new RuntimeException("Sound file not found: " + soundFile);
                }
                Media media = new Media(resource.toString());
                mediaPlayer = new MediaPlayer(media);
                mediaPlayers.put(soundFile, mediaPlayer);
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("Error playing sound: " + e.getMessage());
                return;
            }
        }
        mediaPlayer.stop(); // stop current playing if it is playing
        mediaPlayer.play();
    }

    public void pauseSound(String soundFile) {
        MediaPlayer mediaPlayer = mediaPlayers.get(soundFile);
        if (mediaPlayer != null) {
            mediaPlayer.pause();
        }
    }

    public void resumeSound(String soundFile) {
        MediaPlayer mediaPlayer = mediaPlayers.get(soundFile);
        if (mediaPlayer != null) {
            mediaPlayer.play();
        }
    }
}