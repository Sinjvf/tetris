package com.example.tetris;

import android.graphics.Point;
import com.example.tetris.standart.*;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;


/**
 * Created by Sinjvf on 03.03.2015.
 */
public abstract class MyFigures {


    //  protected String name;
    protected HashMap<Integer, HashSet<Point>>modeHashMap ;
    protected int currentMode;
    protected int x, y;
    protected int movingStep;


    public MyFigures(){
        modeHashMap = new HashMap<Integer,  HashSet<Point > >();
         y=0;

    }
    public int getCurrentMode(){
        return currentMode;
    }
    public int getMovingStep() {
        return movingStep;
    }
    public abstract MyFigures clone();
    public void setXY(int i, int j){
        x = i;   y = j;
    }
    public void setCurrentMode(int mode){
        currentMode = mode;
    }
    /**get fields with position*/
    public HashSet<Point> getFieldsWithPosition() {
        HashSet<Point> field_old, field_new;
        field_old = getHashMap().get(currentMode%4);
        field_new = new HashSet<Point>();
        for (Point k: field_old) {
            field_new.add(new Point(k.x + x, k.y + y));
        }
        return field_new;
    }

    /**get fields with position and new mode without rotation*/
    public  HashSet<Point> getFieldsWithPosition(int shift_mode) {
        HashSet<Point> field_old, field_new;
        field_old = getHashMap().get((currentMode+shift_mode)%4);
        field_new = new HashSet<Point>();
        for (Point k: field_old) {
            field_new.add(new Point(k.x + x, k.y + y));
        }
        return field_new;
    }
    public abstract void move(int i, int j);
    protected abstract HashMap<Integer, HashSet<Point>> getHashMap();
    public static MyFigures newFigure(){return null;}
}
