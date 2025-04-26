package game.core;
// Imports
import city.cs.engine.UserView;
import game.Game;
import game.body.walkers.PlayerWalker;
import game.core.console.Console;
import game.enums.Environments;
import game.levels.LevelFrame;
import game.utils.InventoryButton;
import game.menu.JMenuPanel;
import game.utils.ToMenuButton;
import org.jbox2d.common.Vec2;
import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.util.ArrayList;

// Class
/**
 * Extends {@link UserView} and provides custom rendering for the game.
 * It handles the background, foreground, and various UI elements such as the health bar and inventory.
 *  @author Alexander Smolowitz, alexander.smolowitz@city.ac.uk
 *  @since 03-02-2025
 */
public final class GameView extends UserView {
    // Fields
    /**
     * Font for time Status <br>
     * {@code Name}: Monospaced<br>
     * {@code Style}: {@link Font#PLAIN}<br>
     * {@code Size}: 20
     * @deprecated Time Status is not used any more
     * due to game time being logged in the more robust Console system,
     * where time is logged for each appended message.
     * @see game.core.console.Console
     */
    @Deprecated
    public static final Font STATUS_FONT = new Font("Monospaced", Font.PLAIN, 20);
    /**
     * {@code Name}: Niagara Solid<br>
     * {@code Style} {@link Font#BOLD}<br>
     * {@code Size}: 50
     */
    public static final Font DISPLAY_FONT = new  Font("Niagara Solid", Font.BOLD, 50);
    /**
     * The default zoom level for the game view,
     * used to reset default by calling {@link game.core.GameView#setZoom(float) setZoom()}.
     */
    public static final float DEFAULT_ZOOM = 20.0f;
    private JLabel endText;
    private final Environments environment;
    private static final ArrayList<int[]> slotLocations = new ArrayList<>(); // format: {{x,y,width,height}},{{x,y},{{x,y,width,height}}...
    private final JMenuPanel jMenuPanel = new JMenuPanel(this);
    private final Image parallaxBackground;
    private final Image parallaxForeground;
    private final Image sea;
    private final ArrayList<InventoryButton> inventoryButtons = new ArrayList<>();
    private String gameOverMessage;
    private String victoryMessage;
    private boolean blackScreenDeath = false;
    private final GameWorld gameWorld;
    static {
        slotLocations.add(new int[]{ 800, 533, 400, 100}); //        slotLocations.add(new int[]{x, y, width, height});
        slotLocations.add(new int[]{ 802, 538, 395, 90}); //        slotLocations.add(new int[]{x + 2, y + 35, width-10, height-10});
        slotLocations.add(new int[]{ 810, 543, 80, 80}); //       slotLocations.add(new int[]{10 + x, y + 40, (width-20)/5, height-20});
        slotLocations.add(new int[]{ 894, 543, 80, 80}); //        slotLocations.add(new int[]{13 + x + (width+5)/5, y+40, (width-20)/5, height-20});
        slotLocations.add(new int[]{ 978, 543, 80, 80}); //        slotLocations.add(new int[]{16 + x + ((width+5)/5)*2, y+40, (width-20)/5, height-20});
        slotLocations.add(new int[]{ 1062, 543, 80, 80}); //        slotLocations.add(new int[]{19 + x + ((width+5)/5)*3, y+40, (width-20)/5, height-20});
    }
    // Constructor
    /**
     * Initialises {@link Game#gameTime},
     * Renders parallax images for the given environment. <br>
     * also adds a focus listener to the game view to stop the player when the view loses focus.
     *
     * @param gameWorld the game world to be displayed
     * @param environment the environment
     * @see Environments
     */
    public GameView(GameWorld gameWorld, Environments environment) {
        super(gameWorld, 1200, 630);
        requestFocus();
        setFocusable(true);
        Game.gameTime = new GameTime();
        this.environment = environment;
        this.gameWorld = gameWorld;
        this.setLayout(null);
        populateButtons();
        switch (environment) {
            case MAGIC_CLIFF -> {
                parallaxBackground = new ImageIcon("data/MagicCliffs/PNG/sky2.png").getImage();
                parallaxForeground = new ImageIcon("data/MagicCliffs/PNG/clouds.png").getImage();
                sea = new ImageIcon("data/MagicCliffs/PNG/sea3.png").getImage();
            }
            case HAUNTED_FOREST -> {
                parallaxBackground = scaleImage(new ImageIcon("data/HauntedForest/back.png").getImage());
                parallaxForeground = scaleImage(new ImageIcon("data/HauntedForest/middle.png").getImage());
                sea = null;
                setBackground(Color.BLACK);
            }
            case GOTHIC_CEMETERY -> {
                parallaxBackground = scaleImage(new ImageIcon("data/GothicvaniaCemetery/background.png").getImage());
                parallaxForeground = scaleImage(new ImageIcon("data/GothicvaniaCemetery/graveyard.png").getImage());
                sea = null;
                setBackground(new Color(0, 0, 34));

            }
            default -> throw new IllegalStateException(Console.exceptionMessage("Unexpected value: " + environment));
        }
        addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                super.focusLost(e);
                PlayerWalker player = gameWorld.getPlayer();
                if (!player.isDead()) {
                    player.stopWalking();
                    player.setLinearVelocity(new Vec2(0, player.getLinearVelocity().y));
                }
            }
        });
    }
    // Method | private | scaling
    /**
     * Scales the given image based on the view dimensions.
     *
     * @param image the image to be scaled
     * @return the scaled image
     * @see Game#getScaledDimensions(int, int, int, int) 
     * @see Image#getScaledInstance(int, int, int)
     */
    private Image scaleImage(Image image) {
        int scaleWidth, scaleHeight;
        if (environment.equals(Environments.HAUNTED_FOREST)) {
            scaleWidth = 1200;
            scaleHeight = 630;
        } else {
            scaleWidth =  (int) (1200*1.2f);
            scaleHeight = (int) (630*1.2f);
        }
        int[] scaledDimensions = Game.getScaledDimensions(image.getWidth(this), image.getHeight(this), scaleWidth, scaleHeight);
        return image.getScaledInstance(scaledDimensions[0], scaledDimensions[1], Image.SCALE_SMOOTH);
    }
    // Methods | Background | @Override
    /**
     * Paints the background of the game view.
     * It also draws the parallax background and
     * foreground images based on the player's position,
     * only drawing the images if they are within a certain
     * range of the player.
     *
     * @param g the graphics
     * @see Graphics2D
     */
    @Override
    protected void paintBackground(Graphics2D g) {
        Vec2 playerPos = Game.gameWorld.getPlayer().getPosition();
        int playerX = (int) worldToView(playerPos).getX();
        LevelFrame currentEnvironment = Game.gameWorld.getLevel();
        int yPos;
        int xPos;
        xPos = (int) worldToView(currentEnvironment.getCentre()).getX();
        yPos = (int) worldToView(currentEnvironment.getCentre()).getY();
        if (environment.equals(Environments.HAUNTED_FOREST)) {
            yPos -= 30;
        } else if (environment.equals(Environments.GOTHIC_CEMETERY)) {
            yPos += 200;
        }

        int offset = Math.abs((int) worldToView(currentEnvironment.getBoundary("Left")).getX() - (int) worldToView(currentEnvironment.getBoundary("Right")).getX());
        staticWorldDraw(g, parallaxBackground, playerX, xPos, yPos - parallaxBackground.getHeight(this), offset, 2106);
        staticWorldDraw(g, parallaxForeground, playerX, xPos, yPos - parallaxForeground.getHeight(this), offset, 2106);
        if (sea == null) {return;}
        staticWorldDraw(g, sea, playerX, xPos, yPos, offset, 1053);
    }
    /**
     * Draws static parallax elements in the game view.
     * The elements are drawn in a parallax effect based on the player's position.<br><br>
     * this replaced three methods that output the same thing as this; however, this function performs better.
     *
     * @param graphics the graphics
     * @param img the image to be drawn
     * @param playerX the x position of the player
     * @param xPos the x position to start drawing
     * @param yPos the y position to start drawing
     * @param offset the offset for drawing
     * @param drawDistance the distance to draw from the {@link game.body.walkers.PlayerWalker PlayerWalker}
     */
    private void staticWorldDraw(Graphics2D graphics, Image img, int playerX, int xPos, int yPos, int offset, int drawDistance) {
        int width = img.getWidth(this);
        for (int i = xPos-offset; i < xPos + offset; i+= width)  {
            if (i < playerX + drawDistance && i > playerX - drawDistance) {
                graphics.drawImage(img, i, yPos, this);
            }
        }
    }
    /**
     * Paints the foreground of the game view:<br>
     * - Checks for game over conditions<br>
     * - Draws the health bar<br>
     * - Draws the inventory<br>
     * - Checks the player's status, to draw a vignette when hurt<br>
     *
     * @param g the graphics
     */
    @Override
    protected void paintForeground(Graphics2D g) {
        checkForGameOver(g);
        drawHealthBar(g);
        drawInventory(g);
        checkPlayerStatus(g);
    }
    /**
     * Checks the player's status.
     * If the player is hit or dead, a vignette effect is applied.
     *
     * @param g graphics
     */
    private void checkPlayerStatus(Graphics2D g) {
        if (gameWorld.getPlayer().isHit() || gameWorld.getPlayer().isDead()) {
            hurt(g);
        }
    }
    /**
     * Populates the inventory buttons
     * and sets the z-order of the components.
     */
    private void populateButtons() {
        for (int i = 0; i < 4; i++) {
            inventoryButtons.add(new InventoryButton(this, i));
        }
        for(Component component : this.getComponents()) {
            if (component instanceof InventoryButton) {
                this.setComponentZOrder(component, getComponentCount()-1); // manually correcting z-order after all components are added during init
            }
        }
    }
    /**
     * Pauses the interface by disabling interaction with inventory buttons using consumers.
     */
    public void pauseInterface() {
        inventoryButtons.forEach(InventoryButton::disableInteract);
    }
    /**
     * Resumes the interface by enabling interaction with inventory buttons using consumers.
     */
    public void resumeInterface() {
        inventoryButtons.forEach(InventoryButton::enableInteract);
    }
    /**
     * Checks if Player.isDead and if gameOver is true.<br>
     * If the player is dead, the interface is paused, preventing item use.<br>
     * If the game is over, a message is displayed.
     *
     * @param graphics the graphics
     */
    private void checkForGameOver(Graphics2D graphics) {
        if (gameWorld.getPlayer().isDead()) {
            pauseInterface();
        }
        String message = null;
        Color color = Color.LIGHT_GRAY;
        if (gameOverMessage != null) {
            if (blackScreenDeath) {
                graphics.setColor(Color.BLACK);
                graphics.fillRect(0,0, getWidth(), getHeight());
            }
            color = Color.RED;
            message = gameOverMessage;
        } else if (victoryMessage != null) {
            color = Color.ORANGE;
            message = victoryMessage;
        }
        if (message != null && endText == null) {
            endText = new JLabel(message);
            endText.setFont(DISPLAY_FONT);
            endText.setForeground(color);
            endText.setSize(endText.getPreferredSize());
            add(endText);
            endText.setLocation(getWidth()/2 - endText.getWidth()/2, getHeight()/4 + endText.getHeight()/2);
            ToMenuButton toMenuButton = new ToMenuButton();
            add(toMenuButton);
            toMenuButton.setLocation(getWidth()/2 - toMenuButton.getWidth()/2, getHeight()/4 + endText.getHeight() + 50);
        }

    }
    /**
     * Applies a visual effect (vignette) to indicate the player is hurt.
     *
     * @param graphics the graphics
     */
    private void hurt(Graphics2D graphics) {
        Point centerOfScreen = new Point(getWidth() / 2, getHeight() / 2);
        float r = getWidth() / 2.0f; // radius of the gradient
        float[] fractions = {0.6f, 1.0f}; // this was kind of trial and error, seems to be the right distribution
        Color[] colors = {new Color(0, 0, 0, 0), new Color(100, 0, 0, 150)}; // all zero + 0 alpha is transparent, which moves from a fraction of 0.6 to 1,.0f of red.
        RadialGradientPaint vignette = new RadialGradientPaint(centerOfScreen, r, fractions, colors);
        graphics.setPaint(vignette);
        graphics.fillRect(0, 0, getWidth(), getHeight());
    }
    /**
     * Draws the game time on the screen.
     * @param graphics the graphics
     * @deprecated Due to game time being logged in the more robust Console system,
     * where time is logged for each appended message.
     */
    @Deprecated
    private void drawGameTime(Graphics2D graphics) {
        graphics.setColor(Color.BLUE);
        graphics.drawString(String.format("Timer: %02d" + ":%02d", Game.gameTime.getTimeMinutes(), Game.gameTime.getTimeSeconds()), 5, 20);
    }
    /**
     * Draws the health bar on the screen.
     *
     * @param graphics the graphics
     */
    private void drawHealthBar(Graphics2D graphics) {
        // Health bar dimensions and position
        int xPos = (int) Game.getFrameDimensions().x - 490;
        int healthBarWidth = 490;
        int healthBarHeight = 35;
        int borderThickness = 3;
        // Calculate health percentage
        double healthPercentage = (double) gameWorld.getPlayer().getHealthPoints() / gameWorld.getPlayer().getMaxHealth();
        int filledWidth = (int)(healthPercentage * (healthBarWidth-82));
        String currentColour = getColour(healthBarWidth);
        // Drawing health bar base colour
        graphics.setColor(getRGBValues(currentColour, 1));
        graphics.fillRect((xPos+38) + borderThickness, 10 + borderThickness, filledWidth, healthBarHeight-10);
        // Drawing health bar shadow colour
        graphics.setColor(getRGBValues(currentColour, 2));
        graphics.fillRect((xPos+37) + borderThickness, 25 + borderThickness, filledWidth, healthBarHeight-30);
        // Drawing the health bar border image
        graphics.drawImage(new ImageIcon("data/Display_assets/health_bar/tile000.png").getImage(), xPos, 10, healthBarWidth, healthBarHeight, this);

    }
    /**
     * Draws the inventory on the screen.
     *
     * @param graphics the graphics
     */
    private void drawInventory(Graphics2D graphics) {
        Color temp = graphics.getColor();
        ArrayList<String> path = gameWorld.getPlayerInventory().getInventoryPath(2); // since there are -2 slots in slotLocations
        for (int i = 0; i < slotLocations.size(); i++) {
            if (i == 0) {
                graphics.setColor(new Color(109, 93, 45));
            } else if (i == 1) {
                graphics.setColor(new Color(138, 115, 53));
                graphics.fill3DRect(slotLocations.get(i)[0], slotLocations.get(i)[1], slotLocations.get(i)[2], slotLocations.get(i)[3], true);
                graphics.setColor(new Color(94, 43, 48));
            } else {
                graphics.fill3DRect(slotLocations.get(i)[0], slotLocations.get(i)[1], slotLocations.get(i)[2], slotLocations.get(i)[3], false);
                if (path.get(i) != null) {
                    ImageIcon icon = new ImageIcon(path.get(i));
                    int[] scaledDimensions = Game.getScaledDimensions(icon.getIconWidth(), icon.getIconHeight(), slotLocations.get(i)[2]-20, slotLocations.get(i)[3]-20);
                    icon.setImage(icon.getImage().getScaledInstance(scaledDimensions[0], scaledDimensions[1], 1));
                    inventoryButtons.get(i-2).addIcon(icon);
                } else {
                    inventoryButtons.get(i-2).removeIcon();
                }
            }
        }
        graphics.setColor(temp);
    }
    /**
     * Gets the colour based on the health percentage.
     *
     * @param healthPercentage the health percentage
     * @return the colour as a string
     */
    private String getColour(double healthPercentage) {
        if (healthPercentage <= 0.3) {
            return "RED";
        } else if (healthPercentage <= 0.6) {
            return "YELLOW";
        } else {
            return "GREEN";
        }
    }
    /**
     * Gets the RGB values for the specified colour and bar number.
     *
     * @param colour the colour as a string
     * @param barNum the bar number
     * @return the colour as a Colour object
     */
    private Color getRGBValues(String colour, int barNum) {
        if (barNum == 1) {
            switch (colour) {
                case "RED" -> {return new Color(200, 40, 40);}
                case "YELLOW" -> {return new Color(240, 180, 50);}
                case "GREEN" -> {return new Color(50, 200, 100);}
                default -> {
                    Console.error("Invalid colour in GameView.getRGBValues! Returning default");
                    return new Color(200, 40, 40);
                }
            }
        } else {
            switch (colour) {
                case "RED" -> {return new Color(140, 30, 30);}
                case "YELLOW" -> {return new Color(180, 140, 40);}
                case "GREEN" -> {return new Color(30, 140, 70);}
                default -> {
                    Console.error("Invalid colour in GameView.getRGBValues! Returning default");
                    return new Color(140, 30, 30);
                }
            }
        }
    }
    /**
     * Sets the game over state to true.
     * @param gameOverMessage the game over message
     */
    public void gameOver(String gameOverMessage) {
        this.gameOverMessage = gameOverMessage;

    }
    /**
     * Ends game with the black screen death state to true.
     *
     * @param gameOverMessage the game over message
     */
    public void blackScreenDeath(String gameOverMessage) {
        blackScreenDeath = true;
        gameOver(gameOverMessage);
    }
    /**
     * Sets the game over state to false.
     * @param victoryMessage the victory message
     */
    public void gameWon(String victoryMessage) {
        this.victoryMessage = victoryMessage;
    }

    /**
     * Gets the assigned GameWorld instance.
     * @return the {@link GameWorld} instance.
     */
    public GameWorld getGameWorld() {
        return gameWorld;
    }

    /**
     * Toggles the menu panel to show or hide the menu.
     * @see JMenuPanel
     */
    public void toggleMenu() {
        jMenuPanel.toggleMenu();
    }
}
