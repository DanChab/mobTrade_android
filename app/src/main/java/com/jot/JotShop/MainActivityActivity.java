package com.jot.JotShop;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

public class MainActivityActivity extends AppCompatActivity implements View.OnClickListener{

    Toolbar mToolbar;
    ImageView mShoppingBasket;
    ImageView mScanner;
    ImageView mList;
    ImageView mProfile;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        mToolbar = (Toolbar) findViewById(R.id.mtoolbar);
        setSupportActionBar(mToolbar);

        mShoppingBasket = (ImageView) findViewById(R.id.ic_shoppingBasket);
        mScanner = (ImageView) findViewById(R.id.ic_scanner);
        mList = (ImageView) findViewById(R.id.ic_list);
        mProfile = (ImageView) findViewById(R.id.ic_profile);

        mShoppingBasket.setOnClickListener(this);
        mScanner.setOnClickListener(this);
        mList.setOnClickListener(this);
        mProfile.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){
            case R.id.ic_shoppingBasket:
                Intent intent = new Intent(MainActivityActivity.this,AdapterShoppingBasket.class);
                startActivity(intent);
        }

    }
}
