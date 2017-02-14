package com.last.androsia.last;

import android.provider.BaseColumns;

/**
 * Created by SPhilipps on 1/3/2017.
 */

public final class DBContract {
    public static final  int    DATABASE_VERSION   = 1;
    public static final  String DATABASE_NAME      = "Last.db";
    private static final String TEXT_TYPE          = " TEXT";
    private static final String BLOB_TYPE          = " BLOB";
    private static final String COMMA_SEP          = ",";

    private DBContract() {}

    public static class TagItem implements BaseColumns {
        public static final String TABLE_NAME = "TagItems";
        public static final String COLUMN_TITLE = "title";
        public static final String COLUMN_IMG = "img";
        public static final String COLUMN_CTR_SEEN = "ctrSeen";
        public static final String COLUMN_CTR_OWNED = "ctrOwned";
        public static final String COLUMN_TYPE = "type";
        public static final String COLUMN_DATE = "date";

        public static final String CREATE_TABLE = "CREATE TABLE " +
                TABLE_NAME + " (" +
                _ID + " INTEGER PRIMARY KEY," +
                COLUMN_TITLE + TEXT_TYPE + COMMA_SEP +
                COLUMN_IMG + BLOB_TYPE + COMMA_SEP +
                COLUMN_CTR_SEEN + TEXT_TYPE + COMMA_SEP +
                COLUMN_CTR_OWNED + TEXT_TYPE + COMMA_SEP +
                COLUMN_TYPE + TEXT_TYPE + COMMA_SEP +
                COLUMN_DATE + TEXT_TYPE + " )";
        public static final String DELETE_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;
    }
}
