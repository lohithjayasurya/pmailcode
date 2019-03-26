package com.example.mailapplication;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
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


public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    Button b1, b2, b3;
    EditText e1, e2;
    String s = "";
    SharedPreferences pref;
    CheckBox c;
    int n=0;
    int i=1;
    String s1="";
    String s2="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        b1 = (Button) findViewById(R.id.button3);
        b2 = (Button) findViewById(R.id.button4);
        b3 = (Button) findViewById(R.id.button5);
        e1 = findViewById(R.id.username);
        e2 = findViewById(R.id.password);
        c=(CheckBox)findViewById(R.id.checkBox) ;
        b1.setOnClickListener(this);
        b2.setOnClickListener(this);
        b3.setOnClickListener(this);
        c.setOnClickListener(this);
        getData();
        if(!(s1.equals("NA"))){
            e1.setText(s1);
            e2.setText(s2);
        }
    }



    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onClick(View view) {
        int k, k1;
        String p1 = e1.getText().toString();
        String p2 = e2.getText().toString();
        if(view.getId()==R.id.checkBox){
            i=i+1;
            if(i%2==0) {
                n = 1;
                Toast.makeText(MainActivity.this, "Remember me", Toast.LENGTH_SHORT).show();
            }
            else{
                n=0;
            }

        }
        if (view.getId() == R.id.button3) {

            if (!(p1.isEmpty())) {
                if (p1.length() > 4 && p1.length() < 20) {
                    final RequestQueue queue = Volley.newRequestQueue(this);
                    final String url = "https://p-mail.herokuapp.com/validateEmail"; // your URL

                    queue.start();
                    HashMap<String, String> params = new HashMap<String, String>();
                    params.put("email", e1.getText().toString()); // the entered data as the body.


                    final String k2 = e1.getText().toString();
                    JsonObjectRequest jsObjRequest = new
                            JsonObjectRequest(Request.Method.POST,
                            url,
                            new JSONObject(params),
                            new Response.Listener<JSONObject>() {
                                @Override
                                public void onResponse(JSONObject response) {
                                    try {
                                        String m = response.getString("mails");

                                        if (m.length() != 2)
                                            {
                                                try{
                                                Bundle bundle1 = new Bundle();
                                                bundle1.putString("username", e1.getText().toString());
                                                Intent l1 = new Intent(MainActivity.this, forgotpassword.class);
                                                l1.putExtras(bundle1);
                                                l1.putExtra("username", e1.getText().toString());
                                                startActivity(l1);
                                                finishAffinity();
                                            }


                                            catch (Exception err){
                                                err.printStackTrace();
                                            }

                                        }
                                        else{
                                            Toast.makeText(MainActivity.this, "Username doesn't exist", Toast.LENGTH_LONG).show();

                                        }

                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            e1.setText("");
                        }
                    });
                    queue.add(jsObjRequest);


                } else {
                    Toast.makeText(this, "username should be only 4 to 20 characters", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, "username should not be empty", Toast.LENGTH_SHORT).show();
            }


        }
        if (view.getId() == R.id.button4) {
                                                           if (!(p1.isEmpty())) {
                                                    if (!(p2.isEmpty())) {
                                                        if (p1.length() > 4 && p1.length() < 20) {
                                                            if (p2.length() > 7 && p2.length() < 15) {
                                                                final String url = "https://p-mail.herokuapp.com/login"; // your URL
                                                                final RequestQueue queue = Volley.newRequestQueue(this);

                                                                queue.start();
                                                                HashMap<String, String> params = new HashMap<String, String>();
                                                                params.put("email", e1.getText().toString()); // the entered data as the body.
                                                                params.put("password", e2.getText().toString());


                                                                final String k2 = e1.getText().toString();
                                                                JsonObjectRequest jsObjRequest = new
                                                                        JsonObjectRequest(Request.Method.POST,
                                                                        url,
                                                                        new JSONObject(params),
                                                                        new Response.Listener<JSONObject>() {
                                                                            @Override
                                                                            public void onResponse(JSONObject response) {
                                                                                try {
                                                                                    //e1.setText("");
                                                                                    //e2.setText("");
                                                                                    s = (response.getString("token"));
                                                                                    saveData();
                                                                                    int k=0;
                                                                                    //if(s.equals("")){
                                                                                       // Toast.makeText(MainActivity.this, "email or password is wrong", Toast.LENGTH_SHORT).show();
                                                                                   // }
                                                                                    if (!(s.equals(""))) {
                                                                                        k=1;
                                                                                    }
                                                                                    if(k==1){
                                                                                        if(n==1){
                                                                                            Toast.makeText(MainActivity.this, "Remembered", Toast.LENGTH_LONG).show();
                                                                                            saved();
                                                                                        }
                                                                                        e1.setText("");
                                                                                        e2.setText("");
                                                                                        Toast.makeText(MainActivity.this, "logging in", Toast.LENGTH_SHORT).show();
                                                                                        Intent i = new Intent(MainActivity.this, inbox.class);
                                                                                        startActivity(i);
                                                                                        finishAffinity();
                                                                                    }
                                                                                    if(!(k==1)){
                                                                                        Toast.makeText(MainActivity.this, "email or password is wrong", Toast.LENGTH_SHORT).show();
                                                                                    }

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

                        } else {
                            Toast.makeText(this, "password should be only 8 to 15 characters", Toast.LENGTH_SHORT).show();
                        }


                    } else {
                        Toast.makeText(this, "username should be only 4 to 20 characters", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(this, "password should not be empty", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, "username should not be empty", Toast.LENGTH_SHORT).show();
            }

        }
        if (view.getId() == R.id.button5) {
            Intent l = new Intent(this, register.class);
            startActivity(l);

        }




    }


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public void saveData() {
        SharedPreferences sp = getSharedPreferences
                ("token", Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = sp.edit();
        edit.putString("name1", s);
        edit.commit();

    }
    public void saved() {
        SharedPreferences spi = getSharedPreferences
                ("usernmae", Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = spi.edit();
        edit.putString("name",e1.getText().toString());
        edit.putString("password",e2.getText().toString());
        edit.commit();

    }

    public void getData() {
        SharedPreferences spi = getSharedPreferences("usernmae",
                Context.MODE_PRIVATE);
        s1 = spi.getString("name", "");
        s2 = spi.getString("password", "");
    }


    protected void onPostExecute(String result) {
        Toast.makeText(MainActivity.this, "onPostExecute called", Toast.LENGTH_SHORT).show();
        Log.d("AsyncTask","onPostExecute called");
        // execution of result of Long time consuming operation
        Intent i = new Intent(this, register.class);
        startActivity(i);
    }


    protected void onProgressUpdate(String... text) {
        Log.d("AsyncTask","onProgressUpdate called");
        Toast.makeText(MainActivity.this, "onProgressUpdate called", Toast.LENGTH_SHORT).show();


    }



}










