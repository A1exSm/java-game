package game.menu;
// Imports
import game.core.console.Console;

import javax.swing.*;
import java.awt.*;
// Class
/**
 * The MenuJSlider class extends {@link JSlider} to create a custom slider for the game menu.
 * It includes custom background color and focus settings.
 */
class MenuJSlider extends JSlider {
    // Constructor
    /**
     * Constructor for MenuJSlider.
     * Initializes the slider with specified bounds and adds it to the parent component.
     * Sets the background color and focusable property.
     * @param parent The parent JComponent to which this slider will be added.
     * @param width The width of the slider.
     * @param height The height of the slider.
     */
    protected MenuJSlider(JComponent parent, int width, int height) {
        super();
        parent.add(this);
        setBackground(new Color(115, 102, 73));
        setFocusable(false);
        setOpaque(true);
        setSize(width, height);
    }
    // Methods
}
