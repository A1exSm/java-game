package game.menu;
// Imports
import game.core.GameSound;
import game.core.GameView;
import game.enums.SoundGroups;
import javax.swing.*;
import java.awt.*;
// Class
/**
 * The MenuJButton class extends {@link JButton} to create a custom button for the game menu.
 * It includes a sound effect for button press and changes appearance on hover and press.
 */
class MenuJButton extends JButton {
    // Fields
    private static final GameSound buttonSound = GameSound.createSound("data/Audio/UI/confirm_style_2_001.wav", SoundGroups.UI, 1000);
    // Constructor
    /**
     * Constructor for MenuJButton.
     * Initializes the button with specified {@code text}, {@code bounds}, and change listener.
     * Adds the button to the parent panel and sets its initial appearance.
     * @param parent The parent JPanel to which this button will be added.
     * @param text The text to be displayed on the button.
     * @param bounds The bounds of the button in the format [x, y, width, height].
     * @param addChangeListener A boolean indicating whether to add a change listener for hover and press effects.
     */
    protected MenuJButton(JPanel parent, String text, int[] bounds, boolean addChangeListener) {
        super(text);
        parent.add(this);
        JMenuPanel.boundErrorHandler(this, bounds);
        if (addChangeListener) {
            addChangeListener( e -> {
                if (getModel().isRollover()) {
                    setBackground(new Color(161, 144, 99));
                    setForeground(Color.BLACK);
                } else {
                    setBackground(new Color(115, 102, 73));
                    setForeground(Color.WHITE);

                }
                if (getModel().isPressed()) {
                    buttonSound.play();
                }
            });
        }
        init();

    }
    // Methods
    /**
     * Initializes the button's appearance.
     * Sets the background color, foreground color, border, and font.
     */
    private void init() {
        setBackground(new Color(115, 102, 73));
        setForeground(Color.WHITE);
        setBorderPainted(false);
        setFocusable(false);
        setFocusPainted(false);
        if (System.getProperty("os.name").contains("Mac")) {
            setFont(JMenuPanel.MAC_FONT);
        } else {
            setFont(GameView.DISPLAY_FONT);
        }
        setOpaque(true);
    }
}
