package game;

import com.example.game.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;

public class MainMenu extends Activity{

	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main_menu, menu);
        return true;
    }
	
	public void newGame(View view){
		Intent intent = new Intent(this, MainActivity.class);
	    startActivity(intent);
	}
	
	public void exit(View view){
		moveTaskToBack(false);
	}
	
	@Override
	public void onBackPressed() {
		moveTaskToBack(false);
	    // implement your override logic here
	   return;
	}
}
