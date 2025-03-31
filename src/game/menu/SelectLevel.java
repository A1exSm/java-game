package game.menu;
// Imports

import game.Game;
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
    private JPanel someLevel;
    private JLabel thumbnail;
    private JTextPane descriptionPane;
    private JButton magicStart;
    private JPanel interfacePanel;
    // Fields

    // Constructor
    public SelectLevel(Game game) {
        thumbnail.setIcon(new ImageIcon("data/Magic_Cliffs/thumbnail.png"));
        thumbnail.setHorizontalAlignment(SwingConstants.CENTER);
        thumbnail.setVerticalAlignment(SwingConstants.CENTER);
        descriptionPane.setText("<html><head></head><body><p style=\"font-size: 30px; color: rgb(96,87,73);\">Number of enemies: "+ MagicCliff.NUM_MOBS + "</p></body></html>");
        magicStart.addActionListener(e -> {
            game.getFrame().switchLayout("MagicCliff");
        });
    }

    // Methods
    public JPanel getPanel() {
        return levelPanel;
    }
}
