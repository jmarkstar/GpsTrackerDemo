package com.jmarkstar.gpstracker.tracker;

import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;
import androidx.legacy.content.WakefulBroadcastReceiver;
import android.util.Log;

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
