package Game;
import Board.Board;
import Board.Island;
import Trading.BluePlayer;
import Trading.Player;
import Trading.RedPlayer;
import Trading.ResourceHolder;
import Trading.ResourceHolder.ResourceType;
import Trading.WhitePlayer;

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
		BluePlayer BluePlayer = Trading.BluePlayer.getInstance();
		RedPlayer RedPlayer = Trading.RedPlayer.getInstance();
		WhitePlayer WhitePlayer = Trading.WhitePlayer.getInstance();
		BluePlayer.developStartingPositions();
		RedPlayer.developStartingPositions();
		WhitePlayer.developStartingPositions();
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