package com.jot.JotShop.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.jot.JotShop.App.Config;
import com.jot.JotShop.R;
import com.jot.JotShop.Utils.NotificationUtils;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    // Define objects (Interface)
    Toolbar mToolbar;
    ImageView mShoppingBasket;
    ImageView mNotication;
    ImageView mList;
    ImageView mProfile;


    // Define objects (Firebase )
    private static final  String TAG = MainActivity.class.getSimpleName();
    private BroadcastReceiver mRegistrationBroadcastReceiver;
    //private TextView txtRegId;//Uncomment for debugging to display the Firebase token
    private TextView txtMessage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        //Interface instantiation
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        if (mToolbar != null)
            setSupportActionBar(mToolbar);

        mShoppingBasket = (ImageView) findViewById(R.id.ic_shoppingBasket);
        mNotication = (ImageView) findViewById(R.id.ic_notification);
        mList = (ImageView) findViewById(R.id.ic_list);
        mProfile = (ImageView) findViewById(R.id.ic_profile);

        mShoppingBasket.setOnClickListener(this);
        mNotication.setOnClickListener(this);
        mList.setOnClickListener(this);
        mProfile.setOnClickListener(this);


        //Firebase instantiation
        //txtRegId = (TextView) findViewById(R.id.txt_reg_id);//Uncomment for debugging to display the Firebase token from GCM
        txtMessage = (TextView) findViewById(R.id.txt_push_message);

        mRegistrationBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                // checking for type intent filter
                if (intent.getAction().equals(Config.REGISTRATION_COMPLETE)){
                    // gcm successfully registered
                    // now subscribe to `global` topic to receive app wide notifications
                   // displayFirebaseRegId();
                }else if (intent.getAction().equals(Config.PUSH_NOTIFICATION)){
                    //new push notification is received
                    String message = intent.getStringExtra("message");

                    Toast.makeText(getApplicationContext(),"Push notification: "+ message, Toast.LENGTH_LONG).show();

                    txtMessage.setText(message);
                }
            }
        };
        //displayFirebaseRegId();//Uncomment for debugging to display the Firebase token
    }
    /*
    // Fetches reg id from shared preferences
    // and displays on the screen
    private void displayFirebaseRegId() {
        SharedPreferences pref = getApplicationContext().getSharedPreferences(Config.SHARED_PREF, 0);
        String reId = pref.getString("regId", null);

        Log.e(TAG,"Firebase reg id: " + reId);

        if (!TextUtils.isEmpty(reId))
            txtRegId.setText("Firebase Reg Id: " + reId);
        else
            txtRegId.setText("Firebase Reg Id is not received yet!");
    }
    */
    @Override
    protected void onResume(){
        super.onResume();
        // register GCM registration complete receiver
        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter(Config.REGISTRATION_COMPLETE));


        // register new push message receiver
        // by doing this, the activity will be notified each time a new message arrives
        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter(Config.PUSH_NOTIFICATION));

        // clear the notification area when the app is opened
        NotificationUtils.clearNotifications(getApplicationContext());
    }
    @Override
    protected void onPause() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mRegistrationBroadcastReceiver);
        super.onPause();
    }


    //Clicks handler
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.ic_shoppingBasket:
                Intent intent = new Intent(MainActivity.this,ShopSectionActivity.class);
                startActivity(intent);

        }

    }
}
