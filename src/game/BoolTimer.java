package game;
// Class
public class BoolTimer {
    // Fields
    private boolean bool;
    private final javax.swing.Timer timer;
    // Constructor
    public BoolTimer(double seconds) {
        bool = true;
        timer = new javax.swing.Timer((int) (seconds*1000), e -> { // cast double to int and used lambda so we don't have to import :)
            bool = false;
        });
        timer.setRepeats(false);
        timer.start();
    }
    // Methods
    public boolean getState() {
        return bool;
    }
    public void stop() {
        timer.stop();
    }
}
