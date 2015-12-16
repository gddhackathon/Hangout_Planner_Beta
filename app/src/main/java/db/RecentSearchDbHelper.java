package db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

import model.RecentSearch;

/**
 * Created by AchsahSiri on 12/16/2015.
 */
public class RecentSearchDbHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "GddHangout.db";

    public RecentSearchDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(RecentSearchContract.SQL_CREATE_TABLE);
        System.out.print("Table created" + RecentSearchContract.SQL_CREATE_TABLE);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.i(RecentSearchDbHelper.class.getSimpleName(),
                "Upgrading database from version " + oldVersion + " to " + newVersion);
    }

    public void insertResentSearch(String address, String latLon) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contactValues = new ContentValues();
        contactValues.put(RecentSearchContract.RecentSearchEntry.COLUMN_NAME_ADDRESS, address);
        contactValues.put(RecentSearchContract.RecentSearchEntry.COLUMN_NAME_LAT_LON, latLon);
        long rowId = db.insert(RecentSearchContract.TABLE_NAME, null, contactValues);
        System.out.println("rowId" + rowId);
        db.close();
    }

    public ArrayList<RecentSearch> getResentSearches() {
        ArrayList<RecentSearch> recentSearches = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(RecentSearchContract.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                null);
        cursor.moveToNext();
        try {
            while (cursor.moveToNext()) {
                RecentSearch recentSearch = new RecentSearch();
                recentSearch.setAddress(cursor.getString(cursor.getColumnIndex(RecentSearchContract.RecentSearchEntry.COLUMN_NAME_ADDRESS)));
                recentSearch.setLatLon(cursor.getString(cursor.getColumnIndex(RecentSearchContract.RecentSearchEntry.COLUMN_NAME_LAT_LON)));
                recentSearches.add(recentSearch);
            }
        }
        finally {
            cursor.close();
            db.close();
        }
        return (recentSearches);
    }

}
