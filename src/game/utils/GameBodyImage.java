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
 * @author Your Name
 * @version 1.0
 * @since 2023-10-01
 */
// Class
public class GameBodyImage extends BodyImage {
    // Fields
    private final float width;
    private final float height;
    private final String fileName;
    // Constructor
    public GameBodyImage(String fileName, float height) {
        super(fileName, height);
        this.fileName = fileName;
        ImageIcon icon = new ImageIcon(fileName);
        float aspectRatio = (float) icon.getIconWidth() / (float) icon.getIconHeight();
        this.width = height*aspectRatio;
        this.height = height;
    }
    // Methods | Getters
    public Vec2 getDimensions() {
        return new Vec2(width, height);
    }
    public Vec2 getHalfDimensions() {
        return new Vec2(width/2, height/2);
    }
    public GameBodyImage getScaledInstance(float height) {
        return new GameBodyImage(fileName, this.height + height);
    }
}
