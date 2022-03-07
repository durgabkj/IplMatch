package com.ottego.iplmatch;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class SplashScreen extends AppCompatActivity {
        Context context;
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_splash_screen);

            context = SplashScreen.this;
            start();

        }

        private void start() {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                        startActivity(new Intent(context, MainActivity.class));

                    finish();
                }

            }, 3000);
        }

    }