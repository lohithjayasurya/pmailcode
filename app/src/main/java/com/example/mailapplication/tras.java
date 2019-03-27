package com.example.mailapplication;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
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

public class tras extends AppCompatActivity implements AdapterView.OnItemClickListener{
    ListView lv;
    int n=0;
    String[] messages;
    String[] recmessages;
    String[] rec;
    String[] sub;
    String split[]={};
    String s;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tras);
        lv=(ListView)findViewById(R.id.listview);
        getData();
        final RequestQueue queue = Volley.newRequestQueue(this);
        final String url = "https://p-mail.herokuapp.com/gettrash"; // your URL

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
                            String m = response.getString("mails");
                            Toast.makeText(tras.this,m,Toast.LENGTH_LONG).show();
                            Log.d(m+":   ", "onResponse: ");

                            split = m.split(",");
                            n=split.length/8;
                            Log.d(n+":   ", "onResponse: ");
                            int i,j;
                            recmessages=new String[n];
                            rec=new String[n];
                            String[] date=new String[n];
                            sub=new String[n];
                            String[] time=new String[n];
                            messages=new String[n];
                            for(i=0,j=1;i<n;i++,j+=8){
                                rec[i]=split[j];
                                date[i]=split[j+5];
                                sub[i]=split[j+1];
                                time[i]=split[j+6];
                                recmessages[i]=split[j+2];
                                messages[i]=rec[i]+"\n"+sub[i]+"\n"+date[i]+"  "+time[i];


                               Toast.makeText(tras.this,"TRASH "+n+"messages",Toast.LENGTH_LONG).show();

                            }


                             ArrayAdapter<String> ada;
                            ada = new ArrayAdapter<String>(tras.this,android.R.layout.simple_list_item_1,messages);
                            lv.setAdapter(ada);
                          lv.setOnItemClickListener(tras.this);
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
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        TextView txt = (TextView) view;
        int i = 0;
        for (i = 0; i < n; i++) {
            if (txt.getText().equals(messages[i])) {
                Intent m = new Intent(this, messages3.class);
                m.putExtra("message",recmessages[i]);
                m.putExtra("from",rec[i]);
                m.putExtra("subject",sub[i]);
                startActivity(m);

            }
        }
    }
    }

