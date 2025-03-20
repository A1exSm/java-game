package game.utils.sound;
// Imports

import game.Game;
import game.body.walkers.PlayerWalker;
import game.body.walkers.WalkerFrame;
import game.core.GameSound;
import game.enums.State;
import game.enums.Walkers;

import java.util.HashMap;

// Class
public class SoundFX implements SoundInterface  {
    // Fields
    private final HashMap<State, GameSound> soundMap = new HashMap<>();
    private final javax.swing.Timer runTimer;
    // Constructor
    public SoundFX(Walkers walker) {
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
    private void hashMapSetup(Walkers walker) {
        for (State state : State.values()) {
            soundMap.put(state, null);
        }
        soundMap.putIfAbsent(State.HIT, GameSound.createSound("data/Audio/Global/hurt1.wav", 480));
        switch (walker) {
            case PLAYER -> {
                soundMap.putIfAbsent(State.ATTACK1, GameSound.createSound("data/Audio/Player/sword_attack.wav", 665));
                soundMap.putIfAbsent(State.DEATH, GameSound.createSound("data/Audio/Player/death.wav", 2000));
                soundMap.putIfAbsent(State.JUMP, GameSound.createSound("data/Audio/Player/jump.wav", 500));
                soundMap.putIfAbsent(State.RUN, GameSound.createSound("data/Audio/Player/run.wav", 200));
            }
            case HUNTRESS -> {
                soundMap.putIfAbsent(State.ATTACK1, GameSound.createSound("data/Audio/Attacks/swing.wav", 614));
            }
            case WIZARD -> {
                soundMap.putIfAbsent(State.ATTACK1, GameSound.createSound("data/Audio/Attacks/swing.wav", 614));

            }
            case WORM -> {
                soundMap.putIfAbsent(State.ATTACK1, GameSound.createSound("data/Audio/Attacks/shoot.wav", 820));

            }
            default -> { // we should never reach this point :)
                throw new IllegalArgumentException("Unknown Walker: " + walker);
            }
        }

    }

    private void attemptToPlay(State state, boolean loop) {
        if (soundMap.get(state) != null) {
            if (loop) {
                soundMap.get(state).loop();
            } else {
                soundMap.get(state).play();
            }
        }
    }

    private void attemptToPlayOnTimer(State state, int delayMs) {
        if (soundMap.get(state) != null) {
            GameSound.playOnDelay(soundMap.get(state), delayMs);
        }
    }

    public void setVolumeAll(double volume) {
        for (State state : State.values()) {
            if (soundMap.get(state) != null) {
                soundMap.get(state).setVolume(volume);
            }
        }
    }

    // Methods | Public | Specific calls
    public void attack1(WalkerFrame walker) {
        if (walker instanceof PlayerWalker) {
            attemptToPlayOnTimer(State.ATTACK1, 400);
        }
    }

    public void stopFX(State state) {
        if (state == State.RUN) {
            runTimer.stop();
        }
        if (soundMap.get(state) != null) {
            soundMap.get(state).stop();
        }
    }

    public GameSound getTrackerSound() {
        return soundMap.get(State.ATTACK1);
    }
    // Methods | Public | @Override
    @Override
    public void attack1() {
        attemptToPlay(State.ATTACK1, false);
    }

    @Override
    public void attack2() {
        attemptToPlay(State.ATTACK2, false);
    }

    @Override
    public void attack3() {
        attemptToPlay(State.ATTACK3, false);
    }

    @Override
    public void death() {
        attemptToPlay(State.DEATH, false);
    }

    @Override
    public void fall() {
        attemptToPlay(State.FALL, false);
    }

    @Override
    public void hit() {
        attemptToPlay(State.HIT, false);
    }

    @Override
    public void jump() {
        attemptToPlay(State.JUMP, false);
    }

    @Override
    public void run() {
        if (!runTimer.isRunning()) {runTimer.start();}

    }

    @Override
    public void shoot() {
        attemptToPlay(State.SHOOT, false);
    }
}
