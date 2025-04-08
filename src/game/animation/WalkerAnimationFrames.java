package game.animation;
import static game.enums.Walkers.*;
import static game.enums.State.*;

import game.core.console.Console;
import game.enums.State;
import game.enums.Walkers;
import java.util.HashMap;
// Class
/**
 * The WalkerAnimationFrames class extends the abastract class {@link AnimationFrames} to manage animation frames for different types of walkers.
 * It initializes the frames based on the walker type and animation state.
 */
public final class WalkerAnimationFrames extends AnimationFrames {
    // Fields
    private static final HashMap<Walkers, HashMap<State, Integer>> WALKER_MAP = new HashMap<>();
    public final State animationType;
    public final Walkers walkerType;

    static { // We are making a static map as we don't need a large chunk of logic to execute everytime a new object is made
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
        // Huntress
        WALKER_MAP.get(HUNTRESS).put(ATTACK1, 5);
        WALKER_MAP.get(HUNTRESS).put(ATTACK2, 5);
        WALKER_MAP.get(HUNTRESS).put(ATTACK3, 7);
        WALKER_MAP.get(HUNTRESS).put(DEATH, 8);
        WALKER_MAP.get(HUNTRESS).put(FALL, 2);
        WALKER_MAP.get(HUNTRESS).put(HIT, 3);
        WALKER_MAP.get(HUNTRESS).put(IDLE, 8);
        WALKER_MAP.get(HUNTRESS).put(JUMP, 2);
        WALKER_MAP.get(HUNTRESS).put(RUN, 8);


    }
    // Constructor
    /**
     * Constructor for WalkerAnimationFrames.
     * Initializes the animation frames for the specified walker type and animation state.
     * @param animationName The state of the animation (e.g., IDLE, RUN).
     * @param walkerType The type of walker (e.g., PLAYER, WIZARD).
     */
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
    /**
     * Initializes the walker type and sets the appropriate folder and frame count.<br><br>
     * <i>if there is no key for the {@code walkerType} or inner key for the {@code animationName},
     * a warning will be printed and default values will be assigned.<br>
     * Additionally, {@link #errorHandling()} is called, then the method returns.</i>
     * @param animationName The state of the animation.
     * @param walkerType The type of walker.
     */
    private void initWalkerType(State animationName, Walkers walkerType) {
        // Error Handling
        if (!WALKER_MAP.containsKey(walkerType) || !WALKER_MAP.get(walkerType).containsKey(animationName)) {
            Console.error("Warning: Animation '" + animationName + "' not defined for walker type '" + walkerType + "'");
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
                Y_OFFSET = 1.1f;
                X_OFFSET = 0.25f;
            }
            case WORM -> {
                parentFolder = "WormPNG";
                Y_OFFSET = 0;
                X_OFFSET = -2;

            }
            case HUNTRESS -> {
                parentFolder = "HuntressPNG";
                Y_OFFSET = 0.5f;
                X_OFFSET = 0;
            }
            default -> {
                Console.error("Unhandled walker type: " + walkerType);
                errorHandling();
            }
        }
        if (numFrames <= 0) {
            Console.error("Invalid frame count for " + walkerType + "/" + animationName);
            numFrames = 1;
        }
    }
    /**
     * Handles errors by setting default values for frame count and offsets.
     */
    private void errorHandling() {
        numFrames = 1;
        Y_OFFSET = 0;
        X_OFFSET = 0;
    }
}
