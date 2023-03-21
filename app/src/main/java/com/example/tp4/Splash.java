package com.example.tp4;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class Splash extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent homeIntent = new Intent(Splash.this, MainActivity.class);
                startActivity(homeIntent);
                finish();
            }
        },2000);


        Animation fadeOutAnimation = AnimationUtils.loadAnimation(this, R.anim.fade_out_animation);
        TextView name = findViewById(R.id.name);
        name.setVisibility(View.VISIBLE);
        name.startAnimation(fadeOutAnimation);
    }
}