package com.app.battercharge.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.airbnb.lottie.LottieAnimationView;
import com.app.battercharge.R;
import com.app.battercharge.activity.PreviewScreen;
import com.app.battercharge.model.SubCatModel;

import java.util.ArrayList;

public class CategoryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public final ArrayList<SubCatModel> categoryList;
    public final Context context;


    public static class MyViewHolder extends RecyclerView.ViewHolder {
        LottieAnimationView item_animation;
        LinearLayout linear_card;

        public MyViewHolder(View view) {
            super(view);
            this.item_animation = view.findViewById(R.id.item_animation);
            this.linear_card = view.findViewById(R.id.linear_card);
        }
    }

    public CategoryAdapter(Context context2, ArrayList<SubCatModel> arrayList) {
        this.context = context2;
        this.categoryList = arrayList;

    }

    public int getItemCount() {
        return this.categoryList.size();
    }

    public int getItemViewType(int i) {
        return this.categoryList.get(i) != null ? 1 : 0;
    }

    @NonNull
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

            return new MyViewHolder(LayoutInflater.from(this.context).inflate(R.layout.item_category, viewGroup, false));



    }

    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, @SuppressLint("RecyclerView") final int i) {
        if (viewHolder.getItemViewType() == 1) {
            MyViewHolder myViewHolder = (MyViewHolder) viewHolder;
            myViewHolder.item_animation.setAnimation(this.categoryList.get(i).getAnimation());
            myViewHolder.linear_card.setOnClickListener(view -> {
                Intent intent = new Intent(CategoryAdapter.this.context, PreviewScreen.class);
                intent.putExtra("subCategoryAnimation", CategoryAdapter.this.categoryList.get(i).getAnimation());
                intent.putExtra("subCategorySound", CategoryAdapter.this.categoryList.get(i).getSound());
                CategoryAdapter.this.context.startActivity(intent);
            });


        }
    }
}
