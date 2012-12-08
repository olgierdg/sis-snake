package model;

import game.Coordinates;

public class SnakePiece {

	protected Coordinates coords;
	
	public SnakePiece(int x, int y){
		coords = new Coordinates(x, y);
	}
	
	public SnakePiece(SnakePiece s){
		this.coords.setX(s.getXPos());
		this.coords.setY(s.getYPos());
	}
	
	public int getXPos(){
		return coords.getXPos();
	}
	
	public int getYPos(){
		return coords.getYPos();
	}
	
	public void setXPos(int x){
		this.coords.setX(x);
	}
	
	public void setYPos(int y){
		this.coords.setY(y);
	}
}
