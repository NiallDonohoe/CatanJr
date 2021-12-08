package UnitTests;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import CocoCards.CocoDeck;

public class CocoCardDeckTests {
	
	@BeforeAll
	static void BeforeAll() {
		System.out.println("Running Coco Card Test Cases...");
		
	}

	@BeforeEach
	public void setUp() throws Exception{
		CocoDeck.getInstance();
	}
	
	@After
	public void tearDown() throws Exception{
		CocoDeck.getInstance().destroyMe();
	}

	@Test
	public void weHave20CocoCardsInTotal() {
		assertEquals("The number of islands should be:", 20, CocoDeck.getInstance().getCocoCardDeck().size());
	}
}
