package com.suhun.fragmentpractice;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Timer;
import java.util.TimerTask;

public class F2Fragment extends Fragment {
    private View view;
    private MainActivity activity;
    private ListView listView;
    private TextView clockView;
    Button leftBtn, rightBtn;
    private SimpleAdapter simpleAdapter;
    private ArrayList<HashMap<String, String>> data = new ArrayList<>();
    private String[] from = {"itemKey"};
    private int[] to = {R.id.list_item};
    boolean isStart;
    private int count;
    private Timer timer = new Timer();
    private MyTask myTask;
    private UIHandler uiHandler = new UIHandler();

    public class MyTask extends TimerTask{
        @Override
        public void run() {
            count ++;
            uiHandler.sendEmptyMessage(0);
        }
    }

    public class UIHandler extends Handler{
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            clockView.setText(""+counterToClock());
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if(view==null){
            // Inflate the layout for this fragment
            view = inflater.inflate(R.layout.fragment_f2, container, false);
            initView(view);
        }
        return view;
    }

    private void initView(View view){
        //init TextView
        clockView = view.findViewById(R.id.clock);
        //init Button
        leftBtn = view.findViewById(R.id.leftButton);
        rightBtn = view.findViewById(R.id.rightButton);
        leftBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doLeftFun();
            }
        });
        rightBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doRightFun();
            }
        });
        //init listView
        listView = view.findViewById(R.id.lapData);
        initListViewDataStructure();
    }
    private void initListViewDataStructure(){
        simpleAdapter = new SimpleAdapter(activity, data, R.layout.item, from, to);
        listView.setAdapter(simpleAdapter);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        activity = (MainActivity)context;
    }

    private void doLeftFun(){
        if(isStart){
            doLap();
        }else{
            doReset();
        }
    }

    private void doLap(){
        HashMap<String, String> lapData = new HashMap<>();
        lapData.put(from[0], clockView.getText().toString());
        data.add(0, lapData);
        simpleAdapter.notifyDataSetChanged();
    }

    private void doReset(){
        count = 0;
        data.clear();
        simpleAdapter.notifyDataSetChanged();
        clockView.setText("00:00:00.0");
    }

    private void doRightFun(){
        isStart = !isStart;
        if(isStart){
            leftBtn.setText("Lap");
            rightBtn.setText("Stop");
            doStart();
        }else{
            leftBtn.setText("Reset");
            rightBtn.setText("Start");
            doStop();
        }
    }
    private void doStart(){
        if(myTask==null){
            myTask = new MyTask();
            timer.schedule(myTask, 10, 10);
        }
    }
    private void doStop(){
        if(myTask!=null){
            myTask.cancel();
            myTask = null;
        }
    }
    private String counterToClock(){
        int hs = count % 100;
        int ts = count / 100;
        int hh = ts / (60*60);
        int mm = (ts - hh*60*60) / 60;
        int ss = ts % 60;
        return String.format("%d:%d:%d.%d", hh, mm, ss, hs);
    }
}