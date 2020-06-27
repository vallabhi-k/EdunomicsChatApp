package com.example.edunomics;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class SplashActivity extends AppCompatActivity {
    Animation an;
    ImageView iv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        iv=findViewById(R.id.iv);
        an= AnimationUtils.loadAnimation(this,R.anim.a1);
        iv.startAnimation(an);

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(4000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Intent a=new Intent(SplashActivity.this,RegisterActivity.class);
                startActivity(a);
                finish();
            }
        }).start();


    }
}
