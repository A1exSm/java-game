package game.core;
// Imports

import city.cs.engine.SoundClip;
import game.core.console.Console;
import game.enums.SoundGroups;
import javax.swing.Timer;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;
// Class

/**
 * A class that extending SoundClip and is used to handle the game sounds. <br>
 * Has additional features such as volume control, global volume control, and delayed playing.
 */
public class GameSound extends SoundClip {
    // Fields
    /**
     * A static double field used to hold the global volume of the game sounds.<br>
     * Allows for the global volume to be set in {@link #setGlobal(double)}.
     * @see #setGlobal(double)
     */
    private static double globalVolume = 0.10;

    /**
     * A double field used to hold the local volume of the sound.<br>
     * Allows for individual volume to be set using {@link #setVolume(double)}.
     * @see #setVolume(double)
     */
    private double localVolume;


    /**
     * A boolean field used to hold the state of sound playing.<br>
     * Allows for the sound to be toggled between playing and stopped using {@link #play()} and {@link #stop()}.
     * @see #play()
     * @see #stop()
     */
    private boolean isPlaying = false;

    /**
     * A String field used to holding the local path to the sound file from the root directory.<br>
     */
    private String path;

    /**
     * A Timer field used to hold the elapsed time of the sound playing.<br>
     * Allows for the sound to be stopped after a certain duration using {@link #stop()}.
     * {@link Timer#start()} is called in {@link #play()}.
     * {@link Timer#stop()} is called in {@link #stop()}.
     * @see Timer
     */
    private final Timer elapsedTimer;

    /**
     * A SoundGroups field used to hold the sound group of the sound.<br>
     * @see SoundGroups
     */
    private SoundGroups group;

    // Constructor
    /**
     * Constructor for looping sounds.
     * @param path Path to the sound file.
     * @param loops Whether the sound should loop.
     * @param group The sound group to add the sound to.
     * @throws UnsupportedAudioFileException If the audio file format is not supported.
     * @throws LineUnavailableException If the audio line is unavailable.
     * @throws IOException If an I/O error occurs.
     */
    private GameSound(String path, SoundGroups group, boolean loops) throws UnsupportedAudioFileException, LineUnavailableException, IOException {
        super(path);
        if (loops) {
            loop();
            isPlaying = true;
        }
        elapsedTimer = null;
        setup(path, group);
    }

    /**
     * Constructor for sounds with a specific duration.
     * @param path Path to the sound file.
     * @param duration Duration of the sound in milliseconds.
     * @param group The sound group to add the sound to.
     * @throws UnsupportedAudioFileException If the audio file format is not supported.
     * @throws LineUnavailableException If the audio line is unavailable.
     * @throws IOException If an I/O error occurs.
     */
    private GameSound(String path, SoundGroups group, int duration) throws UnsupportedAudioFileException, LineUnavailableException, IOException {
        super(path);
        elapsedTimer = new Timer(duration, e -> {
            stop();
            isPlaying = false;
        });
        elapsedTimer.setRepeats(false);
        setup(path, group);
    }
    // Methods | Private | Setup
    /**
     * Sets up the sound instance.
     * @param path Path to the sound file.
     * @param group used to call {@link SoundGroups#addSound(GameSound)}.
     */
    private void setup(String path, SoundGroups group) {
        this.path = path;
        this.group = group;
        group.addSound(this);
        setVolume(group.getVolume());
    }

    // Methods | Static | Private
    /**
     * Handles errors during sound creation.
     * @param path Path to the sound file.
     * @param group The sound group to add the sound to.
     * @param loops Whether the sound should loop.
     * @param duration Duration of the sound in milliseconds.
     * @param switcher Switch to determine which constructor to use.
     * @return A new GameSound instance or null if an error occurs.
     */
    private static GameSound errorHandle(String path, SoundGroups group, boolean loops, int duration, boolean switcher) {
        GameSound sound;
        try {
            if (switcher) {
                sound = new GameSound(path, group, loops);
            } else {
                sound = new GameSound(path, group, duration);
            }
        } catch (UnsupportedAudioFileException e) {
            Console.error("Audio file format not supported: " + e.getMessage());
            return null;
        } catch (IOException e) {
            Console.error("Error reading the file: " + e.getMessage());
            return null;
        } catch (LineUnavailableException e) {
            Console.error("Audio line unavailable: " + e.getMessage());
            return null;
        }
        return sound;
    }

    // Methods | Static | Public
    /**
     * Creates a new looping sound.
     * @param path Path to the sound file.
     * @param group The sound group to add the sound to.
     * @return A new GameSound instance.
     */
    public static GameSound createSound(String path, SoundGroups group, boolean loops) {
        return errorHandle(path, group, loops, -1, true);
    }
    /**
     * Creates a new sound with a specific duration.
     * @param path Path to the sound file.
     * @param group The sound group to add the sound to.
     * @param duration Duration of the sound in milliseconds.
     * @return A new GameSound instance.
     */
    public static GameSound createSound(String path, SoundGroups group, int duration) {
        return errorHandle(path, group, false, duration, false);
    }

    /**
     * Plays a sound after a specified delay.
     * @param sound The GameSound instance to play.
     * @param delayMs Delay in milliseconds before playing the sound.
     */
    public static void playOnDelay(GameSound sound, int delayMs) {
        javax.swing.Timer timer = new javax.swing.Timer(delayMs, e ->{
            sound.play();
        });
        timer.setRepeats(false);
        timer.start();
    }

    /**
     * If sounds is already playing, creates a duplicate instance and plays it.
     */
    public void forcedPlay() {
        if (isPlaying) {
            createSound(path, group, elapsedTimer.getDelay()).play();
        } else {
            play();
        }
    }

    // Methods | Public | @Override
    /**
     * Plays the sound if it is not already playing.
     */
    @Override
    public void play() {
        if (isPlaying) {
            Console.warning("GameSound.play() called on sound that is already playing @ " + path);
            return;
        }
        super.play();
        elapsedTimer.start();
        isPlaying = true;
    }

    /**
     * Stops the sound if it is playing.
     */
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

    /**
     * Sets the volume of the sound.<br>
     * If the volume is set to 0, which triggers an IllegalArgumentException, the volume is set to 0.0001.<br>
     * There is nothing to catch an IllegalArgumentException of a volume set too high, as the volume is restricted by the volume slider in {@link game.menu.JMenuPanel}.
     * @param volume (double) The volume level to set.
     */
    @Override
    public void setVolume(double volume) { // When used on mac experiences an issue where occasionally Master Gain Control not available.
        localVolume = volume;
        try {
            super.setVolume(volume);
        } catch (IllegalArgumentException e) {
                super.setVolume(0.0001);
        }
    }
    // Methods | Static | Public | Setters
    /**
     * Sets the global volume for all sounds.
     * @param volume The global volume level to set.
     */
    public static void setGlobal(double volume) {
        globalVolume = volume;
        for (SoundGroups group : SoundGroups.values()) {
            for (GameSound sound : group.getSounds()) {
                sound.setVolume(volume);
            }
        }
    }
    // Methods | Public | Getters
    /**
     * Returns true if the sound is currently playing.
     * @return true if the sound is playing, false otherwise.
     */
    public boolean isPlaying() {
        return isPlaying;
    }

    /**
     * Gets the global volume level.
     * @return (double) The global volume level.
     */
    public static double getGlobalVolume() {
        return globalVolume;
    }

    /**
     * Gets the local volume level of this sound instance.
     * @return (double) The local volume level.
     */
    public double getVolume() {
        return localVolume;
    }

}
