package com.example.mailapplication;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;

public class report extends AppCompatActivity {

    EditText e1,e2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);
        e1  = findViewById(R.id.sent12);
        e2  = findViewById(R.id.received12);
        SharedPreferences sp = getSharedPreferences("count1",
                Context.MODE_PRIVATE);
        int count1=  sp.getInt("inbox",0);
        String c = Integer.toString(count1) ;
        e2.setText(c);
        SharedPreferences sp1 = getSharedPreferences("count2",
                Context.MODE_PRIVATE);
        int count2=  sp1.getInt("sent",0);
        String c1 = Integer.toString(count2) ;
        e1.setText(c1);

    }
}
