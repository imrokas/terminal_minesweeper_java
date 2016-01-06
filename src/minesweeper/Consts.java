package minesweeper;

public interface Consts
{
    //characters to represent marks on grid
    public static final char BLANK = ' ';
    public static final char MINE  = '*';
    public static final char ONE   = '1';
    public static final char TWO   = '2';
    public static final char THREE = '3';
    public static final char FOUR  = '4';
    public static final char FIVE  = '5';
    public static final char SIX   = '6';
    public static final char SEVEN = '7';
    public static final char EIGHT = '8';

    //hidden square character
    public static final char HIDDEN = (char) 2558;

    //character that represents flag
    public static final char FLAG = 'F';

    //character that represents wall or corners outside the grid, or just created empty squares
    public static final char NONE = '\0';

    //numbers that represent directions
    public static final int TOPLEFT  = 0;
    public static final int TOP      = 1;
    public static final int TOPRIGHT = 2;
    public static final int RIGHT    = 3;
    public static final int BOTRIGHT = 4;
    public static final int BOT      = 5;
    public static final int BOTLEFT  = 6;
    public static final int LEFT     = 7;

    //last direction to use in for loops
    public static final int DIRMAX   = 7;
}
