package board;


public class Location {
	public int x;
	public int y;
	public int[] location = new int[] {x,y};
	public lairOrShip los;

	public enum lairOrShip{
		lair,
		ship
	}	

	public Location(int x,int y, lairOrShip los) {
		this.x = x;
		this.y = y;
		this.location = new int[] {x,y};
		this.los = los;
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
	public lairOrShip LairOrShip() {
		return this.los;
	}
	public String toString() {
		return "x="+this.x+", y="+this.y+", "+this.los;
	}
	public boolean isEqual(int x, int y) {
		if (this.x==x && this.y==y)
			return true;
		else
			return false;
	}

}
