package Board;

public class Location {
	
	private int x;
	private int y;
	private int[] location = new int[] {x,y};
	private lairOrShip lairOrShip;

	public enum lairOrShip{
		lair,
		ship
	}	

	public Location(int x,int y, lairOrShip lairOrShip) {
		this.x = x; this.y = y;
		this.location = new int[] {x,y};
		this.lairOrShip = lairOrShip;
	}
	
	public int[] getLocation() {
		return location;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public lairOrShip getLairOrShip() {
		return this.lairOrShip;
	}
	
	public String toString() {
		return "x="+this.x+", y="+this.y+", "+this.lairOrShip;
	}
	
	public boolean isEqual(int x, int y) {
		if (this.x==x && this.y==y)
			return true;
		else
			return false;
	}
	
}
