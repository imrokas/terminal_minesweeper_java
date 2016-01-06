package minesweeper;

import java.util.Random;

/**
 * MineSweeper Backend
 */

public class Grid implements Consts
{
    //2D array of Squares to represent grid
    private Square[][] array;

    //width of the grid
    private final int width;

    //height of the grid
    private final int height;

    //number of mines
    private final int mines;

    //counts the points earned during the move
    private int score;

    /**
     * Constructor
     * @param mines in the grid
     * @param width of the grid
     * @param height of the grid
     * @throws UnableToCreateGrid
     */
    public Grid(int mines2, int width2, int height2) throws UnableToCreateGrid
    {
        if(width2 <= 3)
            throw new UnableToCreateGrid("width must be at least 3");
        if(height2 <= 3)
            throw new UnableToCreateGrid("Height must be at least 3");
        if(mines2 > (width2 * height2) / 2)
            throw new UnableToCreateGrid("Too many mines; mines can be at most half of the area of the grid.");
        if(mines2 < 3)
            throw new UnableToCreateGrid("Too little mines; there must be at least 3 mines.");

        //assign final values to objects
        mines = mines2;
        width = width2;
        height = height2;

        //create the array that represents the grid
        array = new Square[height][width];

        //fill the grid with mines and numbers
        fill();
    }

    /**
     * DEFAULT Constructor
     * 10 mines
     * 10 rows
     * 10 cols
     * @throws UnableToCreateGrid
     */
    public Grid() throws UnableToCreateGrid
    {
        this(10, 10, 10);
    }

    //fills the grid with mines and numbers
    private void fill()
    {
        /**
         * 1.Place mines randomly
         * 2.Fill the remaining squares with corresponding numbers
         */
        Random rand = new Random();

        //initialize the whole array
        for(int row = 0; row < height; row++)
        {
            for(int col = 0; col < width; col++)
            {
                array[row][col] = new Square();
            }
        }

        //1.fill the grid with mines
        for(int i=0; i < mines;)
        {
            int row = rand.nextInt(height - 1);
            int col = rand.nextInt(width -1);

            //check wether the selected square is empty, and if yes, put a mine in it
            if(array[row][col].getMark() != MINE)
            {
                array[row][col].fill(MINE);
                i++;
            }
        }

        //2.fill the remaining squares with corresponding numbers
        for(int i = 0; i < height; i++)
        {
            for(int j = 0; j < width; j++)
            {
                if(array[i][j].getMark() != MINE)
                {
                    int around;
                    try
                    {
                        around = minesAround(i,j);
                    }
                    catch(DirDoesNotExist e)
                    {
                        around = 0;
                    }
                    catch(SquareDoesNotExist e)
                    {
                        around = 0;
                    }

                    switch(around)
                    {
                        case 1: array[i][j].fill(ONE); break;
                        case 2: array[i][j].fill(TWO); break;
                        case 3: array[i][j].fill(THREE); break;
                        case 4: array[i][j].fill(FIVE); break;
                        case 6: array[i][j].fill(SIX); break;
                        case 7: array[i][j].fill(SEVEN); break;
                        case 8: array[i][j].fill(EIGHT); break;
                        default: array[i][j].fill(BLANK);
                    }

                }
            }
        }
    }

    /**
     * Checks that row and col are within the valid range of array
     * @param row
     * @param col
     * @throws SquareDoesNotExist if row or col are outside the grid
     */
    private void checkRange(int row, int col) throws SquareDoesNotExist
    {
        //check wether the row is in valid range
        if(row >= height || row < 0)
            throw new SquareDoesNotExist("Row out of range.");

        //check wether the col is in valid range
        if(col >= width || col < 0)
            throw new SquareDoesNotExist("Column out of range.");
    }

    /**
     * Returns the size of the grid
     * To use: getSize()[0] is width
     * getSize()[1] is height
     *
     * @return{width, height}
     */
    public int[] getSize()
    {
        return new int[] {width, height};
    }

