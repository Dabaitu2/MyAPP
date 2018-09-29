package com.example.tomokokawase.myapp;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class ExchangeRateActivity extends AppCompatActivity implements Runnable{

    private float rate;
    private TextView ipt;
    private TextView opt;
    private TextView opt02;

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

        // msg 对象用于返回主线程, 这里的网络方法有点像ajax的xmlhttprequest
        Message msg = handler.obtainMessage(5);
        msg.obj = "子线程run方法传值";
        handler.sendMessage(msg);
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

        handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                // msg.what 这种魔术数字定义，其含义由自己来定
                if(msg.what == 5) {
                    String str = (String) msg.obj;
                    Log.i("主线程onCreate方法", "handleMessage方法: 获取到消息" + str);
                    opt02.setText(str);
                    getWebRes();
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

    // 在子线程种获取方法
    private void getWebRes() {
        URL url = null;
        try {
            url = new URL("http://www.usd-cny.com/icbc.html");
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

    private String inputString2String (InputStream inputStream) throws IOException {
        if (inputStream == null) {
            return null;
        }
        int i = -1;
        byte[] b = new byte[1024];
        StringBuffer sb = new StringBuffer();
        while ((i = inputStream.read(b)) != -1) {
            sb.append(new String(b, 0, i));		}
        return sb.toString();
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
