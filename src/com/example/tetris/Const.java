package com.example.tetris;

import java.util.ArrayList;

/**
 * Created by sinjvf on 20.11.15.
 */
public class Const {
//Global settings
    public static final int STANDART = 0;
    public static final int AWRY = 1;
    public static final int SHIFTX = 2;  //x shift of grid
    public static final int TRACE = 1; //width of line in grid
    public static final String LOG_TAG = "myLogs";
    public static final int[] PACE = {800, 700, 450, 300, 200, 100, 80};
    public static final int[] POINT_FOR_LINE ={1, 2, 4, 6, 10, 15, 20};
    public static final int[] LEAVE_LEVEL ={20, 20, 10, 10, 8, 5 };
    public static final int sensibility =7; //screen's sensibility  for moving.

    //Data Base
    public static final String DATABASE_NAME = "mydatabase.db";
    public static final String TABLE_NAME = "scores";
    public static final String NAME_COLUMN = "name";
    public static final String SCORE_COLUMN = "score";
    public static final int N_BEST = 10;



    public static final int[] NW={10, 7};
    public static final int[] NH={20, 14};

}
