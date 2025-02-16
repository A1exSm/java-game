package game.animation;
import static game.enums.Walkers.*;
import static game.enums.State.*;
import game.enums.State;
import game.enums.Walkers;
import java.util.HashMap;

public class WalkerAnimationFrames extends AnimationFrames {
    // Fields
    private static final HashMap<State, Integer> PLAYER_MAP = new HashMap<>();
    private static final HashMap<State, Integer> WIZARD_MAP = new HashMap<>();
    public final State animationType;
    public final Walkers walkerType;

    static { // We are making a static map so we don't need a large chunk of logic to execute everytime a new object is made
        PLAYER_MAP.put(ATTACK1, 6);
        PLAYER_MAP.put(ATTACK2, 6);
        PLAYER_MAP.put(DEATH, 6);
        PLAYER_MAP.put(FALL, 2);
        PLAYER_MAP.put(HIT, 4);
        PLAYER_MAP.put(IDLE, 8);
        PLAYER_MAP.put(JUMP, 2);
        PLAYER_MAP.put(RUN, 8);
        // Wizard
        WIZARD_MAP.put(ATTACK1, 8);
        WIZARD_MAP.put(ATTACK2, 8);
        WIZARD_MAP.put(DEATH, 7);
        WIZARD_MAP.put(FALL, 2);
        WIZARD_MAP.put(HIT, 3);
        WIZARD_MAP.put(IDLE, 8);
        WIZARD_MAP.put(JUMP, 2);
        WIZARD_MAP.put(RUN, 8);
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
        folder = animationName.name().toLowerCase();
        if (walkerType == PLAYER) {
            parentFolder = "PlayerPNG";
            numFrames = PLAYER_MAP.get(animationName);
            Y_OFFSET = 0;
        }
        else if (walkerType == WIZARD) {
            parentFolder = "WizardPNG";
            numFrames = WIZARD_MAP.get(animationName);
            Y_OFFSET = 1;
        }
    }
}
