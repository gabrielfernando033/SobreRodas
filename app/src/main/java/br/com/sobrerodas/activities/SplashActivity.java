package br.com.sobrerodas.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;

import br.com.sobrerodas.R;

public class SplashActivity extends AppCompatActivity {

    private static int SPLASH_TIME_OUT = 4000;
    private SharedPreferences prefs = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        // Hide status bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        prefs = getSharedPreferences("com.sobrerodas", MODE_PRIVATE);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // Check if it's first run
                if (prefs.getBoolean("SobreRodasFirstRun", true))
                {
                    prefs.edit().putBoolean("SobreRodasFirstRun", false).commit();
                    Intent intent = new Intent(getApplicationContext(), TutorialActivity.class);
                    intent.putExtra("isFirstRun", true);
                    startActivity(intent);
                    finish();
                }
                else
                {
                    Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        }, SPLASH_TIME_OUT);
    }

}