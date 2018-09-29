package com.example.tomokokawase.myapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class BMIActivity extends AppCompatActivity {

    private TextView height;
    private TextView weight;
    private TextView hint;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bmi);
        height = findViewById(R.id.editText5);
        weight = findViewById(R.id.decimalEditText);
        hint   = findViewById(R.id.textView11);

    }

    public void handleCalc(View view) {
        try {
            double NumberWeight = Double.parseDouble(weight.getText().toString());
            double NumberHeight = Double.parseDouble(height.getText().toString());

            if (NumberHeight < 0.5 || NumberHeight > 3) {
                Toast.makeText(this, "您的身高有误", Toast.LENGTH_LONG).show();
                return;
            }

            if (NumberWeight < 0 || NumberWeight > 400) {
                Toast.makeText(this, "您的体重有误", Toast.LENGTH_LONG).show();
                return;
            }

            double rst = (NumberWeight) / Math.pow(NumberHeight, 2);


            TextView rstText = findViewById(R.id.rst01);
            rstText.setText("您的BMI指数为: "+rst);
            if(rst < 18.5) {
                hint.setText("您的体重过轻");
                return;
            }
            if(rst < 23.9) {
                hint.setText("您的体重正常");
                return;
            }
            if(rst < 27) {
                hint.setText("您的体重过重");
                return;
            }
            if(rst < 32) {
                hint.setText("您的体重肥胖");
                return;
            }
            hint.setText("您的体重过胖");
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "您的输入有误", Toast.LENGTH_LONG).show();
        }
    }
}
