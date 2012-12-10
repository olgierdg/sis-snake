package model;

import game.Map;

import java.util.ArrayList;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

/**
 * O kurde jaki tu syf... Generalnie to jest klasa weza
 * 
 * @author Olo
 *
 */
public class Snake {

	private Rect sourceRect; // the rectangle to be drawn from the animation
								// bitmap
	/*
	private int frameNr; // number of frames in animation
	private int currentFrame = 0; // the current frame
	private long frameTicker; // the time of the last frame update
	private int framePeriod; // milliseconds between each frame (1000/fps)
	*/
	
	private int spriteWidth; // the width of the sprite to calculate the cut out
								// rectangle

	private int spriteHeight; // the height of the sprite
	
	private int canvasHeight;
	private int canvasWidth;
	
	private Bitmap snakeHeadEastBitmap;
	private Bitmap snakeHeadWestBitmap;
	private Bitmap snakeHeadNorthBitmap;
	private Bitmap snakeHeadSouthBitmap;
	private Bitmap snakeBodyBitmap;
	private Bitmap snakeTailEastBitmap;
	private Bitmap snakeTailWestBitmap;
	private Bitmap snakeTailNorthBitmap;
	private Bitmap snakeTailSouthBitmap;
	
	//protected int xPos;
	//protected int yPos;
	private int mDirection = EAST;
    private int mNextDirection = EAST;
    public static final int NORTH = 1;
    public static final int SOUTH = 2;
    public static final int EAST = 3;
    public static final int WEST = 4;
    
    public ArrayList<SnakePiece> snakeBody;
    
    boolean growSnake;
    boolean dirChange;
    int changeCount;
    String gameMode;
    Map map;

	//public Snake(Bitmap bitmap, int xPos, int yPos, int fps, int frameCount) {
    public Snake(Bitmap headEastBitmap, 
    		Bitmap headWestBitmap, 
    		Bitmap headNorthBitmap, 
    		Bitmap headSouthBitmap, 
    		Bitmap bodyBitmap, 
    		Bitmap tailEastBitmap, 
    		Bitmap tailWestBitmap,
    		Bitmap tailNorthBitmap,
    		Bitmap tailSouthBitmap,
    		int xPos, int yPos, String gameMode, Map map) {
    	
		this.snakeHeadEastBitmap = headEastBitmap;
		this.snakeHeadWestBitmap = headWestBitmap;
		this.snakeHeadNorthBitmap = headNorthBitmap;
		this.snakeHeadSouthBitmap = headSouthBitmap;
		
		this.snakeBodyBitmap = bodyBitmap;
		
		this.snakeTailEastBitmap = tailEastBitmap;
		this.snakeTailWestBitmap = tailWestBitmap;
		this.snakeTailNorthBitmap = tailNorthBitmap;
		this.snakeTailSouthBitmap = tailSouthBitmap;
		
		this.gameMode = gameMode;
		this.map = map;
		//this.xPos = xPos;
		//this.yPos = yPos;
		snakeBody = new ArrayList<SnakePiece>();
		snakeBody.add(new SnakePiece(xPos, yPos));
		
		//frameNr = frameCount;
		//spriteWidth = bitmap.getWidth() / frameCount;
		
		spriteWidth = snakeBodyBitmap.getWidth();
		spriteHeight = snakeBodyBitmap.getHeight();
		
		//spriteWidth = 20;
		//spriteHeight = 20;
		
		sourceRect = new Rect(0, 0, spriteWidth, spriteHeight);
		//framePeriod = 1000 / fps;
		//frameTicker = 0l;
		this.growSnake = true;
		//this.dirChange = false;
		//this.changeCount = 0;
	}


   /**
    * Getter dla glowy weza, uzywany glownie przy kolizjach, jesli w ogole nie tylko tam...
    *  
    * @return
    */
    public SnakePiece getHead(){
    	return snakeBody.get(0);
    }
    
