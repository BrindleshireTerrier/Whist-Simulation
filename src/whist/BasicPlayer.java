
package whist;

import whist.Card.Suit;



public class BasicPlayer implements Player {

    
    // Attributes
    private Strategy strategy; // Strategy of player, can be huamn/basic
    private Suit currentTrumps; // Trumps for the player the trick is in
    private Hand playerHand;
    private int playerID;
    
    
    
    // Constructor for a Player
    public BasicPlayer() {
        this.playerID = 0;
        this.strategy = new BasicStrategy();
        this.playerHand = new Hand();
        
    }
    
    // get the hand of the player
    public Hand getHand() {
        return this.playerHand;
    }
    
    
    // set playerID of the player
    public void setID(int playerID) {
        this.playerID = playerID;
    }
    
    
    // deal a card to the player, adding to the hand
    @Override
    public void dealCard(Card c) {
        this.playerHand.addSingle(c);
    }

    
    // Set the strategy of the player customly
    @Override
    public void setStrategy(Strategy s) {
        this.strategy = s;
    }

    // Play a card into a trick
    @Override
    public Card playCard(Trick t) {
        
        //Pick the card played based on strategy
        Card playedCard = strategy.chooseCard(this.playerHand, t);
        
        // Set the the card played and the player
        t.setCard(playedCard, this);

        // test if the card is in the hand
        if(!this.playerHand.removeSingle(playedCard)) {
            throw new NullPointerException("Cannot remove card");
        }
        
        return playedCard;
    }

    
    // Send the compelted trick to the startegy update
    @Override
    public void viewTrick(Trick t) {
        strategy.updateData(t);
    }

    
    // Set trumps to the suit passed as arguments
    @Override
    public void setTrumps(Card.Suit s) {
        this.currentTrumps = s;
    }

    
    // return the id of the player
    @Override
    public int getID() {
        return this.playerID;
    }
    
}
