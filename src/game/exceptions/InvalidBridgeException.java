package game.exceptions;


import game.core.console.Console;

abstract class InvalidBridgeException extends Exception {
    public InvalidBridgeException(String message) {super(Console.exceptionMessage(message));}
}
