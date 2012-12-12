package game;

import java.util.ArrayList;
import java.util.ListIterator;
import java.util.Random;
import java.util.Vector;

import model.Apple;
import model.ColisionDetector;
import model.Coordinates;
import model.Fruit;
import model.OtherFruit;
import model.Portal;
import model.Snake;
import model.SnakePiece;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Vibrator;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.example.game.R;

/**
 * implements SensorEventListener for sensors
 * 
 * @author Olo
 *
 */
public class MainGamePanel extends SurfaceView implements SurfaceHolder.Callback{

	private static Random generator = new Random();
	private MainThread thread;
	private Snake snake;
	private Vector<Apple> apples = new Vector<Apple>();
	private Bitmap appleBitmap;
	
	//TOMEK
	//private Bitmap pearBitmap;
	//private Bitmap cherriesBitmap;
	//private Bitmap watermelonBitmap;
	private Vector<OtherFruit> otherFruits = new Vector<OtherFruit>(); 
	private Vector<Bitmap> otherFruitsBitmaps = new Vector<Bitmap>();
	private long ticker;
	//TOMEK

	private Vibrator vibrator;
	private boolean vibrate;
	
	private String gameMode;
	
	private int downX;
	private int downY;
	private int score;
	private int level;
	private boolean gameOver;
	
	private Map map;
	//private Sensor mAccelerometer;

