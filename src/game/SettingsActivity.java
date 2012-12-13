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

	private CheckBox checkBoxVibrate;
	private CheckBox checkBoxSound;
	private CheckBox checkBoxMusic;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {		
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.activity_settings);
        checkBoxVibrate = (CheckBox) findViewById(R.id.checkBoxVibrate);
        checkBoxVibrate.setChecked(true);
        checkBoxSound = (CheckBox) findViewById(R.id.checkBoxSound);
        checkBoxSound.setChecked(true);
        checkBoxMusic = (CheckBox) findViewById(R.id.checkBoxMusic);
        checkBoxMusic.setChecked(true);
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
    	
    	if(checkBoxVibrate.isChecked())
    		newIntent.putExtra("Vibrate","yes");
    	else
    		newIntent.putExtra("Vibrate","no");
    	
    	if(checkBoxSound.isChecked())
    		newIntent.putExtra("Sound","yes");
    	else
    		newIntent.putExtra("Sound","no");
    	
    	if(checkBoxMusic.isChecked())
    		newIntent.putExtra("Music","yes");
    	else
    		newIntent.putExtra("Music","no");
    	
        setResult(RESULT_OK, newIntent);
        
        finish();
    }
}
