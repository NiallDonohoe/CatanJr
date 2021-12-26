package Trading;

/**
 * Class for ResourceHolder in a game of CatanJr. An object that holds resources.
 * 
 * @author  Niall Donohoe & Shea O'Sullivan
 * @version 1.0
 */
public abstract class ResourceHolder {
	//===========================================================
	// Class Variables 
	//===========================================================
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
	//===========================================================
	// Constructor 
	//===========================================================
	public ResourceHolder(int numCards) {
		this.numGold = numCards;
		this.numMolasses = numCards;
		this.numCutlasses = numCards;
		this.numGoats = numCards;
		this.numWood = numCards;
	}
  	//===========================================================
  	// Other Methods
  	//===========================================================
	public void printTrade(ResourceHolder RH) {
		this.printResources();
		RH.printResources();
	}
	
	public void printResources() {
		System.out.println("Gold: 	   "+this.numGold+"\nMolasses:  "+this.numMolasses+"\nCutlasses: "+this.numCutlasses+"\nGoats:     "+this.numGoats+"\nWood:      "+this.numWood);
	}
	
	// Add resources to class with the specified amount
	/**
	 * moveResource moves resources from ResourceHolder holder in the argument of the method
	 * to the resource holder that the method is being used on.
	 * @param resType The resource type being moved.
	 * @param i The number of the resource being moved.
	 * @param RH The resource holder that the resources are being taken from.
	 */
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
		Stockpile.getInstance().checkRefreshStockpile(); // makes sure no resources are depleted in stockpile.
	}
	/**
	 * resourceAvailable checks if a ResourceHolder has a specified amount of resources of 
	 * a certain type.
	 * @param resource The resource type that is being checked.
	 * @param i The number of resources that we are checking the resource holder has.
	 * @return boolean If the ResourceHolder has the required number of resources.
	 */
	// Checks if a resource is available for a resource holder
	public boolean resourcesAvailable(ResourceType resType, int i) {
		switch(resType) {
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
	/**
	 * instanceType determines the type of ResourceHolder and returns the name of the instance in string form.
	 * @param RH The ResourceHolder that is being tested.
	 * @return String Containing the name of the ResourceHolder object that was entered.
	 */
	public String instanceType(ResourceHolder RH) {
		if(RH instanceof Player)
			return "Player";
		else if(RH instanceof Stockpile)
			return "Stockpile";
		else if(RH instanceof Market)
			return "Market";
		return null;
	}
	/**
	 * getNumGold returns the number of gold that the ResourceHolder has.
	 * @return int numGold The number of gold the ResourceHolder has.
	 */
	public int getNumGold() {
		return this.numGold;
	}
	/**
	 * getNumMolasses returns the number of molasses that the ResourceHolder has.
	 * @return int numGold The number of molasses the ResourceHolder has.
	 */
	public int getNumMolasses() {
		return this.numMolasses;
	}
	/**
	 * getNumCutlasses returns the number of cutlasses that the ResourceHolder has.
	 * @return int numGold The number of cutlasses the ResourceHolder has.
	 */
	public int getNumCutlasses() {
		return this.numCutlasses;
	}
	/**
	 * getNumGoats returns the number of goats that the ResourceHolder has.
	 * @return int numGoats The number of goats the ResourceHolder has.
	 */
	public int  getNumGoats() {
		return this.numGoats;
	}
	/**
	 * getNumWood returns the number of wood that the ResourceHolder has.
	 * @return int numWood The number of wood the ResourceHolder has.
	 */
	public int getNumWood() {
		return this.numWood;
	}
}
