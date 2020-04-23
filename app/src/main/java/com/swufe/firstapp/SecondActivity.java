package com.swufe.firstapp;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class SecondActivity extends AppCompatActivity {
    private  final String TAG="SecondActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        Log.i(TAG,"onCreate");

    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        String  scoreA = findViewById(R.id.scoreA).toString();
        String scoreB =  findViewById(R.id.scoreB).toString();
        outState.putString("teamA-score",scoreA);
        outState.putString("teamB-score",scoreB);

        Log.i(TAG,"onSaveInstanceState");
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        String scoreA=savedInstanceState.getString("teamA-score");
        String scoreB=savedInstanceState.getString("teamB-score");
        ((TextView)findViewById(R.id.scoreA)).setText(scoreA);
        ((TextView)findViewById(R.id.scoreA)).setText(scoreB);

        Log.i(TAG,"onRestoreInstanceState");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(TAG,"onResume");
    }
    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i(TAG,"onRestart");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i(TAG,"onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i(TAG,"onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(TAG,"onDestroy");
    }

    public void btn_1(View btn){
        if(btn.getId()==R.id.btn_A1){
            showScoreA(1);
        }else{
            showScoreB(1);
        }

    }
    public void btn_2(View btn){
        if(btn.getId()==R.id.btn_A2){
            showScoreA(2);
        }else{
            showScoreB(2);
        }
    }
    public void btn_3(View btn){
        if(btn.getId()==R.id.btn_A3){
            showScoreA(3);
        }else{
            showScoreB(3);
        }
    }
    public void btn_r(View btn){
        TextView scoreA = (TextView) findViewById(R.id.scoreA);
        TextView scoreB = (TextView) findViewById(R.id.scoreB);
        scoreA.setText("0");
        scoreB.setText("0");
    }
    public void showScoreA (int addA) {
        TextView scoreA = (TextView) findViewById(R.id.scoreA);

        String oldScoreA = (String) scoreA.getText();
        int newScoreA = Integer.parseInt(oldScoreA) + addA;
        scoreA.setText("" + newScoreA);
    }
    public void showScoreB(int addB) {
        TextView scoreB = (TextView) findViewById(R.id.scoreB);
        String oldScoreB = (String) scoreB.getText();
        int newScoreB = Integer.parseInt(oldScoreB) + addB;
        scoreB.setText("" + newScoreB);
    }

}
