package com.app.battercharge.activity;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.BatteryManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.airbnb.lottie.LottieAnimationView;
import com.app.battercharge.R;
import com.app.battercharge.utils.SharedPreferenceUtils;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class BatteryAnimationView extends AppCompatActivity {
    LottieAnimationView animationView;
    int batLevel;
    public int counter = 0;
    ImageView galleyImage;
    ImageView imgBattery;
    public boolean isRunning = false;
    BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals("android.intent.action.ACTION_POWER_DISCONNECTED")) {
                BatteryAnimationView.this.finish();
            }
        }
    };
    MediaPlayer mediaPlayer2;
    RelativeLayout relative_main;
    TextView txtBatteryCount;
    TextView txt_date;
    TextView txt_time;

    private Handler handler = new Handler();

    boolean isLoop = false;

    private GestureDetector gestureDetector;

    private boolean singleClickAnimationRunning = false;

    @SuppressLint({"WrongConstant", "SimpleDateFormat", "SetTextI18n", "ClickableViewAccessibility"})
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        if (Build.VERSION.SDK_INT >= 28) {
            setShowWhenLocked(true);
        } else {
            getWindow().addFlags(6815744);
        }
        getWindow().getDecorView().setSystemUiVisibility(((getWindow().getDecorView().getSystemUiVisibility() ^ 2) ^ 4) ^ 4096);
        setContentView(R.layout.battery_animation_view);
        this.animationView = findViewById(R.id.animationView);
        this.txtBatteryCount = findViewById(R.id.txtBatteryCount);
        this.txt_time = findViewById(R.id.txt_time);
        this.txt_date = findViewById(R.id.txt_date);
        this.galleyImage = findViewById(R.id.galleyImage);
        this.relative_main = findViewById(R.id.relative_main);
        this.imgBattery = findViewById(R.id.imgBattery);

        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.intent.action.ACTION_POWER_DISCONNECTED");
        registerReceiver(this.mBroadcastReceiver, intentFilter);
        String stringPreference = SharedPreferenceUtils.getStringPreference(this, "anim_id", "neon_anim_4.json");
        String stringPreference2 = SharedPreferenceUtils.getStringPreference(this, "audio_id", "music1.mp3");
        boolean booleanPreference = SharedPreferenceUtils.getBooleanPreference(this, "sound", true);
        boolean booleanPreference2 = SharedPreferenceUtils.getBooleanPreference(this, "per", true);
        boolean booleanPreference3 = SharedPreferenceUtils.getBooleanPreference(this, "show", true);
        boolean booleanPreference4 = SharedPreferenceUtils.getBooleanPreference(this,"lock",false);
       int duration =  SharedPreferenceUtils.getIntegerPreference(this,"S_DURATION",0);
        final int integerPreference = SharedPreferenceUtils.getIntegerPreference(this, "closing", 0);
        final int selectedLoopTimeDuration = SharedPreferenceUtils.getSelectedLoopTimeDuration(this);
        SharedPreferenceUtils.getIntegerPreference(this, "custom", 0);
        String stringPreference3 = SharedPreferenceUtils.getStringPreference(this, "custom_uri", "");
        assert stringPreference3 != null;
        animationView.setRepeatCount(duration);

        if (!isLoop){
           int duinmil=duration*1000;
            handler = new Handler();
            Runnable r = new Runnable() {
                public void run() {
                    finishAffinity();
                   Log.d("@@247", String.valueOf(duration));


               }

           };
           handler.postDelayed(r, duinmil);
        }

        if (!stringPreference3.isEmpty()) {
            this.galleyImage.setVisibility(0);
            this.animationView.setVisibility(8);
           this.galleyImage.setImageURI(Uri.parse(stringPreference3));
        } else {
            this.galleyImage.setVisibility(8);
            this.animationView.setVisibility(0);
            this.animationView.setAnimation(stringPreference);
        }
        this.txt_time.setText(new SimpleDateFormat("hh:mm a").format(new Date()).toUpperCase());
        this.txt_date.setText(DateFormat.format("d MMMM, yyyy ", new Date().getTime()));
        try {
            this.batLevel = ((BatteryManager) getSystemService("batterymanager")).getIntProperty(4);
            this.txtBatteryCount.setText(this.batLevel + " %");
        } catch (Exception ignored) {
        }
        if (booleanPreference2) {
            this.imgBattery.setVisibility(0);
            this.txtBatteryCount.setVisibility(0);
        } else {
            this.imgBattery.setVisibility(8);
            this.txtBatteryCount.setVisibility(8);
        }
        if (!booleanPreference3) {
            this.galleyImage.setVisibility(8);
            this.animationView.setVisibility(8);
        }



        this.relative_main.setOnClickListener(view -> {

            if (singleClickAnimationRunning) {
                // Double click detected while animation is running, show toast
                Toast.makeText(BatteryAnimationView.this, "Double click detected", Toast.LENGTH_SHORT).show();
            } else {
                // Single click detected, proceed with normal single-click behavior
                if (integerPreference == 1) {
                    BatteryAnimationView.this.finish();
                    return;
                }
                if (BatteryAnimationView.this.isRunning && BatteryAnimationView.this.counter == 1) {
                    BatteryAnimationView.this.finish();
                }

                BatteryAnimationView.this.counter++;

                if (!BatteryAnimationView.this.isRunning) {
                    BatteryAnimationView.this.isRunning = true;
                    new Thread(new Runnable() {
                        public void run() {
                            try {
                                Thread.sleep(500);
                                BatteryAnimationView.this.isRunning = false;
                                BatteryAnimationView.this.counter = 0;
                                singleClickAnimationRunning = false;
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }).start();
                }
            }
        });

        if (booleanPreference) {
            try {
                AssetFileDescriptor openFd = getAssets().openFd(stringPreference2);
                MediaPlayer mediaPlayer = new MediaPlayer();
                this.mediaPlayer2 = mediaPlayer;
                this.mediaPlayer2.setDataSource(openFd.getFileDescriptor(), openFd.getStartOffset(), openFd.getLength());
                this.mediaPlayer2.prepare();
                this.mediaPlayer2.start();

            } catch (IOException ignored) {
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        gestureDetector.onTouchEvent(event);
        return super.onTouchEvent(event);
    }


    @Override
    protected void onStop() {
        super.onStop();
    }

    public void onDestroy() {
        super.onDestroy();
        unregisterReceiver(this.mBroadcastReceiver);
        MediaPlayer mediaPlayer = this.mediaPlayer2;
        if (mediaPlayer != null) {
            mediaPlayer.stop();
        }
    }
}
