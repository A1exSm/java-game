package game.exceptions;


import city.cs.engine.Body;
import game.body.staticstructs.ground.Bridge;
import game.body.staticstructs.ground.GroundFrame;

public class InvalidBridgeException extends RuntimeException {


    // constructor
    public InvalidBridgeException(String message) {super(message);}
    public InvalidBridgeException(float distance) {caseThree(distance);}
    public InvalidBridgeException(Body collisionBody) {caseFour(collisionBody.getName());}
    public InvalidBridgeException(String type, GroundFrame bridgeStart, GroundFrame bridgeEnd) {
        switch (type) {
            case "BRIDGE_STOP_START" -> {caseOne(bridgeStart, bridgeEnd);}
            case "BRIDGE_HEIGHT_MISMATCH" -> {caseTwo(bridgeStart, bridgeEnd);}
        }
    }

    private void caseOne(GroundFrame bridgeStart, GroundFrame bridgeEnd) {
        String type;
        String bridge;
        if (bridgeStart == null && bridgeEnd == null) {
            type = "BRIDGE_START_END_NULL";
            bridge = "Start and bridgeEnd";
        } else if (bridgeStart == null) {
            type = "BRIDGE_START_NULL";
            bridge = "Start";
        } else if (bridgeEnd == null) {
            type = "BRIDGE_END_NULL";
            bridge = "End";
        } else {
            throw new InvalidBridgeException("Bridge exception BRIDGE_STOP_START called on non-null bridgeStart and bridgeEnd");
        }
        throw new InvalidBridgeException(type + ": Bridge call's bridge" + bridge + " instance of abstract class GroundFrame is null");
    }

    private void caseTwo(GroundFrame bridgeStart, GroundFrame bridgeEnd) {
        if (bridgeStart.getYTop() == bridgeEnd.getYTop()) {
            throw new InvalidBridgeException("Bridge exception BRIDGE_HEIGHT_MISMATCH called on bridgeStart and bridgeEnd with equal yTop values");
        } else {
            throw new InvalidBridgeException("Bridge exception BRIDGE_HEIGHT_MISMATCH: Bridge yTop values do not match; (" + bridgeStart.getName() + ") " + bridgeStart.getYTop() + " != (" + bridgeEnd.getName() + ") " + bridgeEnd.getYTop());
        }
    }

    private void caseThree(float dist) {
        if (dist > Bridge.MAX_DISTANCE) {
            throw new InvalidBridgeException("Bridge exception BRIDGE_DISTANCE: Bridge distance exceeds maximum distance; " + dist + " > " + Bridge.MAX_DISTANCE);
        } else if (dist < Bridge.MIN_DISTANCE) {
            throw new InvalidBridgeException("Bridge exception BRIDGE_DISTANCE: Bridge distance is less than minimum distance; " + dist + " < " + Bridge.MIN_DISTANCE);
        } else {
            throw new InvalidBridgeException("Bridge exception BRIDGE_DISTANCE called on distance that is within the acceptable range");
        }
    }

    private void caseFour(String bodyName) {
        throw new InvalidBridgeException("Bridge exception BRIDGE_COLLISION: Body " + bodyName + " clips bridge!");
    }
}
