package model;


public class SnakePiece {

	protected Coordinates coords;
	
	public SnakePiece(int x, int y){
		coords = new Coordinates(x, y);
	}
	
	public SnakePiece(SnakePiece s){
		this.coords.setXPos(s.getXPos());
		this.coords.setYPos(s.getYPos());
	}
	
	public int getXPos(){
		return coords.getXPos();
	}
	
	public int getYPos(){
		return coords.getYPos();
	}
	
	public void setXPos(int x){
		this.coords.setXPos(x);
	}
	
	public void setYPos(int y){
		this.coords.setYPos(y);
	}
}
