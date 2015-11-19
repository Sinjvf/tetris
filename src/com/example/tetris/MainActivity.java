package com.example.tetris;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity implements View.OnClickListener {
Button b1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(new Game(this));
        setContentView(R.layout.main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        b1 = (Button)findViewById(R.id.button_new);
        b1.setOnClickListener(this);

    }
        @Override
        public void onClick(View v) {
            // Меняем текст в TextView (tvOut)
            switch (v.getId()) {
                case R.id.button_new:

                    Intent intent = new Intent(this, GameActivity.class);
                    startActivity(intent);

                    break;

        }
    };


}