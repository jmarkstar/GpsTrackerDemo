package com.jmarkstar.gpstracker.activities;

import android.content.Intent;
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
        Intent intent = new Intent();
        intent.setAction(START_JOB_BROADCAST_RECIEVER);
        sendBroadcast(intent);
    }
}
