package game;

import com.example.game.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;

/**
 * Activity menu glownego.
 * 
 * @author Olo
 *
 */
public class MainMenu extends Activity{

	public final static String NEW_RESUME_MSG = "game.MainMenu.NEW_RESUME_MSG";
	public final static String VIBRATE_MSG = "game.MainMenu.VIBRATE_MSG";
	public final static String SOUND_MSG = "game.MainMenu.SOUND_MSG";
	public final static String MUSIC_MSG = "game.MainMenu.MUSIC_MSG";
	
	private boolean vibrate;
	private boolean sound;
	private boolean music;
	
	@Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        
        //Log.d(this.getLocalClassName(), "Siema main menu");
        vibrate = true;
        sound = true;
        music = true;
        setContentView(R.layout.activity_main_menu);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.activity_main_menu, menu);
        return true;
    }
	
    /**
     * Przycisk new game.
     * 
     * @param view
     */
	public void newGame(View view){
		
		Intent intent = new Intent(this, ModeMenuActivity.class);
		intent.putExtra(NEW_RESUME_MSG, "newGame");
		
		if(vibrate) intent.putExtra(VIBRATE_MSG, "vibrateOn");
		else intent.putExtra(VIBRATE_MSG, "vibrateOff");
		
		if(sound) intent.putExtra(SOUND_MSG, "soundOn");
		else intent.putExtra(SOUND_MSG, "soundOff");
		
		if(music) intent.putExtra(MUSIC_MSG, "musicOn");
		else intent.putExtra(MUSIC_MSG, "musicOff");
		
	    startActivity(intent);
	}
	
	/**
	 * Przycisk resume.
	 * 
	 * @param view
	 */
	public void resume(View view){
		
		Intent intent = new Intent(this, ModeMenuActivity.class);
		intent.putExtra(NEW_RESUME_MSG, "resumeGame");
		
		if(vibrate) intent.putExtra(VIBRATE_MSG, "vibrateOn");
		else intent.putExtra(VIBRATE_MSG, "vibrateOff");
		
	    startActivity(intent);
	}
	
	/**
	 * Ustawienia
	 * 
	 * @param view
	 */
	public void settings(View view){
		Intent intent = new Intent(this, SettingsActivity.class);
		startActivityForResult(intent, 1);
	}
	
	protected void onActivityResult(int requestCode, int resultCode, Intent data){
	      switch(requestCode){
	      case 1:
	         if (resultCode == RESULT_OK){
	            String vibrateOption = data.getStringExtra("Vibrate");
	            if(vibrateOption.equalsIgnoreCase("yes"))
	            	vibrate = true;
	            if(vibrateOption.equalsIgnoreCase("no"))
	            	vibrate = false;
	            
	            String soundOption = data.getStringExtra("Sound");
	            if(soundOption.equalsIgnoreCase("yes"))
	            	sound = true;
	            if(soundOption.equalsIgnoreCase("no"))
	            	sound = false;
	            
	            String musicOption = data.getStringExtra("Music");
	            if(musicOption.equalsIgnoreCase("yes"))
	            	music = true;
	            if(musicOption.equalsIgnoreCase("no"))
	            	music = false;
	            break;
	         }
	      }
	   }
}
