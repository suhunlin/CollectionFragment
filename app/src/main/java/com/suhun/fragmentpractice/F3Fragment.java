package com.suhun.fragmentpractice;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Random;

public class F3Fragment extends Fragment {
    public String tag = F3Fragment.class.getSimpleName();
    private View view;
    private MainActivity activity;
    private EditText userInputText;
    private TextView logText;
    private GuessNumber guessNumber;
    int settingSelect = 0;

    public class GuessNumber{
        public String tag = GuessNumber.class.getSimpleName();
        private int answerLen;
        private String answer;

        public GuessNumber(){
            answerLen = 4;
            answer = createAnswer();
            Log.d(tag, "The init answer is " + answer);
        }

        public String createAnswer(){
            HashSet<Integer> numSet = new HashSet<>();
            while(numSet.size()<answerLen){
                numSet.add(new Random().nextInt(10));
            }
            LinkedList<Integer> listData = new LinkedList<>();
            for(Integer num:numSet){
                listData.add(num);
            }
            Collections.shuffle(listData);
            StringBuffer stringBuffer = new StringBuffer();
            for(Integer num:listData){
                stringBuffer.append(num);
            }
            return stringBuffer.toString();
        }

        public String checkAnswer(String userInput){
            int a=0, b=0;
            for(int i=0;i<this.answerLen;i++){
                if(this.answer.charAt(i)==userInput.charAt(i)){
                    a++;
                }else if(this.answer.contains(String.valueOf(userInput.charAt(i)))){
                    b++;
                }
            }
            return String.format("%dA%dB", a, b);
        }

        public void resetAnswer(){
            answer = createAnswer();
            Log.d(tag, "The init resetAnswer is " + answer);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if(view==null){
            // Inflate the layout for this fragment
            view = inflater.inflate(R.layout.fragment_f3, container, false);
            initView(view);
            guessNumber = new GuessNumber();
        }
        return view;
    }
    private void initView(View view){
        //init EditTextView
        userInputText = view.findViewById(R.id.userInput);
        //init TextView
        logText = view.findViewById(R.id.log);
        //init Button
        Button guessBtn, restartBtn, settingBtn;
        guessBtn = view.findViewById(R.id.guess);
        restartBtn = view.findViewById(R.id.restart);
        settingBtn = view.findViewById(R.id.setting);
        //set Button onClick
        guessBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showResult();
            }
        });
        restartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(activity)
                        .setTitle("Reset Game?")
                        .setMessage("Do you want reset game?")
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                resetGame();
                            }
                        })
                        .setNeutralButton("Cancel", null)
                        .show();

            }
        });
        settingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] setLenItems = {"2", "3", "4", "5"};
                int checkedItem = 0;
                for(int i=0;i<setLenItems.length;i++){
                    if(Integer.valueOf(setLenItems[i])==guessNumber.answerLen){
                        checkedItem = i;
                    }
                }
                new AlertDialog.Builder(activity)
                        .setTitle("Set Answer Length")
                        .setSingleChoiceItems(setLenItems, checkedItem, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Log.d(tag, "The init====="+which);
                                settingSelect = which;
                            }
                        })
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                guessNumber.answerLen = Integer.valueOf(setLenItems[settingSelect]);
                                resetGame();
                            }
                        })
                        .setNeutralButton("Cancel", null)
                        .show();

            }
        });
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        activity = (MainActivity)context;
    }
    public void showResult(){
        String userInput = userInputText.getText().toString();
        String result = guessNumber.checkAnswer(userInput);
        String outPutString = userInput + ":" + result + "\n";

        if(result.equals(guessNumber.answerLen+"A0B")){
            new AlertDialog.Builder(activity)
                    .setTitle("Reset Game?")
                    .setMessage(outPutString + "You got it!!!+\n"+"Do you want reset game?")
                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            resetGame();
                            userInputText.setText("");
                        }
                    })
                    .setNeutralButton("Cancel", null)
                    .show();
        }else{
            userInputText.setText("");
            new AlertDialog.Builder(activity)
                    .setTitle("Result")
                    .setMessage(outPutString )
                    .setPositiveButton("Ok", null)
                    .show();
        }
    }
    private void resetGame(){
        guessNumber.resetAnswer();
    }
}