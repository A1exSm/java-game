package game.enums;

import city.cs.engine.Shape;
import game.body.walkers.PlayerWalker;
import game.body.walkers.mobs.HuntressWalker;
import game.body.walkers.mobs.WizardWalker;
import game.body.walkers.mobs.WormWalker;
import org.jbox2d.common.Vec2;

public enum WalkerType {
    PLAYER,
    WIZARD,
    WORM,
    HUNTRESS;
    static {
        PLAYER.HALF_Y = PlayerWalker.HALF_Y;
        PLAYER.HALF_X = PlayerWalker.HALF_X;
        WIZARD.HALF_Y = WizardWalker.HALF_Y;
        WIZARD.HALF_X = WizardWalker.HALF_X;
        WORM.HALF_Y = WormWalker.HALF_Y;
        WORM.HALF_X = WormWalker.HALF_X;
        HUNTRESS.HALF_Y = HuntressWalker.HALF_Y;
        HUNTRESS.HALF_X = HuntressWalker.HALF_X;
    }
    // Fields
    private float HALF_Y;
    private float HALF_X;
    // Methods
    public Vec2 getHalfDimensions() {
        return new Vec2(HALF_X, HALF_Y);
    }
}
