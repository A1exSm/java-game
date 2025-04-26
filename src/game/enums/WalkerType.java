package game.enums;
import game.body.walkers.PlayerWalker;
import game.body.walkers.mobs.HuntressWalker;
import game.body.walkers.mobs.WizardWalker;
import game.body.walkers.mobs.WormWalker;
import org.jbox2d.common.Vec2;

/**
 * Enums representing the different walker types in the game.
 * Stores the half-dimension constants of each walker type.
 */
public enum WalkerType {
    /**
     * The player walker type.
     * This is the default walker type.
     */
    PLAYER(PlayerWalker.HALF_X, PlayerWalker.HALF_Y),
    /**
     * The wizard walker type.
     */
    WIZARD(WizardWalker.HALF_X, WizardWalker.HALF_Y),
    /**
     * The worm walker type.
     */
    WORM(WormWalker.HALF_X, WormWalker.HALF_Y),
    /**
     * The huntress walker type.
     * @deprecated {@link HuntressWalker} is deprecated.
     * @see game.body.walkers.mobs.HuntressWalker
     */
    @Deprecated
    HUNTRESS(HuntressWalker.HALF_X, HuntressWalker.HALF_Y),;
    // Fields
    private final float HALF_Y;
    private final float HALF_X;
    // Constructor
    WalkerType(float HALF_X, float HALF_Y) {
        this.HALF_X = HALF_X;
        this.HALF_Y = HALF_Y;
    }
    // Methods
    /**
     * Gets the static constants for the walker type.
     * Converts the static constants to a Vec2
     * @return the half-dimensions of the walker type
     */
    public Vec2 getHalfDimensions() {
        return new Vec2(HALF_X, HALF_Y);
    }
}
