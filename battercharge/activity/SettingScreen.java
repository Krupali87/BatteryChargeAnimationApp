package com.app.battercharge.activity;

import android.annotation.SuppressLint;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.app.battercharge.R;
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.protocol.HTTP;

public class SettingScreen extends AppCompatActivity {

    LinearLayout linear_more_app;
    LinearLayout linear_privacy;
    LinearLayout linear_rate_us;
    LinearLayout linear_share;
    LinearLayout linear_feedback;


    @SuppressLint("WrongConstant")
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.setting_screen);
        getSupportActionBar().setTitle("Setting");
        getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.cardBgColor));
        this.linear_rate_us = findViewById(R.id.linear_rate_us);
        this.linear_share = findViewById(R.id.linear_share);
        this.linear_more_app = findViewById(R.id.linear_more_app);
        this.linear_privacy = findViewById(R.id.linear_privacy);
        this.linear_feedback = findViewById(R.id.linear_feedback);

        this.linear_rate_us.setOnClickListener(view -> {
            try {
                SettingScreen.this.startActivity(new Intent("android.intent.action.VIEW", Uri.parse("market://details?id=" + SettingScreen.this.getPackageName())));
            } catch (ActivityNotFoundException unused) {
                SettingScreen.this.startActivity(new Intent("android.intent.action.VIEW", Uri.parse("https://play.google.com/store/apps/details?id=" + SettingScreen.this.getPackageName())));
            }
        });
        this.linear_share.setOnClickListener(view -> {
            String string = SettingScreen.this.getString(R.string.app_name);
            String string2 = SettingScreen.this.getString(R.string.description);
            Intent intent = new Intent();
            intent.setAction("android.intent.action.SEND");
            intent.setType(HTTP.PLAIN_TEXT_TYPE);
            intent.putExtra("android.intent.extra.TEXT", string + " \n" + string2 + " \n\nhttp://play.google.com/store/apps/details?id=" + SettingScreen.this.getPackageName());
            SettingScreen.this.startActivity(intent);
        });
        this.linear_more_app.setOnClickListener(view -> {
            Intent intent = new Intent("android.intent.action.VIEW", Uri.parse("https://play.google.com/store/apps/developer?id=Coresoft+Tech"));
            intent.addFlags(268959744);
            SettingScreen.this.startActivity(intent);
        });
        this.linear_privacy.setOnClickListener(view -> SettingScreen.this.startActivity(new Intent("android.intent.action.VIEW", Uri.parse(SettingScreen.this.getString(R.string.app_main_privacy)))));
        this.linear_feedback.setOnClickListener(view -> {
            Intent intent = new Intent("android.intent.action.SEND");
            intent.setType("text/email");
            intent.putExtra("android.intent.extra.EMAIL", new String[]{SettingScreen.this.getString(R.string.feedback_mail)});
            intent.putExtra("android.intent.extra.SUBJECT", "Send Feedback");
            intent.putExtra("android.intent.extra.TEXT", "Dear, ");
            SettingScreen.this.startActivity(Intent.createChooser(intent, "Send mail..."));
        });

    }


}
