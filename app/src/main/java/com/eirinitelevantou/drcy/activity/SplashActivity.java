package com.eirinitelevantou.drcy.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.eirinitelevantou.drcy.R;
import com.eirinitelevantou.drcy.util.PrefsHelper;

import java.util.Timer;
import java.util.TimerTask;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                if(PrefsHelper.isLoggedIn()) {
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                }else{
                    startActivity(new Intent(getApplicationContext(), LoginActivity.class));

                }
                finish();
            }
        }, 3000);
    }
}
