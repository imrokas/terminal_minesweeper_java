package minesweeper;

/**
 * Players class
 * keeps players name and score
 */

public class Player
{   private String name;
    private int score = 0;

    /**
     * Constructor
     * gets the name of the player
     * @param name2
     */
    public Player(String name2)
    {   name = name2;
    }

    /**
     * getter
     * returns the name of the player
     * @return name
     */
    public String PlayersName()
    {   return name;
    }

    /**
     * getter
     * returns the score of the player
     * @return
     */
    public int PlayersScore()
    {   return score;
    }

    /**
     * Sets player's score value to 0
     * @param score
     */
    public void SetScoreToZero()
    {   score = 0;
    }
    
    /**
     * mutator
     * gets the number of scored points during the turn
     * and sums up to the old score
     * @param newPoints
     */
    public void AddToPlayersScore(int newPoints)
    {   score = score + newPoints;
    }

}
