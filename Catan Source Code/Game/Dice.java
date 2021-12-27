package Game;
import java.util.Random;

/**
 * Class for Dice in a game of CatanJr
 * 
 * @author  Niall Donohoe & Shea O'Sullivan
 * @version 1.0
 */

public class Dice {
	
	//===========================================================
	// Class Variables
	//===========================================================
	
	private Random dice;
	static Dice instance = null;
	
	//===========================================================
	// Constructor and Singleton setup
	//===========================================================
		
	private Dice() {
	this.dice = new Random();
	} 
    
    // There should only be one instance of Dice
    static public Dice getInstance() {
		if(instance == null) {
			instance = new Dice();
		}
		return instance;
	}
	
	//===========================================================
	// Other Methods
	//===========================================================
	
	public int roll(){
		int result = (dice.nextInt(6)+1);
		System.out.println("Player Rolled a "+ result);
	    return result;
	}
	
	//===========================================================
    // Singleton destroyer for unit testing ONLY
    //===========================================================
    
    public void destroyMe() {
        instance = null;
    }
}
