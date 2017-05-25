package com.jmarkstar.gpstracker.tracker;

import android.content.Intent;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.util.Log;

/** Este intent service será usando junto al AlarmManager para version de android menores a 5.0.
 * Created by jmarkstar on 24/05/2017.
 */
public class GpsTrackerWakefulService extends GpsTrackerIntentService {

    private static final String TAG = "GpsTrackerIntentService";

    @Override protected void onHandleIntent(@Nullable Intent intent) {
        super.onHandleIntent(intent);
        Log.i(TAG, "Completed service @ " + SystemClock.elapsedRealtime());
        if(intent!=null)
            AlarmWakefulReceiver.completeWakefulIntent(intent);
    }
}
