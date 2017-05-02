package com.Jot.Jot.adapter;

import android.content.Context;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.Jot.Jot.R;
import com.Jot.Jot.model.Product;

import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.MyViewHolder> {

    private Context mContext;
   // private List<Album> albumList;
   private List<Product> products;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private ImageView thumbnail;
        private TextView mProductName;
        private TextView mPriceProduct;
       // public ImageView thumbnail;
       // public ImageView overflow;

        public MyViewHolder(View view) {
            super(view);
            mProductName = (TextView) view.findViewById(R.id.tv_product_name);
            mPriceProduct = (TextView) view.findViewById(R.id.tv_product_price);
            thumbnail = (ImageView) view.findViewById(R.id.thumbnail);
            //overflow = (ImageView) view.findViewById(R.id.overflow);
        }
    }

    public ProductAdapter() {
    }

    public ProductAdapter(Context mContext, List<Product> products) {
        this.mContext = mContext;
        this.products = products;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.client_product_card, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        //Here all the details of the product are bind
        Product product = products.get(position);
        holder.mProductName.setText(product.getName());
        holder.mPriceProduct.setText(product.getPrice());
        Glide.with(mContext).load(product.getSmall())
                .thumbnail(0.5f)
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.thumbnail);

    }

    /**
     * Showing popup menu when tapping on 3 dots
     */
    private void showPopupMenu(View view) {
        // inflate menu
        PopupMenu popup = new PopupMenu(mContext, view);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.menu_album, popup.getMenu());
        popup.setOnMenuItemClickListener(new MyMenuItemClickListener());
        popup.show();
    }

    /**
     * Click listener for popup menu items
     */
    class  MyMenuItemClickListener implements PopupMenu.OnMenuItemClickListener {

        public MyMenuItemClickListener() {
        }

        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {
            switch (menuItem.getItemId()) {
                case R.id.action_add_favourite:
                    Toast.makeText(mContext, "Add to favourite", Toast.LENGTH_SHORT).show();
                    return true;
                case R.id.action_play_next:
                    Toast.makeText(mContext, "Play next", Toast.LENGTH_SHORT).show();
                    return true;
                default:
            }
            return false;
        }
    }

    @Override
    public int getItemCount() {
        return products.size();
    }
}
