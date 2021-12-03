package Trading;

public class Market extends ResourceHolder{
	static Market instance = null;
	private Stockpile stockpile = Stockpile.getInstance();
	private Market() {
		super(0);
		refreshMarket();
	}
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
			stockpile.moveResource(ResourceType.gold,5,this);
			this.refreshMarket();
		}	
		else if(this.numMolasses==5) {
			stockpile.moveResource(ResourceType.molasses,5,this);
			this.refreshMarket();
		}	
		else if(this.numCutlasses==5) {
			stockpile.moveResource(ResourceType.cutlasses,5,this);	
			this.refreshMarket();
		}	
		else if(this.numGoats==5) {
			stockpile.moveResource(ResourceType.goats,5,this);	
			this.refreshMarket();
		}
		else if(this.numWood==5) {
			stockpile.moveResource(ResourceType.wood,5,this);
			this.refreshMarket();
		}
	}
	// Refreshes market if checkRefreshMarket finds the market needs to be refreshed and at the start of the game
	private void refreshMarket() {
		this.moveResource(ResourceType.gold,1,stockpile);
		this.moveResource(ResourceType.molasses,1,stockpile);
		this.moveResource(ResourceType.cutlasses,1,stockpile);
		this.moveResource(ResourceType.goats,1,stockpile);
		this.moveResource(ResourceType.wood,1,stockpile);
		System.out.println("\nMarket refreshed!");
	}

}