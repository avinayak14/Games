
/**
 * Card represents a single playing card with three attributes: rank, suit, and
 * isFaceUp. It will be used in the Solitaire class for the game to work.
 * 
 * @author Avinash Nayak 
 * @version 10 December 2013
 */
public class Card
{
    // instance variables
    private int rank;
    private String suit;
    private boolean isFaceUp;

    /**
     * Constructor for objects of class Card.
     * 
     * @param rank the rank to be set to the necessary card
     * @param suit the suit to be set to the necessary card
     * 
     */
    public Card(int rank, String suit)
    {
        this.rank = rank;
        this.suit = suit;
        isFaceUp = false;
    }

    /**
     * The method getRank returns the rank of the given card.
     * 
     * @return the rank of the given card
     * 
     */
    public int getRank()
    {
        return this.rank;
    }
    
    /**
     * The method getSuit returns the suit of the given card.
     * 
     * @return the suit of the given card
     * 
     */
    public String getSuit()
    {
        return this.suit;
    }
    
    /**
     * The method isRed tests if the card is red or if it is black.
     * 
     * @return true if the card is a diamond or a heart; otherwise, false
     */
    public boolean isRed()
    {
        return getSuit().equals("d") || getSuit().equals("h");
    }
    
    /**
     * The method isFaceUp tests if a card is face up.
     * 
     * @return true if the card is face up; otherwise, false
     */
    public boolean isFaceUp()
    {
        return this.isFaceUp;
    }
    
    /**
     * The method turnUp makes the card face up.
     */
    public void turnUp()
    {
        isFaceUp = true;
    }
    
    /**
     * The method turnDown makes the card face down.
     */
    public void turnDown()
    {
        isFaceUp = false;
    }
    
    /**
     * The method getFileName returns a string with the name of the file in
     * the subdirectory of cards in which the respective image corresponds
     * to the card at hand
     * 
     * @return String a string with the name of the file
     */
    public String getFileName()
    {
        String str = "cards/";
        if(!isFaceUp) 
        {
            str += "back.gif"; 
            return str;
        }
        else
        {
            if(rank == 1) str+="a";
            if(rank >= 2 && rank <= 9) str+=rank;
            if(rank == 10) str+= "t";
            if(rank == 11) str+= "j";
            if(rank == 12) str+= "q";
            if(rank == 13) str+= "k";
            str+= (suit + ".gif");
            return str;
        }
    }
}
