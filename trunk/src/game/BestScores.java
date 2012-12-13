package game;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;

public class BestScores extends Activity {
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        HighScoresPanel panel = new HighScoresPanel(this);
        setContentView(panel);
        SharedPreferences settings = getSharedPreferences("SCORES", MODE_PRIVATE);
        panel.setPrefs(settings);
    }
}
