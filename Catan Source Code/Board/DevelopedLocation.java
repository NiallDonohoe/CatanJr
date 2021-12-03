package board;

import Trading.Player;

public class DevelopedLocation extends Location {
	public Player player;
	
	public DevelopedLocation(int x, int y, DevelopedLocation.lairOrShip los, Player player ) {
		super(x,y,los);
		this.player = player;
	}
	public Player getPlayer() {
		return this.player;
	}
}
