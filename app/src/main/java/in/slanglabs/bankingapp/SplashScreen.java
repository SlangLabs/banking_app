package in.slanglabs.bankingapp;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.akanshisrivastava.bankingapp.R;

import in.slanglabs.platform.SlangBuddy;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        try {
            SlangBuddy.getBuiltinUI().hide();
        } catch (Exception e) {
            Log.d("SplashScreen", "SlangBuddy not yet initialized");
        }
        final Handler handler1 = new Handler();
        handler1.postDelayed(new Runnable() {
            @Override
            public void run() {
                //Do something after 100ms

                Intent intent = new Intent(SplashScreen.this, MainActivity.class);
                startActivity(intent);
                finish();

                //pulsator.stop();
            }
        }, 1500);

    }
}
