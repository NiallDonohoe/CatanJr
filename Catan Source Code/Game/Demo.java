package Game;
import Board.Board;
import Trading.Player;

import java.io.*;

public class Demo {

	public static void main(String[] args) {
		
		System.out.println("Game has Started!");
		
		Board board = Board.getInstance();
		board.declareIslands();
		
		Player demoPlayer = new Player(Trading.Player.colour.Blue);
		
		demoPlayer.printResources();
		demoPlayer.buyCocoCard();
		demoPlayer.printResources();
	}
}