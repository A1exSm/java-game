package game.menu;
// Imports

import game.Game;
import game.core.GameSound;
import game.core.GameView;
import game.core.console.Console;
import game.enums.SoundGroups;
import javax.swing.*;
import javax.swing.border.EtchedBorder;
import java.awt.*;

// Class
/**
 * The JMenuPanel class extends {@link JPanel} to create a custom menu panel for the game.<br>
 * It includes buttons for Continue and Quit, sliders for game music and sound, and a background image.
 */
public class JMenuPanel extends JPanel {
    // Fields
    private static final int  WIDTH = 700;
    private static final int  HEIGHT = 480;
    private static final int X;
    private static final int Y;
    private boolean open = false;
    private Timer timer;
    JLabel backGround = new JLabel();
    private final GameSound openSound = GameSound.createSound("data/Audio/UI/open.wav", SoundGroups.UI, 667);
    public static final Font MAC_FONT = new Font("Niagara Solid", Font.BOLD, 20);
    public static final Font OPTIONS_FONT = new Font("Blackadder ITC", Font.ITALIC, 72);

    static {
        X = (1200-WIDTH)/2;
        Y = (630-HEIGHT)/2 - 25;

    }
    // Constructor
    /**
     * Constructor for {@link JMenuPanel}.
     * Initializes the panel with a specified GameView, sets layout, background, bounds, and border.
     * Adds the panel and background to the GameView and sets their Z-order.
     * @param gameView The GameView to which this panel will be added.
     */
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
        addButtons();
        setVisible(false);
    }
    // Methods
    /**
     * Adds buttons for Continue and Quit, and sliders for game music and sound to the panel.
     * Sets action listeners for the buttons.
     */
  private void addButtons() {
      // note 1 square is 31 units
      new JLabelTitle(this, 333, 50, 251, 157, "Options");
      MenuJButton cont = new MenuJButton(this,"Continue", new int[] {81, 50, 156, 62}, true);
      MenuJButton quit = new MenuJButton(this,"Quit", new int[] {81, 144, 156, 62}, true);
      new MenuSliderSurface(this, 302, 239, 313, 62, Game.getGameMusic(), "Music");
      new MenuSliderSurface(this, 302, 334, 313, 62, SoundGroups.MOBS, "Mob SFX");
      cont.addActionListener(e -> toggleMenu());
      quit.addActionListener(e -> Game.exit());
  }

  /**
   * Hides Components of JPanel except for the background.
   * @see JPanel
   */
  private void hideInterface() {
      for (Component component : getComponents()) {
          if (component != backGround) {
              component.setVisible(false);
          }
      }
  }

  /**
   * Reveals Components of JPanel except for the background.
   * @see JPanel
   */
  private void revealInterface() {
      for (Component component : getComponents()) {
          if (component != backGround) {
              component.setVisible(true);
          }
      }
      }

    /**
     * Creates the background for the panel and adds it to the GameView.
     * @param gameView The GameView to which the background will be added.
     */
    private void createBackground(GameView gameView) {
        gameView.add(backGround);
        backGround.setBounds(10, -57,1000, 630);
    }

    /**
     * Toggles the visibility of the menu.
     * Plays the open sound, sets the background image, and pauses/resumes the game.
     */
    public void toggleMenu() {
        if (!timer.isRunning()) { // means it cant be spammed
            openSound.play();
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
                hideInterface();
                open = false;
                backGround.setIcon(new ImageIcon("data/Display_assets/GUI/Appearance/close.gif"));
                timer.start();
            }
        }
    }

    /**
     * Sets up the timer for the menu.
     * The timer controls the visibility and background image of the menu.
     */
    private void setupTimer() {
        timer = new Timer(2300, e -> {
            if (open) {
                backGround.setIcon(new ImageIcon("data/Display_assets/GUI/Appearance/open.png"));
                revealInterface();
                openSound.stop();
                setVisible(true);
            } else {
                backGround.setVisible(false);
                backGround.setIcon(new ImageIcon());
                openSound.stop();
                Game.gameWorld.togglePause();
            }
        });
        timer.setRepeats(false);
    }

    /**
     * Handles errors when setting bounds for a component.
     * If the {@code bounds} array does not have 4 elements, throws an IllegalArgumentException.
     * @param component The component to set bounds for.
     * @param bounds The {@code bounds} array.
     */
    public static void boundErrorHandler(JComponent component, int[] bounds) {
        try {
            component.setBounds(bounds[0], bounds[1], bounds[2], bounds[3]);
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new IllegalArgumentException(Console.exceptionMessage("Bounds array must have 4 elements!"));
        }
    }
}
