package game.utils.sound;

/**
 * The SoundInterface defines the methods for various sound actions in the game.
 * Implementing classes should provide the specific sound effects for these actions.
 */
public interface SoundInterface {
    /**
     * Plays the sound for the first type of attack.
     */
    public void attack1();

    /**
     * Plays the sound for the second type of attack.
     */
    public void attack2();

    /**
     * Plays the sound for the third type of attack.
     */
    public void attack3();

    /**
     * Plays the sound for a {@link game.body.walkers.WalkerFrame WalkerFrame}'s death.
     */
    public void death();

    /**
     * Plays the sound for a {@link game.body.walkers.WalkerFrame WalkerFrame} falling.
     */
    public void fall();

    /**
     * Plays the sound for a {@link game.body.walkers.WalkerFrame WalkerFrame} getting hit.
     */
    public void hit();

    /**
     * Plays the sound for a {@link game.body.walkers.WalkerFrame WalkerFrame} jumping.
     */
    public void jump();

    /**
     * Plays the sound for a {@link game.body.walkers.WalkerFrame WalkerFrame} running.
     */
    public void run();
}