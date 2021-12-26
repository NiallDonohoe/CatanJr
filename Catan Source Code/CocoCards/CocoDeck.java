package CocoCards;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Class for Coco Deck made up of Coco Cards
 * 
 * @author  Niall Donohoe & Shea O'Sullivan
 * @version 1.0
 */

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
	/**
	* Constructor for a CocoDeck
	*/
    public CocoDeck() {
    	this.declareCocoCards();
    } 
    // There should only be one instance of CocoDeck
    static public CocoDeck getInstance() {
		if(instance == null) {
			instance = new CocoDeck();
		}
		return instance;
	}
    
    //===========================================================
  	// Getters and Setters
  	//===========================================================
	/**
	* getCocoCardDeck method.
	* @return Arraylist of CocoCards representing the CocoDeck
	*/
    public ArrayList<CocoCard> getCocoCardDeck(){
    	return CocoCardDeck;
    }
    
	/**
	* getCocoCard method.
	* @return cocoCard chosen from the deck.
	*/
    public CocoCard getCocoCard(){
    	this.usedCards++;
    	return CocoCardDeck.get(usedCards-1);
    }
    	
  	//===========================================================
  	// Other Methods
  	//===========================================================
    
	/**
	* declareCocoCards method.
	* Creates the required number of each Coco card type, and then shuffles the deck
	*/
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
    	// shuffle to randomize order
    	Collections.shuffle(CocoCardDeck);	
    }
    
	/**
	* checkIfCardsRemaining method.
	* @return boolean indicating if deck is empty
	*/
    public boolean checkIfCardsRemaining() {
    	if(usedCards<20) {
    		return true;
    	}
    	else return false;
    }
    
    //===========================================================
    // Singleton destroyer for unit testing ONLY
    //===========================================================
    
    public void destroyMe() {
        instance = null;
    }
}
