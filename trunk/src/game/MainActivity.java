package game;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Vibrator;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

/**
 * Glowne activity, odpowiedzialne za gre. Tworzy pewne obiekty dla tej gry, przede wszystkim plansze, odpowiada za 
 * przekazanie ustawien do gry oraz za obsluge zapisu/odczytu stanu gry (getPreferences() jest metoda klasy Activity).
 * 
 * @author Olo
 *
 */
public class MainActivity extends Activity {

	private MainGamePanel panel;
	
    //private SensorManager mSensorManager;
    //private Sensor mOrientation;
    //private WindowManager mWindowManager;
    //private Display mDisplay;
    private Vibrator vibrator;
    
    private static String ICICLE_KEY = "snake-view";
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		Intent intent = getIntent();
		String message = intent.getStringExtra(ModeMenuActivity.NEW_RESUME_MSG);
		String vibrate = intent.getStringExtra(ModeMenuActivity.VIBRATE_MSG);
		String gameType  = intent.getStringExtra(ModeMenuActivity.GAME_TYPE_MSG);
		
		Log.d(this.getLocalClassName(),"Siema mainActivity onCreate, msg: "+vibrate);

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		
		//mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
		//mWindowManager = (WindowManager) getSystemService(WINDOW_SERVICE);
        //mDisplay = mWindowManager.getDefaultDisplay();
		
        vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        
	    //panel = new MainGamePanel(this, mSensorManager, mDisplay, vibrator);
        panel = new MainGamePanel(this);
        panel.setVibrator(vibrator);
        panel.setGameType(gameType);
		setContentView(panel);
		
		SharedPreferences settings = getPreferences(MODE_PRIVATE);

        if(vibrate.equalsIgnoreCase("vibrateOn"))
        	panel.setVibrate(true);
        if(vibrate.equalsIgnoreCase("vibrateOff"))
        	panel.setVibrate(false);
        
        if (savedInstanceState == null) {
        	if(message.equalsIgnoreCase("resumeGame"))
    			panel.restoreState(settings);
        }else{

            Bundle map = savedInstanceState.getBundle(ICICLE_KEY);
            if (map != null) {
            	panel.restoreState(map);
            }
        }
	}
	
	@Override
    protected void onStop(){

		super.onStop();

		Log.d(this.getLocalClassName(), "Siema mainActivity onStop");
       
		SharedPreferences settings = getPreferences(MODE_PRIVATE);
		SharedPreferences.Editor editor = settings.edit();

		panel.saveState(editor);
      
		editor.commit();
    }
	
	@Override
    public void onSaveInstanceState(Bundle outState) {
        //Store the game state
        outState.putBundle(ICICLE_KEY, panel.saveState());
    }
}
