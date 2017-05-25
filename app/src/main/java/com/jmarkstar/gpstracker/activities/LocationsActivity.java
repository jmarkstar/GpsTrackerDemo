package com.jmarkstar.gpstracker.activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.jmarkstar.gpstracker.R;

/**
 * Created by jmarkstar on 23/05/2017.
 * */
public class LocationsActivity extends AppCompatActivity implements View.OnClickListener {

    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;
    private static final String START_JOB_BROADCAST_RECIEVER = "com.jmarkstar.gpstracker.intent.action.START_JOB_FIRSTTIME";
    private static final String TAG = "LocationsActivity";

    private Button mBtnStartJob;
    private RecyclerView mRvLocations;
    private TextView mTvLocationListEmpty;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_locations);

        mBtnStartJob = (Button)findViewById(R.id.btn_start_job);
        mBtnStartJob.setOnClickListener(this);
        mRvLocations = (RecyclerView)findViewById(R.id.rv_locations);
        mTvLocationListEmpty = (TextView)findViewById(R.id.tv_locations_empty);
    }

    @Override public void onClick(View v) {
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                sendStartJobBroadcast();
            }else{
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, MY_PERMISSIONS_REQUEST_LOCATION);
            }
        }else{
            sendStartJobBroadcast();
        }
    }

    @Override public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    sendStartJobBroadcast();
                }
            }
        }
    }

    private void sendStartJobBroadcast(){
        Intent intent = new Intent();
        intent.setAction(START_JOB_BROADCAST_RECIEVER);
        sendBroadcast(intent);
    }
}
