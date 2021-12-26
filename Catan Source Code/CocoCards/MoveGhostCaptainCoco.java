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
	
	//===========================================================
	// Methods
	//===========================================================
    /**
     * use method for a coco card. 
     * Allows player to move the Ghost Captain, this is handled through the GUI
     */
	@Override
	public void use(Player player) {
		System.out.println(player.getColour() + " can move the ghost Captain in the GUI");
		// Moving of the Ghost Captain is handled by the GUI
	}

}
