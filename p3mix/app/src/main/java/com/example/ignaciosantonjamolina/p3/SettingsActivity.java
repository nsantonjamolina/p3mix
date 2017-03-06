package com.example.ignaciosantonjamolina.p3;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;


/**
 * Created by ignaciosantonjamolina on 28/2/17.
 */

public class SettingsActivity  extends AppCompatActivity{


    SharedPreferences prefs;
    Button btnBotonSimple;
    EditText etAddFriend;
    String addFriend;
    PostFriendsAsyncTask task;
    String body = null;

    SharedPreferences sharedPreferences;



    @Override
    protected void onCreate(Bundle savedInstanceState){

        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        //prefs = this.getPreferences(Context.MODE_WORLD_READABLE);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());


        btnBotonSimple = (Button)findViewById(R.id.bAddFriend);
        etAddFriend = (EditText) findViewById(R.id.etAddFriend);

        btnBotonSimple.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                addFriend = etAddFriend.getText().toString();
                Context context = getApplicationContext();
                task = new PostFriendsAsyncTask();
                //task.setParent(this);
                task.execute(addFriend);

            }
        });
    }






    @Override
    protected void onPause() {

        EditText etName = (EditText)findViewById(R.id.etName);
        Spinner spinnerset = (Spinner) findViewById(R.id.spinnerset);
        int myNum = 0;
        //SharedPreferences.Editor editor = prefs.edit();
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("nombre", etName.getText().toString());
        try {
            myNum = Integer.parseInt(spinnerset.getSelectedItem().toString());
        } catch(NumberFormatException nfe) {
            System.out.println("Could not parse " + nfe);
        }
        editor.putInt("hello", myNum);
        editor.apply();
        editor.commit();
        super.onPause();

    }

    @Override
    protected void onResume() {
        EditText etName = (EditText)findViewById(R.id.etName);
        Spinner spinnerset = (Spinner) findViewById(R.id.spinnerset);
        etName.setText(sharedPreferences.getString("nombre", ""));
        spinnerset.setSelection(sharedPreferences.getInt("hello", 0));
        super.onResume();
    }

}