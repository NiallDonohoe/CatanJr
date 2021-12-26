package UnitTests;

import static org.junit.Assert.assertNotNull;

import org.junit.After;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import Board.Board;
import CocoCards.CocoDeck;
import Game.Dice;
import Trading.BluePlayer;
import Trading.RedPlayer;
import Trading.WhitePlayer;

public class GameTests {
	
private static Dice testDice;
private static Board testBoard;
private static CocoDeck testCocoDeck; 
protected BluePlayer bluePlayer;
private WhitePlayer whitePlayer;
private RedPlayer redPlayer;

	
	@BeforeAll
	static void BeforeAll() {
		System.out.println("Running Full Gameplay Test Cases, this simulates a full game.");
		Board.getInstance().declareIslands();
		testBoard = Board.getInstance();
		testCocoDeck = CocoDeck.getInstance();
		testDice = Game.Dice.getInstance();
	}
	
	@After
	public void tearDown() throws Exception{
		Board.getInstance().destroyMe();
		CocoDeck.getInstance().destroyMe();
		Dice.getInstance().destroyMe();
		bluePlayer.destroyMe();
		whitePlayer.destroyMe();
		redPlayer.destroyMe();
		
	}
	
//	@Test
//	public void bluePlayerTurn() {
//		
//	}
}
