package com.example.tetris;

import android.graphics.Matrix;
import android.graphics.Point;

import java.util.ArrayList;
import java.util.HashSet;

/**
 * Created by Sinjvf on 03.03.2015.
 */
public class F_I extends MyFigures{
    F_I(){
        super();
        name = "F_I";
        HashSet<Point> hs=new HashSet<Point>();
        hs.add(new Point(0, 0)); hs.add(new Point(0, -1));hs.add(new Point(0, -2));hs.add(new Point(0, -3));
        modeHashMap.put(0, hs);
        hs=new HashSet<Point>();
        hs.add(new Point(-1, 0)); hs.add(new Point(0, 0));hs.add(new Point(1, 0));hs.add(new Point(2, 0));
        modeHashMap.put(1, hs);
        hs=new HashSet<Point>();
        hs.add(new Point(0, 0)); hs.add(new Point(0, -1));hs.add(new Point(0, -2));hs.add(new Point(0, -3));
        modeHashMap.put(2, hs);
        hs=new HashSet<Point>();
        hs.add(new Point(-1, 0)); hs.add(new Point(0, 0));hs.add(new Point(1, 0));hs.add(new Point(2, 0));
        modeHashMap.put(3, hs);
    }
    @Override
    public void rotate(int ang) {
    }
}
