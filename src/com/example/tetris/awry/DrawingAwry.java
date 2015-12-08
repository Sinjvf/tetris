package com.example.tetris.awry;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.util.Log;
import com.example.tetris.Const;
import com.example.tetris.Drawing;
import com.example.tetris.MyFigures;

import java.util.HashSet;

/**
 * Created by sinjvf on 20.11.15.
 */
public class DrawingAwry extends Drawing{


    @Override
    public void setHW( int height, int width){
        this.height = height;
        this.width = width;
        iW = (width*3/4)/ (Const.NW[Const.AWRY]); //height
        iW= (iW%2==0)?iW:iW-1;
        hShift = height/2-iW*3/2;
        this.shiftx = Const.SHIFTX;
        this.shifty = (height-Const.NH[Const.AWRY]*iW)/2;
    }



    /**
     * draw rectangle on canvas with p in (i, j) point
     */
    @Override
    protected void drawField(Paint p, int i, int j) {
        Path path=new Path();
        int absI, absJ;
        path.reset();
        p.setStyle(Paint.Style.FILL);
        absJ = shifty+iW*j/2;
        absI = (j%2==0)
                        ?(int)(shiftx+(i+1)*iW)
                        :(int)(shiftx+(i+0.5f)*iW);


        path.moveTo(absI, absJ+Const.TRACE);
        path.lineTo(absI-iW/2+Const.TRACE, absJ+iW/2);
        path.lineTo(absI, absJ+iW-(Const.TRACE+1));
        path.lineTo(absI+iW/2-(Const.TRACE+1), absJ+iW/2);
        path.close();
        canvas.drawPath(path, p);
    }
    @Override
    protected  void drawFieldForNextFugure(Paint p, int i, int j) {
        Integer iiw;
        iiw = iW/2;
        Path path=new Path();
        int absI, absJ;
        path.reset();
        p.setStyle(Paint.Style.FILL);
        absJ = hShift+iW*2+iiw*j/2;
        absI = iW*(Const.NW[Const.AWRY] )+
                ((j%2==0) ?
                        (int)(shiftx+(i+1)*iiw)
                        :(int)(shiftx+(i+0.5f)*iiw));;
        path.moveTo(absI, absJ+Const.TRACE);
        path.lineTo(absI-iiw/2+Const.TRACE, absJ+iiw/2);
        path.lineTo(absI, absJ+iiw-(Const.TRACE+1));
        path.lineTo(absI+iiw/2-(Const.TRACE+1), absJ+iiw/2);
        path.close();
        canvas.drawPath(path, p);
    }

    /**
     * draw fulling rectangles
     */
    @Override
    public void drawFullScreen() {
        Paint p = new Paint();
        p.setColor(Color.RED);
        p.setStyle(Paint.Style.FILL);
        for (int i = 0; i < Const.NW[Const.AWRY]; i++) {
            for (int j = 0; j < Const.NH[Const.AWRY]*2-1; j++) {
                if (screen.isFull(i, j) && !(j%2==0  && i==Const.NW[Const.AWRY]-1)){
                    drawField(p, i, j);
                }
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

        for (int j=0; j<Const.NH[Const.AWRY];j++) {
            for (int i = 0; i < Const.NW[Const.AWRY] ; i++) {
                canvas.drawLine(shiftx + i * iW,
                        shifty + j * iW,
                        shiftx + (i + 1) * iW,
                        shifty + (j + 1) * iW,
                        p);

                canvas.drawLine(shiftx + (i + 1) * iW,
                        shifty + j * iW,
                        shiftx + (i) * iW,
                        shifty + (j + 1) * iW,
                        p);
            }
        }
        drawMyText(score, level,(width + iW * Const.NW[Const.AWRY]) / 2, hShift );

    }


}
