package game.enums;

import game.core.GameSound;

import java.util.ArrayList;

public enum SoundGroups {
    // Enums
    PLAYER,
    MOBS,
    ITEMS,
    ENVIRONMENT,
    UI,
    MUSIC;

    // Fields
    private final ArrayList<GameSound> sounds;
    private double groupVolume = GameSound.getGlobalVolume();

    // Constructor
    SoundGroups() {
        sounds = new ArrayList<>();
    }

    // Getters
    public ArrayList<GameSound> getSounds() {
        return sounds;
    }
    public void addSound(GameSound sound) {
        sounds.add(sound);
    }
    public double getVolume() {
        return groupVolume;
    }
    public void setVolume(double groupVolume) {
        this.groupVolume = groupVolume;
        if (sounds.isEmpty()) {
            return;
        }
        for (GameSound sound : sounds) {
            sound.setVolume(groupVolume);
        }
    }
}
