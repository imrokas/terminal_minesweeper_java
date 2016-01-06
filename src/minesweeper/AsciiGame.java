package minesweeper;

import java.util.InputMismatchException;
import java.util.Scanner;
import java.lang.StringIndexOutOfBoundsException;


/**
 *
 * @author Roger
 */
public class AsciiGame implements Consts
{
    private int noP = 0;
    private int mines;
    private int width;
    private int height;
    private Grid grid;
    //has current game been lost
    private boolean lost;
    //has current game been won?
    private boolean won;
    //does player want to stop playing
    private boolean done;
    //players
    /**
     *
     */
    public static Player player1;
    /**
     * 
     */
    public static Player player2;

    /**
     *
     */
    public AsciiGame()
    {
        welcome();
        noP = NumberOfPlayers();
        newGame();

        if (noP == 1)
            play();
        else
           play2();
    }

    private void welcome()
    {
        System.out.println("Welcome to MineSweeper!");
    }

    private void newGame()
    {
        done = false;
        lost = false;
        won = false;

        chooseSize();
        try
        {
            grid = new Grid(mines, width, height);
        }
        catch(UnableToCreateGrid e)
        {
            System.out.println("Error ");
            System.out.println(e.getMessage());
            System.out.println("Game will now quit.");
            System.exit(0);
        }
    }

    private void chooseSize()
    {
        Scanner in = new Scanner(System.in);

        //ask if custom size
        boolean finished = false;
        char y;


        while(!finished)
        {
            System.out.println("Do you want to choose a custom size?(y/n) ");
            
            try
            {
             y = in.nextLine().toLowerCase().charAt(0);
            }
            catch (StringIndexOutOfBoundsException e)

            {
                System.out.println("*** You must input a letter ***");
                continue;
            }
            switch(y)
            {
                case 'y':
                    try
                    {
                    System.out.print("width: ");
                    width = in.nextInt();
                    in.nextLine();
                    System.out.print("height: ");
                    height = in.nextInt();
                    in.nextLine();
                    System.out.print("Number of mines: ");
                    mines = in.nextInt();
                    in.nextLine();
                    finished = true;
                    break;
                    }
                    catch(InputMismatchException e)
                    {
                        System.out.println("*** You must input a number ***");
                        continue;
                    }

                case 'n':
                    System.out.println("Defaulting to a 10x10 grid with 10 mines.");
                    mines = 10;
                    width = 10;
                    height = 10;

                    finished = true;
                    break;
                default:
                    System.out.println("Incorrect input. Try again!");
            }
          }
        }

    private void checkWon()
    {
        for(int i = 0; i < height; i++)
        {
            for(int j = 0; j < width; j++)
            {
                try
                {
                    if((grid.getMarkAt(i, j)) != MINE && grid.isHidden(i, j))
                        return;
                }
                catch(SquareDoesNotExist e)
                {
                    continue;
                }
            }
        }
        won = true;
    }

    private void wonMessage(String name)
    {

       System.out.println("Congratulations " + name + "! You won.");
        
    }

    private void lostMessage(String name)
    {
        System.out.println("Sorry " + name + ", you lost :(");
    }

