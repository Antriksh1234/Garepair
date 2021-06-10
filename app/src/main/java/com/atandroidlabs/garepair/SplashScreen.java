package com.atandroidlabs.garepair;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;

import com.atandroidlabs.garepair.FirstInteraction.InteractionActivity;

public class SplashScreen extends Activity {

    SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        sp = getSharedPreferences("com.atandroidlabs.garepair", Context.MODE_PRIVATE);
        boolean firstTime = sp.getBoolean("firstTime", true);
        CountDownTimer timer = new CountDownTimer(1500, 100) {
            @Override
            public void onTick(long l) {
                //Nothing
            }

            @Override
            public void onFinish() {
                Intent intent;
                if (firstTime) {
                    intent = new Intent(getApplicationContext(), InteractionActivity.class);
                } else {
                    intent = new Intent(getApplicationContext(), MainActivity.class);
                }

                startActivity(intent);
                finish();
            }
        };

        timer.start();
    }
}