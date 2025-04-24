package game.menu;

import javax.swing.*;
import java.awt.*;

public class JLabelTitle extends JLabel {

    public JLabelTitle(JComponent parent, int x, int y, int width, int height, String title) {
        super(title);
        setFont(JMenuPanel.OPTIONS_FONT);
        setForeground(new Color(95,86,72));
        setBounds(x, y, width, height);
        parent.add(this);
    }
}
