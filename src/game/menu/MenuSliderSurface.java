package game.menu;
// Imports
import game.core.GameSound;
import game.core.GameView;
import game.core.console.Console;
import game.enums.SoundGroups;
import javax.swing.*;
import java.awt.*;
// Class
/**
 * The MenuSliderSurface class extends {@link JPanel} to create a custom slider surface for the game menu.
 * It includes a slider and a button to control the volume of game sounds or sound groups.
 */
final class MenuSliderSurface extends JPanel {
    // Fields
    private static final Font VOLUME_FONT = new Font("Niagara Solid", Font.BOLD, 25);
    private static final Font VOLUME_FONT_MAC = new Font("Niagara Solid", Font.BOLD, 10);
    private final MenuJSlider slider;
    private final MenuJButton button;
    private double tempVolume = 0.50;
    private final GameSound sound;
    private final SoundGroups group;
    private final JLabel label;
    // Constructors
    /**
     * This Constructor is used to initialise the slider surface with a {@link GameSound} instance,
     * where the parent uses no layout manager.
     * @param parent The parent {@link JComponent} to which this slider surface will be added.
     * @param x The x-coordinate of the slider surface.
     * @param y The y-coordinate of the slider surface.
     * @param width The width of the slider surface.
     * @param height The height of the slider surface.
     * @param sound The {@link GameSound} instance to control the volume of.
     * @param title The text to be displayed on the label - the name of the {@code group} or {@code gameSound}.
     */
    MenuSliderSurface(JComponent parent, int x, int y, int width, int height, GameSound sound, String title) {
        this(parent, x, y, width, height, sound, null, title);
    }
    /**
     * This Constructor is used to initialise the slider surface with a {@link SoundGroups} instance,
     * where the parent uses no layout manager.
     * @param parent The parent {@link JComponent} to which this slider surface will be added.
     * @param x The x-coordinate of the slider surface.
     * @param y The y-coordinate of the slider surface.
     * @param width The width of the slider surface.
     * @param height The height of the slider surface.
     * @param group The {@link SoundGroups} instance representing the group of sounds to control the volume of.
     * @param title The text to be displayed on the label - the name of the {@code group} or {@code gameSound}.
     */
    MenuSliderSurface(JComponent parent, int x, int y, int width, int height, SoundGroups group, String title) {
        this(parent, x, y, width, height, null, group, title);
    }

    /**
     * This Constructor is used to initialise the slider surface with a {@link GameSound} instance,
     * where the parent uses a layout manager.
     * @param parent The parent {@link JComponent} to which this slider surface will be added.
     * @param width The width of the slider surface.
     * @param height The height of the slider surface.
     * @param sound The {@link GameSound} instance to control the volume of.
     * @param title The text to be displayed on the label - the name of the {@code group} or {@code gameSound}.
     */
    MenuSliderSurface(JComponent parent, int width, int height, GameSound sound, String title) {
        this(parent, 0, 0, width, height, sound, null, title);

    }

    /**
     * This Constructor is used to initialise the slider surface with a {@link SoundGroups} instance,
     * where the parent uses a layout manager.
     * @param parent The parent {@link JComponent} to which this slider surface will be added.
     * @param width The width of the slider surface.
     * @param height The height of the slider surface.
     * @param group The {@link SoundGroups} instance representing the group of sounds to control the volume of.
     * @param title The text to be displayed on the label - the name of the {@code group} or {@code gameSound}.
     */
    MenuSliderSurface(JComponent parent, int width, int height, SoundGroups group, String title) {
        this(parent, 0, 0, width, height, null, group, title);
    }

    /**
     * Constructor for MenuSliderSurface.
     * Initializes the slider surface with specified {@code bounds}, game sound, and sound group.
     * Adds the slider surface to the parent component and sets its layout and focusable property.
     * @param parent The parent {@link JComponent} to which this slider surface will be added.
     * @param x The x-coordinate of the slider surface.
     * @param y The y-coordinate of the slider surface.
     * @param width The width of the slider surface.
     * @param height The height of the slider surface.
     * @param gameSound The {@link GameSound} instance to control the volume of.
     * @param group The {@link SoundGroups} instance representing the group of sounds to control the volume of.
     * @param labelText The text to be displayed on the label - the name of the {@code group} or {@code gameSound}.
     */
    MenuSliderSurface(JComponent parent, int x, int y, int width, int height, GameSound gameSound, SoundGroups group, String labelText) {
        super();
        parent.add(this);
        setLayout(null);
        setFocusable(false);
        label = new JLabel(labelText);
        if (parent.getLayout() != null) {
            setSize(width, height);
        } else {
            setBounds(x, y, width, height);
            initLabel(parent, new int[]{x, y, width, height});
        }
        int sliderWidth = (int) (0.75f * width);
        int buttonWidth = width - sliderWidth;
        slider = new MenuJSlider(this, sliderWidth, height);
        slider.setMaximum(200);
        this.group = group;
        sound = group == null ? gameSound : null;
        button = new MenuJButton(this, String.valueOf(getVolume()), new int[] {sliderWidth, 0, buttonWidth, height}, true);
        if (System.getProperty("os.name").contains("Mac")) {
            button.setFont(VOLUME_FONT_MAC);
            label.setFont(JMenuPanel.MAC_FONT);
        } else {
            button.setFont(VOLUME_FONT);
            label.setFont(GameView.DISPLAY_FONT);
        }
        addButtonListeners();
        addSliderListeners();
        updateVolumeAll();
    }

