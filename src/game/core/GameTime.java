package game.core;

/**
 * The GameTime class manages the game's time using a javax.swing.Timer.
 */
public class GameTime {
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
        return (time / 60);
    }

    /**
     * Gets the total time in seconds.
     * @return The total time in seconds.
     */
    public int getTime() {
        return time;
    }
}