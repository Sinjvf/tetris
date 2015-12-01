package com.example.tetris;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.Cursor;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by sinjvf on 23.11.15.
 */
public class DBUser {
    DBHelper dbHelper;
    SQLiteDatabase db;
    private static String queryCount = "SELECT COUNT(*) FROM "+Const.TABLE_NAME;
    private static String having = "MIN("+Const.SCORE_COLUMN+")";
    private static String groupBy = Const.SCORE_COLUMN;
    DBUser( Context context){
        dbHelper = new DBHelper(context);
        db = dbHelper.getWritableDatabase();
    }


    public void printResult(String name, int score){
        db = dbHelper.getWritableDatabase();
        ContentValues cv = new ContentValues();
      //  if (name.length()!=0)
        //    name=null;
        cv.put("name", name);
        cv.put("score", score);
        Cursor cursor2 = db.rawQuery(queryCount, null);
        if (cursor2!=null) {
            if (cursor2.moveToFirst()) {
                if(cursor2.getInt(0)<10){
                    db.insert(Const.TABLE_NAME, null, cv);
                }
                else {

                    Cursor cursor = db.query(Const.TABLE_NAME,
                            null, null, null,
                            null, null, Const.SCORE_COLUMN+" DESC") ;
                    if (cursor!=null) {
                        if (cursor.moveToFirst()) {
                            db.delete(Const.TABLE_NAME, "id="+cursor.getColumnIndex(Const.SCORE_COLUMN),null );
                            db.insert(Const.TABLE_NAME, null, cv);
                        }
                        cursor.close();
                    }

                }
            }
            cursor2.close();
        }
        db.close();
    }

    public void deleteRes(){
        db = dbHelper.getWritableDatabase();
        db.delete(Const.TABLE_NAME, null,null );
        db.close();
    }
    //shows is result one of the best
    public boolean isPrintedResult(int score){
        Integer minScore=0;
        Integer rowCount=0;

        db = dbHelper.getWritableDatabase();
        Cursor cursor2 = db.rawQuery(queryCount, null);
        if (cursor2!=null) {
            if (cursor2.moveToFirst()) {
                rowCount = cursor2.getInt(0);
            }
            cursor2.close();
        }
        if (rowCount<10) {
            db.close();
            return true;}
        Cursor cursor = db.query(Const.TABLE_NAME,
                null, null, null,
                null, null, Const.SCORE_COLUMN) ;

        if (cursor!=null) {
            if (cursor.moveToFirst()) {
                minScore = cursor.getInt(cursor.getColumnIndex(Const.SCORE_COLUMN));
            }
            cursor.close();
        }
        Log.d(Const.LOG_TAG, "min= "+minScore+" now= "+score);
        db.close();
        if (score> minScore) {
            return true;
        }
        return false;
    }
}
