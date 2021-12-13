package UnitTests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.After;
import org.junit.jupiter.api.AfterEach;
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
		Stockpile.getInstance();
	}
	
	@AfterEach
	public void tearDown() throws Exception{
		Stockpile.getInstance().destroyMe();
//		Market.getInstance().destroyMe();
	}

	// Stockpile should start with 18 of each resource. 
	@Test
	public void stockpileHasCorrectStartingResources() {
		assertEquals("The stockpile should start with 18 gold.",18,
				Stockpile.getInstance().getNumGold());
		assertEquals("The stockpile should start with 18 mollasses.",18,
				Stockpile.getInstance().getNumMolasses());
		assertEquals("The stockpile should start with 18 cutlasses.",18,
				Stockpile.getInstance().getNumCutlasses());
		assertEquals("The stockpile should start with 18 goats.",18,
				Stockpile.getInstance().getNumGoats());
		assertEquals("The stockpile should start with 18 wood.",18,
				Stockpile.getInstance().getNumWood());
	}
	// Market should start with 1 of each resource taken from stockpile with stockpile then having 17
	@Test
	public void marketHasCorrectStartingResources() {
		Market.getInstance();
		assertEquals("The market should start with 1 gold.",1,
				Market.getInstance().getNumGold());
		assertEquals("The market should start with 1 mollasses.",1,
				Market.getInstance().getNumMolasses());
		assertEquals("The market should start with 1 cutlasses.",1,
				Market.getInstance().getNumCutlasses());
		assertEquals("The market should start with 1 goats.",1,
				Market.getInstance().getNumGoats());
		assertEquals("The market should start with 1 wood.",1,
				Market.getInstance().getNumWood());
		// Stockpile after player declaration
		assertEquals("After market declaration the stockpile should have 17 gold.",17,
				Stockpile.getInstance().getNumGold());
		assertEquals("After market declaration the stockpile should have 17 mollasses.",17,
				Stockpile.getInstance().getNumMolasses());
		assertEquals("After market declaration the stockpile should have 17 cutlasses.",17,
				Stockpile.getInstance().getNumCutlasses());
		assertEquals("After market declaration the stockpile should have 17 goats.",17,
				Stockpile.getInstance().getNumGoats());
		assertEquals("After market declaration the stockpile should have 17 wood.",17,
				Stockpile.getInstance().getNumWood());
		Market.getInstance().destroyMe();
	}
	// Player should start with 1 wood and molasses taken from stockpile
	@Test
	public void playerHasCorrectStartingResources() {
		testPlayer = new Player(Player.colour.Blue);
		assertEquals("The player should start with 0 gold.",0,
				testPlayer.getNumGold());
		assertEquals("The player should start with 1 mollasses.",1,
				testPlayer.getNumMolasses());
		assertEquals("The player should start with 0 cutlasses.",0,
				testPlayer.getNumCutlasses());
		assertEquals("The player should start with 0 goats.",0,
				testPlayer.getNumGoats());
		assertEquals("The player should start with 1 wood.",1,
				testPlayer.getNumWood());
		
		// Stockpile after player declaration
		assertEquals("After player declaration stockpile should have 18 gold.",18,
				Stockpile.getInstance().getNumGold());
		assertEquals("After player declaration stockpile should have 17 mollasses.",17,
				Stockpile.getInstance().getNumMolasses());
		assertEquals("After player declaration stockpile should have 18 cutlasses.",18,
				Stockpile.getInstance().getNumCutlasses());
		assertEquals("After player declaration stockpile should have 18 goats.",18,
				Stockpile.getInstance().getNumGoats());
		assertEquals("After player declaration stockpile should have 17 wood.",17,
				Stockpile.getInstance().getNumWood());
	}
	@Test
	public void unsuccessfulStockpileTrade() {
		testPlayer = new Player(Player.colour.Red);
		testPlayer.trade(Stockpile.getInstance(), ResourceType.wood, ResourceType.cutlass);
		assertEquals("After failed trade request player should still have 1 wood:",1,
				testPlayer.getNumWood());
		assertEquals("After failed trade request stockpile should still have 18 cutlasses:",18,
				Stockpile.getInstance().getNumCutlasses());
	}
	public void successfulStockpileTrade() {
		testPlayer = new Player(Player.colour.Red);
		testPlayer.moveResource(ResourceType.wood, 1, Stockpile.getInstance());
		assertEquals("Before stockpile trade where wood is offered for cutlass, player should have 2 wood:",2,
				testPlayer.getNumWood());
		testPlayer.trade(Stockpile.getInstance(), ResourceType.wood, ResourceType.cutlass);
		assertEquals("After stockpile trade where wood is offered for cutlass, player should have 0 wood:",0,
				testPlayer.getNumWood());
		assertEquals("After stockpile trade where wood is offered for cutlass, player should have 1 cutlass:",0,
				testPlayer.getNumWood());
		assertEquals("After stockpile trade where wood is offered for cutlass, stockpile should have 18 wood:",18,
				Stockpile.getInstance().getNumWood());
		assertEquals("After stockpile trade where wood is offered for cutlass, stockpile should have 17 cutlasses:",17,
				Stockpile.getInstance().getNumCutlasses());
	}
	@Test
	public void succesfulMarketTrade() {
		testPlayer = new Player(Player.colour.Red);
		testPlayer.trade(Market.getInstance(), ResourceType.wood, ResourceType.gold);
		assertEquals("After market trade where wood was offered for gold, player should have 0 wood:",0,
				testPlayer.getNumWood());
		assertEquals("After market trade where wood was offered for gold, player should have 1 gold:",1,
				testPlayer.getNumGold());
		assertEquals("After market trade where wood was offered for gold, market should have 2 wood:",2,
				Market.getInstance().getNumWood());
		assertEquals("After market trade where wood was offered for gold, market should have 0 gold:",0,
				Market.getInstance().getNumGold());
		Market.getInstance().destroyMe();
	}
	@Test
	public void marketRefreshAllSameResource() {
		Market.getInstance();
		testPlayer.moveResource(ResourceType.wood,4,Stockpile.getInstance());
		testPlayer.trade(Market.getInstance(), ResourceType.wood, ResourceType.gold);
		testPlayer.trade(Market.getInstance(), ResourceType.wood, ResourceType.molasses);
		testPlayer.trade(Market.getInstance(), ResourceType.wood, ResourceType.cutlass);
		testPlayer.trade(Market.getInstance(), ResourceType.wood, ResourceType.goat);
		
		Market.getInstance().printResources();
		assertEquals("Market should refresh after getting 5 of one resource and have 1 gold.",1,
				Market.getInstance().getNumGold());
		assertEquals("Market should refresh after getting 5 of one resource and have 1 mollasses.",1,
				Market.getInstance().getNumMolasses());
		assertEquals("Market should refresh after getting 5 of one resource and have 1 cutlasses.",1,
				Market.getInstance().getNumCutlasses());
		assertEquals("Market should refresh after getting 5 of one resource and have 1 goats.",1,
				Market.getInstance().getNumGoats());
		assertEquals("Market should refresh after getting 5 of one resource and have 1 wood.",1,
				Market.getInstance().getNumWood());
		Market.getInstance().destroyMe();
	}
//	@Test
//	public void stockpileRefresh() {
//		testPlayer = new Player(Player.colour.Red);
//		testPlayer.moveResource(ResourceType.wood,17,Stockpile.getInstance());
//		Stockpile.getInstance().printResources();
//		assertEquals("Stockpile should have 18 resources after reset:",18,
//				Stockpile.getInstance().getNumWood());
//	}
}