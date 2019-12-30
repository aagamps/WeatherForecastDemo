package com.example.weatherforcastapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.weatherforcastapp.R;


public class SplashActivity extends AppCompatActivity {

    private static final int TIME_OUT = 3000;

    /* Entry point for splash activity */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            getIntent();
            requestWindowFeature(Window.FEATURE_NO_TITLE);
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
            setContentView(R.layout.activity_splash);
            ImageView imgLauncherLogo = findViewById(R.id.imgLauncherLogo);
            Animation animationAlpha = AnimationUtils.loadAnimation(this, R.anim.alpha);
            imgLauncherLogo.setAnimation(animationAlpha);
            Animation animationTranslate = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.translat);
            imgLauncherLogo.startAnimation(animationTranslate);

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    try {
                        Intent i = new Intent(SplashActivity.this, MainActivity.class);
                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(i);
                        finish();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }, TIME_OUT);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
