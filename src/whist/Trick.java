
package whist;

import java.util.ArrayList;
import whist.Card.CompareRank;
import whist.Card.Rank;
import whist.Card.Suit;

public class Trick {
    
    // attributes
    public static Suit trumps; // stores the trumps of the trick
    private Card[] cardsPlayed; // keep track of cards played
                                // index represents player id
    private int firstPlayer; // player id of first player
    
    
    // Constructor for hte trick
    public Trick(int p) {
        this.firstPlayer = p;
        this.cardsPlayed = new Card[4];
    }
    
    // return trumps
    public Suit getTrumps() {
        return trumps;
    }
   
    
    // returns position of highest card in trick
    public int findWinner() {
        // iterate through the cards played
        for(int i = 0; i < cardsPlayed.length; i++) {
            // sees if values is not null and then sees the position of the 
            // highest value in the trick
            if(cardsPlayed[i] != null && 
                    cardsPlayed[i].equals(getCurrentHighestCardInTrick())) {
                return i;
            }
        }
        return 0;
    }
    // returns the id of the first player
    public int getFirstPlayer() {
        return firstPlayer;
    }
    
    
    // returns highest card in trick (accounting for trumps and lead)
    public Card getCurrentHighestCardInTrick() {
        
        // Define variables
        Card max = new Card(Rank.TWO, Suit.SPADES);
        CompareRank compareRank = max.compareRank();
        ArrayList<Card> listOfCards = new ArrayList();
        
        // Iterate through cardsPlayed and add all non null cards
        for(int i = 0; i < cardsPlayed.length; i++) {
            
            if(cardsPlayed[i] != null) {
                listOfCards.add(cardsPlayed[i]);
            }
        }
        
        
        // if the trick isn't empty 
        if(!listOfCards.isEmpty()) {
            
            // sort the cards into order of rank
            listOfCards.sort(compareRank);

            boolean foundTrump = false;
            boolean foundLead = false;
            
            // see if trump or lead exists in trick
            for(int i = 0; i < listOfCards.size(); i++) {
                if(listOfCards.get(i).getSuit() == trumps) {
                    foundTrump = true;
                }
                if(listOfCards.get(i).getSuit() == this.getLeadSuit()) {
                    foundLead = true;
                }
            }
            
            
            // if trump is found, return the max trump card
            if(foundTrump) {        
                for(int i = 0; i < listOfCards.size(); i++) {
                    if(listOfCards.get(i).getSuit() == trumps &&
                       compareRank.compare(listOfCards.get(i), max) > 0) {
                        max = listOfCards.get(i);
                    }
                }                
                // if lead found, return the max lead suit card
            } else if(foundLead) {
                for(int i = 0; i < listOfCards.size(); i++) {
                    if(listOfCards.get(i).getSuit() == this.getLeadSuit() &&
                       compareRank.compare(listOfCards.get(i), max) > 0) {
                        max = listOfCards.get(i);
                    }
                }                   
            } else { // if neither, just return the first card
                max = listOfCards.get(0);
            }
        }
        return max;
    }
    
    
    // return the cards played
    public Card[] getCardsPlayed() {
        return cardsPlayed;
    }
    
    
    
    // String representation of the trick
    @Override
    public String toString() {
        String trick = "Trick so far \n";
        trick += "Trump: "+trumps + "\n";
        for(int i = 0; i < cardsPlayed.length; i++) {
            if(cardsPlayed[i] != null) {
                trick += "Player "+ i + " played: "+ cardsPlayed[i] +"\n";
            }
        }
        trick += "------------------";
        
        return trick;
    }
    
    
    // Set trumps for given trick
    public static void setTrumps(Suit trumpSuit) {
        trumps = trumpSuit;
    }
    
    // Get the first player of the tricks suit choice
    public Suit getLeadSuit() {
        return cardsPlayed[firstPlayer].getSuit();
    }
    
    // Set card for given player id
    public void setCard(Card c, Player p) {
        if(c == null || p == null) 
            throw new NullPointerException("Invalid arguments.");
        cardsPlayed[p.getID()] = c;

    }
    
    
    // returns the number of cards played in the trick
    public int cardsPlayedCount() {
        int count = 0;
        for(int i = 0; i < cardsPlayed.length; i++) {
            if(cardsPlayed[i] != null)
                count++;
        }
        return count;
    }
    
    // returns the position in cardsPlayed for which the next available slot is
    public int cardPlayedNextSlot() {
        int playerPos = 0;

        // if 1001, the next one has to be 1(1)01
        if(cardsPlayed[0] != null && cardsPlayed[3] != null &&
                cardsPlayed[1] == null)
            return 1;
        // Iterate through cards played
        for(int i = 0; i < cardsPlayed.length; i++) {
            // if there has been 3 plays and the card is null, it has to be
            // this slot next
            if(this.cardsPlayedCount() == 3 && cardsPlayed[i] == null) {
                return i;
                
            // else it will just input the next available slot (will wrap
            // back round if last pos is 4)
            } else if(cardsPlayed[i] != null) {
                playerPos = (i+1)%4;                
            }
        }
        return playerPos;
    }
        
}
