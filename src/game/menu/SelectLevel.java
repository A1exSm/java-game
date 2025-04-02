package game.menu;
// Imports

import game.Game;
import game.enums.Environments;
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
    // Fields

    // Constructor
    public SelectLevel(Game game) {
        thumbnail.setIcon(new ImageIcon("data/MagicCliffs/thumbnail.png"));
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
    }

    // Methods
    public JPanel getPanel() {
        return levelPanel;
    }
}
