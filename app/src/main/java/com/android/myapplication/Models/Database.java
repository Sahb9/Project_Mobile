package com.android.myapplication.Models;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


import androidx.annotation.Nullable;

public class Database extends SQLiteOpenHelper {
    public Database(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    // truy vấn và không trả kết quả : CREATE,INSERT,UPDATE,DELETE...
    public void QueryData(String sql)
    {
        SQLiteDatabase database = getWritableDatabase();
        database.execSQL(sql);
    }
    // truy vấn có trả kết quả: SELECT
    public Cursor GetData(String sql)
    {
        //* Write viết luôn cũng 9c
        SQLiteDatabase database = getReadableDatabase();
        return database.rawQuery(sql,null);
    }
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}