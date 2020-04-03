package com.swufe.firstapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class SecondActivity extends AppCompatActivity {
    TextView scoreA,scoreB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        scoreA = (TextView) findViewById(R.id.scoreA);
        scoreB = (TextView) findViewById(R.id.scoreB);
    }
    public void btn_A1(View btn){
        showScoreA(1);
    }
    public void btn_A2(View btn){
        showScoreA(2);
    }
    public void btn_A3(View btn){
        showScoreA(3);
    }
    public void btn_r(View btn){
        scoreA.setText(""+0);
        scoreB.setText(""+0);
    }
    public void showScoreA (int addA) {
        String oldScoreA = (String) scoreA.getText();
        int newScoreA = Integer.parseInt(oldScoreA) + addA;
        scoreA.setText("" + newScoreA);
    }
    public void btn_B1(View btn){
        showScoreB(1);
    }
    public void btn_B2(View btn){
        showScoreB(2);
    }
    public void btn_B3(View btn){
        showScoreB(3);
    }
    public void showScoreB(int addB) {
        String oldScoreB = (String) scoreB.getText();
        int newScoreB = Integer.parseInt(oldScoreB) + addB;
        scoreB.setText("" + newScoreB);
    }

}
