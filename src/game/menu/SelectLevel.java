package game.menu;
// Imports
import game.Game;
import game.core.console.Console;
import game.enums.Environments;
import game.levels.GothicCemetery;
import game.levels.HauntedForest;
import game.levels.MagicCliff;
import game.levels.MobStore;
import javax.swing.*;
import javax.swing.event.ChangeListener;
import java.awt.*;
/**
 * The SelectLevel class provides a user interface for selecting game levels.
 * It allows users to choose between different levels and display relevant information about each level.
 */
// Class
public class SelectLevel {
    private JPanel levelPanel;
    private JTabbedPane selectionTabPane;
    private JPanel magicPane;
    private JLabel thumbnail;
    private JTextPane magicDescriptionPane;
    private JButton magicStart;
    private JPanel magicInterfacePanel;
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
    private JButton gothicBack;
    private JButton hauntedBack;
    private JButton magicBack;
    private JPanel magicButtonInterface;
    private JPanel magicStartPanel;
    private JRadioButton magicLevelOne;
    private JRadioButton magicLevelTwo;
    private JPanel magicRadioPanel;
    private JPanel forestButtonInterface;
    private JButton forestBack;
    private JPanel forestStartPanel;
    private JPanel forestRadioPanel;
    private JRadioButton forestLevelOne;
    private JRadioButton forestLevelTwo;
    private JPanel cemeteryButtonInterface;
    private JButton cemeteryBack;
    private JPanel cemeteryStartPanel;
    private JPanel cemeteryRadioPanel;
    private JRadioButton cemeteryLevelOne;
    private JRadioButton cemeteryLevelTwo;
    private JLabel cemeteryWarning;
    private JLabel magicWarning;
    private JLabel forestWarning;
    // Fields
    private static final String STYLE_1 = "style=\"font-family: 'Blackadder ITC'; font-size: 72pt; color: rgb(96,87,73); font-weight: bold; font-style: italic;\"";
    private static final String STYLE_2 = "style=\"font-family: 'Blackadder ITC'; font-size: 32pt; color: rgb(96,87,73); font-weight: bold;\"";
    private static final String STYLE_1_MAC = "style=\"font-family: 'Apple Chancery'; font-size: 72pt; color: rgb(96,87,73); font-weight: bold; font-style: italic;\"";
    private static final String STYLE_2_MAC = "style=\"font-family: 'Apple Chancery'; font-size: 32pt; color: rgb(96,87,73); font-weight: bold;\"";

    /**
     * Sets appropriate default values and states.
     * Adds necessary listeners to components.
     * @param game The game instance to be used for level selection.
     */
    public SelectLevel(Game game) {
        initIcons();
        initLevels();
        initButtons(game);
        updateDescriptionPane(Environments.MAGIC_CLIFF);
        updateDescriptionPane(Environments.HAUNTED_FOREST);
        updateDescriptionPane(Environments.GOTHIC_CEMETERY);
        if (System.getProperty("os.name").contains("Mac")) { // discussed in MainMenu.java
            macOsSetup(cemeteryStart, cemeteryBack, cemeteryLevelOne, cemeteryLevelTwo, cemeteryWarning,
                    forestStart, forestBack, forestLevelOne, forestLevelTwo, forestWarning,
                    magicStart, magicBack, magicLevelOne, magicLevelTwo);
        }
        Options.addSounds(levelPanel);
    }
    // reminder, use GameView JPanel to check whether the scaled images have been completed, and call a static method on a future progress bar to increment!
    // we can call a static method every time a part of the constructor is done to update a text field stating what is being done, like... "compiling probability map...", "Assigning textures...", "Loading level..." etc.
    // future me, this was not viable.

    // Methods

    /**
     * Gets the level panel to be added to a parent component.
     * @return The level panel.
     */
    public JPanel getPanel() {
        return levelPanel;
    }

    private void initIcons() {
        setThumbnail("data/HauntedForest/thumbnail.png", forestThumbnail);
        setThumbnail("data/MagicCliffs/thumbnail.png", thumbnail);
        setThumbnail("data/GothicvaniaCemetery/thumbnail.png", cemeteryThumbnail);
    }

    private void initLevels() {
        setupLevelRadios(magicLevelOne, magicLevelTwo, magicWarning, Environments.MAGIC_CLIFF);
        setupLevelRadios(forestLevelOne, forestLevelTwo, forestWarning, Environments.HAUNTED_FOREST);
        setupLevelRadios(cemeteryLevelOne, cemeteryLevelTwo, cemeteryWarning, Environments.GOTHIC_CEMETERY);
    }

