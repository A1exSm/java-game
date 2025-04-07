package game.menu;
// Imports

import game.Game;
import game.enums.Environments;
import game.levels.GothicCemetery;
import game.levels.HauntedForest;
import game.levels.MagicCliff;

import javax.swing.*;

/**
 *
 */
// Class
public class SelectLevel {
    private JPanel levelPanel;
    private JTabbedPane selectionTabPane;
    private JPanel magicPane;
    private JLabel thumbnail;
    private JTextPane descriptionPane;
    private JButton magicStart;
    private JPanel interfacePanel;
    private JPanel forestPane;
    private JLabel forestThumbnail;
    private JPanel forestInterface;
    private JTextPane forestDescription;
    private JButton forestStart;
    private JPanel cemeteryPane;
    private JLabel cemeteryThumbnail;
    private JPanel cemeteryInterface;
    private JTextPane cemeteryDescription;
    private JButton cemeteryStart;
    // Fields

    // Constructor
    public SelectLevel(Game game) {
        thumbnail.setIcon(new ImageIcon("data/MagicCliffs/thumbnail.png"));
        cemeteryThumbnail.setIcon(new ImageIcon("data/GothicvaniaCemetery/thumbnail.png"));
        thumbnail.setHorizontalAlignment(SwingConstants.CENTER);
        thumbnail.setVerticalAlignment(SwingConstants.CENTER);
        descriptionPane.setText("<html><head></head><body><p style=\"font-size: 30px; color: rgb(96,87,73);\">Number of enemies: "+ MagicCliff.NUM_MOBS + "</p></body></html>");
        magicStart.addActionListener(e -> {
            game.getFrame().switchLayout(Environments.MAGIC_CLIFF);
        });
        forestDescription.setText("<html><head></head><body><p style=\"font-size: 30px; color: rgb(96,87,73);\">Number of enemies: "+ HauntedForest.NUM_MOBS + "</p></body></html>");
        forestStart.addActionListener(e -> {
            game.getFrame().switchLayout(Environments.HAUNTED_FOREST);
        });
        cemeteryDescription.setText("<html><head></head><body><p style=\"font-size: 30px; color: rgb(96,87,73);\">Number of enemies: "+ GothicCemetery.NUM_MOBS + "</p></body></html>");
        cemeteryStart.addActionListener(e -> {
            game.getFrame().switchLayout(Environments.GOTHIC_CEMETERY);
        });
    }
    // reminder, use GameView JPanel to check whether the scaled images have been completed, and call a static method on a future progress bar to increment!
    // we can call a static method every time a part of the constructor is done to update a text field stating what is being done, like... "compiling probability map...", "Assigning textures...", "Loading level..." etc.

    // Methods
    public JPanel getPanel() {
        return levelPanel;
    }
}
