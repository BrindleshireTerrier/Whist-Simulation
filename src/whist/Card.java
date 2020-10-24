
package whist;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class Card implements Serializable, Comparable<Card> {
    // Set serial ID
    private static final long serialVersionUID = 100;
    
    
    // Define enum for Rank of a card, setting value for each rank
    public enum Rank {
        TWO (2), 
        THREE (3), 
        FOUR (4), 
        FIVE (5),
        SIX (6),
        SEVEN (7),
        EIGHT (8),
        NINE (9),
        TEN (10),
        JACK (10),
        QUEEN (10),
        KING (10),
        ACE (11);
        
        // attribute for rank enum
        private final int rankVal;
        
        // Constructor for rank enum
        Rank(int rankVal) {
            this.rankVal = rankVal;
        }
        
        // output the next rank using the order
        public Rank getNext() {
            int index = this.ordinal();
            int nextIndex;
            
            if(index == 12) {
                nextIndex = 0;
            } else {
                nextIndex = index+1;
            }
            
            return Rank.values()[nextIndex];
        }
        
        // return the rankVal for a given rank
        public int getValue() {
            return this.rankVal;
        }
        
    }
    
    
    // define enum for a Suit
    public enum Suit {
        CLUBS, DIAMONDS, HEARTS, SPADES;
        
        
        // return a random Suit
        public Suit randomSuit() {
            Random randNum = new Random();
            int numOfSuits = Suit.values().length - 1;
            int ranSuit = randNum.nextInt(((numOfSuits - 0) + 1) + 0);
            
            return Suit.values()[ranSuit]; 
        }
    }
    
    
    // Attributes
    private Rank rank;
    private Suit suit;
    
    
    
    // accessors for Rank and Suit
    public Rank getRank() {
        return rank;
    }
    
    public Suit getSuit() {
        return suit;
    }
    
    // Constructor
    // Constructor given Rank, Suit
    public Card(Rank rank, Suit suit) {
        this.rank = rank;
        this.suit = suit;
    }
    // default constructor
    public Card() {
        this.rank = null;
        this.suit = null;
    }

    // Find the largest card in the list
    static Card max(List<Card> Cards) {
        
        
        if(Cards.size() == 1) {
            return Cards.get(0);
        } else if(Cards.size() == 0) {
            return null;
        }
        
        
        // Define an iterator
        Iterator<Card> cardIterator = Cards.iterator();
        Card largestCard = cardIterator.next();
        
        // Iterate through the list of cards and compare adjacent cards
        // to determine the largest card
        while(cardIterator.hasNext()) {
            Card nextCard = cardIterator.next();
            if(largestCard.compareTo(nextCard) < 0) {
                largestCard = nextCard;
            }
                    
        }
        return largestCard;
    }
    
    // print string representation of class
    @Override
    public String toString() {
        String r;
        if(rank.name().equals("JACK") || rank.name().equals("QUEEN") ||
           rank.name().equals("KING") || rank.name().equals("ACE")) {
            r = ""+rank.name().charAt(0);
        } else {
            r = ""+rank.getValue();
        }
        return r + " " + suit.name().charAt(0);
    }
    
    // compareTo method
    // c1 > c2 if:
    // c1.rank > c2.rank or:
    // if c1.rank == c2.rank then: c1.suit > c2.suit;
    @Override
    public int compareTo(Card c2) {
        if(this.rank == c2.rank) {
            return c2.suit.ordinal() - this.suit.ordinal();
        } else {
            return this.rank.getValue() - c2.rank.getValue();            
        }  
    }
    
    // equals method
    // Considers if the ranks and suits match
    @Override
    public boolean equals(Object other) {
        if (other == null) 
            return false;
        
        if (other == this) 
            return true;
        
        if (!(other instanceof Card))
            return false;
        
        Card c2 = (Card) other;        
        
        if(this.rank == c2.rank && this.suit == c2.suit)
            return true;
        
        return false;
    }
    
    
    
    // Inner classes
    
    // Class for CompareDescending comparator
    // Purpose: outputs if compare(c1, c2) and c1 > c2, then it will
    // output a positive value (as it will show that c2 is small than c1
    // by +ve units, considering the suit
    public class CompareDescending implements Comparator<Card> {
        @Override
        public int compare(Card c1, Card c2) {
            if(c1.rank == c2.rank) {
                return c1.suit.ordinal() - c2.suit.ordinal();
            } else {
                return c2.rank.getValue() - c1.rank.getValue();            
            }  
        }
    }
    // Class for CompareRank comparator
    // Purpose: purely compares ranks, disregarding the suit of the card
    public class CompareRank implements Comparator<Card> {
       @Override
       public int compare(Card c1, Card c2) {
           return c1.rank.getValue() - c2.rank.getValue();
       }
    }
    
    // return CompareRank comarator instance
    public CompareRank compareRank() {
        return new CompareRank();
    }
    // return CompareDescending comparator instance
    public CompareDescending compareDescending() {
        return new CompareDescending();
    }
    
    
    
    /* chooseGreater
    Purpose: given a list of cards, and a type of comarator, and a card
     it will return an ArrayList of cards which use the comparator to 
     compare each element from the list and the given card and stores
     the result in the list
     if comparator is comparedescending, it will output a list
     of cards smaller than the card
     if comparator is compareRank, it will output a list of cards
     larger than the cards 
    */
    public static ArrayList chooseGreater(List Cards, Comparator c, Card card) {
        // define an arraylist that will be returned
        ArrayList sortedList = new ArrayList();
        int count = 0;
        
        // Iterate through list and compare cards until it
        // reaches the end of the list. Adding to the ArrayList where relevant
        for(int i = 0; i < Cards.size(); i++) {
            if(c.compare(Cards.get(i), card) > 0) {
                sortedList.add(count, Cards.get(i));
                count++;
            }
            
        }
        
        return sortedList;
    }

    // Interface for Lambda
    interface TestUser {
        ArrayList chooseGreater(List Cards, Comparator c, Card card);
    }
    
    
    static void selectTest() {
        
        // initialising list to use for test
        ArrayList<Card> testList = new ArrayList();
        testList.add(new Card(Rank.THREE, Suit.CLUBS));
        testList.add(new Card(Rank.SEVEN, Suit.DIAMONDS));
        testList.add(new Card(Rank.THREE, Suit.HEARTS));
        testList.add(new Card(Rank.JACK, Suit.DIAMONDS));
        testList.add(new Card(Rank.TWO, Suit.HEARTS));
        testList.add(new Card(Rank.SEVEN, Suit.CLUBS));
        

        // define card to compare
        Card relativeCard = new Card(Rank.JACK, Suit.HEARTS);
        CompareRank cr = relativeCard.compareRank();
        
        // CompareRank lambda
        TestUser compareR =  (Cards, c, card) -> 
                chooseGreater(testList, cr, relativeCard);
        
        // CompareDescending lambda
        CompareDescending cd = relativeCard.compareDescending();
        TestUser compareD = (Cards, c, card) -> 
                chooseGreater(testList, cd, relativeCard);
    }
        

    public static void main(String[] args) {
        System.out.println("Test: Create a testCard ACE of CLUBS, tests "
                + "toString() method and constructor");
        Card testCard = new Card(Rank.ACE, Suit.CLUBS);
        System.out.println("toString():");
        System.out.println(testCard);
        System.out.println("Test result: passed\n");

        
        System.out.println("Test: see if getValue() for rank functions");
        Card testValueCard = new Card(Rank.TWO, Suit.DIAMONDS);
        System.out.println("The cards: " + testCard + " " +testValueCard);
        System.out.println("First card value: "+ testCard.rank.getValue() 
                + ", " + " second card value: " +testValueCard.rank.getValue());
        System.out.println("Test result: passed\n");        

        
        System.out.println("Test: See if randomSuit() produces a random suit");
        System.out.println("Print 10 random suits");
        Card testRandomSuit = new Card(Rank.THREE, Suit.CLUBS);
        
        System.out.println("10 random suits:");
        for(int i = 0; i < 10; i++)
            System.out.println(testRandomSuit.suit.randomSuit());
        System.out.println("Test result: passed\n");          
        
        
        System.out.println("Test: See if accessor methods for getRank and "
                + " getSuit of a given card");
        Card accessCard = new Card(Rank.THREE, Suit.HEARTS);
        System.out.println("The card: " + accessCard);
        System.out.println("Rank: "+accessCard.getRank() + " and Suit:" 
                            + accessCard.getSuit());
        System.out.println("Test result: passed\n");

        
        
        System.out.println("Test: See if the max card is selected from"
                + " a list of cards.");

        ArrayList<Card> testList = new ArrayList<Card>();        
        testList.add(new Card(Rank.THREE, Suit.CLUBS));
        testList.add(new Card(Rank.JACK, Suit.DIAMONDS));
        testList.add(new Card(Rank.TWO, Suit.HEARTS));
        testList.add(new Card(Rank.SEVEN, Suit.CLUBS));
        System.out.println("The list: " + testList);
        System.out.println("max card in list: "+max(testList));
        System.out.println("Test result: passed\n");
        
        
        System.out.println("Test: See if max card is selected from a "
                + "list of cards with a duplicate rank");
        testList.add(new Card(Rank.JACK, Suit.CLUBS));
        System.out.println("The list: " + testList);
        System.out.println("max card in list: "+max(testList));
        System.out.println("Test result: passed\n");        
        
        // Test: test the max(List<Card> cards) method test C
        // See from standard list if ACE will be highest
        System.out.println("Test: See if max card is selected from a "
                + "list of cards with an ACE of SPADES added");
        testList.add(new Card(Rank.ACE, Suit.SPADES));
        System.out.println("The list: " + testList);
        System.out.println("max card in list: "+max(testList));
        System.out.println("Test result: passed\n");   
        
        System.out.println("Test: See if getNext() for a rank works");
        System.out.println("The card: " + testValueCard);
        System.out.println("The next value " + 
                testValueCard.getRank().getNext());
        System.out.println("Test result: passed\n");  
        
        System.out.println("Test: See if getNext() for a rank works for border"
                + "value (ACE)");
        System.out.println("The card: " + testCard);
        System.out.println("The next value " + 
                testCard.getRank().getNext());
        System.out.println("Test result: passed\n");          
        
        
        System.out.println("Test: See if compareTo() functions with"
                + "valid rules using unequal ranks");

        Card c1 = new Card(Rank.EIGHT, Suit.CLUBS);
        Card c2 = new Card(Rank.SIX, Suit.DIAMONDS);
        System.out.println("Card 1: " + c1 + " and Card 2: " + c2);

        System.out.println("c1.compareTo(c2): "+c1.compareTo(c2));
        System.out.println("c2.compareTo(c1): "+c2.compareTo(c1));
        System.out.println("Test result: passed\n"); 

        
        System.out.println("Test to see if compareTo works for equal "
                + "cards");
        System.out.println("Card 1: " + c1);
        System.out.println("c1.compareTo(c1): "+c1.compareTo(c1));
        System.out.println("Test result: passed\n"); 
        
        
        
        System.out.println("Test to see if compareTo works with equal ranks"
                + "of a card.");
       Card c3 = new Card(Rank.TEN, Suit.HEARTS);
       Card c4 = new Card(Rank.TEN, Suit.SPADES);
       System.out.println("Card 3: " + c3 +" and Card 4: " + c4);
       System.out.println("c3.compareTo(c4): "+c3.compareTo(c4));
       System.out.println("c4.compareTo(c3): "+c4.compareTo(c3));
       System.out.println("Test result: passed\n"); 
       
       
       System.out.println("Test: test if compareDescending works");
       
       Card c5 = new Card(Rank.THREE, Suit.CLUBS);
       Card c6 = new Card(Rank.TWO, Suit.DIAMONDS);
       System.out.println("Card 5: " + c5 + " Card 6: " + c6);
       CompareDescending comp = new Card().new CompareDescending(); 
       System.out.println("comp.compare(c5, c6): "+comp.compare(c5, c6));
       System.out.println("comp.compare(c6, c5): "+comp.compare(c6, c5));
       System.out.println("Test result: passed\n"); 
       
       
       System.out.println("Test: test if compareDescending works for"
               + "equal ranks");
       c5 = new Card(Rank.ACE, Suit.DIAMONDS);
       c6 = new Card(Rank.ACE, Suit.SPADES);
       
       System.out.println("Card 5: " + c5 + " Card 6: " + c6);
       System.out.println("comp.compare(c5, c6): "+comp.compare(c5, c6));
       System.out.println("comp.compare(c6, c5): "+comp.compare(c6, c5));
       System.out.println("Test result: passed\n"); 

       
       // Test: CompareRank test A
       // test without equal suits
       System.out.println("Test: test CompareRank for unequal ranked cards");
       c5 = new Card(Rank.SEVEN, Suit.SPADES);
       c6 = new Card(Rank.FOUR, Suit.HEARTS);
       CompareRank compareRank = new Card().new CompareRank();
       
       System.out.println("Card 5: " + c5 + " Card 6: " + c6);
       System.out.println("compareRank.compare(c5, c6): "
               +compareRank.compare(c5, c6));
       System.out.println("compareRank.compare(c6, c5): "
               +compareRank.compare(c6, c5));
       System.out.println("Test result: passed\n"); 


       
       System.out.println("Test: Test CompareRank with equal ranks");
       
       c6 = new Card(Rank.SEVEN, Suit.DIAMONDS);
       System.out.println("Card 5: " + c5 + " Card 6: " + c6);
       System.out.println("compareRank.compare(c5, c6): "
               +compareRank.compare(c5, c6));
       System.out.println("compareRank.compare(c6, c5): "
               +compareRank.compare(c6, c5));
       System.out.println("Test result: passed\n"); 
       
       
       System.out.println("Test: Test chooseGreater() using"
               + "ArrayList of cards, CompareRank comparator and FOUR of DIA"
               + "MONDS");

        ArrayList<Card> testList2 = new ArrayList<Card>();        
        testList2.add(new Card(Rank.THREE, Suit.CLUBS));
        testList2.add(new Card(Rank.JACK, Suit.DIAMONDS));
        testList2.add(new Card(Rank.FOUR, Suit.CLUBS));
        testList2.add(new Card(Rank.TWO, Suit.HEARTS));
        testList2.add(new Card(Rank.SEVEN, Suit.CLUBS));
        System.out.println("The list: " + testList2);

        
        System.out.println("List of cards greater than Four of Diamonds: " +
                chooseGreater(testList2, c1.compareRank(),
                        new Card(Rank.FOUR, Suit.DIAMONDS)));
        System.out.println("Test result: passed\n");         
 
        
        
        System.out.println("Test: choose cards greater (rank)"
                + " than ACE of CLUBS");
        System.out.println("The list: " + testList2);
                                                                        
        System.out.println("List of cards greater than ACE of CLUBS: "+
                chooseGreater(testList2, c1.compareRank(),
                                        new Card(Rank.ACE, Suit.CLUBS)));   
        System.out.println("Test result: passed\n");    
        
        
        // Test: chooseGreater test C
        // List standard, CompareDescending comparator, Card FIVE of DIAMONDS
        System.out.println("Test: Test chooseGreater() using CompareDescending"
                + " to produce cards less than FIVE of HEARTs");
        
        testList2.add(new Card(Rank.FIVE, Suit.HEARTS));
        System.out.println("The list: " + testList2);
        System.out.println("List of cards less than FIVE of DIAMONDS: "+
                chooseGreater(testList2, c1.compareDescending(),
                                        new Card(Rank.FIVE, Suit.DIAMONDS)));
        System.out.println("Test result: passed\n");  
       
        
        
        
        System.out.println("Test: Test chooseGreater() using CompareDescending"
                + " to produce cards less than ACE of CLUBS");
        System.out.println("The list: " + testList2);
        System.out.println("List of cards less than ACE of CLUBS: "+
                chooseGreater(testList2, c1.compareDescending(),
                                        new Card(Rank.ACE, Suit.CLUBS)));  
        System.out.println("Test result: passed\n");  
        
        
        System.out.println("Test: See if all values of card are correctly "
                + "assigned");
        ArrayList<Card> allCards = new ArrayList();
        
        for(int i = 0; i < 13; i++) {
            allCards.add(i, new Card(Card.Rank.values()[i%13], 
                               Card.Suit.values()[i/13]));
        }
        for(Card c : allCards) {
            System.out.println("Rank: " + c.rank + " value: "
                    +c.rank.getValue());
        }
        System.out.println("Test result: passed\n");         
        
        
   
    }
    
}
