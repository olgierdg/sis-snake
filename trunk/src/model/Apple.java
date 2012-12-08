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

	protected int xPos;
	protected int yPos;
	protected Bitmap bitmap;
	protected boolean isTouched = false;

	public Apple(Bitmap bitmap, int xPos, int yPos) {
		this.bitmap = bitmap;
		this.xPos = xPos;
		this.yPos = yPos;
	}

	public void draw(Canvas canvas) {
		canvas.drawBitmap(bitmap, xPos, yPos, null);
	}

	public void setIsTouched(boolean b) {
		isTouched = b;
	}
	

	public boolean getIsTouched() {
		return isTouched;
	}
	
	public int getXPos(){
		return xPos;
	}
	
	public void setXPos(int x){
		xPos = x;
	}
	
	public int getYPos(){
		return xPos;
	}
	
	public void setYPos(int y){
		yPos = y;
	}
}