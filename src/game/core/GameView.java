package game.core;
// Imports
import city.cs.engine.UserView;
import game.Game;
import game.utils.InventoryButton;
import game.utils.menu.MenuPanel;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

// Class
public class GameView extends UserView {
    // Fields
    private final Image background = new ImageIcon("data/sky.png").getImage();
    public static final Font STATUS_FONT = new Font("Monospaced", Font.PLAIN, 20);
    public static final Font DISPLAY_FONT = new  Font("Niagara Solid", Font.BOLD, 50);
    private final ArrayList<InventoryButton> inventoryButtons = new ArrayList<>();
    private boolean gameOver = false;
    private final GameWorld gameWorld;
    public final MenuPanel menuPanel = new MenuPanel(this);
    private JLabel statusBar = new JLabel(" ");
//    public JPanel notificationPanel = new JPanel();
    // Constructor
    public GameView(GameWorld gameWorld, int width, int height) {
        super(gameWorld, width, height);
        requestFocus();
        setFocusable(true);
        Game.gameTime = new GameTime();
        this.gameWorld = gameWorld;
        this.setLayout(null);
        populateButtons();
        for(Component component : this.getComponents()) {
            if (component instanceof InventoryButton) {
                this.setComponentZOrder(component, getComponentCount()-1);
            }
        }
    }

    // Methods
    @Override
    protected void paintBackground(Graphics2D g) {
        g.drawImage(background, 0, 0, this);
    }

    @Override
    protected void paintForeground(Graphics2D g) {
        drawGameTime(g);
        checkForGameOver(g);
        drawHealthBar(g);
        inventoryTest(g);
        checkPlayerStatus(g);
    }

    private void checkPlayerStatus(Graphics2D g) {
        if (gameWorld.getPlayer().getHit() || gameWorld.getPlayer().isDead()) {
            hurt(g);
        }
    }

    private void populateButtons() {
        for (int i = 0; i < 4; i++) {
            inventoryButtons.add(new InventoryButton(this, i));
        }
    }
    public void pauseInterface() {
        inventoryButtons.forEach(InventoryButton::disableInteract);
    }

    public void resumeInterface() {
        inventoryButtons.forEach(InventoryButton::enableInteract);
    }

    private void checkForGameOver(Graphics2D graphics) {
        if (gameWorld.getPlayer().isDead()) {
            pauseInterface();
        }
        if (gameOver) {
            graphics.setColor(Color.RED);
            graphics.setFont(DISPLAY_FONT);
            graphics.drawString("GAME OVER", 520, 250);
            /*
            I used to stop the world here.
            BUT I felt that it would be cool if the world and mobs kept doing their things.
            Its like the world is not centred around the player concept, things keep going even if the player is dead.
            */
        }
    }

    private void hurt(Graphics2D graphics) {
        Point centerOfScreen = new Point(getWidth() / 2, getHeight() / 2);
        float r = getWidth() / 2.0f; // radius of the gradient
        float[] fractions = {0.6f, 1.0f}; // this was kinda trial and error, seems to be the right distribution
        Color[] colors = {new Color(0, 0, 0, 0), new Color(100, 0, 0, 150)}; // all zero + 0 alpha is transparent, which moves from a fraction of 0.6 to 1,.0f of red.
        RadialGradientPaint vignette = new RadialGradientPaint(centerOfScreen, r, fractions, colors);
        graphics.setPaint(vignette);
        graphics.fillRect(0, 0, getWidth(), getHeight());
    }

    private void drawGameTime(Graphics2D graphics) {
        graphics.setFont(STATUS_FONT);
        if (Game.gameTime.getTime() == 0) {
            graphics.setColor(Color.RED);
            graphics.drawString("LOADING...", 550, 315);
        } else {
            graphics.setColor(Color.BLUE);
            graphics.drawString(String.format("Timer: %02d" + ":%02d", Game.gameTime.getTimeMinutes(), Game.gameTime.getTimeSeconds()), 5, 20);
        }
    }

    private void drawHealthBar(Graphics2D graphics) {
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
        graphics.setColor(getRGBValues(currentColour, 1));
        graphics.fillRect((xPos+38) + borderThickness, 10 + borderThickness, filledWidth, healthBarHeight-10);
        // Drawing health bar shadow colour
        graphics.setColor(getRGBValues(currentColour, 2));
        graphics.fillRect((xPos+37) + borderThickness, 25 + borderThickness, filledWidth, healthBarHeight-30);
        // Drawing the health bar border image
        graphics.drawImage(new ImageIcon("data/Display_assets/health_bar/tile000.png").getImage(), xPos, 10, healthBarWidth, healthBarHeight, this);

    }

    private void inventoryTest(Graphics2D graphics) {
        Color temp = graphics.getColor();
        ArrayList<int[]> slotLocations = getSlotLocations();
        boolean[] slotRaisedList = getSlotRaisedList();
        ArrayList<String> path = GameWorld.playerInventory.getInventoryPath(2); // since there are -2 slots in slotLocations
        for (int i = 0; i < slotLocations.size(); i++) {
            if (i == 0) {
                graphics.setColor(new Color(109, 93, 45));
            } else if (i == 1) {
                graphics.setColor(new Color(138, 115, 53));
                graphics.fill3DRect(slotLocations.get(i)[0], slotLocations.get(i)[1], slotLocations.get(i)[2], slotLocations.get(i)[3], slotRaisedList[i]);
                graphics.setColor(new Color(94, 43, 48));
            } else {
                graphics.fill3DRect(slotLocations.get(i)[0], slotLocations.get(i)[1], slotLocations.get(i)[2], slotLocations.get(i)[3], slotRaisedList[i]);
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



    private ArrayList<int[]> getSlotLocations() {
        ArrayList<int[]> slotLocations = new ArrayList<>(); // format: {{x,y,width,height}},{{x,y},{{x,y,width,height}}...
        slotLocations.add(new int[]{ 800, 533, 400, 100}); //        slotLocations.add(new int[]{x, y, width, height});
        slotLocations.add(new int[]{ 802, 538, 395, 90}); //        slotLocations.add(new int[]{x + 2, y + 35, width-10, height-10});
        slotLocations.add(new int[]{ 810, 543, 80, 80}); //       slotLocations.add(new int[]{10 + x, y + 40, (width-20)/5, height-20});
        slotLocations.add(new int[]{ 894, 543, 80, 80}); //        slotLocations.add(new int[]{13 + x + (width+5)/5, y+40, (width-20)/5, height-20});
        slotLocations.add(new int[]{ 978, 543, 80, 80}); //        slotLocations.add(new int[]{16 + x + ((width+5)/5)*2, y+40, (width-20)/5, height-20});
        slotLocations.add(new int[]{ 1062, 543, 80, 80}); //        slotLocations.add(new int[]{19 + x + ((width+5)/5)*3, y+40, (width-20)/5, height-20});
        return slotLocations;
    }

    private boolean[] getSlotRaisedList() {
        return new boolean[]{true,true,false,false,false,false};
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
