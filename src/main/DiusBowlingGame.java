package main;
import java.util.Random;

public class DiusBowlingGame {
    int maxTryCount;
    int maxPinCount;
    int maxFrameCount;
    int[] rolls;
    int currentRoll;

    public DiusBowlingGame(int pinCnt, int frameCnt, int tryCnt) {
        /* maximum number of roles can be 2 * frame# * try# (per frame) +
         2 bowls at the end if the last roll is either a strike or a spare */
        this.rolls = new int[2 * frameCnt * tryCnt + 2];
        this.maxTryCount = tryCnt;
        this.maxPinCount = pinCnt;
        this.maxFrameCount = frameCnt;
    }

    public DiusBowlingGame() {
        this(10, 10, 2);
    }

    /**
     * to record a roll result
     * @param p is the value of the roll done by the player
     */
    public void roll(int p) throws OutOfRollException {
        if(currentRoll == rolls.length) //maximum rolls reached
            throw new OutOfRollException();
        rolls[currentRoll++] = p;
    }

    /**
     * roll a ball randomly
     */
    public void roll() throws OutOfRollException {
        if(currentRoll == rolls.length) //maximum rolls reached
            throw new OutOfRollException();
        rolls[currentRoll++] = (new Random()).nextInt(maxPinCount + 1);
    }

    public int score() {
        int score = 0;
        int frame = 0;
        int totalStrikes = 0;

        for (int i = 0; i < maxFrameCount; i++) {
            if (isStrike(frame)) {
                score += maxPinCount + strikeBonus(frame);
                frame++;
                totalStrikes++;
            } else if (isSpare(frame)) {
                score += maxPinCount + spareBonus(frame);
                frame += maxTryCount;
            } else {
                score += RollsTotal(frame);
                frame += maxTryCount;
            }
        }

        //if all rolls are strike the score is the maximum regardless to those last 2 bonus rolls
        if(totalStrikes == maxFrameCount)
            score = maxFrameCount * maxPinCount * (maxTryCount + 1);
        return score;
    }

    private boolean isStrike(int frame) {
        return rolls[frame] == maxPinCount;
    }

    private boolean isSpare(int frame) {
        return RollsTotal(frame) == maxPinCount;
    }

    private int strikeBonus(int frame) {
        // The bonus scoring of a strike is the number of pins knocked down in the next two bowls.
        return rolls[frame + 1] + rolls[frame + 2];
    }

    private int spareBonus(int frame) {
        // The bonus scoring of a spare is the number of pins knocked down in the next frame.
        return rolls[frame + maxTryCount];
    }

    private int RollsTotal(int frame) {
        int totalPoint = 0;
        for (int i = 0; i < maxTryCount; i++) {
            totalPoint += rolls[frame + i];
        }
        return totalPoint;
    }
}
