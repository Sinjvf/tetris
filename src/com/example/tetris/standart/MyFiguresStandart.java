package com.example.tetris.standart;

import android.graphics.Point;
import com.example.tetris.MyFigures;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;


/**
 * Created by Sinjvf on 03.03.2015.
 */
public class MyFiguresStandart extends MyFigures{

    MyFiguresStandart(){
        super();

    }
    public int getCurrentMode(){
        return currentMode;
    }
    public String getName() {
        return name;
    }
    public void setXY(int i, int j){
        x = i;   y = j;
    }
    public void setCurrentMode(int mode){
        currentMode = mode;
    }
    /**get fields with position*/
    public HashSet<Point> getFieldsWithPosition() {
        HashSet<Point> field_old, field_new;
        field_old = modeHashMap.get(currentMode%4);
        field_new = new HashSet<Point>();
        for (Point k: field_old) {
            field_new.add(new Point(k.x + x, k.y + y));
        }
        return field_new;
    }
    /**get fields with position and new mode without rotation*/
    public HashSet<Point> getFieldsWithPosition(int shift_mode) {
        HashSet<Point> field_old, field_new;
        field_old = modeHashMap.get((currentMode+shift_mode)%4);
        field_new = new HashSet<Point>();
        for (Point k: field_old) {
            field_new.add(new Point(k.x + x, k.y + y));
        }
        return field_new;
    }
    public void move(int i, int j){
        x+=i; y+=j;
    }
    public void rotate(int shift_mode)
    {
        currentMode =(shift_mode+currentMode)%4;
    }

    /**
     * create new figure
     */

    public static MyFiguresStandart newFigure(){
        MyFiguresStandart fCurrent;
        Random random;
        random = new Random(System.currentTimeMillis());
        int num = Math.abs(random.nextInt()) % 7;
        switch (num) {
            case 1:
                fCurrent = new F_L();
                break;
            case 2:
                fCurrent = new F_O();
                break;
            case 3:
                fCurrent = new F_P();
                break;
            case 4:
                fCurrent = new F_T();
                break;
            case 5:
                fCurrent = new F_Z();
                break;
            case 6:
                fCurrent = new F_Z_back();
                break;
            default:
                fCurrent = new F_I();
                break;
        }
        fCurrent.setCurrentMode(Math.abs(random.nextInt()) % 4);
        return fCurrent;
    }
}
