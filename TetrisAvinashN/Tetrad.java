 import java.lang.*;
import java.awt.*;
import java.util.concurrent.Semaphore;
/**
 * The Tetrad class creates a new tetrad to be used in the Tetris game.
 * 
 * @author Avinash Nayak
 * @version March 8, 2013
 */
public class Tetrad
{
    // instance variables - replace the example below with your own
    private MyBoundedGrid<Block> grid;
    private Block[] blocks;
    private int blockType;
    public static final int I_BLOCK = 0;
    public static final int T_BLOCK = 1;
    public static final int O_BLOCK = 2;
    public static final int L_BLOCK = 3;
    public static final int J_BLOCK = 4;
    public static final int S_BLOCK = 5;
    public static final int Z_BLOCK = 6;

    /**
     * Constructor for objects of class Tetrad
     */
    public Tetrad(MyBoundedGrid<Block> gr)
    {
        grid = gr;
        blockType = (int)(Math.random()*7);
        Location[] locs = new Location[4];
        //lock = new Semaphore(1, true);
        
        Block block0 = new Block();
        Block block1 = new Block();
        Block block2 = new Block();
        Block block3 = new Block();
        
        blocks = new Block[4];
        blocks[0] = block0;
        blocks[1] = block1;
        blocks[2] = block2;
        blocks[3] = block3;
        
        if (blockType == I_BLOCK)
        {
            block0.setColor(Color.CYAN);
            block1.setColor(Color.CYAN);
            block2.setColor(Color.CYAN);
            block3.setColor(Color.CYAN);
            
            locs[0] = new Location(1, 4);
            locs[1] = new Location(0, 4);
            locs[2] = new Location(2, 4);
            locs[3] = new Location(3, 4);
        }
        else if (blockType == T_BLOCK)
        {
            block0.setColor(Color.MAGENTA);
            block1.setColor(Color.MAGENTA);
            block2.setColor(Color.MAGENTA);
            block3.setColor(Color.MAGENTA);
            
            locs[0] = new Location(0, 4);
            locs[1] = new Location(0, 3);
            locs[2] = new Location(0, 5);
            locs[3] = new Location(1, 4);
        }
        else if (blockType == O_BLOCK)
        {
            block0.setColor(Color.YELLOW);
            block1.setColor(Color.YELLOW);
            block2.setColor(Color.YELLOW);
            block3.setColor(Color.YELLOW);
            
            locs[0] = new Location(0, 4);
            locs[1] = new Location(0, 5);
            locs[2] = new Location(1, 4);
            locs[3] = new Location(1, 5);
        }
        else if (blockType == L_BLOCK)
        {
            block0.setColor(Color.ORANGE);
            block1.setColor(Color.ORANGE);
            block2.setColor(Color.ORANGE);
            block3.setColor(Color.ORANGE);
            
            locs[0] = new Location(2, 4);
            locs[1] = new Location(1, 4);
            locs[2] = new Location(0, 4);
            locs[3] = new Location(2, 5);
        }
        else if (blockType == J_BLOCK)
        {
            block0.setColor(Color.BLUE);
            block1.setColor(Color.BLUE);
            block2.setColor(Color.BLUE);
            block3.setColor(Color.BLUE);
            
            locs[0] = new Location(2, 4);
            locs[1] = new Location(1, 4);
            locs[2] = new Location(0, 4);
            locs[3] = new Location(2, 3);
        }
        else if (blockType == S_BLOCK)
        {
            block0.setColor(Color.GREEN);
            block1.setColor(Color.GREEN);
            block2.setColor(Color.GREEN);
            block3.setColor(Color.GREEN);
            
            locs[0] = new Location(0, 4);
            locs[1] = new Location(0, 5);
            locs[2] = new Location(1, 4);
            locs[3] = new Location(1, 3);
        }
        else if (blockType == Z_BLOCK)
        {
            block0.setColor(Color.RED);
            block1.setColor(Color.RED);
            block2.setColor(Color.RED);
            block3.setColor(Color.RED);
            
            locs[0] = new Location(0, 4);
            locs[1] = new Location(0, 3);
            locs[2] = new Location(1, 4);
            locs[3] = new Location(1, 5);
        }
        addToLocations(grid, locs);
    }

