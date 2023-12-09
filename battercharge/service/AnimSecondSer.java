package com.app.battercharge.service;

import android.annotation.SuppressLint;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Resources;
import android.hardware.display.DisplayManager;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;
import android.view.Display;

import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;

import com.app.battercharge.R;
import com.app.battercharge.activity.BatteryAnimationView;
import com.app.battercharge.utils.SharedPreferenceUtils;


public class AnimSecondSer extends Service {

    private BroadcastReceiver animation = new BroadcastReceiver() {
        @SuppressLint("WrongConstant")
        public void onReceive(Context context, Intent intent) {
           boolean isLockedEnabled = SharedPreferenceUtils.getBooleanPreference(context,"lock",false);
            if (intent.getAction().equals("android.intent.action.ACTION_POWER_CONNECTED")) {
                Intent startIntent;
                if (isLockedEnabled){
                    if (!screenonoff(context)){
                        startIntent = new Intent(context, BatteryAnimationView.class);
                        startIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(startIntent);
                    }
                        else {
                        Log.e("@@245","sreen on");
                        }
                }
                else {
                    startIntent = new Intent(context, BatteryAnimationView.class);
                    startIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(startIntent);
                }

            }
        }
    };
private boolean screenonoff(Context context){
    DisplayManager dm = (DisplayManager)
            context.getSystemService(Context.DISPLAY_SERVICE);
    for (Display display : dm.getDisplays()) {
        if (display.getState() != Display.STATE_OFF) {
            return true;
        }
    }
    return false;
}
    public IBinder onBind(Intent intent) {
        return null;
    }

    public void onCreate() {
        super.onCreate();
        createNotificationChannel();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.intent.action.ACTION_POWER_CONNECTED");
        intentFilter.addAction("android.intent.action.ACTION_POWER_DISCONNECTED");
        intentFilter.addAction("android.intent.action.BATTERY_CHANGED");
        intentFilter.addAction("android.intent.action.ACTION_SCREEN_ON");
        intentFilter.addAction("android.intent.action.ACTION_SCREEN_OFF");
        registerReceiver(this.animation, intentFilter);
    }

    public int onStartCommand(Intent intent, int i, int i2) {
        startForeground(1, new NotificationCompat.Builder((Context) this, "CHANNEL").setContentText(getResources().getString(R.string.battery_charge_animation)).setSmallIcon(R.mipmap.logo).setColor(ContextCompat.getColor(this, R.color.white)).setShowWhen(false).build());
        return Service.START_STICKY;
    }

    @SuppressLint("WrongConstant")
    private void createNotificationChannel() {
        try {
            if (Build.VERSION.SDK_INT >= 26) {
                ((NotificationManager) getSystemService(NotificationManager.class)).createNotificationChannel(new NotificationChannel("CHANNEL", getResources().getString(R.string.battery_charge_animation), 3));
            }
        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
        }
    }

    public void onDestroy() {
        try {
            BroadcastReceiver broadcastReceiver = this.animation;
            if (broadcastReceiver != null) {
                unregisterReceiver(broadcastReceiver);
               this.animation = null;
            }
           
        } catch (IllegalArgumentException e) {
            // Log or handle the exception appropriately
            e.printStackTrace();
        }
        super.onDestroy();
    }

}

