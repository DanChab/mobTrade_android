package com.jot.JotShop.Network;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.android.volley.Request.Method;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import com.jot.JotShop.R;
import com.jot.JotShop.Utils.Const;

import org.json.JSONObject;

/**
 * Created by D4n on 9/22/2016.
 */

public class JsonRequest extends Activity implements OnClickListener{

    private String TAG = JsonRequest.class.getSimpleName();
    private TextView mPrice;
    private TextView mItemName;
    private TextView mId;
    private ProgressDialog pDialog;

    //These tags will be used to cancel the requests
    private String tag_json_obj = "jobj_req", tag_json_array = "jarray_req";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mItemName = (TextView) findViewById(R.id.itemPrice);
        mPrice = (TextView) findViewById(R.id.itemPrice);
        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Loading...");
        pDialog.setCancelable(false);

    }

    private void showProgressDialog(){
        if (!pDialog.isShowing()){
            pDialog.show();
        }
    }

    private void hideProgressDialog(){
        if (pDialog.isShowing()){
            pDialog.hide();
        }
    }

    /**
     * Making json object request
     * */
    private void makeJasonObjReq(){
        showProgressDialog();
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Method.GET,
                Const.URL_JSONOBJECT,null,
                new  Response.Listener<JSONObject>(){
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d(TAG, response.toString());
                        mItemName.setText(response.toString());
                    }
                });
    }

    @Override
    public void onClick(View view) {

    }
}
