package com.example.tetris.standart;

import android.graphics.Point;

import java.util.HashSet;

/**
 * Created by Sinjvf on 03.03.2015.
 */
public class F_P extends MyFiguresStandart {

    F_P() {
        super();
        HashSet<Point> hs=new HashSet<Point>();
        hs.add(new Point(1, -2)); hs.add(new Point(1, -1));hs.add(new Point(1, 0));hs.add(new Point(0, 0));
        modeHashMap.put(0, hs);
        hs=new HashSet<Point>();
        hs.add(new Point(0, -1)); hs.add(new Point(0, 0));hs.add(new Point(1, 0));hs.add(new Point(2, 0));
        modeHashMap.put(1, hs);
        hs=new HashSet<Point>();
        hs.add(new Point(1, -2)); hs.add(new Point(0, -2));hs.add(new Point(0, -1));hs.add(new Point(0, 0));
        modeHashMap.put(2, hs);
        hs=new HashSet<Point>();
        hs.add(new Point(-1, -1)); hs.add(new Point(0, -1));hs.add(new Point(1, -1));hs.add(new Point(1, 0));
        modeHashMap.put(3, hs);
/*
        modeHashMap.put(0, new int[][] {{1, 1, 1, 0}, {0, 1, 2, 2}});
        modeHashMap.put(1, new int[][] {{0, 0, 1, 2}, {0, 1, 1, 1}});
        modeHashMap.put(2, new int[][] {{1, 0, 0, 0}, {0, 0, 1, 2}});
        modeHashMap.put(3, new int[][] {{-1, 0, 1, 1}, {0, 0, 0, 1}});*/
    }

}
