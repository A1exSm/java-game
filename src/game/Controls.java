package game;
// Imports
import org.jbox2d.common.Vec2;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
// Class
class Controls {
    // Fields
    private final GameView view;
    private final Player player;
    private final GameWorld world;
    private int keyPressed;
    private int keyReleased;
    // Constructor
    protected Controls(GameWorld world, Player player, GameView view) {
        this.view = view;
        this.player = player;
        this.world = world;
        addKeyboardInputs();
        addMouseInputs();
    }
    // Methods
    private void addMouseInputs() {
        view.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {}
            @Override
            public void mousePressed(MouseEvent e) {
                if (world.isRunning()) {
                    player.attack();
                }
            }
            @Override
            public void mouseReleased(MouseEvent e) {}
            @Override
            public void mouseEntered(MouseEvent e) {}
            @Override
            public void mouseExited(MouseEvent e) {}
        });
    }
    private void addKeyboardInputs() {
        view.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (world.isRunning()) {
                    keyPressed = e.getKeyCode();
                    if (keyPressed == KeyEvent.VK_A) {
                        player.startWalking(-7);
                    } else if (keyPressed == KeyEvent.VK_D) {
                        player.startWalking(7);
                    } else if (keyPressed == KeyEvent.VK_1) {
                        world.debugOn();
                    } else if (keyPressed == KeyEvent.VK_SPACE || keyPressed == KeyEvent.VK_W) {
                        player.jump(10);
                    }
                }
            }
            @Override
            public void keyReleased(KeyEvent e) {
                if (world.isRunning()) {
                    keyReleased = e.getKeyCode();
                    if ((keyReleased == KeyEvent.VK_A && keyPressed != KeyEvent.VK_D) || (keyReleased == KeyEvent.VK_D && keyPressed != KeyEvent.VK_A)) { // ensures order logic
                        player.stopWalking();
                        player.setLinearVelocity(new Vec2(0, player.getLinearVelocity().y));
                    }
                }
            }
        });
    }
    private void jump() {
        // will check if player's collision normal's y != 0 i.e the player is standing on something, thus can jump
    }
}
