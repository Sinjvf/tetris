package com.example.tetris.standart;

import android.graphics.Point;

import java.util.HashSet;

/**
 * Created by Sinjvf on 07.03.2015.
 */
public class F_Z extends MyFiguresStandart {
    F_Z(){
        super();
        HashSet<Point> hs=new HashSet<Point>();
        hs.add(new Point(0, -1)); hs.add(new Point(1, -1));hs.add(new Point(1, 0));hs.add(new Point(2, 0));
        modeHashMap.put(0, hs);
        hs=new HashSet<Point>();
        hs.add(new Point(1, -2)); hs.add(new Point(0, -1));hs.add(new Point(1, -1));hs.add(new Point(0, 0));
        modeHashMap.put(1, hs);
        hs=new HashSet<Point>();
        hs.add(new Point(0, -1)); hs.add(new Point(1, -1));hs.add(new Point(1, 0));hs.add(new Point(2, 0));
        modeHashMap.put(2, hs);
        hs=new HashSet<Point>();
        hs.add(new Point(1, -2)); hs.add(new Point(0, -1));hs.add(new Point(1, -1));hs.add(new Point(0, 0));
        modeHashMap.put(3, hs);

    }
}
