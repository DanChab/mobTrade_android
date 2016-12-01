package com.jot.JotShop.helper;

import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.jot.JotShop.R;
import com.jot.JotShop.model.Product;

import java.util.ArrayList;

/**
 * Created by D4n on 10/6/2016.
 */

public class SlideshowDialogFragment extends android.support.v4.app.DialogFragment {

    private String TAG = SlideshowDialogFragment.class.getSimpleName();
    private ArrayList<Product> products;
    private ViewPager viewPager;
    private MyViewPagerAdapter myViewPagerAdapter;
    private TextView lblName,lblCount,lblDate;
    private int selectedPosition = 0;

    public static SlideshowDialogFragment newInstance() {
        SlideshowDialogFragment f = new SlideshowDialogFragment();
        return f;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_image_slider,container,false);
        viewPager = (ViewPager) v.findViewById(R.id.viewpager);
        lblName = (TextView) v.findViewById(R.id.ItemName);

        lblCount = (TextView) v.findViewById(R.id.lbl_count);

        lblDate = (TextView) v.findViewById(R.id.date);

        products = (ArrayList<Product>) getArguments().getSerializable("products");
        selectedPosition = getArguments().getInt("position");

        Log.e(TAG,"position: "+ selectedPosition);
        Log.e(TAG,"products size: "+ products.size());

        myViewPagerAdapter = new MyViewPagerAdapter();
        viewPager.setAdapter(myViewPagerAdapter);
        viewPager.addOnPageChangeListener(viewPagerPageChangeListener);

        setCurrentItem(selectedPosition);
        return v;
    }

    private void setCurrentItem(int position){
        viewPager.setCurrentItem(position,false);
        displayMetaInfo(selectedPosition);
    }

    //Page change listener
    ViewPager.OnPageChangeListener viewPagerPageChangeListener = new ViewPager.OnPageChangeListener(){

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {

        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };
//Todo comment out the price and implement from the server side and mode info and features
    private void displayMetaInfo(int position){
        lblCount.setText((position + 1) + " of " + products.size());

        Product product = products.get(position);
        lblName.setText(product.getName());
        lblDate.setText(product.getTimestamp());
       // lblprice.setText(product.getPrice());
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, android.R.style.Theme_Black_NoTitleBar_Fullscreen);
    }


    //	adapter
    private class MyViewPagerAdapter extends PagerAdapter{

        private LayoutInflater layoutInflater;

        public MyViewPagerAdapter() {
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {

            layoutInflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = layoutInflater.inflate(R.layout.image_fullscreen_preview, container, false);

            ImageView imageViewPreview = (ImageView) view.findViewById(R.id.image_preview);

            Product product = products.get(position);

            Glide.with(getActivity()).load(product.getLarge())
                    .thumbnail(0.5f)
                    .crossFade()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(imageViewPreview);

            container.addView(view);

            return view;
        }

        @Override
        public int getCount() {
            return products.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object obj) {
            return view == ((View) obj);
        }


        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }
}
