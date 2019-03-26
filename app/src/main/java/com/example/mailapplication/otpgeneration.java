package com.example.mailapplication;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class otpgeneration extends AppCompatActivity {
        TextView t1;
        EditText e1;
        Button b;
        String username;
        @TargetApi(Build.VERSION_CODES.JELLY_BEAN)


        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_otpgeneration);

            t1=findViewById(R.id.textView3);
            e1=findViewById(R.id.editText);
            b=findViewById(R.id.button);

            Bundle bundle = getIntent().getExtras();
            String stuff = bundle.getString("hai");
            final String stuff1 = bundle.getString("bye");
            t1.setText(""+stuff);
            username=bundle.getString("username");
            String x= e1.getText().toString().trim();

            b.setOnClickListener(new View.OnClickListener() {
                @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                @Override
                public void onClick(View v) {
                    String x= e1.getText().toString().trim();
                    String y = stuff1;
                    if(x.contentEquals(y)){
                        Toast.makeText(getApplicationContext(),"Sucessfully verified",
                                Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(getApplicationContext(), passwordupdation.class);
                        intent.putExtra("username",username);
                        startActivity(intent);
                        finishAffinity();

                    }
                    else {
                        Toast.makeText(getApplicationContext(),"please enter the valid otp",
                                Toast.LENGTH_LONG).show();

                    }
                }
            });

        }

    }
