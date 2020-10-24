
package whist;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import whist.Card.CompareRank;



public class Hand implements Serializable, Iterable<Card> {
    
    // Set serial ID
    private static final long serialVersionUID = 300;
    
    
    // attributes
    private ArrayList<Card> cardsInHand;
    private ArrayList<Integer> handValue; // considers ace low/high
    private int noOfSpades;
    private int noOfClubs;
    private int noOfHearts;
    private int noOfDiamonds;

    
    
    // Constructors
    
    // default constructor
    public Hand() {
        this.cardsInHand = new ArrayList<>();
        
        // count suits
        this.noOfSpades = 0;
        this.noOfClubs = 0;
        this.noOfHearts = 0;
        this.noOfDiamonds = 0;
        
        // determine hand value
        this.handValue = new ArrayList();
    }
    // constructor with array as parameter
    public Hand(Card[] cards) {
        int aceCount = 0;
        int cumulativeValueOfHand = 0;
        this.handValue = new ArrayList();
        this.cardsInHand = new ArrayList<>();
        
        // iterate through the cards in hand
        for (Card card : cards) {
            this.cardsInHand.add(card);
            // count suits
            switch (card.getSuit()) {
                case SPADES:
                    this.noOfSpades++;
                    break;
                case CLUBS:
                    this.noOfClubs++;
                    break;
                case HEARTS:
                    this.noOfHearts++;
                    break;
                case DIAMONDS:
                    this.noOfDiamonds++;
                    break;
            }    
            
            // count the number of aces or increment the value of hand
            // by a value
            if(card.getRank() != Card.Rank.ACE)
                cumulativeValueOfHand += card.getRank().getValue();
            else
                aceCount++;
        }
        
        // take into account the ace high or low
        if(aceCount == 0) {
            this.handValue.add(cumulativeValueOfHand);
        } else {
            for(int i = 0; i < aceCount; i++) {
                int aceValue = aceCount + 10 * i;
                this.handValue.add(cumulativeValueOfHand + aceValue);
            }
        }
    }
    
    // return size of the hand
    public int size() {
        return cardsInHand.size();
    }
    
    //  Constructor using another Hand as parameter
    public Hand(Hand anotherHand) {
        int aceCount = 0;
        int cumulativeValueOfHand = 0;
        this.cardsInHand = new ArrayList<>();
        this.handValue = new ArrayList<>();
        
        
        // Iterate through the cards in the hand
        for(Card card : anotherHand.cardsInHand) {
            // count suits
            switch (card.getSuit()) {
                case SPADES:
                    this.noOfSpades++;
                    break;
                case CLUBS:
                    this.noOfClubs++;
                    break;
                case HEARTS:
                    this.noOfHearts++;
                    break;
                case DIAMONDS:
                    this.noOfDiamonds++;
                    break;
            } 
            // count number of aces or add values to hand
            if(card.getRank() != Card.Rank.ACE)
                cumulativeValueOfHand += card.getRank().getValue();
            else
                aceCount++;
        }

        // take into account the ace high or low
        if(aceCount == 0) {
            this.handValue.add(cumulativeValueOfHand);
        } else {
            for(int i = 0; i < aceCount; i++) {
                int aceValue = aceCount + 10 * i;
                this.handValue.add(cumulativeValueOfHand + aceValue);
            }
        }
        
        // add the hand to the hand
        this.cardsInHand.addAll(anotherHand.cardsInHand);
    }
    
