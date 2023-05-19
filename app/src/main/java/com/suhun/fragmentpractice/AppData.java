package com.suhun.fragmentpractice;

import android.app.Application;

import java.util.LinkedList;

public class AppData extends Application {
    public LotteryData lotteryData;
    public class LotteryData{
        private Integer[] numArray = new Integer[6];

        public Integer[] getLotteryNum(){
            return numArray;
        }
    }

    public AppData(){
        lotteryData = new LotteryData();
    }
}
