package com.example.ignaciosantonjamolina.p3;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.EditText;
import android.widget.Spinner;


/**
 * Created by ignaciosantonjamolina on 28/2/17.
 */

public class SettingsActivity  extends AppCompatActivity {


    SharedPreferences prefs;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        prefs = this.getPreferences(Context.MODE_PRIVATE);
    }

    @Override
    protected void onPause() {

        EditText etName = (EditText)findViewById(R.id.etName);
        Spinner spinnerset = (Spinner) findViewById(R.id.spinnerset);
        int myNum = 0;

        //SharedPreferences prefs = this.getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("nombre", etName.getText().toString());
        try {
            myNum = Integer.parseInt(spinnerset.getSelectedItem().toString());
        } catch(NumberFormatException nfe) {
            System.out.println("Could not parse " + nfe);
        }
        editor.putInt("hello", myNum);
        Log.i("Qué hay escrito xml", prefs.getString("nombre", "OTRO"));
        editor.apply();
        editor.commit();
        super.onPause();

    }

    @Override
    protected void onResume() {
        EditText etName = (EditText)findViewById(R.id.etName);
        Spinner spinnerset = (Spinner) findViewById(R.id.spinnerset);
        //if(prefs == null) prefs = PreferenceManager.getDefaultSharedPreferences(this);

        etName.setText(prefs.getString("nombre", ""));
        spinnerset.setSelection(prefs.getInt("hello", 0));
        Log.i("HAY ESCRITO: ", (prefs.getInt("hello", 0)+""));
        super.onResume();
    }
}