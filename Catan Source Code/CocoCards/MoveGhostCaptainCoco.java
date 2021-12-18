package CocoCards;

import Trading.Player;

/**
 * Class for Coco Card letting player move ghost Captain
 * 
 * @author  Niall Donohoe & Shea O'Sullivan
 * @version 1.0
 */

public class MoveGhostCaptainCoco extends CocoCard{
	//===========================================================
	// Constructor
	//===========================================================
	/**
	* Constructor for a shipOrLair Coco Card
	*/
	public MoveGhostCaptainCoco() {
		this.cardType = CocoDeck.cocoCardType.moveGhostCaptain;
	}
	
	public void use(Player player) {
		player.moveGhostCaptain(1,2);
	}

}
