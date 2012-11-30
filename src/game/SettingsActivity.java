package game;

import com.example.game.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.CheckBox;

/**
 * Activity dla ustawien gry. Layout jest w res/layout/activity_settings.
 * 
 * @author Olo
 *
 */
public class SettingsActivity extends Activity{

	//public final static String VIBRATE_MSG = "game.Settings.VIBRATE_MSG";
	private CheckBox checkBox;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {		
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.activity_settings);
        checkBox = (CheckBox) findViewById(R.id.checkBox1);
        checkBox.setChecked(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {   	
        getMenuInflater().inflate(R.menu.activity_mode_menu, menu);
        return true;
    }
    
    /**
     * Co sie dzieje po nacisnieciu przycisku OK - ustawienia sa zbierane do obiektu Intent i przesylane 
     * z powrotem do menu glownego.
     * 
     * @param view
     */
    public void ok(View view){
    	
    	Intent newIntent = new Intent();
    	
    	if(checkBox.isChecked())
    		newIntent.putExtra("Vibrate","yes");
    	else
    		newIntent.putExtra("Vibrate","no");
    	
        setResult(RESULT_OK, newIntent);
        
        finish();
    }
}
