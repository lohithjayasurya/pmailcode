package com.example.mailapplication;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
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

public class fragment2 extends Fragment {
    EditText txt1,txt2;
    String[] messages;
    String[] recmessages;
    String[] rec;
    String[] sub;
    String[] messages1;
    String[] recmessages1;
    int size,size1;
    int n;
int count1=0,count2=0;
    String[] date = new String[size1];
    String[] date1 = new String[size];


    String[] rec1;
    String[] sub1;
    String split[] = {}, spl[] = {};
    String s = null;
    EditText e1, e2;// object to hold the text view

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_fragment2, container, false);
    }
  /*  This method we discussed in class. But having the logic to get the activity inside changedata method also works.
  @Override
   public void onActivityCreated( Bundle savedInstanceState) {
       super.onActivityCreated(savedInstanceState);
	// get access to the textview object
       txt=(TextView)getActivity().findViewById(R.id.tv);
   }*/

    // method to accept the message from the activity and update the UI
    public void changedata(String i) {
       // txt1=(EditText)getActivity().findViewById(R.id.sent1);
        //txt2=(EditText)getActivity().findViewById(R.id.received1);
        SharedPreferences p = this.getActivity().getSharedPreferences("dates1",
                Context.MODE_PRIVATE);
         size =  p.getInt("array_size1",0);
        for(int l=0;l<size;l++){
            String[] date1 = new String[size];
            date1[l]=p.getString("date1"+l,null);
        }
        SharedPreferences s = this.getActivity().getSharedPreferences("dates2",
                Context.MODE_PRIVATE);
        size1=  p.getInt("array_size2",0);
        for(int u=0;u<size;u++){
            date[u]=p.getString("date2"+u,null);
        }
// get access to the textview object
        for(int q=0;q<size;q++)
        {
            if(date1[q].equals(i)){

                     count1++;

            }
        }
        txt1.setText(count1);
        for(int y=0;y<size;y++)
        {
            if(date1[y].equals(i)){

                count2++;

            }
        }
        txt2.setText(count2);

    }


}
