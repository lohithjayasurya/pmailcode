package com.example.mailapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
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
import java.util.Map;

public class register extends AppCompatActivity implements View.OnClickListener {

    Button b1;
    EditText e1, e2, e3, e4, e5, e6;
    TextView t1;
    RadioButton b2, b3, b4;
    String gender = "others";
    SharedPreferences pref;
    String m="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        b1 = (Button) findViewById(R.id.button);
        b2 = (RadioButton) findViewById(R.id.rd1);
        b3 = (RadioButton) findViewById(R.id.rd2);
        b4 = (RadioButton) findViewById(R.id.rd3);
        e1 = findViewById(R.id.editText);
        e2 = findViewById(R.id.editText2);
        e3 = findViewById(R.id.editText3);
        e4 = findViewById(R.id.editText4);
        e5 = findViewById(R.id.editText5);
        e6 = findViewById(R.id.editText6);
        b2.setOnClickListener(this);
        b3.setOnClickListener(this);
        b4.setOnClickListener(this);
        b1.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        final String p1 = e1.getText().toString();
        String p2 = e2.getText().toString();
        String p3 = e3.getText().toString();
        String p4 = e4.getText().toString();
        String p5 = e5.getText().toString();
        String p6 = e6.getText().toString();

        if (v.getId() == R.id.rd1) {
            gender = "male";
        }
        if (v.getId() == R.id.rd2) {
            gender = "female";
        }
        if (v.getId() == R.id.rd3) {
            gender = "others";
        }

        if (v.getId() == R.id.button) {
            if (!(p1.isEmpty())) {
                if (!(p2.isEmpty())) {
                    if (!(p3.isEmpty())) {
                        if (!(p4.isEmpty())) {
                            if (!(p5.isEmpty())) {
                                if (!(p6.isEmpty())) {
                                    if (p1.length() > 4 && p1.length() < 20) {
                                        if (p2.length() > 0 && p2.length() < 10) {
                                            if (p3.length() > 0 && p3.length() < 10) {
                                                if (p4.length() == 10) {
                                                    if (p5.length() > 7 && p5.length() < 15) {
                                                        if (p6.length() > 7 && p6.length() < 15) {
                                                            if (p5.equals(p6)) {

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
                                                                            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                                                                            @Override
                                                                            public void onResponse(JSONObject response) {
                                                                                try {
                                                                                    m = response.getString("mails");
                                                                                     if(m.length()!=2){
                                                                                        Toast.makeText(register.this, "Username already exists", Toast.LENGTH_LONG).show();
                                                                                         e1.setText("");
                                                                                    }

                                                                                    if (m.length()==2) {

                                                                                        register();
                                                                                        Toast.makeText(register.this, "Registered Successfully", Toast.LENGTH_LONG).show();
                                                                                        Intent i = new Intent(register.this, MainActivity.class);
                                                                                        startActivity(i);
                                                                                        finishAffinity();
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
                                                                Toast.makeText(this, "password and confirm password should match", Toast.LENGTH_SHORT).show();
                                                            }
                                                        } else {
                                                            Toast.makeText(this, "password length should be between 8 to 15 chacters ", Toast.LENGTH_SHORT).show();
                                                        }

                                                    } else {
                                                        Toast.makeText(this, "password length should be between 8 to 15 chacters ", Toast.LENGTH_SHORT).show();
                                                    }
                                                } else {
                                                    Toast.makeText(this, "mobile number incorrect ", Toast.LENGTH_SHORT).show();
                                                }
                                            } else {
                                                Toast.makeText(this, "last name length should be 1 to 10 characters ", Toast.LENGTH_SHORT).show();
                                            }
                                        } else {
                                            Toast.makeText(this, "last name length should be 1 to 10 characters ", Toast.LENGTH_SHORT).show();
                                        }
                                    } else {
                                        Toast.makeText(this, "username length should be 4 to 20 characters ", Toast.LENGTH_SHORT).show();
                                    }
                                } else {
                                    Toast.makeText(this, "please Re-Enter  password", Toast.LENGTH_SHORT).show();
                                }

                            } else {
                                Toast.makeText(this, "password should not be empty", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(this, "phone number should not be empty", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(this, "Last name should not be empty", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(this, "Firstname should not be empty", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, "Username should not be empty", Toast.LENGTH_SHORT).show();
            }

            int t = 0;





        }
    }

    public void register() {

        final RequestQueue queue = Volley.newRequestQueue(this);
        final String url = "https://p-mail.herokuapp.com/register"; // your URL

        queue.start();
        HashMap<String, String> params = new HashMap<String, String>();

        params.put("email", e1.getText().toString()); // the entered data as the body.
        params.put("first_name", e2.getText().toString());
        params.put("last_name", e3.getText().toString());
        params.put("phone_no", e4.getText().toString());
        params.put("gender", gender);
        params.put("password", e6.getText().toString());


        final String k2 = e1.getText().toString();
        JsonObjectRequest jsObjRequest = new
                JsonObjectRequest(Request.Method.POST,
                url,
                new JSONObject(params),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            response.getString("mails");

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                e1.setText("n");
            }
        });
        queue.add(jsObjRequest);


    }


    public void validatemail() {

    }




//
//    public void phoneno() {
//        final RequestQueue queue = Volley.newRequestQueue(this);
//        final String url = "https://p-mail.herokuapp.com/validatePhone"; // your URL
//
//        queue.start();
//        HashMap<String, String> params = new HashMap<String, String>();
//        params.put("phone_no", e4.getText().toString()); // the entered data as the body.
//
//
//        final String k2 = e1.getText().toString();
//        JsonObjectRequest jsObjRequest = new
//                JsonObjectRequest(Request.Method.POST,
//                url,
//                new JSONObject(params),
//                new Response.Listener<JSONObject>() {
//                    @Override
//                    public void onResponse(JSONObject response) {
//                        try {
//                            m = response.getString("nos");
//                            Toast.makeText(register.this, m.length(), Toast.LENGTH_LONG).show();
//                            if (m.length()==2) {
//                                register();
//
//                            }
//                            else{
//                                Toast.makeText(register.this, "Phone no already exists", Toast.LENGTH_LONG).show();
//
//                            }
//
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                e1.setText("");
//            }
//        });
//        queue.add(jsObjRequest);
//
//    }

}







