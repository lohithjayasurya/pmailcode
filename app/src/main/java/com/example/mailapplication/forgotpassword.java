package com.example.mailapplication;


import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Random;

public class forgotpassword extends AppCompatActivity {

        Button b8;
        TextView text;
        static  int x;
        String username;
        String m;
        String n;
        String n1;
        static Random random;
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_forgotpassword);
            Bundle b=getIntent().getExtras();
            username=b.getString("username");
            final int count=generateOtp();
            b8 = findViewById(R.id.button2);
            text=findViewById(R.id.textView8);

            final RequestQueue queue = Volley.newRequestQueue(forgotpassword.this);
            final String url = "https://p-mail.herokuapp.com/email"; // your URL

            queue.start();
            HashMap<String, String> params = new HashMap<String, String>();
            params.put("email",username); // the entered data as the body.


            JsonObjectRequest jsObjRequest = new
                    JsonObjectRequest(Request.Method.POST,
                    url,
                    new JSONObject(params),
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                m = response.getString("phone_no");

                                String[] split = m.split(",");
                                n=split[4].substring(12,split[4].length()-1);
                                n1=n.substring(0,2)+"*******";
                                text.setText(n1);

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    error.printStackTrace();
                }
            });
            queue.add(jsObjRequest);
            b8.setOnClickListener(new View.OnClickListener() {
                @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                @Override
                public void onClick(View v) {

                        Intent intent = new Intent(getApplicationContext(), otpgeneration.class);
                        intent.putExtra("username",username);
                        SmsManager sms = SmsManager.getDefault();
                        sms.sendTextMessage(n, null, "the otp is:" + String.valueOf(count), null, null);



                        Bundle bundle = new Bundle();
                        bundle.putString("hai", n1);
                        bundle.putString("bye", String.valueOf(count));
                        intent.putExtras(bundle);
                        intent.putExtra("username",username);
                        startActivity(intent);
                        finishAffinity();
                    }
            });
        }
        public  static Integer generateOtp() {
            Integer MIN=100000;
            Integer MAX=999999;
            random=new Random();
            x=random.nextInt(((MAX-MIN)+1)+MIN);
            return x;
        }


    }
