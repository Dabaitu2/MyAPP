package com.example.tomokokawase.myapp;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class ExchangeRateActivity extends AppCompatActivity implements Runnable{

    private float rate;
    private TextView ipt;
    private TextView opt;
    private TextView opt02;

    // 初始化展示数据的容器
    private List<Money> moneyList = new ArrayList<>();


    public class Money {
        private float excBuyPrice;

        public float getExcBuyPrice() {
            return excBuyPrice;
        }

        public void setExcBuyPrice(float excBuyPrice) {
            this.excBuyPrice = excBuyPrice;
        }

        public float getCurBuyPrice() {
            return curBuyPrice;
        }

        public void setCurBuyPrice(float curBuyPrice) {
            this.curBuyPrice = curBuyPrice;
        }

        public float getExcSellPrice() {
            return excSellPrice;
        }

        public void setExcSellPrice(float excSellPrice) {
            this.excSellPrice = excSellPrice;
        }

        public float getCurSellPrice() {
            return curSellPrice;
        }

        public void setCurSellPrice(float curSellPrice) {
            this.curSellPrice = curSellPrice;
        }

        public float getInterPrice() {
            return interPrice;
        }

        public void setInterPrice(float interPrice) {
            this.interPrice = interPrice;
        }

        public float getConvertPrice() {
            return convertPrice;
        }

        public void setConvertPrice(float convertPrice) {
            this.convertPrice = convertPrice;
        }

        private String name;

        public Money(float excBuyPrice, String name, float curBuyPrice, float excSellPrice, float curSellPrice, float interPrice, float convertPrice) {
            this.excBuyPrice = excBuyPrice;
            this.name = name;
            this.curBuyPrice = curBuyPrice;
            this.excSellPrice = excSellPrice;
            this.curSellPrice = curSellPrice;
            this.interPrice = interPrice;
            this.convertPrice = convertPrice;
        }

        public String getName() {
            return name;

        }

        public void setName(String name) {
            this.name = name;
        }

        private float curBuyPrice;
        private float excSellPrice;
        private float curSellPrice;
        private float interPrice;
        private float convertPrice;

    }

    // 用于处理子线程的Handler,先预先定义下
    private static Handler handler;

    // 这个activity本身就有在线程下运行的方法(run)
    @Override
    public void run() {
        Log.i("ExchangeRateActivity", "子线程开始运作");
        for (int i = 0; i < 2; i++) {
            Log.i("ExchangeRateActivity", "子线程报数: " + (i+1));
            try {
              Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
                Log.i("ExchangeRateActivity","子线程异常");
            }
        }

        try { //从一个URL加载一个Document对象。
            Document doc = Jsoup.connect("http://www.usd-cny.com").get();
            Elements elements = doc.select("#m > div > div:nth-child(3) > table:nth-child(1) > tbody > tr > td:nth-child(1) > div > a > b");
            for (Element e: elements) {
                Money money = new Money(12,e.text(), 12,13,14,15,16);
                moneyList.add(money);
            }
            Log.i("mytag",elements.text());
        }
        catch(Exception e) {
            Log.i("mytag", e.toString());
        }


        // msg 对象用于返回主线程, 这里的网络方法有点像ajax的xmlhttprequest
        Message msg = handler.obtainMessage(5);
        msg.obj = "子线程run方法传值";
        handler.sendMessage(msg);

//      getWebRes();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exchange_rate);
        ipt = findViewById(R.id.editText3);
        opt = findViewById(R.id.textView12);
        opt02 = findViewById(R.id.editText_getThread);
        rate = 0;

        // 开启子线程,使得run方法生效
        Thread t = new Thread(this);
        t.start();

        // handler就像是个线程监听器，它负责和线程进行交互，获取线程传来的信号和数据
        handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                // msg.what 这种魔术数字定义，其含义由自己来定
                if(msg.what == 5) {
                    String str = (String) msg.obj;
                    Log.i("主线程onCreate方法", "handleMessage方法: 获取到消息" + str);
                    opt02.setText(str);
                    // 初始化RecycleList的数据
                    // initMoney();
                    RecyclerView recyclerView = findViewById(R.id.recycler_view);
                    LinearLayout layout = findViewById(R.id.exchange_rate_layout);
                    LinearLayoutManager layoutManager = new LinearLayoutManager(layout.getContext());
                    recyclerView.setLayoutManager(layoutManager);
                    MoneyAdapter adapter = new MoneyAdapter(moneyList);
                    recyclerView.setAdapter(adapter);
                }
                super.handleMessage(msg);
            }
        };



    }

    public void calcRate(View view) {
        if (view.getId() == R.id.button10) {
            Log.i("欧元说","只有我相等");
            rate = 8.0536f;
        } else if (view.getId() == R.id.button11) {
            Log.i("美元说","只有我相等");
            rate = 6.8785f;
        } else {
            Log.i("韩元说","只有我相等");
            rate = 161.454f;
        }
        opt.setText((ipt.getText().toString()==null ? Float.parseFloat(ipt.getText().toString()): 0 ) * rate + "");
    }


    // mock一下数据
    private void initMoney() {
        for (int i = 0; i < 10; i++) {
            Money money = new Money(12,"某种钱", 12,13,14,15,16);
            moneyList.add(money);
        }
    }

    // 在子线程种获取方法
    private void getWebRes() {
        URL url = null;
        try {
            url = new URL("http://www.usd-cny.com");
            HttpURLConnection http = (HttpURLConnection) url.openConnection();
            InputStream in = http.getInputStream();
            String html = inputString2String(in);
            Log.i("GetWebRes", "获取到html资源: "+html);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * @param inputStream InputStream
     * @return string
     *
     * */
    private String inputString2String (InputStream inputStream) throws IOException {
        final int bufferSize = 1024;
        final char[] buffer  = new char[bufferSize];
        final StringBuilder out = new StringBuilder();
        Reader in = new InputStreamReader(inputStream, "gb2312");
        for (;;) {
            int rsz = in.read(buffer, 0, buffer.length);
            if (rsz < 0) {
                break;
            }
            out.append(buffer, 0, rsz);
        }
        return out.toString();
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == 2) {
            String euro = data.getStringExtra("euro");
            String usd = data.getStringExtra("usd");
            String won = data.getStringExtra("won");
            Log.i("euro", "我的值是: "+euro);
            Log.i("usd", "我的值是: "+usd);
            Log.i("won", "我的值是: "+won);
        }
    }


    public void handleJumpToSave(View view) {
        Intent intent=new Intent(this, SaveActivity.class);
        Bundle bdl = new Bundle();
        bdl.putFloat("Euro", 8.0536f);
        bdl.putFloat("USD", 6.8785f);
        bdl.putFloat("WON", 161.654f);
        intent.putExtras(bdl);
        startActivityForResult(intent, 1);
    }
}
