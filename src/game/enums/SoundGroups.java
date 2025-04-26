package game.enums;
import game.core.GameSound;
import java.util.ArrayList;

/**
 * An enum which represents the different sound groups.
 * Is used to control the volume of the sound in batches.
 */
public enum SoundGroups {
    // Enums
    /**
     * Represents the sound group for player sounds.
     */
    PLAYER,
    /**
     * Represents the sound group for mob sounds.
     */
    MOBS,
    /**
     * Represents the sound group for item sounds.
     */
    ITEMS,
    /**
     * Represents the sound group for environment sounds.
     */
    ENVIRONMENT,
    /**
     * Represents the sound group for UI sounds.
     */
    UI,
    /**
     * Represents the sound group for music.
     */
    MUSIC;
    // Fields
    private final ArrayList<GameSound> sounds;
    private double groupVolume = GameSound.getGlobalVolume();
    // Constructor
    SoundGroups() {
        sounds = new ArrayList<>();
    }
    // Getters
    /**
     * Gets the list of sounds in this group.
     * @return the list of sounds in this group.
     */
    public ArrayList<GameSound> getSounds() {
        return sounds;
    }
    /**
     * Adds a sound to this group.
     * @param sound the sound to add.
     */
    public void addSound(GameSound sound) {
        sounds.add(sound);
    }
    /**
     * Gets the volume of this group.
     * @return the volume of this group.
     */
    public double getVolume() {
        return groupVolume;
    }

    /**
     * Sets the volume of this group.
     * @param groupVolume the volume to set.
     */
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
