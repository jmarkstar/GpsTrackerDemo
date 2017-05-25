package com.jmarkstar.gpstracker.database.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.jmarkstar.gpstracker.database.GpsTrackerDatabaseHelper;
import com.jmarkstar.gpstracker.models.LocationModel;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/** Location Dao.
 * Created by jmarkstar on 23/05/2017.
 */
public class LocationDao {

    private final String ALL_COLUMNS [] = {
            LocationModel.ID_FIELD,
            LocationModel.LATITUDE_FIELD,
            LocationModel.LONGITUDE_FIELD,
            LocationModel.DATE_FIELD
    };

    private SQLiteDatabase mSQLiteDatabase;

    public LocationDao(Context context) {
        mSQLiteDatabase = new GpsTrackerDatabaseHelper(context).getWritableDatabase();
    }

    public long insertLocation(LocationModel locationModel){
        ContentValues contentValues = new ContentValues();
        contentValues.put(LocationModel.LATITUDE_FIELD, locationModel.getLatitude());
        contentValues.put(LocationModel.LONGITUDE_FIELD, locationModel.getLongitude());
        contentValues.put(LocationModel.DATE_FIELD, locationModel.getDate().getTime());
        return mSQLiteDatabase.insert(LocationModel.TABLE_NAME, null, contentValues);
    }

    public List<LocationModel> getAll(){
        Cursor mCursor = mSQLiteDatabase.query(true, LocationModel.TABLE_NAME, ALL_COLUMNS, null, null, null, null, null, null);
        return convertCursorToList(mCursor);
    }

    private List<LocationModel> convertCursorToList(Cursor mCursor){
        List<LocationModel> locations = new ArrayList<>();
        try{
            if(mCursor.moveToFirst()){
                do {
                    LocationModel locationItem = new LocationModel();
                    locationItem.setId(mCursor.getInt(mCursor.getColumnIndex(LocationModel.ID_FIELD)));
                    locationItem.setLatitude(mCursor.getDouble(mCursor.getColumnIndex(LocationModel.LATITUDE_FIELD)));
                    locationItem.setLongitude(mCursor.getDouble(mCursor.getColumnIndex(LocationModel.LONGITUDE_FIELD)));
                    locationItem.setDate(new Date(mCursor.getLong(mCursor.getColumnIndex(LocationModel.DATE_FIELD))));

                    locations.add(locationItem);
                }while (mCursor.moveToNext());
            }
        }finally {
            if (mCursor != null && !mCursor.isClosed())
                mCursor.close();
        }
        return locations;
    }

    public void closeDataBase(){
        mSQLiteDatabase.close();
    }
}
