package com.example.tetris;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.util.Log;

import java.util.HashSet;

/**
 * Created by sinjvf on 20.11.15.
 */
public class Drawing {
    int iW=0;
    int height, width;
    int shiftx=0,  shifty=0;
    Canvas canvas;
    GameScreen screen;
    public void setHW( int height, int width){
        this.height = height;
        this.width = width;
        iW = (width*3/4)/Const.NW; //height / Const.NH;
        this.shiftx = Const.SHIFTX;
        this.shifty = (height-iW*Const.NH)/2;
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
    public void drawFigure(Paint p, MyFigures fig) {
        p.setColor(Color.RED);
        p.setStyle(Paint.Style.FILL);
        HashSet<Point> hashSet = fig.getFieldsWithPosition();
        for (Point k : hashSet) {
            if (k.y>=0)
                drawRect(p, k.x, k.y);
        }
    }

    public void drawNextFigure(Paint p, MyFigures fig){
        p.setColor(Color.RED);
        p.setStyle(Paint.Style.FILL);
        HashSet<Point> hashSet = fig.getFieldsWithPosition();
        for (Point k : hashSet) {
            drawRectForNextFugure(p, k.x, k.y);
        }
    }

    /**
     * draw rectangle on canvas with p in (i, j) point
     */
    private void drawRect(Paint p, int i, int j) {
        canvas.drawRect(shiftx+i * iW + Const.TRACE,
                        shifty+j * iW + Const.TRACE,
                        shiftx+(i + 1) * iW - Const.TRACE,
                        shifty+(j + 1) * iW - Const.TRACE,
                        p);
    }
    private void drawRectForNextFugure(Paint p, int i, int j) {
        Integer iiw;
        iiw = iW/2;
        canvas.drawRect((i-1) * iiw + Const.TRACE+ iW*Const.NW,
                        (j+1) * iiw + Const.TRACE +iW*(Const.NH/2-3/2),
                        (i ) * iiw - Const.TRACE+ iW*Const.NW,
                        (j + 2) * iiw - Const.TRACE+iW*(Const.NH/2-3/2),
                        p);
    }
    /**
     * draw fulling rectangles
     */
    public void drawFullScreen(Paint p) {
        p.setColor(Color.RED);
        p.setStyle(Paint.Style.FILL);
        for (int i = 0; i < Const.NW; i++) {
            for (int j = 0; j < Const.NH; j++) {
                if (screen.isFull(i, j))
                    drawRect(p, i, j);
            }
        }
    }
    public void drawGrid(Integer score, int level, Paint p) {


        /** fill field by color*/
        canvas.drawARGB(255, 102, 204, 255);
        p.setStrokeWidth(Const.TRACE*2);
        p.setColor(Color.BLACK);
        for (int i = 0; i <=Const.NW; i++)
            canvas.drawLine(shiftx+i * iW,
                            shifty+0,
                            shiftx+i * iW,
                            shifty+iW*Const.NH,
                            p);
        for (int i = 0; i <= Const.NH; i++)
            canvas.drawLine(shiftx+0,
                            shifty+i * iW,
                            shiftx+iW * Const.NW,
                            shifty+ i * iW,
                            p);

        p.setColor(Color.BLACK);
        p.setTextAlign(Paint.Align.CENTER);
        p.setTextSize(30);
        canvas.drawText("Next:", (width + iW * Const.NW) / 2, height / 2 - 3*iW, p);
        canvas.drawText("Level:", (width + iW * Const.NW) / 2, height / 2, p);
        canvas.drawText(""+level, (width+iW*Const.NW)/2, height/2+iW, p);
        canvas.drawText("score:", (width + iW * Const.NW) / 2, height/2 + iW*3, p);
        canvas.drawText(""+score, (width+iW*Const.NW)/2, height/2+iW*4, p);



    }
}
