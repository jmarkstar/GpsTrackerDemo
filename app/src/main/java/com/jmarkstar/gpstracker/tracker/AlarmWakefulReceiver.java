package com.jmarkstar.gpstracker.tracker;

import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;
import android.support.v4.content.WakefulBroadcastReceiver;
import android.util.Log;
import com.jmarkstar.gpstracker.tracker.GpsTrackerWakefulService;

/**
 * Created by jmarkstar on 24/05/2017.
 */
public class AlarmWakefulReceiver extends WakefulBroadcastReceiver {

    private static final String TAG = "AlarmWakefulReceiver";

    @Override public void onReceive(Context context, Intent intent) {
        Intent service = new Intent(context, GpsTrackerWakefulService.class);

        Log.i(TAG, "Starting service @ " + SystemClock.elapsedRealtime());
        startWakefulService(context, service);
    }
}
