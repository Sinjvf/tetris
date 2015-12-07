package com.example.tetris.awry;

import android.graphics.Point;
import com.example.tetris.standart.MyFiguresStandart;

import java.util.HashMap;
import java.util.HashSet;

/**
 * Created by Sinjvf on 03.03.2015.
 */
public class F_I extends MyFiguresAwry {
    F_I(){
        super();
        HashSet<Point> hs=new HashSet<Point>();
        hs.add(new Point(0, 0)); hs.add(new Point(1, -1));hs.add(new Point(1, -2));hs.add(new Point(2, -3));
        modeHashMap.put(0, hs);
        hs=new HashSet<Point>();
        hs.add(new Point(1, 0)); hs.add(new Point(1, -1));hs.add(new Point(0, -2));hs.add(new Point(0, -3));
        modeHashMap.put(1, hs);
        hs=new HashSet<Point>();
        hs.add(new Point(0, 0)); hs.add(new Point(1, -1));hs.add(new Point(1, -2));hs.add(new Point(2, -3));
        modeHashMap.put(2, hs);
        hs=new HashSet<Point>();
        hs.add(new Point(1, 0)); hs.add(new Point(1, -1));hs.add(new Point(0, -2));hs.add(new Point(0, -3));
        modeHashMap.put(3, hs);


        HashSet<Point> hs1;//=new HashSet<Point>();
        modeOddHashMap = new HashMap<Integer,  HashSet<Point > >();
        for (int k=0;k<4;k++){
            hs1=new HashSet<Point>();
            hs = modeHashMap.get(k);
            for (int fields =0;fields<4;fields++)

            hs1=(HashSet<Point > )modeHashMap.get(k).clone();
            for (Point hsK: hs){
                if (hsK.y%2==0){
                    hsK.x+=1;
                    hsK.y+=1;
                }
                else{
                    hsK.y=+1;
                }
            }
            modeOddHashMap.put(k, hs);
        }
    }


}
