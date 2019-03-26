package com.example.mailapplication;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
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

public class options extends AppCompatActivity implements  AdapterView.OnItemClickListener {
    ListView lv;
    String s;
    String[] options={"INBOX","STARRED","SEEN","UNSEEN","COMPOSE MESSAGE","SENT","TRASH","REPORT","UPDATE PROFILE","LOGOUT","DELETE ACCOUNT"};
    String su=null;

    public void getData(){
        SharedPreferences sp = getSharedPreferences("token",
                Context.MODE_PRIVATE);
        s = sp.getString("name1", "NA");
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options);

        lv=findViewById(R.id.listv);
        ArrayAdapter<String>ada;
        ada = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,options);
        lv.setAdapter(ada);
        lv.setOnItemClickListener(this);

    }
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        TextView txt=(TextView)view;

        if(txt.getText().equals("INBOX")){
            Intent i =new Intent(this,inbox.class);
            startActivity(i);

        }
        if(txt.getText().equals("STARRED")){
            Intent i =new Intent(this,starred.class);
            startActivity(i);

        }
        if(txt.getText().equals("COMPOSE MESSAGE")){
            Intent i =new Intent(this,composemail.class);
            i.putExtra("message","");
            startActivity(i);
        }
        if(txt.getText().equals("SENT")){
            Intent i =new Intent(this,sent.class);
            startActivity(i);
        }
        if(txt.getText().equals("SEEN")){
            Intent i =new Intent(this,seen.class);
            startActivity(i);
        }
        if(txt.getText().equals("UNSEEN")){
            Intent i =new Intent(this,unseen.class);
            startActivity(i);
        }
        if(txt.getText().equals("TRASH")){

        }
        if(txt.getText().equals("LOGOUT")){


            Intent i =new Intent(this,MainActivity.class);
            startActivity(i);
            finishAffinity();
        }
        if(txt.getText().equals("UPDATE PROFILE"))
        {
            Intent i =new Intent(this,update_profile.class);
            startActivity(i);

        }
        if(txt.getText().equals("DELETE ACCOUNT")){

            getData();
            final RequestQueue queue = Volley.newRequestQueue(this);
            final String url = "https://p-mail.herokuapp.com/deleteAcc"; // your URL

            queue.start();
            HashMap<String, String> params = new HashMap<String, String>();
            getData();
            params.put("token",s);


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
                                Toast.makeText(options.this,m,Toast.LENGTH_LONG).show();

                                Intent i =new Intent(options.this,MainActivity.class);
                                startActivity(i);
                                finishAffinity();
                                  }
                            catch (JSONException e) {
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
        if(txt.getText().equals("REPORT"))
        {
            Intent i =new Intent(this,report.class);
            startActivity(i);

        }




    }




}
