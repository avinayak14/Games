import java.util.*;

/**
 * Solitaire represents the classic card-playing game with stacks.
 * 
 * @author Avinash Nayak
 * @version 14 January 2014
 */
public class Solitaire
{
    public static void main(String[] args)
    {
        new Solitaire();
    }

    private Stack<Card> stock;
    private Stack<Card> waste;
    private Stack<Card>[] foundations;
    private Stack<Card>[] piles;
    private SolitaireDisplay display;

    /**
     * The constructor for the game of Solitaire. It sets up the game.
     */
    public Solitaire()
    {
        foundations = new Stack[4];
        piles = new Stack[7];

        //INSERT CODE HERE
        stock = new Stack<Card>();
        waste = new Stack<Card>();
        
        
        //Creates empty foundations
        for(int i = 0; i<4; i++)
        {
            foundations[i] = new Stack<Card>();
        }
        
        //Creates empty piles
        for(int i = 0; i<7; i++)
        {
            piles[i] = new Stack<Card>();
        }

        createStock();
        deal();
        
        display = new SolitaireDisplay(this);
    }
    
    /**
     * Prints the menu that tells the user what to do.
     */
    
    public void printMenu() {}
    
    /**
     * Returns the card on top of the stock.
     * 
     * @return the card on top of the stock or null if the stock is empty
     */
    public Card getStockCard()
    {
        if(stock.isEmpty())
            return null;
        return stock.peek();
    }

    /**
     * Returns the card on the top of the waste.
     * 
     * @return the card on top of the waste or null if the waste is empty
     */
    public Card getWasteCard()
    {
        if(waste.isEmpty())
            return null;
        return waste.peek();
    }

    /**
     * Returns the card on top of the given foundation.
     * 
     * @param index the index of the foundation with the card to be returned
     * @return the card on the top of foundation[index]
     */
    //precondition:  0 <= index < 4
    //postcondition: returns the card on top of the given
    //               foundation, or null if the foundation
    //               is empty
    public Card getFoundationCard(int index)
    {
        if(foundations[index].isEmpty())
            return null;
        return foundations[index].peek();
    }

    /**
     * Returns a pile with a given index.
     * 
     * @param index the index of the pile that should be returned
     * @return Stack<Card> a reference to the given pile
     */
    //precondition:  0 <= index < 7
    //postcondition: returns a reference to the given pile
    public Stack<Card> getPile(int index)
    {
        return piles[index];
    }

    /**
     * Executes the necessary actions when a stock is clicked, which involves 
     * testing cards at the top of the waste.
     */
    //called when the stock is clicked
    public void stockClicked()
    {
        if(display.isWasteSelected() || display.isPileSelected())
        {}
        else if(stock.isEmpty() == false)
        {
            this.dealThreeCards();
        }
        else
        {
            this.resetStock();
        }
        System.out.println("stock clicked");
    }

    /**
     * Executes the necessary actions when waste is clicked, which involves 
     * selecting or unselecting cards.
     */
    //called when the waste is clicked
    public void wasteClicked()
    {
        System.out.println("waste clicked");
        if(display.isWasteSelected())
        {
            display.unselect();
            for(int findex = 0; findex<4; findex++)
                {
                    if(canAddToFoundation(waste.peek(), findex))
                    {
                        foundations[findex].push(waste.pop());
                        return;
                    }
                }
        }
        else
        {
            if(waste.isEmpty() == false && (!display.isWasteSelected()) &&
               (!display.isPileSelected()))
            {
                display.selectWaste();
                
            }
        }
    }

    /**
     * Executes the necessary actions when foundations is clicked, which 
     * invovles moving cards to the foundation.
     * 
     * @param index the index of the foundation that was clicked
     */
    //precondition:  0 <= index < 4
    //called when given foundation is clicked
    public void foundationClicked(int index)
    {
        if(display.isWasteSelected())
        {
            if(this.canAddToFoundation(waste.peek(), index))
            {
                foundations[index].push(waste.pop());
                display.unselect();
            }
        }
        if(display.isPileSelected())
        {
            if(this.canAddToFoundation(piles[display.selectedPile()].peek(), index))
            {
                foundations[index].push(piles[display.selectedPile()].pop());
                display.unselect();
            }
        }
        System.out.println("foundation #" + index + " clicked");
    }

    /**
     * Executes the necessary actions when a pile is clicked, including 
     * unselecting cards and/or moving them to other piles.
     * 
     * @param index the index of the pile that was clicked
     */
    //precondition:  0 <= index < 7
    //called when given pile is clicked
    public void pileClicked(int index)
    {
        if(!(waste.isEmpty()) && this.canAddToPile(waste.peek(), index))
        {
            if(display.isWasteSelected())
            {
                piles[index].push(waste.pop());
                display.unselect();
            }
        }
        else if(display.isPileSelected())
        {
            if(index == display.selectedPile())
            {
                display.unselect();
                for(int findex = 0; findex<4; findex++)
                {
                    if(canAddToFoundation(piles[index].peek(), findex))
                    {
                        foundations[findex].push(piles[index].pop());
                        return;
                    }
                }
            }
            else
            {
                Stack<Card> c = removeFaceUpCards(display.selectedPile());
                if(canAddToPile(c.peek(), index))
                {
                    addToPile(c, index);
                    display.unselect();
                }
                else
                {
                    addToPile(c, display.selectedPile());
                    display.unselect();
                }
            }
        }
        else if(!(piles[index].isEmpty()) && piles[index].peek().isFaceUp())
        {
            display.selectPile(index);
        }
        else if (!display.isPileSelected() && !display.isWasteSelected()
            && piles[index].peek().isFaceUp() == false)
        {
            piles[index].peek().turnUp();
        }
        
        System.out.println("pile #" + index + " clicked");
    }
    
