package com.jmarkstar.gpstracker.tracker;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.location.Location;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.jmarkstar.gpstracker.ui.LocationsActivity;
import com.jmarkstar.gpstracker.database.dao.LocationDao;
import com.jmarkstar.gpstracker.models.LocationModel;
import java.util.Date;

/** Este intent service es usado por el JobScheduler directamente y por el AlarmManager
 * mediante la clase {@link GpsTrackerWakefulService}.
 * Created by jmarkstar on 24/05/2017.
 */
public class GpsTrackerIntentService extends IntentService implements LocationListener {

    private static final String TAG = "GpsTrackerIntentService";

    private long UPDATE_INTERVAL = 10 * 1000;  /* 10 secs */
    private long FASTEST_INTERVAL = 2000; /* 2 sec */

    private GoogleApiClient mGoogleApiClient;

    public GpsTrackerIntentService() {
        super("GpsTrackerIntentService");
    }

    @Override protected void onHandleIntent(@Nullable Intent intent) {
        Log.v(TAG, "GpsTrackerIntentService ran!");
        startGoogleApiClient();

    }

    private synchronized void startGoogleApiClient() {
        if(mGoogleApiClient == null){
            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(new GoogleApiClient.ConnectionCallbacks(){

                        @Override public void onConnected(@Nullable Bundle bundle) throws SecurityException {
                            Log.i(TAG, "Connection connected");
                            registerRequestUpdate();
                        }

                        @Override public void onConnectionSuspended(int i) {
                            Log.i(TAG, "Connection suspended");
                            mGoogleApiClient.connect();
                        }
                    })
                    .addOnConnectionFailedListener(new GoogleApiClient.OnConnectionFailedListener() {
                        @Override public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
                            Log.i(TAG, "Connection failed: ConnectionResult.getErrorCode() = " + connectionResult.getErrorCode());
                        }
                    })
                    .addApi(LocationServices.API)
                    .build();
            mGoogleApiClient.connect();
        }else{
            mGoogleApiClient.connect();
        }
    }

    private void registerRequestUpdate() throws SecurityException {
        LocationRequest mLocationRequest = LocationRequest.create()
            .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
            .setInterval(UPDATE_INTERVAL)
            .setFastestInterval(FASTEST_INTERVAL);
        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
    }

    @Override public void onLocationChanged(Location location) {
        if (location != null) {
            sendNewLocation(location);
        } else {
            Log.e(TAG, "Location no detected.");
        }

        LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
        if (mGoogleApiClient != null && mGoogleApiClient.isConnected()) {
            Log.v(TAG, "GoogleApiClient was disconnect.");
            mGoogleApiClient.disconnect();
        }
    }

    /** Sends an event to {@link LocationsActivity}.
     * */
    private void sendNewLocation(Location location){
        LocationModel locationModel = new LocationModel();
        locationModel.setLatitude(location.getLatitude());
        locationModel.setLongitude(location.getLongitude());
        locationModel.setDate(new Date());

        saveNewLocation(locationModel);

        Intent intent = new Intent(LocationsActivity.GET_NEW_LOCATION_BROADCAST_RECEIVER);
        intent.putExtra(LocationsActivity.GET_NEW_LOCATION_PARAM, locationModel);
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
    }

    /** Saves new location on the database.
     * */
    private void saveNewLocation(LocationModel locationModel){
        LocationDao locationDao = new LocationDao(this);
        long success = locationDao.insertLocation(locationModel);
        locationDao.closeDataBase();
        if(success >=0)
            Log.v(TAG, "New location was added, id = "+success);
        else
            Log.e(TAG, "Error: new location was not added.");
    }
}
