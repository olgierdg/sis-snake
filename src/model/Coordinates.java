package model;

public class Coordinates {

	private int x;
	private int y;
	
	public Coordinates(int x, int y){
		this.setXPos(x);
		this.setYPos(y);
	}

	public int getXPos() {
		return x;
	}

	public void setXPos(int x) {
		this.x = x;
	}

	public int getYPos() {
		return y;
	}

	public void setYPos(int y) {
		this.y = y;
	}
}