    /**
     * Setter boola okreslajacego czy waz ma rosnac, dziala tylko dla nastepnego wywolania metoda updateSnake(), 
     * na jej koniec jest ustawiamy na false.
     * 
     * @param grow bool okreslajacy czy waz ma urosnac
     */
	public void setGrowSnake(boolean grow){
		this.growSnake = grow;
	}
	
	/**
	 * Obsluga akcji jaka jest ruch paluchem po ekranie. Jak widac licze delte, jak w szkole, prawie. 
	 * Tak sie zastanawialem jak to zrobic, no i w liceum zawsze mowili, nie wiesz co robic to policz delte, 
	 * no i sie okazalo ze nawet dziala. Na studiach mowia zeby calke policzyc, ale to juz mnie przeroslo.
	 * 
	 * @param origEventX wspolrzedna X miejsca w ktorym ktos dotknal ekran paluchem
	 * @param origEventY wspolrzedna Y miejsca w ktorym ktos dotknal ekran paluchem
	 * @param eventX wspolrzedna X miejsca w ktorym ktos podniosl paluch z ekranu
	 * @param eventY wspolrzedna Y miejsca w ktorym ktos podniosl paluch z ekranu
	 * @param canvasWidth szerokosc ekranu (powiedzmy)
	 * @param canvasHeight wysokosc ekranu (powiedzmy)
	 */
	public void handleActionMove(int origEventX, int origEventY, int eventX, int eventY, int canvasWidth, int canvasHeight) {
		
		int deltaX = eventX - origEventX;
		int deltaY = eventY - origEventY;
		if(Math.abs(deltaX) >= Math.abs(deltaY)){
			if(deltaX < 0 && mDirection != EAST) mNextDirection = Snake.WEST;
			if(deltaX > 0 && mDirection != WEST) mNextDirection = Snake.EAST;
		}else{
			if(deltaY < 0 && mDirection != SOUTH) mNextDirection = Snake.NORTH;
			if(deltaY > 0 && mDirection != NORTH) mNextDirection = Snake.SOUTH;
		}
	}

	/*
	public void borders(int canvasWidth) {
		if (xPos > canvasWidth - spriteWidth) {
			xPos = spriteWidth;
		}
		if (xPos < 0) {
			xPos = 0;
		}
	}
	*/

	/**
	 * Metoda rysuje snejka na canvasie
	 * 
	 * @param canvas canvas na ktorym metoda rysuje snejka
	 */
	public void draw(Canvas canvas) {
		for(int i = 0; i<snakeBody.size(); i++){
			if(gameMode.equals("portals")){
				//if(ColisionDetector.isCollision(snakeBody.get(i), map.getBluePortal()) || ColisionDetector.isCollision(snakeBody.get(i), map.getOrangePortal()))
				//	continue;
			}
			Rect destRect = new Rect(snakeBody.get(i).getXPos(), snakeBody.get(i).getYPos(), snakeBody.get(i).getXPos() + spriteWidth, snakeBody.get(i).getYPos() + spriteHeight);
			if(i == 0){
				if(this.mDirection == EAST)
						canvas.drawBitmap(snakeHeadEastBitmap, sourceRect, destRect, null);
					if(this.mDirection == WEST)
						canvas.drawBitmap(snakeHeadWestBitmap, sourceRect, destRect, null);
					if(this.mDirection == NORTH)
						canvas.drawBitmap(snakeHeadNorthBitmap, sourceRect, destRect, null);
					if(this.mDirection == SOUTH)
						canvas.drawBitmap(snakeHeadSouthBitmap, sourceRect, destRect, null);
				}
				else if (i == snakeBody.size()-1){
				
					if(snakeBody.get(snakeBody.size()-1).getXPos() == snakeBody.get(snakeBody.size()-2).getXPos())
						if(snakeBody.get(snakeBody.size()-1).getYPos() > snakeBody.get(snakeBody.size()-2).getYPos())
							canvas.drawBitmap(snakeTailNorthBitmap, sourceRect, destRect, null);
						else
							canvas.drawBitmap(snakeTailSouthBitmap, sourceRect, destRect, null);
					
					if(snakeBody.get(snakeBody.size()-1).getYPos() == snakeBody.get(snakeBody.size()-2).getYPos())
						if(snakeBody.get(snakeBody.size()-1).getXPos() > snakeBody.get(snakeBody.size()-2).getXPos())
							canvas.drawBitmap(snakeTailWestBitmap, sourceRect, destRect, null);
						else
							canvas.drawBitmap(snakeTailEastBitmap, sourceRect, destRect, null);					
				}
				else canvas.drawBitmap(snakeBodyBitmap, sourceRect, destRect, null);
		}
		//boundingRect = destRect;
	}

