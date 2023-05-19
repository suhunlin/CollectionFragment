package com.suhun.fragmentpractice;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

public class MainActivity extends AppCompatActivity {
    public AppData appData;
    private F1Fragment f1;
    private F2Fragment f2;
    private F3Fragment f3;
    private FragmentManager fmgr;
    private FrameLayout frameLayout;
    private Button lotteryBtn, stopWatchBtn, guessNumberBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        appData = (AppData)getApplication();
        initView();
        initFragment();
    }

    public AppData getAppData(){
        return this.appData;
    }

    private void initView(){
        frameLayout = findViewById(R.id.container);
        lotteryBtn = findViewById(R.id.goLottery);
        stopWatchBtn = findViewById(R.id.goStopWatch);
        guessNumberBtn = findViewById(R.id.goGuessNumber);
    }

    private void initFragment(){
        f1 = new F1Fragment();
        f2 = new F2Fragment();
        f3 = new F3Fragment();
        fmgr = getSupportFragmentManager();
        FragmentTransaction transaction = fmgr.beginTransaction();
        transaction.add(R.id.container, f1);
        transaction.commit();
    }

    public void goLotteryFun(View view){
        FragmentTransaction transaction = fmgr.beginTransaction();
        transaction.replace(R.id.container, f1);
        transaction.commit();
    }

    public void goStopWatchFun(View view){
        FragmentTransaction transaction = fmgr.beginTransaction();
        transaction.replace(R.id.container, f2);
        transaction.commit();
    }

    public void goGuessNumberFun(View view){
        FragmentTransaction transaction = fmgr.beginTransaction();
        transaction.replace(R.id.container, f3);
        transaction.commit();
    }
}