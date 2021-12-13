package UnitTests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.After;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import Board.Board;
import Trading.Market;
import Trading.Player;
import Trading.ResourceHolder.ResourceType;
import Trading.Stockpile;

class TradeTests {
	
	private static Player testPlayer;
	
	@BeforeAll
	static void BeforeAll() {
		System.out.println("Running Player Test Cases...");
		
	}

	@BeforeEach
	public void setUp() throws Exception{
		// Player should be initialized with 1 wood, 1 molasses.
//		testPlayer = new Player(Player.colour.Blue);
//		testPlayer.printResources();
//		Board.getInstance();
		// Stockpile should have 18 of each resource, after market declaration it should have 17 of each, after player declaration 16 mollasses and 16 wood
		Stockpile stockpile = Stockpile.getInstance();
		Market market = Market.getInstance();
	}
	
	@After
	//

	@Test
	public void marketHasResources() {
		assertEquals("The market should start with 17 gold.",1,
				Market.getInstance().getNumGold());
		assertEquals("The market should start with 17 mollasses.",1,
				Market.getInstance().getNumMolasses());
		assertEquals("The market should start with 17 cutlasses.",1,
				Market.getInstance().getNumCutlasses());
		assertEquals("The market should start with 17 goats.",1,
				Market.getInstance().getNumGoats());
		assertEquals("The market should start with 17 wood.",1,
				Market.getInstance().getNumWood());
	}
	// stockpile should have 17 resources after being 
	@Test
	public void stockpileHasResources() {
		assertEquals("The stockpile should start with one gold.",17,
				Stockpile.getInstance().getNumGold());
		assertEquals("The stockpile should start with one mollasses.",16,
				Stockpile.getInstance().getNumMolasses());
		assertEquals("The stockpile should start with one cutlasses.",17,
				Stockpile.getInstance().getNumCutlasses());
		assertEquals("The stockpile should start with one goats.",17,
				Stockpile.getInstance().getNumGoats());
		assertEquals("The stockpile should start with one wood.",17,
				Stockpile.getInstance().getNumWood());
	}
	@Test
	public void stockpileTrade() {
		testPlayer.moveResource(ResourceType.wood,4, Stockpile.getInstance());
		testPlayer.trade(Stockpile.getInstance(), ResourceType.wood, ResourceType.cutlass);
		assertEquals("The stockpile should start with one cutlasses.",17,
				Stockpile.getInstance().getNumCutlasses());
	}
	@Test
	public void player0DevelopedLocations() {
		assertEquals("The number of Developed Locations at the start should be:", 0, testPlayer.DevelopedPlayerLocations.size());
	}
	
	@Test
	public void playerHasColour() {
		assertNotNull("The player should have a Colour",  testPlayer.getColour());
	}
}