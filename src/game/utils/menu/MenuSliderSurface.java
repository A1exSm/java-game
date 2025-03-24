package game.utils.menu;
// Imports
import game.body.walkers.mobs.MobWalker;
import game.core.GameSound;
import game.core.GameWorld;
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
    private final MenuJSlider slider;
    private final MenuJButton button;
    private double tempVolume = 0.50;
    private final GameSound sound;
    private final SoundGroups group;
    // Constructor
    /**
     * Constructor for MenuSliderSurface.
     * Initializes the slider surface with specified {@code bounds}, game sound, and sound group.
     * Adds the slider surface to the parent component and sets its layout and focusable property.
     * @param parent The parent {@link JComponent} to which this slider surface will be added.
     * @param bounds The {@code bounds} of the slider surface in the format [x, y, width, height].
     * @param gameSound The {@link GameSound} instance to control the volume of.
     * @param group The {@link SoundGroups} instance representing the group of sounds to control the volume of.
     */
    protected MenuSliderSurface(JComponent parent, int[] bounds, GameSound gameSound, SoundGroups group) {
        parent.add(this);
        setLayout(null);
        setFocusable(false);
        if (group == null) {
            this.group = null;
            this.sound = gameSound;
        } else if (group.equals(SoundGroups.MOBS)) {
            this.group = group;
            sound = GameWorld.getMobs().get(0).soundFX.getTrackerSound();
        } else {
            throw new IllegalArgumentException("Error: Invalid group name in MenuSliderSurface: " + group);
        }
        JMenuPanel.boundErrorHandler(this, bounds);
        int sliderWidth = (int) (0.75f * bounds[2]);
        int buttonWidth = bounds[2] - sliderWidth;
        slider = new MenuJSlider(this, new int[] {0, 0, sliderWidth, bounds[3]});
        slider.setMaximum(200);
        button = new MenuJButton(this, String.valueOf(sound.getVolume()), new int[] {sliderWidth, 0, buttonWidth, bounds[3]}, true);
        button.setFont(VOLUME_FONT);
        addButtonListeners();
        addSliderListeners();
        updateVolumeAll();
    }
    // Methods
    /**
     * Updates the volume of the sound or sound group and reflects the changes on the button and slider.
     */
    private void updateVolumeAll() {
        int volume =  (int) (sound.getVolume()*100);
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
            if (sound.getVolume() == 0) {
                volume = tempVolume;
            } else {
                tempVolume = sound.getVolume();
                volume = 0;
            }
            if (group != null) {
                setGroupVolume(volume);
            } else {
                sound.setVolume(volume);
            }
            updateVolumeAll();
        });
    }
    /**
     * Sets the volume for all sounds in the group.
     * @param volume The volume to be set for the group.
     */
    private void setGroupVolume(double volume) {
        group.getSounds().forEach(sound -> sound.setVolume(volume));
    }
    /**
     * Adds change listeners to the slider to handle volume changes.
     */
    private void addSliderListeners() {
        slider.addChangeListener(e -> {
            if (group != null) {
                setGroupVolume(((double) slider.getValue())/100);
            } else {
                sound.setVolume(((double) slider.getValue()) / 100);
            }
            updateButtonVolume(slider.getValue());
        });
    }
}