    private void takeInput()
    {
        boolean chosen = false;
        while(!chosen)
        {
            Scanner in = new Scanner(System.in);
            int row;
            int col;
            char choice;


            System.out.print("(F)lag/unflag, (R)eveal or (Q)uit: ");

            try
            {
             choice = in.nextLine().toLowerCase().charAt(0);
            }
            catch(StringIndexOutOfBoundsException e)
            {
                System.out.println("*** You must input a letter ***");
                continue;
            }
            switch(choice)
            {
                case 'f':
                    chosen = true;

                    System.out.print("Which column? ");
                    col = in.nextLine().toLowerCase().charAt(0) - 'a';

                    System.out.print("Which row? ");
                    row = in.nextInt();
                    row = row - 1;
                    in.nextLine();

                    try
                    {
                        grid.toogleFlag(row, col);
                        chosen = true;
                    }
                    catch(SquareAlreadyRevealed e)
                    {
                        System.out.println("Can't flag, square has been revealed!");
                    }
                    catch(SquareDoesNotExist e)
                    {
                        System.out.println("Square coordinates " + row + ", " + col + " are incorrect, try again.");
                    }
                    break;

                case 'r':

                    try
                    {
                    System.out.print("Which column? ");
                    col = in.nextLine().toLowerCase().charAt(0) - 'a';
                    }
                    catch (StringIndexOutOfBoundsException e)
                    {
                        System.out.println("** You must input a letter for column **");
                        continue;
                    }
                    
                    try{


                    System.out.print("Which row? ");
                    row = in.nextInt();
                    row = row - 1;
                    in.nextLine();
                    }
                    catch (InputMismatchException e)
                    {
                        System.out.print("** You must input a number for row **");
                        continue;

                    }
                    

                    try
                    {
                        grid.reveal(row, col);
                        chosen = true;
                        if(grid.getMarkAt(row, col) == MINE)
                            lost = true;
                    }
                    catch(SquareHasFlag e)
                    {
                        System.out.println("Cannot reveal square! Square has been flagged, unflag it.");
                    }
                    catch(SquareAlreadyRevealed e)
                    {
                        System.out.println("Square has already been revealed!");
                    }
                    catch(SquareDoesNotExist e)
                    {
                        System.out.println("Square coordinates are incorrect, try again!");
                    }
                    break;

                case 'q':
                    done = true;
                    return;

                default:
                    System.out.println("Incorrect choice, try again!");
            }
        }
    }

    /**
     *
     */
    public void play()
    {
        while(!done)
        {
            /**
             * 1. Print Grid
             * 2. Take input
             * 3. Check if done
             */
            while(!lost && !won)
            {
                if(done)
                    break;
                System.out.println(grid);
                takeInput();

                checkWon();
            }

            if(won)
                wonMessage(player1.PlayersName());

            else if(lost)
                lostMessage(player1.PlayersName());
            line();

            printScoresPlayerOne();
            System.out.println("The solution was: ");
            System.out.println(grid.solutionToString());

            askAnotherGame();
        }
        System.out.println("Thanks for playing!");
    }

    /**
     *
     */
    public void play2()
    {   int turns = 0;

        while(!done)
        {
            while(!lost && !won)
            {   if(done)
                    break;
                System.out.println(grid);
                printScores();

                //check whose turn to go
                if(turns % 2 == 0) //player 1
                {   System.out.println(player1.PlayersName() + ", it is your turn");
                    takeInput();
                    player1.AddToPlayersScore(grid.getScore());
                    checkWon();

                    if(won)
                    {   if(player1.PlayersScore() > player2.PlayersScore())
                        {   wonMessage(player1.PlayersName());
                            System.out.println
                                    (player1.PlayersName() + 
                                    " won by "
                                    + (player1.PlayersScore() - player2.PlayersScore()) + 
                                    " points against " + player2.PlayersName());
                            line();
                            System.out.print("Final Score: ");
                            System.out.print(player1.PlayersScore());
                            System.out.print(":");
                            System.out.println(player2.PlayersScore());
                        }
                        else
                        {   wonMessage(player2.PlayersName());
                            System.out.println
                                    (player2.PlayersName() + 
                                    " won by "
                                    + (player2.PlayersScore() - player1.PlayersScore()) + 
                                    " points against " + player1.PlayersName());
                            line();
                            System.out.print("Final Score: ");
                            System.out.print(player1.PlayersScore());
                            System.out.print(":");
                            System.out.println(player2.PlayersScore());
                        }
                    }
                    else if(lost)
                        wonMessage(player2.PlayersName());

                    turns++;
                }
                else //player 2
                {   System.out.println(player2.PlayersName() + ", it is your turn");
                    takeInput();
                    player2.AddToPlayersScore(grid.getScore());
                    checkWon();

                    if(won)
                    {   if(player1.PlayersScore() > player2.PlayersScore())
                        {   wonMessage(player1.PlayersName());
                            System.out.println
                                    (player1.PlayersName() +
                                    " won by "
                                    + (player1.PlayersScore() - player2.PlayersScore()) +
                                    " points against " + player2.PlayersName());
                            line();
                            System.out.print("Final Score: ");
                            System.out.print(player1.PlayersScore());
                            System.out.print(":");
                            System.out.println(player2.PlayersScore());
                        }
                        else
                        {   wonMessage(player2.PlayersName());
                            System.out.println
                                    (player2.PlayersName() +
                                    " won by "
                                    + (player2.PlayersScore() - player1.PlayersScore()) +
                                    " points against " + player1.PlayersName());
                            line();
                            System.out.print("Final Score: ");
                            System.out.print(player1.PlayersScore());
                            System.out.print(":");
                            System.out.println(player2.PlayersScore());
                        }
                    }
                    else if(lost)
                        wonMessage(player1.PlayersName());

                    turns++;
                }
            }

                line();
                System.out.println("The solution was: ");
                System.out.println(grid.solutionToString());

                askAnotherGame();
                
                /**
                 * if player chooses to play again
                 * set player1 and player2 scores to 0
                 * set turns value to 0
                 */
                player1.SetScoreToZero();
                player2.SetScoreToZero();
                turns = 0;
        }
            System.out.println("Thanks for playing!");
    }

