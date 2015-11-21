package com.example.tetris;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
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
    ImageButton buttonRight, buttonLeft, buttonRotate, buttonDown;
    Button buttonPause, buttonGameOver, buttonExit;
    //Thread gameThread;
    Float currentX, currentY;
    Float previosX, previosY;
    Float motionX, motionY;
    private final static int RIGHT_MOTION = 1;
    private final static int LEFT_MOTION  = -1;
    private final static int UP_MOTION    = 2;
    private final static int DOWN_MOTION  = -2;
    private final static int ROTATE_MOTION = 3;
    private final static int NOTHING = 0;
    int widht;
    int height;
    int typeOfMotion = 0;
    GestureDetector mDetector;
    private DBUser dbUser;
    



    public void onCreate(Bundle savedInstanceState) {
        game = new Game(this);
        game.setListenerToMain(this);
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.game_layout);
        LinearLayout surface = (LinearLayout)findViewById(R.id.linearLayout1);
        surface.addView(game);
        surface.setOnTouchListener(this);

        buttonRight = (ImageButton)findViewById(R.id.button_right);
        buttonLeft = (ImageButton)findViewById(R.id.button_left);
        buttonRotate = (ImageButton)findViewById(R.id.button_rotate);
        buttonDown = (ImageButton)findViewById(R.id.button_down);
        buttonPause = (Button)findViewById(R.id.button_pause);
        buttonGameOver = (Button)findViewById(R.id.button_game_over);
        buttonExit = (Button)findViewById(R.id.button_exit);
        buttonRight.setOnClickListener(this);
        buttonLeft.setOnClickListener(this);
        buttonRotate.setOnClickListener(this);
        buttonDown.setOnClickListener(this);
        buttonPause.setOnClickListener(this);
        buttonGameOver.setOnClickListener(this);
        buttonGameOver.setClickable(false);
        buttonGameOver.setVisibility(View.INVISIBLE);
        buttonExit.setOnClickListener(this);
        mDetector = new GestureDetector(this,this);
        dbUser = new DBUser(this);
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
            case R.id.button_exit:
                this.finish();
                break;
            case R.id.button_game_over:
                setNewGame();
                break;
        }

    }
private  void saveScore(){}
 private void setNewGame(){
     SQLiteDatabase db = dbUser.getWritableDatabase();
     ContentValues cv = new ContentValues();
     String name = "Sinvjf";
     buttonGameOver.setClickable(false);
     buttonGameOver.setVisibility(View.INVISIBLE);
     cv.put("name", name);
     cv.put("score", game.getScore());
     db.insert("scores", null, cv);
     dbUser.close();
     game.newGame();
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
                motionX = currentX - previosX;
                motionY = currentY - previosY;
                if (motionY > height/5){
                    typeOfMotion = DOWN_MOTION;}
                else if (motionX < -widht/5) {
                    Log.d(Const.LOG_TAG, "left "+motionX+ " widght="+widht );
                    typeOfMotion = LEFT_MOTION;
                }
                else if (motionX > widht/5) {
                    Log.d(Const.LOG_TAG, "right "+motionX+ " widght="+widht);
                    typeOfMotion = RIGHT_MOTION;
                }
                else {
                    Log.d(Const.LOG_TAG, "Nothing! "+motionX+ " widght="+widht);
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
        Log.d(Const.LOG_TAG, "single! ");
        return true;
    }


}