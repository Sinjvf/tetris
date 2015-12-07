package com.example.tetris;

import android.graphics.Point;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashSet;

/**
 * Created by Sinjvf on 03.03.2015.
 */
public abstract class GameScreen {
    protected int nI, nJ;
    protected ArrayList <ArrayList<Boolean> > screenArray;
    protected ArrayList <Boolean> line;


    public GameScreen(int iSize, int jSize){
        line = new ArrayList<Boolean>();
        screenArray = new ArrayList<ArrayList<Boolean>>();
        nI = iSize;
        nJ = jSize;
        for (int i =0; i< nI;i++){
            line.add(false);
        }
        for (int i =0; i< nJ;i++){
            screenArray.add((ArrayList<Boolean>)line.clone());
        }
    }


    public abstract boolean isFull(int i, int j);

    /**Proof of ability of move or rotation*/
    /**return 1 if figure must stay in this position
     * return 2 if game over
     * return -1 if figure may shift
     * in the other way return 0*/
    public int canMoveOrRotate(MyFigures fig, int shiftX, int shiftY, int shiftMode){
        MyFigures newFig = fig.clone();
        newFig.move(shiftX, shiftY);
        HashSet<Point> field = newFig.getFieldsWithPosition(shiftMode);
        Log.d(Const.LOG_TAG, "new_pos  , x="+shiftX+", y="+shiftY+", Sh="+shiftMode);
        for (Point k : field) {
            Log.d(Const.LOG_TAG, "x="+k.x+", y="+k.y);
            //bottom
            if (k.y>= nJ){
                Log.d(Const.LOG_TAG,"bottom");
                return 1;}
            //wall
            if (k.x<0 ||k.x>=nI) {

                Log.d(Const.LOG_TAG,"wall");
                return 0;
            }
            //other fig prevents
            if (isFull(k.x,k.y)){
                //other fig prevents rotation
                if (shiftX == 0 && shiftY == 0) {

                    Log.d(Const.LOG_TAG,"pr rot");
                    return 0;
                }
                //game over
                else if (k.y<=0) {

                    Log.d(Const.LOG_TAG,"game over");
                    return 2;
                }
                //fig lay on the other fig
                else {

                    Log.d(Const.LOG_TAG,"lay");
                    return 1;
                }
            }
        }
        //figure may move
        return -1;
    }
    /**del full lines and */
    public int deleteLineIfNesessary(){
        int removingLines=0;
        boolean fullLine;
        for (int j=0; j< nJ;j++){
            fullLine = true;
            for (int i=0;i< nI;i++){
                if (!isFull(i, j)) {fullLine = false;
                break;}
            }
            if (fullLine){
                screenArray.remove(j);
                screenArray.add(0, (ArrayList<Boolean>)line.clone());
                removingLines++;
            }
        }
        return (int)Math.pow(3, removingLines-1);
    }

    public void fillFigureSpace(MyFigures fig){
        HashSet<Point> field = fig.getFieldsWithPosition();
        ArrayList<Boolean> tmpLine;
        for (Point k:field){
            if (k.y>=0){
                tmpLine =(ArrayList<Boolean>) screenArray.get(k.y).clone();
                tmpLine.set(k.x, true);
                screenArray.set(k.y, tmpLine);
            }
        }


    }


}
