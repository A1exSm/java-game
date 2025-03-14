package game.utils.menu;
// Imports

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;

// Class
public class JMenuButton extends JButton {
    // Constructor
    public JMenuButton() {
        super();
        addChangeListener( e -> {
          if (getModel().isRollover()) {
              setBackground(new Color(161, 144, 99));
              setForeground(Color.BLACK);
            } else {
              setBackground(new Color(115, 102, 73));
              setForeground(Color.WHITE);

            }
        });
        init();

    }
    // Methods
    private void init() {
        setBackground(new Color(115, 102, 73));
        setForeground(Color.WHITE);
        setBorderPainted(false);
        setFocusable(false);
        setFocusPainted(false);
//        setRolloverEnabled(true);

    }
}
