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
public final class Controls {
    // Fields
    private final GameView view;
    private final PlayerWalker player;
    private final GameWorld world;
    private int keyPressed;
    private int keyReleased;
    private boolean disabled = false;
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
        addViewScroller();
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
                // Binds which cannot be disabled
                if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                    view.toggleMenu();
                    return;
                } else if (e.getKeyCode() == KeyEvent.VK_F1) {
                    Console.toggleConsole();
                    return;
                }
                if (disabled) {return;}
                // Binds which can be disabled
                if (e.getKeyCode() == KeyEvent.VK_1) {
                    world.useInventoryItem(0);
                } else if (e.getKeyCode() == KeyEvent.VK_2) {
                    world.useInventoryItem(1);
                } else if (e.getKeyCode() == KeyEvent.VK_3) {
                    world.useInventoryItem(2);
                } else if (e.getKeyCode() == KeyEvent.VK_4) {
                    world.useInventoryItem(3);
                }
            }
        });
    }
    /**
     * Mouse input listener
     * Handles View Zooming and Player Attacking
     */
    private void addMouseInputs() {
        view.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (disabled || !world.isRunning() || player.isDead()) {return;}
                int buttonNum = e.getButton();
                if (buttonNum ==MouseEvent.BUTTON1) {
                    player.toggleOnAttack();
                } else if (buttonNum == MouseEvent.BUTTON2) {
                    view.setZoom(GameView.DEFAULT_ZOOM);
                }
            }
        });
    }
    /**
     * Adds a mouse wheel listener to the game view for zooming in and out.
     * The zoom level is adjusted based on the mouse wheel rotation.
     */
    private void addViewScroller() {
        view.addMouseWheelListener(e -> {
            if (disabled) {return;}
            if (e.getWheelRotation() > 0) {
                zoomIn();
            } else {
                zoomOut();
            }
        });
    }
    /**
     * decrements the zoom level of the game view by 1.
     */
    private void zoomIn() {
        if (view.getZoom() > 2) {
            view.setZoom(view.getZoom() - 1);
        }
    }
    /**
     * increments the zoom level of the game view by 1.
     */
    private void zoomOut() {
        view.setZoom(view.getZoom() + 1);
    }

    /**
     * Adds keyboard input listeners to the game view for movement controls.
     */
    private void addKeyboardInputs() {
        view.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (disabled || !world.isRunning() || player.isDead()) {return;}
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
            @Override
            public void keyReleased(KeyEvent e) {
                if (disabled || !world.isRunning() || player.isDead()) {return;}
                keyReleased = e.getKeyCode();
                if ((keyReleased == KeyEvent.VK_A && keyPressed != KeyEvent.VK_D) || (keyReleased == KeyEvent.VK_D && keyPressed != KeyEvent.VK_A)) { // ensures order logic
                    player.stopWalking();
                    player.setLinearVelocity(new Vec2(0, player.getLinearVelocity().y));
                }
            }
        });
    }
    /**
     * gets whether controls are disabled.
     * @return {@code true} if controls are disabled, {@code false} otherwise
     */
    public boolean isDisabled() {
        return disabled;
    }
    /**
     * sets whether controls are disabled.
     * @param disabled {@code true} to disable controls, {@code false} to enable them
     */
    public void setDisable(Boolean disabled) {
        this.disabled = disabled;
    }
}
