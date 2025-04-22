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
 * The console interface is interacted with using the {@link Console} class static methods.
 * @see LogTypes
 * @see Console
 */
class ConsoleInterface {
    // Fields
    private final JFrame frame;
    private boolean isVisible = false;
    private final StyledDocument document;
    private final JTextPane textPane;
    private final JTextField inputField;
    private int inputCounter = 0;
    private CommandProcessor commandProcessor;
    // Constructor
    ConsoleInterface() {
        frame = setupFrame();
        textPane = getTextPane();
        document = textPane.getStyledDocument();
        JScrollPane scrollPane = new JScrollPane(textPane);
        inputField = setupInputField();
        frame.getContentPane().add(scrollPane, BorderLayout.CENTER);
        frame.getContentPane().add(inputField, BorderLayout.SOUTH);
        setupStyles();
        commandProcessor = new CommandProcessor(this);
    }
    // Methods | Setup | private
    private JFrame setupFrame() {
        JFrame newFrame = new JFrame("Console Window");
        newFrame.setPreferredSize(new Dimension(700, 500));
        newFrame.setAutoRequestFocus(false); // don't want the window to gain focus when setVisible() is called.
        newFrame.pack();
        return newFrame;
    }
    private JTextPane getTextPane() {
        JTextPane textPane = new JTextPane();
        textPane.setEditable(false);
        textPane.setBackground(Color.BLACK);
        return textPane;
    }
    private void setupStyles() {
        for (LogTypes type : LogTypes.values()) {
            Style style = textPane.addStyle(type.name(), null);
            StyleConstants.setForeground(style, type.color);
            type.style = style;
        }
    }
    private JTextField setupInputField() {
        JTextField textField = new JTextField();
        textField.setBackground(Color.BLACK);
        textField.setForeground(Color.GREEN);
        textField.setCaretColor(Color.GREEN);
        textField.addActionListener(e -> {
            String command = inputField.getText().trim(); // removes leading and trailing whitespace
            if (!command.isEmpty()) { // only executes when there is text in the field
                commandProcessor.processCommand(command);
                inputField.setText("");
            }
        });
        return textField;
    }
    // Methods | protected
    protected void toggleVisibility() {
        isVisible = !isVisible;
        frame.setVisible(isVisible);
    }
    protected void appendText(String text, LogTypes type) {
        try {
            String timeStamp;
            if (Game.gameTime != null) {
                timeStamp = String.format("%02d:%02d",Game.gameTime.getTimeMinutes(),Game.gameTime.getTimeSeconds());
            } else {
                timeStamp = "00:00";
            }
            document.insertString(document.getLength(), "[" + (++inputCounter) + "] [" + timeStamp +"] [" + type.name() + "] ", type.style);
            document.insertString(document.getLength(), text + "\n", type.style);
            textPane.setCaretPosition(textPane.getDocument().getLength()); // Sets the cursor to the end of the text
        } catch (Exception e) {
            Console.error("Error in console interface: " + e.getMessage());
        }
    }
}
