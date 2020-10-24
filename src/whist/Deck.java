
package whist;



import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;



public class Deck implements Serializable, Iterable<Card> {
    
    // define final constants
    private static final long serialVersionUID = 49;
    private final int MAX_DECK_SIZE = 52;

    
    // attributes
    private List<Card> deckOfCards; 
    private int cardsRemaining = MAX_DECK_SIZE;
    
    /* Constructor for deck
     Purpose: create a deck of unique cards from all suits and ranks
     then shuffle the deck
    */ 
    public Deck() {
        this.deckOfCards = Arrays.asList(new Card[MAX_DECK_SIZE]);
            
        // assign cards of the deck
        for(int i = 0; i < MAX_DECK_SIZE; i++) {
            this.deckOfCards.set(i, new Card(Card.Rank.values()[i%13], 
                               Card.Suit.values()[i/13]));
        }        
        
        // shuffle the deck
        Collections.shuffle(this.deckOfCards);
    }
    
    
    /*
      Reinitialises the deck
      adds up to 52 cards for a full deck
      then shuffles again
    */
    public void newDeck() {
        for(int i = 0; i < MAX_DECK_SIZE; i++) {
           this.deckOfCards.set(i, new Card(Card.Rank.values()[i%13], 
                               Card.Suit.values()[i/13]));
        }

        cardsRemaining = MAX_DECK_SIZE;

        Collections.shuffle(deckOfCards);
    }
    
    // returns the size of the deck
    public int size() {
        return cardsRemaining;
    }
    
    // Iterator inner class
    private class DeckIterator implements Iterator<Card>  {
        
        // define the pointer of the deck to keep track of current card
        private int pointer;
        
        // constructor for the iterator
        public DeckIterator() {
            pointer = 0;
        }
        
        // determines if the pointer has reached the end of deck
        @Override
        public boolean hasNext() {
            return pointer < deckOfCards.size();
        }
        
        
        // determines if there is a next, if so it will increment to next eleme
        // nt and then return the value at the position
        @Override
        public Card next() {
            if(hasNext() == false)
                throw new NoSuchElementException("There isn't a next element");
            return deckOfCards.get(pointer++);
        }
        
        
        // no need for a remove function, so override there being no support
        @Override
        public void remove() {
             throw new UnsupportedOperationException("Remove not supported for"
                    + " SpadeIterator");           
        }

    }
    
    // Spade Iterator inner class
    // Iterates to the next spade in the deck
    private class SpadeIterator implements Iterator<Card> {
        
        // pointer to keep track of hte position with the deck of cards
        private int pointer;
        
        
        public SpadeIterator() {
            pointer = 0;
        }
        
        
        // method to determine if there is another spade in the list
        @Override
        public boolean hasNext() {
            
            for(int i = pointer; i < deckOfCards.size(); i++) {
                if(deckOfCards.get(i).getSuit() == Card.Suit.SPADES) {
                    return true;
                }
            }
            return false;
        }
        
        // method that returns the next spade in the list
        @Override
        public Card next() {
            if(hasNext() == false)
                throw new NoSuchElementException("There isn't a next element");
            
            for(int i = pointer; i < deckOfCards.size(); i++) {
                if(deckOfCards.get(i).getSuit() == Card.Suit.SPADES) {
                    pointer = i+1;
                    return deckOfCards.get(i);
                    
                }
            }

            return null;
        }
        
        // Remove feature not necessary for the spade iterator
        @Override
        public void remove() {
            throw new UnsupportedOperationException("Remove not supported for"
                    + " SpadeIterator");
        }

    }       
    
    // return a deck iterator instance
    @Override
    public Iterator<Card> iterator() {
        return new DeckIterator();
    }
    // return a spade iterator instance
    public SpadeIterator spadeIterator() {
        return new SpadeIterator();
    }
    
    /* deal
    purpose: Deal the top card of the deck, remove it (by adjusting cards
    remaining) and then return the card removed.
    */
    public Card deal() {
        
        // get the current index of the deck
        int currentIndex = MAX_DECK_SIZE - cardsRemaining;
        
        
        // define an iterator
        DeckIterator iterator = new DeckIterator();
        
        // iterate through the deck up to the card needed to be returned
        for(int i = 0; i < currentIndex; i++) {
            iterator.next();
        }
        
        // if not the top card, decrement the cardsRemaining
        if(currentIndex < 52) 
            cardsRemaining--;
        
        // return the top card (will traverse up to the desired card)
        return iterator.next();
    } 
    
   
    
