package animation;

import city.cs.engine.DynamicBody;
import city.cs.engine.World;

public class PlayerFrames extends AnimationFrames {
    private final PlayerState animationName;
    private final World world;

    public PlayerFrames(PlayerState animationName, World world) {
        super(); // parent constructor is empty, but is here to facilitate future implementation
        this.animationName = animationName;
        this.world = world;
        // assigning inherited fields
        height = 18f;
        parentFolder = "PlayerPNG";
        stateSwitch();
        loadFrames();
    }

    private void stateSwitch(){
        switch (animationName) {
            case ATTACK1 -> folder = "attack1";
            case ATTACK2 -> folder = "attack2";
            case DEATH -> folder = "death";
            case FALL -> folder = "fall";
            case HIT -> folder = "hit";
            case HITW -> folder = "hitw";
            case IDLE -> folder = "idle";
            case JUMP -> folder = "jump";
            case RUN -> folder = "run";
        }
    }

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
