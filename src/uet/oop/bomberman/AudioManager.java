package uet.oop.bomberman;

import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

public class AudioManager {

    public static MediaPlayer setAndPlayMusicLoop(MediaPlayer music, String path) {
        try {
            if (music != null && music.getStatus() == MediaPlayer.Status.PLAYING) {
                music.stop();
                music.dispose();
            }
            music = new MediaPlayer(new Media(path));
            music.setCycleCount(MediaPlayer.INDEFINITE);
            music.play();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return music;
    }
    public static MediaPlayer setAndPlayMusic(MediaPlayer music, String path) {
        if (music != null && music.getStatus() == MediaPlayer.Status.PLAYING) {
            music.stop();
            music.dispose();
        }
        try {
            music = new MediaPlayer(new Media(path));
            music.play();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return music;
    }

    public static AudioClip setAndPlaySound(AudioClip sound, String path) {
        try {
            sound = new AudioClip(path);
            sound.play();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sound;
    }
}
