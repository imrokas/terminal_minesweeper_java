package minesweeper;
/* *
 * Exception thrown when a direction is not in the rage specified in Consts
 * @see Consts
 */
class DirDoesNotExist extends Exception
{
    public DirDoesNotExist(String s)
    {
        super(s);
    }
}

/**
 * Exception thrown when a mark is not described in Consts
 * @see Consts
 */
class MarkDoesNotExist extends Exception
{
    public MarkDoesNotExist(String s)
    {
        super(s);
    }
}

/**
 * Exception thrown when calling a Square coordinate not in the Grid
 * @see Grid
 */
class SquareDoesNotExist extends Exception
{
    public SquareDoesNotExist(String s)
    {
        super(s);
    }
}

/* *
 * Exception thrown when creation of Grid failed because of improper dimensions
 * @see Grid # Grid()
 */
class UnableToCreateGrid extends Exception
{
    public UnableToCreateGrid(String s)
    {
        super(s);
    }
}

/**
 * Exception thrown when trying to reveal a Square that has already been
 * revealed
 */
class SquareAlreadyRevealed extends Exception
{
    public SquareAlreadyRevealed(String s)
    {
        super(s);
    }
}

/**
 * Exception thrown when trying to reveal a Square that has a flag
 */
class SquareHasFlag extends Exception
{
    public SquareHasFlag(String s)
    {
        super(s);
    }
}

