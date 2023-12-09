package com.app.battercharge.activity;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.airbnb.lottie.LottieAnimationView;
import com.app.battercharge.R;
import com.app.battercharge.utils.AppLocalDB;


public class SplashScreen extends AppCompatActivity {

    private Button btnstarted;
    public static AppLocalDB appDB;

    private LottieAnimationView animationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);
        appDB = new AppLocalDB(this);
        this.btnstarted = findViewById(R.id.btnstarted);
        this.animationView = findViewById(R.id.animationView);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {


                Animation blinkAnimation = new AlphaAnimation(0.0f, 1.0f);   // From fully visible to fully transparent

                blinkAnimation.setDuration(500); // Blinking duration in milliseconds
                blinkAnimation.setRepeatMode(Animation.REVERSE);
                blinkAnimation.setRepeatCount(Animation.INFINITE);


                btnstarted.startAnimation(blinkAnimation);


                btnstarted.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        nextScreen();

                    }
                });


            }
        }, 3000);

    }

    public final void nextScreen() {
        Intent intent = new Intent(this, HomeScreen.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(intent);
        overridePendingTransition(0, 0);
        finish();
    }
}