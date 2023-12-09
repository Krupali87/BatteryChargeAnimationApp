package com.app.battercharge.activity;

import android.animation.Animator;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.AudioManager;
import android.media.ExifInterface;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.text.format.DateFormat;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.ToggleButton;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;

import com.airbnb.lottie.LottieAnimationView;
import com.app.battercharge.R;
import com.app.battercharge.utils.SharedPreferenceUtils;
import com.google.firebase.crashlytics.buildtools.ndk.internal.elf.EMachine;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class PreviewScreen extends AppCompatActivity {
    LottieAnimationView animationView;
    LottieAnimationView animationView1;
    ImageView backArrow;


    RelativeLayout btnApplied;
    RelativeLayout btnPreview;
    ImageView btnSetting;
    Dialog f5575yaj5;
    boolean f557asd2v;
    Uri f557ghn74x;
    boolean f55a70t;
    Uri f55mm73w;
    boolean f55u;
    int f993m;
    String gh74n;
    RelativeLayout hidePreviewLayout;
    MediaPlayer i780j;
    ImageView imgAnimation;
    ImageView imgAnimation1;
    ImageView imgApply;
    ImageView imgPreview;
    boolean k69s;
    String k896p;
    LinearLayout layoutBattery;
    RelativeLayout layoutVideo;
    RelativeLayout layoutVideo1;
    String nm15o;
    int p32l;

    RelativeLayout previewLayout;
    int q2341k;
    MediaPlayer s479i;
    TextView textDate;
    TextView textTime;
    TextView txtApplied;
    TextView txtPercentage;
    VideoView videoView;
    String vs367q;
    String yj568r;

    AudioManager audioManager;
    private int selectedDuration;

    public int getDuration(int position) {
        switch (position) {
            case 0:
                return 5;
            case 1:
                return 10;
            case 2:
                return 30;
            case 3:
                return 60;
            case 4:
                return 9999;  // or set the appropriate value for looping
            default:
                return -1;  // default value
        }
    }

    private int getSpinnerPos(int duration) {
        switch (duration) {
            case 5:
                return 0;
            case 10:
                return 1;
            case 30:
                return 2;
            case 60:
                return 3;
            case 9999:  // Assuming "loop" is the option for a continuous animation
                return 4;
            default:
                return 0;  // Default to the first item if duration is not recognized
        }
    }


    public static Bitmap rotateImageCamera(Bitmap bitmap, int i) {
        Matrix matrix = new Matrix();
        matrix.postRotate((float) i);
        Bitmap createBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        bitmap.recycle();
        return createBitmap;
    }

    public static Bitmap rotateImageIfRequired(Context context, Bitmap bitmap, Uri uri) {
        try {
            int attributeInt = new ExifInterface(context.getContentResolver().openInputStream(uri)).getAttributeInt(androidx.exifinterface.media.ExifInterface.TAG_ORIENTATION, 1);
            if (attributeInt == 3) {
                return rotateImageCamera(bitmap, EMachine.EM_L10M);
            }
            if (attributeInt == 6) {
                return rotateImageCamera(bitmap, 90);
            }
            if (attributeInt != 8) {
                return bitmap;
            }
            return rotateImageCamera(bitmap, 270);
        } catch (IOException unused) {
            return null;
        }
    }

    @SuppressLint({"WrongConstant", "SetTextI18n", "SimpleDateFormat"})
    private void setData() {
        this.txtPercentage.setText(HomeScreen.getBatteryPercentage(this) + "%");
        this.nm15o = getIntent().getStringExtra("subCategoryAnimation");
        this.vs367q = getIntent().getStringExtra("subCategorySound");
        if (getIntent().getStringExtra("uri") != null) {
            this.f557ghn74x = Uri.parse(getIntent().getStringExtra("uri"));
        }
        if (getIntent().getStringExtra("img_uri") != null) {
            this.f55mm73w = Uri.parse(getIntent().getStringExtra("img_uri"));
        }
        String str = this.nm15o;
        if (str != null && str.trim().equalsIgnoreCase(this.k896p)) {
            this.imgApply.setVisibility(0);
            this.txtApplied.setText("Applied");
        }
        Uri uri = this.f557ghn74x;
        if (uri != null && uri.toString().equals(this.gh74n)) {
            this.imgApply.setVisibility(0);
            this.txtApplied.setText("Applied");
        }
        Uri uri2 = this.f55mm73w;
        if (uri2 != null && uri2.toString().equals(this.gh74n)) {
            this.imgApply.setVisibility(0);
            this.txtApplied.setText("Applied");
        }
        if (this.f557ghn74x != null) {
            this.layoutVideo.setVisibility(0);
            setVideoPlayer();
        }



        else if (this.nm15o != null) {

            this.layoutVideo.setVisibility(8);
            this.imgAnimation.setVisibility(8);
            this.animationView.setAnimation(this.nm15o);
            this.animationView.addAnimatorListener(new Animator.AnimatorListener() {
                public void onAnimationCancel(Animator animator) {

                }

                public void onAnimationEnd(Animator animator) {

                }

                public void onAnimationRepeat(Animator animator) {

                }

                public void onAnimationStart(Animator animator) {

                }
            });
            setMediaPlayer();

        } else {
            this.layoutVideo.setVisibility(8);
            this.imgAnimation.setVisibility(0);

            if (this.imgAnimation.getDrawable() != null) {
                this.imgAnimation.setImageDrawable(null);
            }

            this.imgAnimation.setImageBitmap(rotateImageIfRequired(this, uriToBitmap(this, this.f55mm73w), this.f55mm73w));
        }
        this.btnPreview.setOnClickListener(view -> {

            if (getSupportActionBar() != null) {
                getSupportActionBar().hide();
            }

            PreviewScreen.this.previewLayout.setVisibility(8);
            PreviewScreen.this.hidePreviewLayout.setVisibility(0);
            PreviewScreen.this.getPreData();
            if (PreviewScreen.this.f55a70t) {
                PreviewScreen.this.layoutBattery.setVisibility(0);
            } else {
                PreviewScreen.this.layoutBattery.setVisibility(8);
            }
            PreviewScreen.this.textTime.setText(new SimpleDateFormat("hh:mm a").format(new Date()).toUpperCase());
            PreviewScreen.this.textDate.setText(DateFormat.format("d MMMM, yyyy ", new Date().getTime()));
            if (this.f557ghn74x != null) {
                PreviewScreen.this.layoutVideo1.setVisibility(View.VISIBLE);
                setVideoPlayer();
            } else if (this.nm15o != null) {
                PreviewScreen.this.layoutVideo1.setVisibility(8);
                PreviewScreen.this.imgAnimation1.setVisibility(8);
                PreviewScreen.this.animationView1.setAnimation(PreviewScreen.this.nm15o);
                PreviewScreen.this.animationView1.addAnimatorListener(new Animator.AnimatorListener() {
                    public void onAnimationCancel(Animator animator) {
                    }

                    public void onAnimationEnd(Animator animator) {

                    }

                    public void onAnimationRepeat(Animator animator) {

                    }

                    public void onAnimationStart(Animator animator) {
                    }
                });
                PreviewScreen.this.setMediaPlayer();
            } else {
                this.layoutVideo1.setVisibility(8);
                this.imgAnimation1.setVisibility(0);
                ImageView imageView = PreviewScreen.this.imgAnimation1;
                PreviewScreen previewScreen = PreviewScreen.this;
                imageView.setImageBitmap(PreviewScreen.rotateImageIfRequired(previewScreen, PreviewScreen.uriToBitmap(previewScreen, previewScreen.f55mm73w), PreviewScreen.this.f55mm73w));
            }
        });
        this.animationView1.setOnClickListener(view -> {
            PreviewScreen.this.hidePreviewLayout.setVisibility(8);
            PreviewScreen.this.previewLayout.setVisibility(0);
        });
        this.animationView.setOnClickListener(view -> {
            PreviewScreen.this.btnPreview.setVisibility(0);
            PreviewScreen.this.layoutBattery.setVisibility(8);
            PreviewScreen.this.btnSetting.setVisibility(0);
            PreviewScreen.this.btnApplied.setVisibility(0);
        });

        this.btnSetting.setOnClickListener(view -> {
            PreviewScreen.this.getPreData();
            PreviewScreen.this.showSettingPopup(true);

        });

        this.btnApplied.setOnClickListener(view -> PreviewScreen.this.applyAnimation());
    }

    public void setVideoPlayer() {
        Uri uri = this.f557ghn74x;
        if (uri != null) {
            this.videoView.setVideoURI(uri);
            this.videoView.setOnPreparedListener(mediaPlayer -> {
                PreviewScreen.this.videoView.start();
                PreviewScreen.this.getPreData();
                if (!PreviewScreen.this.f55u) {
                    mediaPlayer.setVolume(0.0f, 0.0f);
                    mediaPlayer.setLooping(true);

                }
                PreviewScreen.this.s479i = mediaPlayer;
            });
        }
    }


    @SuppressLint({"WrongConstant", "ResourceType", "SetTextI18n"})
    public void showSettingPopup(boolean z) {
        getPreData();
        if (i780j != null && f55u) {
            i780j.setVolume(0.0f, 0.0f);
        }

        if (s479i != null && this.f55u) {
            s479i.setVolume(0.0f, 0.0f);
        }

        View inflate = getLayoutInflater().inflate(R.layout.lay_settings, (ViewGroup) null);
        ToggleButton toggleButton = (ToggleButton) inflate.findViewById(R.id.switchLock);
        ToggleButton toggleButton2 = (ToggleButton) inflate.findViewById(R.id.switchSound);
        ToggleButton toggleButton3 = (ToggleButton) inflate.findViewById(R.id.switchPer);
        Spinner spinner = (Spinner) inflate.findViewById(R.id.spinnerDuration);
        spinner.setSelection(getSpinnerPos(this.f993m));
        RelativeLayout relativeLayout = (RelativeLayout) inflate.findViewById(R.id.layoutSound);
        Spinner spinner2 = (Spinner) inflate.findViewById(R.id.spinnerClosingMethod);
        ToggleButton toggleButton4 = (ToggleButton) inflate.findViewById(R.id.switchShow);
        ((ImageView) inflate.findViewById(R.id.backArrow)).setOnClickListener(view -> PreviewScreen.this.onBackPressed());
        ((TextView) inflate.findViewById(R.id.title)).setText("Setting");
        this.f5575yaj5 = new Dialog(this);

        ArrayList<String> durationList = new ArrayList<>();
        durationList.add("5 secs");
        durationList.add("10 secs");
        durationList.add("30 secs");
        durationList.add("1 min");
        durationList.add("loop");
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, R.layout.item_setting_spinner, durationList);
        spinner.setAdapter(arrayAdapter);

        ArrayList<String> arrayList2 = new ArrayList<String>();
        arrayList2.add("Double click");
        arrayList2.add("Single click");
        ArrayAdapter<String> arrayAdapter2 = new ArrayAdapter<>(this, R.layout.item_setting_spinner, arrayList2);

        spinner2.setAdapter(arrayAdapter2);
        if (this.f55mm73w != null) {
            relativeLayout.setVisibility(8);
        }

        if (z) {
            toggleButton2.setChecked(this.f55u);
            toggleButton.setChecked(this.k69s);
            toggleButton3.setChecked(this.f55a70t);
            toggleButton4.setChecked(this.f557asd2v);
            spinner2.setSelection(this.q2341k);
            spinner.setSelection(getSpinnerPos(this.f993m));
        }
        toggleButton2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                SharedPreferenceUtils.setBooleanPreference(PreviewScreen.this, "sound", z);
                // stop charging animation sound and system sound
                if (z) {
                    audioManager.setStreamMute(AudioManager.STREAM_NOTIFICATION, false);
                    audioManager.setStreamMute(AudioManager.STREAM_ALARM, false);
                    /*audioManager.adjustStreamVolume(AudioManager.STREAM_NOTIFICATION, AudioManager.ADJUST_MUTE, AudioManager.FLAG_SHOW_UI);*/

                } else {
                    audioManager.setStreamMute(AudioManager.STREAM_NOTIFICATION, true);
                    audioManager.setStreamMute(AudioManager.STREAM_ALARM, true);
                  /*  audioManager.setStreamMute(AudioManager.STREAM_NOTIFICATION, true);*/

                }


            }
        });
            toggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                public void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                    SharedPreferenceUtils.setBooleanPreference(PreviewScreen.this, "lock", z);


                }

            });

        toggleButton3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                SharedPreferenceUtils.setBooleanPreference(PreviewScreen.this, "per", z);
            }
        });
        toggleButton4.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                SharedPreferenceUtils.setBooleanPreference(PreviewScreen.this, "show", z);
            }
        });
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onNothingSelected(AdapterView<?> adapterView) {
            }

            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long j) {
                PreviewScreen.this.selectedDuration = PreviewScreen.this.getDuration(i);
                SharedPreferenceUtils.setIntegerPreference(PreviewScreen.this, "S_DURATION", PreviewScreen.this.selectedDuration);
                SharedPreferenceUtils.setSelectedLoopTimeDuration(PreviewScreen.this,spinner.getSelectedItemPosition());
            }
        });
        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onNothingSelected(AdapterView<?> adapterView) {
            }

            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long j) {
                SharedPreferenceUtils.setIntegerPreference(PreviewScreen.this, "closing", i);

            }
        });
        this.f5575yaj5.requestWindowFeature(1);
        this.f5575yaj5.getWindow().setBackgroundDrawableResource(17170445);
        this.f5575yaj5.setContentView(inflate, new LinearLayout.LayoutParams(getResources().getDisplayMetrics().widthPixels, getResources().getDisplayMetrics().heightPixels));
        this.f5575yaj5.setOnDismissListener(dialogInterface -> PreviewScreen.this.setMediaPlayer());
        this.f5575yaj5.show();
    }

    public static Bitmap uriToBitmap(Context context, Uri uri) {
        try {
            ParcelFileDescriptor openFileDescriptor = context.getContentResolver().openFileDescriptor(uri, "r");
            Bitmap decodeFileDescriptor = BitmapFactory.decodeFileDescriptor(openFileDescriptor.getFileDescriptor());
            openFileDescriptor.close();
            return decodeFileDescriptor;
        } catch (IOException unused) {
            return null;
        }
    }

    public void applyAnimation() {
        SharedPreferenceUtils.setIntegerPreference(this, "custom", -1);
        SharedPreferenceUtils.setStringPreference(this, "anim_id", this.nm15o);
        SharedPreferenceUtils.setStringPreference(this, "audio_id", this.vs367q);
        SharedPreferenceUtils.setStringPreference(this, "custom_uri", "");
        if (this.f557ghn74x != null) {
            SharedPreferenceUtils.setIntegerPreference(this, "custom", 0);
            SharedPreferenceUtils.setStringPreference(this, "custom_uri", this.f557ghn74x.toString());
            SharedPreferenceUtils.setStringPreference(this, "anim_id", "");
            getPreData();
            setVideoPlayer();
        }
        if (this.f55mm73w != null) {
            SharedPreferenceUtils.setIntegerPreference(this, "custom", 1);
            SharedPreferenceUtils.setStringPreference(this, "custom_uri", this.f55mm73w.toString());
            getPreData();
        }

        if (i780j != null) {
            i780j.reset();
        }
        finish();
    }


    public void getPreData() {
        this.k896p = SharedPreferenceUtils.getStringPreference(this, "anim_id", "neon_anim_4.json");
        this.yj568r = SharedPreferenceUtils.getStringPreference(this, "audio_id", "music1.mp3");
        this.f55u = SharedPreferenceUtils.getBooleanPreference(this, "sound", true);
        this.k69s = SharedPreferenceUtils.getBooleanPreference(this, "lock", false);
        this.f55a70t = SharedPreferenceUtils.getBooleanPreference(this, "per", true);
        this.f557asd2v = SharedPreferenceUtils.getBooleanPreference(this, "show", true);
        this.f993m = SharedPreferenceUtils.getIntegerPreference(this, "S_DURATION", 0);
        this.q2341k = SharedPreferenceUtils.getIntegerPreference(this, "closing", 0);
        this.p32l = SharedPreferenceUtils.getIntegerPreference(this, "custom", -1);
        this.gh74n = SharedPreferenceUtils.getStringPreference(this, "custom_uri", "");
    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.preview_screen);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        this.btnSetting = (ImageView) findViewById(R.id.btnSetting);
        this.imgPreview = (ImageView) findViewById(R.id.imgPreview);
        this.layoutBattery = (LinearLayout) findViewById(R.id.layoutBattery);
        this.btnPreview = (RelativeLayout) findViewById(R.id.btnPreview);
        this.hidePreviewLayout = (RelativeLayout) findViewById(R.id.hidePreviewLayout);
        this.previewLayout = (RelativeLayout) findViewById(R.id.previewLayout);
        this.textTime = (TextView) findViewById(R.id.textTime);
        this.textDate = (TextView) findViewById(R.id.textDate);
        this.layoutVideo1 = (RelativeLayout) findViewById(R.id.layoutVideo1);
        this.imgAnimation1 = (ImageView) findViewById(R.id.imgAnimation1);
        this.animationView1 = (LottieAnimationView) findViewById(R.id.animationView1);
        this.btnApplied = (RelativeLayout) findViewById(R.id.btnApplied);
        this.videoView = (VideoView) findViewById(R.id.videoView);
        this.txtPercentage = (TextView) findViewById(R.id.txtPercentage);
        this.imgApply = (ImageView) findViewById(R.id.imgApply);
        this.txtApplied = (TextView) findViewById(R.id.txtApplied);
        this.layoutVideo = (RelativeLayout) findViewById(R.id.layoutVideo);
        this.imgAnimation = (ImageView) findViewById(R.id.imgAnimation);
        this.animationView = (LottieAnimationView) findViewById(R.id.animationView);
        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) Spinner spinner = (Spinner) findViewById(R.id.spinnerDuration);
        ImageView imageView = (ImageView) findViewById(R.id.backArrow);
        this.backArrow = imageView;
        this.imgPreview = (ImageView) findViewById(R.id.imgPreview);
        imageView.setOnClickListener(view -> PreviewScreen.this.onBackPressed());
        getPreData();
        setData();
        audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);


    }

    @Override
    protected void onPause() {
        if (f5575yaj5 != null && f5575yaj5.isShowing()) {
            f5575yaj5.dismiss();
        }
        super.onPause();
    }


    public void onResume() {
        super.onResume();
        if (i780j != null && !i780j.isPlaying()) {
            i780j.start();
        }
    }

    @Override
    protected void onDestroy() {
        if (f5575yaj5 != null && f5575yaj5.isShowing()) {
            f5575yaj5.dismiss();
        }
        super.onDestroy();
    }


    public void setMediaPlayer() {
        getPreData();
        if (i780j != null) {
            if (i780j.isPlaying()) {
                i780j.stop(); // Stop playback if it's currently playing
            }
            i780j.release(); // Release the MediaPlayer
            i780j = null;
        }

        if (!f55u) {
            if (s479i != null) {
                s479i.setVolume(0.0f, 0.0f);
            }
        } else if (vs367q != null) {
            try {
                i780j = new MediaPlayer();
                AssetFileDescriptor openFd = getApplicationContext().getAssets().openFd(this.vs367q);
                i780j.setDataSource(openFd.getFileDescriptor(), openFd.getStartOffset(), openFd.getLength());
                openFd.close();
                i780j.prepare();
                i780j.start();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            if (s479i != null) {
                s479i.setVolume(1.0f, 1.0f);
            }
        }
    }

}