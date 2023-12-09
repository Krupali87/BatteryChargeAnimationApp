package com.app.battercharge.notifications;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import androidx.core.app.NotificationCompat;
import com.app.battercharge.R;
import com.app.battercharge.activity.HomeScreen;

public class AniNoti extends Notification {
    public NotificationCompat.Builder builder;
    Context context;
    String kmrt67 = "channel_name_battery";
    NotificationManager tyujyt7i87;

    @SuppressLint("WrongConstant")
    public AniNoti(Context context2) {
        this.context = context2;
        Intent putExtra = new Intent(context2, HomeScreen.class).putExtra("optimize", "yes");
        putExtra.setFlags(268468224);
        this.builder = new NotificationCompat.Builder(context2, this.kmrt67).setSmallIcon(R.drawable.bimg).setContentTitle("Charging Animation").setContentText("App is running").setStyle(new NotificationCompat.BigTextStyle()).setAutoCancel(true).setPriority(1).setContentIntent(PendingIntent.getActivity(context2, 0, putExtra, 201326592));
        NotificationManager notificationManager = (NotificationManager) context2.getSystemService("notification");
        this.tyujyt7i87 = notificationManager;
        if (Build.VERSION.SDK_INT >= 26) {
            notificationManager.createNotificationChannel(new NotificationChannel(this.kmrt67, "Channel Name", 4));
        }
    }

    public void updateStatus(String str) {
        this.builder.setContentTitle(str);
        Notification build = this.builder.build();
        build.flags |= 32;
        this.tyujyt7i87.notify(1, build);
    }
}
