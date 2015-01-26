import java.awt.*;
/**
 * This class is the main class of our Tetris game. It has instance variables
 * that keep track of a block display and a block bounded grid.
 * 
 * @author Avinash Nayak 
 * @version March 15, 2013
 */
public class Tetris implements ArrowListener

{
    // instance variables - replace the example below with your own
    private MyBoundedGrid<Block> square;
    private BlockDisplay disp;
    public static final int ROWS = 20;
    public static final int COLS = 10;
    private Tetrad activeTetrad;
    private boolean playing;
    public int displayedScore;
    public int currentLevel;
    public int totalClearedRows;
    public int time;

    

    /**
     * Constructor for objects of class Tetris
     */
    public Tetris()
    {
        time = 1000;
        displayedScore = 0;
        currentLevel = 1;
        totalClearedRows = 0;
        square = new MyBoundedGrid<Block>(ROWS,COLS);
        disp = new BlockDisplay(square);
        disp.setTitle("Level:"+currentLevel+"\t"+"Score:"+displayedScore);
        activeTetrad = new Tetrad(square);
        disp.showBlocks();
        disp.setArrowListener(this);
    }
    
    /**
     * Rotates active tetrad when up key is pressed.
     */
    public void upPressed()
    {
        activeTetrad.rotate();
        disp.showBlocks();
    }
    
    /**
     * Moves active tetrad down one block when down key is pressed
     */
    public void downPressed()
    {
        activeTetrad.translate(1, 0);
        disp.showBlocks();
    }
    
    /**
     * Moves active tetrad to the left when the left key is pressed
     */
    public void leftPressed()
    {
        activeTetrad.translate(0, -1);
        disp.showBlocks();
    }
    
    /**
     * Moves active tetrad to the right when the right key is pressed
     */
    public void rightPressed()
    {
        activeTetrad.translate(0, 1);
        disp.showBlocks();
    }
    
    /**
     * Moves the active tetrad to the lowest point at which it can be.
     */
    public void spacePressed()
    {
        while(activeTetrad.translate(1, 0))
        {
            disp.showBlocks();
        }
    }
    
    /**
     * Plays the tetris game.
     * 
     * @return boolean whether the game is playing or not
     */
    public boolean play()
    {
        while(true)
        {
            try
            {
                //pause for 1000 milliseconds.
                Thread.sleep(time);
                if(activeTetrad.translate(1,0) == true)
                {
                    disp.showBlocks();
                }
                else
                {
                    disp.showBlocks();
                    if(activeTetrad.isAtTop() == true)
                    {
                        System.exit(1);
                    }
                    clearCompletedRows();
                    activeTetrad = new Tetrad(square);
                    disp.showBlocks();
                }
            }
            catch(InterruptedException e)
            {
                //ignore
            }
        }
    }
    
    /**
     * Finds out whether game is over or not.
     * @return true if game is over; returns false if the game is not over
     */
    public boolean isGameOver()
    {
        int mid = square.getNumCols() / 2;
        Location top = new Location (0, mid);
        Location second = new Location (1,mid);
        if(square.get(top) == null && square.get(second) == null)
        {
            return false;
        }
        return true;
    }
    
    /**
     * Displays Game Over when the Tetris game ends.
     */
    public void displayGameOver()
    {
        System.out.print('\f');
        System.out.println("Game Over. I hope you had a blast!");
    }
    
    /**
     * Finds out whether or not inputted row is completed.
     * @param row the row to be checked for completion
     * @return true if row is complete; returns false if the row is not complete
     */
    private boolean isCompletedRow(int row)
    {
        for (int i = 0; i < square.getNumCols(); i++)
        {
            Location blockloc = new Location(row, i);
            
            if (square.get(blockloc) == null)
            {
                return false;
            }
        }
        return true;
    }
        
    /**
     * Clears the inputted row.
     * @param row the row that needs to be cleared
     */
    private void clearRow(int row)
    {
        Block removeBlock;
        Location loc;
        for (int i = 0; i < square.getNumCols(); i++)
        {
            loc = new Location(row, i);
            removeBlock = square.get(loc);
            removeBlock.removeSelfFromGrid();
        }
        for (int j = 0; j < square.getNumCols(); j++)
        {
            for (int k = row - 1; k >= 0; k--)
            {
                loc = new Location(k, j);
                if (square.get(loc) != null)
                {
                    square.get(loc).moveTo(new Location(k + 1, j));
                }
            }
        }
    }
    
    /**
     * Clears all of the rows that have been completed.
     * @return int the number of rows that have been cleared.
     */
    private int clearCompletedRows()
    {
        int clear = 0;
        for (int i = 0; i < square.getNumRows(); i++)
        {
            if (isCompletedRow(i))
            {
                clear++;
                clearRow(i);
                totalClearedRows++;
                if(totalClearedRows%4==0)
                {
                    currentLevel++;
                    time=time*11/12;//decrease in time for each tetrad.
                }
            }
        }
        if(clear==1)
        {
            displayedScore+=100*currentLevel;
        }
        if(clear==2)
        {
            displayedScore+=400*currentLevel;
        }
        if(clear==3)
        {
            displayedScore+=900*currentLevel;
        }
        if(clear==4)
        {
            displayedScore+=1600*currentLevel;
        }
        disp.setTitle("Level:"+currentLevel+"\t"+"Score:"+displayedScore);
        return clear;
    }
    
    /**
     * Main program
     */
    public static void main(String[] args)
    {
        Tetris tetris = new Tetris();
        while(tetris.play())
        {
        }
        System.out.println("Game Over");
    }
}



