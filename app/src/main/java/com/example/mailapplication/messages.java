package com.example.mailapplication;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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

public class messages extends AppCompatActivity implements View.OnClickListener {
    TextView e1;
    String s;
    Button b1, b2, b3, b4;
    String[] y;
    String[] x;
    String[] z;
    String message;
    public void getData() {
        SharedPreferences sp = getSharedPreferences("token",
                Context.MODE_PRIVATE);
        s = sp.getString("name1", "NA");
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messages);
        Bundle b = getIntent().getExtras();
        message = b.getString("message");
        String fr = b.getString("from");
        String sub = b.getString("subject");
        x = fr.split(":");
        y = sub.split(":");
        z = message.split(":");


        x[1] = x[1].substring(1, x[1].length() - 1);
        y[1] = y[1].substring(1, y[1].length() - 1);
        z[1] = z[1].substring(1, z[1].length() - 1);

        //   Toast.makeText(messages.this," "+x[1]+" "+y[1]+" "+z[1],Toast.LENGTH_LONG).show();


        e1 = findViewById(R.id.textView7);
        //Toast.makeText(messages.this," "+me+"  ",Toast.LENGTH_LONG).show();
        String me = "Sender : " + x[1] + "\n" + "Subject : " + y[1] + "\n" + "Message : " + z[1];
        e1.setText(me);


        b1 = findViewById(R.id.button6);
        b1.setOnClickListener(this);
        b2 = findViewById(R.id.button7);
        b2.setOnClickListener(this);
        b3 = findViewById(R.id.button8);
        b3.setOnClickListener(this);
        b4 = findViewById(R.id.button12);
        b4.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.button6) {
            Intent r = new Intent(this, composemail.class);
            r.putExtra("subject", y[1]);
            r.putExtra("message", z[1]);
            startActivity(r);

        }
        if (v.getId() == R.id.button12) {
            getData();

            final RequestQueue queue = Volley.newRequestQueue(this);
            final String url = "https://p-mail.herokuapp.com/starred"; // your URL
            queue.start();
            HashMap<String, String> params = new HashMap<String, String>();
            params.put("token", s); // the entered data as the body.
            params.put("choice", "0");
            params.put("person", x[1]);
            params.put("subject", y[1]);
            params.put("message", z[1]);

            final String k3 = e1.getText().toString();
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
                                Toast.makeText(messages.this, "mail saved as starred", Toast.LENGTH_SHORT).show();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    e1.setText(error.getMessage());
                }
            });
            queue.add(jsObjRequest);


        }
        if (v.getId() == R.id.button7) {
            Intent z = new Intent(this, composemail.class);
            z.putExtra("from", x[1]);
            startActivity(z);
        }
        if (v.getId() == R.id.button8) {
            getData();

            final RequestQueue queue = Volley.newRequestQueue(this);
            final String url = "https://p-mail.herokuapp.com/deleteInb"; // your URL

            queue.start();
            HashMap<String, String> params = new HashMap<String, String>();
            params.put("token", s); // the entered data as the body.
            params.put("rec", x[1]);
            params.put("subject", y[1]);
            params.put("message", z[1]);

            final String k2 = e1.getText().toString();
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
                                Toast.makeText(messages.this, "mail deleted", Toast.LENGTH_SHORT).show();
                                Intent i5 = new Intent(messages.this, inbox.class);
                                startActivity(i5);
                                finishAffinity();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    e1.setText(error.getMessage());
                }
            });
            queue.add(jsObjRequest);


        }

    }
}