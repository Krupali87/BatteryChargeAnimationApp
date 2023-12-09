package com.app.battercharge.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.app.battercharge.R;
import com.app.battercharge.adapter.CategoryAdapter;
import com.app.battercharge.model.SubCatModel;
import com.app.battercharge.utils.SharedPreferenceUtils;
import com.google.firebase.messaging.Constants;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class ViewAllAnimationScreen extends AppCompatActivity {
    String animId;
    String audioId;

    ArrayList<SubCatModel> categoryList = new ArrayList<>();
    String customUri;
    int duration;
    CategoryAdapter f5517k;
    int f5519masd;
    int isClosing;
    boolean isLock;
    boolean isSound;

    RecyclerView recyclerview_category;
    TextView txtToolBar;

    private void setAdapter() {
        try {
            int valueOf = getIntent().getIntExtra("CategoryId", 0);
            this.categoryList = new ArrayList<>();
            JSONArray jSONArray = new JSONObject(loadJsonFromAsset()).getJSONArray(Constants.ScionAnalytics.MessageType.DATA_MESSAGE);
            for (int i = 0; i < jSONArray.length(); i++) {
                JSONObject jSONObject = jSONArray.getJSONObject(i);
                int i2 = jSONObject.getInt("categoryID");
                String string = jSONObject.getString("categoryName");
                JSONArray jSONArray2 = jSONObject.getJSONArray("categoryData");
                if (valueOf == i2) {
                    this.txtToolBar.setText(string);
                    for (int i3 = 0; i3 < jSONArray2.length(); i3++) {
                        JSONObject jSONObject2 = jSONArray2.getJSONObject(i3);
                        this.categoryList.add(new SubCatModel(jSONObject2.getInt("subCategoryID"), jSONObject2.getString("subCategoryAnimation"), jSONObject2.getString("subCategorySound")));

                    }
                }
            }
        } catch (JSONException unused) {
            throw new RuntimeException(unused);
        }
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 3);
        this.recyclerview_category.setLayoutManager(gridLayoutManager);
        final CategoryAdapter categoryAdapter = new CategoryAdapter(this, this.categoryList);
        this.f5517k = categoryAdapter;
       /* gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            public int getSpanSize(int i) {
                int itemViewType = categoryAdapter.getItemViewType(i);
                if (itemViewType != 0) {
                    return itemViewType != 1 ? -1 : 1;
                }
                return 2;
            }
        });*/
        this.recyclerview_category.setAdapter(categoryAdapter);
    }

    private void setData() {
        this.animId = SharedPreferenceUtils.getStringPreference(this, "anim_id", "neon_anim_4.json");
        this.audioId = SharedPreferenceUtils.getStringPreference(this, "audio_id", "music1.mp3");
        this.isSound = SharedPreferenceUtils.getBooleanPreference(this, "sound", true);
        this.isLock = SharedPreferenceUtils.getBooleanPreference(this, "lock", false);
        this.duration = SharedPreferenceUtils.getIntegerPreference(this, "S_DURATION", 0);
        this.isClosing = SharedPreferenceUtils.getIntegerPreference(this, "closing", 0);
        this.f5519masd = SharedPreferenceUtils.getIntegerPreference(this, "custom", -1);
        this.customUri = SharedPreferenceUtils.getStringPreference(this, "custom_uri", "");
    }

    public String loadJsonFromAsset() {
        try {
            InputStream open = getAssets().open("allCategory.json");
            byte[] bArr = new byte[open.available()];
            open.read(bArr);
            open.close();
            return new String(bArr, StandardCharsets.UTF_8);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @SuppressLint("SetTextI18n")
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.screen_category);
        getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.cardBgColor));
        this.txtToolBar = findViewById(R.id.txtToolBar);
        this.recyclerview_category = findViewById(R.id.recyclerview_category);
        this.txtToolBar.setText("Category Animation");
        setData();
        setAdapter();

    }



}
