package game.utils.menu;
// Imports

import javax.swing.*;
import java.awt.*;

// Class
class MenuJSlider extends JSlider {
    // Fields

    // Constructor
    protected MenuJSlider(JComponent parent, int[] bounds) {
        super();
        parent.add(this);
        JMenuPanel.boundErrorHandler(this, bounds);
        setBackground(new Color(115, 102, 73));
        setFocusable(false);
    }
    // Methods
}
