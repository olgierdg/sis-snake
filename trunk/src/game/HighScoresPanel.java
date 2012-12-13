package game;

import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class HighScoresPanel extends SurfaceView implements SurfaceHolder.Callback {

	SharedPreferences settings;
	Thread thread;
	
	public HighScoresPanel(Context context) {
		super(context);
		getHolder().addCallback(this);
		setFocusable(true);
		// TODO Auto-generated constructor stub
		thread = new ScoresThread(getHolder(), this);
	}

	public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3) {
		// TODO Auto-generated method stub
		
	}

	public void surfaceCreated(SurfaceHolder arg0) {
		// TODO Auto-generated method stub
        thread.start();
	}

	public void surfaceDestroyed(SurfaceHolder arg0) {
		// TODO Auto-generated method stub
		
	}

	public void setPrefs(SharedPreferences settings){
		this.settings = settings;
	}
	
	public void render(Canvas canvas){
		java.util.Map<String, Integer> map = (Map<String, Integer>) settings.getAll();
		int count = map.size();
		SortedSet<Integer> vals = new TreeSet<Integer>(map.values());
		Integer[] arr = new Integer[count];
		vals.toArray(arr);

		canvas.drawColor(Color.rgb(0, 94, 0));
		Paint paint = new Paint();
		paint.setColor(Color.WHITE);

		paint.setTextSize(22);
		canvas.drawText("Highscores: ", 30, 25, paint);
		
		int i = 0;
		//vals.
		//for (Integer val : vals) { 
		//	i++;
		//	canvas.drawText(i+" : "+val, 30, 25+25*i, paint);
		   // do something
		//}
		while(i < count){
			if(arr[count-1-i] != null) canvas.drawText(i+" : "+arr[count-1-i], 30, 25+25*i, paint);
			i++;
			if(i == 11) break;
		}
	}
}
