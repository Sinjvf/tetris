package com.example.tetris;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.GestureDetector;
/**
 * Created by Sinjvf on 09.03.2015.
 */
public class GameActivity extends Activity implements View.OnTouchListener, View.OnClickListener, ListenerToMain, GestureDetector.OnGestureListener{
    Game game;
    final Activity act =this;
   // SurfaceView gameView;
    Button buttonRight, buttonLeft, buttonRotate, buttonDown,  buttonPause, buttonGameOver, buttonNewGame;
    //Thread gameThread;
    Float currentX, currentY;
    Float previosX, previosY;
    Float motionX, motionY;
    final static int RIGHT_MOTION = 1;
    final static int LEFT_MOTION  = -1;
    final static int UP_MOTION    = 2;
    final static int DOWN_MOTION  = -2;
    final static int ROTATE_MOTION = 3;
    final static int NOTHING = 0;
    int widht;
    int height;
    int typeOfMotion = 0;
    GestureDetector mDetector;

    private static final String TAG = "myLogs";



    public void onCreate(Bundle savedInstanceState) {
        game = new Game(this);
        game.setListenerToMain(this);
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.game_layout);
        LinearLayout surface = (LinearLayout)findViewById(R.id.linearLayout1);
        surface.addView(game);
        surface.setOnTouchListener(this);

        buttonRight = (Button)findViewById(R.id.button_right);
        buttonLeft = (Button)findViewById(R.id.button_left);
        buttonRotate = (Button)findViewById(R.id.button_rotate);
        buttonDown = (Button)findViewById(R.id.button_down);
        buttonPause = (Button)findViewById(R.id.button_pause);
        buttonGameOver = (Button)findViewById(R.id.button_game_over);
        buttonNewGame = (Button)findViewById(R.id.button_new_game);
        buttonRight.setOnClickListener(this);
        buttonLeft.setOnClickListener(this);
        buttonRotate.setOnClickListener(this);
        buttonDown.setOnClickListener(this);
        buttonPause.setOnClickListener(this);
        buttonGameOver.setOnClickListener(this);
        buttonGameOver.setClickable(false);
        buttonGameOver.setVisibility(View.INVISIBLE);
        buttonNewGame.setOnClickListener(this);
        mDetector = new GestureDetector(this,this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_left:
                game.moveFigure(game.getFCurrent(), -1, 0, 0);
                break;
            case R.id.button_right:
                game.moveFigure(game.getFCurrent(), 1, 0, 0);
                break;
            case R.id.button_rotate:
                game.moveFigure(game.getFCurrent(), 0, 0, 1);
                break;
            case R.id.button_down:
                game.moveFigure(game.getFCurrent(), 0, 1, 0);
                game.moveFigure(game.getFCurrent(), 0, 1, 0);
                break;
            case R.id.button_pause:
                game.setNotPause(!game.getNotPause());
                break;
            case R.id.button_new_game:
                game.newGame();

                break;
            case R.id.button_game_over:
                buttonGameOver.setClickable(false);
                buttonGameOver.setVisibility(View.INVISIBLE);
                game.newGame();
                break;
        }
    }




    @Override
    public boolean onTouch(View v, MotionEvent event) {
        widht = game.getWidth();
        height = game.getHeight();
        currentX = event.getX();
        currentY = event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN: // нажатие
                previosX = currentX;
                previosY = currentY;
                break;
            case MotionEvent.ACTION_MOVE: // движение




                break;
            case MotionEvent.ACTION_UP: // отпускание
        //        motionX = 0.0f;
        //        motionY = 0.0f;
                motionX = currentX - previosX;
                motionY = currentY - previosY;
                if (motionY > height/5){
                    typeOfMotion = DOWN_MOTION;}
                else if (motionX < -widht/5) {
                    Log.d(TAG, "left "+motionX+ " widght="+widht );
                    typeOfMotion = LEFT_MOTION;
                }
                else if (motionX > widht/5) {
                    Log.d(TAG, "right "+motionX+ " widght="+widht);
                    typeOfMotion = RIGHT_MOTION;
                }
                else {
                    Log.d(TAG, "Nothing! "+motionX+ " widght="+widht);
                    typeOfMotion = NOTHING;
                }
                switch (typeOfMotion) {
                    case RIGHT_MOTION:
                        game.moveFigure(game.getFCurrent(), 1, 0, 0);
                        break;
                    case LEFT_MOTION:
                        game.moveFigure(game.getFCurrent(), -1, 0, 0);
                        break;
                    case DOWN_MOTION:
                        game.moveFigure(game.getFCurrent(), 0, 1, 0);
                        break;
                }
                break;
        }
        mDetector.onTouchEvent(event);
        return true;
        }

    @Override
    public void onListenToMain(){
        buttonGameOver.setClickable(true);
        buttonGameOver.getHandler().post(
                new Runnable() {
                    public void run() {
                        buttonGameOver.setVisibility(View.VISIBLE);
                    }
                 }
        );
    }

    @Override
    public boolean onDown(MotionEvent event) {
        return true;
    }

    @Override
    public boolean onFling(MotionEvent event1, MotionEvent event2,
                           float velocityX, float velocityY) {
        return true;
    }

    @Override
    public void onLongPress(MotionEvent event) {
        game.setNotPause(!game.getNotPause());
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
                            float distanceY) {
        return true;
    }

    @Override
    public void onShowPress(MotionEvent event) {
    }

    @Override
    public boolean onSingleTapUp(MotionEvent event) {
        game.moveFigure(game.getFCurrent(), 0, 0, 1);
        Log.d(TAG, "single! ");
        return true;
    }


}