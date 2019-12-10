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
import android.content.Context;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.queens.delivery.R;
import com.queens.delivery.models.Orders;

import java.util.List;
import java.util.ArrayList;

public class DeliveredParcelAdapter extends RecyclerView.Adapter<DeliveredParcelAdapter.DeliveredParcelViewHolder> implements Filterable {
    private Context mContext;
    private List<Orders> mData;
    private List<Orders> mDataFiltered;
    boolean isDark = false;
    private OnItemClickListener mListener;

    public interface OnItemClickListener{
        void onItemClick(View itemView, int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        this.mListener = listener;
    }

    public DeliveredParcelAdapter(Context mContext, List<Orders> mData, boolean isDark) {
        this.mContext = mContext;
        this.mData = mData;
        this.isDark = isDark;
        this.mDataFiltered = mData;
    }

    public DeliveredParcelAdapter(Context mContext, List<Orders> mData) {
        this.mContext = mContext;
        this.mData = mData;
        this.mDataFiltered = mData;

    }

    @NonNull
    @Override
    public DeliveredParcelViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View layout = LayoutInflater.from(mContext).inflate(R.layout.fragment_delivered_parcel_list,viewGroup,false);
        return new DeliveredParcelViewHolder(layout);
    }

    @Override
    public void onBindViewHolder(@NonNull DeliveredParcelViewHolder dpViewHolder, int position) {

        // bind data here

        // we apply animation to views here
        // first lets create an animation for user photo
        //exchangeViewHolder.img_user.setAnimation(AnimationUtils.loadAnimation(mContext,R.anim.fade_transition_animation));

        // lets create the animation for the whole card
        // first lets create a reference to it
        // you ca use the previous same animation like the following

        // but i want to use a different one so lets create it ..
        dpViewHolder.container.setAnimation(AnimationUtils.loadAnimation(mContext, R.anim.fade_scale_animation));
        dpViewHolder.order_code.setText(mDataFiltered.get(position).getBillNo());
        dpViewHolder.customer_phone.setText(mDataFiltered.get(position).getCustomerPhone());
        dpViewHolder.customer_address.setText(mDataFiltered.get(position).getCustomerAddress());
        dpViewHolder.date.setText(mDataFiltered.get(position).getDate());
    }

    @Override
    public int getItemCount() {return mDataFiltered.size();}

    public void clearAll(){
        mDataFiltered.clear();
        notifyDataSetChanged();
    }

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
                    List<Orders> lstFiltered = new ArrayList<>();
                    for (Orders row : mData) {

                        if (row.getBillNo().toLowerCase().contains(Key.toLowerCase())){
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


                mDataFiltered = (List<Orders>) results.values;
                notifyDataSetChanged();

            }
        };
    }


    public class DeliveredParcelViewHolder extends RecyclerView.ViewHolder {

        TextView order_code,customer_address,customer_phone,date;
        RelativeLayout container;

        public DeliveredParcelViewHolder(@NonNull View itemView) {
            super(itemView);
            container = itemView.findViewById(R.id.container);
            order_code = itemView.findViewById(R.id.order_code);
            customer_address = itemView.findViewById(R.id.customer_address);
            customer_phone = itemView.findViewById(R.id.customer_phone);
            date = itemView.findViewById(R.id.date);

            if (isDark) {
                setDarkTheme();
            }

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(mListener != null){
                        int position = getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION){
                            mListener.onItemClick(v, position);
                        }
                    }
                }
            });

        }


        private void setDarkTheme() {

            container.setBackgroundResource(R.drawable.card_bg_dark);

        }


    }
}
