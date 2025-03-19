package game.core;
// Imports

import city.cs.engine.SoundClip;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;

// Class
public class GameSound extends SoundClip {
    // Fields
    public boolean isPaused = false;
    private boolean isPlaying = false;
    private final String path;
    // Constructor
    public GameSound(String path, boolean loops) throws UnsupportedAudioFileException, LineUnavailableException, IOException {
        super(path);
        this.path = path;
        if (loops) {
            loop();
            isPlaying = true;
        }
    }
    // Methods | Static | Public
    public static GameSound createSound(String path, boolean loops) {
        GameSound sound;
        try {
            sound = new GameSound(path, loops);
        } catch (UnsupportedAudioFileException e) {
            System.out.println("Audio file format not supported: " + e.getMessage());
            return null;
        } catch (IOException e) {
            System.out.println("Error reading the file: " + e.getMessage());
            return null;
        } catch (LineUnavailableException e) {
            System.out.println("Audio line unavailable: " + e.getMessage());
            return null;
        }
        return sound;
    }

    public static void playOnDelay(GameSound sound, int delayMs) {
        javax.swing.Timer timer = new javax.swing.Timer(delayMs, e ->{
            sound.play();
        });
        timer.setRepeats(false);
        timer.start();
    }
    // Methods | Public | @Override
    @Override
    public void play() {
        if (isPlaying) {
            GameSound.createSound(path, false).play(); // allows multiple at once
        }
        super.play();
        isPlaying = true;
    }
}
