package UnitTests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.After;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import Board.Board;

public class BoardTests {
	
	@BeforeAll
	static void BeforeAll() {
		System.out.println("Running Board Test Cases...");
		
	}

	@BeforeEach
	public void setUp() throws Exception{
		Board.getInstance().declareIslands();
	}
	
	@After
	public void tearDown() throws Exception{
		Board.getInstance().destroyMe();
	}

	@Test
	public void weHave13Islands() {
		assertEquals("The number of islands should be:", 13, Board.getInstance().getIslands().size());
	}
	
	@Test
	public void weHave72AvailableLocations() {
		assertEquals("We should have 72 available positions at the start:", 72, Board.availableLocations.size());
	}

	@Test
	public void weHave0DevelopedLocations() {
		assertEquals("We should have 0 Developed Locations at the start:", 0, Board.developedLocations.size());
	}
	
	@Test
	public void weHaveACocoDeck() {
		assertNotNull("We should have 0 Developed Locations at the start:", Board.cocoDeck);
	}
	
	
}