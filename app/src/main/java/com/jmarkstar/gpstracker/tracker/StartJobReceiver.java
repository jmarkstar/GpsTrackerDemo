package com.jmarkstar.gpstracker.tracker;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;
import com.jmarkstar.gpstracker.utils.Utils;

/** Es llamado desde el boton iniciar job o despues de que el dispositivo se despierte.
 * segun la version del S.O. inicializar el AlarmManager o el JobScheduler.
 * Created by jmarkstar on 24/05/2017.
 */
public class StartJobReceiver extends BroadcastReceiver {

    private static final String TAG = "StartJobReceiver";

    @Override public void onReceive(Context context, Intent intent) {
        Log.v(TAG, "StartJobReceiver starting");
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
            Utils.setJobScheduler(context);
        else
            Utils.setAlarmManager(context);
    }
}
