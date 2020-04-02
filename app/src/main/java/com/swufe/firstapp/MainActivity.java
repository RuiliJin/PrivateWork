package com.swufe.firstapp;

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    TextView out;
    EditText inp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        out = (TextView)findViewById(R.id.showText);
        inp = (EditText)findViewById(R.id.inpText);

        Button btn = (Button)findViewById(R.id.btn1);
        btn.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        Log.i("click","onclick");
        EditText inp = (EditText)findViewById(R.id.inpText);
        double in = Double.parseDouble(inp.getText().toString());
        inp.setText(in+"摄氏度");
        double ou = in*33.8;
        out.setText("结果是"+ou+"华氏度");
    }
}
