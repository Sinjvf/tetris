package com.example.tetris;

import android.content.Context;
import android.graphics.*;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import com.example.tetris.awry.GameScreenAwry;
import com.example.tetris.awry.MyFiguresAwry;
import com.example.tetris.standart.DrawingStandart;
import com.example.tetris.standart.GameScreenStandart;
import com.example.tetris.standart.MyFiguresStandart;
import com.example.tetris.awry.DrawingAwry;

import java.util.ArrayList;


/**
 * Created by Sinjvf on 21.02.2015.
 */
/** main game view*/
public class Game extends SurfaceView implements SurfaceHolder.Callback {

    private int width, height;
    private MyFigures fCurrent;
    private MyFigures fNext;
    private Drawing draw;
    private GameScreen screen;

    private Paint p;
    private Canvas canvas;
    private boolean gameOver;
    private Integer score, level;
    private volatile boolean notPause = true;
    private ListenerToMain listenerToMain;
    private DrawThread drawThread;
    private int pace;
    private int type;
    private ArrayList<Integer> checkingLevel;
   // boolean isFirstFlag; //This flag is setted  only when application start

    /**
     * constructor
     */
    public Game(Context context, int type) {
        super(context);
        newGame(type);
    }

    void setListenerToMain(ListenerToMain listenerToMain){
        this.listenerToMain = listenerToMain;}

    public Integer getScore() {
        return score;
    }

    public void newGame(int type){
        this.type = type;
        p = new Paint();
        score = 0;
        level =0;
        gameOver = false;
        checkingLevel = new ArrayList<Integer>();
        checkingLevel.add(0);
        for (int i=0; i<6; i++){
            checkingLevel.add(checkingLevel.get(i)+Const.POINT_FOR_LINE[i]*Const.LEAVE_LEVEL[i] );
        }
        getHolder().addCallback(this);
        switch (type){
            case Const.STANDART:
                screen = new GameScreenStandart(Const.NW[type], Const.NH[type]);
                fCurrent = MyFiguresStandart.newFigure();
                fNext = MyFiguresStandart.newFigure();
                draw = new DrawingStandart();
                break;
            case Const.AWRY:
                screen = new GameScreenAwry(Const.NW[type],  Const.NH[type]*2-1);
                fCurrent = MyFiguresAwry.newFigure();
                fNext = MyFiguresAwry.newFigure();
                draw = new DrawingAwry();
                break;
        }
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
        stop();
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
    public  synchronized  boolean moveFigure(MyFigures fig, int shiftX, int shiftY, int shift_mode) {
        int stay;

          Log.d("myLogs", " CHECK MOVE");
        if ((stay = screen.canMoveOrRotate(fig, shiftX, shiftY, shift_mode))==-1) {
            fig.setCurrentMode((fig.getCurrentMode()+shift_mode)%4);
            Log.d("myLogs", " MOVE");
            fig.move(shiftX,  shiftY);
            return true;
        } else if (stay == 2){
                    gameOver = true;
                    screen.fillFigureSpace(fig);
                    if (listenerToMain != null)
                        listenerToMain.onListenToMain();
                }
                else if (stay==1){
                    screen.fillFigureSpace(fig);
                    score+=screen.deleteLineIfNesessary()*Const.POINT_FOR_LINE[level];
                    checkAndSetLevel();
                }
            return false;
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
            setPace(level);
        }


    }


    class DrawThread extends Thread {
        private long prevTime;
        long now;
        long elapsedTime;
        private volatile boolean running = false;
        private SurfaceHolder surfaceHolder;


        public DrawThread(SurfaceHolder surfaceHolder) {
            this.surfaceHolder = surfaceHolder;
            prevTime = System.currentTimeMillis();

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
                                switch (type){
                                    case Const.STANDART:
                                        fNext = MyFiguresStandart.newFigure();
                                        break;
                                    case Const.AWRY:
                                        fNext = MyFiguresAwry.newFigure();
                                        break;
                                }
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
