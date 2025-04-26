package game.enums;
import javax.swing.text.Style;
import java.awt.*;
/**
 * Enum representing the different labels for the Console.
 * Each label has a corresponding colour for display purposes.
 * @see game.core.console.Console
 */
public enum LogTypes {
    /**
     * Represents a log message.
     */
    LOG(Color.LIGHT_GRAY),
    /**
     * Represents an information message.
     */
    INFO(new Color(120, 255, 120)),
    /**
     * Represents a warning message.
     */
    WARNING(new Color(255, 200, 0)),
    /**
     * Represents an error message.
     */
    ERROR(new Color(255, 100, 100)),
    /**
     * Represents a debug message.
     */
    DEBUG(new Color(100, 200, 255)),
    /**
     * Represents a help message.
     */
    HELP(new Color(190, 120, 255)),
    /**
     * Represents a user inputted message.
     */
    INPUT(new Color(255, 255, 255)),
    /**
     * Represents a response message to a user input.
     */
    RESPONSE(new Color(180, 180, 255)),
    /**
     * Represents an invalid user input message.
     */
    INVALID( new Color(255, 160, 160));
    // Fields
    /**
     * The colour associated with the log type.
     * Used to set the colour of console messages
     */
    public final Color color;
    /**
     * The style associated with the log type.
     * Used to set the style of console messages
     */
    public Style style;
    // Constructor
    LogTypes(Color color) {
        this.color = color;
    }
}
