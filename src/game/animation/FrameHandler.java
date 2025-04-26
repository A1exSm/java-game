package game.animation;
// Imports
import city.cs.engine.AttachedImage;
import game.body.walkers.mobs.WormWalker;
import game.body.walkers.WalkerFrame;
import game.enums.Direction;
import game.enums.State;
import org.jbox2d.common.Vec2;
// Class
/**
 * The FrameHandler class manages the animation frames for a walker in the game.
 * It handles frame cycling, resetting, and updating based on the walker's state and direction.
 * @author Alexander Smolowitz, alexander.smolowitz@city.ac.uk
 * @since 16-02-2025
 */
final class FrameHandler {
    // Fields
    private final WalkerFrame walker;
    private final WalkerAnimationFrames walkerFrame;
    private final State animationType;
    private int currentFrame;
    // Constructor
    /**
     * Constructor for FrameHandler.
     * Initializes the frame handler with the specified walker and animation state.
     * @param walker The WalkerFrame object representing the walker.
     * @param animation The State object representing the animation state.
     */
    FrameHandler(WalkerFrame walker, State animation) {
        this.walker = walker;
        walkerFrame = new WalkerAnimationFrames(animation, walker.getWalkerType()); // creating a new Frame for the game.animation (= PlayerState)
        currentFrame = 1;
        animationType = animation;
    }
    // Methods
    /**
     * Resets the current frame to the first frame.
     */
    void resetFrame() {
        currentFrame = 1;
    }
    /**
     * Increments the current frame and calls {@link #cycleFrame(Direction)}
     * If the current frame exceeds the number of frames, it resets to the first frame.
     * @param direction The Direction object representing the direction of the walker.
     */
    void incrementFrame(Direction direction) {
        currentFrame++;
        if (currentFrame > walkerFrame.numFrames) currentFrame = 1; // ensures that there is no situation where we are accessing a Frame which is out of range (of the array indexing)
        if (animationType == State.ATTACK1 && currentFrame == 11 && walker instanceof WormWalker worm) {
            worm.shootProjectile();
        }
        cycleFrame(direction);
    }
    /**
     * Cycles the current frame and flips it based of the {@code Walker's} direction.
     * @param direction the {@code Walker} is facing
     */
    void cycleFrame(Direction direction) {
        walker.removeAllImages();
        switch (direction) {
            case RIGHT -> new AttachedImage(walker, walkerFrame.getAnimationFrames().get(currentFrame - 1), 1, 0, new Vec2(walkerFrame.X_OFFSET, walkerFrame.Y_OFFSET));
            case LEFT -> new AttachedImage(walker, walkerFrame.getAnimationFrames().get(currentFrame - 1), 1, 0, new Vec2(walkerFrame.X_OFFSET, walkerFrame.Y_OFFSET)).flipHorizontal();
        }
    }
    // Getters
    /**
     * Returns the number of frames in the animation.
     * @return The number of frames in the animation.
     */
    int getNumFrames() {
        return walkerFrame.numFrames;
    }
}
