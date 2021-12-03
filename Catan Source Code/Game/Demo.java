package Game;
import Board.Board;
import java.io.*;

public class Demo {

	public static void main(String[] args) {
		
		System.out.println("Game has Started!");
		
		Board board = Board.getInstance();
		board.declareIslands();
		board.printEach();
	}
}