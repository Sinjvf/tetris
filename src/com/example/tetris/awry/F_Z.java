package com.example.tetris.awry;

import android.graphics.Point;
import com.example.tetris.standart.MyFiguresStandart;

import java.util.HashSet;

/**
 * Created by Sinjvf on 07.03.2015.
 */
public class F_Z extends MyFiguresAwry {
    F_Z(){
        super();
        HashSet<Point> hs=new HashSet<Point>();
        hs.add(new Point(0, -1)); hs.add(new Point(0, 0));hs.add(new Point(0, 1));hs.add(new Point(0, 2));
        modeHashMap.put(0, hs);
        hs=new HashSet<Point>();
        hs.add(new Point(1, 0)); hs.add(new Point(1, 1));hs.add(new Point(0, 0));hs.add(new Point(0, 1));
        modeHashMap.put(1, hs);
        hs=new HashSet<Point>();
        hs.add(new Point(0, -1)); hs.add(new Point(0, 0));hs.add(new Point(0, 1));hs.add(new Point(0, 2));
        modeHashMap.put(2, hs);
        hs=new HashSet<Point>();
        hs.add(new Point(1, 0)); hs.add(new Point(1, 1));hs.add(new Point(0, 0));hs.add(new Point(0, 1));
        modeHashMap.put(3, hs);

        setOddHashMap();
    }
}
