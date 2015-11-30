package com.example.tetris;

import android.graphics.Point;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Vector;

/**
 * Created by Sinjvf on 03.03.2015.
 */
public class GameScreen {
    private int n_i, n_j;
    private ArrayList <ArrayList<Boolean> > screenArray;
    private ArrayList <Boolean> line;
    GameScreen(int i_size, int j_size){
        line = new ArrayList<Boolean>();
        screenArray = new ArrayList<ArrayList<Boolean>>();
        n_i = i_size;
        n_j = j_size;
        for (int i =0; i<n_i;i++){
            line.add(false);
        }
        for (int i =0; i<n_j;i++){
            screenArray.add((ArrayList<Boolean>)line.clone());
        }
    }


    public boolean isFull(int i, int j){
        if (j<0)return false;
        return screenArray.get(j).get(i);
    }
    public boolean isFull(Point point){
        return screenArray.get(point.y).get(point.x);
    }

    /**Proof of ability of move or rotation*/
    public boolean canMoveOrRotate(MyFigures fig, int shift_i, int shift_j, int shift_mode){
        HashSet<Point> field = fig.getFieldsWithPosition(shift_mode);
        for (Point k : field) {
            if (shift_i+k.x>=n_i|| shift_i+k.x<0)return false;
            else if (shift_j+k.y>=n_j)return false;
            if (isFull(k.x+shift_i,k.y+shift_j )) return false;
        }
        return true;
    }
    /**del full lines and */
    public int deleteLineIfNesessary(){
        int removingLines=0;
        boolean fullLine;
        for (int j=0; j<n_j;j++){
            fullLine = true;
            for (int i=0;i<n_i;i++){
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

    /**return 1 if figure must stay in this position
     * return 2 if game over*/
    public int mustStay(MyFigures fig, int shiftX, int shiftY){
        HashSet<Point> field = fig.getFieldsWithPosition();
        for (Point k : field) {
            if (k.y<0 ) {
                if (shiftX == 0 && shiftY == 0) return 1;
                else return 2;
            }
            if (k.y+1>=n_j)return 1;
            if (isFull(k.x,k.y+1 )) return 1;
        }
        return 0;
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
