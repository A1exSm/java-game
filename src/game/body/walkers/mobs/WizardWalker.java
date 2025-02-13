package game.body.walkers.mobs;
// Imports
import game.enums.Direction;
import game.enums.State;
import city.cs.engine.*;
import game.GameWorld;
import org.jbox2d.common.Vec2;
import static game.enums.State.*;
import static game.enums.Direction.*;
import java.util.HashMap;

// Class
public class WizardWalker extends MobWalker {
    // Fields
    public static final float HALF_X = 1;
    public static final float HALF_Y = 2;
    private static int wizardCount = -1;
    private final static HashMap<State, BodyImage> animationStateMap = new HashMap<>(); // it is final since objects fields can be changed when final
    private AttachedImage currentAttachedImage;
    private BodyImage currentBodyImage;
    private Direction currentDirection = RIGHT;
    {
        wizardCount++;
    }
    // Constructor
    public WizardWalker(GameWorld gameWorld, Vec2 origin) {
        super(gameWorld, new BoxShape(1,2), origin, true);
        populateMap();
        setName("Wizard"+wizardCount);
        initImages();
        GameWorld.addWizard(this);
    }

    // Methods
    @Override
    public void animate() {
        if (animationStateMap.get(getState()) != currentBodyImage || currentDirection != getDirection()) {
            removeAllImages();
            currentBodyImage = animationStateMap.get(getState());
            currentAttachedImage = new AttachedImage(this, currentBodyImage,1, 0, new Vec2(0,1));
            if (getDirection() == LEFT) currentAttachedImage.flipHorizontal();
            currentDirection = getDirection();
        }
    }


    private void populateMap() {
        animationStateMap.put(ATTACK1, new BodyImage("data/WizardGifs/ATTACK1.gif",18));
        animationStateMap.put(ATTACK2, new BodyImage("data/WizardGifs/ATTACK2.gif",18));
        animationStateMap.put(DEATH, new BodyImage("data/WizardGifs/DEATH.gif",18));
        animationStateMap.put(FALL, new BodyImage("data/WizardGifs/FALL.gif",18));
        animationStateMap.put(HIT, new BodyImage("data/WizardGifs/HIT.gif",18));
        animationStateMap.put(IDLE, new BodyImage("data/WizardGifs/IDLE.gif",18));
        animationStateMap.put(JUMP, new BodyImage("data/WizardGifs/JUMP.gif",18));
        animationStateMap.put(RUN, new BodyImage("data/WizardGifs/RUN.gif",18));


    }

    private void initImages() {
        currentBodyImage = new BodyImage("data/WizardGifs/IDLE.gif",18);
        currentAttachedImage = new AttachedImage(this, currentBodyImage, 1, 0, new Vec2(0, 1));
    }
}
