package com.swufe.firstapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class RateActivity extends AppCompatActivity implements Runnable {
    private final String TAG = "Rate";
    private float dollarRate = 0.1f;
    private float euroRate = 0.2f;
    private float wonRate = 0.3f;

    TextView show;
    EditText rmb;
    Handler handler;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rate);
        show = (TextView) findViewById(R.id.shouOut);
        rmb = (EditText) findViewById(R.id.rmb);
        //获取SP里保存的数据
        SharedPreferences sharedPreferences = getSharedPreferences("myrate", MODE_PRIVATE);
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        dollarRate = sharedPreferences.getFloat("dollar_rate", 0.0f);
        euroRate = sharedPreferences.getFloat("euro_rate", 0.0f);
        wonRate = sharedPreferences.getFloat("won_rate", 0.0f);
        Log.i(TAG, "onCreate:sp dollarRate=" + dollarRate);
        Log.i(TAG, "onCreate:sp euroRate=" + euroRate);
        Log.i(TAG, "onCreate:sp wonRate=" + wonRate);

        //获取当前系统时间
        String updateDate = sharedPreferences.getString("update_date", " ");
        Date today = Calendar.getInstance().getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        final String todayStr = sdf.format(today);
        Log.i(TAG, "onCreate:todayStr=" + todayStr);
        //判断时间
        if (!todayStr.equals(updateDate)) {
            Log.i(TAG, "onCreate:需要更新");
            //开启子线程
            Thread t = new Thread(this);
            t.start();
        } else {
            Log.i(TAG, "onCreate:不需更新");
        }


        handler = new Handler() {
            @Override
            public void handleMessage(@NonNull Message msg) {
                if (msg.what == 5) {
                    Bundle bdl = (Bundle) msg.obj;
                    dollarRate = bdl.getFloat("dollar-rate");
                    euroRate = bdl.getFloat("euro-rate");
                    wonRate = bdl.getFloat("won-rate");
                    Log.i(TAG, "handleMessage:dollarRate=" + dollarRate);
                    Log.i(TAG, "handleMessage:euroRate=" + euroRate);
                    Log.i(TAG, "handleMessage:wonRate=" + wonRate);
                    Toast.makeText(RateActivity.this, "汇率已更新", Toast.LENGTH_SHORT).show();
                    //Log.i(TAG,"handleMessage:getMessage msg="+str);
                    //show.setText(str);

                    //将新获得汇率写到sp中
                    SharedPreferences sharedPreferences = getSharedPreferences("myrate", MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putFloat("dollar_rate", dollarRate);
                    editor.putFloat("euro_rate", euroRate);
                    editor.putFloat("won_rate", wonRate);
                    editor.putString("update_date", todayStr);
                    editor.apply();
                    Log.i(TAG, "onActivityResult: " + (Calendar.getInstance().getTime()).toString() + "更新数据已保存到SP");

                }
                super.handleMessage(msg);
            }
        };
    }

    public void onClick(View btn) {
        Log.i(TAG, "onClick:");
        String str = rmb.getText().toString();
        Log.i(TAG, "onClick: get str=" + str);

        float r = 0;//val=0;
        if (str.length() > 0) {
            r = Float.parseFloat(str);
        } else {//没有输入内容
            Toast.makeText(this, "请输入金额", Toast.LENGTH_SHORT).show();
        }
        Log.i(TAG, "onClick:r=" + r);
        //计算
        /*if(btn.getId()==R.id.btn_dollar){  //常量
           val= r*(1/6.7f);
        }else if(btn.getId()==R.id.btn_euro){
            val = r*(1/11f);
      }else{
          val=r*500;
    }show.setText(val+"");//show.setText(String.valueOf(val))
    }*/
        if (btn.getId() == R.id.btn_dollar) { //变量
            show.setText(String.format("%.2f", r * dollarRate));
        } else if (btn.getId() == R.id.btn_euro) {
            show.setText(String.format("%.2f", r * euroRate));
        } else {
            show.setText(String.format("%.2f", r * wonRate));
        }
    }

    public void openOne(View btn) {
        openConfig();
    }

    private void openConfig() {
        Intent config = new Intent(this, ConfigActivity.class);
        //打开一个页面
        //打开网页 Intent web = new Intent(Intent.ACTION_VIEW,Uri.parse("http..."));
        //Intent  phone = new Intent(Intent.ACTION_DIAL,Uri.parse("183888"));
        config.putExtra("dollar_rate_key", dollarRate);
        config.putExtra("euro_rate_key", euroRate);
        config.putExtra("won_rate_key", wonRate);

        Log.i(TAG, "openOne:dollar_rate_key=" + dollarRate);
        Log.i(TAG, "openOne:euro_rate_key=" + euroRate);
        Log.i(TAG, "openOne:won_rate_key=" + wonRate);

        //startActivity(config);
        startActivityForResult(config, 1);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.rate, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.menu_set) {
            openConfig();
        }else if (item.getItemId() == R.id.open_list){
            //打开列表窗口
            Intent list = new Intent(this, MyList2Activity.class);
            startActivity(list);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == 1 && resultCode == 2) {
            Bundle bundle = data.getExtras();
            dollarRate = bundle.getFloat("key_dollar", 0.1f);
            euroRate = bundle.getFloat("key_euro", 0.2f);
            wonRate = bundle.getFloat("key_won", 0.3f);
            Log.i(TAG, "onActivityResult:dollarRate=" + dollarRate);
            Log.i(TAG, "onActivityResult:euroRate=" + euroRate);
            Log.i(TAG, "onActivityResult:wonRate=" + wonRate);

            //将新获得汇率写到sp中
            SharedPreferences sharedPreferences = getSharedPreferences("myrate", MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putFloat("dollar_rate", dollarRate);
            editor.putFloat("euro_rate", euroRate);
            editor.putFloat("won_rate", wonRate);
            editor.commit();
            Log.i(TAG, "onActivityResult: 数据已保存到SP");
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void run() {
        Log.i(TAG, "run:run....");
        for (int i = 1; i < 6; i++) {
            Log.i(TAG, "run:i=" + i);
            try {
                Thread.sleep(0);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        //用于保存获取的数据


        //获取网络数据
        /*try {
            URL url = new URL("https://www.usd-cny.com/bankofchina.htm");
            HttpURLConnection http= (HttpURLConnection) url.openConnection();
            InputStream in=http.getInputStream();

            String html = inputSteamString(in);
            Log.i(TAG,"run:html="+html);
            Document doc=Jsoup.parse(html);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }*/
        Bundle bundle = new Bundle();
        bundle = getFromBOC();
        //获取msg对象，用于返回主线程
        Message msg = handler.obtainMessage(5);
        // msg.what=5;
        //msg.obj="hello from Run()";
        msg.obj = bundle;
        handler.sendMessage(msg);


    }

    private Bundle getFromBOC() {
        Bundle bundle = new Bundle();
        Document doc = null;
        try {
            doc = Jsoup.connect("https://www.boc.cn/sourcedb/whpj/").get();
            Log.i(TAG, "run:" + doc.title());
            Elements tables = doc.getElementsByTag("table");
           /* int i=1;
            for(Element table :tables){
                Log.i(TAG,"run:table["+i+"]="+table);
                i++;

            }*/
            Element table = tables.get(1);
            //Log.i(TAG,"run:table[0]="+table);
            //获取td中的数据
            Elements tds = table.getElementsByTag("td");
            for (int i = 0; i < tds.size(); i += 8) {
                Element td1 = tds.get(i);
                Log.i(TAG, "run:text=" + td1.text());
                Element td2 = tds.get(i + 5);
                Log.i(TAG, "run:text=" + td2.text());
                String str1 = td1.text();
                String val = td2.text();
                if ("美元".equals(str1)) {
                    bundle.putFloat("dollar-rate", 100f / Float.parseFloat(val));
                    Log.i(TAG, "putFloat:dollar-rate=" + 100f / Float.parseFloat(val));
                } else if ("欧元".equals(str1)) {
                    bundle.putFloat("euro-rate", 100f / Float.parseFloat(val));
                } else if ("韩国元".equals(str1)) {
                    bundle.putFloat("won-rate", 100f / Float.parseFloat(val));
                }
            }
            /*for(Element td :tds){
                Log.i(TAG,"run:td="+td);
                Log.i(TAG,"run:text="+td.text());
                Log.i(TAG,"run:html="+td.html());
            }*/
        } catch (IOException e) {
            e.printStackTrace();
        }
        return  bundle;
    }
    private Bundle getFromUsdCny() {
        Bundle bundle = new Bundle();
        Document doc = null;
        try {
            doc = Jsoup.connect("https://www.usd-cny.com/bankofchina.htm").get();
            Log.i(TAG, "run:" + doc.title());
            Elements tables = doc.getElementsByTag("table");
           /* int i=1;
            for(Element table :tables){
                Log.i(TAG,"run:table["+i+"]="+table);
                i++;

            }*/
            Element table = tables.get(0);
            //Log.i(TAG,"run:table[0]="+table);
            //获取td中的数据
            Elements tds = table.getElementsByTag("td");
            for (int i = 0; i < tds.size(); i += 6) {
                Element td1 = tds.get(i);
                Log.i(TAG, "run:text=" + td1.text());
                Element td2 = tds.get(i + 5);
                Log.i(TAG, "run:text=" + td2.text());
                String str1 = td1.text();
                String val = td2.text();
                if ("美元".equals(str1)) {
                    bundle.putFloat("dollar-rate", 100f / Float.parseFloat(val));
                    Log.i(TAG, "putFloat:dollar-rate=" + 100f / Float.parseFloat(val));
                } else if ("欧元".equals(str1)) {
                    bundle.putFloat("euro-rate", 100f / Float.parseFloat(val));
                } else if ("韩元".equals(str1)) {
                    bundle.putFloat("won-rate", 100f / Float.parseFloat(val));
                }
            }
            /*for(Element td :tds){
                Log.i(TAG,"run:td="+td);
                Log.i(TAG,"run:text="+td.text());
                Log.i(TAG,"run:html="+td.html());
            }*/
        } catch (IOException e) {
            e.printStackTrace();
        }
        return  bundle;
    }

    private String inputSteamString(InputStream inputStream) throws IOException {
        final int bufferSize=1024;
        final char[] buffer=new char[bufferSize];
        final StringBuilder out=new StringBuilder();
        Reader in =new InputStreamReader(inputStream,"gb2312");
        for (;;){
            int rsz = in.read(buffer,0,buffer.length);
            if(rsz<0){
                break;
            }out.append(buffer,0,rsz);
        }
        return out.toString();
        //完成
    }
}
