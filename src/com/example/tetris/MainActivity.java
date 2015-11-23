package com.example.tetris;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity implements View.OnClickListener {
    Button button_new, button_rating, button_exit_app;
    private DBHelper dbHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(new Game(this));
        setContentView(R.layout.main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        button_new = (Button)findViewById(R.id.button_new);
        button_rating = (Button)findViewById(R.id.button_rating);
        button_exit_app = (Button)findViewById(R.id.button_exit_app);
        button_new.setOnClickListener(this);
        button_rating.setOnClickListener(this);
        button_exit_app.setOnClickListener(this);
        dbHelper = new DBHelper(this);
    }
        @Override
        public void onClick(View v) {

            SQLiteDatabase db = dbHelper.getWritableDatabase();
            // Меняем текст в TextView (tvOut)
            switch (v.getId()) {
                case R.id.button_new:
                    Intent intent = new Intent(this, GameActivity.class);
                    startActivity(intent);
                    break;
                case R.id.button_exit_app:
                    this.finish();
                    break;
                case R.id.button_rating:
                    Intent intentr = new Intent(this, PrintResultsActivity.class);
                    startActivity(intentr);
                    Cursor c = null;
                    c = db.query("scores", null, null, null, null, null, "score");
                    if (c != null) {
                        if (c.moveToFirst()) {
                            String str;
                            do {
                                str = "";
                                for (String cn : c.getColumnNames()) {
                                    str = str.concat(cn + " = "
                                            + c.getString(c.getColumnIndex(cn)) + "; ");
                                }
                                Log.d(Const.LOG_TAG, str);

                            } while (c.moveToNext());
                        }
                        c.close();
                    } else
                        Log.d(Const.LOG_TAG, "Cursor is null");
                    break;
        }
            dbHelper.close();
    };


}