package game;

public class Coordinates {

	private int x;
	private int y;
	
	public Coordinates(int x, int y){
		this.setX(x);
		this.setY(y);
	}

	public int getXPos() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getYPos() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}
}
