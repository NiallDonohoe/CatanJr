package UnitTests;
import static org.junit.Assert.*;
import org.junit.After;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import Game.Dice;

public class DieTests {
	
private static Dice testDice;

	@BeforeAll
	static void BeforeAll() {
		System.out.println("Running Die Test Cases...");
	}
	
	@After
	public void tearDown() throws Exception{
		Dice.getInstance().destroyMe();
		Board.Board.getInstance().destroyMe();
	}

	@BeforeEach
	public void setUp() throws Exception{
		testDice = Game.Dice.getInstance();
	}
	
	@Test
	public void rollReturnsAValue() {
		assertNotNull("Dice Rolls should return a number:", testDice.roll());
	}
	
	@Test
	public void rollReturnsAValueBetween1and6() {
		for(int i=0;i<30; i++) { // perform 30 rolls and ensure values as expected
			int result = testDice.roll();
			assertTrue(result==1||result==2||result==3||result==4||result==5||result==6);
		}
	}
}