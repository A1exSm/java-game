package game.utils.menu;
// Imports

import game.Game;
import game.core.GameView;
import javax.swing.*;
import javax.swing.border.EtchedBorder;
import java.awt.*;

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
        setLayout(new GridLayout(5, 4));
        setBounds(X, Y, WIDTH, HEIGHT);
        setBackground(new Color(0,0,0,59));
        setBorder(new EtchedBorder(EtchedBorder.RAISED));
        gameView.setComponentZOrder(this, 0);
//        add(new JTextField(), BorderLayout.CENTER);0
        setupTimer();
        setVisible(true);
        createBackground(gameView);
        addButtons();
    }
    // Methods

  private void addButtons() {
      for (int i = 1; i <= 20; i++) {
          JMenuButton button = new JMenuButton("" + i);
          button.setVisible(true); // Ensure the button is visible
          add(button);
          setComponentZOrder(button, 0); // Set the Z-order of the button
      }
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
        gameView.setComponentZOrder(backGround, 1);
//        backGround.setIcon(new ImageIcon("data/Display_assets/GUI/Appear/open.png"));
        backGround.setBounds(10, -57,1000, 630);
    }


    public void toggleInventory() {
        if (!timer.isRunning()) { // means it cant be spammed
            if (!open) {
                open = true;
                backGround.setVisible(true);
                backGround.setIcon(new ImageIcon("data/Display_assets/GUI/Appearance/open.gif"));
                Game.gameWorld.togglePause();
                timer.start();
            } else {
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
//                addButtons();
            } else {
//                removeButtons();
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
