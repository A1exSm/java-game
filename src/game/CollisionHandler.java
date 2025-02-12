package game;// Imports

import city.cs.engine.CollisionEvent;
import city.cs.engine.CollisionListener;
import game.body.walkers.WalkerFrame;

// Class
public class CollisionHandler implements CollisionListener {
    // Fields
    WalkerFrame walker;
    // Constructor
    public CollisionHandler(WalkerFrame walker) {
        this.walker = walker;
        walker.addCollisionListener(this);
    }
    // Override Methods
    @Override
    public void collide(CollisionEvent e) {
        isOnSurface(e);
    }
    // Methods
    private void isOnSurface(CollisionEvent e) {
//        walker.isOnSurface = e.getNormal().y != 0;
    }
}
