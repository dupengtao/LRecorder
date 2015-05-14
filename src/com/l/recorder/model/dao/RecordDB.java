package com.l.recorder.model.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by zhangjiahao on 15-5-14.
 */
public class RecordDB extends SQLiteOpenHelper {

    private static String DB_NAME = "record.db";

    public static String RECORD_TABLE = "record";

    public static String _ID = "_id";
    public static String RECORD_NAME = "recordName";
    public static String RECORD_PATH = "recordPath";
    public static String RECORD_TIME = "recordTime";
    public static String RECORD_DURING = "recordDuring";
    public static String RECORD_IS_CALL = "recordIsCall";
    public static String RECORD_FLAGS = "recordFlags";

    public RecordDB(Context context) {
        this(context, DB_NAME, null, 1);
    }

    public RecordDB(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "create table " + RECORD_TABLE + "(" + _ID + " integer  primary key autoincrement," + RECORD_PATH + " text," + RECORD_NAME + " text," + RECORD_TIME + " integer," + RECORD_DURING
                + " integer," + RECORD_FLAGS + " text," + RECORD_IS_CALL + " integer default 0" + ")";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }


}
