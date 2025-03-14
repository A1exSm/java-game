package game.utils.menu;
// Imports

import game.Game;
import game.core.GameView;
import javax.swing.*;
import javax.swing.border.EtchedBorder;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

// Class
public class MenuPanel extends JPanel {
    // Fields
    private static final int  WIDTH = 700;
    private static final int  HEIGHT = 480;
    private static final int X;
    private static final int Y;
    private boolean open = false;
    private Timer timer;
    JLabel backGround = new JLabel();

    static {
        X = (1200-WIDTH)/2;
        Y = (630-HEIGHT)/2 - 25;

    }
    // Constructor
    public MenuPanel(GameView gameView) {
        super();
        setLayout(null); // don't want to use a layout manager
        setBackground(new Color(0, 0, 0, 0));
        setBounds(X, Y, WIDTH, HEIGHT);
        setBorder(new EtchedBorder(EtchedBorder.RAISED));
//        add(new JTextField(), BorderLayout.CENTER);0
        setupTimer();
        gameView.add(this);
        createBackground(gameView);
        gameView.setComponentZOrder(backGround,1);
        gameView.setComponentZOrder(this, 0);
        setVisible(false);
    }
    // Methods

  private void addButtons() {
        int[] oneSquare = {81, 82, 30, 30}; // new width/height = oneSquare[2] + numSquares Sometimes :) requires tweaking
        JMenuButton button = new JMenuButton();
        add(button);
        button.setText("Continue");
        button.setFont(GameView.DISPLAY_FONT);
        button.setBounds(81, 82-32, 156, 62);
        button.addActionListener(e -> toggleMenu());
  }

  private void removeButtons() {
        for (Component comp : getComponents()) {
            if (comp instanceof JMenuButton) {
                remove(comp);
            }
        }
    }

    private void createBackground(GameView gameView) {
        gameView.add(backGround);
//        backGround.setIcon(new ImageIcon("data/Display_assets/GUI/Appear/open.png"));
        backGround.setBounds(10, -57,1000, 630);
    }


    public void toggleMenu() {
        if (!timer.isRunning()) { // means it cant be spammed
            if (!open) {
                open = true;
                backGround.setVisible(true);
                backGround.setIcon(new ImageIcon("data/Display_assets/GUI/Appearance/open.gif"));
                if (Game.gameWorld.isRunning()) {
                    Game.gameWorld.togglePause();
                }
                timer.start();
            } else {
                setVisible(false);
                removeButtons();
                open = false;
                backGround.setIcon(new ImageIcon("data/Display_assets/GUI/Appearance/close.gif"));
                timer.start();
            }
        }
    }


    private void setupTimer() {
        timer = new Timer(2300, e -> {
            if (open) {
                backGround.setIcon(new ImageIcon("data/Display_assets/GUI/Appearance/open.png"));
                addButtons();
                setVisible(true);
            } else {
                backGround.setVisible(false);
                backGround.setIcon(new ImageIcon());
                Game.gameWorld.togglePause();
            }
        });
        timer.setRepeats(false);
    }
    // Getters

    public boolean isOpen() {
        return open;
    }

}
