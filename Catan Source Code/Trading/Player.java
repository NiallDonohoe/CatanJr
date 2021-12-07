package Trading;
import java.util.ArrayList;
import Board.Board;
import Board.DevelopedLocation;

public class Player extends ResourceHolder{
	public int numUsedCoco;	// number of used coco cards
    public ArrayList<DevelopedLocation> DevelopedPlayerLocations = new ArrayList<DevelopedLocation>();
    private Board board = Board.getInstance();
    private colour playerColour;

	public enum colour{
		Red,
		Orange,
		Blue,
		White
	};
	
	public Player(colour playerColour) {
		super(0);
		this.playerColour = playerColour;
	}
	
	public void printDevelopedLocations() {
		System.out.println("\n"+this.playerColour+" player developed locations:");
		for(int i=0;i<DevelopedPlayerLocations.size();i++) {
//			int[] temp = DevelopedPlayerLocations.get(i).getLocation();
			System.out.println(DevelopedPlayerLocations.get(i).toString());
		}
	}
	public void BuildOptions() {
		System.out.print(this.playerColour+" can build on spaces:");
		for(int i=0;i<DevelopedPlayerLocations.size();i++) {
			int[] temp = DevelopedPlayerLocations.get(i).getLocation();
			System.out.println("\nx="+temp[0]+", y="+temp[1]);
//			lairOrShip los = DevelopedPlayerLocations.get(i).LairOrShip();
			int x1=temp[0]-1, y1=temp[1];
			int x2=temp[0]+1, y2=temp[1];
			int x3=temp[0], y3=temp[1]-1;
			int x4=temp[0], y4=temp[1]+1;
			if(board.PositionAvailable(x1,y1)!=-1)
				System.out.println("x="+x1+". y="+y1);
			if(board.PositionAvailable(x1,y1)!=-1)
				System.out.println("x="+x2+". y="+y2);
			if(board.PositionAvailable(x3, y3)!=-1)
				System.out.println("x="+x3+". y="+y3);
			if(board.PositionAvailable(x4, y4)!=-1)
				System.out.println("x="+x4+". y="+y4);
		}
	}
	public colour getColour() {
		return this.playerColour;
	}
	public void printResources() {
		System.out.println("\n"+playerColour+" player");
		super.printResources();;
	}

	public void stockpileTrade(Stockpile RH, ResourceType offeredRes, ResourceType requestedRes) {
		this.printTrade(RH);
		// Trade if the trade is possible.
		if(this.tradePossible(RH, offeredRes, 2, requestedRes, 1)) {
			// Give the offered resources to the stockpile.
			RH.moveResource(offeredRes,2,this);
			// Take the requested resources from the stockpile.
			this.moveResource(requestedRes,1,RH);
		}
		this.printTrade(RH);
	}
	public void marketTrade(Market RH, ResourceType offeredRes, ResourceType requestedRes) {
//		this.printTrade(CH);
		if(this.tradePossible(RH, offeredRes, 1, requestedRes, 1)) {
			// Gives offered resource to market 
			RH.moveResource(offeredRes,1,this);
			// Takes the requested resource from the market.
			this.moveResource(requestedRes,1,RH);
		}
//		this.printTrade(CH);
		RH.checkRefreshMarket();
	}
	// See if the trading player and the player, market or stockpile specified have the resources specified
	public boolean tradePossible(ResourceHolder RH, ResourceType offeredRes, int numOfferedRes, ResourceType requestedRes, int numRequestedRes) {
		if(this.resourcesAvailable(offeredRes,numOfferedRes)&&RH.resourcesAvailable(requestedRes,numRequestedRes))
			return true;
		else if(!this.resourcesAvailable(offeredRes,numOfferedRes)) {
			System.out.println("\n"+this.playerColour+" player does not have the offered resources for trade.");
			return false;
		}
//		else if(!BCH.ResourcesAvailable(requestedRes,numRequestedRes)) {
		else {
			System.out.println(this.instanceType(RH)+" does not have have the requested resources for trade.");
			return false;
		}
	}

	
	

}
