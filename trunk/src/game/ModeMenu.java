package game;

import com.example.game.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;

/**
 * Activity dla menu wyboru trybu gry. Layout jest w res/layout/activity_mode_menu.
 * 
 * @author Olo
 *
 */
public class ModeMenu extends Activity{

	public final static String EXTRA_MESSAGE = "game.ModeMenu.MESSAGE";
	private String message;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
		
        super.onCreate(savedInstanceState);
        
        Intent intent = getIntent();
		message = intent.getStringExtra(MainMenu.EXTRA_MESSAGE);
		
        Log.d(this.getLocalClassName(),"Siema mode menu, msg: "+message);
        setContentView(R.layout.activity_mode_menu);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
    	
        getMenuInflater().inflate(R.menu.activity_mode_menu, menu);
        return true;
    }
	
    /**
     * To sie wykonuje jak sie kliknie button z napisem 'Normal'. Tak samo trzeba bedzie pododawac buttonki 
     * dla innych trybow.
     * 
     * @param view
     */
	public void newNormalGame(View view){
		
		Intent intent = new Intent(this, MainActivity.class);
		intent.putExtra(EXTRA_MESSAGE, message);
	    startActivity(intent);
	}
	
	/**
	 * To co sie dzieje na buttonie back
	 * 
	 * @param view
	 */
	public void backToMainMenu(View view){
		
		Intent intent = new Intent(this, MainMenu.class);
	    startActivity(intent);
	}
}
