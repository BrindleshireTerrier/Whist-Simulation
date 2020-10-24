
package whist;


import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;
import whist.Card.CompareRank;
import whist.Card.Rank;
import whist.Card.Suit;

public class BasicStrategy implements Strategy {

    
    
    // return whether a hand has a given suit
    public static boolean hasHandSuit(Hand h, Suit s) {
        Hand hand = new Hand(h);
        Iterator<Card> it = hand.iterator();
        
        while(it.hasNext()) {
            if(it.next().getSuit().equals(s)) {
                return true;
            }
        }
        return false;
    }
    
    // get the largest card of a given suit within the hand
    public static Card getLargestCardOfSuit(Hand h, Suit s) {
        Hand sortedHand = new Hand(h).sortByRank();
        Iterator<Card> it = sortedHand.iterator();
        Card max = new Card(Rank.TWO, Suit.SPADES);
        CompareRank cr = max.compareRank();
        
        // Test if hand has the suit
        if(hasHandSuit(h, s)) {
            while(it.hasNext()) {
                Card nextCard = it.next();
                if(cr.compare(nextCard, max) > 0 &&
                    nextCard.getSuit().equals(s)) {
                    max = nextCard;
                }
            }
            return max;
        } 
        return null;
    }
    
    // get the smallest card ignoring a suit within the hand
    public static Card getSmallestCardIgnoreSuit(Hand h, Suit s) {
        Hand sortedHand = new Hand(h).sortByRank();
        Iterator<Card> it = sortedHand.iterator();
        Card min = new Card(Rank.ACE, Suit.CLUBS);
        CompareRank cr = min.compareRank();
        
        // Test if hand has the suit
        if(hasNonTrump(h, s)) {
            while(it.hasNext()) {
                Card nextCard = it.next();
                if(cr.compare(min, nextCard) > 0 &&
                    !(nextCard.getSuit().equals(s))) {
                    min = nextCard;
                }
            }
            return min;
        } 
        return null;                
    }
    
    // get the smallest card of a certain suit within a hand
    public static Card getSmallestCardOfSuit(Hand h, Suit s) {
        Hand sortedHand = new Hand(h).sortByRank();
        Iterator<Card> it = sortedHand.iterator();
        Card min = new Card(Rank.ACE, Suit.CLUBS);
        CompareRank cr = min.compareRank();
        
        // Test if hand has the suit
        if(hasHandSuit(h, s)) {
            while(it.hasNext()) {
                Card nextCard = it.next();
                if(cr.compare(min, nextCard) > 0 &&
                    nextCard.getSuit().equals(s)) {
                    min = nextCard;
                }
            }
            return min;
        } 
        return null;        
    }
    // get the smallest card in the hand
    public static Card getSmallestCardOfHand(Hand h) {
        Hand sortedHand = new Hand(h).sortByRank();
        Iterator<Card> it = sortedHand.iterator();
        
        if(it.hasNext()) {
            return it.next();
        }
        return null;
    }
    // get the largest card in the hand
    public static Card getLargestCardOfHand(Hand h) {
        Hand sortedHand = new Hand(h).sortByRank();
        Iterator<Card> it = sortedHand.iterator();
        Card max = new Card(Rank.TWO, Suit.SPADES);
        CompareRank cr = max.compareRank();
        
        // Test if hand has the suit
        if(it.hasNext()) {
            while(it.hasNext()) {
                max = it.next();
            }
            return max;
        } 
        
        return null;        
    }
    
    // test to see whether the hand has a non trump
    public static boolean hasNonTrump(Hand h, Suit trump) {
        Hand hand = new Hand(h);
        Iterator<Card> it = hand.iterator();
        
        while(it.hasNext()) {
            if(!(it.next().getSuit().equals(trump))) {
                return true;
            }
        }
        return false;
    }  
    
    
    
