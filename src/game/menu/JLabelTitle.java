package game.menu;
import javax.swing.*;
import java.awt.*;
/**
 * The JLabelTitle class extends {@link JLabel} to create a custom title label for the game menu.
 * It sets the font, foreground colour, and bounds for the label.
 */
public class JLabelTitle extends JLabel {
    /**
     * Constructor for JLabelTitle.
     * Initializes the label with specified parent component, position, size, and title text.
     * Sets the font and foreground colour for the label.
     * Adds the label to the parent component.
     * @param parent The parent JComponent to which this label will be added.
     * @param x The x-coordinate of the label's position.
     * @param y The y-coordinate of the label's position.
     * @param width The width of the label.
     * @param height The height of the label.
     * @param title The text to be displayed on the label.
     */
    public JLabelTitle(JComponent parent, int x, int y, int width, int height, String title) {
        super(title);
        setFont(JMenuPanel.OPTIONS_FONT);
        setForeground(new Color(95,86,72));
        setBounds(x, y, width, height);
        parent.add(this);
    }
}
