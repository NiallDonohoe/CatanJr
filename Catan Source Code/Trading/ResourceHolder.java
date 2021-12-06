package Trading;


// This a class that defines a holder of cards (market, player, stockpile)
public abstract class ResourceHolder {
	protected int numGold;
	protected int numMolasses;
	protected int numCutlasses;
	protected int numGoats;
	protected int numWood;
	
	public enum ResourceType {
		gold,
		molasses,
		cutlass,
		goat,
		wood,
		none
	}
	
	public ResourceHolder(int numCards) {
		this.numGold = numCards;
		this.numMolasses = numCards;
		this.numCutlasses = numCards;
		this.numGoats = numCards;
		this.numWood = numCards;
	}
	
	public void printTrade(ResourceHolder RH) {
		this.printResources();
		RH.printResources();
	}
	
	public void printResources() {
		System.out.println("Gold: 	   "+this.numGold+"\nMolasses:  "+this.numMolasses+"\nCutlasses: "+this.numCutlasses+"\nGoats:     "+this.numGoats+"\nWood:      "+this.numWood);
	}
	
	// Add resources to class with the specified amount
	public void moveResource(ResourceType resType, int i, ResourceHolder RH) {
		switch (resType){
		case gold:
			this.numGold+=i;
			RH.numGold-=i;
			break;
		case molasses:
			this.numMolasses+=i;
			RH.numMolasses-=i;
			break;
		case cutlass:
			this.numCutlasses+=i;
			RH.numCutlasses-=i;
			break;
		case goat:
			this.numGoats+=i;
			RH.numGoats-=i;
			break;
		case wood:
			this.numWood+=i;
			RH.numWood-=i;
			break;
		default:
			break;
		}
	}
	
	// Checks if a resource is available for a resource holder
	public boolean resourcesAvailable(ResourceType resource, int i) {
		switch(resource) {
		case gold:
			if(this.numGold>=i)
				return true;
		case molasses:
			if(this.numMolasses>=i)
				return true;
		case cutlass:
			if(this.numCutlasses>=i)
				return true;
		case goat:
			if(this.numGoats>=i)
				return true;
		case wood:	
			if(this.numWood>=i)
				return true;
		default:
			return false;
		}
	}
	
	public String instanceType(ResourceHolder RH) {
		if(RH instanceof Player)
			return "Player";
		else if(RH instanceof Stockpile)
			return "Stockpile";
		else if(RH instanceof Market)
			return "Market";
		return null;
	}
}
