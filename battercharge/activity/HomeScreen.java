package com.app.battercharge.activity;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.PowerManager;
import android.provider.Settings;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;

import com.app.battercharge.R;
import com.app.battercharge.notifications.AniNoti;
import com.app.battercharge.service.AnimSecondSer;
import com.app.battercharge.utils.AnimShaPrefsUtils;
import com.bumptech.glide.Glide;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

public class HomeScreen extends AppCompatActivity {
    @SuppressLint("StaticFieldLeak")
    public static AniNoti animNotification;
    public static BroadcastReceiver broadcastReceiver;

    TextView battrytext;
    CardView button_animation;
    CardView button_battery_info;
    CardView button_gallery;
    CardView button_setting;
    ImageView lottieBattery;
    PowerManager pms3eaw;

    int SELECT_PICTURE = 200;



    private void batteryOptimizationDialog() {
        new MaterialAlertDialogBuilder(this, R.style.AlertDialogTheme).setTitle("Background Running Permission").setMessage("Allow permission to run app in background").setCancelable(false).setPositiveButton("OK", (dialogInterface, i) -> {
            dialogInterface.dismiss();
            Intent intent = new Intent();
            intent.setAction("android.settings.REQUEST_IGNORE_BATTERY_OPTIMIZATIONS");
            intent.setData(Uri.parse("package:" + HomeScreen.this.getPackageName()));
            HomeScreen.this.startActivityForResult(intent, 2);
        }).setNegativeButton("Cancel", (dialogInterface, i) -> {
            dialogInterface.dismiss();
            HomeScreen.this.finish();
        }).show();
    }

    private void broadcastReceiver() {
        broadcastReceiver = new BroadcastReceiver() {
            public void onReceive(Context context, Intent intent) {

            }
        };

        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.intent.action.BATTERY_CHANGED");
        intentFilter.addAction("android.intent.action.BATTERY_LOW");
        intentFilter.addAction("android.intent.action.PHONE_STATE");
        intentFilter.addAction("android.intent.action.BOOT_COMPLETED");
        registerReceiver(broadcastReceiver, intentFilter);
    }

    public static int getBatteryPercentage(Context context) {
        Intent registerReceiver = context.registerReceiver(null, new IntentFilter("android.intent.action.BATTERY_CHANGED"));
        return (int) ((((float) registerReceiver.getIntExtra("level", -1)) / ((float) registerReceiver.getIntExtra("scale", -1))) * 100.0f);
    }

    @SuppressLint("WrongConstant")
    private boolean serviceRunning(Class<?> cls) {
        for (ActivityManager.RunningServiceInfo runningServiceInfo : ((ActivityManager) getSystemService("activity")).getRunningServices(Integer.MAX_VALUE)) {
            if (cls.getName().equals(runningServiceInfo.service.getClassName())) {
                return true;
            }
        }
        return false;
    }

    @SuppressLint("WrongConstant")
    private void setAnimation() {
        AnimShaPrefsUtils.setFirst(this, true);
        Glide.with(this).load(R.drawable.bimg).into(this.lottieBattery);
        animNotification = new AniNoti(this);
        registerReceiver(new BroadcastReceiver() {
            @SuppressLint("SetTextI18n")
            public void onReceive(Context context, Intent intent) {
                HomeScreen.this.battrytext.setText((((float) (intent.getIntExtra("level", -1) * 100)) / ((float) intent.getIntExtra("scale", -1))) + "%");
            }
        }, new IntentFilter("android.intent.action.BATTERY_CHANGED"));
        this.pms3eaw = (PowerManager) getSystemService("power");
        if (!Settings.canDrawOverlays(this)) {
            new MaterialAlertDialogBuilder(this, R.style.AlertDialogTheme).setTitle("Permission Required").setCancelable(false).setMessage(getResources().getString(R.string.overlay_permission)).setPositiveButton("OK", (dialogInterface, i) -> HomeScreen.this.startActivityForResult(new Intent("android.settings.action.MANAGE_OVERLAY_PERMISSION", Uri.parse("package:" + HomeScreen.this.getPackageName())), 1)).setNegativeButton("Cancel", (dialogInterface, i) -> HomeScreen.this.finish()).show();
        } else if (!this.pms3eaw.isIgnoringBatteryOptimizations(getPackageName())) {
            batteryOptimizationDialog();
        }
    }




    @SuppressLint({"IntentReset", "ObsoleteSdkInt"})
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.home_screen);
        this.button_animation = findViewById(R.id.button_animation);
        this.button_gallery = findViewById(R.id.button_gallery);
        this.button_battery_info = findViewById(R.id.button_battery_info);
        this.button_setting = findViewById(R.id.button_setting);
        this.lottieBattery = findViewById(R.id.lottieBattery);
        this.battrytext = findViewById(R.id.battrytext);
        getSupportActionBar().setTitle("Home");
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.black)));



        Intent intent = new Intent(this, AnimSecondSer.class);

        if (Build.VERSION.SDK_INT >= 24) {
            ContextCompat.startForegroundService(this, intent);
        } else {
            startService(intent);
        }
        setAnimation();
        broadcastReceiver();
        this.button_animation.setOnClickListener(view -> HomeScreen.this.startActivity(new Intent(HomeScreen.this, AnimationCategoryScreen.class)));
        this.button_gallery.setOnClickListener(view -> {
            Intent i = new Intent();
            i.setType("image/*");
            i.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(Intent.createChooser(i, "Select Picture"), SELECT_PICTURE);
        });


        this.button_battery_info.setOnClickListener(view -> HomeScreen.this.startActivity(new Intent(HomeScreen.this, BatteryInformationScreen.class)));
        this.button_setting.setOnClickListener(view -> HomeScreen.this.startActivity(new Intent(HomeScreen.this, SettingScreen.class)));
    }
    @SuppressLint("MissingSuperCall")
    public void onBackPressed() {
        super.onBackPressed();
        finishAffinity();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode,  Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode==RESULT_OK){
            if (requestCode == SELECT_PICTURE){
                Uri selectedImageUri = data.getData();
                Intent previewIntent = new Intent(this,PreviewScreen.class);
                previewIntent.putExtra("img_uri",selectedImageUri.toString());
                startActivity(previewIntent);
            }
        }
    }
}
