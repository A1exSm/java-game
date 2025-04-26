package game.utils;
// Imports
import game.core.GameWorld;
import game.core.GameView;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
// Class
/**
 * The InventoryButton class represents a button in the game's inventory UI.
 * It extends {@link JButton} and provides additional functionality specific to its purpose.
 */
public class InventoryButton extends JButton {
    // Fields
    private ImageIcon icon;
    private final GameWorld world;
    private final int buttonIndex;
    private Timer rePaintTimer;
    private static final ArrayList<int[]> buttonPositions = new ArrayList<>(){};
    static {
        buttonPositions.add(new int[]{ 810, 543, 75, 75});
        buttonPositions.add(new int[]{ 894, 543, 75, 75});
        buttonPositions.add(new int[]{ 978, 543, 75, 75});
        buttonPositions.add(new int[]{ 1062, 543, 75, 75});
    }
    // Constructor
    /**
     * Constructs an InventoryButton with the specified GameView and button index.
     *
     * @param view the GameView to which this button will be added
     * @param buttonIndex the index of this button in the inventory
     */
    public InventoryButton(GameView view, int buttonIndex) {
        super();
        view.add(this);
        view.setComponentZOrder(this, 1);
        this.world = view.getGameWorld();
        this.buttonIndex = buttonIndex;
        setRolloverEnabled(false); // stops the button from changing colour when hovered over
        setBackground(new Color(94, 43, 48));
        setFocusable(false); // prevents button from taking keyboard focus (we can move while interacting with menu)
        setButtonBounds();
        setBorderPainted(false);
        initListeners();
    }
    // Methods | Public
    /**
     * Sets the bounds of the button based on its index in a buttonPositions array.
     */
    private void setButtonBounds() {
        setBounds(buttonPositions.get(buttonIndex)[0], buttonPositions.get(buttonIndex)[1], buttonPositions.get(buttonIndex)[2], buttonPositions.get(buttonIndex)[3]);
    }
    /**
     * Initializes the mouse and action listeners for the button.
     */
    private void initListeners() {
        // MouseListener
        MouseListener mouseListener = new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON3) {
                    world.dropInventoryItem(buttonIndex);
                }
            }
        };
        addMouseListener(mouseListener);
        // ActionListener
        addActionListener(e -> world.useInventoryItem(buttonIndex));
    }
    /**
     * Adds an icon to the button and starts a repaint timer.<br>
     * The repaint timer enables GIFs to be updated and displayed correctly.
     *
     * @param icon the ImageIcon to be added to the button
     */
    public void addIcon(ImageIcon icon) {
        if (this.icon==null) {
            this.icon = icon;
            setIcon(this.icon);
            if (rePaintTimer != null) {
                rePaintTimer.stop();
            }
            rePaintTimer = new Timer(200, e -> repaint());
            rePaintTimer.start();
        }
    }
    /**
     * Removes the icon from the button and stops the repaint timer.
     */
    public void removeIcon() {
        if (this.icon!=null) {
            this.icon = null;
            setIcon(null);
            rePaintTimer.stop();
        }
    }
    /**
     * Disables interaction with the button.
     */
    public void disableInteract() {
        setEnabled(false);
    }
    /**
     * Enables interaction with the button.
     */
    public void enableInteract() {
        setEnabled(true);
    }

}
