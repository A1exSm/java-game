package game.body.walkers.mobs;
// Imports
import game.enums.Direction;
import game.enums.State;
import city.cs.engine.*;
import game.GameWorld;
import game.enums.Walkers;
import org.jbox2d.common.Vec2;
import static game.enums.State.*;
import static game.enums.Direction.*;

import java.awt.event.ActionListener;
import java.util.HashMap;

// Class
public class WizardWalker extends MobWalker {
    // Fields
    public static final float HALF_X = 1;
    public static final float HALF_Y = 2;
    private static int wizardCount = -1;
    private static final int MAX_HP = 1000;
    private int HealthPoints = 1000;

    // Constructor
    public WizardWalker(GameWorld gameWorld, Vec2 origin) {
        super(gameWorld, new BoxShape(1,2), origin, true, Walkers.WIZARD);
//        populateMap();
        wizardCount++;
        setName("Wizard"+wizardCount);
//        initImages();
        GameWorld.addWizard(this);
    }

    public void takeDamage(int damage, String String) {
        HealthPoints -= damage;
        checkHealth();
    }

    private void checkHealth() {
        if (HealthPoints <= 0) {
            beginDeath();
        } else {

        }
    }
}
