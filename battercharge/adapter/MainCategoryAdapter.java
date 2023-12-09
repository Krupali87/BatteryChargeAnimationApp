package com.app.battercharge.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.airbnb.lottie.LottieAnimationView;
import com.app.battercharge.R;
import com.app.battercharge.activity.ViewAllAnimationScreen;
import com.app.battercharge.model.MainCatModel;

import java.util.List;

public class MainCategoryAdapter extends RecyclerView.Adapter<MainCategoryAdapter.MyViewHolder> {
    private Context context;
    private List<MainCatModel> mainCategoryList;

    public MainCategoryAdapter(Context context, List<MainCatModel> mainCategoryList) {
        this.context = context;
        this.mainCategoryList = mainCategoryList;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        LottieAnimationView animationView;
        LinearLayout linearMainCard;
        TextView txtName;

        public MyViewHolder(View itemView) {
            super(itemView);
            animationView = itemView.findViewById(R.id.recycleranimation_View);
            linearMainCard = itemView.findViewById(R.id.linear_main_card);
            txtName = itemView.findViewById(R.id.txt_name);
        }
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.main_cat_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public int getItemCount() {
        if(mainCategoryList==null) return 0;
        return mainCategoryList.size();
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void onBindViewHolder(MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        if (mainCategoryList != null && position < mainCategoryList.size()) {
            holder.txtName.setText(mainCategoryList.get(position).getCategoryName());
            holder.animationView.setAnimation(mainCategoryList.get(position).getCategoryAnimation());
            MainCategoryAdapter adapter = new MainCategoryAdapter(context,mainCategoryList);
            adapter.notifyDataSetChanged();
            holder.linearMainCard.setOnClickListener(view -> {
                Intent intent = new Intent(context, ViewAllAnimationScreen.class);
                intent.putExtra("CategoryId", mainCategoryList.get(position).getCategoryId());
                context.startActivity(intent);
            });
        }
    }
}