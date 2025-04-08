package game.enums;


import javax.swing.text.Style;
import java.awt.*;

public enum LogTypes {
    LOG,
    INFO,
    WARNING,
    ERROR,
    DEBUG;
    static {
        // Default style
        LOG.color = Color.LIGHT_GRAY;
        // Info style
        INFO.color = new Color(120, 255, 120);
        // Error style
        ERROR.color = new Color(255, 100, 100);
        // Warning style
        WARNING.color = new Color(255, 200, 0);
        // Debug style
        DEBUG.color = new Color(100, 200, 255);
    }
    // Fields
    public Color color;
    public Style style;
}
