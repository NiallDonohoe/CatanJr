package UnitTests;

import static org.junit.Assert.assertNotNull;

import org.junit.After;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import Game.Dice;

public class GameTests {
	
private static Dice testDice;
	
	@BeforeAll
	static void BeforeAll() {
		System.out.println("Running Game Test Cases...");
	}
	
	@After
	public void tearDown() throws Exception{
		Dice.getInstance().destroyMe();
	}

	@BeforeEach
	public void setUp() throws Exception{
		testDice = Game.Dice.getInstance();
	}
	
	@Test
	public void rollReturnsAValue() {
		assertNotNull("Dice Rolls should return a number:", testDice.rollDice());
	}
}
