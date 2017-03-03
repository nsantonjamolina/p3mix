package com.example.ignaciosantonjamolina.p3;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    /*
        This method is executed when the activity is created to populate the ActionBar with actions
     */

    public boolean onCreateOptionsMenu(Menu menu) {
        // Generate the Menu object from the XML resource file
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.example_menu, menu);
        //getMenuInflater().inflate(R.menu.example_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.credits:
                Log.i("ActionBar", "Nuevo!");
                Intent intent = new Intent(this, CreditsActivity.class);
                startActivity(intent);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Button button = (Button) findViewById(R.id.bSettings);
        final Button button1 = (Button) findViewById(R.id.bScore);
        final Button button2 = (Button) findViewById(R.id.bPlay);

        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                dashboard(v);

//                // Perform action on click
//                Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
//                startActivity(intent);
            }
        });

        button1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                dashboard(v);

//                // Perform action on click
//                Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
//                startActivity(intent);
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                dashboard(v);

            }
        });

    }

    /*
        Método para ejecutar las diferentes actividades cuando se hace click en algún botón del dashboard
    */
    public void dashboard(View v){

        //Objeto intent para lanzar las actividades relacionadas
        Intent intent = null;
        //Determinar el activity a lanzar de acuerdo al id the botón
       // switch (v.getId()){
        switch(v.getId()){

            case R.id.bPlay:
                Log.i("main","botón play pulsado");
                intent = new Intent(this, PlayActivity.class);
                break;

            case R.id.bScore:
                Log.i("main","botón score pulsado");
                intent = new Intent(this, ScoreActivity.class);

                break;

            case R.id.bSettings:
                Log.i("main","botón settings pulsado");
                intent = new Intent(this, SettingsActivity.class);
                break;
        }

        // Lanzar la actividad indicada
        if(intent != null) startActivity(intent);

    }
}
