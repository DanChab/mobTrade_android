package com.Jot.Jot.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.Jot.Jot.R;
import com.Jot.Jot.model.Advertiser;

import java.util.List;

/**
 * Created by D4n on 1/19/2017.
 */

public class AdvertiserAdapter extends RecyclerView.Adapter<AdvertiserAdapter.ViewHolder> {

    //private ImageLoader imageLoader;
    private Context mContext;
    public TextView textViewSlogan;
    List<Advertiser> advertisers;//List to store all advertisers

    public class ViewHolder extends RecyclerView.ViewHolder{
        public ImageView imageViewLogo;

        //Initializing Views
        public ViewHolder(View itemView) {
            super(itemView);
            imageViewLogo = (ImageView) itemView.findViewById(R.id.iv_Logo);
            //Todo : Decide to keep it or remove in live
            //textViewName = (TextView) itemView.findViewById(R.id.tv_Name);
            //textViewSlogan = (TextView) itemView.findViewById(R.id.tv_slogan);
        }
    }

    public AdvertiserAdapter(Context mContext, List<Advertiser> advertisers) {
        super();
        this.advertisers = advertisers;
        this.mContext = mContext;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.advertiser_list, parent,false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        Advertiser advertiser = advertisers.get(position);//Getting the particular item from the list

        //Loading image from url
        Glide.with(mContext).load(advertiser.getLogoUrl())
                .thumbnail(0.5f)
                .crossFade().diskCacheStrategy(DiskCacheStrategy.ALL)
                //.bitmapTransform(new BlurTransformation(mContext, 50),new CropCircleTransformation(mContext))
                .into(holder.imageViewLogo);

        //Loading the name, slogan
        //String name = advertiser.getName();
       // String slogan = advertiser.getSlogan();
       // String advertiserId = advertiser.getAdvertiserId();
       // textViewName.setText(name);
       // textViewSlogan.setText(slogan);

    }

    @Override
    public int getItemCount() {
        return advertisers.size();
    }


}