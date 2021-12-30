package Game;

import java.util.ArrayList;
import java.util.HashSet;

import Board.Board;
import Trading.Player;

public class Game {
	
	private static int numPlayers;
	private static HashSet<Player> playersSet = new HashSet<Player>();
	private static ArrayList<Player> players = new ArrayList<Player>();
	public static int Turn = 1;
	
	/**
	 * startGame instantiates all singleton instances that need to be declared for the game to start.
	 */
	public static void startGame() {
		Board.getInstance();
		Board.getInstance().declareIslands();
		Dice.getInstance();
		CocoCards.CocoDeck.getInstance();
		Trading.Stockpile.getInstance();
		Trading.Market.getInstance();
	}
    //===========================================================
  	// Getters and Setters
  	//===========================================================
	/**
	 * setNumPlayer Sets the number of players in the game should be 3 or 4.
	 * @param numSelected The number of selected players.
	 */
	public static void setNumPlayers(int numSelected) {
		if(numSelected == 3 || numSelected == 4)
			numPlayers = numSelected;
		else
			numPlayers = 0;
	}
	/**
	 * getNumPlayers Gets the number of players in the game.
	 * @return int getNumPlayers. The number of players added to the game.
	 */
	public static int getNumPlayers() {
		return numPlayers;
	}
	/**
	 * getCurrentPlayer Gets the player whose turn it currently is.
	 * @return Player. Will return whichever player is currently taking their turn.
	 */
	// Gets the player whose turn it currently is.
	public static Player getCurrentPlayer() {
		return players.get(Turn-1);
	}
	/**
	 * getPlayerTurnNumber returns the number of the player whose turn it currently is.
	 * @return int Turn.
	 */
	public static int getPlayerTurnNumber() {
		return Turn;
	}
	/**
	 * getPlayer gets a player based on their number
	 * @param i the player position in the players ArrayList.
	 * @return Player. The player whose position in players ArrayLust was specified by i.
	 */
	public static Player getPlayer(int i) {
		return players.get(i);
	}
	/**
	 * getGameWinner returns String of the player colour who has won the game.
	 * @return String.
	 */
	public static String getGameWinner() {
		for(Player p: players) {
			if(p.getUnbuiltLairs()==0) {
				return ""+ p.getColour()+"";
			}
		}
		return "No player has won the game.";
	}
	/**
	 * getPlayers return the players ArrayList.
	 * @return ArrayList<Player>
	 */
	public static ArrayList<Player> getPlayers() {
		return players;
	}
	/**
	 * getPlayersSet returns the playersSet HashSet, used only for unit testing. 
	 * @return HashSet<Player>
	 */
	public static HashSet<Player> getPlayersSet(){
		return playersSet;
	}
	
  	//===========================================================
  	// Other Methods
  	//===========================================================
	/**
	 * nextPlayer is used on end turn to switch the current player to the next player.
	 */
	public static void nextPlayer() {
		if(Game.Turn == 1)
			Game.Turn = 2;
		else if(Game.Turn == 2)
			Game.Turn = 3;
		else if(Game.Turn == 3 && players.size() == 3)
			Game.Turn = 1;
		else if (Game.Turn == 3 && players.size() == 4)
			Game.Turn = 4;
		else if (Game.Turn == 4)
			Game.Turn = 1;
		System.out.println(getCurrentPlayer().getColour()+" players turn.");
	}
	/**
	 * addPlayer is used for adding a player to the players array list.
	 * @param p The inputed Player being added who cannot be a duplicate of an already added Player.
	 */
	public static void addPlayer(Player p) {
		if(playersSet.add(p)) {
			players.add(p);
		}
		else {
			System.out.println("That colour is already taken");
		}
	}
	/**
	 * checkForAWinner checks if a player has reached 0 unbuilt lairs and won the game.
	 * @return boolean. Indicating true if a player has won and false if a player has not.
	 */
	public static boolean checkForAWinner() {
		for(Player p: players) {
			if(p.getUnbuiltLairs()==0) {
				System.out.println("We have a Winner!!! "+ p.getColour() +" has won.");
				return true;
			}
		}
		return false;
		
	}
}
