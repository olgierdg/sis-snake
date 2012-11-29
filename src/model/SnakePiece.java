package model;

public class SnakePiece {

	protected int xPos;
	protected int yPos;
	
	public SnakePiece(int x, int y){
		this.xPos = x;
		this.yPos = y;
	}
	
	public SnakePiece(SnakePiece s){
		this.xPos = s.xPos;
		this.yPos = s.yPos;
	}
	
	public int getXPos(){
		return xPos;
	}
	
	public int getYPos(){
		return yPos;
	}
}
