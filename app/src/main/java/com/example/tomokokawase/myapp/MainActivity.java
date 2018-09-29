package com.example.tomokokawase.myapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView textView = findViewById(R.id.welcomeText);
        textView.setText(R.string.tomoWelcome);
        Button btn = findViewById(R.id.tryButton);
        btn.setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.test_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.select01 :
                Intent intent = new Intent(MainActivity.this, RealHomeworkActivity.class);
                startActivity(intent);
                Log.i("select01", "我被点击了");
                break;
            case R.id.select02 :
                intent = new Intent(MainActivity.this, BMIActivity.class);
                startActivity(intent);
                Log.i("select02", "我被点击了");
                break;
            case R.id.inGroup01 :
                SharedPreferences sharedPreferences = getSharedPreferences("myPreference", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("field01", "被存储的第一个字段");
                editor.putString("field02", "被存储的第二个字段");
                editor.putFloat("euro",  8.0536f);
                editor.putFloat("usd", 6.8785f);
                editor.putFloat("won", 161.654f);
                editor.apply();
                break;
            case R.id.inGroup02 :
                sharedPreferences = getSharedPreferences("myPreference", MODE_PRIVATE);
                String outValue = sharedPreferences.getString("field01", "没有预设值!");
                String outValue02 = sharedPreferences.getString("field02", "没有预设值!");
                Log.i("outValue01", "第一个预设值为" + outValue);
                Log.i("outValue02", "第二个预设值为" + outValue02);

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {
        Log.i("btn", "onClick: 123456");
        EditText editText = findViewById(R.id.editText);
        String value = editText.getText().toString();
        TextView textView = findViewById(R.id.welcomeText);
        textView.setText("Hello " + value);
    }

    public void btnOnclick(View view) {
        Log.i("anotherBtn", "onClick: yeyeyeyeye");
        EditText editText = findViewById(R.id.editText2);
        double value = Integer.parseInt(editText.getText().toString());
        double outValue = 9 / 5 * value + 32;
        TextView textView = findViewById(R.id.textView);
        textView.setText("换算出来的华氏度为 " + outValue);
    }

    public void handleJump(View view) {
        Log.i("btn5", "onClick: ready to jump");
        Intent intent=new Intent(MainActivity.this, RealHomeworkActivity.class);
        startActivity(intent);
    }

    public void handleJumpToBMI(View view) {
        Log.i("btn6", "onClick: ready to jump");
        Intent intent=new Intent(MainActivity.this, BMIActivity.class);
        startActivity(intent);
    }

    public void handleJumpToRate(View view) {
        Intent intent=new Intent(MainActivity.this, ExchangeRateActivity.class);
        startActivity(intent);
    }
}
