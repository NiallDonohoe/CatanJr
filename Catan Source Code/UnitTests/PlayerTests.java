package UnitTests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.After;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import Board.Board;
import Trading.BluePlayer;
import Trading.Player;
import Trading.Player.colour;
import Trading.ResourceHolder.ResourceType;
import Trading.Stockpile;

class PlayerTests {
	
	private static Player testPlayer;
	
	@BeforeAll
	static void BeforeAll() {
		System.out.println("Running Player Test Cases...");
		BluePlayer.getInstance().destroyMe();
		Board.getInstance().destroyMe();
		
	}

	@BeforeEach
	public void setUp() throws Exception{
		testPlayer = BluePlayer.getInstance();
		Board.getInstance();
		Board.getInstance().declareIslands();
	}
	
	@After
	public void tearDown() throws Exception{
		Board.getInstance().destroyMe();
	}
	
	@AfterEach
	public void afterEach() {
		Board.getInstance().destroyMe();
		BluePlayer.getInstance().destroyMe();
	}
	

	@Test
	public void player0UsedCoco() {
		assertEquals("The number used coco at the start should be:", 0, testPlayer.numUsedCoco);
	}
	
	@Test
	public void playerStartsWith0DevelopedLocations() {
		assertEquals("The number of Developed Locations at the start should be:", 0, testPlayer.DevelopedPlayerLocations.size());
	}
	
	@Test
	public void playerHasColour() {
		assertNotNull("The player should have a Colour",  testPlayer.getColour());
	}
	
	@Test
	public void playerCannotDevelopWithNotEnoughResources() {
		BluePlayer.getInstance();
		assertTrue("Player should not be able to build with not enough resources",
		Board.getInstance().buyLairOrShip(7, 3, BluePlayer.getInstance())==false);
	}
	
	@Test
	public void playerCanDevelopWIthEnoughResources() {
		Board.getInstance().printDevelopedLocations();
		BluePlayer.getInstance();
		BluePlayer.getInstance().moveResource(ResourceType.goat, 1, Stockpile.getInstance());
		BluePlayer.getInstance().moveResource(ResourceType.cutlass, 1, Stockpile.getInstance());
	
		Board.getInstance().buyLairOrShip(7, 3, BluePlayer.getInstance());
		
		int i0 = Board.getInstance().positionDeveloped(7, 3);

		assertTrue("Player should be able to build with enough resources",
		Board.getInstance().getDevelopedLocations().get(i0).getPlayer().getColour()==colour.Blue);	
	}
	@Test
	public void playerCanOnlyDevelop8ShipsLocations() {
		BluePlayer.getInstance().developStartingPositions();
		// Start with 2 ship locations
		Board.getInstance().developLocation(8, 1, testPlayer); // third ship location
		System.out.println("Num unbuilt ships:"+testPlayer.getUnbuiltShips());
		Board.getInstance().developLocation(7, 3, testPlayer);
		
		Board.getInstance().developLocation(6, 3, testPlayer); // fourth ship location
		System.out.println("Num unbuilt ships:"+testPlayer.getUnbuiltShips());
		
		Board.getInstance().developLocation(8, 3, testPlayer); // fifth ship location
		System.out.println("Num unbuilt ships:"+testPlayer.getUnbuiltShips());
		
		Board.getInstance().developLocation(20, 5, testPlayer); // sixth ship location
		System.out.println("Num unbuilt ships:"+testPlayer.getUnbuiltShips());
			
		Board.getInstance().developLocation(17, 5, testPlayer); 
		Board.getInstance().developLocation(16, 5, testPlayer);	// seventh ship location		
		System.out.println("Num unbuilt ships:"+testPlayer.getUnbuiltShips());
		
		
		Board.getInstance().developLocation(15, 5, testPlayer);
		Board.getInstance().developLocation(14, 5, testPlayer);	// eight ship location
		System.out.println("Num unbuilt ships:"+testPlayer.getUnbuiltShips());

		assertTrue("Number of unbuilt ships should be 0: ",testPlayer.getUnbuiltShips()==0); 
				
		assertTrue("Player not be able to build when the number of unbuilt ships is 0: ",
				Board.getInstance().developLocation(17, 3, testPlayer)==false);
	}
}
