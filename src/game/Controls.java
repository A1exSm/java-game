package game;
// Imports
import city.cs.engine.Body;
import game.body.walkers.PlayerWalker;
import city.cs.engine.CollisionListener;
import game.body.walkers.mobs.MobWalker;
import game.enums.Walkers;
import game.utils.GameView;
import org.jbox2d.common.Vec2;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
// Class
class Controls {
    // Fields
    private final GameView view;
    private final PlayerWalker player;
    private final GameWorld world;
    private int keyPressed;
    private int keyReleased;
    private CollisionListener surfaceListener;
    // Constructor
    protected Controls(GameWorld world, PlayerWalker player, GameView view) {
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
                    if (!player.isDead()) {
                        player.toggleOnAttack();
                    }
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
                    if (!player.isDead()) {
                        keyPressed = e.getKeyCode();
                        if (keyPressed == KeyEvent.VK_A) {
                            player.startWalking(-7);
                        } else if (keyPressed == KeyEvent.VK_D) {
                            player.startWalking(7);
                            // Hotswap keys (for quick assignment to test things)
                        } else if (keyPressed == KeyEvent.VK_1) {
                            Game.debugOn();
                        } else if (keyPressed == KeyEvent.VK_2) {
                            for (MobWalker mob : GameWorld.getMobs()) {
                                mob.toggleOnHit();
                            }
                        } else if (keyPressed == KeyEvent.VK_3) {
                            GameWorld.getMobs().forEach(MobWalker::toggleOnAttack);
                        } else if (keyPressed == KeyEvent.VK_4) {
                            for (MobWalker mob : GameWorld.getMobs()) {
                                if (mob.getWalkerType() == Walkers.WORM) mob.die();
                            }
                        } else if (keyPressed == KeyEvent.VK_5) {
                        } else if (keyPressed == KeyEvent.VK_6) {
                        } else if (keyPressed == KeyEvent.VK_7) {
                        } else if (keyPressed == KeyEvent.VK_8) {
                        } else if (keyPressed == KeyEvent.VK_SPACE || keyPressed == KeyEvent.VK_W) {
                            if (isOnSurface()) {
                                player.jump(10);
                            }
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

    private boolean isOnSurface() { // attempt at preventing jumping on surfaces, flawed cus we need the body in contacts half height
        for (Body body : player.getBodiesInContact()) {
            if (body.getPosition().y < player.getPosition().y-2) {
                return true;
            }
        }
        return false;
    }
}