    // iterate through deck test
    // this is used for testing
    public static void printDeck(Deck d) {
        for(int i = 0; i <d.deckOfCards.size(); i++)
            System.out.println(i + ": "+d.deckOfCards.get(i));
    }
    
    
    public static void main(String[] args) {
        // Test: Test to see if deck contains all cards and whether it is in
        // a shuffled order
        
        
        System.out.println("Test: Test to see if deck contains all cards and "
                + "whether it is in a shuffled order");
        Deck d1 = new Deck();
        printDeck(d1);
        System.out.println();
        Deck d2 = new Deck();
        printDeck(d2);
        
        System.out.println("Test result: passed\n");
        
        
        System.out.println("Test: Use the spade iterator to iterate through"
                + " all spades in a deck I should be able to iterate through "
                + "13 spades" );
        System.out.println("Expected result: The idea is that the order "
                + "of the output should match order the spades appear in the"
                + "deck");
        SpadeIterator spadeIterator = d2.spadeIterator();
        while(spadeIterator.hasNext())
            System.out.println(spadeIterator.next());
        
        System.out.println("Test result: passed\n");
        
       
        
        System.out.println("Test: Test to see if there is another spade in the"
                + " iterator after deck is fully traversed");
            
        System.out.println("Expected result: false");
        System.out.println("spadeIterator.hasNext(): "+spadeIterator.hasNext());
        System.out.println("Test result: passed\n");
        
        
        
        System.out.println("Test: Use deck iterator to see if it iterates"
                + " through the entire deck");
        
        Iterator<Card> deckIterator = d2.iterator();
        
        System.out.println("Expected result: All cards should be traversed, it"
                + "should also stop once iterated through entire deck."
                + "And also there should be a count of 52 cards");
        int count = 0;
        System.out.println("The following is iterator traversing the deck");
        while(deckIterator.hasNext()) {
            System.out.println(deckIterator.next());         
            count++;
        }
        System.out.println("Amount of cards in deck: " +count);
        System.out.println("Test result: passed\n");


        System.out.println("Test: use deal() twice and "
                + "see whether it prints the two carts on");
        // Test: use deal() twice and see whether it prints the two carts on 
        // deck
        Deck testDeck = new Deck();
        
        System.out.println("The deck:");
        printDeck(testDeck);
        
        System.out.println("Expected result: The top two cards from printDeck");
        System.out.println("\nTop two cards:");
        System.out.println(testDeck.deal());
        System.out.println(testDeck.deal());
        System.out.println("Test result: passed\n");

        
        
        
        System.out.println("Test: See if the size of the deck is"
                + " valid based on the amount of cards dealt");

        System.out.println("Expected result: 50");
        System.out.println("Size of deck : "+testDeck.size());
        System.out.println("Test result: passed\n");

        
        
        System.out.println("Test: Reinitialize the deck, see if the size "
                + "is back to 52 with unique cards and different order");
        System.out.println("Expected result: a completely new deck with "
                + "different order");        
        testDeck.newDeck();
        System.out.println("testDeck.newDeck() reinitialized deck:");
        printDeck(testDeck);
        System.out.println("Test result: Passed\n");

        
        System.out.println("Test: call deal() 52 times");
        System.out.println("Expected result: deal all cards in list above");
        System.out.println("also expected to not run into an exception");
        for(int i = 0; i < 52; i++) {
            System.out.println(testDeck.deal());
        }
        System.out.println("Test result: passed\n");

        
        
        System.out.println("Test: call deal() one more time now that it has"
                + "run out of cards");
        System.out.println("Expected result: throw an exception stating"
                + "there is not another element");
        try {
            testDeck.deal();
        } catch(Exception e) {
            System.out.println(e.getMessage());
        }
        System.out.println("Test result: passed\n");
        
        
        System.out.println("Test: see whether the size of the deck is 0 using"
                + " size()");
        System.out.println("Expected result: 0");
        System.out.println(testDeck.size());
        System.out.println("Test result: passed\n");

        
        
        // Initialise new deck
        testDeck.newDeck();
        
        String filePath = "SpadesDeck.ser";
        
        try {
            FileOutputStream output = new FileOutputStream(filePath);
            ObjectOutputStream objOutput = new ObjectOutputStream(output);

            SpadeIterator iterator = testDeck.spadeIterator();
            
            while(iterator.hasNext()) 
                objOutput.writeObject(iterator.next());
            
            objOutput.close();
        } catch(Exception e) {
            e.printStackTrace();
        } 
        
        
        System.out.println("Test: Read from the SpadesDeck.ser after it has "
                + " had spades from the deck inserted. ");
        System.out.println("Expected result: All spades from deck as an "
                + "output");
        // testing to open from object file
        try {
            FileInputStream input = new FileInputStream(filePath);
            ObjectInputStream objReader = new ObjectInputStream(input);
            try {
                while(true) {
                    Object oIn = (Card) objReader.readObject();
                    System.out.println(oIn);
                }
            } catch(EOFException e) {
                System.out.println("End of ser file");
            }
            
        } catch(Exception e) {
            e.printStackTrace();
        }
        System.out.println("Test result: passed");
        
    }
  
}
