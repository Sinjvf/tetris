package com.example.tetris;

import android.graphics.*;
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


    public void setHW( int height, int width){
        this.height = height;
        this.width = width;
        hShift = height/2-iW*3/2;
        iW = (width*3/4)/ Const.S_NW; //height / Const.S_NH;
        this.shiftx = Const.SHIFTX;
        this.shifty = (height-Const.S_NH*iW)/2;
    }

    public void setCanvas(Canvas canvas) {
        this.canvas = canvas;
    }

    public void setScreen(GameScreen screen) {
        this.screen = screen;
    }

    /**
     * draw figure on canvas with p
     */
    public abstract void drawFigure( MyFigures fig);
    public abstract void drawNextFigure( MyFigures fig);
    public abstract void drawFullScreen() ;
    public abstract void drawGrid(Integer score, int level);


    protected void drawMyText(Integer score, int level){
        Paint p=new Paint();
        p.setColor(Color.BLACK);
        p.setTextAlign(Paint.Align.CENTER);
        p.setTextSize(30);
        canvas.drawText("Next:", (width + iW * Const.S_NW) / 2, hShift-0.5f, p);
        canvas.drawText("Level:", (width + iW * Const.S_NW) / 2, hShift+iW*3, p);
        canvas.drawText(""+level, (width+iW*Const.S_NW)/2, hShift+iW*4, p);
        canvas.drawText("score:", (width + iW * Const.S_NW) / 2, hShift + iW*6, p);
        canvas.drawText(""+score, (width+iW*Const.S_NW)/2, hShift+iW*7, p);
    }

}
