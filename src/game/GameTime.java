package game;


public class GameTime {
    // fields
    private int time;
    private javax.swing.Timer timer;
    // constructor
    public GameTime() {
        startTimer();
        timer.setRepeats(true);
        timer.start();
    }
    // setup methods
    private void startTimer() {
        // 0.1s
        timer = new javax.swing.Timer(1000, e -> time++);
    }
    // getters
    public int getTimeSeconds() {
        return (time%60);
    }
    public int getTimeMinutes() {
        return (time/60);
    }
    // control
    public void toggleTimer() {
        if (timer.isRunning()) timer.stop();
        else timer.start();
    }
}
