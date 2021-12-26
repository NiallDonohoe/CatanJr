package CocoCards;

import Trading.Player;

/**
 * Abstract Class for Coco Cards in a game of CatanJr
 * 
 * @author  Niall Donohoe & Shea O'Sullivan
 * @version 1.0
 */

public abstract class CocoCard {
	//===========================================================
	// Class Variables 
	//===========================================================
	protected CocoDeck.cocoCardType cardType;

	//===========================================================
	// Class Methods
	//===========================================================
    /**
     * Abstract use method for a coco card. 
     * Overridden in each subclass
     */
	public abstract void use(Player player);
	
	//===========================================================
	// Getters & Setters
	//===========================================================
    /**
     * getCardType method.
     * @return gets the type of coco card
     * Overridden in each subclass
     */
	public CocoDeck.cocoCardType getCardType() {
		return cardType;
	}
}