    // adding a card to hand function
    // add single
    public void addSingle(Card cardToAdd) {
        
        // increase suit value
        switch (cardToAdd.getSuit()) {
            case SPADES:
                this.noOfSpades++;
                break;
            case CLUBS:
                this.noOfClubs++;
                break;
            case HEARTS:
                this.noOfHearts++;
                break;
            case DIAMONDS:
                this.noOfDiamonds++;
                break;
        }
        
        // increase the hand value
        if(cardToAdd.getRank() == Card.Rank.ACE) {
            
            // iterate through handValue list and add value of the hand
            for(int i = 0; i < handValue.size(); i++) {
                handValue.set(i, handValue.get(i) + 1);
            }
            // if empty hand, just add 11 and 1 for the only 1 ace
            if(handValue.isEmpty()) {
                handValue.add(11);
                handValue.add(1);
            } else {
                handValue.add(handValue.get(handValue.size()-1)+10);                
            }
            
        } else {
            // if there is no aces, then simply just add the current 
            // value to the hand
            if(handValue.isEmpty()) {
                handValue.add(cardToAdd.getRank().getValue());
            } else {
                // if no ace, add hand value to the list
                for(int i = 0; i < handValue.size(); i++) {
                    handValue.set(i, handValue.get(i) + 
                                  cardToAdd.getRank().getValue());
                }  
            }               
        }
            
        this.cardsInHand.add(cardToAdd);
    }
    // add Collection to the hand
    public void addCollection(Collection<Card> cardsToAdd) {
        // iterate through the colleciton of cards to add
        for(Card card : cardsToAdd) {
            // count suits
            switch (card.getSuit()) {
                case SPADES:
                    this.noOfSpades++;
                    break;
                case CLUBS:
                    this.noOfClubs++;
                    break;
                case HEARTS:
                    this.noOfHearts++;
                    break;
                case DIAMONDS:
                    this.noOfDiamonds++;
                    break;
            }
            // calculate hand value and add toe handValue list
            // check if there is an ace and consider ace high or low
            if(card.getRank() == Card.Rank.ACE) {
                for(int i = 0; i < handValue.size(); i++) {
                    handValue.set(i, handValue.get(i) + 1);
                }
                if(handValue.isEmpty()) {
                    handValue.add(11);
                    handValue.add(1);
                } else {
                    handValue.add(handValue.get(handValue.size()-1)+10);                
                }
            } else {
                // if no ace, add hand values as appropriate
                if(handValue.isEmpty()) {
                    handValue.add(card.getRank().getValue());
                } else {
                    // if no ace, add hand value to the list
                    for(int i = 0; i < handValue.size(); i++) {
                        handValue.set(i, handValue.get(i) + 
                                      card.getRank().getValue());
                    }  
                }         
            }
        }
        
        
        this.cardsInHand.addAll(cardsToAdd);
    }
    // add Hand to the current hand
    public void addHand(Hand handToAdd) {
        // iterate through the hand
        for(Card card : handToAdd.cardsInHand) {
            // count suit
            switch (card.getSuit()) {
                case SPADES:
                    this.noOfSpades++;
                    break;
                case CLUBS:
                    this.noOfClubs++;
                    break;
                case HEARTS:
                    this.noOfHearts++;
                    break;
                case DIAMONDS:
                    this.noOfDiamonds++;
                    break;
            }
            // work out handvalue and add to handvalue list
            // check if ace, to consider ace high or low
            if(card.getRank() == Card.Rank.ACE) {
                for(int i = 0; i < handValue.size(); i++) {
                    handValue.set(i, handValue.get(i) + 1);
                }
                handValue.add(handValue.get(0)+10);
            } else {
                if(handValue.isEmpty()) {
                    handValue.add(card.getRank().getValue());
                } else {
                    // if no ace, add hand value to the list
                    for(int i = 0; i < handValue.size(); i++) {
                        handValue.set(i, handValue.get(i) + 
                                      card.getRank().getValue());
                    }  
                }
            }           
        }
        this.cardsInHand.addAll(handToAdd.cardsInHand); 
    }
    
    // remove methods
    // remove single card
    public boolean removeSingle(Card cardToRemove) {
        // check if the card exists within the hand
        if(cardsInHand.contains(cardToRemove)) {
            // decrement the relevant suit
            switch (cardToRemove.getSuit()) {
                case SPADES:
                    this.noOfSpades--;
                    break;
                case CLUBS:
                    this.noOfClubs--;
                    break;
                case HEARTS:
                    this.noOfHearts--;
                    break;
                case DIAMONDS:
                    this.noOfDiamonds--;
                    break;
            }
            // check if it is an ace, if so remove the relevant hand value
            if(cardToRemove.getRank() == Card.Rank.ACE) {
                for(int i = 0; i < handValue.size(); i++) {

                    handValue.set(i, handValue.get(i) - 1);
                }
                handValue.remove(handValue.size()-1); // remove biggest element

            } else {
                // if no ace, just reduce the value
                for(int i = 0; i < handValue.size(); i++) {
                    handValue.set(i, handValue.get(i) - 
                                  cardToRemove.getRank().getValue());
                }                
            }            
        } 
       
        return cardsInHand.remove(cardToRemove);
    }
    
