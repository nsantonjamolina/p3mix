package com.example.ignaciosantonjamolina.p3;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TabHost;
import android.widget.TextView;

/**
 * Created by ignaciosantonjamolina on 28/2/17.
 */

public class ScoreActivity  extends AppCompatActivity {

    public boolean onCreateOptionsMenu(Menu menu) {
        // Generate the Menu object from the XML resource file
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.score_menu, menu);
        //getMenuInflater().inflate(R.menu.example_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.borra:
                invalidateOptionsMenu();
                // Chapuza momentánea para borrar un dato
                // Chapuza momentánea para borrar un dato
                // Chapuza momentánea para borrar un dato
                final TextView f = (TextView)findViewById(R.id.f);
                String scoreNombre3 = f.getText().toString();
                scoreNombre3 = "";
                f.setText(scoreNombre3);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);

    // Class for accessing an application's resources.
    Resources res = getResources();

    TabHost tabs=(TabHost)findViewById(android.R.id.tabhost);
    tabs.setup();

    TabHost.TabSpec spec=tabs.newTabSpec("mitab1");
    spec.setContent(R.id.tab1);
    spec.setIndicator("Local");
            //res.getDrawable(android.R.drawable.ic_btn_speak_now, ));

        tabs.addTab(spec);

    spec=tabs.newTabSpec("mitab2");
    spec.setContent(R.id.tab2);
    spec.setIndicator("Friends");
            //res.getDrawable(android.R.drawable.ic_dialog_map, ));
    tabs.addTab(spec);

    tabs.setCurrentTab(0);

    final TextView a = (TextView)findViewById(R.id.a);
    String nombreScore1 = a.getText().toString();
        nombreScore1 = "Nacho San";
        a.setText(nombreScore1);

        final TextView b = (TextView)findViewById(R.id.b);
        String scoreNombre1 = b.getText().toString();
        scoreNombre1 = "3000";
        b.setText(scoreNombre1);

        final TextView c = (TextView)findViewById(R.id.c);
        String nombreScore2 = c.getText().toString();
        nombreScore2 = "Ignatius";
        c.setText(nombreScore2);

        final TextView d = (TextView)findViewById(R.id.d);
        String scoreNombre2 = d.getText().toString();
        scoreNombre2 = "6000";
        d.setText(scoreNombre2);

        final TextView e = (TextView)findViewById(R.id.e);
        String nombreScore3 = e.getText().toString();
        nombreScore3 = "Nacho Xan";
        e.setText(nombreScore3);

        final TextView f = (TextView)findViewById(R.id.f);
        String scoreNombre3 = f.getText().toString();
        scoreNombre3 = "9000";
        f.setText(scoreNombre3);

}
}