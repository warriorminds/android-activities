package com.warriorminds.activities;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


/*
* Each Activity that you create should extend Activity. There are other options to extend from, like
* AppCompatActivity, FragmentActivity and ActionBarActivity.
*
* Remember to add an entry in AndroidManifest.xml for each Activity that you create. (Android Studio
* does this for you if you create an Activity -> Activity type.
* */
public class MainActivity extends Activity {

    private final String TAG = getClass().getSimpleName();

    private Button btnStartActivity, btnStartExplicit, btnStartImplicit;
    public static final int START_ACTIVITY_REQUEST_CODE = 1000;
    public static final String SAVE_INSTANCE_STATE_KEY = "instance_state";

    /* This callback is used when the activity is being created.
    *  In here you can get the reference for each view in your layout,
    *  and restore values from the saved instance state.
    *
    *  In here also you need to set your activity's layout.
    *
    *  This is the only required method that you must implement of the activity's lifecycle.
    * */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Add your layout here.
        setContentView(R.layout.activity_main);

        Log.d(TAG, "onCreate() - creating the activity.");

        /*
        * If savedInstanceState is not null, it means that it is not the first time the activity
        * is created, so we can check for saved values to restore instance state.
        * This is important because EVERY TIME YOU ROTATE YOUR PHONE, THE ACTIVITY IS DESTROYED AND
        * RECREATED. And you want the user to keep the experience and values that he had before
        * rotating the phone.
        * */
        if(savedInstanceState != null){
            String savedValue = savedInstanceState.getString(SAVE_INSTANCE_STATE_KEY);
            Toast.makeText(this, savedValue, Toast.LENGTH_SHORT).show();
        }

        btnStartExplicit = (Button) findViewById(R.id.btnStartActivityExplicit);
        btnStartExplicit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*
                * Start your activity with an explicit intent.
                * You tell the intent the current context that wants to open the new activity,
                * and also you tell it the activity to be opened.
                * Then you only need to call startActivity() and pass the intent created.
                * */
                Intent intent = new Intent(MainActivity.this, OtherActivity.class);
                startActivity(intent);
            }
        });

        btnStartImplicit = (Button) findViewById(R.id.btnStartActivityImplicit);
        btnStartImplicit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*
                * You create an implicit intent, telling the action that you want to perform.
                * All the activities that know how to respond to this action will be prompted
                * and the user can select which one to use.
                * */
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("http://www.google.com"));
                startActivity(intent);
            }
        });

        btnStartActivity = (Button) findViewById(R.id.btnSstartActivity);
        btnStartActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*
                * You create the intent the same way, but now you use startActivityForResult
                * and you send here the intent and the request code.
                * */
                Intent intent = new Intent(MainActivity.this, OtherActivity.class);
                startActivityForResult(intent, START_ACTIVITY_REQUEST_CODE);
            }
        });
    }

    /*
    * This callback is used when the activity is already created and is about to
    * become visible.
    *
    * Here you can start/register different components like Broadcast receivers.
    * This method is also called if your activity was in stop state and gained focus again.
    * */
    @Override
    protected void onStart() {
        super.onStart();

        Log.d(TAG, "onStart() - Starting the activity, about to become visible.");
    }

    /*
    * This callback is used when the activity is now visible and the user is interacting
    * with it.
    *
    * Here you can start foreground-only behaviors within your activity.
    * */
    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume() - activity is now visible to the user.");
    }

    /*
    * This callback is used when your activity is still visible but it has lost its focus.
    * For example, when there is a dialog over your app. It is also called when you are exiting
    * your activity - it goes through all the methods until it is destroyed.
    *
    * Here you need to stop foreground-only behaviors and most important of all, you should
    * save any values that should persist in your app. There is no guarantee that the next
    * methods will be called, so this is important to be done here.
    * */
    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause() - losing focus, but still visible.");
    }

    /*
    * This callback is used when your activity is still alive but is no longer visible.
    * If the system doesn't kill your activity, and if it is opened again, from here it will
    * go to onRestart callback method.
    *
    * It is not guaranteed that this callback is used, if the system kills your activity first
    * it will not be called.
    * */
    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop() - activity is no longer visible.");
    }

    /*
    * This callback is the last one. It is called when your activity is destroyed.
    *
    * You can release resources here.
    * */
    @Override
    protected void onDestroy() {
        super.onDestroy();

        Log.d(TAG, "onDestroy() - destroying the activity.");
    }

    /*
    * This callback is used after onStop, when the user goes back to the activity and the activity
    * is not destroyed first.
    * */
    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(TAG, "onRestart() - restarting the activity.");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == START_ACTIVITY_REQUEST_CODE){
            switch(resultCode){
                case Activity.RESULT_OK:
                    String message = data.getExtras().getString(OtherActivity.RESULT_EXTRA);
                    Toast.makeText(this, "RESULT_OK, message: " + message, Toast.LENGTH_SHORT).show();
                    break;
                case Activity.RESULT_CANCELED:
                    message = data.getExtras().getString(OtherActivity.RESULT_EXTRA);
                    Toast.makeText(this, "RESULT_CANCELED, message: " + message, Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    }

    /*
    * This method is called before destroying your app. You need to save some activity state here
    * so you can restore it in the onCreate method when the activity is created again.
    * */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.d(TAG, "onSaveInstanceState");
        outState.putString(SAVE_INSTANCE_STATE_KEY, "saved on instance state.");
    }
}