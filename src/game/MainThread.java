package game;

import android.graphics.Canvas;
import android.view.SurfaceHolder;

public class MainThread extends Thread {

	private SurfaceHolder surfaceHolder;
	private MainGamePanel gamePanel;
	private boolean running;
	private Canvas canvas;
	private boolean gameOver;
	
	public MainThread(SurfaceHolder surfaceHolder, MainGamePanel gamePanel) {
		super();
		gameOver = false;
		this.surfaceHolder = surfaceHolder;
		this.gamePanel = gamePanel;
	}

	@Override
	public void run() {
		while (running) {
			canvas = null;
				canvas = this.surfaceHolder.lockCanvas();
				synchronized (surfaceHolder) {
					if(canvas != null){
						if(running){
							gamePanel.update();
							gamePanel.render(canvas);
						}
					}
				}
				if (canvas != null && !gameOver) {
					surfaceHolder.unlockCanvasAndPost(canvas);
				}
				if (canvas != null && gameOver) {
					gamePanel.renderGameOver(canvas);
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
	
	public void setGameOver(boolean b){
		gameOver = true;
	}
}