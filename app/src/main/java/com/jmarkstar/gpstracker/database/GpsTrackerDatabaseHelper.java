package com.jmarkstar.gpstracker.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.jmarkstar.gpstracker.models.LocationModel;

/**
 * Created by jmarkstar on 23/05/2017.
 */
public final class GpsTrackerDatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "gps_tracker.db";
    private static final int DATABASE_VERSION  = 1;

    private StringBuffer SQL_CREATE_TABLE_LOCATION = new StringBuffer()
            .append("CREATE TABLE IF NOT EXISTS "+ LocationModel.TABLE_NAME+" ( ")
            .append(LocationModel.ID_FIELD+" INTEGER PRIMARY KEY AUTOINCREMENT, ")
            .append(LocationModel.LATITUDE_FIELD+" REAL, ")
            .append(LocationModel.LONGITUDE_FIELD+" REAL, ")
            .append(LocationModel.DATE_FIELD+" INTEGER )");

    private final StringBuffer SQL_DELETE_TABLE_LOCATION = new StringBuffer()
            .append("DROP TABLE IF EXISTS " + LocationModel.TABLE_NAME);

    public GpsTrackerDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_TABLE_LOCATION.toString());
    }

    @Override public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_TABLE_LOCATION.toString());
        onCreate(db);
    }
}
