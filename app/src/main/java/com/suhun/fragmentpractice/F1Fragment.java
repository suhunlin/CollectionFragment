package com.suhun.fragmentpractice;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.HashSet;
import java.util.Random;
import java.util.TreeSet;

public class F1Fragment extends Fragment {
    private View view;
    private MainActivity activity;
    private int[] numResource = {R.id.num0, R.id.num1, R.id.num2, R.id.num3, R.id.num4, R.id.num5};
    private TextView[] lotteryNum = new TextView[numResource.length];
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if(view==null){
            // Inflate the layout for this fragment
            view = inflater.inflate(R.layout.fragment_f1, container, false);
            initView(view);
        }
        return view;
    }
    private void initView(View view){
        //init Button
        Button createLotteryBtn = view.findViewById(R.id.creatLottery);
        createLotteryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showLottery();
            }
        });

        //init TextView
        for(int i=0; i<numResource.length;i++){
            lotteryNum[i] = view.findViewById(numResource[i]);
        }
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        activity = (MainActivity)context;
    }
    public void showLottery(){
        createLottery();
        for(int i=0;i<numResource.length;i++){
            lotteryNum[i].setText(""+ activity.appData.lotteryData.getLotteryNum()[i]);
        }
    }
    private void createLottery(){
        TreeSet<Integer> numSet = new TreeSet<>();
        while(numSet.size()<numResource.length){
            numSet.add(new Random().nextInt(49)+1);
        }
        int i=0;
        for(Integer num:numSet){
            activity.appData.lotteryData.getLotteryNum()[i++] = num;
        }
    }
}