    private void initButtons(Game game) {
        // MagicCliffs
        magicStart.addActionListener(e -> {
            if (magicLevelOne.isSelected()) {
                game.getFrame().selectLevel(Environments.MAGIC_CLIFF, 1);
            } else if (magicLevelTwo.isSelected()) {
                game.getFrame().selectLevel(Environments.MAGIC_CLIFF, 2);
            } else if (!magicWarning.isVisible()) {
                lockedWarning(magicLevelOne, magicLevelTwo, magicWarning);
            }
        });
        magicBack.addActionListener(e -> Game.exitToMainMenu());
        // HauntedForest
        forestStart.addActionListener(e -> {
            if (forestLevelOne.isSelected()) {
                game.getFrame().selectLevel(Environments.HAUNTED_FOREST, 1);
            } else if (forestLevelTwo.isSelected()) {
                game.getFrame().selectLevel(Environments.HAUNTED_FOREST, 2);
            } else if (!forestWarning.isVisible()) {
                lockedWarning(forestLevelOne, forestLevelTwo, forestWarning);
            }
        });
        forestBack.addActionListener(e -> Game.exitToMainMenu());
        // GothicCemetery
        cemeteryStart.addActionListener(e -> {
            if (cemeteryLevelOne.isSelected()) {
                game.getFrame().selectLevel(Environments.GOTHIC_CEMETERY, 1);
            } else if (cemeteryLevelTwo.isSelected()) {
                game.getFrame().selectLevel(Environments.GOTHIC_CEMETERY, 2);
            } else if (!cemeteryWarning.isVisible()) {
                lockedWarning(cemeteryLevelOne, cemeteryLevelTwo, cemeteryWarning);
            }
        });
        cemeteryBack.addActionListener(e -> Game.exitToMainMenu());
    }

    private void lockedWarning(JRadioButton levelOne, JRadioButton levelTwo, JLabel warning ) {
        if (!levelOne.isEnabled() && !levelTwo.isEnabled()) {
            warning.setText("All levels are locked.");
        } else {
            warning.setText("Please select a level.");
        }
        warning.setVisible(true);
    }
    private void setThumbnail(String path, JLabel thumbnail) {
        thumbnail.setIcon(new ImageIcon(new ImageIcon(path).getImage().getScaledInstance(1160, 480, Image.SCALE_SMOOTH)));
        thumbnail.setHorizontalAlignment(SwingConstants.CENTER);
        thumbnail.setVerticalAlignment(SwingConstants.CENTER);
    }

    private void setupLevelRadios(JRadioButton levelOne, JRadioButton levelTwo, JLabel warning, Environments environment) {
        levelOne.setText("Level 1");
        levelTwo.setText("Level 2");
        boolean one = environment.getBool(1);
        boolean two = environment.getBool(2);
        setLevelStatus(levelTwo, levelOne, warning, two, one);
        setLevelStatus(levelOne, levelTwo, warning, one, two);
    }

    private void setLevelStatus(JRadioButton levelOne, JRadioButton levelTwo, JLabel warning, boolean one, boolean two) {
        if (two) {
            levelTwo.addActionListener(e -> {
                if (levelTwo.isSelected()) {
                    levelOne.setSelected(false);
                    if (warning.isVisible()) {
                        warning.setVisible(false);
                    }
                }
            });
            if (!one) {
                levelTwo.setSelected(true);
            }
        } else {
            levelTwo.setEnabled(false);
        }
    }

    private void updateDescriptionPane(Environments environment) {
        // selection process
        JTextPane pane;
        JRadioButton levelOne, levelTwo;
        MobStore mobStore;
        switch (environment) {
            case MAGIC_CLIFF -> {
                pane = magicDescriptionPane;
                levelOne = magicLevelOne;
                levelTwo = magicLevelTwo;
                mobStore = MagicCliff.NUM_MOBS;
            }
            case HAUNTED_FOREST -> {
                pane = forestDescription;
                levelOne = forestLevelOne;
                levelTwo = forestLevelTwo;
                mobStore = HauntedForest.NUM_MOBS;
            }
            case GOTHIC_CEMETERY -> {
                pane = cemeteryDescription;
                levelOne = cemeteryLevelOne;
                levelTwo = cemeteryLevelTwo;
                mobStore = GothicCemetery.NUM_MOBS;
            }
            default -> throw new IllegalArgumentException(Console.exceptionMessage("Invalid environment: " + environment));
        }
        // update the description pane
        ChangeListener updateDescription = e -> {
            if (levelOne.isSelected()) {
                setText(pane, mobStore, environment, 0);
            } else if (levelTwo.isSelected()) {
                setText(pane, mobStore, environment, 1);
            }
        };
        levelOne.addChangeListener(updateDescription);
        levelTwo.addChangeListener(updateDescription);
        if (levelOne.isSelected()) {
            setText(pane, mobStore, environment, 0);
        } else if (levelTwo.isSelected()) {
            setText(pane, mobStore, environment, 1);
        } else {
            setText(pane, environment);
        }
    }

    private void setText(JTextPane pane, MobStore mobStore, Environments environment, int level) {
        if (System.getProperty("os.name").contains("Mac")) {
            pane.setText("<html><head></head><body><p " + STYLE_1_MAC + ">" + environment.title + "</p><p " + STYLE_2_MAC + ">Level: " + (level+1) + "</p><p " + STYLE_2_MAC + ">Number of enemies: " + mobStore.getMobData(level) + "</p></body></html>");
            return;
        }
        pane.setText("<html><head></head><body><p " + STYLE_1 + ">" + environment.title + "</p><p " + STYLE_2 + ">Level: " + (level+1) + "</p><p " + STYLE_2 + ">Number of enemies: " + mobStore.getMobData(level) + "</p></body></html>");
    }

    private void setText(JTextPane pane, Environments environment) {
        if (System.getProperty("os.name").contains("Mac")) {
            pane.setText("<html><head></head><body><p " + STYLE_1_MAC + ">" + environment.title + "</p><br></body></html>");
            return;
        }
        pane.setText("<html><head></head><body><p " + STYLE_1 + ">" + environment.title + "</p><br></body></html>");
    }

    private void macOsSetup(JComponent... components) { // uses varargs to set all inputted components to opaque
        for (JComponent component : components) {
            component.setOpaque(true);
        }
    }
}