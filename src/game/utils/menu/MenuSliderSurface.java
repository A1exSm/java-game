package game.utils.menu;
// Imports

import game.Game;
import javax.swing.*;
import java.awt.*;


// Class
class MenuSliderSurface extends JPanel {
    // Fields
    private final Font VOLUME_FONT = new Font("Niagara Solid", Font.BOLD, 25);
    private final MenuJSlider slider;
    private final MenuJButton button;
    private double tempVolume = 0.50;
    // Constructor
    protected MenuSliderSurface(JComponent parent, int[] bounds) {
        parent.add(this);
        setLayout(null);
        JMenuPanel.boundErrorHandler(this, bounds);
        int sliderWidth = (int) (0.75f * bounds[2]);
        int buttonWidth = bounds[2] - sliderWidth;
        slider = new MenuJSlider(this, new int[] {0, 0, sliderWidth, bounds[3]});
        slider.setMaximum(200);
        button = new MenuJButton(this, String.valueOf(Game.getVolume()), new int[] {sliderWidth, 0, buttonWidth, bounds[3]}, true);
        button.setFont(VOLUME_FONT);
        addButtonListeners();
        addSliderListeners();
        updateVolumeAll();
    }
    // Methods
    private void updateVolumeAll() {
        int volume =  (int) (Game.getVolume()*100);
        updateButtonVolume(volume);
        updateSliderVolume(volume);
    }

    private void updateButtonVolume(int volume) {
        button.setText(volume + " %");
    }

    private void updateSliderVolume(int volume) {
        slider.setValue(volume);
    }

    private void addButtonListeners() {
        button.addActionListener(e -> {
            if (Game.getVolume() == 0) {
                Game.setVolume(tempVolume);
                updateVolumeAll();
            } else {
                tempVolume = Game.getVolume();
                Game.setVolume(0);
                updateVolumeAll();
            }
        });
    }

    private void addSliderListeners() {
        slider.addChangeListener(e -> {
            Game.setVolume(((double) slider.getValue())/100);
            updateButtonVolume(slider.getValue());
        });
    }
}
