package game.utils;
// Imports
import city.cs.engine.BodyImage;
import org.jbox2d.common.Vec2;
import javax.swing.*;
/**
 * GameBodyImage class extends BodyImage to create a custom class
 * It calculates the width based on the aspect ratio of the image file.
 * The class provides a method to get the dimensions of the image.
 *
 * @author Alexander Smolowitz
 */
// Class
public class GameBodyImage extends BodyImage {
    // Fields
    private final float width;
    private final float height;
    private final String fileName;
    // Constructor
    /**
     * Constructor for GameBodyImage.<br>
     * Takes a file name and height to create a BodyImage with the correct aspect ratio.
     *
     * @param fileName the name of the image file
     * @param height the height of the image
     */
    public GameBodyImage(String fileName, float height) {
        super(fileName, height);
        this.fileName = fileName;
        ImageIcon icon = new ImageIcon(fileName);
        float aspectRatio = (float) icon.getIconWidth() / (float) icon.getIconHeight();
        this.width = height*aspectRatio;
        this.height = height;
    }
    // Methods | Getters
    /**
     * Gets the calculated dimensions of the image
     * @return a Vec2 object representing the dimensions of the image
     * @see Vec2
     */
    public Vec2 getDimensions() {
        return new Vec2(width, height);
    }
    /**
     * Gets the calculated half-dimensions of the image
     * @return a Vec2 object representing the half-dimensions of the image
     * @see Vec2
     */
    public Vec2 getHalfDimensions() {
        return new Vec2(width/2, height/2);
    }
    /**
     * Gets a new scaled instance of the image with the specified height.
     * The height is added to the current height of the image.
     * @param height the height to add to the current height
     * @return a new GameBodyImage object with the updated height
     */
    public GameBodyImage getScaledInstance(float height) {
        return new GameBodyImage(fileName, this.height + height);
    }
}