	/**
	 * Metoda odpowiedzialna za aktualizacje weza w trakcie gry, przesuwa weza, no, tak naprawde to tworzy nowa glowe 
	 * weza, o wspolrzednych zmienionych o rozmiar sprite'a w stosunku do starej glowy, w ktorym kierunku zmienionych 
	 * to zalezy od kierunku. Nowa glowa jest wrzucana na poczatek listy, a ostatnia czesc ogona jest usuwana, 
	 * chyba ze bool growSnake jest na true, wtedy nie jest usuwana i waz 'rosnie'. To jest tak proste ze 
	 * az nie ogarniam... Czemu ja tego na Pascala nie klepalem tylko Tetrisa...
	 */
	public void updateSnake() {
        
        SnakePiece head = snakeBody.get(0);
        SnakePiece newHead = new SnakePiece(1, 1);

        /*
        if(mNextDirection != mDirection){
        	this.dirChange = true;
        	this.changeCount = snakeBody.size();
        }
        if(dirChange) changeCount--;
        */
        mDirection = mNextDirection;

        switch (mDirection) {
	        case EAST: {
	            newHead = new SnakePiece(head.getXPos() + 20, head.getYPos());
	            if(newHead.getXPos() > canvasWidth-19) newHead.setXPos(0);
	            break;
	        }
	        case WEST: {
	            newHead = new SnakePiece(head.getXPos() - 20, head.getYPos());
	            if(newHead.getXPos() == -20) newHead.setXPos(canvasWidth-20-(canvasWidth % 20));
	            break;
	        }
	        case NORTH: {
	            newHead = new SnakePiece(head.getXPos(), head.getYPos() - 20);
	            if(newHead.getYPos()== 20) newHead.setYPos(canvasHeight-20-(canvasHeight % 20)); 
	            break;
	        }
	        case SOUTH: {
	            newHead = new SnakePiece(head.getXPos(), head.getYPos() + 20);
	            if(newHead.getYPos() > canvasHeight-19) newHead.setYPos(40);
	            break;
	        }
        }

        snakeBody.add(0, newHead);

        if (!growSnake) {
        	snakeBody.remove(snakeBody.size() - 1);
        }
        growSnake = false;
    }
	
	/*
	private void calculateSourceRect(long gameTime) {
		if (gameTime > frameTicker + framePeriod) {
			frameTicker = gameTime;
			// increment the frame
			currentFrame++;
			if (currentFrame >= frameNr) {
				currentFrame = 0;
			}
		}
		// define the rectangle to cut out sprite
		sourceRect.left = currentFrame * spriteWidth;
		sourceRect.right = sourceRect.left + spriteWidth;
	}
	*/

	/*
	 * 
	 * Te metody chyba nie wymagaja komentarza, same settery i gettery
	 * 
	 */

	public int getDir(){
		return mDirection;
	}
	
	public void setDir(int dir){
		this.mDirection = dir;
	}
	
	public int getNextDir(){
		return mNextDirection;
	}
	
	public void setNextDir(int nDir){
		this.mNextDirection = nDir;
	}
	
	public ArrayList<SnakePiece> getSnakeBody(){
		return snakeBody;
	}
	
	public void setSnakeBody(ArrayList<SnakePiece> body){
		this.snakeBody = body;
	}
	
	public void setWidth(int width){
		this.canvasWidth = width;
	}
	
	public void setHeight(int height){
		this.canvasHeight = height;
	}
}
