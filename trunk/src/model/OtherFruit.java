package model;

import android.graphics.Bitmap;

public class OtherFruit extends Fruit {
	public int bonus;
	
	public OtherFruit(Bitmap bitmap, int xPos, int yPos, int bonus) {
		super(bitmap, xPos, yPos);
		this.bonus = bonus;
	}
}
