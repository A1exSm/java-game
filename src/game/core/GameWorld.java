package game.core;
// Imports
import game.Game;
import game.body.items.Inventory;
import game.body.staticstructs.Ground;
import game.body.staticstructs.Trampoline;
import game.body.walkers.PlayerWalker;
import game.body.walkers.mobs.HuntressWalker;
import game.body.walkers.mobs.MobWalker;
import game.body.walkers.mobs.WizardWalker;
import city.cs.engine.*;
import game.body.walkers.mobs.WormWalker;
import game.enums.State;
import game.enums.WalkerBehaviour;
import game.enums.Walkers;
import org.jbox2d.common.Vec2;
import game.animation.*;

import java.awt.*;
import java.util.ArrayList;

// Class
public class GameWorld extends World {
    // Fields
    private static Game game;
    private final PlayerWalker player;
    private static final ArrayList<MobWalker> mobs = new ArrayList<>();
    public static final Inventory playerInventory = new Inventory(4);
    private boolean toggleMobsPassive = false;

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
        areaOne();
    }

    private void playGround() {
        float offset = 100f;
        new Ground(this, new Vec2(2, 2f), new Vec2(-5+offset, 2f));
        new Ground.Platform(this, new Vec2(20+offset, 4));
        new Ground.Platform(this, new Vec2(27+offset, 7));
        new Trampoline(this, new Vec2(-20+offset, 1));
        initMobs();
//        toggleMobsPassive();

    }
    private void areaOne() {
        ArrayList<Body> areaOne= new ArrayList<>();
        Ground temp1 = new Ground(this, new Vec2(20,2.0f), new Vec2(-25.0f, 200f));
        StaticBody temp2 = new StaticBody(this, new PolygonShape(0,0, 0,-4, -10,-4, -10,10));
        temp2.setPosition(new Vec2(-45.0f, 202f));
        Ground temp3 = sheerWall(new Vec2(-3.9f, 213f), 15.0f);
        Ground temp6 = sheerWall(new Vec2(-56.1f, 218f), 20.0f);
        Ground temp4 = new Ground(this, new Vec2(20, 1.0f), new Vec2(-25.0f, 210f));
        DynamicBody box = new DynamicBody(this, new BoxShape(0.5f,0.5f));
        SolidFixture boxFixture = new SolidFixture(box, new BoxShape(2f, 2f));
        box.setPosition(new Vec2(-25.0f, 212f));
        Ground temp5 = new Ground(this, new Vec2(20, 1.0f), new Vec2(-35.0f, 219f));
        areaOne.add(temp1);
        areaOne.add(temp2);
        areaOne.add(temp3);
        areaOne.add(temp4);
        areaOne.add(temp5);
        areaOne.add(temp6);
        for (Body body : areaOne) {
            Color col =  new Color(120, 110, 100);
            body.setFillColor(col);
            body.setLineColor(col);
        }
        box.setFillColor(new Color(85, 72, 63));
        box.setName("box");
        temp2.setName("Stairs");
        player.setPosition(new Vec2(-28.0f, 212.0f));
        player.setPosition(new Vec2(0, 2));
    }

    private Ground sheerWall(Vec2 pos, float height) {
        Ground temp3 = new Ground(this, new Vec2(1,4.0f), pos);
        SolidFixture temp3Fixture = new SolidFixture(temp3, new BoxShape(1.1f,  height));
        temp3Fixture.setFriction(0.00005f);
        return temp3;
    }

    // Store
    private void initMobs() {
        new WizardWalker(this, new Vec2(-25, 202));
        new WizardWalker(this, new Vec2(70,2));
        new WizardWalker(this, new Vec2(127, 8.9f));
        new WizardWalker(this, new Vec2(-30,2));
        new WizardWalker(this, new Vec2(-80,2));
        new WizardWalker(this, new Vec2(-110,2));
        new WormWalker(this, new Vec2(20,2));
//        new WormWalker(this, new Vec2(-25,222));
        new HuntressWalker(this, new Vec2(-25, 222));
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

    public static void useInventoryItem(int index) {
        playerInventory.use(index);
    }

    public static void dropInventoryItem(int index) {
        playerInventory.drop(index);
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

    public static WormWalker nameToWorm(String name) {
        for (MobWalker mob : mobs) {
            if (mob.getWalkerType().equals(Walkers.WORM) && mob.getName().equals(name)) { // ensures correct type & name
                return (WormWalker) mob; // casts the mob to a WormWalker
            }
        }
        System.err.println("WormWalker with name: "+ name + " not found! Returning null.");
        return null;
    }

    public static WizardWalker nameToWizard(String name) {
        for (MobWalker mob : mobs) {
            if (mob.getWalkerType().equals(Walkers.WIZARD) && mob.getName().equals(name)) { // ensures correct type & name
                return (WizardWalker) mob; // casts the mob to a WormWalker
            }
        }
        System.err.println("WizardWalker with name: "+ name + " not found! Returning null.");
        return null;
    }

    public void toggleMobsPassive() { // only meant to be called during setup
        toggleMobsPassive = !toggleMobsPassive;
        for (MobWalker mob : mobs) {
            mob.setBehaviour(WalkerBehaviour.PASSIVE);
        }
    }

}