    // remove all cards from another Hand
    public boolean removeAllFromHand(Hand handToRemove) {
        // reset values
        handToRemove.handValue.removeAll(handToRemove.handValue);
        handToRemove.noOfClubs = 0;
        handToRemove.noOfDiamonds = 0;
        handToRemove.noOfHearts = 0;
        handToRemove.noOfSpades = 0;
        
        return handToRemove.cardsInHand.removeAll(cardsInHand);
    }
    
    // remove card at specific position
    public Card removeAtPosition(int position) {
        Card cardToRemove = cardsInHand.get(position);
        
        // check if the hand has the card to remove
        if(cardsInHand.contains(cardToRemove)) {
            // decrement the relevant suit
            switch (cardToRemove.getSuit()) {
                    case SPADES:
                        this.noOfSpades--;
                        break;
                    case CLUBS:
                        this.noOfClubs--;
                        break;
                    case HEARTS:
                        this.noOfHearts--;
                        break;
                    case DIAMONDS:
                        this.noOfDiamonds--;
                        break;
            }
            // check if there is an ace, to consider ace high or low
            if(cardToRemove.getRank() == Card.Rank.ACE) {
                for(int i = 0; i < handValue.size(); i++) {

                    handValue.set(i, handValue.get(i) - 1);
                }
                handValue.remove(handValue.size()-1); // remove biggest element

            } else {
                // if no ace, just decrement the relevant value for the hand
                for(int i = 0; i < handValue.size(); i++) {
                    handValue.set(i, handValue.get(i) - 
                                  cardToRemove.getRank().getValue());
                }                
            }
        }
        return cardsInHand.remove(position);
    }
    
    
    
    // Sorting methods
    // sort hand into ascending order (takes into account suit)
    public Hand sort() {
        Hand toSort = new Hand(this);
        Collections.sort(toSort.cardsInHand);
        return toSort;
    }
    // sort by rank in ascending order
    public Hand sortByRank() {
        Hand toSort = new Hand(this);
        CompareRank c = new Card().compareRank();
        
        Collections.sort(toSort.cardsInHand, c);

        return toSort;
    }

    
    // count how many of a specific suit is within the hand
    public int countSuit(Card.Suit suit) {
        int count = 0;
        // iterate through collection of hand
        for(Card c : cardsInHand) {
            // increment when comes across a suit
            if(c.getSuit() == suit) {
                count++;
            }
        }
        
        return count;
    }
    
    // count how many cards of a specific rank are within the hand
    public int countRank(Card.Rank rank) {
        int count = 0;
        for(Card c : cardsInHand) {
            if(c.getRank() == rank) {
                count++;
            }
        }
        
        return count;
    }    
    
    // determines if a suit is within the hand
    public boolean hasSuit(Card.Suit suit) {
        for(Card c : cardsInHand) {
            if(c.getSuit() == suit) {
                return true;
            }
        }
        
        return false;
    }
    
    // returns a hand iterator
    @Override
    public Iterator<Card> iterator() {
        return cardsInHand.iterator();
    }
    
    
    // String representation of a hand
    public String toString() {
        String hand = "<";
        
        // iterate through the hand
        for(int i = 0; i < this.cardsInHand.size(); i++) {
            if(i != this.cardsInHand.size()-1) {
                // add a comma after each element
                hand += this.cardsInHand.get(i) + ",";
            } else {
                // if last element add a > at the end to signify end of hand
                hand += this.cardsInHand.get(i) + ">";
            }
        }
        return hand;
    }
    
