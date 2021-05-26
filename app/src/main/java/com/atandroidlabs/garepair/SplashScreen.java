package com.atandroidlabs.garepair;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;

import com.atandroidlabs.garepair.FirstInteraction.InteractionActivity;

public class SplashScreen extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        CountDownTimer timer = new CountDownTimer(1500, 100) {
            @Override
            public void onTick(long l) {
                //Nothing
            }

            @Override
            public void onFinish() {
                Intent intent = new Intent(getApplicationContext(), InteractionActivity.class);
                startActivity(intent);
                finish();
            }
        };

        timer.start();
    }
}