package game.body.staticstructs.ground;
// Imports
import city.cs.engine.BoxShape;
import city.cs.engine.DestructionEvent;
import city.cs.engine.DestructionListener;
import city.cs.engine.StaticBody;
import game.Game;
import game.core.GameWorld;
import game.core.console.Console;
import org.jbox2d.common.Vec2;
// Class

/**
 * An abstract class used for all ground objects in the game.
 * Acts as an API for creating and interacting with ground objects.
 *  @author Alexander Smolowitz, alexander.smolowitz@city.ac.uk
 *  @since 30-01-2025
 */
public abstract class GroundFrame extends StaticBody {
    // Fields
    protected final Vec2 halfDimensions;
    protected final Vec2 originPos;
    protected float yTop = 0;
    private static int count = -1;
    private boolean propEnabled = false;
    private Prop[] propNodes;
    private boolean destroyed = false;
    // constructor
    /**
     * Constructor for the GroundFrame class.
     * Generally used by ground objects which know their dimensions and origin position from the start.
     * @param gameWorld The game world to which this ground frame belongs.
     * @param halfDimensions The half-dimensions of the ground frame.
     * @param originPos The origin position of the ground frame.
     */
    public GroundFrame(GameWorld gameWorld, Vec2 halfDimensions, Vec2 originPos) {
        super(gameWorld, new BoxShape(halfDimensions.x, halfDimensions.y));
        this.halfDimensions = halfDimensions;
        this.originPos = originPos;
        this.setPosition(originPos);
        this.setName("Ground"+(++count));
        addDestructionListener(destructionEvent -> destroyed = true);
        isDebug();
    }
    /**
     * Constructor for the GroundFrame class.
     * Generally used by ground objects which do not know their dimension
     * and origin position from the start, or require no fixtures.
     * @param gameWorld The game world to which this ground frame belongs.
     */
    public GroundFrame(GameWorld gameWorld) {
        super(gameWorld);
        this.halfDimensions = new Vec2();
        this.originPos = new Vec2();
        this.setName("Ground"+(++count));
        addDestructionListener(destructionEvent -> destroyed = true);
        isDebug();
    }
    /**
     * Checks if the game is in debug mode and sets
     * the line colour and outline accordingly.
     * This is needed due to {@link Game#debugOn()}
     * only handling already created bodies.
     * If a body is created after debug has been toggled,
     * it will not be debugged without this method.
     */
    private void isDebug() {
        boolean debugOn = Game.isDebugOn();
        setLineColor(Game.getColor(debugOn));
        setAlwaysOutline(debugOn);
    }
    // protected
    /**
     * Used to call the super class setPosition method.
     * As the super class setPosition method is overridden in this class
     * by {@link #setPosition(Vec2 pos)}.
     * @param pos The position to set.
     */
    protected void superSetPosition(Vec2 pos) {
        super.setPosition(pos);
    }
    // override
    /**
     * Sets the position of the ground frame.
     * This method is overridden to ensure that the origin position
     * is set correctly and that the yTop value is reset.<br><br>
     * To use the super class setPosition method, use {@link #superSetPosition(Vec2 pos)}.
     * @param pos The position to set.
     */
    @Override
    public void setPosition(Vec2 pos) {
        super.setPosition(pos);
        if (pos != originPos) {
            originPos.x = pos.x;
            originPos.y = pos.y;
        }
        resetYTop();
    }
    // Getters
    /**
     * Returns the half-dimensions of the ground frame.
     * @return half-dimensions.
     */
    public Vec2 getHalfDimensions() {
        return halfDimensions;
    }
    /**
     * Returns the origin position of the ground frame.
     * @return origin position.
     */
    public Vec2 getOriginPos() {
        return originPos;
    }
    /**
     * Returns the yTop value of the ground frame.
     * yTop - the top y position of the ground frame. (the surface)
     * @return yTop value.
     */
    public float getYTop() {
        return yTop;
    }
    // Methods | Public
    /**
     * Resets the yTop value to the top of the ground frame.
     * This method is called in {@link #setPosition(Vec2 pos)}.
     */
    public void resetYTop() {
        yTop = originPos.y + halfDimensions.y;
    }
    // Methods | Protected
    /**
     * Sets the yTop value of the ground frame.
     * This method is used to set the yTop value manually.
     * @param yTop The new yTop value.
     */
    public void setYTop(float yTop) {
        this.yTop = yTop;
    }
    /**
     * Sets the propEnabled value of the ground frame.
     * Nodes allow for props to be added to the ground frame.
     * Node positions are calculated by dividing the width of the ground frame by the number of nodes.
     * @param propEnabled The new propEnabled value.
     * @param nodeCount The number of nodes to create for the prop array.
     */
    public void setPropEnabled(boolean propEnabled, int nodeCount) {
        this.propEnabled = propEnabled;
        if (propNodes == null) {
            propNodes = new Prop[nodeCount];
        }
    }
    /**
     * Returns the propEnabled value of the ground frame.
     * @return propEnabled value.
     */
    public boolean isPropEnabled() {
        return propEnabled;
    }
    /**
     * Adds a prop to the specified index.
     * Displays a warning and deletes the Prop if
     * the array is full, the index is occupied,
     * or the index is out of bounds.
     * @param prop The prop to add.
     * @param index The index to add the prop to. {@code -1} adds to the first available index.
     * @return The prop.
     * @throws IllegalStateException if propEnabled is false.
     */
    public Prop addProp(Prop prop, int index) {
        if (!propEnabled) {
            throw new IllegalStateException(Console.exceptionMessage("Illegal state Prop.isEnabled == false when calling addProp()!"));
        } else if (index > propNodes.length -1 || index < 0) {
            if (index != -1) {
                Console.warning("Prop index " + index + " out of bounds for range " + (propNodes.length -1) + ", deleting prop!");
                prop.destroy();
                return prop;
            }
            for (int i = 0; i < propNodes.length; i++) {
                if (propNodes[i] == null) {
                    index = i;
                    break;
                }
            }
            if (index == -1) {
                Console.warning("No space for prop, deleting prop!");
                prop.destroy();
                return prop;
            }
        }
        if (propNodes[index] != null) {
            Console.warning("Prop already exists at index " + index + ", deleting new prop!");
            prop.destroy();
            return prop;
        }
        float space = (getHalfDimensions().x*2)/propNodes.length;
        prop.setPosition(new Vec2((originPos.x - halfDimensions.x) + space/2 + (index * space), yTop + prop.getImage().getHalfDimensions().y));
        propNodes[index] = prop;
        return prop;
    }
    /**
     * Removes the prop at the specified index.
     * Displays a warning if the index is empty.
     * @param index The index to remove the prop from.
     * @throws IllegalStateException if propEnabled is false.
     * @throws IndexOutOfBoundsException if the index is out of bounds.
     */
    public void removeProp(int index) {
        if (!propEnabled) {
            throw new IllegalStateException(Console.exceptionMessage("Illegal state Prop.isEnabled == false when calling removeProp()!"));
        } else if (index > propNodes.length -1 || index < 0) {
            throw new IndexOutOfBoundsException(Console.exceptionMessage("Prop index " + index + " out of bounds for range " + (propNodes.length -1)));
        }
        if (propNodes[index] == null) {
            Console.warning("Prop does not exist at index " + index + ", cannot remove prop!");
            return;
        }
        propNodes[index].destroy();
        propNodes[index] = null;
    }
    /**
     * Removes all attached images from the ground frame.
     * Calls the {@link #paint()} method to repaint the ground frame.
     * @see city.cs.engine.AttachedImage
     */
    public void repaint() {
        removeAllImages();
        paint();
    }

    /**
     * Gets the position of the ground frame.
     * @return The position
     */
    public Vec2 getPos() { // shorter cus I want it to be like that
        return getPosition();
    }
    // Methods | Public | Static
    /**
     * Returns a random float between the specified min and max values.
     * This is inclusive of both min and max.
     * @param min The minimum value.
     * @param max The maximum value.
     * @return A random float between min and max.
     */
    public static float randRangeFloat(float min, float max) {
        return min + (float) (Math.random() * (max - min + 1));
    }
    /**
     * Returns a random integer between the specified min and max values.
     * This is inclusive of both min and max.
     * @param min The minimum value.
     * @param max The maximum value.
     * @return A random integer between min and max.
     */
    public static int randRangeInt(int min, int max) {
        return min + (int) (Math.random() * (max - min + 1));
    }
    // Methods | Public | Abstract
    /**
     * Handles the creation of attached images for the ground frame.
     * Used for the initial creation of the ground frame.
     * @see city.cs.engine.AttachedImage
     */
    public abstract void paint();

    /**
     * Checks if the ground frame is destroyed.
     * @return {@code true} if the ground frame is destroyed, {@code false} otherwise.
     */
    public boolean isDestroyed() {
        return destroyed;
    }
}
