package game;

import java.util.Random;
import java.util.Vector;

import model.Apple;
import model.ColisionDetector;
import model.Snake;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.view.Display;
import android.view.MotionEvent;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.example.game.R;

public class MainGamePanel extends SurfaceView implements SurfaceHolder.Callback, SensorEventListener {

	private static Random generator = new Random();
	private MainThread thread;
	private Snake spaceship;
	private Vector<Apple> obstacles = new Vector<Apple>();
	
	private int downX;
	//private int upX;
	private int downY;
	//private int upY;
	
	private Sensor mAccelerometer;

	public MainGamePanel(Context context, SensorManager sm, Display d) {
		super(context);
		getHolder().addCallback(this);
		setFocusable(true);
		mAccelerometer = sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
		sm.registerListener(this, mAccelerometer, SensorManager.SENSOR_DELAY_UI);
		thread = new MainThread(getHolder(), this);
	}

	/*
	private boolean mGameIsRunning;

	public void start() {
	    if (!mGameIsRunning) {
	    	thread.setRunning(true);
	        thread.start();
	        mGameIsRunning = true;
	    } else {
	        thread.onResume();
	    }
	}
	*/
	
	public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
		// TODO Auto-generated method stub
	}

	public void surfaceCreated(SurfaceHolder holder) {
		//spaceship = new Snake(BitmapFactory.decodeResource(getResources(), R.drawable.food), ((getWidth() / 2) - 20), (getHeight() - 100), 4, 4);
		Bitmap b = BitmapFactory.decodeResource(getResources(), R.drawable.food);
		spaceship = new Snake(Bitmap.createScaledBitmap(b, 20, 20, true), 40, 40, getWidth(), getHeight());
		
		createApple();
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


	private void createApple() {
		int i = getWidth()/20;
		int j = getHeight()/20;
		if (obstacles.isEmpty()){
			int appleX;
			do{
				appleX = generator.nextInt(i)*20;
			}while(appleX % 20 != 0);
			int appleY;
			do{
				appleY = generator.nextInt(j)*20;
			}while(appleY % 20 != 0);
			Bitmap b = BitmapFactory.decodeResource(getResources(), R.drawable.obstacle);
				obstacles.add(new Apple(Bitmap.createScaledBitmap(b, 20, 20, true), appleX, appleY));
		}
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		/*
		if (event.getAction() == MotionEvent.ACTION_MOVE) {
			//int historySize = event.getHistorySize();
		    //int pointerCount = event.getPointerCount();
			//spaceship.handleActionMove((int)event.getHistoricalX(1), (int)event.getHistoricalX(1),(int) event.getX(), (int) event.getY(), getWidth(), getHeight());
			
			final int historySize = event.getHistorySize();
		     final int pointerCount = event.getPointerCount();
		     for (int h = 0; h < historySize; h++) {
		         System.out.printf("At time %d:", event.getHistoricalEventTime(h));
		         for (int p = 0; p < pointerCount; p++) {
		             System.out.printf("  pointer %d: (%f,%f)",
		            		 event.getPointerId(p), event.getHistoricalX(p, h), event.getHistoricalY(p, h));
		         }
		     }
		}
		*/

		if (event.getAction() == MotionEvent.ACTION_DOWN){
			this.downX = (int)event.getX();
			this.downY = (int)event.getY();
		}
		
		if (event.getAction() == MotionEvent.ACTION_UP){
			//this.upX = (int)event.getX();
			//this.upY = (int)event.getY();
			spaceship.handleActionMove(this.downX, this.downY,(int) event.getX(), (int) event.getY(), getWidth(), getHeight());
			
		}
		
		return true;
	}


	
	protected void render(Canvas canvas) {
		canvas.drawColor(Color.BLACK);
		spaceship.draw(canvas);
		if(!obstacles.isEmpty())
		for (Apple obstacle : obstacles) {
			obstacle.draw(canvas);
		}
	}

	public void update() {
		
		createApple();
		
		try {
			MainThread.sleep(300);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		checkAndHandleCollisions();
		spaceship.updateSnake();
	}

	public void checkAndHandleCollisions() {
		
		Apple obstacle = obstacles.get(0);
		if (ColisionDetector.isCollision(spaceship, obstacle)) {	
			obstacles.remove(obstacle);
			spaceship.setGrowSnake(true);
			render(thread.getCanvas());
		}
		if (ColisionDetector.isCollision(spaceship)) {
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
}
