package game.utils.menu;
// Imports

import game.Game;
import org.jbox2d.common.Vec2;
import javax.swing.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.HashMap;

// Class
/**
 * The GameJMenuBar class extends {@link JMenuBar} to create a custom menu bar for the game.
 * It includes menus for File, Settings, and the Player with various menu items and their respective action listeners.
 */
public class GameJMenuBar extends JMenuBar {
    // Fields
    private HashMap<String, ArrayList<JMenuItem>> menuItems = new HashMap<>();
    // Constructor
    /**
     * Constructor for GameJMenuBar.
     * Initializes the menu bar by adding File, Settings, and Player menus.
     */
    public GameJMenuBar() {
        super();
        // JMenu Init
        addFile();
        addSettings();
        addPlayer();
    }

    // Methods | Menu Creation
    /**
     * Creates a new menu with the given name.<br><br>
     * <i>If a menu with the same name already exists, a warning message is printed and the method returns.</i>
     *
     * @param name The name of the menu to be created.
     */
    private void newMenu(String name) {
        if (menuItems.putIfAbsent(name, new ArrayList<>()) != null) { // so basically this returns null if the key is not present, and the value if it is present
            System.err.println("Warning: JMenu with name: " + name + " already exists! Returning.");
            return;
        }
        JMenu menu = new JMenu(name);
        menu.setIcon(new ImageIcon("data/HuntressPNG/Spear.png"));
        add(menu);
    }
    /**
     * Creates a new menu item under the specified menu.<br><br>
     * <i>If the given {@code menuName} is not valid, or {@code itemName} has already been used,
     * a warning message is printed and the method returns</i>
     *
     * @param menuName The name of the menu to add the item to.
     * @param itemName The name of the menu item to be created.
     */
    // Methods | Menu Item Creation
    private void newMenuItem(String menuName, String itemName) {
        if (!menuItems.containsKey(menuName)) {
            System.err.println("Warning: JMenu with name: " + menuName + " does not exist! Returning.");
            return;
        } else if (menuItems.get(menuName).contains(itemName)) {
            System.err.println("Warning: JMenuItem with name: " + itemName + " already exists in JMenu: " + menuName + "! Returning.");
            return;
        }
        JMenuItem item = new JMenuItem(itemName);
        menuItems.get(menuName).add(item);
        getMenu(menuName).add(item);
        item.setActionCommand(itemName);
    }

    // Methods | Init
    /**
     * Initializes the File menu with its items and action listeners.
     */
    private void addFile() {
        // Creating File Menu
        newMenu("File");
        newMenuItem("File", "Exit");
        newMenuItem("File", "Pause");
        // Setting Mnemonics
        setMenuMnemonic("File", KeyEvent.VK_F);
        setMenuItemMnemonic("File", "Exit", KeyEvent.VK_E);
        setMenuItemMnemonic("File", "Pause", KeyEvent.VK_P);
        // Action Listeners
        addFileListeners(menuItems.get("File").toArray(JMenuItem[]::new)); // convert ArrayList to a new Array of type JMenuItem
    }
    /**
     * Initializes the Settings menu with its items and action listeners.
     */
    private void addSettings() {
        // Creating Settings Menu
        newMenu("Settings");
        newMenuItem("Settings", "Toggle Debug");
        // Setting Mnemonics
        setMenuMnemonic("Settings", KeyEvent.VK_S);
        setMenuItemMnemonic("Settings", "Toggle Debug", KeyEvent.VK_D);
        // Action Listeners
        addSettingsListeners(menuItems.get("Settings").toArray(JMenuItem[]::new));
    }
    /**
     * Initializes the Player menu with its items and action listeners.
     */
    private void addPlayer() {
        // Creating Player Menu
        newMenu("Player");
        newMenuItem("Player", "Make Ghostly");
        newMenuItem("Player", "Make Solid");
        newMenuItem("Player", "Location 1");
        newMenuItem("Player", "Location 2");
        // Setting Mnemonics
        setMenuMnemonic("Player", KeyEvent.VK_P);
        setMenuItemMnemonic("Player", "Make Ghostly", KeyEvent.VK_G);
        setMenuItemMnemonic("Player", "Make Solid", KeyEvent.VK_S);
        setMenuItemMnemonic("Player", "Location 1", KeyEvent.VK_1);
        setMenuItemMnemonic("Player", "Location 2", KeyEvent.VK_2);
        // Action Listeners
        addPlayerListeners(menuItems.get("Player").toArray(JMenuItem[]::new));
    }
    /**
     * Adds action listeners to the File menu items.
     * @param items An array of JMenuItem objects in the File menu.
     */
    private void addFileListeners(JMenuItem[] items) {
        items[0].addActionListener(e -> {
            if (e.getActionCommand().equals("Exit")) {
                if (Game.gameWorld.isRunning()) {Game.gameWorld.togglePause();}
                if (!Game.exit()) {Game.gameWorld.togglePause();}
            }
        });

        items[1].addActionListener(e -> {
            if (e.getActionCommand().equals("Pause")) {
                if (Game.gameWorld.isRunning()) items[1].setText("Pause");
                else items[1].setText("Resume");
                Game.gameView.jMenuPanel.toggleMenu();
            }
        });
    }
    /**
     * Adds action listeners to the Settings menu items.
     * @param items An array of JMenuItem objects in the Settings menu.
     */
    private void addSettingsListeners(JMenuItem[] items) {
        items[0].addActionListener(e -> {
            if (e.getActionCommand().equals("Toggle Debug")) {
                Game.debugOn();
            }
        });
    }

