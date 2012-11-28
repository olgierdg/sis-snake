package model;

import java.util.ArrayList;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

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
	
	protected Bitmap bitmap;
	protected int xPos;
	protected int yPos;
	private int mDirection = EAST;
    private int mNextDirection = EAST;
    private static final int NORTH = 1;
    private static final int SOUTH = 2;
    private static final int EAST = 3;
    private static final int WEST = 4;
    
    public ArrayList<SnakePiece> snakeBody;
    
    boolean growSnake;

	//public Snake(Bitmap bitmap, int xPos, int yPos, int fps, int frameCount) {
    public Snake(Bitmap bitmap, int xPos, int yPos, int width, int height) {
		this.bitmap = bitmap;
		this.xPos = xPos;
		this.yPos = yPos;
		snakeBody = new ArrayList<SnakePiece>();
		snakeBody.add(new SnakePiece(xPos, yPos));
		//frameNr = frameCount;
		//spriteWidth = bitmap.getWidth() / frameCount;
		
		spriteWidth = bitmap.getWidth();
		spriteHeight = bitmap.getHeight();
		
		//spriteWidth = 20;
		//spriteHeight = 20;
		
		sourceRect = new Rect(0, 0, spriteWidth, spriteHeight);
		//framePeriod = 1000 / fps;
		//frameTicker = 0l;
		this.canvasWidth = width;
		this.canvasHeight = height;
		this.growSnake = true;
	}

    public SnakePiece getHead(){
    	return snakeBody.get(0);
    }
    
	public void newSnakePiece(){
		snakeBody.add(new SnakePiece(snakeBody.get(snakeBody.size()-1)));
	}
	
	public void setGrowSnake(boolean g){
		this.growSnake = g;
	}
	
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

	public void handleActionMove(float eventX, float eventY, int canvasWidth, int canvasHeight) {
		if(Math.abs(eventX) > Math.abs(eventY)){
			if(eventX < 0 && mDirection != EAST) mNextDirection = Snake.WEST;
			if(eventX > 0 && mDirection != WEST) mNextDirection = Snake.EAST;
		}else{
			if(eventY < 0 && mDirection != SOUTH) mNextDirection = Snake.NORTH;
			if(eventY > 0 && mDirection != NORTH) mNextDirection = Snake.SOUTH;
		}
	}
	
	public void borders(int canvasWidth) {
		if (xPos > canvasWidth - spriteWidth) {
			xPos = spriteWidth;
		}
		if (xPos < 0) {
			xPos = 0;
		}
	}

	// the draw method which draws the corresponding frame
	public void draw(Canvas canvas) {
		for(int i = 0; i<snakeBody.size(); i++){
			Rect destRect = new Rect(snakeBody.get(i).xPos, snakeBody.get(i).yPos, snakeBody.get(i).xPos + spriteWidth, snakeBody.get(i).yPos + spriteHeight);
			canvas.drawBitmap(bitmap, sourceRect, destRect, null);
		}
		//boundingRect = destRect;
	}

	/* the update method for ship
	public void update(long gameTime, int canvasWidth) {
		//super.update();
		mDirection = mNextDirection;
		
		calculateSourceRect(gameTime);
		borders(canvasWidth);
	}
	 */
	
	public void updateSnake() {
        

        SnakePiece head = snakeBody.get(0);
        SnakePiece newHead = new SnakePiece(1, 1);

        mDirection = mNextDirection;

        switch (mDirection) {
	        case EAST: {
	            newHead = new SnakePiece(head.xPos + 20, head.yPos);
	            if(newHead.xPos > canvasWidth-19) newHead.xPos = 0;
	            break;
	        }
	        case WEST: {
	            newHead = new SnakePiece(head.xPos - 20, head.yPos);
	            if(newHead.xPos == -20) newHead.xPos = canvasWidth-20-(canvasWidth % 20);
	            break;
	        }
	        case NORTH: {
	            newHead = new SnakePiece(head.xPos, head.yPos - 20);
	            if(newHead.yPos == -20) newHead.yPos = canvasHeight-20-(canvasHeight % 20); 
	            break;
	        }
	        case SOUTH: {
	            newHead = new SnakePiece(head.xPos, head.yPos + 20);
	            if(newHead.yPos > canvasHeight-19) newHead.yPos = 0;
	            break;
	        }
        }

        // push a new head onto the ArrayList and pull off the tail
        snakeBody.add(0, newHead);
        // except if we want the snake to grow
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
}
