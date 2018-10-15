package com.example.wangjie.list_rate;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends Activity implements AdapterView.OnItemClickListener{

    private  static List<Map<String,String>> list=new ArrayList<>();
    private ListView lv_main;
    private static  Map<String,String> map;
    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if(msg.what==1){
                String[] from={"title","rate"};
                int[] to={R.id.tv_title,R.id.tv_rate};
                ArrayList<Map<String,String>> l= (ArrayList<Map<String,String>>) msg.obj;
                SimpleAdapter adapter=new SimpleAdapter(MainActivity.this,l,R.layout.mylist,from,to);
                lv_main.setAdapter(adapter);
                lv_main.setEmptyView(findViewById(R.id.tv_noData));
                lv_main.setOnItemClickListener(MainActivity.this);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lv_main=findViewById(R.id.lv_main);
        new Thread(){
            @Override
            public void run() {
                try {
                    getRateFromWeb("http://www.zou114.com/agiotage/huilv.asp?bi=CNY");
                    Message message=Message.obtain();
                    message.what=1;
                    message.obj=list;
                    handler.sendMessage(message);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    private static  void getRateFromWeb(String url)throws IOException {
        Document document = Jsoup.connect(url).get();
        Elements trs= document.getElementsByTag("table").get(0).getElementsByTag("tr");
        for(Element tr : trs) {
            Elements tds=tr.getElementsByTag("td");
           map=new HashMap<>();
           map.put("title",tds.get(0).text());
           map.put("rate",tds.get(2).text());
           list.add(map);
        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        String title= ((TextView)view.findViewById(R.id.tv_title)).getText().toString();
        String rate=((TextView)view.findViewById(R.id.tv_rate)).getText().toString();
        String nation=title.substring(4,title.length()-2);
        Intent intent=new Intent(this,RateActivity.class);
        intent.putExtra("nation",nation);
        intent.putExtra("rate",rate);
        startActivity(intent);
    }
}