    /**
     * Adds action listeners to the Player menu items.
     * @param items An array of JMenuItem objects in the Player menu.
     */
    private void addPlayerListeners(JMenuItem[] items) {
        items[0].addActionListener(e -> {
            if (e.getActionCommand().equals("Make Ghostly")) {
                Game.gameWorld.getPlayer().makePlayerGhostly();
            }
        });
        items[1].addActionListener(e -> {
            if (e.getActionCommand().equals("Make Solid")) {
                Game.gameWorld.getPlayer().makePlayerSolid();
            }
        });
        items[2].addActionListener(e -> {
            if (e.getActionCommand().equals("Location 1")) {
                Game.gameWorld.getPlayer().setPosition(new Vec2(0, 2));
            }
        });
        items[3].addActionListener(e -> {
            if (e.getActionCommand().equals("Location 2")) {
                Game.gameWorld.getPlayer().setPosition(new Vec2(-25, 204));
            }
        });    }

    // Methods
    /**
     * Retrieves the {@link JMenu} object with the specified name.
     * @param name The name of the menu to retrieve.
     * @return The JMenu object with the specified name, or null if not found.
     */
    private JMenu getMenu(String name) {
        for (int i = 0; i < getMenuCount(); i++) {
            if (getMenu(i).getText().equals(name)) {
                return getMenu(i);
            }
        }
        return null;
    }
    /**
     * Retrieves the {@link JMenuItem} instance with the specified name from the specified menu.
     * @param menuName The name of the menu containing the item.
     * @param itemName The name of the menu item to retrieve.
     * @return The JMenuItem object with the specified name, or a new JMenuItem if not found.
     */
    private JMenuItem getMenuItem(String menuName, String itemName) {
        if (!menuItems.containsKey(menuName)) {
            System.err.println("Warning: JMenu with name: " + menuName + " does not exist!");
            return new JMenuItem();
        }
        for (int i = 0; i < menuItems.get(menuName).size(); i++) {
            if (menuItems.get(menuName).get(i).getText().equals(itemName)) {
                return menuItems.get(menuName).get(i);
            }
        }
        System.err.println("Warning: JMenuItem with name: " + itemName + " does not exist in JMenu: " + menuName + "!");
        return new JMenuItem();
    }
    /**
     * Sets the mnemonic for the specified menu.<br><br>
     * <i>If the {@code name} is not valid,
     * a warning message is printed and the method returns.</i>
     * @param name The name of the menu.
     * @param keyEvent The key event to set as the mnemonic.
     */
    protected void setMenuMnemonic(String name, int keyEvent) {
        if (getMenu(name) == null) {
            System.err.println("Warning: Cannot setMnemonic since JMenu with name: " + name + " does not exist! Returning.");
            return;
        }
        getMenu(name).setMnemonic(keyEvent);
    }
    /**
     * Sets the mnemonic for the specified menu item.
     * @param menuName The name of the menu containing the item.
     * @param itemName The name of the menu item.
     * @param keyEvent The key event to set as the mnemonic.
     */
    protected void setMenuItemMnemonic(String menuName, String itemName, int keyEvent) {
        getMenuItem(menuName, itemName).setMnemonic(keyEvent);
    }
}