    // choose the card based on the hand and given trick
    @Override
    public Card chooseCard(Hand h, Trick t)  {

        // Determine if it is the first play in the trick
        if(t.cardsPlayedCount() == 0) {
           // sort hand into ascending order
           Hand sortedHand = h;
           Iterator<Card> it = sortedHand.iterator();
           
           Card max = it.next();
           CompareRank compareRank = max.compareRank();
           // Iterate through hand and select the largest card
           while(it.hasNext()) {
               Card nextCard = it.next();
               if(compareRank.compare(nextCard, max) > 0) {
                   max = nextCard;
               }
           }
           
           // Return the largest card in hand
           return max;
        } 
        
        // Determine whether it is the second play in the trick
        // if it is, that must mean no partners have played yet
        if(t.cardsPlayedCount() == 1) {
            // Can I match suit?
            if(hasHandSuit(h, t.getLeadSuit())) {
                // Cn I beat it?
                
                Card largestOfSuit = getLargestCardOfSuit(h, t.getLeadSuit());
                CompareRank cr = largestOfSuit.compareRank();
                
                if(cr.compare(largestOfSuit,
                        t.getCurrentHighestCardInTrick()) > 0) {
                    // Yes- Play larger
                    return largestOfSuit;
                } else {
                    // no- play smallest
                    Card smallestOfSuit = getSmallestCardOfSuit(h, 
                                            t.getLeadSuit());
                    
                    return smallestOfSuit;
                }
            } else {
                // Can I trump?
                if(hasHandSuit(h, t.getTrumps())) {
                    // Yes- play smallest trumps card
                    Card smallestTrumps = getSmallestCardOfSuit(h,
                                           t.getTrumps());
                    
                    return smallestTrumps;
                } else {
                    // No- play smallest hand
                    Card smallestInHand = getSmallestCardOfHand(h);
                    
                    return smallestInHand;
                }
            }
        }
            
            
    // Determine if it is a partners play in the trick
        if(t.cardsPlayedCount() == 2 || t.cardsPlayedCount() == 3) {
            // Is the partner winning?
            int posOfWinning = t.findWinner();
            int posInterval = t.cardPlayedNextSlot() - posOfWinning;

            if(posInterval == 2 || posInterval == -2) {
                // Yes- Can I match suit?    
                if(hasHandSuit(h, t.getLeadSuit())) {  
                    // Yes- Play lowest
                    Card smallestOfSuit = getSmallestCardOfSuit(h,
                                                t.getLeadSuit());


                    return smallestOfSuit;
                } else {
                    //no - Do I have a non trumps?
                    if(hasNonTrump(h, t.getTrumps())) {
                        // Yes - Play lowest
                        Card smallestNonTrump = getSmallestCardIgnoreSuit(
                                                h, t.getTrumps());
                        return smallestNonTrump;
                    } else {
                        // No- play lowest trumps   
                        Card smallestTrump = getSmallestCardOfSuit(
                                              h, t.getTrumps());
                        return smallestTrump;
                    }
                }
            } else {
                // Can I match suit?
                if(hasHandSuit(h, t.getLeadSuit())) {
                // Cn I beat it?

                Card largestOfSuit = getLargestCardOfSuit(h, t.getLeadSuit());
                CompareRank cr = largestOfSuit.compareRank();

                if(cr.compare(largestOfSuit,
                       t.getCurrentHighestCardInTrick()) > 0) {
                   // Yes- Play larger
                   return largestOfSuit;
                } else {
                   // no- play smallest
                   Card smallestOfSuit = getSmallestCardOfSuit(h, 
                                           t.getLeadSuit());

                   return smallestOfSuit;
                }
                } else {
                // Can I trump?
                if(hasHandSuit(h, t.getTrumps())) {
                   // Yes- play smallest trumps card
                   Card smallestTrumps = getSmallestCardOfSuit(h,
                                          t.getTrumps());

                   return smallestTrumps;
                } else {
                   // No- play smallest hand
                   Card smallestInHand = getSmallestCardOfHand(h);

                   return smallestInHand;
                }
                }
            }
        } 
        return null;
    }

    // store latest completed trick
    @Override
    public void updateData(Trick c) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter
                                                      ("CompletedTricks.txt"));
            
            
            int firstPlayer = c.getFirstPlayer();
            
            writer.write("Trumps: "+c.getTrumps());
            writer.newLine();
            writer.write("Player " + firstPlayer+ ": "
                                +c.getCardsPlayed()[firstPlayer]);
            writer.newLine();
            
            firstPlayer++;
            writer.write("Player " + ((firstPlayer)%4) + ": " +
                    c.getCardsPlayed()[(firstPlayer)%4]);
            writer.newLine();
            
            firstPlayer++;
            writer.write("Player " + ((firstPlayer)%4) + ": " +
                    c.getCardsPlayed()[(firstPlayer)%4]);
            writer.newLine();
            
            firstPlayer++;
            writer.write("Player " + ((firstPlayer)%4) + ": " +
                    c.getCardsPlayed()[(firstPlayer)%4]);
            writer.newLine();    
            writer.write("------------------------");
            writer.newLine();
            
            writer.close();
        } catch (IOException ex) {
            System.out.println(ex);
        }
    }

   
}
