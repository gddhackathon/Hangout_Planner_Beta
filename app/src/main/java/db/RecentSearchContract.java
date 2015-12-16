package db;

import android.provider.BaseColumns;

/**
 * Created by AchsahSiri on 12/16/2015.
 */
public class RecentSearchContract {
    public RecentSearchContract() {}

    public static final String TABLE_NAME = "RecentSearchInformation";


    public static final String SQL_CREATE_TABLE = "CREATE TABLE IF NOT EXISTS " + RecentSearchContract.TABLE_NAME +
            " ("+ RecentSearchEntry.COLUMN_NAME_ADDRESS + " TEXT NOT NULL PRIMARY KEY," +
            RecentSearchEntry.COLUMN_NAME_LAT_LON + " TEXT NOT NULL " +")";

    public static abstract class RecentSearchEntry implements BaseColumns {
        public static final String COLUMN_NAME_ADDRESS = "address";
        public static final String COLUMN_NAME_LAT_LON = "latLon";
    }

}
