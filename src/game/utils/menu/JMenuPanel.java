package game.utils.menu;
// Imports

import game.Game;
import game.core.GameView;
import javax.swing.*;
import javax.swing.border.EtchedBorder;
import java.awt.*;

// Class
public class JMenuPanel extends JPanel {
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
    public JMenuPanel(GameView gameView) {
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
        MenuJButton cont = new MenuJButton(this,"Continue", new int[] {81, 50, 156, 62}, true);
        MenuJButton quit = new MenuJButton(this,"Quit", new int[] {81, 144, 156, 62}, true);
        MenuSliderSurface slider = new MenuSliderSurface(this, new int[] {302, 50, 313, 62});
        cont.addActionListener(e -> toggleMenu());
        quit.addActionListener(e -> Game.exit());
  }

  private void removeButtons() {
        for (Component comp : getComponents()) {
            if (comp instanceof MenuJButton) {
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

    protected static void boundErrorHandler(JComponent component, int[] bounds) {
        try {
            component.setBounds(bounds[0], bounds[1], bounds[2], bounds[3]);
        } catch (ArrayIndexOutOfBoundsException e) {
            System.err.println("Error: Bounds array must have 4 elements! Setting bounds to default.");
            component.setBounds(81, 82, 30, 30);
        }
    }
}
