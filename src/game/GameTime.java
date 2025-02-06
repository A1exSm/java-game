package game;
// Class
public class GameTime {
    // Fields
    private int time;
    private javax.swing.Timer timer;
    // Constructor
    public GameTime() {
        startTimer();
        timer.setRepeats(true);
        timer.start();
    }
    // Methods
    private void startTimer() {
        // 0.1s
        timer = new javax.swing.Timer(1000, e -> time++);
    }
    public void toggleTimer() {
        if (timer.isRunning()) timer.stop();
        else timer.start();
    }
    // Getters
    public int getTimeSeconds() {
        return (time%60);
    }
    public int getTimeMinutes() {
        return (time/60);
    }
}
