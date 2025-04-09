package game.utils;
// Imports
import game.core.GameWorld;
import game.body.walkers.PlayerWalker;
import game.core.GameView;
import game.core.console.Console;
import org.jbox2d.common.Vec2;

import java.awt.event.*;
// Class
/**
 * Controls class handles the main keyboard and mouse inputs for the game.
 */
public class Controls {
    // Fields
    private final GameView view;
    private final PlayerWalker player;
    private final GameWorld world;
    private int keyPressed;
    private int keyReleased;
    // Constructor
    /**
     * Constructor for Controls.<br>
     * Initialises required Listeners and fields.
     *
     * @param world the game world
     * @param player the player character
     * @param view the game view
     */
    public Controls(GameWorld world, PlayerWalker player, GameView view) {
        this.view = view;
        this.player = player;
        this.world = world;
        addKeyboardInputs();
        addMouseInputs();
        setupKey();
    }
    // Methods
    /**
     * Setup for non-movement key bindings.<br>
     * Sets up key bindings for inventory and menu controls.
     */
    public void setupKey() {
        view.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                    view.jMenuPanel.toggleMenu();
                } else if (e.getKeyCode() == KeyEvent.VK_1) {
                    GameWorld.useInventoryItem(0);
                } else if (e.getKeyCode() == KeyEvent.VK_2) {
                    GameWorld.useInventoryItem(1);
                } else if (e.getKeyCode() == KeyEvent.VK_3) {
                    GameWorld.useInventoryItem(2);
                } else if (e.getKeyCode() == KeyEvent.VK_4) {
                    GameWorld.useInventoryItem(3);
                } else if (e.getKeyCode() == KeyEvent.VK_F1) {
                    Console.toggleConsole();
                }
            }
        });
    }
    /**
     * Mouse input listener for attacking.
     */
    private void addMouseInputs() {
        view.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (world.isRunning()) {
                    if (!player.isDead()) {
                        player.toggleOnAttack();
                    }
                }
            }
        });
    }
    /**
     * Adds keyboard input listeners to the game view for movement controls.
     */
    private void addKeyboardInputs() {
        view.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (world.isRunning()) {
                    if (!player.isDead()) {
                        keyPressed = e.getKeyCode();
                        if (keyPressed == KeyEvent.VK_A) {
                            player.startWalking(-7);
                        } else if (keyPressed == KeyEvent.VK_D) {
                            player.startWalking(7);
                            // Hotswap keys (for quick assignment to test things)
                        } else if (keyPressed == KeyEvent.VK_SPACE || keyPressed == KeyEvent.VK_W) {
                            player.jump(0);
                        }
                    }
                }
            }
            @Override
            public void keyReleased(KeyEvent e) {
                if (world.isRunning()) {
                    if (!player.isDead()) {
                        keyReleased = e.getKeyCode();
                        if ((keyReleased == KeyEvent.VK_A && keyPressed != KeyEvent.VK_D) || (keyReleased == KeyEvent.VK_D && keyPressed != KeyEvent.VK_A)) { // ensures order logic
                            player.stopWalking();
                            player.setLinearVelocity(new Vec2(0, player.getLinearVelocity().y));
                        }
                    }
                }
            }
        });
    }
}
