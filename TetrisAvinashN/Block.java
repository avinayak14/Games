import java.awt.Color;

/**
 * Creates a block in a certain bounded grid.
 * 
 * @author Avinash Nayak
 * @version March 6, 2013
 */
public class Block
{
    private MyBoundedGrid<Block> grid;
    private Location location;
    private Color color;

    /**
     * Constructs a blue block, because blue is the greatest color ever! Sets
     * the grid and the location equal to null.
     */
    public Block()
    {
        color = Color.BLUE;
        grid = null;
        location = null;
    }

    /**
     * Gets the color of this block.
     * @return Color the color of this block
     */
    public Color getColor()
    {
        return this.color;
    }

    /**
     * Gets the color of this block to newColor.
     */
    public void setColor(Color newColor)
    {
        color = newColor;
    }

    //gets the grid of this block, or null if this block is not contained in a grid
    public MyBoundedGrid<Block> getGrid()
    {
        return grid;
    }

    //gets the location of this block, or null if this block is not contained in a grid
    public Location getLocation()
    {
        return location;
    }

    //removes this block from its grid
    //precondition:  this block is contained in a grid
    public void removeSelfFromGrid()
    {
        grid.remove(getLocation());
        grid = null;
        location = null;
    }

    //puts this block into location loc of grid gr
    //if there is another block at loc, it is removed
    //precondition:  (1) this block is not contained in a grid
    //               (2) loc is valid in gr
    public void putSelfInGrid(MyBoundedGrid<Block> gr, Location loc)
    {
        grid = gr;
        Block oldblock = grid.put(loc, this);
        if(oldblock != null)
        {
            oldblock.grid = null;
            oldblock.location = null;
        }
        location = loc;
    }

    /**
     * Moves this block to newLocation
     * if there is another block at newLocation, it is removed
     * precondition:  (1) this block is contained in a grid
                   (2) newLocation is valid in the grid of this block
     * @param Location the new location for the block to be moved to
     */
    public void moveTo(Location newLocation)
    {
        this.grid.remove(getLocation());
        this.putSelfInGrid(grid, newLocation);
    }

    /**
     * Gets a string with the location and color of this block.
     * 
     * @return String the string with the location and color of the block
     */
    //returns a string with the location and color of this block
    public String toString()
    {
        return "Block[location=" + location + ",color=" + color + "]";
    }
}