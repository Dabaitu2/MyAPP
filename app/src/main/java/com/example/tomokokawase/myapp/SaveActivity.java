package com.example.tomokokawase.myapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class SaveActivity extends AppCompatActivity {

    private TextView text01;
    private TextView text02;
    private TextView text03;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save);
        text01 = findViewById(R.id.textView13);
        text02 = findViewById(R.id.textView14);
        text03 = findViewById(R.id.textView15);

    }


    public void saveAndBack(View view) {
        Intent intent = getIntent();
        String eruo = intent.getExtras().getFloat("Euro")+"";
        String usd  = intent.getExtras().getFloat("USD")+"";
        String won  = intent.getExtras().getFloat("WON")+"";

        text01.setText(eruo);
        text02.setText(usd);
        text03.setText(won);

        intent.putExtra("euro", eruo);
        intent.putExtra("usd", usd);
        intent.putExtra("won", won);
        setResult(2, intent);
        finish();
    }
}
