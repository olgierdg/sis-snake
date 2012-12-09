package model;

public class Portal {

	private Coordinates coords;
	private int direction;
	public static final int NORTH = 1;
    public static final int SOUTH = 2;
    public static final int EAST = 3;
    public static final int WEST = 4;
    
    public Portal(int x, int y, int dir){
    	direction = dir;
    	coords = new Coordinates(x, y);
    }
    
    public int getXPos(){
    	return coords.getXPos();
    }
    
    public int getYPos(){
    	return coords.getYPos();
    }
    
    public void setXPos(int x){
    	coords.setXPos(x);
    }
    
    public void setYPos(int y){
    	coords.setYPos(y);
    }

	public int getDirection() {
		return direction;
	}

	public void setDirection(int direction) {
		this.direction = direction;
	}
}
