package com.example.tetris;

import android.content.Context;
import android.graphics.*;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;


/**
 * Created by Sinjvf on 21.02.2015.
 */
/** main game view*/
public class Game extends SurfaceView implements SurfaceHolder.Callback {

    int width, height;
    MyFigures fCurrent;
    MyFigures fNext;
    GameScreen screen;
    Paint p;
    Canvas canvas;
    boolean gameOver;
    Integer score, level;
    volatile boolean notPause = true;
    ListenerToMain listenerToMain;
    private DrawThread drawThread;
    int pace;
    ArrayList<Integer> checkingLevel;
   // boolean isFirstFlag; //This flag is setted  only when application start

    /**
     * constructor
     */
    public Game(Context context) {
        super(context);
        newGame();
    }

    void setListenerToMain(ListenerToMain listenerToMain){
        this.listenerToMain = listenerToMain;}

    public Integer getScore() {
        return score;
    }
    public void newGame(){
        screen = new GameScreen(Const.NW, Const.NH);
        p = new Paint();
        fCurrent = MyFigures.newFigure();
        fNext = MyFigures.newFigure();
        score = 0;
        level =0;
        gameOver = false;
        checkingLevel = new ArrayList<Integer>();
        checkingLevel.add(0);
        for (int i=0; i<6; i++){
            checkingLevel.add(checkingLevel.get(i)+Const.POINT_FOR_LINE[i]*Const.LEAVE_LEVEL[i] );
        }
        getHolder().addCallback(this);
    }
    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width,
                               int height) {
   //     Log.d(Const.LOG_TAG, "----CHANGE");

    }
    @Override
    public void surfaceCreated(SurfaceHolder holder) {
     //   Log.d(Const.LOG_TAG, "-----CREATE");
        drawThread = new DrawThread(getHolder());
        drawThread.setRunning(true);
        drawThread.start();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
      //  boolean retry = true;
        stop();
        //drawThread.interrupt();
/*        while (retry) {
            try {
                drawThread.join();
                retry = false;
            } catch (InterruptedException e) {}
        }*/
    }

    public  synchronized void stop() {
        this.setNotPause(false);
        drawThread.setRunning(false);

    }

    public synchronized void setNotPause(boolean pause)
    {
        notPause = pause;
    }
    public boolean getNotPause()
    {
        return notPause;
    }
    public boolean moveFigure(MyFigures fig, int shift_i, int shift_j, int shift_mode) {
        if (screen.canMoveOrRotate(fig, shift_i, shift_j, shift_mode)) {
            fig.setCurrentMode((fig.currentMode+shift_mode)%4);
            fig.move(shift_i, shift_j);
            return true;
        } else {
            int stay;
            if ((stay = screen.mustStay(fig, shift_i, shift_j)) > 0) {
                if (stay == 2){
                    gameOver = true;
                    screen.fillFigureSpace(fig);
                    if (listenerToMain != null)
                        listenerToMain.onListenToMain();
                }
                else {
                    screen.fillFigureSpace(fig);
                    score+=screen.deleteLineIfNesessary()*Const.POINT_FOR_LINE[level];
                    checkAndSetLevel();
                }
            }
            return false;
        }
    }
    public MyFigures getFCurrent() {
        return fCurrent;
    }

    private synchronized void setPace(int level){
        pace = Const.PACE[level];
    }
    private synchronized void checkAndSetLevel(){
        while ((level <7) && (score>=checkingLevel.get(level+1))){
            level++;
        }


    }


    class DrawThread extends Thread {
        //Timing
        private long prevTime;
        long now;
        long elapsedTime;
        private volatile boolean running = false;
        private SurfaceHolder surfaceHolder;
        Drawing draw;

        public DrawThread(SurfaceHolder surfaceHolder) {
            this.surfaceHolder = surfaceHolder;
            prevTime = System.currentTimeMillis();
            draw = new Drawing();
        }

        public void setRunning(boolean running) {
            this.running = running;
        }


        @Override
        public void run() {
            width = getWidth();
            height = getHeight();
            draw.setHW(height, width);
          //  Log.d("myLogs", "WIDGH=" + width + "   HEIGHT=" + height);
            while (running) {
                while (notPause) {
                    now = System.currentTimeMillis();
                    elapsedTime = now - prevTime;
                    canvas = null;
                    if (elapsedTime > Const.PACE[level]) {
                        if (moveFigure(fCurrent, 0, 1, 0)) {
                            prevTime = now;
                        } else
                            if (!gameOver) {
                                fCurrent = fNext;
                                fNext = MyFigures.newFigure();
                            }
                        try {
                            canvas = surfaceHolder.lockCanvas(null);
                            if (canvas != null){
                                draw.setCanvas(canvas);
                                draw.setScreen(screen);
                                draw.drawGrid(score, level);
                                draw.drawFullScreen();
                                draw.drawFigure(fCurrent);
                                draw.drawNextFigure( fNext);
                                if (gameOver){
                                    notPause = false;
                                    running = false;
                                //    draw.drawGameOver();
                                    }
                            }
                            else {running = false;}
                        } finally {
                            if (canvas != null) {
                                surfaceHolder.unlockCanvasAndPost(canvas);
                            }
                        }
                    }
                }
            }

        }
    }

}
