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
    // Constructor
    public GameSound(String path, boolean loops) throws UnsupportedAudioFileException, LineUnavailableException, IOException {
        super(path);
        if (loops) {loop();}
        else {play();}
    }
    // Methods
}
