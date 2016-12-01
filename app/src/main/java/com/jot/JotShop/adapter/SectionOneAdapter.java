package com.jot.JotShop.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.jot.JotShop.R;
import com.jot.JotShop.model.Product;

import java.util.List;

/**
 * Created by D4n on 10/4/2016.
 */

public class SectionOneAdapter extends RecyclerView.Adapter<SectionOneAdapter.MyViewHolder> {

    private List<Product> productList;
    private Context mContext;
    private TextView mPrice;
    private TextView mProductName;

    public class MyViewHolder extends RecyclerView.ViewHolder{
        private ImageView thumbnail;
        private MyViewHolder(View View) {
            super(View);
            thumbnail = (ImageView) View.findViewById(R.id.thumbnail);
            mPrice = (TextView) View.findViewById(R.id.tv_price);
            mProductName = (TextView) View.findViewById(R.id.tv_productName);
        }
    }

    public SectionOneAdapter(Context context, List<Product> productList){
        mContext = context;
        this.productList = productList;
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.gallery_thumbnail, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Product product = productList.get(position);
        String timestamp = product.getTimestamp();
        String productName = product.getName();
        mPrice.setText(timestamp);
        mProductName.setText(productName);


        Glide.with(mContext).load(product.getMedium())
                .thumbnail(0.5f)
                .crossFade().diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.thumbnail);
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }
    //Handle the clicks
    public interface ClickListener{
        void onClick(View view, int position);
        void  onLongClick(View view, int position);
        //Todo add to list here
    }

    public static class RecyclerTouchListener  implements RecyclerView.OnItemTouchListener{

        private GestureDetector gestureDetector;
        private SectionOneAdapter.ClickListener clickListener;

        public RecyclerTouchListener(Context context, final RecyclerView recyclerView,final SectionOneAdapter.ClickListener clickListener){
            this.clickListener = clickListener;
            gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener(){
                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    return true;
                }

                @Override
                public void onLongPress(MotionEvent e) {
                    View child = recyclerView.findChildViewUnder(e.getX(), e.getY());
                    if (child != null && clickListener != null) {
                        clickListener.onLongClick(child, recyclerView.getChildPosition(child));
                    }
                }
            });

        }
        @Override
        public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {

            View child = rv.findChildViewUnder(e.getX(),e.getY());
            if (child != null && clickListener != null && gestureDetector.onTouchEvent(e)){
                clickListener.onClick(child,rv.getChildAdapterPosition(child));
            }
            return false;
        }

        @Override
        public void onTouchEvent(RecyclerView rv, MotionEvent e) {

        }

        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

        }
    }
}