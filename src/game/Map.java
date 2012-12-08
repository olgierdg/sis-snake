package game;

import java.util.ListIterator;
import java.util.Vector;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.Log;

public class Map {

	private int width;
	private int height;
	
	private Bitmap wallBitmap;
	private Bitmap orangePortalBitmap;
	private Bitmap bluePortalBitmap;
	
	Vector<Coordinates> level1;
	private Rect sourceRect;
	
	public Map(Bitmap wallBitmap, 
    		Bitmap orangePortalBitmap, 
    		Bitmap bluePortalBitmap){
		this.wallBitmap = wallBitmap;
		this.bluePortalBitmap = bluePortalBitmap;
		this.orangePortalBitmap = orangePortalBitmap;
		
		sourceRect = new Rect(0, 0, 20, 20);
		level1 = new Vector<Coordinates>();
	}
	
	public Vector<Coordinates> getLevel(){
		
		return level1;
	}
	
	public void generateLevel(){
		generateWalls();
	}
	
	public void generateWalls(){
		
		int pWidth = width / 20;
		Log.d("level1","siema generateLevel pWidth : "+pWidth);
		int pHeight = height / 20;
		Log.d("level1","siema generateLevel pHeight : "+pHeight);
		for(int i = 2; i<pHeight-1; i++){
			level1.add(new Coordinates(0,20*i));
			level1.add(new Coordinates((pWidth-1)*20,20*i));
		}
		for(int i = 0; i<pWidth; i++){
			level1.add(new Coordinates(20*i,40));
			level1.add(new Coordinates(20*i, (pHeight-1)*20));
		}
	}
	
	public void showLevel(){
		Log.d("level1","siema showLevel level1");
		ListIterator<Coordinates> it = level1.listIterator();
		while(it.hasNext()){
			Coordinates c = it.next();
			Log.d("level1","X: "+c.getXPos()+", Y: "+c.getYPos());
		}
	}
	
	/**
	 * Metoda rysuje snejka na canvasie
	 * 
	 * @param canvas canvas na ktorym metoda rysuje snejka
	 */
	public void draw(Canvas canvas) {
		ListIterator<Coordinates> it = level1.listIterator();
		while(it.hasNext()){
			Coordinates c = it.next();
			Rect destRect = new Rect(c.getXPos(), c.getYPos(), c.getXPos() + 20, c.getYPos() + 20);
			canvas.drawBitmap(wallBitmap, sourceRect, destRect, null);
		
		}
		//boundingRect = destRect;
	}

	public void setWidth(int width){
		this.width = width;
	}
	
	public void setHeight(int height){
		this.height = height;
	}
}