    /**
     * Detects how many mines are around the square
     * @param row the square is in
     * @param col the square is in
     * @return number of the mines around the square
     * @throws DirDoesNotExist
     * @throws SquareDoesNotExist if the row or col is invalid
     * DIRMAX is last number of direction in loops, check Consts
     */
    public int minesAround(int row, int col) throws DirDoesNotExist, SquareDoesNotExist
    {
        //check wether the row and col are in valid range
        checkRange(row, col);

        int around = 0;

        for(int dir = 0; dir <= DIRMAX; dir++)
        {
            if(getAdjacentMark(row, col, dir) == MINE)
                around++;
        }

        return around;
    }

    /**
     * Returns the mark adjacent to array[row][col] in direction dir
     * @param row
     * @param col
     * @param dir
     * @return the mark adjacent to array[row][col], NONE('\0') if wall or corner
     * @throws DirDoesNotExist
     * @throws SquareDoesNotExist
     */
    private char getAdjacentMark(int row, int col, int dir) throws DirDoesNotExist, SquareDoesNotExist
    {
        return getAdjacentSquare(row, col, dir).getMark();
    }

    /**
     * Returns the square next to array[row][col] in direction dir
     * if [row][col] references a wall or a corner, return a new blank square
     *
     * @param row of origin square
     * @param col of origin square
     * @param dir of adjacent square relative to origin square
     * @return Square adjacent to origin square in direction dir
     * @throws SquareDoesNotExist when row or col refers to coordinates outside the grid
     * @throws DirDoesNotExist when parameter dir is not a direction in Consts (i.e. not 0 <= dir <= 7)
     */
    private Square getAdjacentSquare(int row, int col, int dir) throws SquareDoesNotExist, DirDoesNotExist
    {
        //chech if row and col are withing the grid range
        checkRange(row, col);

        switch(dir)
        {
            case TOPLEFT:
                try
                {
                    checkRange(row - 1, col -1);
                    return array[row-1][col-1];
                }
                catch(SquareDoesNotExist e)
                {
                    //if on top left corner
                    return new Square();
                }
            case TOP:
                try
                {
                    checkRange(row - 1, col);
                    return array[row-1][col];
                }
                catch(SquareDoesNotExist e)
                {
                    //if on top wall
                    return new Square();
                }
            case TOPRIGHT:
                try
                {
                    checkRange(row - 1, col + 1);
                    return array[row-1][col+1];
                }
                catch(SquareDoesNotExist e)
                {
                    //if on top right corner
                    return new Square();
                }
            case RIGHT:
                try
                {
                    checkRange(row, col + 1);
                    return array[row][col+1];
                }
                catch(SquareDoesNotExist e)
                {
                    //if on right wall
                    return new Square();
                }
            case BOTRIGHT:
                try
                {
                    checkRange(row + 1, col + 1);
                    return array[row+1][col+1];
                }
                catch(SquareDoesNotExist e)
                {
                    //if on bottom right corner
                    return new Square();
                }
            case BOT:
                try
                {
                    checkRange(row + 1, col);
                    return array[row+1][col];
                }
                catch(SquareDoesNotExist e)
                {
                    //if on bottom wall
                    return new Square();
                }
            case BOTLEFT:
                try
                {
                    checkRange(row + 1, col - 1);
                    return array[row+1][col-1];
                }
                catch(SquareDoesNotExist e)
                {
                    //in on bottom left corner
                    return new Square();
                }
            case LEFT:
                try
                {
                    checkRange(row, col - 1);
                    return array[row][col-1];
                }
                catch(SquareDoesNotExist e)
                {
                    //if on the left wall
                    return new Square();
                }
            default:
                throw new DirDoesNotExist("Direction " + dir + " does not exist.");
        }
    }

    /**
     * Returns the mark at array[row][col]
     * @param row
     * @param col
     * @return what is at that particular square
     * @throws SquareDoesNotExist
     */
    public char getMarkAt(int row, int col) throws SquareDoesNotExist
    {
        //check wether the square is within the grid
        checkRange(row, col);

        return array[row][col].getMark();
    }

