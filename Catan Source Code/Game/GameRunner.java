package Game;

import java.util.ArrayList;
import java.util.HashSet;

import Board.Island;
import Trading.Player;

public class GameRunner {
	
	private static int numPlayers;
	public static HashSet<Player> players = new HashSet<Player>();
	
	public static void setNumPlayers(int numSelected) {
		numPlayers = numSelected;
	}
	
	public static int getNumPlayers() {
		return numPlayers;
	}	
	
}
