package com.example.mailapplication;


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

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class passwordupdation extends AppCompatActivity {

        String username;
        EditText e5,e6;
        Button b1;
        TextView t1;


        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_passwordupdation);
            Bundle b=getIntent().getExtras();
            username=b.getString("username");
            e5=findViewById(R.id.editText6);
            e6=findViewById(R.id.editText7);
            t1=findViewById(R.id.textView6);
            b1=findViewById(R.id.button3);
            b1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String x =e5.getText().toString().trim();
                    String y =e6.getText().toString().trim();
                    if(!(x.isEmpty())){
                        if(!(y.isEmpty())){
                            if(x.length()>=8){
                                if(y.length()>=8){
                                    if(equalret(x,y)){
                                        final RequestQueue queue = Volley.newRequestQueue(passwordupdation.this);
                                        final String url = "https://p-mail.herokuapp.com/update_pass"; // your URL

                                        queue.start();
                                        HashMap<String, String> params = new HashMap<String, String>();
                                        params.put("mail",username); // the entered data as the body.
                                        params.put("password",e5.getText().toString());

                                        JsonObjectRequest jsObjRequest = new
                                                JsonObjectRequest(Request.Method.POST,
                                                url,
                                                new JSONObject(params),
                                                new Response.Listener<JSONObject>() {
                                                    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                                                    @Override
                                                    public void onResponse(JSONObject response) {
                                                        try {
                                                            String m = response.getString("message");

                                                            Toast.makeText(getApplicationContext(), "password updated",
                                                                    Toast.LENGTH_SHORT).show();
                                                            Intent i=new Intent(passwordupdation.this,MainActivity.class);
                                                            startActivity(i);
                                                            finishAffinity();
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

                                    }
                                    else{
                                        Toast.makeText(getApplicationContext(), "password and confirm passwoord doesn't matched",
                                                Toast.LENGTH_SHORT).show();
                                    }
                                }
                                else{
                                    Toast.makeText(getApplicationContext(), "password should be atleast 8 characters",
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                            else{
                                Toast.makeText(getApplicationContext(), "password should be atleast 8 characters",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                        else{
                            Toast.makeText(getApplicationContext(), "confirm password should not be empty",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                    else{
                        Toast.makeText(getApplicationContext(), "password should not be empty",
                                Toast.LENGTH_SHORT).show();
                    }


                }
            });

        }
    public boolean equalret(String x,String y)
    {
        return x.equals(y);
    }
    }
