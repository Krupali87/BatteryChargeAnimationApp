package com.app.battercharge.activity;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;
import com.app.battercharge.R;

public class BatteryInformationScreen extends AppCompatActivity {

    public String batteryLevel;
    public String batteryPowerSource;
    public String batteryStatus;
    public String batteryTemperature;
    public String batteryType;
    public String batteryVoltage;
    private int deviceHealth;

    boolean isOpenScreen = false;
    private final BroadcastReceiver mBatInfoReceiver = new BroadcastReceiver() {
        @SuppressLint("SetTextI18n")
        public void onReceive(Context context, Intent intent) {
            int intExtra = intent.getIntExtra("plugged", -1);
            float intExtra2 = ((float) intent.getIntExtra("temperature", 0)) / 10.0f;
            int intExtra3 = intent.getIntExtra(NotificationCompat.CATEGORY_STATUS, -1);
            BatteryInformationScreen.this.batteryLevel = ((((float) intent.getIntExtra("level", -1)) / ((float) intent.getIntExtra("scale", -1))) * 100.0f) + " %";
            BatteryInformationScreen.this.txtBatteryLevel.setText(BatteryInformationScreen.this.batteryLevel);
            BatteryInformationScreen.this.batteryType = intent.getStringExtra("technology") + " Battery";
            BatteryInformationScreen.this.txtBatteryTitle.setText(BatteryInformationScreen.this.batteryType);
            BatteryInformationScreen.this.txtBatteryType.setText(BatteryInformationScreen.this.batteryType);
            BatteryInformationScreen batteryInformationScreen = BatteryInformationScreen.this;
            batteryInformationScreen.batteryPowerSource = batteryInformationScreen.powerSource(intExtra);
            BatteryInformationScreen.this.batteryTemperature = intExtra2 + " °C / " + ((intExtra2) + 32.0f) + " °F";
            BatteryInformationScreen.this.txtBatteryTemperature.setText(BatteryInformationScreen.this.batteryTemperature);
            BatteryInformationScreen.this.batteryVoltage = ((float) (((double) intent.getIntExtra("voltage", 0)) * 0.001d)) + " v";
            BatteryInformationScreen.this.txtVoltage.setText(BatteryInformationScreen.this.batteryVoltage);
            BatteryInformationScreen batteryInformationScreen2 = BatteryInformationScreen.this;
            batteryInformationScreen2.batteryStatus = batteryInformationScreen2.getBatteryStatus(intExtra3);
            BatteryInformationScreen.this.txtStatus.setText(BatteryInformationScreen.this.batteryStatus);
            BatteryInformationScreen batteryInformationScreen3 = BatteryInformationScreen.this;
            String sb = batteryInformationScreen3.getBatteryCapacity(batteryInformationScreen3) / 1000 + " mAh";
            BatteryInformationScreen.this.txtBatteryCapacity.setText(sb);
            BatteryInformationScreen.this.setDeviceHealth(intent.getIntExtra("health", 0));
            if (BatteryInformationScreen.this.getDeviceHealth() == 7) {
                BatteryInformationScreen.this.txtPowerConnector.setText("Cold");
            }
            if (BatteryInformationScreen.this.getDeviceHealth() == 4) {
                BatteryInformationScreen.this.txtPowerConnector.setText("Dead");
            }
            if (BatteryInformationScreen.this.getDeviceHealth() == 2) {
                BatteryInformationScreen.this.txtPowerConnector.setText("Good");
            }
            if (BatteryInformationScreen.this.getDeviceHealth() == 3) {
                BatteryInformationScreen.this.txtPowerConnector.setText("OverHeat");
            }
            if (BatteryInformationScreen.this.getDeviceHealth() == 5) {
                BatteryInformationScreen.this.txtPowerConnector.setText("Over voltage");
            }
            if (BatteryInformationScreen.this.getDeviceHealth() == 1) {
                BatteryInformationScreen.this.txtPowerConnector.setText("Unknown");
            }
            if (BatteryInformationScreen.this.getDeviceHealth() == 6) {
                BatteryInformationScreen.this.txtPowerConnector.setText("Unspecified Failure");
            }
        }
    };

    TextView txtBatteryCapacity;
    TextView txtBatteryLevel;
    TextView txtBatteryTemperature;
    TextView txtBatteryTitle;
    TextView txtBatteryType;
    TextView txtPowerConnector;
    TextView txtStatus;
    TextView txtToolBar;
    TextView txtVoltage;

    public final String getBatteryStatus(int i) {
        return i != 1 ? i != 2 ? i != 3 ? i != 4 ? i != 5 ? "Unknown" : "Full" : "Not charging" : "Discharging" : "Charging" : "Unknown";
    }

    public final String powerSource(int i) {
        return i != 1 ? i != 2 ? i != 4 ? "Battery" : "Wireless" : "USB" : "AC";
    }


    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.battery_information_screen);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        this.isOpenScreen = true;
        getSupportActionBar().setTitle("Battery Information");
        getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.cardBgColor));
        this.txtBatteryLevel = findViewById(R.id.txtBatteryLevel);
        this.txtBatteryTitle = findViewById(R.id.txtBatteryTitle);
        this.txtBatteryType = findViewById(R.id.txtBatteryType);
        this.txtBatteryTemperature = findViewById(R.id.txtBatteryTemperature);
        this.txtVoltage = findViewById(R.id.txtVoltage);
        this.txtStatus = findViewById(R.id.txtStatus);
        this.txtBatteryCapacity = findViewById(R.id.txtBatteryCapacity);
        this.txtPowerConnector = findViewById(R.id.txtPowerConnector);
        registerReceiver(this.mBatInfoReceiver, new IntentFilter("android.intent.action.BATTERY_CHANGED"));


    }



    /* access modifiers changed from: protected */
    public void onDestroy() {
        super.onDestroy();
        unregisterReceiver(this.mBatInfoReceiver);
    }

    public final int getDeviceHealth() {
        return this.deviceHealth;
    }

    public final void setDeviceHealth(int i) {
        this.deviceHealth = i;
    }

    public final long getBatteryCapacity(Context context) {
        @SuppressLint("WrongConstant") BatteryManager batteryManager = (BatteryManager) context.getSystemService("batterymanager");
        int intProperty = batteryManager.getIntProperty(1);
        int intProperty2 = batteryManager.getIntProperty(4);
        if (intProperty == Integer.MIN_VALUE || intProperty2 == Integer.MIN_VALUE) {
            return 0;
        }
        return (intProperty / intProperty2) * 100L;
    }



}
