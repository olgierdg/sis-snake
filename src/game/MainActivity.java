package game;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.PowerManager;
import android.os.Vibrator;
import android.os.PowerManager.WakeLock;
import android.util.Log;
import android.view.Display;
import android.view.Window;
import android.view.WindowManager;

public class MainActivity extends Activity {

	MainGamePanel panel;
	
	private static String ICICLE_KEY = "sejw";
	Bundle savedInstanceState;
	
    private SensorManager mSensorManager;
    private Sensor mOrientation;
    private WindowManager mWindowManager;
    private Display mDisplay;
    private Vibrator vibrator;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		
		
		Intent intent = getIntent();
		String message = intent.getStringExtra(ModeMenu.EXTRA_MESSAGE);
		
		Log.d(this.getLocalClassName(),"Siema mainActivity onCreate, msg: "+message);

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		
		mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
		mWindowManager = (WindowManager) getSystemService(WINDOW_SERVICE);
        mDisplay = mWindowManager.getDefaultDisplay();
		
        vibrator =  (Vibrator) getSystemService(VIBRATOR_SERVICE);
        
	    panel = new MainGamePanel(this, mSensorManager, mDisplay, vibrator);
		setContentView(panel);
		
		SharedPreferences settings = getPreferences(MODE_PRIVATE);

		if(message.equalsIgnoreCase("resumeGame"))
			panel.restoreState(settings);
                  
	}
	
	@Override
    protected void onStop(){

		super.onStop();

		Log.d(this.getLocalClassName(), "Siema mainActivity onStop");
       
		// We need an Editor object to make preference changes.
		// All objects are from android.context.Context
		SharedPreferences settings = getPreferences(MODE_PRIVATE);
		SharedPreferences.Editor editor = settings.edit();
		//editor.putBoolean("silentMode", mSilentMode);

		panel.saveState(editor);
      
		// Commit the edits!
		editor.commit();
    }
	

	/*
	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		super.onRestoreInstanceState(savedInstanceState);
		
		Log.d(this.getLocalClassName(),"Siema mainActivity onRestore");
		
		if (savedInstanceState != null) {
            // We are being restored
			
			mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
			mWindowManager = (WindowManager) getSystemService(WINDOW_SERVICE);
	        mDisplay = mWindowManager.getDefaultDisplay();
			
		    panel = new MainGamePanel(this, mSensorManager, mDisplay);
			setContentView(panel);
			
            Bundle map = savedInstanceState.getBundle(ICICLE_KEY);
            if (map != null) {
                panel.restoreState(map);
            }
		}
	}
	*/
	/*
	@Override
    public void onSaveInstanceState(Bundle outState) {
        //Store the game state
		Log.d(this.getLocalClassName(),"Siema mainActivity onSave");
		
        outState.putBundle(ICICLE_KEY, panel.saveState());
        //super.onSaveInstanceState(outState);
    }
	*/
	/*
	@Override
	public void onBackPressed(){
		finish();
	}
	*/
}
