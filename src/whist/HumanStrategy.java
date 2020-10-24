
package whist;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;
import java.util.Scanner;
import whist.Card.Rank;
import whist.Card.Suit;

public class HumanStrategy implements Strategy{

    @Override
    public Card chooseCard(Hand h, Trick t) {
        
        Card cardToPlay = null;
        
        Scanner userinput = new Scanner(System.in);
        
        System.out.println("Your Hand: " + h);
        boolean validInput = false;
        
        Rank rankChoice = null;
        Suit suitChoice = null;
        
        // Test to see if the input is in the correct format
        while(validInput == false) {
            System.out.println("\n"+t);
            boolean correctFormat = true;
            System.out.print("The following are valid inputs:");
            System.out.print(" 2, 3, 4, 5, 6, 7, 8, 9, 10, J, Q, K, A | "
                    + "C, D, H, S\n");
            System.out.println("Your hand: " + h);
            System.out.println("Rank: ");
            String rank = userinput.nextLine();
            switch(rank) {
                case "2":
                    rankChoice = Rank.TWO;
                break;
                case "3":
                    rankChoice = Rank.THREE;                    
                    break;
                case "4":
                    rankChoice = Rank.FOUR; 
                    break;
                
                case "5":
                    rankChoice = Rank.FIVE; 
                    break;
                case "6":
                    rankChoice = Rank.SIX; 
                    break;
                case "7":
                    rankChoice = Rank.SEVEN;
                    break;
                case "8":
                    rankChoice = Rank.EIGHT;
                    break;
                case "9":
                    rankChoice = Rank.NINE; 
                    break;
                case "10":
                    rankChoice = Rank.TEN;
                    break;
                case "J":
                    rankChoice = Rank.JACK;
                    break;
                case "Q":
                    rankChoice = Rank.QUEEN;
                    break;
                case "K":
                    rankChoice = Rank.KING;
                    break;
                case "A":
                    rankChoice = Rank.ACE;
                    break;
                    
                default:
                    System.out.println("Invalid rank");
                    correctFormat = false;
                    break;
            }
            if(correctFormat) {
            System.out.println("Suit: ");
            String suit = userinput.nextLine();
            switch(suit) {
                case "C":
                    suitChoice = Suit.CLUBS;
                    break;
                case "D":
                    suitChoice = Suit.DIAMONDS;
                    break;
                case "H":
                    suitChoice = Suit.HEARTS;
                    break;
                case "S":
                    suitChoice = Suit.SPADES;
                    break;
                default:
                    System.out.println("invalid suit");
                    correctFormat = false;
                    break;
            }
            }
            // if the format was correct, continue
            if(correctFormat) {
                cardToPlay = new Card(rankChoice, suitChoice);
                    boolean foundCard = false;
                    // Test to see if card is in Hand
                    Hand testHand = new Hand(h);
                    Iterator<Card> it = testHand.iterator();
                    while(it.hasNext()) {
                        Card nextCard = it.next();
                        if(nextCard.equals(cardToPlay)) {
                            foundCard = true;
                        }
                    }
                    if(!foundCard) {
                        System.out.println("Card not in hand");
                    }
                    // test to see if matches lead suit
                    boolean suitValid = true;
                    boolean handHasLeadSuit = false;
                    testHand = new Hand(h);
                    it = testHand.iterator();
                    if(t.cardsPlayedCount() > 0) {
                        while(it.hasNext()) {
                            Card nextCard = it.next();
                            if(nextCard.getSuit().equals(t.getLeadSuit())) {
                                handHasLeadSuit = true;
                            }
                        }
                        if(handHasLeadSuit) {
                            if(!(cardToPlay.getSuit()
                                    .equals(t.getLeadSuit()))) {
                                suitValid = false;
                            }
                        } 
                        
                        if(!suitValid) {
                            System.out.println("Suit doesn't match");
                            System.out.println("Lead suit is " + 
                                    t.getLeadSuit());
                        }
                    } 




                    // Test for all factors
                    if(foundCard == true && suitValid == true) {
                        validInput = true;
                    }                
            }
            
            
        }
        
        return cardToPlay;
    }

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
