package game.utils.sound;
// Imports

import game.body.walkers.PlayerWalker;
import game.body.walkers.WalkerFrame;
import game.body.walkers.mobs.HuntressWalker;
import game.body.walkers.mobs.WizardWalker;
import game.body.walkers.mobs.WormWalker;
import game.core.GameSound;
import game.enums.State;
import game.enums.Walkers;

import java.util.HashMap;

// Class
public class SoundFX implements SoundInterface  {
    // Fields
    private final HashMap<State, GameSound> soundMap = new HashMap<>();
    // Constructor
    public SoundFX(Walkers walker) {
        hashMapSetup(walker);
    }
    // Methods | Private | HashMap
    private void hashMapSetup(Walkers walker) {
        State[] supportedStates;
        for (State state : State.values()) {
            soundMap.put(state, null);
        }
        soundMap.putIfAbsent(State.HIT, GameSound.createSound("data/Audio/Global/hurt1.wav", false));
        switch (walker) {
            case PLAYER -> {
                soundMap.putIfAbsent(State.ATTACK1, GameSound.createSound("data/Audio/Player/sword_attack.wav", false));
                soundMap.putIfAbsent(State.DEATH, GameSound.createSound("data/Audio/Player/death.wav", false));
                soundMap.putIfAbsent(State.JUMP, GameSound.createSound("data/Audio/Player/jump.wav", false));
            }
            case HUNTRESS -> {
                supportedStates = HuntressWalker.SUPPORTED_STATES;
            }
            case WIZARD -> {
                supportedStates = WizardWalker.SUPPORTED_STATES;
            }
            case WORM -> {
                supportedStates = WormWalker.SUPPORTED_STATES;
            }
            default -> { // we should never reach this point :)
                throw new IllegalArgumentException("Unknown Walker: " + walker);
            }
        }

    }

    private void attemptToPlay(State state) {
        if (soundMap.get(state) != null) {
            soundMap.get(state).play();
        }
    }

    private void attemptToPlayOnTimer(State state, int delayMs) {
        if (soundMap.get(state) != null) {
            GameSound.playOnDelay(soundMap.get(state), delayMs);
        }
    }

    // Methods | Public | Specific calls
    public void attack1(WalkerFrame walker) {
        if (walker instanceof PlayerWalker) {
            attemptToPlayOnTimer(State.ATTACK1, 400);
        }
    }
    // Methods | Public | @Override
    @Override
    public void attack1() {
        attemptToPlay(State.ATTACK1);
    }

    @Override
    public void attack2() {
        attemptToPlay(State.ATTACK2);
    }

    @Override
    public void attack3() {
        attemptToPlay(State.ATTACK3);
    }

    @Override
    public void death() {
        attemptToPlay(State.DEATH);
    }

    @Override
    public void fall() {
        attemptToPlay(State.FALL);
    }

    @Override
    public void hit() {
        attemptToPlay(State.HIT);
    }

    @Override
    public void jump() {
        attemptToPlay(State.JUMP);
    }

    @Override
    public void run() {
        attemptToPlay(State.RUN);
    }

    @Override
    public void shoot() {
        attemptToPlay(State.SHOOT);
    }
}
