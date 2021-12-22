package CocoCards;
import java.util.ArrayList;
import java.util.Collections;

import Trading.Player;

public class CocoDeck {
	
	//===========================================================
	// Class Variables 
	//===========================================================	
	private ArrayList<CocoCard> CocoCardDeck = new ArrayList<CocoCard>();
	static CocoDeck instance = null;
	private int usedCards=0;
	public enum cocoCardType{
		moveGhostCaptain,
		getShipOrLair,
		getGoatAndCutlass,
		getMolassesAndWood
	}
	
	//===========================================================
	// Constructor - Singleton
	//===========================================================
    public CocoDeck() {
    	this.declareCocoCards();
    } 
    // There should only be one instance of Board
    static public CocoDeck getInstance() {
		if(instance == null) {
			instance = new CocoDeck();
		}
		return instance;
	}
    
    //===========================================================
  	// Getters and Setters
  	//===========================================================
    
    public ArrayList<CocoCard> getCocoCardDeck(){
    	return CocoCardDeck;
    }
    
    public CocoCard getCocoCard(){
    	this.usedCards++;
    	return CocoCardDeck.get(usedCards-1);
    }
    
    public boolean checkIfCardsRemaining() {
    	if(usedCards<20) {
    		return true;
    	}
    	else return false;
    }
    
  	
  	//===========================================================
  	// Other Methods
  	//===========================================================
    public void declareCocoCards() {
    	
    	for(int i = 0; i<11; i++) {
    		CocoCardDeck.add(new MoveGhostCaptainCoco());
    	}
    	
    	for(int i = 0; i<3; i++) {
    		CocoCardDeck.add(new GetShipOrLairCoco());
    	}
    	
    	for(int i = 0; i<3; i++) {
    		CocoCardDeck.add(new GetGoatAndCutlassCoco());
    	}
    	
    	for(int i = 0; i<3; i++) {
    		CocoCardDeck.add(new GetMolassesAndWoodCoco());
    	} 
    	
    	Collections.shuffle(CocoCardDeck);	
    }
    
    //===========================================================
    // Singleton destroyer for unit testing ONLY
    //===========================================================
    
    public void destroyMe() {
        instance = null;
    }
}
