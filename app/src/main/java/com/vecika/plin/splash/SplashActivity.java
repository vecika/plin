package com.vecika.plin.splash;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.vecika.plin.R;
import com.vecika.plin.login.LoginActivity;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);



        new Handler().postDelayed(new Runnable() {
            public void run() {

                    startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                    finish();


            }
        }, 1 * 1500);

    }









}
