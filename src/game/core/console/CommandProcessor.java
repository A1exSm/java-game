package game.core.console;
import game.Game;
import game.enums.Commands;
import game.enums.LogTypes;
/**
 * CommandProcessor is responsible for processing commands entered into the console.
 * It validates the command, executes it, and provides feedback to the user.
 * @author Alexander Smolowitz, alexander.smolowitz@city.ac.uk
 * @since 22-04-2025
 */
final class CommandProcessor {
    private final ConsoleInterface console;
    /**
     * Constructor for CommandProcessor.
     * @param console The console interface to interact with.
     */
    CommandProcessor(ConsoleInterface console) {
        this.console = console;
    }
    /**
     * Processes the command entered by the user.
     * It validates the command, executes it, and provides feedback.
     * @param command The command entered by the user.
     */
    protected void processCommand(String command) {
        if (responseMessage(command, isValid(command))) {
            executeCommand(command, getCommandType(command));
        }
    }
    /**
     * Gives the LogTypes of the command:
     * Either {@link LogTypes#RESPONSE} or {@link LogTypes#INVALID}
     * @param command The command to validate.
     * @return LogTypes indicating whether the command is valid or invalid.
     */
    private LogTypes isValid(String command) {
        for (Commands cmd : Commands.values()) {
            if (command.toLowerCase().startsWith(cmd.name().toLowerCase())) {
                return LogTypes.RESPONSE;
            }
        }
        return LogTypes.INVALID;
    }
    /**
     * Gives a boolean value for if the command is valid,
     * and provides feedback to the user.
     * @param command The command entered by the user.
     * @param inputType The type of input (valid or invalid).
     * @return boolean indicating whether the command was valid or not.
     */
    private boolean responseMessage(String command, LogTypes inputType) {
        if (inputType == LogTypes.INVALID) {
            console.appendText("'" + command + "' is not an accepted command!" + " To see the list of accepted commands, type 'help'", LogTypes.INVALID);
            return false;
        } else {
            console.appendText(command, LogTypes.INPUT);
            return true;
        }
    }
    /**
     * Determines the command type based on the command string.
     * If the command is not recognised, it defaults to {@link Commands#HELP}.
     * Defaulting to help means that the user will be able to see the list of commands.
     * @param command The command string entered by the user.
     * @return The corresponding Commands enum value.
     */
    private Commands getCommandType(String command) {
        for (Commands cmd : Commands.values()) {
            if (command.toLowerCase().startsWith(cmd.name().toLowerCase())) {
                return cmd;
            }
        }
        return Commands.HELP; // Default to HELP if no match is found`
    }
    /**
     * Executes the command based on its {@link Commands} enum.
     * @param command The command string entered by the user.
     * @param commandType The type of command to execute
     * @see Commands
     */
    private void executeCommand(String command, Commands commandType) {
        switch (commandType) {
            case HELP -> help();
            case DEBUG -> debug(command);
            case LEVEL_STATUS -> levelStatus();
            default -> console.appendText("Command Processing Error!", LogTypes.ERROR);
        }
    }
    /**
     * Lists all the accepted commands and their descriptions.
     */
    private void help() {
        StringBuilder helpMessage = new StringBuilder("The following commands are accepted:");
        for (Commands cmd : Commands.values()) {
            helpMessage.append("\n\t");
            helpMessage.append(" - [   ] '");
            helpMessage.append(cmd.name().toLowerCase());
            helpMessage.append("'");
        }
        console.appendText(helpMessage.toString(), LogTypes.HELP);
    }
    /**
     * Toggles the debug mode on or off based on the command.
     * The command String is used to extract the last word to determine the action.
     * @param command The command string entered by the user.
     */
    private void debug(String command) {
        if (!command.toLowerCase().endsWith("on") && !command.toLowerCase().endsWith("off")) {
            console.appendText(command + " is not valid! DEBUG must end with 'on' or 'off'", LogTypes.INVALID);
            return;
        }
        if (Game.gameWorld == null) {
            console.appendText("Game world is not initialised! Please only attempt once a level has started!", LogTypes.ERROR);
            return;
        }
        if (command.toLowerCase().endsWith("on")) {
            if (Game.isDebugOn()) {
                console.appendText("Debug mode is already ON", LogTypes.DEBUG);
            } else {
                Game.debugOn();
                console.appendText("Debug mode is now ON", LogTypes.DEBUG);
            }
        } else {
            if (Game.isDebugOn()) {
                Game.debugOn();
                console.appendText("Debug mode is now OFF", LogTypes.DEBUG);
            } else {
                console.appendText("Debug mode is already OFF", LogTypes.DEBUG);
            }
        }
    }
    /**
     * Displays the current level status.
     */
    private void levelStatus() {
        if (Game.gameWorld != null) {
            console.appendText("Level Status: " + Game.gameWorld.getLevel(), LogTypes.INFO);

        } else {
            console.appendText("Game.gameWorld is not initialised! Please only attempt once a level has started!", LogTypes.ERROR);
        }
    }
}
