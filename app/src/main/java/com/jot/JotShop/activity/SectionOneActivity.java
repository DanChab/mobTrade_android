package com.jot.JotShop.activity;

import android.app.ProgressDialog;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.jot.JotShop.App.AppController;
import com.jot.JotShop.R;
import com.jot.JotShop.adapter.SectionOneAdapter;
import com.jot.JotShop.model.Image;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class SectionOneActivity extends AppCompatActivity {
    private String TAG = SectionOneActivity.class.getSimpleName();
    private static final String endpoint = "http://api.androidhive.info/json/glide.json";
    private List<Image> images;
    private ProgressDialog pDialog;
    private SectionOneAdapter mAdapter;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.section_one_activity);

        //Handling toolbar activities
        Toolbar toolbar = (Toolbar) findViewById(R.id.section_one_toolbar);
        setSupportActionBar(toolbar);
        // add back arrow to toolbar
        if (getSupportActionBar() != null){
            final Drawable upArrow = getResources().getDrawable(R.drawable.abc_ic_ab_back_material);
            upArrow.setColorFilter(getResources().getColor(R.color.Black), PorterDuff.Mode.SRC_ATOP);
            getSupportActionBar().setHomeAsUpIndicator(upArrow);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        pDialog = new ProgressDialog(this);
        images = new ArrayList<>();
        mAdapter = new SectionOneAdapter(getApplicationContext(), images);


        RecyclerView.LayoutManager mLayoutManager =new GridLayoutManager(getApplication(),2);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);

      /*  recyclerView.addOnItemTouchListener(new SectionOneAdapter.RecyclerTouchListener(getApplicationContext(), recyclerView, new SectionOneAdapter.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("images", (Serializable) images);
                bundle.putInt("position", position);

                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                SlideshowDialogFragment newFragment = SlideshowDialogFragment.newInstance();
                newFragment.setArguments(bundle);
                newFragment.show(ft,"slideshow");
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
*/
        fetchImages();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            finish(); // close this activity and return to preview activity (if there is any)
        }

        return super.onOptionsItemSelected(item);
    }

    private void  fetchImages(){
        pDialog.setMessage("Please Wait...");
        pDialog.show();

        JsonArrayRequest req = new JsonArrayRequest(endpoint,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d(TAG, response.toString());
                        pDialog.hide();

                        images.clear();
                        for (int i = 0 ;  i < response.length(); i++){
                            try {
                                JSONObject object = response.getJSONObject(i);
                                Image image = new Image();
                                image.setName(object.getString("name"));

                                JSONObject url =  object.getJSONObject("url");
                                image.setSmall(url.getString("small"));
                                image.setMedium(url.getString("medium"));
                                image.setLarge(url.getString("large"));
                                //Todo: put the price field in the DB
                                //image.setPrice(url.getString("price"));

                                images.add(image);

                            } catch (JSONException e) {
                                Log.e(TAG,"Json parsing error: " + e.getMessage());
                            }
                        }
                        mAdapter.notifyDataSetChanged();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG,"Error:"+ error.getMessage());
                pDialog.hide();
            }
        });

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(req,TAG);
    }

}
