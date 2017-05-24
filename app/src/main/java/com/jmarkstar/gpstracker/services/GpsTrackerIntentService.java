package com.jmarkstar.gpstracker.services;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.util.Log;

/** Este intent service es usado por el JobScheduler directamente y por el AlarmManager
 * mediante la clase {@link GpsTrackerWakefulService}.
 * Created by jmarkstar on 24/05/2017.
 */
public class GpsTrackerIntentService extends IntentService {

    private static final String TAG = "GpsTrackerIntentService";

    public GpsTrackerIntentService() {
        super("GpsTrackerIntentService");
    }

    @Override protected void onHandleIntent(@Nullable Intent intent) {
        Log.v(TAG, "GpsTrackerIntentService ran!");
    }
}
