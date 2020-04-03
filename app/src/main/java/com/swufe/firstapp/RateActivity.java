package com.swufe.firstapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class RateActivity extends AppCompatActivity {
    TextView show;
    EditText rmb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rate);
        show = (TextView)findViewById(R.id.rmb);
        rmb=(EditText)findViewById(R.id.rmb);
    }
    public void onClick(View btn){
        float r=0,val=0;
        String str=rmb.getText().toString();
        if(str.length()>0){
            r=Float.parseFloat(str);
        }else{
            Toast.makeText(this,"请输入金额",Toast.LENGTH_SHORT).show();
        }
        if(btn.getId()==R.id.btn_dollar){
            val= r*(1/6.7f);
        }else if(btn.getId()==R.id.btn_euro){
            val = r*(1/11f);
        }else{
            val=r*500;
        }show.setText(val+"");//show.setText(String.valueOf(val))
    }
    public void openOne(View btn){
        Intent hello = new Intent(this,SecondActivity.class);
        //打开一个页面
       //打开网页 Intent web = new Intent(Intent.ACTION_VIEW,Uri.parse("http..."));
        //Intent  phone = new Intent(Intent.ACTION_DIAL,Uri.parse("183888"));
        startActivity(hello);
    }
}
