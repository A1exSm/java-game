package game;

import city.cs.engine.UserView;

import javax.swing.*;
import java.awt.*;

class GameView extends UserView {
    private final Image background = new ImageIcon("data/sky.png").getImage();

    protected GameView(GameWorld gameWorld, int width, int height) {
        super(gameWorld, width, height);
        requestFocus();
        setFocusable(true);
        GameWorld.gameTime = new GameTime();
    }


    @Override
    protected void paintBackground(Graphics2D g) {
//        background = background.getScaledInstance(800, 600, Image.SCALE_DEFAULT); // IDK how this works :( can't get it to work
        g.drawImage(background, 0, 0, this);
    }
    public static final Font STATUS_FONT = new Font("Monospaced", Font.PLAIN, 20);

    @Override
    protected void paintForeground(Graphics2D g) {
        g.setColor(Color.BLUE);
        g.setFont(STATUS_FONT);
        g.drawString(String.format("Timer: %02d" + ":%02d", GameWorld.gameTime.getTimeMinutes(), GameWorld.gameTime.getTimeSeconds()), 5, 20);
//        g.drawString(String.format("GameTime: %02d",GameWorld.gameTime.getTimeSeconds()), 5, 50);
    }
}
