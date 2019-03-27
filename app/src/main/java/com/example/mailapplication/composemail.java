package com.example.mailapplication;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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



public class composemail extends AppCompatActivity implements View.OnClickListener {
EditText e1,e2,e3;
Button b1;
String s=null;
String from,sub,mess;
String[] z;
int n;
int i=0;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_composemail);
        Bundle b=getIntent().getExtras();
        from=b.getString("from");
        sub=b.getString("subject");
        mess=b.getString("message");
        e1=findViewById(R.id.editText8);
        e2=findViewById(R.id.editText9);
        e3=findViewById(R.id.editText10);
        b1=findViewById(R.id.button2);
        e1.setText(from);
        e2.setText(sub);
        e3.setText(mess);
   b1.setOnClickListener(this);


    }
    public void getData(){
        SharedPreferences sp = getSharedPreferences("token",
                Context.MODE_PRIVATE);
        s = sp.getString("name1","NA");
    }

    public void validateEmail(final int k){
        final RequestQueue queue = Volley.newRequestQueue(this);
        final String url = "https://p-mail.herokuapp.com/validateEmail"; // your URL

        queue.start();
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("email",z[k]); // the entered data as the body.


        final String k2 = e1.getText().toString();
        JsonObjectRequest jsObjRequest = new
                JsonObjectRequest(Request.Method.POST,
                url,
                new JSONObject(params),
                new Response.Listener<JSONObject>() {
                    @RequiresApi(api = Build.VERSION_CODES.O)
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String m = response.getString("mails");

                            if (m.length() != 2)
                            {
                                Compose(k);
                            }
                            else{
                                Toast.makeText(composemail.this, "Username "+z[k]+" doesn't exist", Toast.LENGTH_LONG).show();

                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
                , new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //e1.setText("");
            }
        });
        queue.add(jsObjRequest);

    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void Compose(final int k) {
        String to,sub,msg;

                to = z[k];
        sub=((e2.getText().toString()));
        msg=(e3.getText().toString());

    final RequestQueue queue = Volley.newRequestQueue(this);
        final String url = "https://p-mail.herokuapp.com/compose"; // your URL
        getData();
        queue.start();
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("to", to); // the entered data as the body.
        params.put("subject", sub);
        params.put("message", msg);
        params.put("token", s);
        final JsonObjectRequest jsObjRequest = new
                JsonObjectRequest(Request.Method.POST,
                url,
                new JSONObject(params),
                new Response.Listener<JSONObject>() {
                    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String m=response.getString("message");
                            Toast.makeText(composemail.this, "Mail sent successfully to "+z[k], Toast.LENGTH_SHORT).show();
                            if(k==n-1) {
                                Intent i5 = new Intent(composemail.this,inbox.class);
                                startActivity(i5);
                                finishAffinity();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                e1.setText(error.toString());
                e2.setText(s);
            }

        });
        queue.add(jsObjRequest);
    }


    @Override
    public void onClick(View v) {
        String p1 = e1.getText().toString();

        String p2 = e2.getText().toString();
        String p3 = e3.getText().toString();
        if(!(emptycheck(p1))) {
            if (!(emptycheck(p2))) {
                if (p2.length() < 15) {
                    if (!(p3.isEmpty())) {
                        if(!((p2.contains(","))||(p2.contains(":"))||(p2.contains("\\"))||(p3.contains(","))||(p3.contains(":"))||(p3.contains("\\"))||(p2.contains("/"))||(p3.contains("/")))){
                        z=e1.getText().toString().split(",");

                         n=z.length;
                        Log.d(n+":   ", "onResponse: ");
                        for(i=0;i<n;i++) {
                            getData();
                            Log.d(z[i] + ":   ", "onResponse: ");
                            validateEmail(i);
                        }

                        }
                        else{
                            Toast.makeText(composemail.this, "subject or message should not contains , or : or \\ or /", Toast.LENGTH_SHORT).show();
                        }
                        }else{
                        Toast.makeText(composemail.this, "message should not be empty", Toast.LENGTH_SHORT).show();
                    }

                    } else {
                        Toast.makeText(composemail.this, "subject should not be greater than 15 characters", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Toast.makeText(composemail.this, "subject should not be empty", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(composemail.this, "username should not be empty", Toast.LENGTH_SHORT).show();
            }


    }

    public boolean emptycheck(String x)
    {
        return x.isEmpty();
    }

    }

