package com.jmarkstar.gpstracker.services;

import android.app.IntentService;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.location.Location;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.jmarkstar.gpstracker.R;

/** Este intent service es usado por el JobScheduler directamente y por el AlarmManager
 * mediante la clase {@link GpsTrackerWakefulService}.
 * Created by jmarkstar on 24/05/2017.
 */
public class GpsTrackerIntentService extends IntentService implements LocationListener {

    private static final String TAG = "GpsTrackerIntentService";

    private long UPDATE_INTERVAL = 10 * 1000;  /* 10 secs */
    private long FASTEST_INTERVAL = 2000; /* 2 sec */

    private GoogleApiClient mGoogleApiClient;
    private LocationRequest mLocationRequest;

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
        mLocationRequest = LocationRequest.create()
            .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
            .setInterval(UPDATE_INTERVAL)
            .setFastestInterval(FASTEST_INTERVAL);
        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
    }

    private void showNotification(){
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this)
                        .setContentTitle("GCM Notification")
                        .setStyle(new NotificationCompat.BigTextStyle()
                                .bigText("Localizacion Obtenida"))
                        .setContentText("Localizacion Obtenida")
                        .setSmallIcon(R.mipmap.ic_launcher_round);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        notificationManager.notify(0, mBuilder.build());
    }

    @Override public void onLocationChanged(Location location) {
        if (location != null) {
            Log.v(TAG, String.format("%s: %f", "latitude", location.getLatitude()));
            Log.v(TAG, String.format("%s: %f", "longitude", location.getLongitude()));
        } else {
            Log.e(TAG, "Location no detected.");
        }

        LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
        if (mGoogleApiClient != null && mGoogleApiClient.isConnected()) {
            Log.v(TAG, "GoogleApiClient was disconnect.");
            mGoogleApiClient.disconnect();
        }
    }
}
