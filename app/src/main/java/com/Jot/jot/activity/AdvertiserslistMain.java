package com.Jot.Jot.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.Jot.Jot.App.AppController;
import com.Jot.Jot.Const.Const;
import com.Jot.Jot.DividerItemDecoration;
import com.Jot.Jot.R;
import com.Jot.Jot.adapter.AdvertiserAdapter;
import com.Jot.Jot.helper.RecyclerTouchListener;
import com.Jot.Jot.model.Advertiser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by D4n on 1/19/2017.
 */

public class AdvertiserslistMain extends AppCompatActivity {

    private  String TAG = MainActivity.class.getSimpleName();
    //Create a list of advertisers
    private List<Advertiser> listAdvertisers;
    private ProgressDialog pDialog;

    //Creating views
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_advertiser_list);

        //Handling toolbar activities
        Toolbar toolbar = (Toolbar) findViewById(R.id.advertiser_list_toolbar);
        setSupportActionBar(toolbar);

        // add back arrow to toolbar
        if (getSupportActionBar() != null){
            final Drawable upArrow = getResources().getDrawable(R.drawable.abc_ic_ab_back_material);
            upArrow.setColorFilter(getResources().getColor(R.color.Gray), PorterDuff.Mode.SRC_ATOP);
            getSupportActionBar().setHomeAsUpIndicator(upArrow);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        //Initializing view
        recyclerView = (RecyclerView) findViewById(R.id.advertiser_list_recycler_view);
        pDialog = new ProgressDialog(this);
        listAdvertisers = new ArrayList<>();

        adapter = new AdvertiserAdapter(getApplication(), listAdvertisers);

        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplication());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Advertiser advertiser = listAdvertisers.get(position);
                String advertiserId = advertiser.getAdvertiserId();
                String advertiserSlogan = advertiser.getSlogan();
                String advertiserBackDrop = advertiser.getBackDrop();
                String advertiserName = advertiser.getName();
                String productOwnerId = advertiser.getProductOwnerId();

                //Pass the keys values  to ClientProfileMain...
                Bundle bundle = new Bundle();
                bundle.putString("advertiserId",advertiserId);
                bundle.putString("advertiserSlogan",advertiserSlogan);
                bundle.putString("advertiserBackDrop",advertiserBackDrop);
                bundle.putString("advertiserName",advertiserName);
                bundle.putString("productOwnerId",productOwnerId);

                Intent intent = new Intent(AdvertiserslistMain.this, ClientProfileMain.class);
                intent.putExtras(bundle);
                startActivity(intent);
                //Todo Remove the Toast in Live
                Toast.makeText(getApplicationContext(), advertiserId +" "+ advertiserSlogan +" "+  advertiserBackDrop +" "+  advertiserName +"is selected!", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onLongClick(View view, int position) {
            //Todo: do spatial stuff like following feature
            }
        }));

        fetchAdvertisersList();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            finish(); // close this activity and return to preview activity (if there is any)
        }

        return super.onOptionsItemSelected(item);
    }

    //This guy here get data from the cloud by using Volley
    private void fetchAdvertisersList() {
        //Initializing ProgressBar
        pDialog.setMessage("Loading...");
        pDialog.show();

        JsonArrayRequest req = new JsonArrayRequest(Const.DATA_GET_ADVERTiSER_LIST,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d(TAG, response.toString());
                        pDialog.hide();
                        listAdvertisers.clear();
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject object = response.getJSONObject(i);

                                //Check the value of the error
                                //boolean error = object.getBoolean("error");

                                /**
                                Here the Advertiser class gets data for the getter and setter...yep right here
                                 All details(name, slogan, logo, id, backdrop,productOwnerId) of the advertiser are requested here
                                 then will be  passed to the ClientProfile class after the click, this prevent network request twice.
                                 */
                           // if (!error) {

                                Advertiser advertiser = new Advertiser();

                               // JSONObject advertiserDetails = object.getJSONObject("advertisers");
                                advertiser.setAdvertiserId(object.getString(Const.TAG_ID));
                                advertiser.setName(object.getString(Const.TAG_NAME));
                                advertiser.setSlogan(object.getString(Const.TAG_SLOGAN));
                                advertiser.setLogoUrl(object.getString(Const.TAG_LOGO_URL));
                                advertiser.setProductOwnerId(object.getString(Const.CONTACT_DETAILS));
                                advertiser.setBackDrop(object.getString(Const.TAG_BACKDROP));

                                //Add all parameters to the list...
                                listAdvertisers.add(advertiser);


                          //  }

                            } catch (JSONException e) {
                                Log.e(TAG, "Json parsing error" + e.getMessage());
                            }
                        }
                        adapter.notifyDataSetChanged();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG, "Error network: " + error.getMessage());
                Toast.makeText(AdvertiserslistMain.this, "Could not retrieve try later", Toast.LENGTH_SHORT).show();
                pDialog.hide();
            }
        });
        /// Adding request to request queue
        AppController.getInstance().addToRequestQueue(req,TAG);

    }

}
