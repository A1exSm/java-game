package game.utils;
// Imports
import city.cs.engine.UserView;
import game.GameWorld;
import javax.swing.*;
import java.awt.*;
// Class
public class GameView extends UserView {
    // Fields
    private final Image background = new ImageIcon("data/sky.png").getImage();
    private final GameWorld gameWorld;
    public static final Font STATUS_FONT = new Font("Monospaced", Font.PLAIN, 20);
    private boolean gameOver = false;
    // Constructor
    public GameView(GameWorld gameWorld, int width, int height) {
        super(gameWorld, width, height);
        requestFocus();
        setFocusable(true);
        GameWorld.gameTime = new GameTime();
        this.gameWorld = gameWorld;
    }
    // Methods
    @Override
    protected void paintBackground(Graphics2D g) {
//        background = background.getScaledInstance(800, 600, Image.SCALE_DEFAULT); // IDK how this works :( can't get it to work
        g.drawImage(background, 0, 0, this);
    }
    @Override
    protected void paintForeground(Graphics2D g) {
        g.setFont(STATUS_FONT);
        if (GameWorld.gameTime.getTime() == 0) {
            g.setColor(Color.RED);
            g.drawString("LOADING...", 550, 315);
        } else {
            g.setColor(Color.BLUE);
            g.drawString(String.format("Timer: %02d" + ":%02d", GameWorld.gameTime.getTimeMinutes(), GameWorld.gameTime.getTimeSeconds()), 5, 20);
        }
        if (gameOver) {
            g.setColor(Color.RED);
            g.setFont(new  Font("Niagara Solid", Font.BOLD, 50));
            g.drawString("GAME OVER", 520, 250);
            gameWorld.togglePause();
        }
//        g.drawString(String.format("GameTime: %02d",GameWorld.gameTime.getTimeSeconds()), a5, 50);
    }

    public void gameOver() {
        gameOver = true;
    }
}
