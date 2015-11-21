package com.example.tetris;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;

import java.util.HashSet;

/**
 * Created by sinjvf on 20.11.15.
 */
public class Drawing {
    int iW=0;
    int height, width;
    Canvas canvas;
    GameScreen screen;
    public void setHW( int height, int width){
        this.height = height;
        this.width = width;
        iW = height / Const.NH;
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
        canvas.drawRect(i * iW + Const.TRACE,
                        j * iW + Const.TRACE,
                        (i + 1) * iW - Const.TRACE,
                        (j + 1) * iW - Const.TRACE,
                        p);
    }
    private void drawRectForNextFugure(Paint p, int i, int j) {
        Integer iiw, iiW;
        iiw = iW/2;
        iiW = iW/2;
        canvas.drawRect(i * iiw + Const.TRACE + iW*Const.NW,
                        j * iiW + Const.TRACE +iW*(Const.NH/2-3/2),
                        (i + 1) * iiw - Const.TRACE+iW*Const.NW,
                        (j + 1) * iiW - Const.TRACE+iW*(Const.NH/2-3/2),
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
    public void drawGrid(Integer score, Paint p) {


        /** fill field by color*/
        canvas.drawARGB(255, 102, 204, 255);
        p.setStrokeWidth(Const.TRACE*2);
        p.setColor(Color.BLACK);
        for (int i = 0; i <=Const.NW; i++)
            canvas.drawLine(i * iW,
                            0,
                            i * iW,
                            iW*Const.NH,
                            p);
        for (int i = 0; i <= Const.NH; i++)
            canvas.drawLine(0,
                            i * iW,
                            iW * Const.NW,
                            i * iW,
                            p);

        p.setColor(Color.BLACK);
        p.setTextAlign(Paint.Align.CENTER);
        p.setTextSize(30);
        canvas.drawText("score:", (width + iW * Const.NW) / 2, height / 2, p);
        canvas.drawText(""+score, (width+iW*Const.NW)/2, height/2+2*iW, p);
        canvas.drawText("next:", (width + iW * Const.NW) / 2, height / 2 - 3*iW, p);



    }
}
