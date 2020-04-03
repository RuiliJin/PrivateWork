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
        scoreA.setText("0");
        scoreB.setText("0");
    }
    public void showScoreA (int addA) {
        String oldScoreA = (String) scoreA.getText();
        int newScoreA = Integer.parseInt(oldScoreA) + addA;
        scoreA.setText("" + newScoreA);
    }
    public void showScoreB(int addB) {
        String oldScoreB = (String) scoreB.getText();
        int newScoreB = Integer.parseInt(oldScoreB) + addB;
        scoreB.setText("" + newScoreB);
    }

}
