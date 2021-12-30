package UnitTests;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.After;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import Board.Board;
import Board.Island;
import Board.Location;
import Trading.BluePlayer;
import Trading.RedPlayer;

@TestMethodOrder(MethodOrderer.MethodName.class)
public class BoardTests {
	
	@BeforeAll
	static void BeforeAll() {
		System.out.println("Running Board Test Cases...");
		Board.getInstance().declareIslands();
	}
	
	@After
	public void tearDown() throws Exception{
		Board.getInstance().destroyMe();
		BluePlayer.getInstance().destroyMe();
	}
	
  	//===========================================================
  	// Testing Initial State of Board
  	//===========================================================
	
	@Test
	@Order(1)
	public void weHave13Islands() {
		assertEquals("The number of islands should be:", 13, Board.getInstance().getIslands().size());
	}
	
	@Test
	@Order(2)
	public void weHaveASpookyIsland() {
		assertNotNull("We should have 1 Spooky Island", Board.getInstance().getSpookyIsland());
	}
	
	@Test
	//@Order(3)
	public void weHaveAllAvailableLocations() {
		for(Location l: Board.getInstance().getAvailableLocations()) {
			assertTrue(Board.getInstance().positionAvailable(l.getX(),l.getY())>=0);
		}
	}

	@Test
	//@Order(4)
	public void weHave0DevelopedLocations() {
		for(Location l: Board.getInstance().getDevelopedLocations()) {
			assertFalse(Board.getInstance().positionAvailable(l.getX(),l.getY())>=0);
		}
	}
	
	@Test
	@Order(5)
	public void weHaveACocoDeck() {
		assertNotNull("We should have a CocoDeck:", Board.getInstance().getCocoDeck());
	}
	
  	//===========================================================
  	// Testing Board Coordinates
  	//===========================================================
	
	@Test
	@Order(6)
	public void checkLairCoordinates() {
		
		for(Island i: Board.getInstance().getIslands()) {
			int islandX = i.getX();
			int islandY = i.getY();
			for(int j=0; j<i.getIslandAvailableLairLocations().size(); j++) {
				int locationX = i.getIslandAvailableLairLocations().get(j).getX();
				int locationY = i.getIslandAvailableLairLocations().get(j).getY();
				assertTrue(islandX==locationX||islandX==locationX+2||islandX==locationX-2);
				assertTrue(islandY==locationY||islandY==locationY+1||islandY==locationY-1);
			}
		}	
	}
	
	@Test
	@Order(7)
	public void checkShipCoordinates() {
		
		for(Island i: Board.getInstance().getIslands()) {
			int islandX = i.getX();
			int islandY = i.getY();
			for(int j=0; j<i.getIslandAvailableShipLocations().size(); j++) {
				int locationX = i.getIslandAvailableShipLocations().get(j).getX();
				int locationY = i.getIslandAvailableShipLocations().get(j).getY();
				assertTrue(islandX==locationX||islandX==locationX+2||islandX==locationX-2||islandX==locationX+1||islandX==locationX-1);
				assertTrue(islandY==locationY||islandY==locationY+1||islandY==locationY-1);
			}
		}	
	}
	
  	//===========================================================
  	// Testing Board/Island/Player Interaction
  	//===========================================================
	
	@Test
	@Order(8)
	public void weCanDevelopAShipLocation() {
		BluePlayer bluePlayer = BluePlayer.getInstance();
		bluePlayer.developStartingPositions();
		int initialNumDeveloped = bluePlayer.getPlayerDevelopedLocations().size();
		Board.getInstance().developLocation(8, 3, bluePlayer); // this is a ship location	
		assertTrue(initialNumDeveloped < bluePlayer.getPlayerDevelopedLocations().size());
	}
	
	@Test
	@Order(9)
	public void weCanDevelopALairLocation() {
		BluePlayer bluePlayer = BluePlayer.getInstance();
		bluePlayer.developStartingPositions();
		int initialNumDeveloped = bluePlayer.getPlayerDevelopedLocations().size();
		Board.getInstance().developLocation(7, 3, bluePlayer); // this is a lair location	
		assertTrue(initialNumDeveloped < bluePlayer.getPlayerDevelopedLocations().size());
	}
	
	@Test
	@Order(10)
	public void weCantBuildOnADevelopedLocation() {
		BluePlayer bluePlayer = BluePlayer.getInstance();
		bluePlayer.developStartingPositions();
		assertFalse(Board.getInstance().canBuildOnLocation(7, 1, bluePlayer)); // already Developed
	}
	
	@Test
	@Order(11)
	public void resourcesAreGeneratedOnDieRoll() {
		BluePlayer bluePlayer = BluePlayer.getInstance();
		bluePlayer.developStartingPositions();

		// save initial number of resources
		int initialNumWood = BluePlayer.getInstance().getNumWood();
		int initialNumCutlass = BluePlayer.getInstance().getNumCutlasses();
		int initialNumGoats = BluePlayer.getInstance().getNumGoats();
		int initialNumGold = BluePlayer.getInstance().getNumGold();
		int initialNumMolasses = BluePlayer.getInstance().getNumMolasses();
		
		for(int result=1; result<5; result++) {
			Board.getInstance().handleGeneratingIslandResources(1);
			assertTrue(initialNumCutlass <= BluePlayer.getInstance().getNumCutlasses() && initialNumWood <= BluePlayer.getInstance().getNumWood()
					&& initialNumGoats <= BluePlayer.getInstance().getNumGoats() && initialNumGold <= BluePlayer.getInstance().getNumGold()
					&& initialNumMolasses <= BluePlayer.getInstance().getNumMolasses());
		}
	
	}
	
	@Test
	@Order(12)
	public void ghostCaptainStopsResourceGeneration() {
		BluePlayer bluePlayer = BluePlayer.getInstance();
		RedPlayer redPlayer = RedPlayer.getInstance();
		redPlayer.developStartingPositions();
		// Place ghost Captain on an island
		Island i = Board.getInstance().getIslands().get(0);
		i.setGhostCaptain(true, redPlayer.getColour());
		
		// save initial number of resources
		int initialNumWood = BluePlayer.getInstance().getNumWood();
		int initialNumCutlass = BluePlayer.getInstance().getNumCutlasses();
		int initialNumGoats = BluePlayer.getInstance().getNumGoats();
		int initialNumGold = BluePlayer.getInstance().getNumGold();
		int initialNumMolasses = BluePlayer.getInstance().getNumMolasses();
		
		// try to generateResources for the island
		i.generateResources();
		
		// no resources generated as ghost captain present
		assertTrue(initialNumCutlass == BluePlayer.getInstance().getNumCutlasses() && initialNumWood == BluePlayer.getInstance().getNumWood()
				&& initialNumGoats == BluePlayer.getInstance().getNumGoats() && initialNumGold == BluePlayer.getInstance().getNumGold()
				&& initialNumMolasses == BluePlayer.getInstance().getNumMolasses());

	}	
}