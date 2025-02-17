package game;

public class Game {
    public static GameWorld game; // allow for game to restart
    public static void main(String[] args) {
        game = new GameWorld();
    }
}
