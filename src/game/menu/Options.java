package game.menu;
import game.Game;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import game.core.GameSound;
import game.core.console.Console;
import game.enums.SoundGroups;
import game.levels.LevelData;
import game.levels.StatusGetter;
import java.awt.*;
/**
 * The Options class provides a user interface for game options, including volume controls and reset progress functionality.
 * It allows users to adjust game settings and reset their progress if desired.
 */
public class Options {
    private static final GameSound GAME_SOUND = GameSound.createSound("data/Audio/UI/confirm_style_2_001.wav", SoundGroups.UI, 1000);
    private static final GameSound CURSOR_SOUND = GameSound.createSound("data/Audio/UI/cursor_style_5.wav", SoundGroups.UI, 600);
    private JPanel optionsPanel;
    private JPanel topInnerPanel;
    private JPanel innerPanel;
    private JPanel musicVolumePanel;
    private JPanel mobVolumePanel;
    private JPanel allVolumePanel;
    private JPanel soundPanel;
    private JLabel titleLabel;
    private JPanel topOuterPanel;
    private JLabel mobVolumeLabel;
    private JLabel globalVolumeLabel;
    private JLabel musicVolumeLabel;
    private JButton resetProgressButton;
    private JPanel resetProgressPanel;
    private JPanel backPane;
    private JButton back;
    private JPanel dataSelectionPanel;
    private JButton dataSelectionButton;
    /**
     * Initializes the options panel.
     * Adds the appropriate components and
     * assigns their listeners and default values/state.
     */
    public Options() {
        new MenuSliderSurface(allVolumePanel, 0,0,313, 62, "All");
        new MenuSliderSurface(musicVolumePanel, 313, 62, Game.getGameMusic(), "Music");
        new MenuSliderSurface(mobVolumePanel, 313, 62, SoundGroups.MOBS, "Mobs");
        back.addActionListener(e -> Game.exitToMainMenu());
        resetProgressButton.addActionListener(e -> {
            String message = "Are you sure you want to reset your progress?\nIf so the overridden data will be temporarily saved to data/Saves/save2, until the next reset";
            int answer = JOptionPane.showConfirmDialog(null, message, "Reset Progress", JOptionPane.YES_NO_OPTION);
            if (answer == JOptionPane.YES_OPTION) {
                LevelData.resetLevelData();
            }
        });
        dataSelectionButton.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser("data/Saves");
            FileNameExtensionFilter filter = new FileNameExtensionFilter("Files", "txt");
            fileChooser.setFileFilter(filter);
            fileChooser.setAcceptAllFileFilterUsed(false);
            fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
            int answer = fileChooser.showDialog(getOptionsPanel(), "Select Level Data");
            if (answer == JFileChooser.APPROVE_OPTION) {
                String filePath = fileChooser.getSelectedFile().getPath();
                if (StatusGetter.isAllowed(filePath)) {
                    Game.selectNewLevelData(filePath, "data/Saves/save3.txt");
                } else {
                    Console.warning(filePath + " is not allowed");
                }
            }
        });
        addSounds(optionsPanel);
        if (System.getProperty("os.name").contains("Mac")) {
            Font mac_font = new Font("Apple Chancery", Font.ITALIC, 72);
            Font mac_font_buttons = new Font("Apple Chancery", Font.BOLD, 36);
            setMacFonts(optionsPanel, mac_font, mac_font_buttons);
            Console.log("here");
        }
    }
    private void setMacFonts(JPanel panel, Font mac_font, Font mac_font_buttons) {
        for (Component component : panel.getComponents()) {
            if (component instanceof JPanel subPanel) {
                setMacFonts(subPanel, mac_font, mac_font_buttons);
            } else if (component == titleLabel) {
                component.setFont(mac_font);
            } else if (component instanceof JLabel) {
                component.setFont(mac_font_buttons);
            }
        }
    }

    /**
     * Gets the options panel to add to a parent component.
     * @return the options panel
     */
    public JPanel getOptionsPanel() {
        return optionsPanel;
    }

    private void createUIComponents() {
        // init
        allVolumePanel = new JPanel(new GridLayout());
        musicVolumePanel = new JPanel(new GridLayout());
        mobVolumePanel = new JPanel(new GridLayout());
        topInnerPanel = new JPanel(new GridLayout());
        back = new JButton("Back");
        // setup
        setupPanel(topInnerPanel, new Dimension(150, 50), new Color(95, 86, 72), 3, 3, 3, 3);
    }

    private void setupPanel(JPanel panel, Dimension size, Color colour, int top, int left, int bottom, int right) {
        panel.setBorder(BorderFactory.createEmptyBorder(top, left, bottom, right));
        panel.setBackground(colour);
        panel.setMinimumSize(size);
        panel.setPreferredSize(size);
        panel.setMaximumSize(size);
    }
    /**
     * Adds sound effects to buttons in the given panel.
     * Has a recursive call to add sounds to nested panels.
     * @param panel The JPanel to which sound effects will be added.
     */
    public static void addSounds(JPanel panel) {
        for (Component component : panel.getComponents()) {
            if (component instanceof JButton button) {
                button.setRolloverEnabled(true);
                button.addActionListener(e -> GAME_SOUND.play());
                button.addChangeListener(e -> {
                    if (button.getModel().isRollover()) {
                        CURSOR_SOUND.forcedPlay();
                    }
                });
            } else if (component instanceof JPanel) {
                addSounds((JPanel) component);
            }
        }
    }
}
