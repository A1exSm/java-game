package game.core;
// Imports
import city.cs.engine.EngineerView;

import javax.swing.*;
// Class
public class GameFrame extends JFrame {
    // Constructor
    public GameFrame(String title, GameView userView) {
        super(title);
        add(userView);
        setup();
    }
    public GameFrame(String title, EngineerView engineerView) {
        super(title);
        add(engineerView);
        setup();
    }

    // Methods

    private void setup(){
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationByPlatform(true);
        setResizable(false);
        setVisible(true);
        pack();
    }

}
