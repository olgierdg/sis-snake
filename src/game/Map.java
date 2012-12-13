package game;

import java.util.ListIterator;
import java.util.Vector;

import model.Coordinates;
import model.Portal;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

public class Map {

	private int width;
	private int height;
	
	private Bitmap wallBitmap;
	private Bitmap orangeWestPortalBitmap;
	private Bitmap orangeEastPortalBitmap;
	private Bitmap blueWestPortalBitmap;
	private Bitmap blueEastPortalBitmap;
	
	Vector<Coordinates> level;
	private Portal bluePortal;
	private Portal orangePortal;
	private Rect sourceRect;
	private String gameMode;
	
	public Map(Bitmap wallBitmap, 
    		Bitmap orangeWestPortalBitmap, 
    		Bitmap orangeEastPortalBitmap,
    		Bitmap blueWestPortalBitmap,
    		Bitmap blueEastPortalBitmap,
    		String gameMode){
		
		this.wallBitmap = wallBitmap;
		this.orangeWestPortalBitmap = orangeWestPortalBitmap;
		this.orangeEastPortalBitmap = orangeEastPortalBitmap;
		this.blueWestPortalBitmap = blueWestPortalBitmap;
		this.blueEastPortalBitmap = blueEastPortalBitmap;
		
		this.gameMode = gameMode;
		
		sourceRect = new Rect(0, 0, 20, 20);
		level = new Vector<Coordinates>();
	}
	
	public Vector<Coordinates> getLevel(){
		
		return level;
	}
	
	public void generateLevel(int levelNr){
		if(levelNr == 1) generateWalls();
		if(levelNr == 2) generateLevel2();
		
		int halfWidth;
		int pWidth = width / 20;
		int pHeight = height / 20;
		halfWidth = pWidth/2;
		
		for(int i = 2; i<pHeight-1; i++){
			if(i!=22 && i!=15) level.add(new Coordinates((halfWidth-1)*20,20*i));
		}

		bluePortal = new Portal((halfWidth-1)*20, 22*20, Portal.WEST);
		orangePortal  = new Portal((halfWidth-1)*20, 15*20, Portal.EAST);
	}
	
	private void generateLevel2(){
		int pWidth = width / 20;

		int pHeight = height / 20;

		int halfWidth = pWidth/2;
		int halfHeight = pHeight/2;
		
		for(int i = 1; i<halfHeight; i++){
			level.add(new Coordinates(0,(halfHeight*20)+20*i));
		}
		for(int i = 0; i<halfWidth; i++){
			level.add(new Coordinates(20*i, (pHeight-1)*20));
		}
		for(int i = 0; i<pWidth; i++){
			level.add(new Coordinates(20*i,halfHeight*20));
		}
	}
	
	public void generateWalls(){
		
		int pWidth = width / 20;
		int pHeight = height / 20;
		
		for(int i = 2; i<pHeight-1; i++){
			level.add(new Coordinates(0,20*i));
			level.add(new Coordinates((pWidth-1)*20,20*i));
		}
		for(int i = 0; i<pWidth; i++){
			level.add(new Coordinates(20*i,40));
			level.add(new Coordinates(20*i, (pHeight-1)*20));
		}
	}
	
	public void showLevel(){
		ListIterator<Coordinates> it = level.listIterator();
		while(it.hasNext()){
			Coordinates c = it.next();
		}
	}
	
	/**
	 * Metoda rysuje snejka na canvasie
	 * 
	 * @param canvas canvas na ktorym metoda rysuje snejka
	 */
	public void draw(Canvas canvas) {
		ListIterator<Coordinates> it = level.listIterator();
		while(it.hasNext()){
			Coordinates c = it.next();
			Rect destRect = new Rect(c.getXPos(), c.getYPos(), c.getXPos() + 20, c.getYPos() + 20);
			canvas.drawBitmap(wallBitmap, sourceRect, destRect, null);
		
		}
		if(gameMode.equals("portals")){
			Rect destRect = null;
			
			destRect = new Rect(bluePortal.getXPos()-20, bluePortal.getYPos(), bluePortal.getXPos(), bluePortal.getYPos()+20);
			canvas.drawBitmap(blueWestPortalBitmap, sourceRect, destRect, null);
			
			destRect = new Rect(bluePortal.getXPos(), bluePortal.getYPos(), bluePortal.getXPos()+20, bluePortal.getYPos()+20);
			canvas.drawBitmap(wallBitmap, sourceRect, destRect, null);
			
			destRect = new Rect(orangePortal.getXPos()+20, orangePortal.getYPos(), orangePortal.getXPos()+40, orangePortal.getYPos()+20);
			canvas.drawBitmap(orangeEastPortalBitmap, sourceRect, destRect, null);
			
			destRect = new Rect(orangePortal.getXPos(), orangePortal.getYPos(), orangePortal.getXPos()+20, orangePortal.getYPos()+20);
			canvas.drawBitmap(wallBitmap, sourceRect, destRect, null);
		}
	}

	public void setWidth(int width){
		this.width = width;
	}
	
	public void setHeight(int height){
		this.height = height;
	}

	public Portal getBluePortal() {
		return bluePortal;
	}

	public void setBluePortal(Portal bluePortal) {
		this.bluePortal = bluePortal;
	}

	public Portal getOrangePortal() {
		return orangePortal;
	}

	public void setOrangePortal(Portal orangePortal) {
		this.orangePortal = orangePortal;
	}
	
	public void setGameMode(String gameMode){
		this.gameMode = gameMode;
	}
}
