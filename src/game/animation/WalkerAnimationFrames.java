package game.animation;
import static game.enums.Walkers.*;
import static game.enums.State.*;
import game.enums.State;
import game.enums.Walkers;
import java.util.HashMap;

public class WalkerAnimationFrames extends AnimationFrames {
    // Fields
    private static final HashMap<Walkers, HashMap<State, Integer>> WALKER_MAP = new HashMap<>();
    public final State animationType;
    public final Walkers walkerType;

    static { // We are making a static map so we don't need a large chunk of logic to execute everytime a new object is made
        // Populate WALKER_MAP
        for (Walkers w : Walkers.values()) {WALKER_MAP.put(w, new HashMap<>());}
        // Player
        WALKER_MAP.get(PLAYER).put(ATTACK1, 6);
        WALKER_MAP.get(PLAYER).put(ATTACK2, 6);
        WALKER_MAP.get(PLAYER).put(DEATH, 6);
        WALKER_MAP.get(PLAYER).put(FALL, 2);
        WALKER_MAP.get(PLAYER).put(HIT, 4);
        WALKER_MAP.get(PLAYER).put(IDLE, 8);
        WALKER_MAP.get(PLAYER).put(JUMP, 2);
        WALKER_MAP.get(PLAYER).put(RUN, 8);
        // Wizard
        WALKER_MAP.get(WIZARD).put(ATTACK1, 8);
        WALKER_MAP.get(WIZARD).put(ATTACK2, 8);
        WALKER_MAP.get(WIZARD).put(DEATH, 7);
        WALKER_MAP.get(WIZARD).put(FALL, 2);
        WALKER_MAP.get(WIZARD).put(HIT, 3);
        WALKER_MAP.get(WIZARD).put(IDLE, 8);
        WALKER_MAP.get(WIZARD).put(JUMP, 2);
        WALKER_MAP.get(WIZARD).put(RUN, 8);
        // Worm
        WALKER_MAP.get(WORM).put(ATTACK1, 16);
        WALKER_MAP.get(WORM).put(DEATH, 8);
        WALKER_MAP.get(WORM).put(HIT, 3);
        WALKER_MAP.get(WORM).put(IDLE, 9);
        WALKER_MAP.get(WORM).put(RUN, 9);

    }
    // Constructor
    public WalkerAnimationFrames(State animationName, Walkers walkerType) {
        super(); // parent constructor is empty, but is here to facilitate future implementation
        // assigning inherited fields
        animationType = animationName;
        this.walkerType = walkerType;
        height = 18f;
        initWalkerType(animationName, walkerType);
        this.loadFrames();
    }
    // Methods
    private void initWalkerType(State animationName, Walkers walkerType) {
        // Error Handling
        if (!WALKER_MAP.containsKey(walkerType) || !WALKER_MAP.get(walkerType).containsKey(animationName)) {
            System.err.println("Warning: Animation '" + animationName + "' not defined for walker type '" + walkerType + "'");
            // fallback animation
            parentFolder = walkerType.name() + "PNG";
            folder = "idle";
            errorHandling();
            return;
        } else { // to ensure error handling is not mutated/over-written.
            parentFolder = walkerType.name() + "PNG";
            folder = animationName.name().toLowerCase();
            numFrames = WALKER_MAP.get(walkerType).get(animationName);
        }
        // Main Logic
        switch (walkerType) {
            case PLAYER -> {
                parentFolder = "PlayerPNG";
                Y_OFFSET = 0;
                X_OFFSET = 0;
            }
            case WIZARD ->  {
                parentFolder = "WizardPNG";
                Y_OFFSET = 1;
                X_OFFSET = 0;
            }
            case WORM -> {
                parentFolder = "WormPNG";
                Y_OFFSET = 0;
                X_OFFSET = -2;

            }
            default -> {
                System.err.println("Unhandled walker type: " + walkerType);
                errorHandling();
            }
        }
        if (numFrames <= 0) {
            System.err.println("Invalid frame count for " + walkerType + "/" + animationName);
            numFrames = 1;
        }
    }
    private void errorHandling() {
        numFrames = 1;
        Y_OFFSET = 0;
        X_OFFSET = 0;
    }
}
