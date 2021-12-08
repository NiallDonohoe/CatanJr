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
	int cardCounter;
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
    	this.cardCounter++;
    	return CocoCardDeck.get(cardCounter-1);
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
