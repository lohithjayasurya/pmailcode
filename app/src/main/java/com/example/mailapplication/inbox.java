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
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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

public class inbox extends AppCompatActivity implements AdapterView.OnItemClickListener{
    ListView lv;
    EditText editText;
    int n;
    String[] messages;
    String[] recmessages;
    String[] rec;
    String[] sub;
    String split[]={};


String s=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inbox);
        lv=(ListView)findViewById(R.id.lv1);
        editText = (EditText) findViewById(R.id.editText);
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
                            messages=new String[n];
                            recmessages=new String[n];
                             rec=new String[n];
                            String[] date=new String[n];
                             sub=new String[n];
                            String[] time=new String[n];
                            for(i=0,j=1;i<n;i++,j+=8){
                                rec[i]=split[j];
                                date[i]=split[j+5];
                                sub[i]=split[j+1];
                                time[i]=split[j+6];
                                recmessages[i]=split[j+2];
                                messages[i]=rec[i]+"\n"+sub[i]+"\n"+date[i]+"  "+time[i];


                                Toast.makeText(inbox.this,"INBOX"+"-"+n+"messages",Toast.LENGTH_LONG).show();

                            }
                            SharedPreferences sp = getSharedPreferences
                                    ("count1", Context.MODE_PRIVATE);
                            SharedPreferences.Editor edit = sp.edit();
                            edit.putInt("inbox",n );
                            edit.commit();

                            final ArrayAdapter<String> ada;
                            ada = new ArrayAdapter<String>(inbox.this,android.R.layout.simple_list_item_1,messages);
                            lv.setAdapter(ada);
                            editText.addTextChangedListener(new TextWatcher() {

                                @Override
                                public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
                                    ada.getFilter().filter("\"recieved_mail\""+":"+"\""+cs);
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
                            lv.setOnItemClickListener(inbox.this);
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
                String[] y;
                String[] x;
                String[] z;
                x = rec[i].split(":");
                y = sub[i].split(":");
                z = recmessages[i].split(":");
                x[1]=x[1].substring(1,x[1].length()-1);
                y[1]=y[1].substring(1,y[1].length()-1);
                z[1]=z[1].substring(1,z[1].length()-1);
                getData();

                final RequestQueue queue = Volley.newRequestQueue(this);
                final String url = "https://p-mail.herokuapp.com/seen"; // your URL

                queue.start();
                HashMap<String, String> params = new HashMap<String, String>();
                params.put("token", s); // the entered data as the body.
                params.put("message",z[1]);
                params.put("subject",y[1]);
                params.put("person",x[1]);

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
                                    Toast.makeText(inbox.this, "seen", Toast.LENGTH_SHORT).show();
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                       // e1.setText(error.getMessage());
                    }
                });
                queue.add(jsObjRequest);

                Intent m = new Intent(this, messages.class);
                m.putExtra("message",recmessages[i]);
                m.putExtra("from",rec[i]);
                m.putExtra("subject",sub[i]);
                startActivity(m);

            }
        }
    }


public boolean onCreateOptionsMenu(Menu menu){
    MenuInflater inflater=getMenuInflater();
    inflater.inflate(R.menu.top_menu,menu);
      return true;
}

@RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
public boolean onOptionsItemSelected(MenuItem item){
        if(item.getItemId()==R.id.starred){
            Intent i =new Intent(this,starred.class);
            startActivity(i);

        }
   else
       if(item.getItemId()==R.id.seen){
           Intent i =new Intent(this,seen.class);
           startActivity(i);


       }
    else if(item.getItemId()==R.id.unseen){
           Intent i =new Intent(this,unseen.class);
           startActivity(i);

       }
    else if(item.getItemId()==R.id.composemessage){
           Intent i =new Intent(this,composemail.class);
           i.putExtra("message","");
           startActivity(i);

       }
    else if(item.getItemId()==R.id.sent){
           Intent i =new Intent(this,sent.class);
           startActivity(i);

    }
    else if(item.getItemId()==R.id.trash){

    }
    else if(item.getItemId()==R.id.report){
           Intent i =new Intent(this,report.class);
           startActivity(i);

       }
    else if(item.getItemId()==R.id.updateprofile){
           Intent i =new Intent(this,update_profile.class);
           startActivity(i);

       }
    else if(item.getItemId()==R.id.logout){
           Intent i =new Intent(this,MainActivity.class);
           startActivity(i);
           finishAffinity();

       }
    else if(item.getItemId()==R.id.deleteaccount){
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
                              Toast.makeText(inbox.this,m,Toast.LENGTH_LONG).show();

                               Intent i =new Intent(inbox.this,MainActivity.class);
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

        return false;
}
}
