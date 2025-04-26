package game.enums;
/**
 * The different states of walkers in the game.
 * These are the core of the Finite State Machine (FSM)
 * of which walkers operate under.
 */
public enum State {
    /**
     * The idle state of the walker.
     * This is the default state of the walker.
     */
    IDLE,
    /**
     * The walking state of the walker.
     * This is the state in which the walker moves.
     */
    RUN,
    /**
     * The jumping state of the walker.
     * This is the state in which the walker jumps.
     */
    JUMP,
    /**
     * The falling state of the walker.
     * This is the state in which the walker falls.
     */
    FALL,
    /**
     * The hurt state of the walker.
     * This is the state in which the walker is hit.
     */
    HIT,
    /**
     * The first attack state of the walker.
     * This is the state in which the walker attacks.
     */
    ATTACK1,
    /**
     * The second attack state of the walker.
     * This is the state in which the walker attacks.
     */
    ATTACK2,
    /**
     * The third attack state of the walker.
     * This is the state in which the walker attacks.
     */
    ATTACK3,
    /**
     * The death state of the walker.
     * This is the state in which the walker dies.
     */
    DEATH
}
