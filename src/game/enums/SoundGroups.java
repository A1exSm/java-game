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
}
