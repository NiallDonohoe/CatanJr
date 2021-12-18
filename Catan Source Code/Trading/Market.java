package Trading;

public class Market extends ResourceHolder{
	//===========================================================
	// Class Variables 
	//===========================================================
	static Market instance = null;
	
	//===========================================================
	// Constructor - Singleton
	//===========================================================
	private Market() {
		super(0);
		refreshMarket();
	}
  	//===========================================================
  	// Other Methods
  	//===========================================================
	
	// There should only be one instance of Market
	static public Market getInstance() {
		if(instance == null)
			instance = new Market();
		return instance;
	}
	public void printResources() {
		System.out.println("\nMarket");
		super.printResources();
	}
	// Checks if the market needs to be refreshed after every trade with the market.
	protected void checkRefreshMarket() {
		if( this.numGold==5) {
			Stockpile.getInstance().moveResource(ResourceType.gold,5,this);
			this.refreshMarket();
		}	
		else if(this.numMolasses==5) {
			Stockpile.getInstance().moveResource(ResourceType.molasses,5,this);
			this.refreshMarket();
		}	
		else if(this.numCutlasses==5) {
			Stockpile.getInstance().moveResource(ResourceType.cutlass,5,this);	
			this.refreshMarket();
		}	
		else if(this.numGoats==5) {
			Stockpile.getInstance().moveResource(ResourceType.goat,5,this);	
			this.refreshMarket();
		}
		else if(this.numWood==5) {
			Stockpile.getInstance().moveResource(ResourceType.wood,5,this);
			this.refreshMarket();
		}
	}
	
	// Refreshes market if checkRefreshMarket finds the market needs to be refreshed and at the start of the game
	private void refreshMarket() {
		this.moveResource(ResourceType.gold,1,Stockpile.getInstance());
		this.moveResource(ResourceType.molasses,1,Stockpile.getInstance());
		this.moveResource(ResourceType.cutlass,1,Stockpile.getInstance());
		this.moveResource(ResourceType.goat,1,Stockpile.getInstance());
		this.moveResource(ResourceType.wood,1,Stockpile.getInstance());
	}
	
    //===========================================================
    // Singleton destroyer for unit testing ONLY
    //===========================================================
    
    public void destroyMe() {
        instance = null;
    }

}
