package com.jot.JotShop;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.jot.JotShop.Utils.Const;

import org.json.JSONObject;

import java.util.List;

/**
 * Created by D4n on 9/25/2016.
 */
public class AdapterShoppingBasket extends RecyclerView.Adapter<AdapterShoppingBasket.ViewHolder> {
    private List<Product> mProducts;
    private boolean mHorizontal;
    String TAG = AdapterShoppingBasket.class.getSimpleName();

    public AdapterShoppingBasket(boolean horizontal, List<Product> products) {
        mHorizontal = horizontal;
        mProducts = products;

    }

    @Override
    public AdapterShoppingBasket.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return mHorizontal ? new ViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter, parent, false)) :
                new ViewHolder(LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.adapter_vertical, parent, false));
    }

    @Override
    public void onBindViewHolder(AdapterShoppingBasket.ViewHolder holder, int position) {
        Product product = mProducts.get(position);
        holder.mItemImage.setImageResource(product.getThumbnail());
        holder.mItemName.setText(product.getName());
        holder.ratingTextView.setText(String.valueOf(product.getPrice()));
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    JsonObjectRequest jsonObReq = new JsonObjectRequest(Request.Method.GET, Const.URL_JSONOBJECT, null,
            new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    Log.d(TAG, response.toString());

                }
            }, new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {

        }
    });

    public static class ViewHolder extends RecyclerView.ViewHolder{
        public ImageView mItemImage;
        public TextView mItemName;
        public TextView ratingTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            mItemImage = (ImageView) itemView.findViewById(R.id.itemImage);
            mItemName = (TextView) itemView.findViewById(R.id.itemName);
            ratingTextView = (TextView) itemView.findViewById(R.id.ratingTextView);
        }
    }
}
