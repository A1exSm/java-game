package game.utils.sound;
// Imports

import game.Game;
import game.body.walkers.PlayerWalker;
import game.body.walkers.WalkerFrame;
import game.core.GameSound;
import game.core.console.Console;
import game.enums.SoundGroups;
import game.enums.State;
import game.enums.WalkerType;
import java.util.HashMap;

// Class/**
// * The SoundFX class implements the SoundInterface and provides sound effects for various game actions.
// */

public class SoundFX implements SoundInterface  {
    // Fields
    private final HashMap<State, GameSound> soundMap = new HashMap<>();
    private final javax.swing.Timer runTimer;
    // Constructor
    /**
     * Constructs a SoundFX object for the specified walker.
     * GameSounds are then assigned as per the {@link WalkerType Walker}.
     *
     * @param walker the type of walker for which the sound effects are set up
     */
    public SoundFX(WalkerType walker) {
        hashMapSetup(walker);
        runTimer = new javax.swing.Timer(200, e -> {
            if (soundMap.get(State.RUN).isPlaying()) {
                soundMap.get(State.RUN).stop();
            } else {
                if (Game.gameWorld.getPlayer().isOnSurface()) {
                    soundMap.get(State.RUN).play();
                } else {
                    soundMap.get(State.RUN).stop();
                }
            }
        });
        runTimer.setRepeats(true);
    }
    // Methods | Private | HashMap
    /**
     * Sets up the sound map for the specified walker.
     * The map is created in the format <State, GameSound> and populated with the appropriate GameSounds.
     *
     * @param walker the type of walker for which the sound map is set up
     * @throws IllegalArgumentException if an <b>unsupported</b> {@link WalkerType Walker} is given
     */
    private void hashMapSetup(WalkerType walker) {
        SoundGroups group;
        if (walker.equals(WalkerType.PLAYER)) {
            group = SoundGroups.PLAYER;
        } else {
            group = SoundGroups.MOBS;
        }
        for (State state : State.values()) {
            soundMap.put(state, null);
        }
        soundMap.putIfAbsent(State.HIT, GameSound.createSound("data/Audio/Global/hurt1.wav", group, 480));
        switch (walker) {
            case PLAYER -> {
                soundMap.putIfAbsent(State.ATTACK1, GameSound.createSound("data/Audio/Player/sword_attack.wav", group, 665));
                soundMap.putIfAbsent(State.DEATH, GameSound.createSound("data/Audio/Player/death.wav", group, 2000));
                soundMap.putIfAbsent(State.JUMP, GameSound.createSound("data/Audio/Player/jump.wav", group, 500));
                soundMap.putIfAbsent(State.RUN, GameSound.createSound("data/Audio/Player/run.wav", group, 200));
            }
            case HUNTRESS -> {
                soundMap.putIfAbsent(State.ATTACK1, GameSound.createSound("data/Audio/Attacks/swing.wav", group, 614));
            }
            case WIZARD -> {
                soundMap.putIfAbsent(State.ATTACK1, GameSound.createSound("data/Audio/Attacks/swing.wav", group, 614));

            }
            case WORM -> {
                soundMap.putIfAbsent(State.ATTACK1, GameSound.createSound("data/Audio/Attacks/shoot.wav", group, 820));

            }
            default -> { // we should never reach this point :)
                throw new IllegalArgumentException(Console.exceptionMessage("Unknown Walker: " + walker));
            }
        }

    }
    /**
     * Attempts to play the sound for the specified state.
     *
     * @param state the state for which the sound is played
     * @param loop whether the sound should loop
     */
    private void attemptToPlay(State state, boolean loop) {
        if (soundMap.get(state) != null) {
            if (loop) {
                soundMap.get(state).loop();
            } else {
                soundMap.get(state).play();
            }
        }
    }
    /**
     * Attempts to play the sound for the specified state after a delay.
     *
     * @param state the state for which the sound is played
     * @param delayMs the delay in milliseconds before the sound is played
     */
    private void attemptToPlayOnTimer(State state, int delayMs) {
        if (soundMap.get(state) != null) {
            GameSound.playOnDelay(soundMap.get(state), delayMs);
        }
    }
    /**
     * Sets the volume for all sounds.
     *
     * @param volume the volume level to set for all sounds
     */
    public void setVolumeAll(double volume) {
        for (State state : State.values()) {
            if (soundMap.get(state) != null) {
                soundMap.get(state).setVolume(volume);
            }
        }
    }

    // Methods | Public | Specific calls
    /**
     * Plays the attack1 sound for the specified walker frame.
     *
     * @param walker the walker frame for which the attack1 sound is played
     */
    public void attack1(WalkerFrame walker) {
        if (walker instanceof PlayerWalker) {
            attemptToPlayOnTimer(State.ATTACK1, 400);
        }
    }
    /**
     * Stops the sound effect for the specified state.
     *
     * @param state the state for which the sound effect is stopped
     */
    public void stopFX(State state) {
        if (state == State.RUN) {
            runTimer.stop();
        }
        if (soundMap.get(state) != null) {
            soundMap.get(state).stop();
        }
    }
    /**
     * Gets the sound for the tracker state.
     *
     * @return the GameSound object for designated tracker state
     */
    public GameSound getTrackerSound() {
        return soundMap.get(State.ATTACK1);
    }
    // Methods | Public | @Override
    /**
     * Plays the sound for the first type of attack.
     */
    @Override
    public void attack1() {
        attemptToPlay(State.ATTACK1, false);
    }
    /**
     * Plays the sound for the second type of attack.
     */
    @Override
    public void attack2() {
        attemptToPlay(State.ATTACK2, false);
    }
    /**
     * Plays the sound for the third type of attack.
     */
    @Override
    public void attack3() {
        attemptToPlay(State.ATTACK3, false);
    }
    /**
     * Plays the sound for {@link WalkerFrame Walker's} death.
     */
    @Override
    public void death() {
        attemptToPlay(State.DEATH, false);
    }
    /**
     * Plays the sound for falling.
     */
    @Override
    public void fall() {
        attemptToPlay(State.FALL, false);
    }
    /**
     * Plays the sound for getting hit.
     */
    @Override
    public void hit() {
        attemptToPlay(State.HIT, false);
    }
    /**
     * Plays the sound for jumping.
     */
    @Override
    public void jump() {
        attemptToPlay(State.JUMP, false);
    }
    /**
     * Plays the sound for running.
     */
    @Override
    public void run() {
        if (!runTimer.isRunning()) {runTimer.start();}
    }
}
