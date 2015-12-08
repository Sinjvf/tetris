package com.example.tetris.awry;

import android.graphics.Point;
import android.util.Log;
import com.example.tetris.Const;
import com.example.tetris.GameScreen;
import com.example.tetris.MyFigures;

import java.util.ArrayList;
import java.util.HashSet;

/**
 * Created by Sinjvf on 03.03.2015.
 */
public class GameScreenAwry extends GameScreen{



    public GameScreenAwry(int i_size, int j_size){
        super(i_size, j_size);
    }

@Override
    public boolean isFull(int i, int j){
        if (j<0)return false;
        else if (j%2==0 && i== nI-1) return true;
        return screenArray.get(j).get(i);
    }

    @Override
    protected  void removeLine(int j){
        ArrayList <Boolean> TmpLine;
        int k=2;
        for (int i=j-2;i>0;i=i-2){

            Log.d(Const.LOG_TAG, "copy line =  "+i+"  to  "+ (Integer)(i+2));
            TmpLine=(ArrayList<Boolean>)screenArray.get(i).clone();
            screenArray.set(i + 2, TmpLine);
            k=k+2;
        }
        screenArray.set(j-k, blankLine);
    }


}
