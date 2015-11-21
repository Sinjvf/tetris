package com.example.tetris;

import android.content.Context;
import android.graphics.*;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

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
    int stroke;
    GameScreen screen;
    Paint p;
    Canvas canvas;
    boolean gameOver;
    Integer score;
    boolean notPause = true;
    ListenerToMain listenerToMain;
    private DrawThread drawThread;

    /**
     * constructor
     */
    public Game(Context context) {
        super(context);
        getHolder().addCallback(this);
        p = new Paint();
        screen = new GameScreen(Const.NW, Const.NH);
        score = 0;
        gameOver = false;

    }

    void setListenerToMain(ListenerToMain listenerToMain){
        this.listenerToMain = listenerToMain;}

    public Integer getScore() {
        return score;
    }
    public void newGame(){
        screen = new GameScreen(Const.NW, Const.NH);
        fCurrent = MyFigures.newFigure();
        fNext = MyFigures.newFigure();
        score = 0;
        gameOver = false;
        getHolder().removeCallback(this);
        getHolder().addCallback(this);
    }
    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width,
                               int height) {

    }
    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        drawThread = new DrawThread(getHolder());
        drawThread.setRunning(true);
        drawThread.start();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        boolean retry = true;
        drawThread.setRunning(false);
        while (retry) {
            try {
                drawThread.join();
                retry = false;
            } catch (InterruptedException e) {}
        }
    }
    public void setNotPause(boolean pause)
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
            //   fig.rotate(shift_mode);
            //             Toast.makeText(this, fig.currentMode + "  rotate!", Toast.LENGTH_SHORT).show();
            fig.move(shift_i, shift_j);
            return true;
        } else {
            int stay;
            if ((stay = screen.mustStay(fig)) > 0) {
                if (stay == 2){
                    gameOver = true;
                    screen.fillFigureSpace(fig);
                    if (listenerToMain != null)
                        listenerToMain.onListenToMain();
                }
                else {
                    screen.fillFigureSpace(fig);
                    score+=screen.deleteLineIfNesessary();
                }
            }
            return false;
        }
    }
    public MyFigures getFCurrent() {
        return fCurrent;
    }
    class DrawThread extends Thread {
        //Timing
        private long prevTime;
        long now;
        long elapsedTime;
        private boolean running = false;
        private SurfaceHolder surfaceHolder;
        Drawing draw;

        public DrawThread(SurfaceHolder surfaceHolder) {
            this.surfaceHolder = surfaceHolder;
            prevTime = System.currentTimeMillis();
            fCurrent = MyFigures.newFigure();
            fNext = MyFigures.newFigure();
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
            Log.d("myLogs", "WIDGH=" + width + "   HEIGHT=" + height);
            while (running) {
                while (notPause) {
                    now = System.currentTimeMillis();
                    elapsedTime = now - prevTime;
                    canvas = null;
                    if (elapsedTime > 200) {
                        if (moveFigure(fCurrent, 0, 1, 0)) {
                            prevTime = now;
                        } else if (!gameOver) {
                            fCurrent = fNext;
                            fNext = MyFigures.newFigure();}
                        try {
                            canvas = surfaceHolder.lockCanvas(null);
                            if (canvas != null){
                                draw.setCanvas(canvas);
                                draw.setScreen(screen);
                                draw.drawGrid(score, p);
                                draw.drawFullScreen(p);
                                draw.drawFigure(p, fCurrent);
                                draw.drawNextFigure(p, fNext);
                                if (gameOver){
                                    running = false;
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
