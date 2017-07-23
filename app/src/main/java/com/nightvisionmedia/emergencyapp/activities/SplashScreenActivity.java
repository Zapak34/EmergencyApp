package com.nightvisionmedia.emergencyapp.activities;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.nightvisionmedia.emergencyapp.R;

public class SplashScreenActivity extends AppCompatActivity {
    ImageView splashScreenLogo;
    Animation logoAnim;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        splashScreenLogo = (ImageView) findViewById(R.id.ivSplashLogo);
        logoAnim = AnimationUtils.loadAnimation(this, R.anim.splashanim);
        splashScreenLogo.startAnimation(logoAnim);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                logoAnim.cancel();
                startActivity(new Intent(SplashScreenActivity.this,LoginScreenActivity.class));
                android.os.Process.killProcess(android.os.Process.myPid());
            }
        },5500);
    }
}
