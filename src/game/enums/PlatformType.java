package game.enums;
import game.utils.GameBodyImage;
/**
 * Enum Representing the different types of platforms for MagicCliffs.
 */
public enum PlatformType {
    /**
     * Represents {@link game.body.staticstructs.ground.magicCliffs.MagicCliffDark}
     */
    CLIFF_DARK(new GameBodyImage("data/MagicCliffs/cliff/dark_left.png", 15.5f), new GameBodyImage("data/MagicCliffs/cliff/dark_middle.png", 15.5f),4.8f, -0.5f, -1.28f,-0.5f),
    /**
     * Represents {@link game.body.staticstructs.ground.magicCliffs.MagicCliffLight}
     */
    CLIFF_LIGHT(new GameBodyImage("data/MagicCliffs/cliff/light_left.png", 15.5f), new GameBodyImage("data/MagicCliffs/cliff/dark_middle.png", 15.5f),4.8f, -0.5f, -1.28f,-0.5f),
    /**
     * *NOT IMPLEMENTED*
     */
    GROUND(new GameBodyImage("data/MagicCliffs/ground/light_left.png", 15.5f), new GameBodyImage("data/MagicCliffs/ground/light_large.png", 4.5f), 5.1f, 0.5f, 0, 4.8f);

    // Fields
    /**
     * The image for the side of the platform.
     */
    public final GameBodyImage sideImage;
    /**
     * The image for the middle of the platform.
     */
    public final GameBodyImage middleImage;
    /**
     * The x offset for the side image.
     */
    public final float sideX;
    /**
     * The y offset for the side image.
     */
    public final float sideY;
    /**
     * The x offset for the middle image.
     */
    public final float middleX;
    /**
     * The y offset for the middle image.
     */
    public final float middleY;
    // Constructor
    PlatformType(GameBodyImage sideImage, GameBodyImage middleImage, float sideX, float sideY, float middleX, float middleY) {
        this.sideImage = sideImage;
        this.middleImage = middleImage;
        this.sideX = sideX;
        this.sideY = sideY;
        this.middleX = middleX;
        this.middleY = middleY;
    }
}
