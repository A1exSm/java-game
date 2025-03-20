package game.utils.menu;
// Imports

import game.core.GameSound;
import game.core.GameView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

// Class
class MenuJButton extends JButton {
    // Fields
    private static final GameSound buttonSound = GameSound.createSound("data/Audio/UI/confirm_style_2_001.wav", 1000);
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
                if (getModel().isPressed()) {
                    buttonSound.play();
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
