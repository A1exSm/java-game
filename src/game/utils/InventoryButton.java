package game.utils;
// Imports

import game.core.GameWorld;
import game.core.GameView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

// Class
public class InventoryButton extends JButton {
    // Fields
    private ImageIcon icon;
    private final int buttonIndex;
    private Timer rePaintTimer;
    private static final ArrayList<int[]> buttonPositions = new ArrayList<>(){};
    private MouseListener mouseListener;
    private ActionListener actionListener;
    static {
        buttonPositions.add(new int[]{ 810, 520, 75, 80});
        buttonPositions.add(new int[]{ 891, 520, 75, 80});
        buttonPositions.add(new int[]{ 972, 520, 75, 80});
        buttonPositions.add(new int[]{ 1053, 520, 75, 80});
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
        initListeners();
    }
    // Methods | Public
    private void setButtonBounds() {
        setBounds(buttonPositions.get(buttonIndex)[0], buttonPositions.get(buttonIndex)[1], buttonPositions.get(buttonIndex)[2], buttonPositions.get(buttonIndex)[3]);
    }

    private void initListeners() {
        // MouseListener
        mouseListener = new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON3) {
                    GameWorld.dropInventoryItem(buttonIndex);
                }
            }
        };
        addMouseListener(mouseListener);
        // ActionListener
        actionListener = e -> GameWorld.useInventoryItem(buttonIndex);
        addActionListener(actionListener);
    }

    private void removeListeners() {
        removeMouseListener(mouseListener);
        removeActionListener(actionListener);
    }

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
    public void removeIcon() {
        if (this.icon!=null) {
            this.icon = null;
            setIcon(null);
            rePaintTimer.stop();
        }
    }

    public void disableInteract() {
        removeListeners();
    }

}
