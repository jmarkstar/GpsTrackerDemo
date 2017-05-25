package com.jmarkstar.gpstracker.ui;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.jmarkstar.gpstracker.R;
import com.jmarkstar.gpstracker.database.dao.LocationDao;
import com.jmarkstar.gpstracker.models.LocationModel;
import com.jmarkstar.gpstracker.utils.SharedPreferencesUtil;

import java.util.List;

/**
 * Created by jmarkstar on 23/05/2017.
 * */
public class LocationsActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "LocationsActivity";

    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;
    private static final String START_JOB_BROADCAST_RECEIVER = "com.jmarkstar.gpstracker.intent.action.START_JOB_FIRSTTIME";
    public static final String GET_NEW_LOCATION_BROADCAST_RECEIVER = "com.jmarkstar.gpstracker.intent.action.GET_NEW_LOCATION";
    public static final String GET_NEW_LOCATION_PARAM = "new_location";

    private Button mBtnStartJob;
    private RecyclerView mRvLocations;
    private TextView mTvLocationListEmpty;

    private LocationAdapter locationAdapter;
    private List<LocationModel> locations;

    private SharedPreferencesUtil sharedPreferencesUtil;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_locations);

        sharedPreferencesUtil = new SharedPreferencesUtil(this);
        bindViews();

        LocalBroadcastManager.getInstance(this).registerReceiver(mNewLocationReceiver,
                new IntentFilter(GET_NEW_LOCATION_BROADCAST_RECEIVER));

        locationAdapter = new LocationAdapter();
        LocationDao locationDao = new LocationDao(this);
        locations = locationDao.getAll();
        locationAdapter.addList(locations);
        showlist();

        mRvLocations.setLayoutManager( new LinearLayoutManager(this));
        mRvLocations.setAdapter(locationAdapter);
    }

    @Override protected void onDestroy() {
        super.onDestroy();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mNewLocationReceiver);
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

    private BroadcastReceiver mNewLocationReceiver = new BroadcastReceiver(){

        @Override public void onReceive(Context context, Intent intent) {
            if(intent!=null){
                LocationModel newLocation = intent.getParcelableExtra(GET_NEW_LOCATION_PARAM);
                Log.v(TAG, "new location = "+newLocation.toString());
                locations.add(newLocation);
                locationAdapter.notifyDataSetChanged();
                if(mRvLocations.getVisibility() == View.GONE)
                    showlist();
            }
        }
    };

    private void bindViews(){
        mBtnStartJob = (Button)findViewById(R.id.btn_start_job);
        if(!sharedPreferencesUtil.getFlagJob()){
            mBtnStartJob.setOnClickListener(this);
            mBtnStartJob.setText(R.string.main_btn_start_job_text);
        }else{
            mBtnStartJob.setText(R.string.locations_running_text);
            mBtnStartJob.setEnabled(false);
        }

        mRvLocations = (RecyclerView)findViewById(R.id.rv_locations);
        mTvLocationListEmpty = (TextView)findViewById(R.id.tv_locations_empty);
    }

    /** Envía una actión al BroadcastReceiver que ejecutará el AlarmManager or JobScheduler.
     * */
    private void sendStartJobBroadcast(){
        sharedPreferencesUtil.setFlagJob(true);
        mBtnStartJob.setText(R.string.locations_running_text);
        mBtnStartJob.setEnabled(false);
        Intent intent = new Intent();
        intent.setAction(START_JOB_BROADCAST_RECEIVER);
        sendBroadcast(intent);
    }

    private void showlist(){
        if(locations.size() > 0){
            mRvLocations.setVisibility(View.VISIBLE);
            mTvLocationListEmpty.setVisibility(View.GONE);
        }else{
            mRvLocations.setVisibility(View.GONE);
            mTvLocationListEmpty.setVisibility(View.VISIBLE);
        }
    }
}
