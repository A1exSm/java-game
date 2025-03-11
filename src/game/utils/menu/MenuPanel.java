package game.utils.menu;
// Imports

import game.Game;
import game.core.GameView;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.plaf.BorderUIResource;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

// Class
public class MenuPanel extends JPanel {
    // Fields
    private static final int  WIDTH = 650;
    private static final int  HEIGHT = 410;
    private static final int X;
    private static final int Y;
    private static final ArrayList<Image> appearances = new ArrayList<>();
    private int currentFrame = 0;
    private boolean open = false;
    private Timer timer;

    static {
        X = (1200-WIDTH)/2;
        Y = (630-HEIGHT)/2 - 25;

        for (int i = 0; i <= 11; i++) { // 11 is the number of menu appear images
            appearances.add(getImage("data/Display_assets/GUI/Appear/" + i + ".png"));
        }
    }
    // Constructor
    public MenuPanel(GameView gameView) {
        super();
        setBounds(X, Y, WIDTH, HEIGHT);
        setBackground(new Color(0,0,0,0));
//        add(new JTextField(), BorderLayout.CENTER);
        setupTimer();
        setVisible(true);
    }
    // Methods
    public void open() {
        if (!open) {
            open = true;
            timer.start();
        }
    }


    private void setupTimer() {
        timer = new Timer(300, e -> {
            if (open) {}
        });
        timer.setRepeats(true);
    }
    // Getters
    public static int getWIDTH() {
        return WIDTH;
    }
    public static int getHEIGHT() {
        return HEIGHT;
    }

    private static Image getImage(String path) {
        ImageIcon icon = new ImageIcon(path);
        return icon.getImage().getScaledInstance((int) (800*1.5f), (int) (600*1.5f), Image.SCALE_DEFAULT);
    }
    public Image getImage() {
        return appearances.get(currentFrame);
    }

    public boolean isOpen() {
        return open;
    }

}
