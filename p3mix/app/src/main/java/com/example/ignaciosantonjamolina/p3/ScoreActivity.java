package com.example.ignaciosantonjamolina.p3;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.database.sqlite.SQLiteException;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ProgressBar;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.sql.SQLDataException;
import java.util.List;

/**
 * Created by ignaciosantonjamolina on 28/2/17.
 */

public class ScoreActivity  extends AppCompatActivity {

    ProgressBar progressBar = null;
    boolean newScore = true;
    int borralinea = 0;
    String borrado = "";
    int elementobd = 0;
    List<Score> list;
    // Holds reference to the asynchronous task that gets scores from the web service
    GetScoresAsyncTask task;

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

/*        final TextView a = (TextView)findViewById(R.id.a);
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
        f.setText(scoreNombre3);*/

//        final TextView aa = (TextView)findViewById(R.id.aa);
//        String nombreScore11 = a.getText().toString();
//        nombreScore1 = "Nacho San";
//        a.setText(nombreScore1);
//
//        final TextView bb = (TextView)findViewById(R.id.bb);
//        String scoreNombre11 = b.getText().toString();
//        scoreNombre1 = "3000";
//        b.setText(scoreNombre1);
//
//        final TextView cc = (TextView)findViewById(R.id.cc);
//        String nombreScore22 = c.getText().toString();
//        nombreScore2 = "Ignatius";
//        c.setText(nombreScore2);
//
//        final TextView dd = (TextView)findViewById(R.id.dd);
//        String scoreNombre22 = d.getText().toString();
//        scoreNombre2 = "6000";
//        d.setText(scoreNombre2);
//
//        final TextView ee = (TextView)findViewById(R.id.ee);
//        String nombreScore33 = e.getText().toString();
//        nombreScore3 = "Nacho Xan";
//        e.setText(nombreScore3);
//
//        final TextView ff = (TextView)findViewById(R.id.ff);
//        String scoreNombre33 = f.getText().toString();
//        scoreNombre3 = "9000";
//        f.setText(scoreNombre3);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);

    }

    public boolean onCreateOptionsMenu(Menu menu) {
        // Generate the Menu object from the XML resource file
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.score_menu, menu);
        importBD();
        fillTV();
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.borra:
                if(borralinea == 0) {
                    invalidateOptionsMenu();
                    final TextView f = (TextView) findViewById(R.id.f);
                    final TextView e = (TextView) findViewById(R.id.e);
                    f.setText(borrado); e.setText(borrado);
                    borralinea++;
                    return true;
                } else if (borralinea == 1){
                    final TextView c = (TextView) findViewById(R.id.c);
                    final TextView d = (TextView) findViewById(R.id.d);
                    c.setText(borrado); d.setText(borrado);
                    borralinea++;
                    return true;
                } else if (borralinea == 2) {
                    final TextView b = (TextView) findViewById(R.id.b);
                    final TextView a = (TextView) findViewById(R.id.a);
                    b.setText(borrado);
                    a.setText(borrado);
                    borralinea++;
                    return true;
                } else {
                    Toast.makeText(this, "No hay más puntuaciones", Toast.LENGTH_SHORT).show();
                }


            case R.id.refreshbd:

                try {
                    list= PuntuacionesSQLiteHelper.getInstance(this).getScores();
                } catch (SQLiteException e) {
                    e.printStackTrace();
                }

                final TextView a = (TextView)findViewById(R.id.a);
                final TextView b = (TextView)findViewById(R.id.b);
                Score s = list.get(elementobd);
                //a.setText(s.getName());
                a.setText("Desde ScoreActivity");
                b.setText(s.getScore()+"");

                elementobd++;
                return true;





            case R.id.refresh:
                // Check if the connection is available
                if(isConnectionAvailable()){

                    // Display the ProgressBar to let the user know that something is in progress
                    progressBar.setVisibility(ProgressBar.VISIBLE);

                    // Create a new connectiong to the WS
                    task =new GetScoresAsyncTask();
                    task.setParent(this);
                    // Start the task
                    task.execute();

                    // Set to false the flag that controls whether to display the action
                    // for getting another quotation until the asynchronous task finishes
                    newScore = false;
                    // Ask the system to rebuild the options of the ActionBar
                    supportInvalidateOptionsMenu();
                }
                // There is no Internet connection available, so inform the user about that
                else {
                    Toast.makeText(this, R.string.connection_not_available, Toast.LENGTH_SHORT).show();
                }

                // The action was successfully resolved
                return true;

            case R.id.bAddFriend:



            default:
                return super.onOptionsItemSelected(item);
        }
    }

    // Méthod for asynchronous tasks to notify that a new score has been obtained
    public void gotScore(String name, int score){
        // Update information displayed on the screen: text and author
        ((TextView) findViewById(R.id.aa)).setText(name);
        ((TextView) findViewById(R.id.bb)).setText(score);
        Log.i("CLASE SCORE", name);
        Log.i("CLASE SCORE", score+"");
        // Change to true those flags that control whether to display an action for
        // getting a new quotation and adding the current one to the favourites list
        newScore = true;
        //addQuotation = true;
        // Ask the system to rebuild the options of the ActionBar
        supportInvalidateOptionsMenu();

        // Hide the ProgressBar to let the user know that the operation has finished
        progressBar.setVisibility(ProgressBar.INVISIBLE);

    }
    // Méthod for asynchronous tasks to notify was not possible to get the requested scores
    public void resetScore(){

        // Display a popup notifying the problem
        Toast.makeText(this, "There was a problem to retrieve the score", Toast.LENGTH_SHORT).show();
        progressBar.setVisibility(ProgressBar.INVISIBLE);


    }
    // Method executed whenever the activity is going to be destroyed
   // public void onDestroy(){}

    /*
        Check whether Internet connectivity is available
    */
    private boolean isConnectionAvailable() {

        // Get a reference to the ConnectivityManager
        ConnectivityManager manager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        // Get information for the current default data network
        NetworkInfo info = manager.getActiveNetworkInfo();
        // Return true if there is network connectivity
        return ((info != null) && info.isConnected());
    }

    public void importBD(){

        try {
            list= PuntuacionesSQLiteHelper.getInstance(this).getScores();
        } catch (SQLiteException e) {
            e.printStackTrace();
        }
    }

    public void fillTV(){

        final TextView a = (TextView)findViewById(R.id.a);
        final TextView b = (TextView)findViewById(R.id.b);
        Score s = list.get(elementobd);
        a.setText(s.getName());
        b.setText(s.getScore()+"");
        elementobd++;
        final TextView c = (TextView)findViewById(R.id.c);
        final TextView d = (TextView)findViewById(R.id.d);
        s = list.get(elementobd);
        c.setText(s.getName());
        d.setText(s.getScore()+"");
        elementobd++;
        final TextView e = (TextView)findViewById(R.id.e);
        final TextView f = (TextView)findViewById(R.id.f);
        s = list.get(elementobd);
        e.setText(s.getName());
        f.setText(s.getScore()+"");
    }
}