package game.animation;

import city.cs.engine.DynamicBody;
import city.cs.engine.World;

public class PlayerFrames extends AnimationFrames {
    // Fields
    private final PlayerState animationName;
    private final World world;
    // Constructor
    public PlayerFrames(PlayerState animationName, World world) {
        super(); // parent constructor is empty, but is here to facilitate future implementation
        this.animationName = animationName;
        this.world = world;
        // assigning inherited fields
        height = 18f;
        parentFolder = "PlayerPNG";
        stateSwitch();
        this.loadFrames();
    }
    // Methods
    private void stateSwitch(){
        switch (animationName) {
            case ATTACK1 -> {
                folder = "attack1";
                numFrames = 6;
            }
            case ATTACK2 -> {
                folder = "attack2";
                numFrames = 6;
            }
            case DEATH -> {
                folder = "death";
                numFrames = 6;
            }
            case FALL -> {
                folder = "fall";
                numFrames = 2;
            }
            case HIT -> {
                folder = "hit";
                numFrames = 4;
            }
            case HITW -> {
                folder = "hitw";
                numFrames = 4;
            }
            case IDLE -> {
                folder = "idle";
                numFrames = 8;
            }
            case JUMP -> {
                folder = "jump";
                numFrames = 2;
            }
            case RUN -> {
                folder = "run";
                numFrames = 8;
            }
        }
    }
    // Getters
    private DynamicBody getBody() { // I made it this way so that if they player is destroyed, I won't have to reconstruct this object
        DynamicBody playerBody = null;
        for (DynamicBody body : world.getDynamicBodies()) {
            if (body.getName().equals("Player")) {
                playerBody = body;
            }
        }
        return playerBody;
    }
}
