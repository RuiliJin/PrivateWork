package com.swufe.firstapp;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MyList2Activity extends ListActivity implements Runnable, AdapterView.OnItemClickListener {

    private String TAG="MyList2";
    Handler handler;
    private ArrayList<HashMap<String,String>> listItems;//存放文字图片信息
    private SimpleAdapter listItemAdapter;//适配器

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initListView();
       /* MyAdapter myAdapter=new MyAdapter(this,R.layout.list_item,listItems);
        this.setListAdapter(myAdapter);*/
        this.setListAdapter(listItemAdapter);

        Thread t= new Thread(this);
        t.start();

        handler = new Handler() {
            @Override
            public void handleMessage(@NonNull Message msg) {
                if(msg.what==7){
                    List<HashMap<String,String>> list2 = (List<HashMap<String,String>>) msg.obj;
                    listItemAdapter= new SimpleAdapter(MyList2Activity.this,list2, R.layout.list_item,
                            new String[]{"ItemTitle", "ItemDetail"},
                            new int[]{R.id.itemTitle, R.id.itemDetail} );
                    setListAdapter(listItemAdapter);
                }
                super.handleMessage(msg);
            }
        };
        getListView().setOnItemClickListener(this);
    }

    private void initListView() {
        listItems = new ArrayList<HashMap<String, String>>();
        for (int i = 0; i < 10; i++) {
            HashMap<String, String> map = new HashMap<String, String>();
            map.put("ItemTitle", "Rate:" + i);
            map.put("ItemDetail", "detail" + i);
            listItems.add(map);
        }
        //生成适配器的Item和动态数组对应的元素
        listItemAdapter = new SimpleAdapter(this, listItems,//listItems数据源
                R.layout.list_item,//listItem的mtl布局实现
                new String[]{"ItemTitle", "ItemDetail"},
                new int[]{R.id.itemTitle, R.id.itemDetail});

    }

    @Override
    public void run() {
        //获取网络数据，放入List带会主线程
        List<HashMap<String,String>> retList= new  ArrayList<HashMap<String,String>>();
        Document doc = null;
        try {
            Thread.sleep(300);
            doc = Jsoup.connect("https://www.boc.cn/sourcedb/whpj/").get();
            Log.i(TAG, "run:" + doc.title());
            Elements tables = doc.getElementsByTag("table");

            Element table2 = tables.get(1);

            Elements tds = table2.getElementsByTag("td");
            for (int i = 0; i < tds.size(); i += 8) {
                Element td1 = tds.get(i);
                Element td2 = tds.get(i + 5);

                String str1 = td1.text();
                String val = td2.text();
                Log.i(TAG, "run:"+str1+"==>" + val);
                HashMap<String,String> map = new HashMap<String,String>();
                map.put("ItemTitle",str1);
                map.put("ItemDetail",val);
                retList.add(map);
            }

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        Message msg = handler.obtainMessage(7);
        // msg.what=5;
        //msg.obj="hello from Run()";
        msg.obj = retList;
        handler.sendMessage(msg);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Log.i(TAG,"onItemClick:parent="+parent);
        Log.i(TAG,"onItemClick:view="+view);
        Log.i(TAG,"onItemClick:position="+position);
        Log.i(TAG,"onItemClick:id="+id);

        HashMap<String,String> map= (HashMap<String, String>) getListView().getItemAtPosition(position);//获得当前行数据对象
        String titleStr = map.get("ItemTitle");
        String detailStr = map.get("ItemDetail");
        Log.i(TAG,"onItemClick:titleStr="+titleStr);
        Log.i(TAG,"onItemClick:detailStr="+detailStr);

        TextView title = (TextView) view.findViewById(R.id.itemTitle);
        TextView detail = (TextView) view.findViewById(R.id.itemDetail);
        String titleStr2= (String) title.getText();
        String detailStr2= (String) detail.getText();
        Log.i(TAG,"onItemClick:titleStr2="+titleStr2);
        Log.i(TAG,"onItemClick:detailStr2="+detailStr2);

        //打开新页面，传参
        Intent rateCalc = new Intent(this,RateCalcActivity.class);
        rateCalc.putExtra("title",titleStr);
        rateCalc.putExtra("detail",Float.parseFloat(detailStr));
        startActivity(rateCalc);

    }
}