    /**
     * Reveals the square at coords (row, col)
     * If revealed square is blank, reveals adjacent squares
     *
     * @param row
     * @param col
     * @throws SquareDoesNotExist when row, col is out of range
     * @throws SquareHasFlag if row, col has a flag and cannot be revealed
     * @throws SquareAlreadyRevealed if the square was already revealed
     */
    public void reveal(int row, int col) throws SquareDoesNotExist, SquareHasFlag, SquareAlreadyRevealed
    { 
        //check if square on the grid, if not, throw SquareDoesNotExist
        checkRange(row, col);

        //check wether is there a flag in a square
        if(hasFlag(row, col))
            throw new SquareHasFlag("Square has a flag, cannot reveal.");

        //check wether is the square already revealed
        if(isRevealed(row, col))
            throw new SquareAlreadyRevealed("Square is already revealed");

        if(array[row][col].isRevealed())
            return;

        array[row][col].reveal();

        /**
         * score counter
         * if BLANK add 1 point
         * else add number in square multiplied by 2
         */
        if(array[row][col].getMark() == BLANK)
            score += 1;
        else if(array[row][col].getMark() != '*')
            score += (2 * (array[row][col].getMark() - 48));
        else if (array[row][col].getMark()=='*')
            score = 0;
        

        if(array[row][col].getMark() == BLANK)
        {
            try //top left
            {
                reveal(row - 1, col - 1);
            }
            catch(SquareDoesNotExist e){}
            catch(SquareAlreadyRevealed e){}

            try //top
            {
                reveal(row - 1, col);
            }
            catch(SquareDoesNotExist e){}
            catch(SquareAlreadyRevealed e){}

            try //top right
            {
                reveal(row - 1, col + 1);
            }
            catch(SquareDoesNotExist e){}
            catch(SquareAlreadyRevealed e){}

            try //right
            {
                reveal(row, col + 1);
            }
            catch(SquareDoesNotExist e){}
            catch(SquareAlreadyRevealed e){}

            try //bottom right
            {
                reveal(row + 1, col + 1);
            }
            catch(SquareDoesNotExist e){}
            catch(SquareAlreadyRevealed e){}

            try //bottom
            {
                reveal(row + 1, col);
            }
            catch(SquareDoesNotExist e){}
            catch(SquareAlreadyRevealed e){}

            try // bottom left
            {
                reveal(row + 1, col);
            }
            catch(SquareDoesNotExist e){}
            catch(SquareAlreadyRevealed e){}

            try //left
            {
                reveal(row, col - 1);
            }
            catch(SquareDoesNotExist e){}
            catch(SquareAlreadyRevealed e){}
        }
    }

    /**
     * Returns if the square in coords row, col is hidden or not
     *
     * @param row
     * @param col
     * @return true if it is hidden, false otherwise
     * @throws SquareDoesNotExist
     *
     * @see {@link #isRevealed(int, int)}
     * @see Square #isHidden()
     */
    public boolean isHidden(int row, int col) throws SquareDoesNotExist
    {
        //check if square is within the grid
        checkRange(row, col);

        return array[row][col].isHidden();
    }

    /**
     * Returns if the square in coords (row, col) is revealed or not
     *
     * @param row
     * @param col
     * @return true if it is revealed, false otherwise
     * @throws SquareDoesNotExist
     *
     * @see {@link #isHidden(int, int)
     * @see Square #isRevealed()
     */
    public boolean isRevealed(int row, int col) throws SquareDoesNotExist
    {
        checkRange(row, col);

        return array[row][col].isRevealed();
    }

    /**
     * Adds a flag in (row, col)
     *
     * if row, col does not exist, does not do anything
     *
     * @param row
     * @param col
     * @throws SquareDoesNotExist if (row, col) are invalid
     * @throws SquareAlreadyRevealed
     */
    public void toogleFlag(int row, int col) throws SquareDoesNotExist, SquareAlreadyRevealed
    {
        checkRange(row, col);

        if(isRevealed(row, col))
            //cannot flag because the square is revealed
            throw new SquareAlreadyRevealed("Cannot be flagged; square is revealed.");

        array[row][col].toggleFlag();
    }

