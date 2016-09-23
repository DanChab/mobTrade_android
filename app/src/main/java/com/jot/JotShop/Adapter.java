package com.jot.JotShop;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by D4n on 9/4/2016.
 */
public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder>{
    private List<Items> mItems;
    private boolean mHorizontal;
    public  Adapter(boolean horizontal, List<Items> items){
        mHorizontal = horizontal;
        mItems = items;

    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return mHorizontal ? new ViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter, parent, false)) :
                new ViewHolder(LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.adapter_vertical, parent, false));
    }

    @Override
    public void onBindViewHolder(Adapter.ViewHolder holder, int position) {
        Items item = mItems.get(position);
        holder.imageView.setImageResource(item.getDrawable());
        holder.nameTextView.setText(item.getName());
        holder.ratingTextView.setText(String.valueOf(item.getRating()));
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView imageView;
        public TextView nameTextView;
        public TextView ratingTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.imageView);
            nameTextView = (TextView) itemView.findViewById(R.id.nameTextView);
            ratingTextView = (TextView) itemView.findViewById(R.id.itemPrice);
        }

    }
}