    /**
     * Adds locations from an array into the grid.
     * 
     * @param MyBoundedGrid<Block> gr grid that the tetrad will be in
     * @param type the type that the tetrad will be
     */
    private void addToLocations(MyBoundedGrid<Block> gr, Location[] locs)
    {
        for (int i = 0; i < locs.length; i++)
        {
            blocks[i].putSelfInGrid(gr, locs[i]);
        }
    }
    
    /**
     * Gets the block type of the tetrad.
     * 
     * @return the block type of the tetrad
     */
    public int getBlockType()
    {
        return this.blockType;
    }
    
    /**
     * Removes tetrad blocks from the grid they are in
     * 
     * @return Location[] locations of blocks before removal
     */
     private Location[] removeBlocks()
     {
         Location[]locs = new Location[blocks.length];
         for (int i=0; i<blocks.length; i++)
         {
             locs[i] = blocks[i].getLocation();
             blocks[i].removeSelfFromGrid();
         }
         return locs;
     }
        
     /**
      * Checks if the inputted locations in the grid is empty.
      * 
      * @param MyBoundedGrid<Block> grid inputted grid
      * @param Location[] locs inputted location
      * @return true if inputted location in inputted grid is empty; otherwise returns false
      */
     private boolean areEmpty(MyBoundedGrid<Block> grid, Location[] locs)
     {
         for (int i=0; i<locs.length; i++)
         {
             if (!grid.isValid(locs[i])||grid.get(locs[i])!=null)
             {
                 return false;
             }
         }
         return true;
     }
     
    /**
     * Translated a tetrad.
     * 
     * @param deltaRow change in rows that the tetrad will be translated
     * @param deltaCol change in columns that tetrad will be translated
     * @return true if the translation occurs; otherwise returns false
     */
     public boolean translate(int deltaRow, int deltaCol)
     {
             Location trans;
             Location[] locs = removeBlocks();
             Location[] transloc = new Location[locs.length];
            for (int i=0; i<locs.length; i++)
            {
                trans = new Location(locs[i].getRow()+deltaRow, locs[i].getCol()+deltaCol);
                transloc[i] = trans;
            }
            if (areEmpty(grid, transloc))
            {
                for(int i = 0; i<locs.length; i++)
                {
                    blocks[i].putSelfInGrid(grid, transloc[i]);
                }
                return true;
            }
            for(int i = 0; i<locs.length; i++)
            {
                blocks[i].putSelfInGrid(grid, locs[i]);
            }
            return false;
    }
    
    /**
     * Rotates a tetrad by 90 degrees
     * 
     * @return true if rotation occurs; otherwise returns false
     */
    public boolean rotate()
    {
         Location rot;
         Location[] locs = removeBlocks();
         Location[] rotloc = new Location[locs.length];
         int newRow;
         int newCol;
         for (int i=0; i<locs.length; i++)
         {
             newRow = locs[0].getRow()-locs[0].getCol()+locs[i].getCol();
             newCol = locs[0].getRow()+locs[0].getCol()-locs[i].getRow();
             rot = new Location(newRow,newCol);
             rotloc[i] = rot;
         }
         if (areEmpty(grid, rotloc))
         {
             for(int i = 0; i<locs.length; i++)
             {
                 blocks[i].putSelfInGrid(grid, rotloc[i]);
             }
             return true;
         }
         for(int i = 0; i<locs.length; i++)
         {
             blocks[i].putSelfInGrid(grid, locs[i]);
         }
         return false;
    }
    /**
     * Checks if block is at top of the grid.
     * 
     * @return boolean if the block is at the top of the grid
     */
    public boolean isAtTop()
    {
        for(int i = 0; i<blocks.length; i++)
        {
            Location loc1 = new Location(0, 4);
            Location loc2 = new Location(0, 5);
            if(blocks[i].getLocation().equals(loc1) ||
               blocks[i].getLocation().equals(loc2))
            {
                return true;
            }
        }
        return false;
    }
   
     
}

