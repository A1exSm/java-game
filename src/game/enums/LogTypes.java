package game.enums;


import javax.swing.text.Style;
import java.awt.*;

public enum LogTypes {
    LOG,
    INFO,
    WARNING,
    ERROR,
    DEBUG,
    HELP,
    INPUT,
    RESPONSE,
    INVALID;
    static {
        // Default color
        LOG.color = Color.LIGHT_GRAY;
        // Info color
        INFO.color = new Color(120, 255, 120);
        // Error color
        ERROR.color = new Color(255, 100, 100);
        // Warning color
        WARNING.color = new Color(255, 200, 0);
        // Debug color
        DEBUG.color = new Color(100, 200, 255);
        // Help color
        HELP.color = new Color(190, 120, 255);
        // Input color
        INPUT.color = new Color(255, 255, 255);
        // Response color
        RESPONSE.color = new Color(180, 180, 255);
        // Invalid color
        INVALID.color = new Color(255, 160, 160);
    }
    // Fields
    public Color color;
    public Style style;
}
