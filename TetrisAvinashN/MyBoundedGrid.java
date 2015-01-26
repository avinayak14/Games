import java.util.ArrayList;
/**
 * Makes the bounded grid that I am dealing with.
 * 
 * @author Avinash Nayak
 * @version March 1, 2013
 */
//A MyBoundedGrid is a rectangular grid with a finite number of rows and columns.
public class MyBoundedGrid<E>
{
    private Object[][] occupantArray;  // the array storing the grid elements

    //Constructs an empty MyBoundedGrid with the given dimensions.
    //(Precondition:  rows > 0 and cols > 0.)
    //@param rows the number of rows in the grid
    //@param cols the number of columns in the grid
    public MyBoundedGrid(int rows, int cols)
    {
        occupantArray = new Object[rows][cols];
    }

    //returns the number of rows
    //@return int the number of rows in the array
    public int getNumRows()
    {
        return occupantArray.length;
    }

    //returns the number of columns
    //@return int the number of columns in the array
    public int getNumCols()
    {
        return occupantArray[0].length;
    }

    //returns true if loc is valid in this grid, false otherwise
    //precondition:  loc is not null
    //@param Location the location to be tested if valid or not
    //@return true if the location is valid, otherwise false
    public boolean isValid(Location loc)
    {
        return 0 <= loc.getRow() && loc.getRow() < getNumRows() 
        && 0 <= loc.getCol() && loc.getCol() < getNumCols();
    }

    //returns the object at location loc (or null if the location is unoccupied)
    //precondition:  loc is valid in this grid
    //@param loc the location to be accessed (creds to NateDaLOL)
    //@return E the object at the location
    public E get(Location loc)
    {
        if(!isValid(loc))
        {
            throw new IllegalArgumentException("Location "+loc+" is invalid");
        }
        return (E) occupantArray[loc.getRow()][loc.getCol()];
    }

    //puts obj at location loc in this grid and returns the object previously at that location (or null if the
    //location is unoccupied)
    //precondition:  loc is valid in this grid
    //@param Location for the object to be put in
    //@param E the object to be put at that location
    //@return E the previous occupant of the location
    public E put(Location loc, E obj)
    {
        if (!isValid(loc))
        {
            throw new IllegalArgumentException("Location "+loc+" is invalid");
        }
        if (obj == null)
            throw new NullPointerException("obj == null");
        E previousOccupant = this.get(loc);
        occupantArray[loc.getRow()][loc.getCol()] = obj;
        return previousOccupant;
    }

    //removes the object at location loc from this grid and returns the object that was removed (or null if the
    //location is unoccupied
    //precondition: loc is valid in this grid
    //@param Location the location for a potential object to be removed from
    //@return E the potential object removed from the location
    public E remove(Location loc)
    {
        if(!isValid(loc))
        {
            throw new IllegalArgumentException("The given location " + loc + " is invalid.");
        }
        E locnew = this.get(loc);
        occupantArray[loc.getRow()][loc.getCol()] = null;
        return locnew;
    }

    //returns an array list of all occupied locations in this grid
    //@return ArrayList<Location> the array list of occupied locations
    public ArrayList<Location> getOccupiedLocations()
    {
        ArrayList <Location> locations = new ArrayList <Location> ();
        for(int row = 0; row < getNumRows(); row++)
        {
            for(int col = 0; col < getNumCols(); col++)
            {
                Location loc = new Location (row, col);
                if(get(loc)!=null)
                {
                    locations.add(loc);
                }
            }
        }
        return locations;
    }
}