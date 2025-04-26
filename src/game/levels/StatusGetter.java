package game.levels;
// Imports
import game.core.console.Console;
import game.enums.Environments;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

/**
 * Handles the fetching of data from files.
 */
// Class
public class StatusGetter {
    // Fields
    private static int count = 0;
    // Methods
    /**
     * Checks if the file is present and valid.
     * @param path The path to the file.
     * @return True if the file is present and valid, false otherwise.
     */
    public static boolean isFilePresent(String path) {
        try (FileReader fr = new FileReader(path)) {
            Console.debug("Checking save file " + (++count) + " / 3");
            BufferedReader br = new BufferedReader(fr);
            String testLine = br.readLine();
            if (testLine == null) {
                throw new IOException(Console.exceptionMessage("Empty save file"));
            } else if (!testLine.equals("Level Data")) {
                throw new IOException(Console.exceptionMessage("Invalid save file format, first line reads: " + testLine));
            }
        } catch (IOException e) {
            if (e instanceof FileNotFoundException) {
                Console.warning("Save file not found: " + path);
            }
            return false;
        }
        return true;
    }
    /**
     * Retrieves the level data from the file.
     * If clearOnError is true, the returned hashmap will be cleared on error.
     * @param environment The environment to get the level data for.
     * @param path The path to the file.
     * @param clearOnError Whether to clear the level data on error.
     * @return A HashMap of level data.
     */
    public static HashMap<Integer, Boolean> getLevelData(Environments environment, String path, boolean clearOnError) {
        HashMap<Integer, Boolean> levelData = new HashMap<>();
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String line;
            int lineNumber = 0;
            while ((line = br.readLine()) != null) {
                if (lineNumber == environment.index) { // finds the line which matches the environment
                    String[] levels = line.split(","); // splits line into levelData
                    for (String data : levels) {
                        String[] parts = data.split(":"); // splits levelData into level and locked status
                        int level = Integer.parseInt(parts[0]);
                        if (!parts[1].equalsIgnoreCase("true") && !parts[1].equalsIgnoreCase("false")) {
                            throw new IllegalArgumentException("Invalid boolean level data: " + data);
                        } else {
                            boolean isLocked = Boolean.parseBoolean(parts[1]);
                            levelData.put(level, isLocked);
                        }
                    }
                }
                lineNumber++;
            }
        } catch (Exception e) {
            Console.errorTrace("Error reading file for " + environment.name() + ": " + e.getMessage());
            if (clearOnError) {
                levelData.clear(); // ensures negative feedBack
            }
            return levelData;
        }
        return levelData;
    }
    /**
     * Checks if the given file path is valid,
     * meaning that it returns a non-empty set of level data
     * for all environments.
     * @param path The path to the file.
     * @return True if the level is allowed, false otherwise.
     */
    public static boolean isAllowed(String path) {
        if (getLevelData(Environments.MAGIC_CLIFF, path, true).isEmpty()) {
            return false;
        } else if (getLevelData(Environments.HAUNTED_FOREST, path, false).isEmpty()) {
            return false;
        }
        return !getLevelData(Environments.GOTHIC_CEMETERY, path, false).isEmpty();
    }
}
