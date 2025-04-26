package game.animation;
import game.utils.GameBodyImage;
import java.util.ArrayList;
/**
 * The AnimationFrames class manages a collection of animation frames for a game character or object.
 * It loads frames from specified folders and provides access to the loaded frames.<br>
 * {@link AnimationFrames} is abstract, and thus cannot be directly created.
 * @author Alexander Smolowitz, alexander.smolowitz@city.ac.uk
 * @since 12-02-2025
 */
abstract class AnimationFrames {
    // Fields
    private final ArrayList<GameBodyImage> animationFrames = new ArrayList<>();
    /**
     * The parent folder where the animation frames are stored.
     * This is typically the name of the character or object.
     */
    protected String parentFolder;
    /**
     * The specific folder within the parent folder where the animation frames are stored.
     * This is typically the name of the animation
     */
    protected String folder;
    /**
     * The number of frames in the animation.
     * The number of images in the specified folder.
     */
    protected int numFrames;
    /**
     * Used to create the BodyImage objects.
     */
    protected float height;
    /**
     * The Y offset for the animation frames.
     * Used in the creation of an {@link city.cs.engine.AttachedImage AttachedImage}.
     */
    protected float Y_OFFSET;
    /**
     * The X offset for the animation frames.
     * Used in the creation of an {@link city.cs.engine.AttachedImage AttachedImage}.
     */
    protected float X_OFFSET;
    // Methods
    /**
     * Loads animation frames from the specified folder.
     * Ensures no duplicate frames are loaded if called multiple times.
     */
    protected void loadFrames() {
        if (animationFrames.isEmpty()) { // ensures no accidental duplicate frames if somehow loadFrames is called a second time (only while I have not implemented exception handling)
            for (int i = 0; i < numFrames; i++) {
                animationFrames.add(new GameBodyImage(String.format("data/%s/%s/tile%03d.png", parentFolder, folder, i), height));
            }
        }
    }
    // Getter
    /**
     * Returns the list of loaded animation frames.
     * @return An ArrayList of BodyImage objects representing the animation frames.
     */
    protected ArrayList<GameBodyImage> getAnimationFrames() {
        return animationFrames;
    }
}
