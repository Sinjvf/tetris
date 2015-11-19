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
    final int n_w, n_h;
    int i_w, i_h;
    HashMap<Integer, MyFigures> figuresHashMap;
    MyFigures fCurrent;
    MyFigures f_next;
    int stroke;
    GameScreen screen;
    Paint p;
    Canvas canvas;
    Random random;
    int num;
    boolean gameOver;
    Integer score;
    boolean notPause = true;
    ListenerToMain listenerToMain;

    void setListenerToMain(ListenerToMain listenerToMain){
        this.listenerToMain = listenerToMain;}

    private DrawThread drawThread;

    /**
     * constructor
     */
    public Game(Context context) {
        super(context);
        getHolder().addCallback(this);
        n_w = 10;
        n_h = 20;
     //   width = getWidth();
     //   height = getHeight();
        p = new Paint();
        stroke = 1;
        screen = new GameScreen(n_w, n_h);
        random = new Random(System.currentTimeMillis());
        num = 0;
        score = 0;
        gameOver = false;

    }

    public int getMyWidth() {
        return width;
    }
    public int getMyHeight() {
        return height;
    }

    public void newGame(){

        screen = new GameScreen(n_w, n_h);
        fCurrent = newFigure();
        random = new Random(System.currentTimeMillis());
        num = 0;
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
            } catch (InterruptedException e) {
            }
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

    /**
     * draw figure on canvas with p
     */
    private void drawFigure(Paint p, MyFigures fig) {
        p.setColor(Color.RED);
        p.setStyle(Paint.Style.FILL);
        HashSet<Point> hashSet = fig.getFieldsWithPosition();
        for (Point k : hashSet) {
            drawRect(p, k.x, k.y);
        }
    }

    /**
     * draw rectangle on canvas with p in (i, j) point
     */
    private void drawRect(Paint p, int i, int j) {
        canvas.drawRect(i * i_w + stroke, j * i_h + stroke, (i + 1) * i_w - stroke, (j + 1) * i_h - stroke, p);
    }

    /**
     * draw fulling rectangles
     */
    private void drawFullScreen(Paint p) {
        p.setColor(Color.RED);
        p.setStyle(Paint.Style.FILL);
        for (int i = 0; i < n_w; i++) {
            for (int j = 0; j < n_h; j++) {
                if (screen.isFull(i, j))
                    drawRect(p, i, j);
            }
        }
    }

    /**
     * create new figure
     */
    private MyFigures newFigure() {
        num = Math.abs(random.nextInt()) % 7;
        switch (num) {
            case 1:
                fCurrent = new F_L();
                break;
            case 2:
                fCurrent = new F_O();
                break;
            case 3:
                fCurrent = new F_P();
                break;
            case 4:
                fCurrent = new F_T();
                break;
            case 5:
                fCurrent = new F_Z();
                break;
            case 6:
                fCurrent = new F_Z_back();
                break;
            default:
                fCurrent = new F_I();
                break;
        }
        fCurrent.setCurrentMode(Math.abs(random.nextInt()) % 4);
        return fCurrent;
    }

    /**
     * draw grid for game
     */
    private void drawGrid() {

        i_h = height / n_h;
        i_w = i_h;
        /** fill field by color*/
        canvas.drawARGB(255, 102, 204, 255);
        p.setStrokeWidth(stroke * 2);
        p.setColor(Color.BLACK);
        for (int i = 0; i <=n_w; i++)
            canvas.drawLine( i * i_w, 0, i * i_w, i_h*n_h, p);
        for (int i = 0; i <= n_h; i++)
            canvas.drawLine(0, i * i_h, i_w * n_w, i * i_h, p);

        p.setColor(Color.BLACK);
        p.setTextAlign(Paint.Align.CENTER);
        p.setTextSize(30);
        canvas.drawText("score:", (width + i_w * n_w) / 2, height / 2, p);
        canvas.drawText(""+score, (width+i_w*n_w)/2, height/2+2*i_h, p);


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

        public DrawThread(SurfaceHolder surfaceHolder) {
            this.surfaceHolder = surfaceHolder;
            prevTime = System.currentTimeMillis();
            fCurrent = newFigure();
        }

        public void setRunning(boolean running) {
            this.running = running;
        }


        @Override
        public void run() {
            width = getWidth();
            height = getHeight();
            Log.d("myLogs", "WIDGH=" + width + "   HEIGHT=" + height);
            while (running) {
                while (notPause) {
                    now = System.currentTimeMillis();
                    elapsedTime = now - prevTime;
                    canvas = null;
                    if (elapsedTime > 200) {
                        if (moveFigure(fCurrent, 0, 1, 0)) {
                            prevTime = now;
                        } else if (!gameOver) fCurrent = newFigure();
                        try {
                            canvas = surfaceHolder.lockCanvas(null);
                            //                  if (canvas == null)
                            //                     continue;

                            drawGrid();
                            drawFullScreen(p);
                            drawFigure(p, fCurrent);
                            if (gameOver){
                                running = false;
                                }
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
