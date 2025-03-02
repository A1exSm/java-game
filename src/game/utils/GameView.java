package game.utils;
// Imports
import city.cs.engine.UserView;
import game.Game;
import game.GameWorld;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.Point2D;

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
        Game.gameTime = new GameTime();
        this.gameWorld = gameWorld;
    }
    // Methods
    @Override
    protected void paintBackground(Graphics2D g) {
        g.drawImage(background, 0, 0, this);
        // Drawing health bar
        int xPos = (int)Game.getFrameDimensions().x - 520;
        int healthPointWidth = 48;
        Image healthImage = new ImageIcon("data/Display_assets/health_bar/tile000.png").getImage();
        g.drawImage(healthImage, xPos, 0, 500, 50, this);
        for (int i = 0; i < gameWorld.getPlayer().getHealthPoints()/125; i++) {
            g.drawImage(new ImageIcon("data/Display_assets/health_bar/tile007.png").getImage(), xPos + healthPointWidth*i, 0, 500, 50, this);
        }
    }
    @Override
    protected void paintForeground(Graphics2D g) {
        g.setFont(STATUS_FONT);
        if (Game.gameTime.getTime() == 0) {
            g.setColor(Color.RED);
            g.drawString("LOADING...", 550, 315);
        } else {
            g.setColor(Color.BLUE);
            g.drawString(String.format("Timer: %02d" + ":%02d", Game.gameTime.getTimeMinutes(), Game.gameTime.getTimeSeconds()), 5, 20);
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
