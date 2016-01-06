package minesweeper;

public class Square implements Consts
{
    private char mark;
    private boolean hidden;
    private boolean flagged;

    public void fill(char mark2)
    {
        mark = mark2;
    }

    public boolean isHidden()
    {
        return hidden;
    }

    public boolean isRevealed()
    {
        return !hidden;
    }

    public char reveal()
    {
        hidden = false;
        return mark;
    }

    public void hide()
    {
        hidden = true;
    }

    public char getMark()
    {
        return mark;
    }

    public void toggleFlag()
    {
        if(flagged)
            flagged = false;
        else
            flagged = true;
    }

    public boolean hasFlag()
    {
        return flagged;
    }

    /**
     * Constructor
     * @param mark
     * @param hidden
     */
    public Square(char mark, boolean hidden)
    {
        fill(mark);

        if(hidden)
            hide();
        else
            reveal();

        flagged = false;
    }

    /**
     * DEFAULT CONSTRUCTOR
     * Initializes the Square to mark = BLANK and hidden = true
     */
    public Square()
    {
        this(NONE, true);
    }
}
