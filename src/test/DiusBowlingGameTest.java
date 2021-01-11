package test;

import static org.junit.Assert.*;

import main.DiusBowlingGame;
import main.OutOfRollException;
import org.junit.Before;
import org.junit.Test;

public class DiusBowlingGameTest {

    private DiusBowlingGame game;

    void rollMany(int n, int pins, DiusBowlingGame g) throws OutOfRollException {
        for (int i = 0; i < n; i++)
            g.roll(pins);
    }

    @Before
    public void setUp() {
        this.game = new DiusBowlingGame();
    }

    @Test
    public void testMaxRollsReachedException() {
        try {
            rollMany(100, 0, game);
        } catch (OutOfRollException e) {
            assertFalse("Too many rolls!", false);
        }
    }

    @Test
    public void testAllStrikesGet300FinalBonusRollsIgnored() throws OutOfRollException {
        for (int i = 0; i < 10; i++)
            game.roll(10);
        assertEquals(300, game.score());
    }

    @Test
    public void testAllStrikesGet300FinalBonusRollsUsed() throws OutOfRollException {
        for (int i = 0; i < 10; i++)
            game.roll(10);
        game.roll(1);
        game.roll(6);
        assertEquals(300, game.score());
    }

    @Test
    public void testZero() {
        try {
            rollMany(20, 0, game);
        } catch (OutOfRollException e) {
            e.printStackTrace();
        }
        assertEquals(0, game.score());
    }

    @Test
    public void testAll4s() throws OutOfRollException {
        rollMany(20, 4, game);
        assertEquals(80, game.score());
    }

    @Test
    public void testOneNoneSpare() throws OutOfRollException {
        game.roll(4);
        game.roll(4);
        rollMany(18, 0, game);
        assertEquals(8, game.score());
    }

    @Test
    public void testOneSpare() throws OutOfRollException {
        game.roll(4);
        game.roll(6);
        game.roll(5);
        rollMany(17, 0, game);
        assertEquals(20, game.score());
    }

    @Test
    public void testOneStrike() throws OutOfRollException {
        game.roll(10);
        game.roll(5);
        game.roll(4);
        rollMany(16, 0, game);
        assertEquals(28, game.score());
    }

    @Test
    public void testLastRollIsStrike() throws OutOfRollException {
        rollMany(18, 0, game);
        game.roll(10);
        //2 bonus rolls here
        game.roll(3);
        game.roll(4);
        assertEquals(17, game.score());
    }

    @Test
    public void testLastRollIsStrikeAlsoBonusRolesAreStrike() throws OutOfRollException {
        rollMany(18, 0, game);
        game.roll(10);
        //2 bonus rolls here
        game.roll(10);//no more bonus here
        game.roll(10);//no more bonus here
        assertEquals(30, game.score());
    }

    @Test
    public void testLastRollIsSpare() throws OutOfRollException {
        rollMany(19, 0, game);
        game.roll(10);
        //1 bonus roll here
        game.roll(6);
        assertEquals(16, game.score());
    }

    @Test
    public void testLastRollIsSpareBonusRoleIsStrike() throws OutOfRollException {
        rollMany(19, 0, game);
        game.roll(10);
        //1 bonus roll here
        game.roll(10);//no more bonus on bonus roll
        assertEquals(20, game.score());
    }
}