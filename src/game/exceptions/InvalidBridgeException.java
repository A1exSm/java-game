package game.exceptions;


import city.cs.engine.Body;
import game.body.staticstructs.ground.Bridge;
import game.body.staticstructs.ground.GroundFrame;

abstract class InvalidBridgeException extends Exception {
    public InvalidBridgeException(String message) {super(message);}
}
