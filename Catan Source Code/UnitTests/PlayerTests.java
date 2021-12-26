package UnitTests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.After;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import Board.Board;
import Trading.BluePlayer;
import Trading.Player;

class PlayerTests {
	
	private static Player testPlayer;
	
	@BeforeAll
	static void BeforeAll() {
		System.out.println("Running Player Test Cases...");
		
	}

	@BeforeEach
	public void setUp() throws Exception{
		testPlayer = BluePlayer.getInstance();
	}
	
	@After
	public void tearDown() throws Exception{
		Board.getInstance().destroyMe();
	}
	

	@Test
	public void player0UsedCoco() {
		assertEquals("The number used coco at the start should be:", 0, testPlayer.numUsedCoco);
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