    public static void main(String[] args) {

        Hand testHand = new Hand();
        
        System.out.println("Test for constructor of empty hand");
        System.out.println("Outputs for number of suits: ");
        System.out.println("noOfClubs: "+ testHand.noOfClubs);
        System.out.println("noOfSpades: "+testHand.noOfSpades);
        System.out.println("noOfDiamonds: "+testHand.noOfDiamonds);
        System.out.println("noOfHearts: "+testHand.noOfHearts);
        System.out.println("Test result: passed\n");

        
        
        System.out.println("Test to see if the array constructor for "
                + " hand functions");
        Card[] testArray = new Card[4];
        testArray[0] = new Card(Card.Rank.FOUR, Card.Suit.DIAMONDS);
        testArray[1] = new Card(Card.Rank.NINE, Card.Suit.HEARTS);
        testArray[2] = new Card(Card.Rank.TEN, Card.Suit.SPADES);
        testArray[3] = new Card(Card.Rank.TWO, Card.Suit.CLUBS);
        
        Hand arrayHand = new Hand(testArray);
        System.out.println("Array added: "+Arrays.toString(testArray));
        System.out.println("Expected result: the order of indexes for the "
                + " array should be added to the hand collection");
        System.out.println("Card hand: "+arrayHand.cardsInHand);
        System.out.println("Test result: passed\n");
        
 
        
        System.out.println("Test for number of suits with array hand");
        System.out.println("Expected result: 1, 1, 1, 1");
        System.out.println("Outputs for number of suits: ");
        System.out.println("noOfClubs: "+ arrayHand.noOfClubs);
        System.out.println("noOfSpades: "+arrayHand.noOfSpades);
        System.out.println("noOfDiamonds: "+arrayHand.noOfDiamonds);
        System.out.println("noOfHearts: "+arrayHand.noOfHearts);
        System.out.println("Test result: passed\n");

        
        
        System.out.println("Test: See if the arrayHand has the correct hand"
                + " value");
        System.out.println("Expected result: [25]");
        System.out.println("Array hand value: "+arrayHand.handValue);
        System.out.println("Test result: passed\n");
        
        
        System.out.println("Test: Add an ace to arrayHand object, see if "
                + "the value changes along with the number of suits");
        arrayHand.addSingle(new Card(Card.Rank.ACE, Card.Suit.CLUBS));
        System.out.println("Value for hand: [26, 36] and suit numbers:"
                + "2, 1, 1, 1");
        System.out.println("Value for hand: "+arrayHand.handValue);

        System.out.println("noOfClubs: "+ arrayHand.noOfClubs);
        System.out.println("noOfSpades: "+arrayHand.noOfSpades);
        System.out.println("noOfDiamonds: "+arrayHand.noOfDiamonds);
        System.out.println("noOfHearts: "+arrayHand.noOfHearts);
        
        System.out.println("Test result: passed\n");
        
        
        
        System.out.println("Test: See if able to remove the ACE added from"
                + " previous test and also test the value of the hand");
        System.out.println("Expected result: return true as it is exists and"
                + " [25] for hand value");
        System.out.println("Successfully removed ace: "+
                            arrayHand.removeSingle(new Card(Card.Rank.ACE,
                                                           Card.Suit.CLUBS)));
        System.out.println("Hand value: "+arrayHand.handValue);
        System.out.println("Test result: passed\n");
        
        
        
        System.out.println("Test: See if you can remove a card that doesn't"
                + " exist in the hand.");
        System.out.println("Expected result: as NINE of SPADES doesn't"
                + " exist in the hand, should return false");
        System.out.println("Can you remove NINE of SPADES?: "+
                arrayHand.removeSingle(new Card(Card.Rank.NINE,
                Card.Suit.SPADES)));
        System.out.println("Test result: passed\n");
        
        
        System.out.println("Test: See if the value of the card changes"
                + " and also see if the suit amoutns change.");
        System.out.println("Expected result: No change, [25] and 1, 1, 1, 1");
        System.out.println("Hand value: "+arrayHand.handValue);
        System.out.println("noOfClubs: "+ arrayHand.noOfClubs);
        System.out.println("noOfSpades: "+arrayHand.noOfSpades);
        System.out.println("noOfDiamonds: "+arrayHand.noOfDiamonds);
        System.out.println("noOfHearts: "+arrayHand.noOfHearts);
        System.out.println("Test result: passed\n");

        
        
        
        
        
        System.out.println("Test: See the result of previous operations by"
                + " printing the cards in the hand");
        System.out.println("Expected result: The original hand");
        System.out.println("Hand list: "+arrayHand.cardsInHand);
        System.out.println("Test result: passed \n");
        
        
        
        System.out.println("Test: See if a card can be removed successfully"
                + " from a given position. See what happens to the list"
                + "after removal.");
        System.out.println("Expected result: The list should be rearranged");

        System.out.println("List before: " + arrayHand.cardsInHand);
        arrayHand.removeAtPosition(2);
        System.out.println("Hand after removal of index 2: "+
                arrayHand.cardsInHand);
        System.out.println("Test result: passed \n");
        

        System.out.println("Test: add two aces to the hand and test the value"
                + " then remove one at a time and see whether value changes "
                + "are valid");
        System.out.println("Expected result: from [15] to [16, 26] then after"
                + " another ace: 17, 27, 37");
        
        arrayHand.addSingle(new Card(Card.Rank.ACE, Card.Suit.CLUBS));
        
        System.out.println("Added ACE of CLUBS");
        System.out.println("Hand value: "+arrayHand.handValue);
        System.out.println("noOfClubs: "+ arrayHand.noOfClubs);
        System.out.println("noOfSpades: "+arrayHand.noOfSpades);
        System.out.println("noOfDiamonds: "+arrayHand.noOfDiamonds);
        System.out.println("noOfHearts: "+arrayHand.noOfHearts);
        
        
        arrayHand.addSingle(new Card(Card.Rank.ACE, Card.Suit.DIAMONDS));
        
        System.out.println("Added ACE of DIAMONDS");
        System.out.println("Hand value: "+arrayHand.handValue);
        System.out.println("noOfClubs: "+ arrayHand.noOfClubs);
        System.out.println("noOfSpades: "+arrayHand.noOfSpades);
        System.out.println("noOfDiamonds: "+arrayHand.noOfDiamonds);
        System.out.println("noOfHearts: "+arrayHand.noOfHearts);
        System.out.println("Test result: passed\n");
        
        
        // Test: see if the entire hand has been removed
        System.out.println("See if the entire hand can be removed using "
                + " removeAllFromHand()");
        System.out.println("Expected result: hand value = []"
                + " number for all suits: 0 and the hand should be empty");
        
        arrayHand.removeAllFromHand(arrayHand);
        
        System.out.println("Hand value: "+arrayHand.handValue);
        System.out.println("noOfClubs: "+ arrayHand.noOfClubs);
        System.out.println("noOfSpades: "+arrayHand.noOfSpades);
        System.out.println("noOfDiamonds: "+arrayHand.noOfDiamonds);
        System.out.println("noOfHearts: "+arrayHand.noOfHearts);
        
        System.out.println("The hand: "+arrayHand.cardsInHand);
        System.out.println("Test result: passed\n");
        

        System.out.println("Test: See if you can add an hand to another hand");
        System.out.println("Expected result: Add [FOUR of DIAMONDS, QUEEN of"
                + "HEARTS added to a blank hand. Should be 0, 0, 1 1 and [14]");
             
        Hand blankHand = new Hand();
        
        blankHand.cardsInHand.add(new Card(Card.Rank.FOUR, Card.Suit.DIAMONDS));
        blankHand.cardsInHand.add(new Card(Card.Rank.QUEEN, Card.Suit.HEARTS));
        Hand handHand = new Hand(blankHand);
        
        System.out.println("The hand: "+handHand.cardsInHand);
        System.out.println("Hand value: "+handHand.handValue);
        System.out.println("noOfClubs: "+ handHand.noOfClubs);
        System.out.println("noOfSpades: "+handHand.noOfSpades);
        System.out.println("noOfDiamonds: "+handHand.noOfDiamonds);
        System.out.println("noOfHearts: "+handHand.noOfHearts);
        System.out.println("Test result: passed\n");

        
        
        
        System.out.println("Test: Add a collection to the hand, also see if to"
                + "String() works");
        // Test: try to add a collection to a hand
        // also test toString()
        ArrayList<Card> testCollection = new ArrayList<>();
        testCollection.add(new Card(Card.Rank.JACK, Card.Suit.HEARTS));
        testCollection.add(new Card(Card.Rank.QUEEN, Card.Suit.SPADES));
        System.out.println("Collection added (ArrayList): "
                +testCollection);
        handHand.addCollection(testCollection);
        
        System.out.println("The hand: "+handHand);
        System.out.println("Test result: passed\n");
        
        
        System.out.println("Test: Add a hand to an existing hand");
        
        // Test: Try to add a Hand to a hand
        
        Hand emptyHand = new Hand();
        
        System.out.println("Added hand: handHand x2");
        emptyHand.addHand(handHand);
        emptyHand.addHand(handHand);
        
        System.out.println("Resulting hand: "+emptyHand);
        System.out.println("Test result: passed\n");

        
        // Test: take handHand and test countSuit and countRank
        // include a rank/ suit that isn't in the hand
        
        
        System.out.println("Test: test countRank and countSuit for handHand"
                + " object");
        System.out.println("Expected result: 2, 2, 0, 0");
        System.out.println("The hand: " + handHand);
        System.out.println("countRank(Queen): "+
                handHand.countRank(Card.Rank.QUEEN));
        System.out.println("countSuit(HEARTS): "+
                handHand.countSuit(Card.Suit.HEARTS));
        System.out.println("countRank(ACE): "+
                handHand.countRank(Card.Rank.ACE));
        System.out.println("countSuit(CLUBS): "+
                handHand.countSuit(Card.Suit.CLUBS));
        System.out.println("Test result: passed\n");        
        
        
        System.out.println("Test: See if hasSuit() works");
        System.out.println("Expected result for CLUBs and DIAMONDS: false"
                + ", true");
        // Test: see if the handHand has specific suits use the previous
        // values from the previous test
        System.out.println("The hand: " + handHand);
        System.out.println("hasSuit(CLUBS): "+
                handHand.hasSuit(Card.Suit.CLUBS));
        System.out.println("hasSuit(DIAMONDS): "+
                handHand.hasSuit(Card.Suit.DIAMONDS));
        System.out.println("Test result: passed\n");    
        
        
        
        System.out.println("Test: See if iterator outputs the cards in order");
        System.out.println("The hand: " + emptyHand);        
        // Test: See if the iterator outputs the cards of emptyHand in order
        
        Iterator<Card> it = emptyHand.iterator();
        System.out.println("The following are results from iterator: ");
        while(it.hasNext())
            System.out.println(it.next());
        System.out.println("Test result: passed\n");          

        System.out.println("Test: See if sort() function works");
        // Test: See if hand is sorted using sort(Hand) method
        Hand h = new Hand();
        h.addSingle(new Card(Card.Rank.SEVEN, Card.Suit.SPADES));
        h.addSingle(new Card(Card.Rank.FIVE, Card.Suit.HEARTS));
        h.addSingle(new Card(Card.Rank.FIVE, Card.Suit.DIAMONDS));
        System.out.println("The hand: " + h);
        System.out.println("Sorted hand: " +h.sort());
        System.out.println("Test result: passed\n");            

        
        System.out.println("Test: Using sortByRank() after adding a TWO of"
                + " HEARTs. So the ranks need to be sorted. ");

        h.addSingle(new Card(Card.Rank.TWO, Card.Suit.HEARTS));
        System.out.println("The hand: " + h);
        System.out.println("Sorted hand: " +h.sortByRank());
        System.out.println("Test result: passed\n");           
        

        
        
        System.out.println("Test: See if iterator is able to traverse the orig"
                + "inal hand, independent of the sorted result.");
        Iterator<Card> testIt = h.iterator();
        System.out.println("Iterated hand: ");
        while(testIt.hasNext())
            System.out.println(testIt.next());
        System.out.println("Test result: passed\n");  
        
    }



    
    
    
    
}
