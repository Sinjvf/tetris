package com.example.tetris;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

/**
 * Created by sinjvf on 20.11.15.
 */
public class PrintResultsActivity extends Activity implements View.OnClickListener {
    Button button_exit, button_erase;
    private DBHelper dbHelper;
    TableLayout resTable;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(new Game(this));
        setContentView(R.layout.results);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        button_exit = (Button)findViewById(R.id.button_exit_from_res);
        button_erase = (Button)findViewById(R.id.button_erase);
        resTable = (TableLayout)findViewById(R.id.res_table);
        button_exit.setOnClickListener(this);
        button_erase.setOnClickListener(this);
        dbHelper = new DBHelper(this);
        printResults();
        //resTable.addView(new TableRow(this), );
    }
    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.button_exit_from_res:
                this.finish();
                break;
            case R.id.button_erase:
                DBUser dbUser = new DBUser(this);
                dbUser.deleteRes();
                Intent intentr = new Intent(this, PrintResultsActivity.class);
                startActivity(intentr);
                finish();
                break;
        }
    }

    public void printResults(){
        DBHelper dbHelper;
        dbHelper = new DBHelper(this);
        SQLiteDatabase db;
        db = dbHelper.getWritableDatabase();
        Cursor cursor = db.query(Const.TABLE_NAME,
                null, null, null,
                null, null, Const.SCORE_COLUMN+" DESC") ;

        if (cursor!=null) {
            if (cursor.moveToFirst()) {
                int nameColIndex = cursor.getColumnIndex("name");
                int scoreColIndex = cursor.getColumnIndex("score");
                Integer i=0;
                do {
                    i++;
                    Log.d(Const.LOG_TAG, "READ_DB");
                    TableRow record = new TableRow(this);
                    record.setGravity(Gravity.CENTER_HORIZONTAL);
                    TextView place = new TextView(this);
                    TextView tname = new TextView(this);
                    TextView tscore = new TextView(this);
                    place.setTextSize(25);
                    tname.setTextSize(25);
                    tscore.setTextSize(25);
                    place.setGravity(Gravity.CENTER);
     //               tname.setGravity(Gravity.RIGHT);
    //                tscore.setGravity(Gravity.RIGHT);
                  //  place.getResources().getColor(R.color.bgreen);
                    tname.setText(cursor.getString(nameColIndex));
                    tscore.setText(cursor.getString(scoreColIndex));
                    place.setText(i.toString());
                    record.addView(place);
                    record.addView(tname);
                    record.addView(tscore);
                    resTable.addView(record);


                } while (cursor.moveToNext());
            }
            cursor.close();
        }
        db.close();





    }
}
