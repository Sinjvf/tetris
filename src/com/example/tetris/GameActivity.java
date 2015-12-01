package com.example.tetris;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.*;
import android.widget.*;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.GestureDetector;
import android.os.Handler;

/**
 * Created by Sinjvf on 09.03.2015.
 */
public class GameActivity extends Activity implements View.OnTouchListener, View.OnClickListener, ListenerToMain, GestureDetector.OnGestureListener{

    Game game;
    final Activity act =this;
   // SurfaceView gameView;
    ImageButton buttonPause, buttonRight, buttonLeft, buttonRotate, buttonDown;
    Button  buttonGameOver, buttonExit;
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
    LinearLayout surface;
    DBUser db;
    TextView textView;
    Handler handler;




    public void onCreate(Bundle savedInstanceState) {
     //   Log.d(Const.LOG_TAG, "create " + this.toString());
        game = new Game(this);
        game.setListenerToMain(this);
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.game_layout);
        surface = (LinearLayout)findViewById(R.id.linearLayout1);
        surface.addView(game);
        surface.setOnTouchListener(this);
        handler = surface.getHandler();


        buttonRight = (ImageButton)findViewById(R.id.button_right);
        buttonLeft = (ImageButton)findViewById(R.id.button_left);
        buttonRotate = (ImageButton)findViewById(R.id.button_rotate);
        buttonDown = (ImageButton)findViewById(R.id.button_down);
        buttonPause = (ImageButton)findViewById(R.id.button_pause);
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



       // test.setClickable(false);
      //  test.setVisibility(View.INVISIBLE);


        db= new DBUser(this);
        textView = new TextView(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onStart() {
        super.onStart();
    //    Log.d(Const.LOG_TAG, "START");
    }

    @Override
    protected void onResume() {
        super.onResume();
        game.setNotPause(true);
      //  Log.d(Const.LOG_TAG, "RESUME");
    }

    @Override
    protected void onPause() {
        super.onPause();
        game.setNotPause(false);
      //  Log.d(Const.LOG_TAG, "PAUSE");
    }

    @Override
    protected void onStop() {
        super.onStop();
     //   Log.d(Const.LOG_TAG, "STOP");
    }

    private void checkResuts(){
        int score =game.getScore();
        game.stop();
        Log.d(Const.LOG_TAG, "Game Over! "+score);
        if (db.isPrintedResult(score)){
            Log.d(Const.LOG_TAG, "is Printed res");
            Intent intent = new Intent(this, SaveResultsActivity.class);
            intent.putExtra("score", score);
            startActivity(intent);
        }

        //  finish();
    }

    @Override
    public void onListenToMain(){
        Log.d(Const.LOG_TAG, "Show game over");
        //     checkResuts();
        //  finish();

        surface.getHandler().post(
                new Runnable() {
                    public void run() {

                        buttonGameOver.setClickable(true);
                        buttonGameOver.setVisibility(View.VISIBLE);
                        buttonPause.setClickable(false);
                        buttonDown.setClickable(false);
                        buttonLeft.setClickable(false);
                        buttonRight.setClickable(false);
                        buttonRotate.setClickable(false);



                    }
                }
        );
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
                checkResuts();
                this.finish();
                break;
            case R.id.button_game_over:
                checkResuts();
                this.finish();
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
                motionX = currentX - previosX;
                motionY = currentY - previosY;
                if (motionY > height/5){
                    typeOfMotion = DOWN_MOTION;
                    previosY = currentY;}
                else if (motionX < -widht/5) {
                    typeOfMotion = LEFT_MOTION;
                    previosX=currentX;
                }
                else if (motionX > widht/5) {
                    typeOfMotion = RIGHT_MOTION;
                    previosX=currentX;
                }
                else {
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
            case MotionEvent.ACTION_UP: // отпускание

                break;
        }
        mDetector.onTouchEvent(event);
        return true;
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
      //  Log.d(Const.LOG_TAG, "single! ");
        return true;
    }


}