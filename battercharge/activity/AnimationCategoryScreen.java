package com.app.battercharge.activity;

import android.annotation.SuppressLint;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.app.battercharge.R;
import com.app.battercharge.adapter.MainCategoryAdapter;
import com.app.battercharge.model.MainCatModel;
import com.google.firebase.messaging.Constants;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class AnimationCategoryScreen extends AppCompatActivity {

    ArrayList mainCatList;

    RecyclerView recycler;
    TextView txtToolBar;


    @SuppressLint("SetTextI18n")
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.screen_animation_category_screen);
        getSupportActionBar().setTitle("All Animation");
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.category_color)));
       /* getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.cardBgColor));*/
        this.recycler = findViewById(R.id.recycler);
        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) TextView textView = findViewById(R.id.txtToolBar);
        setAd();
    }

    public void setAd() {
        try {
            JSONArray jSONArray = new JSONObject(loadJsonAsset()).getJSONArray(Constants.ScionAnalytics.MessageType.DATA_MESSAGE);
            this.mainCatList = new ArrayList();
            for (int i = 0; i < jSONArray.length(); i++) {
                JSONObject jSONObject = jSONArray.getJSONObject(i);
                String string = jSONObject.getString("categoryName");
                Integer valueOf = jSONObject.getInt("categoryID");
                JSONObject jSONObject2 = jSONObject.getJSONArray("categoryData").getJSONObject(0);
                this.mainCatList.add(new MainCatModel(string, valueOf, jSONObject2.getString("subCategoryAnimation"), jSONObject2.getString("subCategorySound")));

            }
            MainCategoryAdapter adapter = new MainCategoryAdapter(this,mainCatList);
            recycler = (RecyclerView) findViewById(R.id.recycler);
            recycler.setHasFixedSize(true);
            recycler.setAdapter(adapter);
            recycler.setLayoutManager(new GridLayoutManager(this,2));
           /* gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                public int getSpanSize(int i) {
                    int itemViewType = mainCategoryAdapter.getItemViewType(i);
                    if (itemViewType != 0) {
                        return itemViewType != 1 ? -1 : 1;
                    }
                    return 2;
                }
            });*/
        } catch (Exception e) {
           e.printStackTrace();

        }
    }

    public String loadJsonAsset() {
        try {
            InputStream open = getAssets().open("allCategory.json");
            byte[] bArr = new byte[open.available()];
            open.read(bArr);
            open.close();

            return new String(bArr, StandardCharsets.UTF_8);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }




}
