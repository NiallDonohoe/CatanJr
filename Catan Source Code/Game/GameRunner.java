package Game;

import java.util.ArrayList;
import java.util.HashSet;

import Board.Board;
import Board.Island;
import Trading.Player;

public class GameRunner {
	
	private static int numPlayers;
	public static HashSet<Player> playersSet = new HashSet<Player>();
	public static ArrayList<Player> players = new ArrayList<Player>();
	public static int Turn = 1;
	
	public static void setNumPlayers(int numSelected) {
		numPlayers = numSelected;
	}
	
	public static int getNumPlayers() {
		return numPlayers;
	}
	public static void startGame() {
		Board board = Board.getInstance();
		board.declareIslands();
		Dice dice = Dice.getInstance();
		CocoCards.CocoDeck cocoDeck = CocoCards.CocoDeck.getInstance();
		Trading.Stockpile stockpile = Trading.Stockpile.getInstance();
		Trading.Market market = Trading.Market.getInstance();
	}
	// Implements Player Turns
	public static void nextPlayer() {
		if(GameRunner.Turn == 1)
			GameRunner.Turn = 2;
		else if(GameRunner.Turn == 2)
			GameRunner.Turn = 3;
		else if(GameRunner.Turn == 3 && players.size() == 3)
			GameRunner.Turn = 1;
		else if (GameRunner.Turn == 3 && players.size() == 4)
			GameRunner.Turn = 4;
		else if (GameRunner.Turn == 4)
			GameRunner.Turn = 1;
	}
	// Gets the player whose turn it currently is.
	public static Player getCurrentPlayer() {
		return players.get(Turn-1);
	}
	public static int getPlayerTurnNumber() {
		return Turn;
	}
	public static Player getPlayer(int i) {
		return players.get(i);
	}
	
	public static void addPlayer(Player p) {
		if(playersSet.add(p)) {
			players.add(p);
		}
		else {
			System.out.println("That colour is already taken");
		}
	}
	
	public static boolean checkForAWinner() {
		for(Player p: players) {
			if(p.getUnbuiltLairs()==0) {
				System.out.println("We have a Winner!!! "+ p.getColour() +" has won.");
				return true;
			}
		}
		return false;
		
	}
	public static String getGameWinner() {
		for(Player p: players) {
			if(p.getUnbuiltLairs()==0) {
				return ""+ p.getColour()+"";
			}
		}
		return "No player has won the game.";
	}
	
}
