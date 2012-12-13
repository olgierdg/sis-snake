package game;

import android.graphics.Canvas;
import android.view.SurfaceHolder;

public class ScoresThread extends Thread {

	private SurfaceHolder surfaceHolder;
	private HighScoresPanel gamePanel;
	private Canvas canvas;

	public ScoresThread(SurfaceHolder surfaceHolder, HighScoresPanel gamePanel) {
		super();
		this.surfaceHolder = surfaceHolder;
		this.gamePanel = gamePanel;
	}

	
	@Override
	public void run() {

			canvas = null;
				canvas = this.surfaceHolder.lockCanvas();
				synchronized (surfaceHolder) {
					if(canvas != null){
						gamePanel.render(canvas);
					}
				}
				surfaceHolder.unlockCanvasAndPost(canvas);
		
	}

}