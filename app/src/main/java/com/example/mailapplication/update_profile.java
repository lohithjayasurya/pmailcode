package com.example.mailapplication;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
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

public class update_profile extends AppCompatActivity implements View.OnClickListener {
    String s = null;
    Button b1, b2;
    TextView t;
    EditText e1,e2,e3;
    String split[]={};
    String[] email;
    String[] firstname;
    String[] lastname;
    String[] number;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_profile);
        getData();
        t=findViewById(R.id.textView10);
        e1=findViewById(R.id.editText12);
        e2=findViewById(R.id.editText14);
        e3=findViewById(R.id.editText15);
        b1=findViewById(R.id.button9);
        b2=findViewById(R.id.button10);
        b1.setOnClickListener(this);
        b2.setOnClickListener(this);
        final RequestQueue queue = Volley.newRequestQueue(this);
        final String url = "https://p-mail.herokuapp.com/getdetails"; // your URL

        queue.start();
        HashMap<String, String> params = new HashMap<String, String>();
        getData();
        params.put("token",s);


        JsonObjectRequest jsObjRequest = new
                JsonObjectRequest(Request.Method.POST,
                url,
                new JSONObject(params),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String m = response.getString("person");
                            split = m.split(",");
                            email = split[1].split(":");
                            email[1]=email[1].substring(1,email[1].length()-1);
                            firstname = split[2].split(":");
                            firstname[1]=firstname[1].substring(1,firstname[1].length()-1);
                            lastname = split[3].split(":");
                            lastname[1]=lastname[1].substring(1,lastname[1].length()-1);
                            number = split[4].split(":");
                            number[1]=number[1].substring(1,number[1].length()-1);
                            t.setText(email[1]+"@pmail.com");
                            e1.setText(firstname[1]);
                            e2.setText(lastname[1]);
                            e3.setText(number[1]);

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
    public void getData(){
        SharedPreferences sp = getSharedPreferences("token",
                Context.MODE_PRIVATE);
        s = sp.getString("name1","NA");
    }

    @Override
    public void onClick(View v) {
        final String p1 = e1.getText().toString();
        String p2 = e2.getText().toString();
        String p3 = e3.getText().toString();
        if(v.getId()==R.id.button9){
            if(!(p1.isEmpty())){
                if(!(p2.isEmpty())){
                    if(!(p3.isEmpty())){
                        if(p1.length() > 0 && p1.length() < 10){
                            if(p2.length() > 0 && p2.length() < 10){
                                if(p3.length()==10){
                                    getData();

                                    final RequestQueue queue = Volley.newRequestQueue(this);
                                    final String url = "https://p-mail.herokuapp.com/update"; // your URL

                                    queue.start();
                                    HashMap<String, String> params = new HashMap<String, String>();
                                    params.put("token", s); // the entered data as the body.
                                    params.put("first_name",p1);
                                    params.put("last_name",p2);
                                    params.put("phone_no",p3);

                                    JsonObjectRequest jsObjRequest = new
                                            JsonObjectRequest(Request.Method.POST,
                                            url,
                                            new JSONObject(params),
                                            new Response.Listener<JSONObject>() {
                                                @Override
                                                public void onResponse(JSONObject response) {
                                                    try {

                                                        String m = response.getString("message");
                                                        Toast.makeText(update_profile.this, "profile updated", Toast.LENGTH_SHORT).show();
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
                                else
                                {
                                    Toast.makeText(this, "mobile number incorrect ", Toast.LENGTH_SHORT).show();
                                }
                            }
                            else
                            {
                                Toast.makeText(this, "last name should be between 1 to 10 characters ", Toast.LENGTH_SHORT).show();
                            }
                        }
                        else
                        {
                            Toast.makeText(this, "first name should be between 1 to 10 characters ", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else
                    {
                        Toast.makeText(this, "mobile number should not be empty ", Toast.LENGTH_SHORT).show();
                    }
                }
                else
                {
                    Toast.makeText(this, "last name should not be empty ", Toast.LENGTH_SHORT).show();
                }

            }
            else
            {
                Toast.makeText(this, "first name should not be empty ", Toast.LENGTH_SHORT).show();
            }

        }
        if(v.getId()==R.id.button10){
            Intent intent = new Intent(getApplicationContext(), pasupdation2.class);
            intent.putExtra("username",email[1]);
            startActivity(intent);
        }
    }
}






