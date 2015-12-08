package com.example.tetris;

import android.graphics.*;
import android.util.Log;
import com.example.tetris.standart.MyFiguresStandart;

import java.util.HashSet;

/**
 * Created by sinjvf on 20.11.15.
 */
public abstract class Drawing {
    protected int iW=0;
    protected int height, width;
    protected int shiftx=0,  shifty=0;  //grid shifts
    protected int hShift =0; //text shift
    protected Canvas canvas;
    protected GameScreen screen;


    public void setCanvas(Canvas canvas) {
        this.canvas = canvas;
    }
    public void setScreen(GameScreen screen) {
        this.screen = screen;
    }


    public abstract void setHW( int height, int width);


    public void drawFigure( MyFigures fig) {
        Paint p = new Paint();
        p.setColor(Color.RED);
        p.setStyle(Paint.Style.FILL);
        Log.d("myLogs", "DRAW FIG");
        HashSet<Point> hashSet = fig.getFieldsWithPosition();
        for (Point k : hashSet) {
            if (k.y>=0)
                drawField(p, k.x, k.y);
        }
    }
    public void drawNextFigure( MyFigures fig){
        Paint p = new Paint();
        p.setColor(Color.RED);
        p.setStyle(Paint.Style.FILL);
        Log.d("myLogs", "DRAW NEXT FIG");
        HashSet<Point> hashSet = fig.getFieldsWithPosition();
        for (Point k : hashSet) {
            drawFieldForNextFugure(p, k.x, k.y);
        }
    }
    public abstract void drawFullScreen() ;
    public abstract void drawGrid(Integer score, int level);
    protected abstract  void drawField(Paint p, int i, int j);
    protected abstract void drawFieldForNextFugure(Paint p, int i, int j);



    protected void drawMyText(Integer score, int level,  int wShift, int hShift){
        Paint p=new Paint();
        p.setColor(Color.BLACK);
        p.setTextAlign(Paint.Align.CENTER);
        p.setTextSize(30);
        canvas.drawText("Next:", wShift, hShift-0.5f, p);
        canvas.drawText("Level:", wShift, hShift+iW*3, p);
        canvas.drawText(""+level, wShift, hShift+iW*4, p);
        canvas.drawText("score:", wShift, hShift + iW*5, p);
        canvas.drawText(""+score, wShift, hShift+iW*6, p);
    }

}
