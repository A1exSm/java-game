package game.animation;

import city.cs.engine.BodyImage;

import java.util.ArrayList;
/**
 * The AnimationFrames class manages a collection of animation frames for a game character or object.
 * It loads frames from specified folders and provides access to the loaded frames.<br>
 * {@link AnimationFrames} is abstract, and thus cannot be directly created.
 */
abstract class AnimationFrames {
    // Fields
    private final ArrayList<BodyImage> animationFrames = new ArrayList<>();
    protected String parentFolder;
    protected String folder;
    protected int numFrames;
    protected float height;
    protected float Y_OFFSET;
    protected float X_OFFSET;
    // Methods
    /**
     * Loads animation frames from the specified folder.
     * Ensures no duplicate frames are loaded if called multiple times.
     */
    protected void loadFrames() {
        if (animationFrames.isEmpty()) { // ensures no accidental duplicate frames if somehow loadFrames is called a second time (only while I have not implemented exception handling)
            for (int i = 0; i < numFrames; i++) {
                animationFrames.add(new BodyImage(String.format("data/%s/%s/tile%03d.png", parentFolder, folder, i), height));
            }
        }
    }
    // Getter
    /**
     * Returns the list of loaded animation frames.
     * @return An ArrayList of BodyImage objects representing the animation frames.
     */
    protected ArrayList<BodyImage> getAnimationFrames() {
        return animationFrames;
    }
}
