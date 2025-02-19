package game.animation;

import city.cs.engine.BodyImage;

import java.util.ArrayList;

class AnimationFrames {
    // Fields
    private final ArrayList<BodyImage> animationFrames = new ArrayList<>();
    protected String parentFolder;
    protected String folder;
    protected int numFrames;
    protected float height;
    protected float Y_OFFSET;
    protected float X_OFFSET;
    // Constructor
    public AnimationFrames() {}
    // Methods
    protected void loadFrames() {
        if (animationFrames.isEmpty()) { // ensures no accidental duplicate frames if somehow loadFrames is called a second time (only while I have not implemented exception handling)
            for (int i = 0; i < numFrames; i++) {
                animationFrames.add(new BodyImage(String.format("data/%s/%s/tile%03d.png", parentFolder, folder, i), height));
            }
        }
    }
    // Getter
    protected ArrayList<BodyImage> getAnimationFrames() {
        return animationFrames;
    }
}
