package com.jot.JotShop;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.jot.JotShop.Utils.Const;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ShoppingBasket extends AppCompatActivity implements Toolbar.OnMenuItemClickListener{

    public static final String ORIENTATION = "orientation";

    private RecyclerView mRecyclerView;
    private boolean mHorizontal;
    String TAG = ShoppingBasket.class.getSimpleName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shopping_basket_activity);

        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.inflateMenu(R.menu.shopping_basket);
        toolbar.setOnMenuItemClickListener(this);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setHasFixedSize(true);

        if (savedInstanceState == null) {
            mHorizontal = true;
        } else {
            mHorizontal = savedInstanceState.getBoolean(ORIENTATION);
        }

        setupAdapter();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(ORIENTATION, mHorizontal);
    }

    private void setupAdapter() {
        List<Product> products = getProducts();

        SnapAdapter snapAdapter = new SnapAdapter();
        if (mHorizontal) {
            snapAdapter.addSnap(new Snap(Gravity.CENTER_HORIZONTAL, "Snap center", products));
            snapAdapter.addSnap(new Snap(Gravity.START, "Snap start", products));
            snapAdapter.addSnap(new Snap(Gravity.END, "Snap end", products));
        } else {
            snapAdapter.addSnap(new Snap(Gravity.CENTER_VERTICAL, "Snap center", products));
            snapAdapter.addSnap(new Snap(Gravity.TOP, "Snap top", products));
            snapAdapter.addSnap(new Snap(Gravity.BOTTOM, "Snap bottom", products));
        }

        mRecyclerView.setAdapter(snapAdapter);
    }

    JsonObjectRequest jsonReq = new JsonObjectRequest(Request.Method.GET, Const.URL_JSONOBJECT,
            null, new Response.Listener<JSONObject>() {
        @Override
        public void onResponse(JSONObject response) {
            Log.d(TAG, response.toString());

        }
    }, new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {

        }
    });

    private List<Product> getProducts() {
        List<Product> products = new ArrayList<>();
        int id=0;
        int thumbnail =0;
        products.add(new Product("Rice+", id, thumbnail,4.6f));
        return products;
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        if (item.getItemId() == R.id.layoutType) {
            mHorizontal = !mHorizontal;
            setupAdapter();
            item.setTitle(mHorizontal ? "Vertical" : "Horizontal");
        }
        return false;
    }
}