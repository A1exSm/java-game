package game.utils.menu;
// Imports

import javax.swing.*;

// Class
class GameJMenu extends JMenu {
    // Fields
    private static int menuCount = -1;
    protected final int menuIndex;
    {menuCount++;}
    // Constructor
    GameJMenu(String name) {
        super(name);
        menuIndex = menuCount;
    }
    // Methods
}
