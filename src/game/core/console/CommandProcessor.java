package game.core.console;

import game.Game;
import game.enums.Commands;
import game.enums.LogTypes;

import java.util.HashMap;

class CommandProcessor {
    private final ConsoleInterface console;
    CommandProcessor(ConsoleInterface console) {
        this.console = console;
    }

    protected void processCommand(String command) {
        if (responseMessage(command, isValid(command))) {
            executeCommand(command, getCommandType(command));
        }
    }

    private LogTypes isValid(String command) {
        for (Commands cmd : Commands.values()) {
            if (command.toLowerCase().startsWith(cmd.name().toLowerCase())) {
                return LogTypes.RESPONSE;
            }
        }
        return LogTypes.INVALID;
    }
    private boolean responseMessage(String command, LogTypes inputType) {
        if (inputType == LogTypes.INVALID) {
            console.appendText("'" + command + "' is not an accepted command!" + " To see the list of accepted commands, type 'help'", LogTypes.INVALID);
            return false;
        } else {
            console.appendText(command, LogTypes.INPUT);
            return true;
        }
    }

    private Commands getCommandType(String command) {
        for (Commands cmd : Commands.values()) {
            if (command.toLowerCase().startsWith(cmd.name().toLowerCase())) {
                return cmd;
            }
        }
        return Commands.HELP; // Default to HELP if no match is found`
    }

    private void executeCommand(String command, Commands commandType) {
        switch (commandType) {
            case HELP -> { help(commandType); }
            case DEBUG -> {debug(command);}
            case LEVEL_STATUS -> {levelStatus();}
            default -> {console.appendText("Command Processing Error!", LogTypes.ERROR);}
        }
    }

    private void help(Commands commandType) {
        String helpMessage = "The following commands are accepted:";
        for (Commands cmd : Commands.values()) {
            helpMessage = helpMessage + "\n\t" + "- '" + cmd.name().toLowerCase() + "'";
        }
        console.appendText(helpMessage, LogTypes.HELP);
    }

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

    private void levelStatus() {
        if (Game.gameWorld != null) {
            console.appendText("Level Status: " + Game.gameWorld.getLevel(), LogTypes.INFO);

        } else {
            console.appendText("Game.gameWorld is not initialised! Please only attempt once a level has started!", LogTypes.ERROR);
        }
    }
}
