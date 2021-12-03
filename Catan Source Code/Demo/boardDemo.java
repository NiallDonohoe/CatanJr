package demo;
import Trading.ResourceHolder.ResourceType;
import board.Island;
import board.Board;
import board.Location;
import java.io.*;

import Trading.Market;
import Trading.Player;
import Trading.Stockpile;
public class boardDemo {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		Board board = Board.getInstance();	
		// wood1
		board.addIsland(new int[]{9,2}, Board.corner.Regular,Board.resource.wood,1);
		// goat2
		board.addIsland(new int[]{ 13,2}, Board.corner.Regular,Board.resource.goat,2);
		// should be only 10 islands from the declaration of these duplicates
		
		
		// Declare all islands
//		Island sword4 = new Island(new int[]{ 5,2}, Board.corner.SW,Board.resource.cutlass,4);
	    Island wood1 = new Island(new int[]{9,2}, Board.corner.Regular,Board.resource.wood,1);
//	    Island goat2 = new Island(new int[]{ 13,2}, Board.corner.Regular,Board.resource.goat,2);
//	    Island barrel4 = new Island(new int[]{ 17,2}, Board.corner.SE,Board.resource.molasses,4);

//	    Island wood3 = new Island(new int[]{ 3,4}, Board.corner.Regular,Board.resource.wood,3);
//	    Island gold5 = new Island(new int[]{ 7,4}, Board.corner.Regular,Board.resource.gold,5);
//	    Island ghost = new Island(new int[]{ 11,4}, Board.corner.Regular,Board.resource.none,6);
//	    Island gold3 = new Island(new int[]{ 15,4}, Board.corner.Regular,Board.resource.gold,3);
//	    Island goat5 = new Island(new int[]{ 19,4}, Board.corner.Regular,Board.resource.goat,5);
//
//	    Island sword1 = new Island(new int[]{ 13,2}, Board.corner.NW,Board.resource.cutlass,1);
//		Island wood2 = new Island(new int[]{ 9,6}, Board.corner.Regular,Board.resource.wood,2);
//	    Island goat1 = new Island(new int[]{ 13,6}, Board.corner.Regular,Board.resource.goat,1);
//	    Island barrel2 = new Island(new int[]{ 13,2}, Board.corner.NE,Board.resource.molasses,2);
		
		
		
	    
//		wood1.printEach();
//		System.out.println(wood1.containsPos(7, 1));
		Player Player2 = new Player(Player.Colour.Blue);
		board.printEach();
		
		System.out.println(board.PositionAvailable(11,3));
		board.developPosition(11,3,Player2);
		System.out.println(board.PositionAvailable(11,3));
		System.out.println(board.PositionDeveloped(11,3));

//		wood1.developPosition(7, 3, Player1,board);
		Player2.printDevelopedLocations();
//		wood1.playersWithDevelopedLairs();
//		goat2.playersWithDevelopedLairs();
//		wood1.printEach();
//		wood1.playersWithDevelopedLairs();
		
//		board.printEach();
		
		
		Stockpile stockpile = Stockpile.getInstance();
		Market market = Market.getInstance();
		
		board.developedLairsEachIsland();
	
//		stockpile.decrementWood();
//		stockpile.getWood();
//		Stockpile stockpile1 = Stockpile.getInstance();
//		stockpile1.decrementWood();
//		stockpile1.getWood();
		
		
//		Player1.printResources();
//		stockpile.printResources();
		// Demonstrate stockpile trade
//		Player1.StockpileTrade(stockpile, ResourceType.wood, ResourceType.cutlasses);
				
		// Demonstrate market refresh capabilities.
//		Player Player1 = new Player(Player.Colour.Red);
//		Player1.printResources();
//		Player1.addResource(ResourceType.wood, 5, stockpile);
//		Player1.MarketTrade(market, ResourceType.wood, ResourceType.cutlasses);
//		Player1.MarketTrade(market, ResourceType.wood, ResourceType.gold);
//		Player1.MarketTrade(market, ResourceType.wood, ResourceType.molasses);
//		Player1.MarketTrade(market, ResourceType.wood, ResourceType.wood);
//		Player1.MarketTrade(market, ResourceType.wood, ResourceType.goats);
//		market.printResources();
	}

}
