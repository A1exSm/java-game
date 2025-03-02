package game;
// Imports
import game.body.dynamicstructs.DynamicPolygon;
import game.body.staticstructs.Ground;
import game.body.staticstructs.Trampoline;
import game.body.walkers.PlayerWalker;
import game.body.walkers.mobs.MobWalker;
import game.body.walkers.mobs.WizardWalker;
import city.cs.engine.*;
import game.body.walkers.mobs.WormWalker;
import game.enums.State;
import game.enums.Walkers;
import org.jbox2d.common.Vec2;
import game.animation.*;
import java.util.ArrayList;

// Class
public class GameWorld extends World {
    // Fields
    private static Game game;
    private final PlayerWalker player;
    private static final ArrayList<MobWalker> mobs = new ArrayList<>();

    // Constructor
    public GameWorld(Game game) {
        super();
        GameWorld.game = game;
        player = new PlayerWalker(this);
        new WalkerAnimationFrames(State.RUN, Walkers.PLAYER);
        populate();
        // end of constructor start of a new world :)
        start();
    }

    // Population methods
    private void groundImg2() {
        for (float i = -500+6.6f; i < 500; i+=12) {
            Ground temp = new Ground(this, new Vec2(6, 2.5f), new Vec2(i, -7.5f));
            temp.addImage(new BodyImage("data/ground_tiles/tile_0x2.png",5f)).setOffset(new Vec2(0, 5f));
        }
    }

    private void populate() {
        groundImg2();
        new Ground(this, new Vec2(500, 2f), new Vec2(0, -2f)); // changed from 2.5 to 2 so that fillColor is not visible
        playGround();
    }

    private void playGround() {
        float offset = 100f;
        new Ground(this, new Vec2(2, 2f), new Vec2(-5+offset, 2f));
        DynamicBody toy = new DynamicBody(this, new DynamicPolygon(new Vec2[]{new Vec2(0, 0), new Vec2(), new Vec2(6, 0), new Vec2(6, 2), new Vec2(4, 4), new Vec2(2, 4), new Vec2(0, 2), new Vec2(0, 0)}));
        toy.setLinearVelocity(new Vec2(offset, 1));
        toy.setName("Toy");
        new Ground.Platform(this, new Vec2(20+offset, 4));
        new Ground.Platform(this, new Vec2(27+offset, 7));
        new Trampoline(this, new Vec2(-20+offset, 1));
        initWizards();
    }

    // Store
    private void initWizards() {
        new WizardWalker(this, new Vec2(80,2));
        new WizardWalker(this, new Vec2(110, 2));
        new WizardWalker(this, new Vec2(-30,2));
        new WizardWalker(this, new Vec2(-80,2));
        new WizardWalker(this, new Vec2(-110,2));
        new WormWalker(this, new Vec2(-10,2));
    }

    // External Getters & Setters
    public void togglePlayerAttack() {
        player.toggleOffAttack();
    }

    public PlayerWalker getPlayer() {
        return player;
    }

    public void togglePause() {
        game.togglePause();
    }

    //Mobology
    public static ArrayList<MobWalker> getMobs() {
        ArrayList<MobWalker> aliveMobs = new ArrayList<>();
        for (MobWalker mob : mobs) {
            if(!mob.isDead()) aliveMobs.add(mob);
        }
        return aliveMobs;
    }

    public static void addMob(MobWalker mob) {
        mobs.add(mob);
    }

    public static void removeMob(MobWalker mob) {
        mobs.remove(mob);
    }
}
