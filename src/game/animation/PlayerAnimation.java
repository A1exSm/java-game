package game.animation;
// Imports
import city.cs.engine.World;
import city.cs.engine.Walker;
import game.body.walkers.PlayerWalker;
import game.enums.Direction;
import game.enums.State;

// Class
class PlayerAnimation {
    // Fields
    private final Walker player;
    private final PlayerFrames playerFrame;
    private int currentFrame;
    // Constructor
    protected PlayerAnimation(World world, PlayerWalker player, State animation) {
        this.player = player;
        playerFrame = new PlayerFrames(animation, world); // creating a new Frame for the game.animation (= PlayerState)
        currentFrame = 1;
    }
    // Methods
    protected void resetFrame() {
        currentFrame = 1;
    }
    protected void incrementFrame(Direction direction) {
        currentFrame++;
        if (currentFrame > playerFrame.numFrames) currentFrame = 1; // ensures that there is no situation where we are accessing a Frame which is out of range (of the array indexing)
        cycleFrame(direction);
    }
    protected void cycleFrame(Direction direction) {
        player.removeAllImages();
        switch (direction) {
            case RIGHT -> player.addImage(playerFrame.getAnimationFrames().get(currentFrame - 1));
            case LEFT -> player.addImage(playerFrame.getAnimationFrames().get(currentFrame - 1)).flipHorizontal(); // PNG is facing right, so if the movement is to the left with call .flipHorizontal();
        }
    }
    // Getters
    protected int getNumFrames() {
        return playerFrame.numFrames;
    }
}
