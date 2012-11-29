package game;

import java.util.ArrayList;
import java.util.Random;
import java.util.Vector;

import model.Apple;
import model.ColisionDetector;
import model.Snake;
import model.SnakePiece;


import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Vibrator;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.example.game.R;

public class MainGamePanel extends SurfaceView implements SurfaceHolder.Callback, SensorEventListener {

	private static Random generator = new Random();
	private MainThread thread;
	private Snake snake;
	private Vector<Apple> apples = new Vector<Apple>();
	private Bitmap appleBitmap;

	private Vibrator vibrator;
	
	private int downX;
	//private int upX;
	private int downY;
	//private int upY;
	
	private Sensor mAccelerometer;

	public MainGamePanel(Context context, SensorManager sm, Display d, Vibrator vibrator) {
		super(context);
		getHolder().addCallback(this);
		setFocusable(true);
		mAccelerometer = sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
		sm.registerListener(this, mAccelerometer, SensorManager.SENSOR_DELAY_UI);
		this.vibrator = vibrator;
		
		appleBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.obstacle);
		Bitmap snakeHeadEastBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.glowa_east);
		Bitmap snakeHeadWestBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.glowa_west);
		Bitmap snakeHeadNorthBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.glowa_north);
		Bitmap snakeHeadSouthBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.glowa_south);
		Bitmap snakeBodyBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.tulow);
		Bitmap snakeTailEastBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ogon_east);
		Bitmap snakeTailWestBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ogon_west);
		Bitmap snakeTailNorthBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ogon_north);
		Bitmap snakeTailSouthBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ogon_south);
		
		snake = new Snake(Bitmap.createScaledBitmap(snakeHeadEastBitmap, 20, 20, true),
				Bitmap.createScaledBitmap(snakeHeadWestBitmap, 20, 20, true),
				Bitmap.createScaledBitmap(snakeHeadNorthBitmap, 20, 20, true),
				Bitmap.createScaledBitmap(snakeHeadSouthBitmap, 20, 20, true),
				Bitmap.createScaledBitmap(snakeBodyBitmap, 20, 20, true),
				Bitmap.createScaledBitmap(snakeTailEastBitmap, 20, 20, true),
				Bitmap.createScaledBitmap(snakeTailWestBitmap, 20, 20, true),
				Bitmap.createScaledBitmap(snakeTailNorthBitmap, 20, 20, true),
				Bitmap.createScaledBitmap(snakeTailSouthBitmap, 20, 20, true),
				40, 40);
		
		thread = new MainThread(getHolder(), this);
	}

	public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
		// TODO Auto-generated method stub
	}

	public void surfaceCreated(SurfaceHolder holder) {
		snake.setWidth(getWidth());
		snake.setHeight(getHeight());
		
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
			} catch (InterruptedException e) {
			}
		}
	}

	/*
	public void newGame(){
		snake = new Snake(Bitmap.createScaledBitmap(snakeBitmap, 20, 20, true), 40, 40);
	}
	*/
	
	/**
	 * Metoda tworzy nowe jablko. Niestety mozna to wywolac dopiero po surfaceCreated bo odnosi sie do rozmiarow
	 * tej wlasnie powierzchni
	 */
	private void createApple() {
		int i = getWidth()/20;
		int j = getHeight()/20;
		
		int appleX;
		do{
			appleX = generator.nextInt(i)*20;
		}while(appleX % 20 != 0);
		
		int appleY;
		do{
			appleY = generator.nextInt(j)*20;
		}while(appleY % 20 != 0);	
		
		apples.add(new Apple(Bitmap.createScaledBitmap(appleBitmap, 20, 20, true), appleX, appleY));		
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
		snake.draw(canvas);
		if(!apples.isEmpty())
		for (Apple obstacle : apples) {
			obstacle.draw(canvas);
		}
	}

	/**
	 * Metoda aktualizuje stan gry - tworzy jablka (jak trzeba), sprawdza i obsluguje kolizje i aktualizuje weza.
	 */
	public void update() {
		
		if (apples.isEmpty()) createApple();
		
		try {
			MainThread.sleep(300);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		checkAndHandleCollisions();
		snake.updateSnake();
	}

	/**
	 * Metoda sprawdza i obsluguje kolizje - co innego jesli kolizja z jablkiem a co innego jesli z samym soba.
	 */
	public void checkAndHandleCollisions() {
		
		Apple obstacle = apples.get(0);
		if (ColisionDetector.isCollision(snake, obstacle)) {
			vibrator.vibrate(500);
			apples.remove(obstacle);
			snake.setGrowSnake(true);
			render(thread.getCanvas());
		}
		if (ColisionDetector.isCollision(snake)) {
			vibrator.vibrate(500);
			thread.setRunning(false);
		}
	}

	public MainThread getThread(){
		return this.thread;
	}

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
	
	/**
	 * Metoda zapisuje stan gry uzywajac do tego Shared Preferences.
	 * 
	 * @param editor
	 */
	public void saveState(SharedPreferences.Editor editor) {

		Log.d("game.MainGamePanel", "Siema gejm sejw");
        
        //zapis weza
        //editor.putInt("headXPos", snake.getXPos());
        //editor.putInt("headYPos", snake.getYPos());
        
        editor.putInt("direction", snake.getDir());
        editor.putInt("nextDirection", snake.getNextDir());
        
        editor.putInt("snakeBodySize", snake.getSnakeBody().size());

        int i = 0;
        for(SnakePiece s: snake.getSnakeBody()){
        	editor.putInt("snakeBodyPiece"+i+"X", s.getXPos());
        	editor.putInt("snakeBodyPiece"+i+"Y", s.getYPos());
        }
        
		if(!apples.isEmpty()){
			editor.putInt("appleXPos", apples.get(0).getXPosition());
			editor.putInt("appleYPos", apples.get(0).getYPosition()); 
        }
    }
	
	/**
	 * Metoda wczytuje stan gry
	 * 
	 * @param settings
	 */
	public void restoreState(SharedPreferences settings) {
		
		Log.d("game.MainGamePanel", "Siema gejm lold");
		
		ArrayList<SnakePiece> body = new ArrayList<SnakePiece>();
		int snakeBodySize = settings.getInt("snakeBodySize", 0);
		
		for(int i = 0; i<snakeBodySize; i++){
			body.add(new SnakePiece(settings.getInt("snakeBodyPiece"+i+"X", 0), settings.getInt("snakeBodyPiece"+i+"Y", 0)));
		}
		
		snake.setXPos(settings.getInt("headXPos", 40));
		snake.setYPos(settings.getInt("headYPos", 40));
		snake.setDir(settings.getInt("direction", Snake.EAST));
		if(snakeBodySize != 0){
			snake.setSnakeBody(body);
			snake.setGrowSnake(false);
		}
		snake.setNextDir(settings.getInt("nextDirection", Snake.EAST));

		int appleX = settings.getInt("appleXPos", 13);
		int appleY = settings.getInt("appleYPos", 13);
		if(appleX != 13 && appleY != 13) apples.add(new Apple(Bitmap.createScaledBitmap(appleBitmap, 20, 20, true), appleX, appleY));

    }
	
}
