package game.animation;
// Imports
import city.cs.engine.AttachedImage;
import game.body.walkers.WalkerFrame;
import game.enums.Direction;
import game.enums.State;
import org.jbox2d.common.Vec2;

// Class
class FrameHandler {
    // Fields
    private final WalkerFrame walker;
    private final WalkerAnimationFrames walkerFrame;
    private int currentFrame;
    // Constructor
    protected FrameHandler(WalkerFrame walker, State animation) {
        this.walker = walker;
        walkerFrame = new WalkerAnimationFrames(animation, walker.getWalkerType()); // creating a new Frame for the game.animation (= PlayerState)
        currentFrame = 1;
    }
    // Methods
    protected void resetFrame() {
        currentFrame = 1;
    }
    protected void incrementFrame(Direction direction) {
        currentFrame++;
        if (currentFrame > walkerFrame.numFrames) currentFrame = 1; // ensures that there is no situation where we are accessing a Frame which is out of range (of the array indexing)
        cycleFrame(direction);
    }
    protected void cycleFrame(Direction direction) {
        walker.removeAllImages();
        System.out.println();
        switch (direction) {
            case RIGHT -> new AttachedImage(walker, walkerFrame.getAnimationFrames().get(currentFrame - 1), 1, 0, new Vec2(walkerFrame.X_OFFSET, walkerFrame.Y_OFFSET));
            case LEFT -> new AttachedImage(walker, walkerFrame.getAnimationFrames().get(currentFrame - 1), 1, 0, new Vec2(walkerFrame.X_OFFSET, walkerFrame.Y_OFFSET)).flipHorizontal();
        }
    }
    // Getters
    protected int getNumFrames() {
        return walkerFrame.numFrames;
    }
}
