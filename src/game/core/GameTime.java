package game.core;

/**
 * The GameTime class manages the game's time using a javax.swing.Timer.
 *  @author Alexander Smolowitz, alexander.smolowitz@city.ac.uk
 *  @since 05-02-2025
 */
public final class GameTime {
    // Fields
    /**
     * The current time in seconds.
     */
    private int time;
    /**
     * The timer to increment {@link #time}.
     */
    private javax.swing.Timer timer;
    /**
     * Constructor for GameTime.
     * Initializes and starts the timer.
     */
    public GameTime() {
        startTimer();
        timer.setRepeats(true);
        timer.start();
    }
    /**
     * Starts the timer with a delay of 1 second (1000 milliseconds).
     * Increments the time field by 1 every second.
     */
    private void startTimer() {
        timer = new javax.swing.Timer(1000, e -> time++);
    }
    /**
     * Toggles the timer between running and stopped states.
     */
    public void toggleTimer() {
        if (timer.isRunning()) timer.stop();
        else timer.start();
    }
    /**
     * Gets the current time in seconds.
     * @return The current time in seconds (0-59).
     */
    public int getTimeSeconds() {
        return (time % 60);
    }
    /**
     * Gets the current time in minutes.
     * @return The current time in minutes.
     */
    public int getTimeMinutes() {
        return (time / 60) % 60;
    }
    /**
     * Gets the current time in units of hours.
     * @return The current time in hours.
     */
    public int getTimeHours() {
        return time / 3600;
    }
    /**
     * Gets the total time in seconds.
     * @return The total time in seconds.
     * @deprecated due to more need specific methods
     * such as {@link #getTimeSeconds()}, {@link #getTimeMinutes()},
     * and {@link #getTimeHours()}.
     */
    @Deprecated
    public int getTime() {
        return time;
    }
}