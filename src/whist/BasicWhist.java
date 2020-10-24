
package whist;

import java.util.Scanner;
import whist.Card.Suit;

public class BasicWhist {
    
    
    // Define game variables
    static final int NOS_PLAYERS = 4; // Number of players allowed
    static final int NOS_TRICKS = 13; // Number of tricks 
    static final int WINNING_POINTS = 7; // Number of poitns required to win 
                                            // the entire game
    int team1Points=0;  // Team 1: Player 0 and 2
    int team2Points=0; // Team 2: Player 1 and 3
    BasicPlayer[] players; // array of players
    
    // Constructor for the game
    public BasicWhist(BasicPlayer[] p) {
        players = p;
    }
    // using a deck, deal all 52 cards/ 13 per player
    public void dealHands(Deck newDeck){
        for(int i=0;i<52;i++){
            players[i%NOS_PLAYERS].dealCard(newDeck.deal());
        }
    }
    
    // Playing trick
    // A trick is one round of play, where each player plays 1 card
    public Trick playTrick(Player firstPlayer){
        // initialize the trick given the first player
        Trick t=new Trick(firstPlayer.getID());
        int playerID=firstPlayer.getID();
        
        // State the trumps of the trick
        System.out.println("Trumps: " + t.getTrumps());
        
        // iterate through all the players
        for(int i=0;i<NOS_PLAYERS;i++) {
            int next=(playerID+i)%NOS_PLAYERS; // used so regardless of
                                        // starting position, it will pick
                                        // the correct player
             // pick the card played by the player
            try {
                System.out.println("HAND: "+players[next].getHand());
                Card cardPlayed = players[next].playCard(t);
                // output to console the card played by the player
                System.out.println("Player " + next + " played: " + cardPlayed);

                
                // send the card played to the trick
                t.setCard(cardPlayed,players[next]);
            } catch(NullPointerException e) {
                System.out.println("No card played");
            }
        }
        
        
        return t;
    }
    
    // Send a completed trick to all players
    public void sendCompletedTricks(Trick t) {
        for(int i = 0; i < NOS_PLAYERS; i++) {
            players[i].viewTrick(t);
        }
    }
    
    /* playGame
        This is a round, it plays through 13 tricks, so all players go through
        all cards. Once ran out, it will add the score.
    */
    public void playGame(){
        System.out.println("----- Round Start ------");
        int team1RoundWins = 0, team2RoundWins = 0;
        
        // Initiate a deck for the start of the round
        Deck d=new Deck();
        dealHands(d);
        
        
        // Choose a random player to start the round
        int firstPlayer = (int)(NOS_PLAYERS*Math.random());
        // Choose a random trumps for the round
        Suit trumps= Suit.SPADES.randomSuit();
        // Set the trump for the trick and players
        Trick.setTrumps(trumps);
        for(int i=0;i<NOS_PLAYERS;i++)
            players[i].setTrumps(trumps);
        
        // Iterate through number of tricks per round
        for(int i=0;i<NOS_TRICKS;i++){
            System.out.println("----- Trick no " + (i+1) + " -----");
            // Play the given trick for the first player
            Trick t=playTrick(players[firstPlayer]);
            
            // assign the firstPlayer for next round to be 
            // winner of previous trick
            try {
                firstPlayer = t.findWinner();
            } catch(NullPointerException e) {
                System.out.println("Unable to find winner");
            }
            
            // State the player who won
            System.out.println("Winner player number: "+firstPlayer);
            
            // If Player 0 or 2 won the trick, increment team 1
            // else (player 1 or 3) increment team 2
            if(firstPlayer == 0 || firstPlayer == 2) {
                team1RoundWins++;
            } else {
                team2RoundWins++;
            }
            System.out.println("----- End trick -----");
            
            // send the completed trick to all players
            sendCompletedTricks(t);
            
        }
        
        // Once round is complete (13 tricks) state the winner
        System.out.println("----- Round end summary -----");
        System.out.println("Tricks won by Team 1: " + team1RoundWins);
        System.out.println("Tricks won by Team 2: " +team2RoundWins);   
        
        
        // Whoever has most points, difference between points and 6 represents
        // the score of the team for the match
        if(team1RoundWins > team2RoundWins) {
            team1Points += team1RoundWins - 6;
        } else {
            team2Points += team2RoundWins - 6;
        }
        
        
        System.out.println("Team 1 cumulated points: "+team1Points);
        System.out.println("Team 2 cumulated points: "+team2Points);
        System.out.println("");
    }
  
    public void playMatch(){
        // Reset scores at start of the match
        team1Points=0;
        team2Points=0;
        
        // Keep playing until either team reaches the desired amount of points
        while(team1Points<WINNING_POINTS && team2Points<WINNING_POINTS){
            playGame();
        }
        
        // Says the results of hte match
        System.out.println("----- Match Summary -----");
        if(team1Points>=WINNING_POINTS)
            System.out.println("Winning team is team1 1 with "+ team1Points +
                    " points");
        else
            System.out.println("Winning team is team2 1 with "+team2Points + 
                     " points");
        
        System.out.println("");
         
    }
    public static void basicGame(){
        BasicPlayer[] p = new BasicPlayer[NOS_PLAYERS];
        

        // Generate players
        for(int i=0;i<p.length;i++){
            p[i] = new BasicPlayer();
            p[i].setID(i);
        }
        // Create the game
        BasicWhist bg = new BasicWhist(p);
        System.out.println("---------- Match Start ----------");
        bg.playMatch(); //Just plays a single match
        System.out.println("---------- Match End ----------");
    }
    
    // human game
    public static void humanGame() {
        BasicPlayer[] p = new BasicPlayer[NOS_PLAYERS];
        // Generate players
        for(int i=0;i<p.length;i++){
            p[i] = new BasicPlayer();
            p[i].setID(i);
        }
        // Set player 0 to be the human, the rest are basicstrategy
        p[0].setStrategy(new HumanStrategy());
        
        BasicWhist bg = new BasicWhist(p);
        System.out.println("---------- Match Start ----------");
        bg.playMatch(); //Just plays a single match
        System.out.println("---------- Match End ----------");        
    }
    
    public static void main(String[] args) {
        boolean play = true;
        Scanner userinput = new Scanner(System.in);
        
        // Game loop
        do {
            System.out.println("------------------------");
            System.out.println("(H)uman or (B)asic game?");

            String response = userinput.next();
            
            if(response.equals("B")) {
                basicGame();
                System.out.println("Play another game? Y or N");
                response = userinput.next();
                
                if(response.equals("N"))
                    play = false;
            } else if(response.equals("H")) {
                humanGame();
                if(response.equals("N"))
                    play = false;                
            } else {
                System.out.println("Invalid response");
            }
        } while(play == true);

    }
    
}
    
    
    
    
    
    

