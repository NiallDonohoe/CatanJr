package Trading;

import Trading.ResourceHolder.ResourceType;

public class Stockpile extends ResourceHolder{
	
	static Stockpile instance = null;
	private Stockpile() {
		super(18);
	}
	
	static public Stockpile getInstance() {
		if(instance == null)
			instance = new Stockpile();
		return instance;
	}
	
	public void printResources() {
		System.out.println("\nStockpile");
		super.printResources();
	}
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
	private void refreshResource(ResourceType resource) {
		if(BluePlayer.PlayerExists())
			takePlayerResource(resource,BluePlayer.getInstance());
		if(OrangePlayer.PlayerExists())
			takePlayerResource(resource,BluePlayer.getInstance());
		if(RedPlayer.PlayerExists())
			takePlayerResource(resource,BluePlayer.getInstance());
		if(WhitePlayer.PlayerExists())
			takePlayerResource(resource,BluePlayer.getInstance());
	}
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