    private void askAnotherGame()
    {
        Scanner in = new Scanner(System.in);

        System.out.println("Want to play again?(y/n) ");
        char choice;
        while(true)
        {
            try{
            choice = in.nextLine().toLowerCase().charAt(0);
            }
            catch (StringIndexOutOfBoundsException e)
                    {
                        System.out.println("** You must input a letter **");
                        continue;
                    }


            switch(choice)
            {
                case 'y':
                    newGame();
                    return;
                case 'n':
                    done = true;
                    return;
                default:
                    System.out.println("Incorrect choice! Try again.");
                    break;
            }
        }
    }
    /**
     * Finds out the game mode(Single player or Multi player)
     * @return number of players (1 or 2)
     */
    public static int NumberOfPlayers()
    {   Scanner in = new Scanner(System.in);
        int NoOfPlayers = 0;

        System.out.println("Choose the game mode:");
        System.out.println("(S)ingle Player");
        System.out.println("(M)ulti Player (Person vs. Person)");

        boolean done = false;
        int choice;
        int numplayers;
        char players;

        while(!done)
        {
            try
            {
                 players=(in.nextLine().toLowerCase().charAt(0));

            }
            catch (StringIndexOutOfBoundsException e)
                    {
                        System.out.println("** You must input a letter **");
                        continue;
                    }


            switch (players)


            {
                case 's':
                    NoOfPlayers = 1;
                    System.out.println("Enter your name:");
                    String name = in.nextLine();
                    player1 = new Player(name);
                    done = true;
                    break;
                case 'm':
                    NoOfPlayers = 2;
                    System.out.println("Enter your name player 1:");
                    String name2 = in.nextLine();
                    player1 = new Player(name2);
                    System.out.println("Enter your name player 2:");
                    name2 = in.nextLine();
                    player2 = new Player(name2);
                    done = true;
                    break;
                default:
                    System.out.println("Invalid choice! Try again.");
                    System.out.println("Choose the game mode:");
                    System.out.println("(S)ingle Player");
                    System.out.println("(M)ulti Player (Person vs. Person)");
            }
        }

        return NoOfPlayers;
    }

    /**
     *
     */
    public static void printScores()
    {   line();
        System.out.println(player1.PlayersName() + " scored: " + player1.PlayersScore());
        System.out.println(player2.PlayersName() + " scored: " + player2.PlayersScore());
        line();
    }

     public static void printScoresPlayerOne()
    {   line();
        System.out.println(player1.PlayersName() + " scored: " + player1.PlayersScore());
        line();
    }


    //prints dotted line to separate text
    /**
     *
     */
    public static void line()
    {
        System.out.println("--------------------------------------------------");
    }
    /**
     *
     * @param args
     */
    public static void main(String[] args)
    {   new AsciiGame();
    }

}
