package UnitTests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.jupiter.api.Order;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import Board.Board;
import Trading.BluePlayer;
import Trading.OrangePlayer;
import Trading.RedPlayer;
import Trading.WhitePlayer;

@TestMethodOrder(MethodOrderer.MethodName.class)	
public class GameTests {
	
	@BeforeAll
	static void beforeAll() {
		System.out.println("Running Gameplay Test Cases.");
		Game.Game.startGame();
		Game.Game.getPlayers().clear();
		Game.Game.getPlayersSet().clear();
		BluePlayer.getInstance().destroyMe();
		RedPlayer.getInstance().destroyMe();
		OrangePlayer.getInstance().destroyMe();
		WhitePlayer.getInstance().destroyMe();
	}
	@BeforeEach
	public void beforeEach() {
		Game.Game.startGame();
		Game.Game.getPlayers().clear();
		Game.Game.getPlayersSet().clear();
		BluePlayer.getInstance().destroyMe();
		RedPlayer.getInstance().destroyMe();
		OrangePlayer.getInstance().destroyMe();
		WhitePlayer.getInstance().destroyMe();
	}	
	@AfterEach
	public void afterEach() throws Exception{
		Game.Game.getPlayers().clear();
		Game.Game.getPlayersSet().clear();
		BluePlayer.getInstance().destroyMe();
		RedPlayer.getInstance().destroyMe();
		OrangePlayer.getInstance().destroyMe();
		WhitePlayer.getInstance().destroyMe();
	}


	@Test
	@Order(1)
	public void only3Players() {
		Game.Game.setNumPlayers(2);
		assertEquals("The number of players should be 0 if the number selected is not 3 or 4",0,Game.Game.getNumPlayers());
		Game.Game.setNumPlayers(5);
		assertEquals("The number of players should be 0 if the number selected is not 3 or 4",0,Game.Game.getNumPlayers());
	}
	@Test
	@Order(2)
	public void correctNumberOfPlayers() {
		Game.Game.setNumPlayers(3);
		assertEquals("The number of players should be 0 if the number selected is not 3 or 4",3,Game.Game.getNumPlayers());
		Game.Game.setNumPlayers(4);
		assertEquals("The number of players should be 0 if the number selected is not 3 or 4",4,Game.Game.getNumPlayers());
	}
	@Test
	@Order(3)
	public void addPlayerToGameClass() {
		Game.Game.addPlayer(BluePlayer.getInstance());
		assertTrue(Game.Game.getPlayer(0) == BluePlayer.getInstance());
	}
	@Test
	@Order(4)
	public void noDuplicatePlayersInGameClass() {
		Game.Game.getPlayers().clear();
		Game.Game.getPlayersSet().clear();
		Game.Game.addPlayer(BluePlayer.getInstance());
		Game.Game.addPlayer(BluePlayer.getInstance());
		assertTrue(Game.Game.getPlayer(0) == BluePlayer.getInstance());
		assertEquals("The number of players in the players arraylist should not change if the same player is added.",1,Game.Game.getPlayers().size());
		
	}
	@Test
	@Order(5)
	public void nextTurnWorksCorrectlyWith4Players() {
		Game.Game.setNumPlayers(4);
		Game.Game.addPlayer(BluePlayer.getInstance());
		Game.Game.addPlayer(RedPlayer.getInstance());
		Game.Game.addPlayer(OrangePlayer.getInstance());
		Game.Game.addPlayer(WhitePlayer.getInstance());
		
		assertTrue("The first player should have been the first player added, ie the first to pick their colour.",
				Game.Game.getCurrentPlayer() == BluePlayer.getInstance());
		Game.Game.nextPlayer();
		assertTrue("The second player should have been the second player added.",
				Game.Game.getCurrentPlayer() == RedPlayer.getInstance());
		Game.Game.nextPlayer();
		assertTrue("The third player should have been the third player added.",
				Game.Game.getCurrentPlayer() == OrangePlayer.getInstance());
		Game.Game.nextPlayer();
		assertTrue("The fourth player should have been the fourth player added.",
				Game.Game.getCurrentPlayer() == WhitePlayer.getInstance());
		Game.Game.nextPlayer();
		assertTrue("After the fourth player finishes their turn it should return to first player.",
				Game.Game.getCurrentPlayer() == BluePlayer.getInstance());
		
		
	}
	@Test
	@Order(6)
	public void nextTurnWorksCorrectlyWith3Players() {
		Game.Game.setNumPlayers(3);
		Game.Game.addPlayer(BluePlayer.getInstance());
		Game.Game.addPlayer(RedPlayer.getInstance());
		Game.Game.addPlayer(OrangePlayer.getInstance());
	
		assertTrue("The first player should have been the first player added, ie the first to pick their colour.",
				Game.Game.getCurrentPlayer() == BluePlayer.getInstance());
		Game.Game.nextPlayer();
		assertTrue("The second player should have been the second player added.",
				Game.Game.getCurrentPlayer() == RedPlayer.getInstance());
		Game.Game.nextPlayer();
		assertTrue("The third player should have been the third player added.",
				Game.Game.getCurrentPlayer() == OrangePlayer.getInstance());
		Game.Game.nextPlayer();
		assertTrue("After the third player finishes their turn it should return to first player.",
				Game.Game.getCurrentPlayer() == BluePlayer.getInstance());		
	}
	@Test
	@Order(7)
	public void winGameWhenLairsReach0() {
		Game.Game.setNumPlayers(3);
		Game.Game.addPlayer(BluePlayer.getInstance());
		Game.Game.addPlayer(RedPlayer.getInstance());
		Game.Game.addPlayer(OrangePlayer.getInstance());

		Board.getInstance().developLocation(7, 3, BluePlayer.getInstance());
		Board.getInstance().developLocation(8, 3, BluePlayer.getInstance());
		Board.getInstance().developLocation(9, 3, BluePlayer.getInstance());
		Board.getInstance().developLocation(10, 3, BluePlayer.getInstance());
		Board.getInstance().developLocation(11, 3, BluePlayer.getInstance());
		Board.getInstance().developLocation(12, 3, BluePlayer.getInstance());
		Board.getInstance().developLocation(13, 3, BluePlayer.getInstance());
		Board.getInstance().developLocation(14, 3, BluePlayer.getInstance());
		Board.getInstance().developLocation(15, 3, BluePlayer.getInstance());
		Board.getInstance().developLocation(16, 3, BluePlayer.getInstance());
		Board.getInstance().developLocation(17, 3, BluePlayer.getInstance());
		Board.getInstance().developLocation(17, 4, BluePlayer.getInstance());
		Board.getInstance().developLocation(17, 5, BluePlayer.getInstance());
		System.out.println("Number of lairs left:"+BluePlayer.getInstance().getColour().toString()+","+Game.Game.getGameWinner());

		assertTrue("The check for winner should return true when blue player has all lairs built.",
				Game.Game.checkForAWinner() == true);		
		assertTrue("The blue player should be the winner",
				Game.Game.getGameWinner().equals(BluePlayer.getInstance().getColour().toString()));
	}
}
