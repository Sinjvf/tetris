package com.example.tetris.awry;

import android.graphics.Point;
import com.example.tetris.Const;
import com.example.tetris.MyFigures;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;



/**
 * Created by Sinjvf on 03.03.2015.
 */
public class MyFiguresAwry extends MyFigures{
    protected HashMap<Integer, HashSet<Point>>modeOddHashMap;


    MyFiguresAwry(){
        super();
        x=3;
        movingStep=2;
    }


    protected void setOddHashMap(){
        HashSet<Point> hs;

        for (int k=0;k<4;k++){
            hs=modeHashMap.get(k);
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
    @Override
    protected  HashMap<Integer, HashSet<Point>> getHashMap(){
      //  if (x%2==0)
            return modeHashMap;
      //  else
     //       return modeOddHashMap;
    }

    public void move(int i, int j){
        if (i % 2 == 0)
            x += i/2;
        else
            if (y%2==0){
                x+=(i+1)/2;
                y+=1;
            }
            else{
                x+=(i-1)/2;
                y=+1;
            }


        y+=j* movingStep;
    }

    public MyFiguresAwry clone(){
        MyFiguresAwry newFig = new MyFiguresAwry();
        newFig.modeHashMap = (HashMap<Integer, HashSet<Point>>)modeHashMap.clone();
        newFig.setCurrentMode(currentMode);
        newFig.setXY(x, y);
        return newFig;
    }
    public static MyFiguresAwry newFigure(){
        MyFiguresAwry fCurrent;
        Random random;
        random = new Random(System.currentTimeMillis());
        int num = Math.abs(random.nextInt()) % 8;
   /*     switch (num) {
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
            case 7:
                fCurrent = new F_I();
                break;
            default:
                fCurrent = new F_I_extended();
                break;
        }*/
        fCurrent=new F_I();
        fCurrent.setCurrentMode(Math.abs(random.nextInt()) % 4);
        return fCurrent;
    }
}