    /**
     * Constructor for MenuSliderSurface, for setting global volume ONLY. <br>
     * Initializes the slider surface with specified bounds and label text.
     * Adds the slider surface to the parent component and sets its layout and focusable property.
     * @param parent The parent {@link JComponent} to which this slider surface will be added.
     * @param x The x-coordinate of the slider surface.
     * @param y The y-coordinate of the slider surface.
     * @param width The width of the slider surface.
     * @param height The height of the slider surface.
     * @param labelText The text to be displayed on the label - e.g. "Global Volume".
     */
    MenuSliderSurface(JComponent parent, int x, int y, int width, int height, String labelText) {
        super();
        parent.add(this);
        sound = null;
        group = null;
        setLayout(null);
        setFocusable(false);
        label = new JLabel(labelText);
        if (parent.getLayout() != null) {
            setSize(width, height);
        } else {
            setBounds(x, y, width, height);
            initLabel(parent, new int[]{x, y, width, height});
        }
        int sliderWidth = (int) (0.75f * width);
        int buttonWidth = width - sliderWidth;
        slider = new MenuJSlider(this, sliderWidth, height);
        slider.setMaximum(200);
        button = new MenuJButton(this, String.valueOf(GameSound.getGlobalVolume()), new int[] {sliderWidth, 0, buttonWidth, height}, true);
        if (System.getProperty("os.name").contains("Mac")) {
            button.setFont(VOLUME_FONT_MAC);
            label.setFont(JMenuPanel.MAC_FONT);
        } else {
            button.setFont(VOLUME_FONT);
            label.setFont(GameView.DISPLAY_FONT);
        }
        addGlobalButtonListener();
        addGlobalSliderListeners();
        updateButtonVolume((int) (GameSound.getGlobalVolume() * 100));
        updateSliderVolume((int) (GameSound.getGlobalVolume() * 100));
    }

    // Methods
    private void initLabel(JComponent parent, int[] bounds) {
        label.setOpaque(true);
        label.setBackground(new Color(115, 102, 73));
        label.setForeground(Color.WHITE);
        label.setFont(GameView.DISPLAY_FONT);
        label.setHorizontalAlignment(SwingConstants.CENTER);
        parent.add(label);
        JMenuPanel.boundErrorHandler(label, new int[]{bounds[0] - 221, bounds[1], 156, bounds[3]});
    }
    /**
     * Updates the volume of the sound or sound group and reflects the changes on the button and slider.
     */
    private void updateVolumeAll() {
        int volume = (int) (getVolume() * 100);
        updateButtonVolume(volume);
        updateSliderVolume(volume);
    }
    /**
     * Updates the volume displayed on the button.
     * @param volume The volume to be displayed on the button.
     */
    private void updateButtonVolume(int volume) {
        button.setText(volume + " %");
    }
    /**
     * Updates the volume displayed on the slider.
     * @param volume The volume to be displayed on the slider.
     */
    private void updateSliderVolume(int volume) {
        slider.setValue(volume);
    }
    /**
     * Adds action listeners to the button to handle volume changes.
     */
    private void addButtonListeners() {
        button.addActionListener(e -> {
            double volume;
            if (getVolume() == 0) {
                volume = tempVolume;
            } else {
                tempVolume = getVolume();
                volume = 0;
            }
            setVolume(volume);
            updateVolumeAll();
        });
    }

    /**
     * Adds change listeners to the slider to handle volume changes.
     */
    private void addSliderListeners() {
        slider.addChangeListener(e -> {
            setVolume(((double) slider.getValue()) / 100);
            updateButtonVolume(slider.getValue());
        });
    }
    /**
     * Gets the volume of the sound or sound group.
     * @return The volume of the sound or sound group.
     */
    private double getVolume() {
        return sound != null ? sound.getVolume() : group.getVolume();
    }
    /**
     * Sets the volume of the sound or sound group.
     * @param volume The volume to be set for the sound or sound group.
     */
    private void setVolume(double volume) {
        if (sound != null) {
            sound.setVolume(volume);
        } else {
            group.setVolume(volume);
        }
    }
    // global functions
    private void addGlobalButtonListener() {
        button.addActionListener(e -> {
            double volume;
            if (GameSound.getGlobalVolume() == 0) {
                volume = tempVolume;
            } else {
                tempVolume = GameSound.getGlobalVolume();
                volume = 0;
            }
            GameSound.setGlobal(volume);
            updateButtonVolume((int) (GameSound.getGlobalVolume() * 100));
            updateSliderVolume((int) (GameSound.getGlobalVolume() * 100));
        });
    }
    private void addGlobalSliderListeners() {
        slider.addChangeListener(e -> {
            GameSound.setGlobal(((double) slider.getValue()) / 100);
            updateButtonVolume(slider.getValue());
        });
    }
}