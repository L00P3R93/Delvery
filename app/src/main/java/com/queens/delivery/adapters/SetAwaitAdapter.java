package com.queens.delivery.adapters;


import android.annotation.SuppressLint;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;
import android.view.LayoutInflater;
import android.view.animation.AnimationUtils;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.content.Context;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.queens.delivery.R;
import com.queens.delivery.models.Products;

import java.util.List;
import java.util.ArrayList;

public class SetAwaitAdapter extends RecyclerView.Adapter<SetAwaitAdapter.SetAwaitViewHolder>{
    private Context mContext;
    private List<Products> mData;
    private List<Products> mDataFiltered;
    boolean isDark = false;


    public SetAwaitAdapter(Context mContext, List<Products> mData, boolean isDark) {
        this.mContext = mContext;
        this.mData = mData;
        this.isDark = isDark;
        this.mDataFiltered = mData;
    }

    public SetAwaitAdapter(Context mContext, List<Products> mData) {
        this.mContext = mContext;
        this.mData = mData;
        this.mDataFiltered = mData;

    }

    @NonNull
    @Override
    public SetAwaitViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View layout = LayoutInflater.from(mContext).inflate(R.layout.fragment_set_await_list,viewGroup,false);
        return new SetAwaitViewHolder(layout);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull SetAwaitViewHolder homeViewHolder, int position) {

        // bind data here

        // we apply animation to views here
        // first lets create an animation for user photo
        //homeViewHolder.img_user.setAnimation(AnimationUtils.loadAnimation(mContext,R.anim.fade_transition_animation));

        // lets create the animation for the whole card
        // first lets create a reference to it
        // you ca use the previous same animation like the following

        // but i want to use a different one so lets create it ..
        homeViewHolder.container.setAnimation(AnimationUtils.loadAnimation(mContext,R.anim.fade_scale_animation));
        homeViewHolder.product_name.setText(mData.get(position).getProduct_name());
        homeViewHolder.quantity.setText("Quantity: "+mData.get(position).getQuantity());
        Glide.with(this.mContext).load(mData.get(position).getProduct_image()).diskCacheStrategy(DiskCacheStrategy.ALL).into(homeViewHolder.product_image);
    }

    @Override
    public int getItemCount() {return mData.size();}

    public void clearAll(){
        mDataFiltered.clear();
        notifyDataSetChanged();
    }
    public class SetAwaitViewHolder extends RecyclerView.ViewHolder {

        TextView product_name, quantity;
        ImageView product_image;
        RelativeLayout container;

        public SetAwaitViewHolder(@NonNull View itemView) {
            super(itemView);
            container = itemView.findViewById(R.id.container);
            product_name = itemView.findViewById(R.id.product_name);
            quantity = itemView.findViewById(R.id.quantity);
            product_image = itemView.findViewById(R.id.product_image);

            if (isDark) {
                setDarkTheme();
            }
        }


        private void setDarkTheme() {

            container.setBackgroundResource(R.drawable.card_bg_dark);

        }


    }

}



