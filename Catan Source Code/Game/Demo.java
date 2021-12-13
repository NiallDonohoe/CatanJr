package Game;
import Board.Board;
import Board.Island;
import Trading.Player;
import Trading.ResourceHolder;
import Trading.ResourceHolder.ResourceType;

import java.io.*;
import java.util.ArrayList;

public class Demo {

	public static void main(String[] args) {
		
		ArrayList<Player> players = new ArrayList<Player>();
		
		System.out.println("Game has Started!");
		
		// Make Board
		Board board = Board.getInstance();
		board.declareIslands();
		
		// Make Dice
		Dice dice = Dice.getInstance();
		
		// Make CocoDeck
		CocoCards.CocoDeck cocoDeck = CocoCards.CocoDeck.getInstance();
		
		// Make Marketplace
		Trading.Market market = Trading.Market.getInstance();
		
		// Make Stockpile
		Trading.Stockpile stockpile = Trading.Stockpile.getInstance();
		
		// Make players
		Player BluePlayer = new Player(Trading.Player.colour.Blue);
		Player RedPlayer = new Player(Trading.Player.colour.Red);
		Player WhitePlayer = new Player(Trading.Player.colour.White);
		
		players.add(BluePlayer); players.add(RedPlayer); players.add(WhitePlayer);
		
		// players get 2 lairs and 1 ship - still to implement
		
		// test a trade
		BluePlayer.printResources();
		RedPlayer.printResources();
		WhitePlayer.printResources();
		stockpile.printResources();
		market.printResources();
		
		WhitePlayer.trade(market, Trading.ResourceHolder.ResourceType.molasses, Trading.ResourceHolder.ResourceType.wood);
		
		BluePlayer.printResources();
		RedPlayer.printResources();
		WhitePlayer.printResources();
		stockpile.printResources();
		market.printResources();
		
		
		// roll a die
		board.actOnDieRoll(dice.roll(),BluePlayer);
		
		BluePlayer.printResources();
		RedPlayer.printResources();
		WhitePlayer.printResources();
		stockpile.printResources();
		market.printResources();
	}
}