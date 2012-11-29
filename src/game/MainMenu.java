package game;

import com.example.game.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;

public class MainMenu extends Activity{

	public final static String EXTRA_MESSAGE = "game.MainMenu.MESSAGE";
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(this.getLocalClassName(),"Siema main menu");
        setContentView(R.layout.activity_main_menu);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main_menu, menu);
        return true;
    }
	
	public void newGame(View view){
		Intent intent = new Intent(this, ModeMenu.class);
		String message = "newGame";
		intent.putExtra(EXTRA_MESSAGE, message);
	    startActivity(intent);
	}
	
	public void resume(View view){
		Intent intent = new Intent(this, ModeMenu.class);
		String message = "resumeGame";
		intent.putExtra(EXTRA_MESSAGE, message);
	    startActivity(intent);
	}
	
	public void exit(View view){
		moveTaskToBack(false);
	}
	
}
