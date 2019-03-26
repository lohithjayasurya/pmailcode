package com.example.mailapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class messages2 extends AppCompatActivity implements View.OnClickListener{
    TextView e1;

    String s;
    Button b1,b2,b3;
    String[] y;
    String[] x;
    String[] z;
    String message;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messages2);
        Bundle b=getIntent().getExtras();
        message=b.getString("message");
        String fr=b.getString("from");
        String sub=b.getString("subject");
        x = fr.split(":");
        y = sub.split(":");
        z = message.split(":");
        x[1]=x[1].substring(1,x[1].length()-1);
        y[1]=y[1].substring(1,y[1].length()-1);
        z[1]=z[1].substring(1,z[1].length()-1);
        //Toast.makeText(messages.this," "+x[1]+"  ",Toast.LENGTH_LONG).show();
        String me=fr+"\n"+sub+"\n"+message;
        e1=findViewById(R.id.textView7);
        e1.setText(me);
        b1=findViewById(R.id.button6);
        b1.setOnClickListener(this);
        b2=findViewById(R.id.button7);
        b2.setOnClickListener(this);

    }
    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.button6) {
            Intent r = new Intent(this, composemail.class);
            r.putExtra("subject", y[1]);
            r.putExtra("message", z[1]);
            startActivity(r);

        }
        if (v.getId() == R.id.button7) {
            Intent z = new Intent(this, composemail.class);
            z.putExtra("from", x[1]);
            startActivity(z);
        }
    }
}