    /**
     * Creates a stock of 52 randomly shuffled cards in a standard deck. 
     */
    public void createStock()
    {
        ArrayList<Card> newDeck = new ArrayList<Card>();
        for(int r = 1; r<=13; r++)
        {
            newDeck.add(new Card(r, "h"));
            newDeck.add(new Card(r, "d"));
            newDeck.add(new Card(r, "c"));
            newDeck.add(new Card(r, "s"));
        }
        
        while(newDeck.size()>0)
            stock.push(newDeck.remove((int)(Math.random()*newDeck.size())));
    }
    
    /**
     * Deals cards from the stock to the 7 piles in the arrangement shown in 
     * the picture earlier in the lab. It is called from the constuctor.
     */
    public void deal()
    {
        for(int index = 0; index<7; index++)
        {
            for(int j = 0; j<=index; j++)
            {
                piles[index].push(stock.pop());
                if(j == index)
                {
                    piles[index].peek().turnUp();
                }
            }
        }
    }
    
    /**
     * Moves the top three cards from the stock onto the top of the waste. If 
     * there are fewer than three cards on the stock, it should transfer 
     * whatever is left to waste. It must turn up each card moved onto waste.
     */
    
    public void dealThreeCards()
    {
        for(int i = 1; i<=3; i++)
        {
            if(stock.isEmpty())
                return;
            waste.push(stock.pop());
            waste.peek().turnUp();
        }
    }
    
    /**
     * Repeatedly moves the top card from the waste to the top of the stock 
     * until no cards are left in the waste.
     */
    public void resetStock()
    {
        while(waste.isEmpty() == false)
        {
            stock.push(waste.pop());
            stock.peek().turnDown();
        }
    }
    
    /**
     * Tests if a certain type of card can be moved to the top of a given pile.
     * 
     * @param Card the card that might be able to be added to a pile
     * @param index the index of the pile that a card might be able to
     *              be added to
     * @return true if a card can be legally added to a pile; otherwise, false
     */
    //precondition:  0 <= index < 7
    //postcondition: Returns true if the given card can be
    //               legally moved to the top of the given
    //               pile
    private boolean canAddToPile(Card card, int index)
    {
        if(piles[index].isEmpty() == false)
        {
            if(piles[index].peek().isFaceUp())
            {
                if(card.isRed() != piles[index].peek().isRed())
                {
                    if(card.getRank() == piles[index].peek().getRank()-1)
                    { return true; }
                    return false;
                }
                return false;
                
            }
            return false;
        }
        else
        {
            return card.getRank()==13;
        }
    }
    
    /**
     * Takes away all the face-up cards on the top of the given pile.
     * 
     * @param index the index of the pile from which face-up cards must be
     *              removed
     * @return Stack<Card> all the face-up cards on the top of the given pile
     */
    //precondition:  0 <= index < 7
    //postcondition: Removes all face-up cards on the top of
    //               the given pile; returns a stack
    //               containing these cards
    private Stack<Card> removeFaceUpCards(int index)
    {
        Stack<Card> faceUpStack = new Stack<Card>();
        while(!(piles[index].isEmpty()) && piles[index].peek().isFaceUp())
        {
            faceUpStack.push(piles[index].pop());
        }
        return faceUpStack;
        
    }
    
    /**
     * Takes away all elements from cards and puts them on the given pile.
     * 
     * @param Stack<Card> cards a bunch of cards to be added to a pile
     * @param index the index of a pile that cards need to be added to
     */
    //precondition:  0 <= index < 7
    //postcondition: Removes elements from cards, and adds
    //               them to the given pile.
    private void addToPile(Stack<Card> cards, int index)
    {
        while(!(cards.isEmpty()))
        {
            piles[index].push(cards.pop());
        }
    }
    
    /**
     * Tests if a card can be added to the foundation by the given rules.
     * 
     * @param Card a card that could be added to the foundation
     * @param index the index of the foundation that a card could be added to
     * @return true if a card can be added to the foundation; otherwise, false
     */
    //precondition:  0 <= index < 4
    //postcondition: Returns true if the given card can be
    //               legally moved to the top of the given
    //               foundation
    private boolean canAddToFoundation(Card card, int index)
    {
        if(foundations[index].isEmpty())
        {
            return card.getRank()==1;
        }
        else
        {
            return card.getSuit() == foundations[index].peek().getSuit() &&
                   card.getRank() == foundations[index].peek().getRank()+1;
        }
    }
}