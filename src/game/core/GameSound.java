package game.core;
// Imports

import city.cs.engine.SoundClip;
import game.Game;
import javax.swing.Timer;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;
import java.util.ArrayList;

// Class
public class GameSound extends SoundClip {
    // Fields
    private static ArrayList<GameSound> sounds = new ArrayList<>();
    private static double globalVolume = 0.10;
    private double localVolume;
    public boolean isPaused = false;
    private boolean isPlaying = false;
    private String path;
    private final boolean loops;
    private final Timer elapsedTimer;
    // Constructor
    private GameSound(String path, boolean loops) throws UnsupportedAudioFileException, LineUnavailableException, IOException {
        super(path);
        this.loops = loops;
        if (loops) {
            loop();
            isPlaying = true;
        }
        elapsedTimer = null;
        setup(path);
    }
    private GameSound(String path, int duration) throws UnsupportedAudioFileException, LineUnavailableException, IOException {
        super(path);
        this.loops = false;
        elapsedTimer = new Timer(duration, e -> {
            stop();
            isPlaying = false;
        });
        elapsedTimer.setRepeats(false);
        setup(path);
    }
    // Methods | Private | Setup
    private void setup(String path) {
        this.path = path;
        sounds.add(this);
        setVolume(GameSound.getGlobalVolume());
    }
    // Methods | Static | Private
    private static GameSound errorHandle(String path, boolean loops, int duration, boolean switcher) {
        GameSound sound;
        try {
            if (switcher) {
                sound = new GameSound(path, loops);
            } else {
                sound = new GameSound(path, duration);
            }
        } catch (UnsupportedAudioFileException e) {
            System.err.println("Audio file format not supported: " + e.getMessage());
            return null;
        } catch (IOException e) {
            System.err.println("Error reading the file: " + e.getMessage());
            return null;
        } catch (LineUnavailableException e) {
            System.err.println("Audio line unavailable: " + e.getMessage());
            return null;
        }
        return sound;
    }

    // Methods | Static | Public
    public static GameSound createSound(String path, boolean loops) {
        return errorHandle(path, loops, -1, true);
    }

    public static GameSound createSound(String path, int duration) {
        return errorHandle(path, false, duration, false);
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
            System.err.println("Warning: GameSound.play() called on sound that is already playing @ " + path);
            return;
        }
        super.play();
        elapsedTimer.start();
        isPlaying = true;
    }
    @Override
    public void stop() {
        if (isPlaying) {
            super.stop();
            if (elapsedTimer.isRunning()) {
                elapsedTimer.stop();
            }
            isPlaying = false;
        }
    }
    @Override
    public void pause() {
        if(!isPaused) {
            super.pause();
            isPaused = true;
        }
    }

    @Override
    public void resume() {
        if (isPaused) {
            super.resume();
            isPaused = false;
        }
    }

    @Override
    public void setVolume(double volume) {
        localVolume = volume;
        try {
            super.setVolume(volume);
        } catch (IllegalArgumentException e) {
                super.setVolume(0.0001);
        }
    }
    // Methods | Static | Public | Setters
    public static void setGlobal(double volume) {
        globalVolume = volume;
        for (GameSound sound : sounds) {
            sound.setVolume(volume);
        }
    }
    // Methods | Public | Getters
    public boolean isPlaying() {
        return isPlaying;
    }
    public static double getGlobalVolume() {
        return globalVolume;
    }

    public double getVolume() {
        return localVolume;
    }

}
