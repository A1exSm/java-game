package game.utils.menu;
// Imports

import game.core.GameView;

import javax.swing.*;
import java.awt.*;

// Class
class MenuJButton extends JButton {
    // Constructor
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
            });
        }
        init();

    }
    // Methods
    private void init() {
        setBackground(new Color(115, 102, 73));
        setForeground(Color.WHITE);
        setBorderPainted(false);
        setFocusable(false);
        setFocusPainted(false);
        setFont(GameView.DISPLAY_FONT);

    }
}
