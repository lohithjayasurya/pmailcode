package com.example.mailapplication;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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

public class seen extends AppCompatActivity implements AdapterView.OnItemClickListener {
    ListView lv;
    int n;
    String[] messages;
    String[] newmessages;
    String[] recmessages;
    String[] rec;
    String[] sub;
    String[] newrecmessages;
    String[] newrec;
    String[] newsub;
    String split[]={};
    String[] star;
    String[] x;
    String s=null;
    int k;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seen);
        lv=(ListView)findViewById(R.id.listview);
        getData();
        final RequestQueue queue = Volley.newRequestQueue(this);
        final String url = "https://p-mail.herokuapp.com/inbox"; // your URL

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
                            String m = response.getString("views");
                            split = m.split(",");
                            n=split.length/8;
                            int i,j;
                            recmessages=new String[n];
                            newrecmessages=new String[n];
                            messages=new String[n];

                            rec=new String[n];
                            newrec=new String[n];
                            String[] date=new String[n];
                            star=new String[n];
                            sub=new String[n];
                            newsub=new String[n];
                            String[] time=new String[n];
                            for(i=0,j=1,k=0;i<n;i++,j+=8){
                                rec[i]=split[j];
                                date[i]=split[j+5];
                                sub[i]=split[j+1];
                                time[i]=split[j+6];
                                recmessages[i]=split[j+2];
                                star[i]=split[j+3];
                                x=star[i].split(":");
                                String p1=x[1].toString();
                                Log.d(x[1]+":   ", "onResponse: ");
                                if(p1.matches("true")) {
                                    //Toast.makeText(getApplicationContext(), "1", Toast.LENGTH_SHORT).show();


                                    messages[i] = rec[i] + "\n" + sub[i] + "\n" + date[i] + "  " + time[i];
                                    // Toast.makeText(getApplicationContext(), "2", Toast.LENGTH_SHORT).show();


                                    k = k + 1;
                                    Log.d(":   ", "onResponse: "+k);
                                }

                            }
                            final ArrayAdapter<String> ada;
                            newmessages=new String[k];
                            for(i=0,k=0;i<n;i++){
                                x=star[i].split(":");
                                String p1=x[1].toString();
                                //Toast.makeText(getApplicationContext(),messages.length+","+n,Toast.LENGTH_SHORT).show();
                                Log.d("hey", "onResponse: "+messages.length+","+n+","+i+",");

                                if(p1.matches("true")){
                                    //Toast.makeText(getApplicationContext(),"Inin",Toast.LENGTH_SHORT).show();
                                    newmessages[k]=messages[i];
                                    newrec[k]=rec[i];
                                    newsub[k]=sub[i];
                                    newrecmessages[k]=recmessages[i];
                                    Log.d("hey", "onResponse: "+newmessages[k]+","+k);
                                    k=k+1;
                                }
                            }
                            //Toast.makeText(starred.this,"-"+newmessages[1],Toast.LENGTH_LONG).show();
                            ada = new ArrayAdapter<String>(seen.this,android.R.layout.simple_list_item_1,newmessages);
                            lv.setAdapter(ada);
                            lv.setOnItemClickListener(seen.this);
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
        for (i = 0; i < k; i++) {
            if (txt.getText().equals(newmessages[i])) {
                Intent x = new Intent(this, messages1.class);
                x.putExtra("message",newrecmessages[i]);
                x.putExtra("from",newrec[i]);
                x.putExtra("subject",newsub[i]);
                startActivity(x);

            }
        }
    }





}
