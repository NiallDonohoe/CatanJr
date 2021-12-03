package Board;
import Trading.Player;

public class DevelopedLocation extends Location{
	
	private Player player;
	
	public DevelopedLocation(int x, int y, DevelopedLocation.lairOrShip lairOrShip, Player player ) {
		super(x,y,lairOrShip);
		this.player = player;
	}
	
	public Player getPlayer() {
		return this.player;
	}
}