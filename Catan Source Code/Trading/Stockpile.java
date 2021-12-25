package Trading;
/**
 * Class for Stockpile in a game of CatanJr.
 * @author Niall Donohoe and Shea O'Sullivan
 *
 */
public class Stockpile extends ResourceHolder{
	//===========================================================
	// Class Variables 
	//===========================================================
	static Stockpile instance = null;
	
	//===========================================================
	// Constructor - Singleton
	//===========================================================
	private Stockpile() {
		super(18);
	}
	
  	//===========================================================
  	// Other Methods
  	//===========================================================
	static public Stockpile getInstance() {
		if(instance == null)
			instance = new Stockpile();
		return instance;
	}
	
	public void printResources() {
		System.out.println("\nStockpile");
		super.printResources();
	}
	/**
	 * checkRefreshStockpile is used to check if a resource has reached 0 and instigates refresh if it has.
	 */
	public void checkRefreshStockpile() {
		if( this.numGold==0)
			refreshResource(ResourceType.gold);
		else if(this.numMolasses==0) 	
			refreshResource(ResourceType.molasses);
		else if(this.numCutlasses==0) 
			refreshResource(ResourceType.cutlass);
		else if(this.numGoats==0) 
			refreshResource(ResourceType.goat);
		else if(this.numWood==0)	
			refreshResource(ResourceType.wood);
	}
	/**
	 * refreshResource is used to determine which players to take resource from.
	 * @param resource the resource type being taken from the players.
	 */
	private void refreshResource(ResourceType resource) {
		if(BluePlayer.playerExists())
			takePlayerResource(resource,BluePlayer.getInstance());
		if(OrangePlayer.playerExists())
			takePlayerResource(resource,OrangePlayer.getInstance());
		if(RedPlayer.playerExists())
			takePlayerResource(resource,RedPlayer.getInstance());
		if(WhitePlayer.playerExists())
			takePlayerResource(resource,WhitePlayer.getInstance());
	}
	/**
	 * takePlayerResource moves all of resource of certain type from a player to the stockpile.
	 * @param resource the resource being taken by the stockpile.
	 * @param player the player the resource is being taken from.
	 */
	private void takePlayerResource(ResourceType resource,Player player) {
		int numResource = 0;
		// Set the number of resources to be moved
		if(resource == ResourceType.gold)
			numResource = player.getNumGold();
		if(resource == ResourceType.molasses)
			numResource = player.getNumMolasses();
		if(resource == ResourceType.cutlass)
			numResource = player.getNumCutlasses();
		if(resource == ResourceType.goat)
			numResource = player.getNumGoats();
		if(resource == ResourceType.wood)
			numResource = player.getNumWood();
		this.moveResource(resource, numResource, player);
	}

    //===========================================================
    // Singleton destroyer for unit testing ONLY
    //===========================================================
    
    public void destroyMe() {
        instance = null;
    }
}
