package model;

import android.graphics.Bitmap;
import android.graphics.Canvas;

/**
 * Klasa jabluszka, how sweet. Nic sie tu nie dzieje, jak w moim zyciu osobistym.
 * 
 * @author Olo
 *
 */
public class Apple {

	private Coordinates coords;
	protected Bitmap bitmap;
	protected boolean isTouched = false;

	public Apple(Bitmap bitmap, int xPos, int yPos) {
		this.bitmap = bitmap;
		coords = new Coordinates(xPos, yPos);
	}

	public void draw(Canvas canvas) {
		canvas.drawBitmap(bitmap, coords.getXPos(), coords.getYPos(), null);
	}

	public void setIsTouched(boolean b) {
		isTouched = b;
	}
	
	public boolean getIsTouched() {
		return isTouched;
	}
	
	public int getXPos(){
		return coords.getXPos();
	}
	
	public void setXPos(int x){
		coords.setXPos(x);
	}
	
	public int getYPos(){
		return coords.getYPos();
	}
	
	public void setYPos(int y){
		coords.setYPos(y);
	}
}