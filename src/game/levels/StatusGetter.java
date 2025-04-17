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
 *
 */
// Class
public class StatusGetter {
    // Fields
    public static int count = 0;
    // Constructor
    // Methods
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
    public static HashMap<Integer, Boolean> getLevelData(Environments environment) {
        HashMap<Integer, Boolean> levelData = new HashMap<>();
        try (BufferedReader br = new BufferedReader(new FileReader(StatusSaver.PATH_1))) {
            String line;
            int lineNumber = 0;
            while ((line = br.readLine()) != null) {
                if (lineNumber == environment.index) { // finds the line which matches the environment
                    String[] levels = line.split(","); // splits line into levelData
                    for (String data : levels) {
                        String[] parts = data.split(":"); // splits levelData into level and locked status
                        int level = Integer.parseInt(parts[0]);
                        boolean isLocked = Boolean.parseBoolean(parts[1]);
                        levelData.put(level, isLocked);
                    }
                }
                lineNumber++;
            }
        } catch (IOException e) {
            Console.errorTrace("Error reading file for " + environment.name() + ": " + e.getMessage());
            return levelData;
        }
        return levelData;
    }
}
