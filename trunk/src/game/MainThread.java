package game;

import android.graphics.Canvas;
import android.view.SurfaceHolder;

public class MainThread extends Thread {

	private SurfaceHolder surfaceHolder;
	private MainGamePanel gamePanel;
	private boolean running;
	private Canvas canvas;

	public MainThread(SurfaceHolder surfaceHolder, MainGamePanel gamePanel) {
		super();
		this.surfaceHolder = surfaceHolder;
		this.gamePanel = gamePanel;
	}

	/*
	private Object mPauseLock = new Object();  
	private boolean mPaused;

	public void onPause() {
	    synchronized (mPauseLock) {
	        mPaused = true;
	    }
	}

	public void onResume() {
	    synchronized (mPauseLock) {
	        mPaused = false;
	        this.setRunning(true);
	        mPauseLock.notifyAll();
	    }
	}
	*/
	
	@Override
	public void run() {
		while (running) {
			canvas = null;
				canvas = this.surfaceHolder.lockCanvas();
				synchronized (surfaceHolder) {
					if(canvas != null){
						gamePanel.update();
						if(running)
							gamePanel.render(canvas);
					}
				}
				/*
				synchronized (mPauseLock) {
				    while (mPaused) {
				        try {
				            mPauseLock.wait();
				        } catch (InterruptedException e) {
				        }
				    }
				}
				*/
				if (canvas != null) {
					surfaceHolder.unlockCanvasAndPost(canvas);
				}
		}
	}

	public Canvas getCanvas(){
		return canvas;
	}
	
	public void setRunning(boolean running) {
		this.running = running;
	}
}