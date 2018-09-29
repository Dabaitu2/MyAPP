package com.example.tomokokawase.myapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;


public class RealHomeworkActivity extends AppCompatActivity {
    private TextView teamA;
    private TextView teamB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_real_homework);
        teamA = findViewById(R.id.textView8);
        teamB = findViewById(R.id.textView6);
    }

    public void teamAplus3(View view) {
        String outValue = (Integer.parseInt(teamA.getText().toString()) + 3)+"";
        teamA.setText(outValue);
    }

    public void teamAplus2(View view) {
        String outValue = (Integer.parseInt(teamA.getText().toString()) + 2)+"";
        teamA.setText(outValue);
    }

    public void teamAplus1(View view) {
        String outValue = (Integer.parseInt(teamA.getText().toString()) + 1)+"";
        teamA.setText(outValue);
    }

    public void teamBplus3(View view) {
        String outValue = (Integer.parseInt(teamB.getText().toString()) + 3)+"";
        teamB.setText(outValue);
    }

    public void teamBplus2(View view) {
        String outValue = (Integer.parseInt(teamB.getText().toString()) + 2)+"";
        teamB.setText(outValue);
    }

    public void teamBplus1(View view) {
        String outValue = (Integer.parseInt(teamB.getText().toString()) + 1)+"";
        teamB.setText(outValue);
    }

    public void reset(View view) {
        teamA.setText("0");
        teamB.setText("0");
    }
}



