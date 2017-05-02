package com.Jot.Jot.activity;

import android.app.ProgressDialog;
import android.content.res.Resources;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.TypedValue;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.Jot.Jot.App.AppController;
import com.Jot.Jot.Const.Const;
import com.Jot.Jot.R;
import com.Jot.Jot.adapter.ProductAdapter;
import com.Jot.Jot.adapter.ProductViewAdapter;
import com.Jot.Jot.model.Product;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


/**
 * Created by D4n on 1/20/2017.
 */

public class ClientProfileMain extends AppCompatActivity {

    public static final String KEY_ID = "clientId";

    private String TAG = ClientProfileMain.class.getSimpleName();
    private RecyclerView mRecyclerView;
    private ProgressDialog pDialog;
    private ProductAdapter mAdapter;
    private ArrayList<Product> products;

    //Retrieve the value of the client selected from the list in the ClientProfile
    String clientId;
    String clientSlogan;
    String clientBackDrop;
    String clientName;


    // private TextView mName, mSlogan;
    //private ImageView mBackdrop;
    //private List<Album> albumList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.client_profile_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        initCollapsingToolbar();

        // add back arrow to toolbar
        if (getSupportActionBar() != null){
            final Drawable upArrow = getResources().getDrawable(R.drawable.abc_ic_ab_back_material);
            upArrow.setColorFilter(getResources().getColor(R.color.White), PorterDuff.Mode.SRC_ATOP);
            getSupportActionBar().setHomeAsUpIndicator(upArrow);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        TextView mName = (TextView) findViewById(R.id.tv_client_name);
        TextView mSlogan = (TextView) findViewById(R.id.tv_client_slogan);
        ImageView mBackdrop = (ImageView) findViewById(R.id.iv_backdrop);

        getProfileDetails(mName, mSlogan, mBackdrop);

        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        pDialog = new ProgressDialog(this);
        products = new ArrayList<>();
        mAdapter = new ProductAdapter(getApplicationContext(), products);

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 2);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(10), true));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(mAdapter);

        // Fetching image in big format

        mRecyclerView.addOnItemTouchListener(new ProductViewAdapter.RecyclerTouchListener(getApplicationContext(), mRecyclerView, new ProductViewAdapter.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("products", products);
                bundle.putInt("position", position);

                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                SlideshowDialogFragment newFragment = SlideshowDialogFragment.newInstance();
                newFragment.setArguments(bundle);
                newFragment.show(ft, "slideshow");
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

        doGetProducts();
        doReceiveProducts();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            finish(); // close this activity and return to preview activity (if there is any)
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * Initializing collapsing toolbar
     * Will show and hide the toolbar productName on scroll
     */
    private void initCollapsingToolbar() {

        final CollapsingToolbarLayout collapsingToolbar =
                (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbar.setTitle(" ");
        AppBarLayout appBarLayout = (AppBarLayout) findViewById(R.id.appbar);
        appBarLayout.setExpanded(true);

        // hiding & showing the productName when toolbar expanded & collapsed
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow = false;
            int scrollRange = -1;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                }
                if (scrollRange + verticalOffset == 0) {
                    collapsingToolbar.setTitle(getString(R.string.app_name));
                    isShow = true;
                } else if (isShow) {
                    collapsingToolbar.setTitle(" ");
                    isShow = false;
                }
            }
        });
    }

    /**
     This dude get parameters and set the header of the the client profile  with the backdrop, name and slogan
     */
    public void getProfileDetails(TextView name, TextView slogan, ImageView backdrop){
        Bundle getbundle = getIntent().getExtras();
        if (getbundle !=null){
            clientId = getbundle.getString("clientId");
            clientSlogan = getbundle.getString("clientSlogan");
            clientBackDrop = getbundle.getString("clientBackDrop");
            clientName = getbundle.getString("clientName");

            Log.d(TAG,"SELECTED USER HERE "+clientId );
            Log.d(TAG,"SELECTED USER HERE "+clientSlogan );
            Log.d(TAG,"SELECTED USER HERE "+clientBackDrop );
            Log.d(TAG,"SELECTED USER HERE "+clientName );

            name.setText(clientName);
            slogan.setText(clientSlogan);

            //For the image Glide will do the trick...
            try {
                Glide.with(this).load(clientBackDrop).into( backdrop);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

    }

    /**
     This dude requests data from the server for the client selected from the list.
     */
public void doGetProducts() {
    //Request data
    StringRequest stringRequest = new StringRequest(Request.Method.POST, Const.DATA_URL_ADVERTISER_PROFILE,
            new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    JSONObject jObj = new JSONObject();
                }
            }, new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {

        }
    }) {
        @Override
        protected Map<String, String> getParams() {
            Map<String, String> params = new HashMap<>();
            params.put(KEY_ID, clientId);
            return params;
        }
    };
    RequestQueue requestQueue = Volley.newRequestQueue(this);
    requestQueue.add(stringRequest);
}

    /**
     This dude handles data from the server requested earlier.
     */
    private void doReceiveProducts() {

        pDialog.setMessage("Loading...");
        pDialog.show();

        JsonArrayRequest req = new JsonArrayRequest(Const.DATA_URL_ADVERTISER_PROFILE,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d(TAG, response.toString());
                        pDialog.hide();

                        products.clear();
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject object = response.getJSONObject(i);
                                Product product = new Product();
                                product.setName(object.getString(Const.TAG_PRODUCT_NAME));
                                product.setPrice(object.getString(Const.TAG_PRODUCT_PRICE));
//                                JSONObject url = object.getJSONObject("url");
                                product.setSmall(object.getString(Const.TAG_PRODUCT_IMAGE_URL));
//                                product.setMedium(url.getString("medium"));
//                                product.setLarge(url.getString("large"));

                                products.add(product);

                            } catch (JSONException e) {
                                Log.e(TAG, "Json parsing error: " + e.getMessage());
                            }
                        }

                        mAdapter.notifyDataSetChanged();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Error: " + error.getMessage());
                pDialog.hide();

                //Network Handling
                if (error.networkResponse == null) {
                    //Show timeout error message
                    Toast.makeText(getApplicationContext(), "Could Not retrieve Please Try later...", Toast.LENGTH_LONG).show();
                }
            }
        });

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(req, TAG);


    }
    /**
     * RecyclerView item decoration - give equal margin around grid item
     */
    public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {

        private int spanCount;
        private int spacing;
        private boolean includeEdge;

        public GridSpacingItemDecoration(int spanCount, int spacing, boolean includeEdge) {
            this.spanCount = spanCount;
            this.spacing = spacing;
            this.includeEdge = includeEdge;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            int position = parent.getChildAdapterPosition(view); // item position
            int column = position % spanCount; // item column

            if (includeEdge) {
                outRect.left = spacing - column * spacing / spanCount; // spacing - column * ((1f / spanCount) * spacing)
                outRect.right = (column + 1) * spacing / spanCount; // (column + 1) * ((1f / spanCount) * spacing)

                if (position < spanCount) { // top edge
                    outRect.top = spacing;
                }
                outRect.bottom = spacing; // item bottom
            } else {
                outRect.left = column * spacing / spanCount; // column * ((1f / spanCount) * spacing)
                outRect.right = spacing - (column + 1) * spacing / spanCount; // spacing - (column + 1) * ((1f /    spanCount) * spacing)
                if (position >= spanCount) {
                    outRect.top = spacing; // item top
                }
            }
        }
    }

    /**
     * Converting dp to pixel
     */
    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }
}
