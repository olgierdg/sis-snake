package game;

import com.example.game.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;

/**
 * 
 * @author Olo
 *
 */
public class PortalsMenu extends Activity{

	public final static String NEW_RESUME_MSG = "game.PortalsMenu.NEW_RESUME_MSG";
	public final static String VIBRATE_MSG = "game.PortalsMenu.VIBRATE_MSG";
	public final static String GAME_TYPE_MSG = "game.PortalsMenu.GAME_TYPE_MSG";
	public final static String LEVEL_MSG = "game.PortalsMenu.LEVEL_MSG";
	
	private String message;
	private String vibrate;
	private String gameType;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
		
        super.onCreate(savedInstanceState);
        
        Intent intent = getIntent();
		message = intent.getStringExtra(ModeMenuActivity.NEW_RESUME_MSG);
		vibrate = intent.getStringExtra(ModeMenuActivity.VIBRATE_MSG);
		gameType = intent.getStringExtra(ModeMenuActivity.GAME_TYPE_MSG);
		
        Log.d(this.getLocalClassName(),"Siema portals menu, msg: "+vibrate);
        setContentView(R.layout.activity_portals_menu);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
    	
        getMenuInflater().inflate(R.menu.activity_portals_menu, menu);
        return true;
    }

	public void newPortalsGame(View view, int level){
		
		Intent intent = new Intent(this, MainActivity.class);
		intent.putExtra(NEW_RESUME_MSG, message);
		intent.putExtra(VIBRATE_MSG, vibrate);
		intent.putExtra(GAME_TYPE_MSG, gameType);
		intent.putExtra(LEVEL_MSG, level);
	    startActivity(intent);
	}
}
