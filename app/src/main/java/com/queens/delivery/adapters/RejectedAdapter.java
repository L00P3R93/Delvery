package com.queens.delivery.adapters;

import android.view.View;
import android.view.ViewGroup;
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

import com.queens.delivery.R;
import com.queens.delivery.models.NewsItem;

import java.util.List;
import java.util.ArrayList;


public class RejectedAdapter extends RecyclerView.Adapter<RejectedAdapter.RejectedViewHolder> implements Filterable {
    private Context mContext;
    private List<NewsItem> mData;
    private List<NewsItem> mDataFiltered;
    boolean isDark = false;

    public RejectedAdapter(Context mContext, List<NewsItem> mData, boolean isDark) {
        this.mContext = mContext;
        this.mData = mData;
        this.isDark = isDark;
        this.mDataFiltered = mData;
    }

    public RejectedAdapter(Context mContext, List<NewsItem> mData) {
        this.mContext = mContext;
        this.mData = mData;
        this.mDataFiltered = mData;

    }

    @NonNull
    @Override
    public RejectedViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View layout = LayoutInflater.from(mContext).inflate(R.layout.fragment_rejected_list,viewGroup,false);
        return new RejectedViewHolder(layout);
    }

    @Override
    public void onBindViewHolder(@NonNull RejectedViewHolder rejectedViewHolder, int position) {

        // bind data here

        // we apply animation to views here
        // first lets create an animation for user photo
        rejectedViewHolder.img_user.setAnimation(AnimationUtils.loadAnimation(mContext,R.anim.fade_transition_animation));

        // lets create the animation for the whole card
        // first lets create a reference to it
        // you ca use the previous same animation like the following

        // but i want to use a different one so lets create it ..
        rejectedViewHolder.container.setAnimation(AnimationUtils.loadAnimation(mContext,R.anim.fade_scale_animation));
        rejectedViewHolder.tv_title.setText(mDataFiltered.get(position).getTitle());
        rejectedViewHolder.tv_content.setText(mDataFiltered.get(position).getContent());
        rejectedViewHolder.tv_date.setText(mDataFiltered.get(position).getDate());
        rejectedViewHolder.img_user.setImageResource(mDataFiltered.get(position).getUserPhoto());
    }

    @Override
    public int getItemCount() {return mDataFiltered.size();}

    @Override
    public Filter getFilter() {

        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {

                String Key = constraint.toString();
                if (Key.isEmpty()) {

                    mDataFiltered = mData ;

                }
                else {
                    List<NewsItem> lstFiltered = new ArrayList<>();
                    for (NewsItem row : mData) {

                        if (row.getTitle().toLowerCase().contains(Key.toLowerCase())){
                            lstFiltered.add(row);
                        }

                    }

                    mDataFiltered = lstFiltered;

                }


                FilterResults filterResults = new FilterResults();
                filterResults.values= mDataFiltered;
                return filterResults;

            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {


                mDataFiltered = (List<NewsItem>) results.values;
                notifyDataSetChanged();

            }
        };
    }
    public class RejectedViewHolder extends RecyclerView.ViewHolder {

        TextView tv_title,tv_content,tv_date;
        ImageView img_user;
        RelativeLayout container;

        public RejectedViewHolder(@NonNull View itemView) {
            super(itemView);
            container = itemView.findViewById(R.id.container);
            tv_title = itemView.findViewById(R.id.tv_title);
            tv_content = itemView.findViewById(R.id.tv_description);
            tv_date = itemView.findViewById(R.id.tv_date);
            img_user = itemView.findViewById(R.id.img_user);

            if (isDark) {
                setDarkTheme();
            }

        }


        private void setDarkTheme() {

            container.setBackgroundResource(R.drawable.card_bg_dark);

        }


    }
}
