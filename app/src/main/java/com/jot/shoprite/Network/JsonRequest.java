package com.jot.shoprite.Network;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

/**
 * Created by D4n on 9/22/2016.
 */

public class JsonRequest extends Activity implements OnClickListener{

    private String TAG = JsonRequest.class.getSimpleName();
    private TextView mPrice;
    private TextView mItemName;
    private TextView id;


    //These tags will be used to cancel the requests
    private String tag_json_obj = "jobj_req", tag_json_array = "jarray_req";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onClick(View view) {

    }
}
