package com.example.mailapplication;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Collections;
import java.util.HashMap;

public class sent extends AppCompatActivity implements AdapterView.OnItemClickListener{
    ListView lv;
    int n;
    EditText editText;
    String[] messages;
    String[] rec;
    String[] sub;
    String[] recmessages;
    String split[]={};

    String s=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sent);
        lv=(ListView)findViewById(R.id.lv1);
        editText=(EditText)findViewById(R.id.editText13);
        getData();
        final RequestQueue queue = Volley.newRequestQueue(this);
        final String url = "https://p-mail.herokuapp.com/sent"; // your URL

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
                            n=split.length/9;
                            int i,j;
                            messages=new String[n];
                            recmessages=new String[n];
                            rec=new String[n];
                            String[] date=new String[n];
                            sub=new String[n];
                            String[] time=new String[n];
                            for(i=0,j=1;i<n;i++,j+=9){
                                rec[i]=split[j];
                                date[i]=split[j+6];
                                sub[i]=split[j+1];
                                time[i]=split[j+7];
                                recmessages[i]=split[j+2];
                                messages[i]=rec[i]+"\n"+sub[i]+"\n"+date[i]+"  "+time[i];

                                Toast.makeText(sent.this,"Sent"+"-"+n+"messages",Toast.LENGTH_LONG).show();
                            }
                            SharedPreferences sp1 = getSharedPreferences
                                    ("count2", Context.MODE_PRIVATE);
                            SharedPreferences.Editor edit11 = sp1.edit();
                            edit11.putInt("sent",n );
                            edit11.commit();


                            final ArrayAdapter<String> ada;
                            ada = new ArrayAdapter<String>(sent.this,android.R.layout.simple_list_item_1,messages);
                            lv.setAdapter(ada);
                            editText.addTextChangedListener(new TextWatcher() {

                                @Override
                                public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
                                    ada.getFilter().filter("\"reciever\""+":"+"\""+cs);
                                }

                                @Override
                                public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
                                   // Toast.makeText(getApplicationContext(), "before text change", Toast.LENGTH_LONG).show();
                                }

                                @Override
                                public void afterTextChanged(Editable arg0) {
                                    //Toast.makeText(getApplicationContext(), "after text change", Toast.LENGTH_LONG).show();
                                }
                            });
                            lv.setOnItemClickListener(sent.this);
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

        //Toast.makeText(this," "+n+"  ",Toast.LENGTH_LONG).show();
        String[] leng=new String[n];



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
                Intent x = new Intent(this, messages1.class);
                x.putExtra("message",recmessages[i]);
                x.putExtra("from",rec[i]);
                x.putExtra("subject",sub[i]);
                startActivity(x);

            }
        }
    }


}
