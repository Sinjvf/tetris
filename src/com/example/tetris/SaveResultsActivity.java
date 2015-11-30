package com.example.tetris;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by sinjvf on 23.11.15.
 */
public class SaveResultsActivity extends Activity implements View.OnClickListener {
    Integer score;
    String name;
    Button button_ok, button_cancel;
    EditText textName;
    TextView textScore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.save_results);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        button_ok = (Button)findViewById(R.id.button_save_res);
        button_cancel = (Button)findViewById(R.id.button_cancel_res);
        button_ok.setOnClickListener(this);
        button_cancel.setOnClickListener(this);
        textName = (EditText)findViewById(R.id.winnerName);
        textScore = (TextView)findViewById(R.id.textScore);

        Intent intent = getIntent();
        score = intent.getIntExtra("score", 0);
      //  Log.d(Const.LOG_TAG, "score = "+score);
        textScore.setText(score.toString());
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button_save_res:
                saveRes();
                finish();
                break;
            case R.id.button_cancel_res:
                this.finish();
                break;

        }
    }
    private void saveRes(){
        DBUser db = new DBUser(this);
        name = textName.getText().toString();
        db.printResult(name, score);
    }
}
