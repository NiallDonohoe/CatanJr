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
		if( this.numGold==0) {
			
		}	
		else if(this.numMolasses==5) {
			
		}	
		else if(this.numCutlasses==5) {
			
		}	
		else if(this.numGoats==5) {
			
		}
		else if(this.numWood==5) {
			
		}	
	}

    //===========================================================
    // Singleton destroyer for unit testing ONLY
    //===========================================================
    
    public void destroyMe() {
        instance = null;
    }
}
