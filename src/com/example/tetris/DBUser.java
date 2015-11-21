package com.example.tetris;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by sinjvf on 20.11.15.
 */
public class DBUser extends SQLiteOpenHelper {
    public DBUser(Context context) {
        // конструктор суперкласса
        super(context, "myDB", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d(Const.LOG_TAG, "--- onCreate database ---");
        // создаем таблицу с полями
        db.execSQL("create table scores ("
                + "id integer primary key autoincrement,"
                + "name text,"
                + "score integer" + ");");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}

