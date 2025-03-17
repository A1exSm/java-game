package game.utils.menu;
// Imports

import game.Game;
import org.jbox2d.common.Vec2;
import javax.swing.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.HashMap;

// Class
public class GameJMenuBar extends JMenuBar {
    // Fields
    private HashMap<String, ArrayList<JMenuItem>> menuItems = new HashMap<>();
    // Constructor
    public GameJMenuBar() {
        super();
        // JMenu Init
        addFile();
        addSettings();
        addPlayer();
    }

    // Methods | Menu Creation
    private void newMenu(String name) {
        if (menuItems.putIfAbsent(name, new ArrayList<>()) != null) { // so basically this returns null if the key is not present, and the value if it is present
            System.err.println("Warning: JMenu with name: " + name + " already exists! Returning.");
            return;
        }
        JMenu menu = new JMenu(name);
        menu.setIcon(new ImageIcon("data/HuntressPNG/Spear.png"));
        add(menu);
    }

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
                Game.gameView.JMenuPanel.toggleMenu();
            }
        });
    }

    private void addSettingsListeners(JMenuItem[] items) {
        items[0].addActionListener(e -> {
            if (e.getActionCommand().equals("Toggle Debug")) {
                Game.debugOn();
            }
        });
    }


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
    private JMenu getMenu(String name) {
        for (int i = 0; i < getMenuCount(); i++) {
            if (getMenu(i).getText().equals(name)) {
                return getMenu(i);
            }
        }
        return null;
    }

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

    protected void setMenuMnemonic(String name, int keyEvent) {
        if (getMenu(name) == null) {
            System.err.println("Warning: Cannot setMnemonic since JMenu with name: " + name + " does not exist! Returning.");
            return;
        }
        getMenu(name).setMnemonic(keyEvent);
    }

    protected void setMenuItemMnemonic(String menuName, String itemName, int keyEvent) {
        getMenuItem(menuName, itemName).setMnemonic(keyEvent);
    }
}
