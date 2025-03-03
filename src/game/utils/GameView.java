package game.utils;
// Imports
import city.cs.engine.UserView;
import game.Game;
import game.GameWorld;
import javax.swing.*;
import java.awt.*;

// Class
public class GameView extends UserView {
    // Fields
    private final Image background = new ImageIcon("data/sky.png").getImage();
    public static final Font STATUS_FONT = new Font("Monospaced", Font.PLAIN, 20);
    private boolean gameOver = false;
    private final GameWorld gameWorld;
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
        // Health bar dimensions and position
        int xPos = (int)Game.getFrameDimensions().x - 490;
        int healthBarWidth = 480;
        int healthBarHeight = 35;
        int borderThickness = 3;
        // Calculate health percentage
        double healthPercentage = (double) gameWorld.getPlayer().getHealthPoints() / gameWorld.getPlayer().getMaxHealth();
        int filledWidth = (int)(healthPercentage * (healthBarWidth-82));
        String currentColour = getColour(healthBarWidth);
        // Drawing health bar base colour
        g.setColor(getRGBValues(currentColour, 1));
        g.fillRect((xPos+38) + borderThickness, 10 + borderThickness, filledWidth, healthBarHeight-10);
        // Drawing health bar shadow colour
        g.setColor(getRGBValues(currentColour, 2));
        g.fillRect((xPos+37) + borderThickness, 25 + borderThickness, filledWidth, healthBarHeight-30);
        // Drawing the health bar border image
        g.drawImage(new ImageIcon("data/Display_assets/health_bar/tile000.png").getImage(), xPos, 10, healthBarWidth, healthBarHeight, this);
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

    private String getColour(double healthPercentage) {
        if (healthPercentage <= 0.3) {
            return "RED";
        } else if (healthPercentage <= 0.6) {
            return "YELLOW";
        } else {
            return "GREEN";
        }
    }

    private Color getRGBValues(String colour, int barNum) {
        if (barNum == 1) {
            switch (colour) {
                case "RED" -> {return new Color(200, 40, 40);}
                case "YELLOW" -> {return new Color(240, 180, 50);}
                case "GREEN" -> {return new Color(50, 200, 100);}
                default -> {
                    System.out.println("Invalid colour in GameView.getRGBValues! Returning default");
                    return new Color(200, 40, 40);
                }
            }
        } else {
            switch (colour) {
                case "RED" -> {return new Color(140, 30, 30);}
                case "YELLOW" -> {return new Color(180, 140, 40);}
                case "GREEN" -> {return new Color(30, 140, 70);}
                default -> {
                    System.out.println("Invalid colour in GameView.getRGBValues! Returning default");
                    return new Color(140, 30, 30);
                }
            }
        }
    }

    public void gameOver() {
        gameOver = true;
    }
}
