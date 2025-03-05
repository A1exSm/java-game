package game.utils;
// Imports

import game.GameWorld;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

// Class
public class InventoryButton extends JButton {
    // Fields
    private ImageIcon icon;
    private final int buttonIndex;
    private Timer rePaintTimer;
    private static final ArrayList<int[]> buttonPositions = new ArrayList<>(){};
    static {
        buttonPositions.add(new int[]{(1200-200)+10, (610-200)+10, (200-25)/2, (200-25)/2});
        buttonPositions.add(new int[]{(1200-200)+(200+5)/2, (610-200)+10, (200-25)/2, (200-25)/2});
        buttonPositions.add(new int[]{(1200-200)+10, (610-200)+(200+5)/2, (200-25)/2, (200-25)/2});
        buttonPositions.add(new int[]{(1200-200)+(200+5)/2, (610-200)+(200+5)/2, (200-25)/2, (200-25)/2});
    }
    // Constructor
    public InventoryButton(GameView view, int buttonIndex) {
        super();
        view.add(this);
        view.setComponentZOrder(this, 0);
        this.buttonIndex = buttonIndex;
        setRolloverEnabled(false); // stops the button from changing colour when hovered over
        setBackground(new Color(94, 43, 48));
        setFocusable(false); // prevents button from taking keyboard focus (we can move while interacting with menu)
        setButtonBounds();
        setBorderPainted(false);
        addActionListener();
    }
    // Methods
    private void setButtonBounds() {
        setBounds(buttonPositions.get(buttonIndex)[0], buttonPositions.get(buttonIndex)[1], buttonPositions.get(buttonIndex)[2], buttonPositions.get(buttonIndex)[3]);
    }
    public void addIcon(ImageIcon icon) {
        if (this.icon==null) {
            this.icon = icon;
            setIcon(this.icon);
            if (rePaintTimer != null) {
                rePaintTimer.stop();
            }
            rePaintTimer = new Timer(200, e -> {
                repaint();
            });
            rePaintTimer.start();
        }
    }
    public void removeIcon() {
        if (this.icon!=null) {
            this.icon = null;
            setIcon(null);
            rePaintTimer.stop();
        }
    }

    private void addActionListener() {
        addActionListener(e -> {
           GameWorld.useInventoryItem(buttonIndex);
        });
    }
}
