package com.example.tetris.standart;

import android.graphics.*;
import com.example.tetris.Const;
import com.example.tetris.Drawing;
import com.example.tetris.GameScreen;
import com.example.tetris.MyFigures;

import java.util.HashSet;

/**
 * Created by sinjvf on 20.11.15.
 */
public class DrawingStandart extends Drawing{

    /**
     * draw figure on canvas with p
     */
    @Override
    public void drawFigure( MyFigures fig) {
        Paint p = new Paint();
        p.setColor(Color.RED);
        p.setStyle(Paint.Style.FILL);
        HashSet<Point> hashSet = fig.getFieldsWithPosition();
        for (Point k : hashSet) {
            if (k.y>=0)
                drawRect(p, k.x, k.y);
        }
    }
@Override
    public void drawNextFigure( MyFigures fig){
        Paint p = new Paint();
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
        canvas.drawRect((i-1) * iiw + Const.TRACE+ iW*Const.S_NW +1,
                        (j+1) * iiw + Const.TRACE +hShift+iW,
                        (i ) * iiw - Const.TRACE+ iW*Const.S_NW,
                        (j + 2) * iiw - Const.TRACE+hShift+iW,
                        p);
    }
    /**
     * draw fulling rectangles
     */
    @Override
    public void drawFullScreen() {
        Paint p = new Paint();
        p.setColor(Color.RED);
        p.setStyle(Paint.Style.FILL);
        for (int i = 0; i < Const.S_NW; i++) {
            for (int j = 0; j < Const.S_NH; j++) {
                if (screen.isFull(i, j))
                    drawRect(p, i, j);
            }
        }
    }
    @Override
    public void drawGrid(Integer score, int level) {
        Paint p = new Paint();

        /** fill field by color*/
        canvas.drawARGB(255, 102, 204, 255);
        p.setStrokeWidth(Const.TRACE*2);
        p.setColor(Color.BLACK);
        for (int i = 0; i <=Const.S_NW; i++)
            canvas.drawLine(shiftx+i * iW,
                            shifty+0,
                            shiftx+i * iW,
                            shifty+iW*Const.S_NH,
                            p);
        for (int i = 0; i <= Const.S_NH; i++)
            canvas.drawLine(shiftx+0,
                            shifty+i * iW,
                            shiftx+iW * Const.S_NW,
                            shifty+ i * iW,
                            p);

        drawMyText(score, level);

    }



    //my small crutch. Not used
public void drawGameOver(){
    Paint p1 = new Paint();
    Paint p = new Paint();
    Rect mTextBoundRect = new Rect();
    String text1 = "Game Over!";
    String text2 = "Press \"exit\" key to escape";
    float realWidth1, realWidth2, realHeight1, realHeight2;
    p1.setARGB(255, 52, 104, 255);
    p1.setStyle(Paint.Style.FILL);
    canvas.drawRect(iW+shiftx, shifty+6*iW, width - (shiftx+iW), height -(shifty+6*iW), p1 ) ;

    p.setColor(Color.BLACK);
    p.setTextSize(80);
    realWidth1 = p.measureText(text1);
    p.getTextBounds(text1, 0, text1.length(), mTextBoundRect);
    realHeight1 = mTextBoundRect.height();
    canvas.drawText(text1,
            width/2 - (realWidth1 / 2f),
            height/2 + (realHeight1 /2f-2*iW),
            p
    );

    p.setTextSize(30);
    realWidth2 = p.measureText(text2);
    p.getTextBounds(text2, 0, text2.length(), mTextBoundRect);
    realHeight2 = mTextBoundRect.height();
    canvas.drawText(text2,
            width/2 - (realWidth2 / 2f),
            height/2 + (realHeight2 /2f+iW),
            p
    );
}
}
