package game.utils.menu;
// Imports

import game.Game;
import game.body.walkers.mobs.MobWalker;
import game.core.GameSound;
import game.core.GameWorld;

import javax.swing.*;
import java.awt.*;


// Class
class MenuSliderSurface extends JPanel {
    // Fields
    private final Font VOLUME_FONT = new Font("Niagara Solid", Font.BOLD, 25);
    private final MenuJSlider slider;
    private final MenuJButton button;
    private double tempVolume = 0.50;
    private final GameSound sound;
    private final String group;
    // Constructor
    protected MenuSliderSurface(JComponent parent, int[] bounds, GameSound gameSound, String group) {
        parent.add(this);
        setLayout(null);
        setFocusable(false);
        if (group == null) {
            this.group = null;
            this.sound = gameSound;
        } else if (group.equals("Mobs")) {
            this.group = group;
            sound = GameWorld.getMobs().get(0).soundFX.getTrackerSound();
        } else {
            throw new IllegalArgumentException("Error: Invalid group name in MenuSliderSurface");
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
    private void updateVolumeAll() {
        int volume =  (int) (sound.getVolume()*100);
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

    private void setGroupVolume(double volume) {
        switch (group) {
            case "Mobs" -> {
                for (MobWalker mob : GameWorld.getMobs()) {
                    mob.soundFX.setVolumeAll(volume);
                }
            }
            default -> {System.err.println("Error: Invalid group name in MenuSliderSurface");}
        }
    }

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
