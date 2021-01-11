import main.DiusBowlingGame;
import main.OutOfRollException;

/**
 * The DiusBowlingApp class implements an application that
 * simply runs a specific version of bowling game.
 */
public class DiusBowlingApp {
    public static void main(String[] args) throws OutOfRollException {
        System.out.println("Hello World!");
        DiusBowlingGame game = new DiusBowlingGame();
        game.roll();
        game.roll();
        game.roll();
        game.roll();
        game.roll();
        game.roll();
        game.roll();
        game.roll();
        game.roll();
        game.roll();
        System.out.println("Game score is: " + game.score());
    }
}
