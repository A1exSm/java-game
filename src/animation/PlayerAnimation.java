package animation;
// Imports
import city.cs.engine.World;
import game.BoolTimer;
import city.cs.engine.Walker;

// Class
class PlayerAnimation {
    // Fields
    private final Walker player;
    private final PlayerFrames playerFrame;
    private int currentFrame;
    // Constructor
    protected PlayerAnimation(World world, Walker player, PlayerState animation) {
        this.player = player;
        playerFrame = new PlayerFrames(animation, world);
        currentFrame = 1;
    }
    // Methods
    protected void resetFrame() {
        currentFrame = 1;
    }
    protected void incrementFrame(Direction direction) {
        currentFrame++;
        if (currentFrame > playerFrame.numFrames) currentFrame = 1;
        cycleFrame(direction);
    }
    protected void cycleFrame(Direction direction) {
        player.removeAllImages();
        switch (direction) {
            case RIGHT -> player.addImage(playerFrame.getAnimationFrames().get(currentFrame - 1));
            case LEFT -> player.addImage(playerFrame.getAnimationFrames().get(currentFrame - 1)).flipHorizontal();
        }
    }
    // Getters
    protected int getNumFrames() {
        return playerFrame.numFrames;
    }
}
