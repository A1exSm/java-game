package game.core.console;
// Imports
import game.Game;
import game.enums.LogTypes;
import javax.swing.*;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import java.awt.*;
// Class
/**
 * A class that handles the console interface for the game.<br>
 * The console interface is a {@link JFrame} that displays text in different colours depending on the type of log.<br>
 * The console interface is interacted with using the {@link Console} class static methods.<br>
 * The console is a great way to visually display information, providing a more robust set of tools.
 * @see LogTypes
 * @see Console
 * @author Alexander Smolowitz, alexander.smolowitz@city.ac.uk
 * @since 08-04-2025
 */
final class ConsoleInterface {
    // Fields
    private final JFrame frame;
    private boolean isVisible = false;
    private final StyledDocument document;
    private final JTextPane textPane;
    private final JTextField inputField;
    private int inputCounter = 0;
    private final CommandProcessor commandProcessor;
    // Constructor
    /**
     * Constructor for the ConsoleInterface class.<br>
     * Sets up the frame, text pane, input field and styles for the console interface.
     */
    ConsoleInterface() {
        frame = setupFrame();
        textPane = getTextPane();
        document = textPane.getStyledDocument(); // use a StyledDocument to allow for different colours per line basis
        JScrollPane scrollPane = new JScrollPane(textPane); // scroll pane to allow for traversing the document visually
        inputField = setupInputField();
        frame.getContentPane().add(scrollPane, BorderLayout.CENTER);
        frame.getContentPane().add(inputField, BorderLayout.SOUTH);
        setupStyles();
        commandProcessor = new CommandProcessor(this);
    }
    // Methods | Setup | private
    /**
     * Sets up the frame for the console interface.<br>
     * The frame is a {@link JFrame} that is used to display the console interface.<br>
     * No setDefaultCloseOperation() called, as JFrames default to HIDE_ON_CLOSE.
     * @return The frame.
     */
    private JFrame setupFrame() {
        JFrame newFrame = new JFrame("Console Window");
        newFrame.setPreferredSize(new Dimension(700, 500));
        newFrame.setAutoRequestFocus(false); // do not want the window to gain focus when setVisible() is called.
        newFrame.pack();
        return newFrame;
    }
    /**
     * Sets up the text pane for the console interface.<br>
     * The text pane is a {@link JTextPane} that is used to display the text in different colours depending on the {@link LogTypes LogType}.
     * @return The text pane.
     */
    private JTextPane getTextPane() {
        JTextPane textPane = new JTextPane();
        textPane.setEditable(false);
        textPane.setBackground(Color.BLACK);
        return textPane;
    }
    /**
     * Sets up the styles for the console interface.<br>
     * The styles are used to set the colour of the text depending on the {@link LogTypes LogType}.
     * @see LogTypes
     */
    private void setupStyles() {
        for (LogTypes type : LogTypes.values()) {
            Style style = textPane.addStyle(type.name(), null);
            StyleConstants.setForeground(style, type.color);
            type.style = style;
        }
    }
    /**
     * Sets up the input field for the console interface.<br>
     * The input field is a {@link JTextField} that is used to enter commands.<br>
     * @return The input field.
     */
    private JTextField setupInputField() {
        JTextField textField = new JTextField();
        textField.setBackground(Color.BLACK);
        textField.setForeground(Color.GREEN);
        textField.setCaretColor(Color.GREEN);
        textField.addActionListener(e -> {
            String command = inputField.getText().trim(); // removes leading and trailing whitespace
            if (!command.isEmpty()) { // only executes when the inputField has something in it
                commandProcessor.processCommand(command);
                inputField.setText("");
            }
        });
        return textField;
    }
    // Methods | protected
    /**
     * Toggles the visibility of the console interface to {@code true} or {@code false}.<br>
     * If the console is already visible, it will be hidden and vice versa.
     */
    protected void toggleVisibility() {
        isVisible = !isVisible;
        frame.setVisible(isVisible);
    }
    /**
     * Appends text to the console with a timestamp and type.<br>
     * The text is appended to the end of the document with a newline character.<br>
     * The cursor is set to the end of the text.
     * @param text The text to append.
     * @param type The type of log.
     * @see LogTypes
     */
    protected void appendText(String text, LogTypes type) {
        try {
            String timeStamp;
            if (Game.gameTime != null) {
                timeStamp = String.format("%02d:%02d:%02d",Game.gameTime.getTimeHours(),Game.gameTime.getTimeMinutes(),Game.gameTime.getTimeSeconds());
            } else {
                timeStamp = "00:00:00";
            }
            document.insertString(document.getLength(), "[" + (++inputCounter) + "] [" + timeStamp +"] [" + type.name() + "] ", type.style);
            document.insertString(document.getLength(), text + "\n", type.style);
            textPane.setCaretPosition(textPane.getDocument().getLength()); // Sets the cursor to the end of the text
        } catch (Exception e) {
            Console.error("Error in console interface: " + e.getMessage()); // I recognise that if something goes very wrong, this will create an infinite recursive loops of error catches
        }
    }
}
