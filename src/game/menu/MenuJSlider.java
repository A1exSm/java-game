package game.menu;
// Imports
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
     * @param bounds The bounds of the slider in the format [x, y, width, height].
     */
    protected MenuJSlider(JComponent parent, int[] bounds) {
        super();
        parent.add(this);
        JMenuPanel.boundErrorHandler(this, bounds);
        setBackground(new Color(115, 102, 73));
        setFocusable(false);
    }
    // Methods
}
