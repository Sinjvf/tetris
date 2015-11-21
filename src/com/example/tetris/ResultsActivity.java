package com.example.tetris;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

/**
 * Created by sinjvf on 20.11.15.
 */
public class ResultsActivity extends Activity implements View.OnClickListener {
    Button button_exit;
    private DBUser dbUser;
    TableLayout resTable;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(new Game(this));
        setContentView(R.layout.results);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        button_exit = (Button)findViewById(R.id.button_exit_from_res);
        resTable = (TableLayout)findViewById(R.id.res_table);
        button_exit.setOnClickListener(this);
        dbUser = new DBUser(this);
        TableRow record = new TableRow(this);
        record.setGravity(Gravity.CENTER_HORIZONTAL);
        TextView tname = new TextView(this);
        TextView tscore = new TextView(this);
        tname.setText("Sinjvf");
        tscore.setText("100500");
        record.addView(tname, 1, 1);
        record.addView(tscore, 0, 0);
        resTable.addView(record, 0, 0);
        //resTable.addView(new TableRow(this), );
    }
    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.button_exit_from_res:
                this.finish();
                break;
        }
    }
}
