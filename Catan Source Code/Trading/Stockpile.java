package Trading;

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
}