    /**
     * check wether the square is flagged already
     *
     * @param row
     * @param col
     * @return true if yes, otherwise false
     * @throw SquareDoesNotExist
     */
    public boolean hasFlag(int row, int col)
    {
        try
        {
            checkRange(row, col);
        }
        catch(SquareDoesNotExist e)
        {
            return false;
        }

        return array[row][col].hasFlag();
    }

    /**
     * Check wether there are any flags around
     *
     * @param row
     * @param col
     * @return number or flags around, or 0 if invalid square
     * @throw SquareDoesNotExist
     */
    public int flagsAround(int row, int col)
    {
        try
        {
            checkRange(row, col);
        }
        catch(SquareDoesNotExist e)
        {
            return 0;
        }

        int around = 0;

        for(int dir = 0; dir <= DIRMAX; dir++)
        {
            try
            {
                if(getAdjacentSquare(row, col, dir).hasFlag())
                    around++;
            }
            catch(SquareDoesNotExist e){}
            catch(DirDoesNotExist e){}
        }

        return around;
    }

    /**
     * Returns a string representation of the grid, with hidden square in black
     * @returns Grid as a string
     * @see #solutionToString()
     * @Override
     */
    @Override
    public String toString()
    {
        String s = new String();

        s += "\n";
        //add letters on top
        s += "     "; //5 spaces for padding
        char letter = 'A';
        for(int i = 0; i < width; i++)
        {
            s += letter;
            s += ' '; //space for padding
            letter++;
        }
        s += "\n";

        //add top border
        s +="   + ";
        for(int i = 0; i < width; i++)
        {
            s += "- ";
        }
        s += "+";
        s += "\n";

        for(int i = 0; i < height; i++)
        {
            //add number at start of the line
            if((i+1)/10 == 0) //if only one digit, pad with one space
            {
                s += " ";
                s += (i + 1);
            }
            else
            {
                s += (i + 1);
            }
            s += " | ";

            for (int j = 0; j < width; j++)
            {
                if(array[i][j].isRevealed())
                    s += array[i][j].getMark();
                else if(array[i][j].hasFlag())
                    s += FLAG;
                else
                    s += HIDDEN;

                s += " ";
            }
            s += "| ";

            //add number at the end of the line
            s += (i + 1);

            s += "\n";
        }

        //add bottom border
        s += "   +";
        for(int i = 0; i < width; i++)
        {
            s += " -";
        }
        s += " +";
        s += "\n";

        //add letters at the bottom
        s += "     ";
        letter = 'A';
        for(int i = 0; i < width; i++)
        {
            s += letter;
            s += " ";
            letter++;
        }
        s += "\n";

        return s;
    }

    /**
     * returns a string representing the grid with all squares revealed
     * @return string of revealed grid
     * @see #toString()
     */
    public String solutionToString()
    {
        String s = new String();

        s += "\n";
        //add letters on top
        s += "     "; //5 spaces for padding
        char letter = 'A';
        for(int i = 0; i < width; i++)
        {
            s += letter;
            s += ' '; //space for padding
            letter++;
        }
        s += "\n";

        //add top border
        s +="   + ";
        for(int i = 0; i < width; i++)
        {
            s += "- ";
        }
        s += "+";
        s += "\n";

        for(int i = 0; i < height; i++)
        {
            //add number at the start of the line
            if((i+1)/10 == 0)
            {
                s += " ";
                s += (i + 1);
            }
            else
                s += (i + 1);

            s += " | ";

            for(int j = 0; j < width; j++)
            {
                s += array[i][j].getMark();
                s += " ";
            }

            s += "| ";
            //add number at the end of the line
            s += (i+1);

            s += "\n";
        }

        //add bottom border
        s += "   + ";
        for(int i = 0; i < width; i++)
        {
            s += "- ";
        }
        s += "+";
        s += "\n";

        //add letters at the bottom
        s += "     ";
        letter = 'A';
        for(int i = 0; i < width; i++)
        {
            s += letter;
            s += " ";
            letter++;
        }
        s += "\n";

        return s;
    }

    public int getScore()
    {   int myScore = score;
        score = 0;
        return myScore;
    }
}