	//public MainGamePanel(Context context, SensorManager sm, Display d, Vibrator vibrator) {
	public MainGamePanel(Context context, String gameMode) {
		super(context);
		
		getHolder().addCallback(this);
		setFocusable(true);
		//mAccelerometer = sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
		//sm.registerListener(this, mAccelerometer, SensorManager.SENSOR_DELAY_UI);
			
		this.score = 0;
		this.level = 1;
		this.gameOver = false;
		this.gameMode = gameMode;
		
		Bitmap wallBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.wall);
		Bitmap orangeWestPortalBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.orange_west);
		Bitmap orangeEastPortalBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.orange_east);
		Bitmap blueWestPortalBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.blue_west);
		Bitmap blueEastPortalBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.blue_east);
		map = new Map(Bitmap.createScaledBitmap(wallBitmap, 20, 20, true), 
				Bitmap.createScaledBitmap(orangeWestPortalBitmap, 20, 20, true), 
				Bitmap.createScaledBitmap(orangeEastPortalBitmap, 20, 20, true), 
				Bitmap.createScaledBitmap(blueWestPortalBitmap, 20, 20, true), 
				Bitmap.createScaledBitmap(blueEastPortalBitmap, 20, 20, true),
				gameMode);
		
		appleBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.apple);
		
		//TOMEK
		Bitmap pearBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.pear);
		Bitmap cherriesBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.cherries);
		Bitmap watermelonBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.watermelon);
		
		this.otherFruitsBitmaps.add(Bitmap.createScaledBitmap(pearBitmap, 20, 20, true));
		this.otherFruitsBitmaps.add(Bitmap.createScaledBitmap(cherriesBitmap, 20, 20, true));
		this.otherFruitsBitmaps.add(Bitmap.createScaledBitmap(watermelonBitmap, 20, 20, true));
		//TOMEK
		
		Bitmap snakeHeadEastBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.glowa_sprite_east);
		Bitmap snakeHeadWestBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.glowa_sprite_west);
		Bitmap snakeHeadNorthBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.glowa_sprite_north);
		Bitmap snakeHeadSouthBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.glowa_sprite_south);
		Bitmap snakeBodyBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.tulow);
		Bitmap snakeTailEastBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ogon_sprite_east);
		Bitmap snakeTailWestBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ogon_sprite_west);
		Bitmap snakeTailNorthBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ogon_sprite_north);
		Bitmap snakeTailSouthBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ogon_sprite_south);
		
		snake = new Snake(Bitmap.createScaledBitmap(snakeHeadEastBitmap, 120, 20, true),
				Bitmap.createScaledBitmap(snakeHeadWestBitmap, 120, 20, true),
				Bitmap.createScaledBitmap(snakeHeadNorthBitmap, 120, 20, true),
				Bitmap.createScaledBitmap(snakeHeadSouthBitmap, 120, 20, true),
				Bitmap.createScaledBitmap(snakeBodyBitmap, 20, 20, true),
				Bitmap.createScaledBitmap(snakeTailEastBitmap, 80, 20, true),
				Bitmap.createScaledBitmap(snakeTailWestBitmap, 80, 20, true),
				Bitmap.createScaledBitmap(snakeTailNorthBitmap, 80, 20, true),
				Bitmap.createScaledBitmap(snakeTailSouthBitmap, 80, 20, true),
				4,6,4,															//TOMEK
				80, 80, gameMode, map);
				
		
		this.ticker = 0l;		//TOMEK
		thread = new MainThread(getHolder(), this);
		
	}

	public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
		// TODO Auto-generated method stub
	}

	public void surfaceCreated(SurfaceHolder holder) {
		snake.setWidth(getWidth());
		snake.setHeight(getHeight());
		
		map.setWidth(getWidth());
		map.setHeight(getHeight());
		if(gameMode.equals("walls")) {
			map.generateWalls();
			//map.showLevel();
		}
		if(gameMode.equals("portals")) {
			map.generateLevel();
			//map.showLevel();
		}
		
		thread.setGameType(gameMode);
		//if(apples.isEmpty()) createApple();
		
		thread.setRunning(true);
        thread.start();
	}

	public void surfaceDestroyed(SurfaceHolder holder) {
		thread.setRunning(false);
		boolean retry = true;
		while (retry) {
			try {
				thread.join();
				retry = false;
			} catch (InterruptedException e) {}
		}
	}
	
	/**
	 * Metoda tworzy nowe jablko. Niestety mozna to wywolac dopiero po surfaceCreated bo odnosi sie do rozmiarow
	 * tej wlasnie powierzchni
	 */
	private void createApple() {
		int i = getWidth()/20;
		int j = getHeight()/20;
		
		Apple apple = new Apple(Bitmap.createScaledBitmap(appleBitmap, 20, 20, true), 0, 0);
		do{
			int appleX;
			do{
				appleX = generator.nextInt(i)*20;
			}while(appleX % 20 != 0);
			apple.setXPos(appleX);
			
			int appleY;
			do{
				appleY = generator.nextInt(j)*20;
				if(appleY < 40) appleY = 43;
			}while(appleY % 20 != 0);
			apple.setYPos(appleY);		
			
		}while(ColisionDetector.isCollision(apple, map, snake));
		
		apples.add(apple);		
	}
	
	
	/**
	 * TOMEK
	 */
	private void createOtherFruit() {
		int i = getWidth()/20;
		int j = getHeight()/20;
		int n = generator.nextInt(3);
		
		OtherFruit fruit = new OtherFruit(otherFruitsBitmaps.get(n), 0, 0, n+1);
		do{
			int fruitX;
			do{
				fruitX = generator.nextInt(i)*20;
			}while(fruitX % 20 != 0);
			fruit.setXPos(fruitX);
			
			int fruitY;
			do{
				fruitY = generator.nextInt(j)*20;
				if(fruitY < 40) fruitY = 43;
			}while(fruitY % 20 != 0);
			fruit.setYPos(fruitY);		
			
		}while(ColisionDetector.isCollision(fruit, map, snake));
		
		otherFruits.add(fruit);		
	}
	
	private void removeOtherFruit() {
		otherFruits.remove(0);
	}

	/**
	 * Co sie dzieje jak sie nacisnie ekran.
	 * Przesuniecie (czyli MotionEvent.ACTION_MOVE) rozlozylem na 2 eventy DOWN i UP, zczytuje wspolrzedne
	 * i przekazuje do weza.
	 */
	@Override
	public boolean onTouchEvent(MotionEvent event) {

		if (event.getAction() == MotionEvent.ACTION_DOWN){
			this.downX = (int)event.getX();
			this.downY = (int)event.getY();
		}
		
		if (event.getAction() == MotionEvent.ACTION_UP){
			snake.handleActionMove(this.downX, this.downY,(int) event.getX(), (int) event.getY(), getWidth(), getHeight());
		}
		
		return true;
	}

	/**
	 * Metoda renderujaca
	 * 
	 * @param canvas
	 */
	protected void render(Canvas canvas) {
		canvas.drawColor(Color.BLACK);
		if(gameMode.equals("portals") || gameMode.equals("walls")) map.draw(canvas);
		Paint paint = new Paint();
		paint.setColor(Color.WHITE);
		canvas.drawLine((float)0, (float)40, (float)getWidth(), (float)40, paint);
		paint.setTextSize(22);
		canvas.drawText("Score: "+score, 30, 25, paint);
		canvas.drawText("Level: "+level, 300, 25, paint);
		
		//if(gameType.equalsIgnoreCase("portals")) Map.draw(canvas);
		
		if(!apples.isEmpty())
			for (Apple obstacle : apples) {
				obstacle.draw(canvas);
			}
		
		//TOMEK
		if(!otherFruits.isEmpty())
			for (OtherFruit obstacle : otherFruits) {
				obstacle.draw(canvas);
			}
		
		snake.draw(canvas);
		
	}

	protected void renderGameOver(Canvas canvas) {
		canvas.drawColor(Color.BLACK);
		if(gameMode.equals("portals") || gameMode.equals("walls")) map.draw(canvas);
		Paint paint = new Paint();
		paint.setColor(Color.WHITE);
		paint.setTextSize(40);
		canvas.drawText("GAME", 100, 400, paint);
		canvas.drawText("OVER", 100, 500, paint);

	}

	/**
	 * Metoda aktualizuje stan gry - tworzy jablka (jak trzeba), sprawdza i obsluguje kolizje i aktualizuje weza.
	 */
	public void update() {
		
		if(apples.isEmpty()) createApple();
		//if(otherFruits.isEmpty()) createOtherFruit();	//TOMEK
		int sleepTime = 300 - level*50;
		try {
			MainThread.sleep(sleepTime);
			//MainThread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		if(!gameOver) snake.updateSnake(System.currentTimeMillis());   //TOMEK
		checkAndHandleCollisions();
		this.calculateBonus();	//TOMEK
	}
	
	
	/*TOMEK*/
	private void calculateBonus() {
		long gameTime = System.currentTimeMillis();
		if(otherFruits.isEmpty()) {
			long time = this.ticker + 1000*(7 + generator.nextInt(7)) + level*500;
			if(gameTime > time) {
				this.ticker = gameTime;
				this.createOtherFruit();
			}
		}
		
		else {
			long time = this.ticker + 10000 - level*1000;
			if(gameTime >  time) {
				this.ticker = gameTime;
				this.removeOtherFruit();
			}
		}
	}
	/*TOMEK*/
	
	
	

	/**
	 * Metoda sprawdza i obsluguje kolizje - co innego jesli kolizja z jablkiem a co innego jesli z samym soba.
	 */
	public void checkAndHandleCollisions() {
		
		//Apple obstacle = apples.get(0);
		if(gameMode.equals("walls") || gameMode.equals("portals")){
			if (ColisionDetector.isCollisionWalls(snake, map, gameMode)) {
				this.gameOver = true;
				vibrator.vibrate(500);		
				thread.setGameOver(true);
				Log.d("game.MainGamePanel", "Siema gameover " +gameOver);
				thread.setRunning(false);
			}
		}
		if(gameMode.equals("portals")){
			ListIterator<SnakePiece> it = snake.getSnakeBody().listIterator();
			while(it.hasNext()){
				SnakePiece s = it.next();
				if(ColisionDetector.isCollision(s, map.getBluePortal())){
					if(map.getBluePortal().getDirection() == Portal.WEST){
						if(snake.getDir() == Snake.EAST){
							//s.setXPos(map.getOrangePortal().getXPos());
							s.setXPos(map.getOrangePortal().getXPos()+20);
							s.setYPos(map.getOrangePortal().getYPos());							
						}
					}
				}
				if(ColisionDetector.isCollision(s, map.getOrangePortal())){
					if(map.getOrangePortal().getDirection() == Portal.EAST){
						if(snake.getDir() == Snake.WEST){
							//s.setXPos(map.getBluePortal().getXPos());
							s.setXPos(map.getBluePortal().getXPos()-20);
							s.setYPos(map.getBluePortal().getYPos());
						}
					}
				}
			}
		}
		
		Apple apple = apples.get(0);		//TOMEK
		if (ColisionDetector.isCollision(snake, apple)) {
			this.incrementScore(0);
			if(vibrate) vibrator.vibrate(500);
			apples.remove(apple);
			snake.setGrowSnake(true);
			render(thread.getCanvas());
		}
		
		//TOMEK
		if(!otherFruits.isEmpty()) {
			OtherFruit fruit = otherFruits.get(0);		
			
			if (ColisionDetector.isCollision(snake, fruit)) {
				this.incrementScore(fruit.bonus);
				this.ticker = System.currentTimeMillis();
				if(vibrate) vibrator.vibrate(500);
				otherFruits.remove(fruit);
				snake.setGrowSnake(true);
				render(thread.getCanvas());
			}
		}
		//TOMEK
		
		if (ColisionDetector.isCollision(snake)) {
			this.gameOver = true;
			vibrator.vibrate(500);		
			Log.d("game.MainGamePanel", "Siema gameover " +gameOver);
			thread.setRunning(false);
		}
	}

	public MainThread getThread(){
		return this.thread;
	}

	/*
	public void onAccuracyChanged(Sensor arg0, int arg1) {
		// TODO Auto-generated method stub
		
	}

	public void onSensorChanged(SensorEvent event) {
		// TODO Auto-generated method stub
		//float azimuth_angle = event.values[0];
	    float pitch_angle = event.values[0];
	    float roll_angle = event.values[1];
	    //spaceship.handleActionMove(pitch_angle, roll_angle, getWidth(), getHeight());
	}
	*/
	
	/**
	 * Metoda zapisuje stan gry uzywajac do tego Shared Preferences.
	 * 
	 * @param editor
	 */
	public void saveState(SharedPreferences.Editor editor) {

		Log.d("game.MainGamePanel", "Siema gejm sejw "+gameOver);
        
        //zapis weza      
        editor.putInt("direction", snake.getDir());
        editor.putInt("nextDirection", snake.getNextDir());
        
        editor.putInt("snakeBodySize", snake.getSnakeBody().size());

        for(int i = 0; i<snake.getSnakeBody().size(); i++){
        	editor.putInt("snakeBodyPiece"+i+"X", snake.getSnakeBody().get(i).getXPos());
        	//Log.d("game.Snake", "snakeBodyPiece"+i+"X"+snake.getSnakeBody().get(i).getXPos());
        	
        	editor.putInt("snakeBodyPiece"+i+"Y", snake.getSnakeBody().get(i).getYPos());
        	//Log.d("game.Snake", "snakeBodyPiece"+i+"Y"+snake.getSnakeBody().get(i).getYPos());
        }
        
        //zapis jablka
		if(!apples.isEmpty()){
			editor.putInt("appleXPos", apples.get(0).getXPos());
			editor.putInt("appleYPos", apples.get(0).getYPos()); 
        }
		
		editor.putBoolean("gameOver", gameOver);	
		editor.putInt("Score", score);
		editor.putInt("Level", level);
    }
	
	/**
	 * Metoda zapisuje stan gry uzywajac do tego Bundle.
	 * 
	 * @param editor
	 */
	public Bundle saveState() {

		Log.d("game.MainGamePanel", "Siema gejm sejw orientejszyn");
		
		Bundle bundle = new Bundle();
        //zapis weza      
		bundle.putInt("direction", snake.getDir());
		bundle.putInt("nextDirection", snake.getNextDir());
        
		bundle.putInt("snakeBodySize", snake.getSnakeBody().size());

        for(int i = 0; i<snake.getSnakeBody().size(); i++){
        	bundle.putInt("snakeBodyPiece"+i+"X", snake.getSnakeBody().get(i).getXPos());        	
        	bundle.putInt("snakeBodyPiece"+i+"Y", snake.getSnakeBody().get(i).getYPos());
        }
        
        //zapis jablka
		if(!apples.isEmpty()){
			bundle.putInt("appleXPos", apples.get(0).getXPos());
			bundle.putInt("appleYPos", apples.get(0).getYPos()); 
        }
		
		bundle.putBoolean("gameOver", gameOver);	
		bundle.putInt("Score", score);
		bundle.putInt("Level", level);
		
		return bundle;
    }
	
	/**
	 * Metoda wczytuje stan gry uzywajac SharedPreferences
	 * 
	 * @param settings
	 */
	public void restoreState(SharedPreferences settings) {
		
		ArrayList<SnakePiece> body = new ArrayList<SnakePiece>();
		int snakeBodySize = settings.getInt("snakeBodySize", 0);
		
		for(int i = 0; i<snakeBodySize; i++){
			body.add(new SnakePiece(settings.getInt("snakeBodyPiece"+i+"X", 0), settings.getInt("snakeBodyPiece"+i+"Y", 0)));
		}
		
		snake.setDir(settings.getInt("direction", Snake.EAST));
		if(snakeBodySize != 0){
			snake.setSnakeBody(body);
			snake.setGrowSnake(false);
		}
		snake.setNextDir(settings.getInt("nextDirection", Snake.EAST));

		int appleX = settings.getInt("appleXPos", 13);
		int appleY = settings.getInt("appleYPos", 13);
		if(appleX != 13 && appleY != 13) apples.add(new Apple(Bitmap.createScaledBitmap(appleBitmap, 20, 20, true), appleX, appleY));
		else createApple();
		this.score = settings.getInt("Score", 0);
		this.level = settings.getInt("Level", 1);
		this.gameOver = settings.getBoolean("gameOver", false);

		if(gameOver) this.thread.setGameOver(true);

    }
	
	/**
	 * Metoda wczytuje stan gry uzywajac Bundle
	 * 
	 * @param settings
	 */
	public void restoreState(Bundle bundle) {
		
		ArrayList<SnakePiece> body = new ArrayList<SnakePiece>();
		int snakeBodySize = bundle.getInt("snakeBodySize", 0);
		
		for(int i = 0; i<snakeBodySize; i++){
			body.add(new SnakePiece(bundle.getInt("snakeBodyPiece"+i+"X", 0), bundle.getInt("snakeBodyPiece"+i+"Y", 0)));
		}
		
		snake.setDir(bundle.getInt("direction", Snake.EAST));
		if(snakeBodySize != 0){
			snake.setSnakeBody(body);
			snake.setGrowSnake(false);
		}
		snake.setNextDir(bundle.getInt("nextDirection", Snake.EAST));

		int appleX = bundle.getInt("appleXPos", 13);
		int appleY = bundle.getInt("appleYPos", 13);
		if(appleX != 13 && appleY != 13) apples.add(new Apple(Bitmap.createScaledBitmap(appleBitmap, 20, 20, true), appleX, appleY));
		else createApple();
		
		this.score = bundle.getInt("Score", 0);
		this.level = bundle.getInt("Level", 1);
		this.gameOver = bundle.getBoolean("gameOver", false);

		if(gameOver) this.thread.setGameOver(true);

    }
	
	public void setVibrate(boolean vibrate){
		this.vibrate = vibrate;
	}
	
	public void setVibrator(Vibrator vibrator){
		this.vibrator = vibrator;
	}
	
	public void setGameType(String gameType){
		this.gameMode = gameType;
	}
	
	public void incrementScore(int n){
		score = score + 10 + n*5;
		if(score >= level*100) level++;
	}
	
}
