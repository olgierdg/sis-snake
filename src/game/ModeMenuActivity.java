package game;

import com.example.game.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;

/**
 * Activity dla menu wyboru trybu gry. Layout jest w res/layout/activity_mode_menu.
 * Te wszystkie Stringi, wiadomosci i w ogole sluza do przekazania dalej ustawien z menu glownego, 
 * jak na razie dokladnie ustawienia wibracji i wyboru nowa gra/kontynuuj.
 * 
 * @author Olo
 *
 */
public class ModeMenuActivity extends Activity{

	public final static String NEW_RESUME_MSG = "game.ModeMenu.NEW_RESUME_MSG";
	public final static String VIBRATE_MSG = "game.ModeMenu.VIBRATE_MSG";
	public final static String SOUND_MSG = "game.ModeMenu.SOUND_MSG";
	public final static String MUSIC_MSG = "game.ModeMenu.MUSIC_MSG";
	public final static String GAME_TYPE_MSG = "game.MainMenu.GAME_TYPE_MSG";
	
	private String message;
	private String vibrate;
	private String sound;
	private String music;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
		
        super.onCreate(savedInstanceState);
        
        Intent intent = getIntent();
		message = intent.getStringExtra(MainMenu.NEW_RESUME_MSG);
		vibrate = intent.getStringExtra(MainMenu.VIBRATE_MSG);
		sound = intent.getStringExtra(MainMenu.SOUND_MSG);
		music = intent.getStringExtra(MainMenu.MUSIC_MSG);
		
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
		intent.putExtra("NEW_RESUME_MSG", message);
		intent.putExtra("VIBRATE_MSG", vibrate);
		intent.putExtra("SOUND_MSG", sound);
		intent.putExtra("MUSIC_MSG", music);
		intent.putExtra("GAME_TYPE_MSG", "normal");
	    startActivity(intent);
	}
	
	public void newWallsGame(View view){
		
		Intent intent = new Intent(this, MainActivity.class);
		intent.putExtra("NEW_RESUME_MSG", message);
		intent.putExtra("VIBRATE_MSG", vibrate);
		intent.putExtra("SOUND_MSG", sound);
		intent.putExtra("MUSIC_MSG", music);
		intent.putExtra("GAME_TYPE_MSG", "walls");
	    startActivity(intent);
	}
	
	public void newPortalsGame(View view){
		
		Intent intent = new Intent(this, PortalsMenu.class);
		intent.putExtra(NEW_RESUME_MSG, message);
		intent.putExtra(VIBRATE_MSG, vibrate);
		intent.putExtra(SOUND_MSG, sound);
		intent.putExtra(MUSIC_MSG, music);
		intent.putExtra(GAME_TYPE_MSG, "portals");
	    startActivity(intent);
	}
}
