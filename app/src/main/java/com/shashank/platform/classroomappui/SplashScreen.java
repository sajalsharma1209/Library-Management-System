package com.shashank.platform.classroomappui;

import android.content.Intent;

import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import android.support.v7.app.AppCompatActivity;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class SplashScreen extends AppCompatActivity {

    Animation top, middle, bottom;
    ImageView imageView;
    TextView title, bottomtitle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash_screen);

        imageView = findViewById(R.id.logo);
        title = findViewById(R.id.title);


        top = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.top);
        middle = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.middle);
//        bottom = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.bottom);

        imageView.setAnimation(top);
        title.setAnimation(middle);
//        bottomtitle.setAnimation(bottom);
        Thread timerThread = new Thread() {
            public void run() {
                try {
                    sleep(3000);
                    Intent i = new Intent(SplashScreen.this, LoginScreen.class);
                    startActivity(i);
                    finish();
                } catch (Exception e) {
                }
            }
        };
        timerThread.start();
    }
}
