package com.jot.JotShop;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

public class ShoppingBasket extends AppCompatActivity implements Toolbar.OnMenuItemClickListener {

    public  static  final String ORIENTATION = "orientation";
    private RecyclerView mRecyclerView;
    private boolean mHorizontal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shopping_basket_activity);

        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        Toolbar toolbar = (Toolbar) findViewById(R.id.shop_basket_toolbar);
        toolbar.inflateMenu(R.menu.shopping_basket);

        toolbar.setOnMenuItemClickListener(this);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setHasFixedSize(true);

        if (savedInstanceState==null){
            mHorizontal = true;

        }else{
            mHorizontal = savedInstanceState.getBoolean(ORIENTATION);
        }
        setupAdapter();
    }

    private void setupAdapter() {
        List<Items> apps = getApps();

        SnapAdapter snapAdapter = new SnapAdapter();
        if (mHorizontal){
            snapAdapter.addSnap(new Snap(Gravity.CENTER_HORIZONTAL, "Snap center",apps));
            snapAdapter.addSnap(new Snap(Gravity.START, "Snap start", apps));
            snapAdapter.addSnap(new Snap(Gravity.END, "Snap end", apps));
        }else{
            snapAdapter.addSnap(new Snap(Gravity.CENTER_VERTICAL, "Snap center", apps));
            snapAdapter.addSnap(new Snap(Gravity.TOP, "Snap top", apps));
            snapAdapter.addSnap(new Snap(Gravity.BOTTOM, "Snap bottom", apps));
        }
        mRecyclerView.setAdapter(snapAdapter);
    }

    private List<Items> getApps() {
        List<Items> apps = new ArrayList<>();
        apps.add(new Items("item",R.drawable.ic_shopping_basket_blue,60));
        apps.add(new Items("item",R.drawable.ic_shopping_basket_blue,60));
        apps.add(new Items("item",R.drawable.ic_shopping_basket_blue,60));
        apps.add(new Items("item",R.drawable.ic_shopping_basket_blue,60));
        apps.add(new Items("item",R.drawable.ic_shopping_basket_blue,60));

        return apps;
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        if (item.getItemId()==R.id.layoutType){
            mHorizontal = !mHorizontal;
            setupAdapter();
            item.setTitle(mHorizontal ? "vertical": "Horizontal");
        }
        return false;
    }
}
