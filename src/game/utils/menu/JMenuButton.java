package game.utils.menu;
// Imports

import javax.swing.*;
import java.awt.*;

// Class
public class JMenuButton extends JButton {
    // Fields
    public final String name;
    // Constructor
    public JMenuButton(String name) {
        super();
        System.out.println("Here");
        this.name = name;
        addActionListener(e -> {
            System.out.println(getIcon().getIconWidth() + ", " + getIcon().getIconHeight());
        });
        addChangeListener( c -> {
            if (getModel().isRollover()) {
            }
        });
        init();

    }
    // Methods
    private void init() {
        setBackground(new Color(0,0,0,0));
        ImageIcon icon = new ImageIcon("data/Display_assets/GUI/Buttons/tile001.png");
        ImageIcon rollOverIcon = new ImageIcon("data/Display_assets/GUI/Buttons/tile003.png");
        ImageIcon pressedIcon = new ImageIcon("data/Display_assets/GUI/Buttons/tile002.png");
        setIcon(new ImageIcon(icon.getImage().getScaledInstance(174,95, 1)));
        setContentAreaFilled(false);
        setFocusable(false);
        setBorderPainted(false);
        setRolloverEnabled(false);
        setRolloverIcon(new ImageIcon(rollOverIcon.getImage().getScaledInstance(174,95, 1)));
        setPressedIcon(new ImageIcon(pressedIcon.getImage().getScaledInstance(174,95, 1)));
    }
}
