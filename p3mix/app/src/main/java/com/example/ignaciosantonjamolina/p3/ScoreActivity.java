package com.example.ignaciosantonjamolina.p3;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
    SharedPreferences prefs;


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);

        prefs = this.getPreferences(Context.MODE_WORLD_READABLE);
        fillTV();

        // Class for accessing an application's resources.
        Resources res = getResources();

        TabHost tabs=(TabHost)findViewById(android.R.id.tabhost);
        tabs.setup();

        TabHost.TabSpec spec=tabs.newTabSpec("mitab1");
        spec.setContent(R.id.tab1);
        spec.setIndicator("Local");

        tabs.addTab(spec);

        spec=tabs.newTabSpec("mitab2");
        spec.setContent(R.id.tab2);
        spec.setIndicator("Friends");
        tabs.addTab(spec);

        tabs.setCurrentTab(0);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);

    }

    public boolean onCreateOptionsMenu(Menu menu) {
        // Generate the Menu object from the XML resource file
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.score_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.borra:

                PuntuacionesSQLiteHelper.getInstance(this).clearAllScores();
                list.clear();
                return true;

            case R.id.refreshbd:

                elementobd=0;
                fillTV();
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

    public void fillTV(){

        try {
            list= PuntuacionesSQLiteHelper.getInstance(this).getScores();
        } catch (SQLiteException e) {
            e.printStackTrace();
        }
        if(!list.isEmpty()){
            if(elementobd==0 && elementobd<list.size()){
                final TextView a = (TextView)findViewById(R.id.a);
                final TextView b = (TextView)findViewById(R.id.b);
                Score s = list.get(elementobd);
                a.setText(s.getName());
                b.setText(s.getScore()+"");
                elementobd++;
            }if(elementobd == 1 && elementobd<list.size()){
                final TextView c = (TextView)findViewById(R.id.c);
                final TextView d = (TextView)findViewById(R.id.d);
                Score s = list.get(elementobd);
                c.setText(s.getName());
                d.setText(s.getScore()+"");
                elementobd++;
            }if(elementobd == 2 && elementobd<list.size()){
                final TextView e = (TextView)findViewById(R.id.e);
                final TextView f = (TextView)findViewById(R.id.f);
                Score s = list.get(elementobd);
                e.setText(s.getName());
                f.setText(s.getScore()+"");
                elementobd++;
            }if(elementobd==3 && elementobd<list.size()){
                final TextView g = (TextView)findViewById(R.id.g);
                final TextView h = (TextView)findViewById(R.id.h);
                Score s = list.get(elementobd);
                g.setText(s.getName());
                h.setText(s.getScore()+"");
                elementobd++;
            }if(elementobd == 4 && elementobd<list.size()){
                final TextView i = (TextView)findViewById(R.id.i);
                final TextView j = (TextView)findViewById(R.id.j);
                Score s = list.get(elementobd);
                i.setText(s.getName());
                j.setText(s.getScore()+"");
                elementobd++;
            }if(elementobd == 5 && elementobd<list.size()){
                final TextView k = (TextView)findViewById(R.id.k);
                final TextView l = (TextView)findViewById(R.id.l);
                Score s = list.get(elementobd);
                k.setText(s.getName());
                l.setText(s.getScore()+"");
                elementobd++;
            }if(elementobd==6 && elementobd<list.size()){
                final TextView m = (TextView)findViewById(R.id.m);
                final TextView n = (TextView)findViewById(R.id.n);
                Score s = list.get(elementobd);
                m.setText(s.getName());
                n.setText(s.getScore()+"");
                elementobd++;
            }if(elementobd == 7 && elementobd<list.size()){
                final TextView o = (TextView)findViewById(R.id.o);
                final TextView p = (TextView)findViewById(R.id.p);
                Score s = list.get(elementobd);
                o.setText(s.getName());
                p.setText(s.getScore()+"");
                elementobd++;
            }if(elementobd == 8 && elementobd<list.size()){
                final TextView q = (TextView)findViewById(R.id.q);
                final TextView r = (TextView)findViewById(R.id.r);
                Score s = list.get(elementobd);
                q.setText(s.getName());
                r.setText(s.getScore()+"");
                elementobd++;
            }if(elementobd == 9 && elementobd<list.size()){
                final TextView ss = (TextView)findViewById(R.id.s);
                final TextView t = (TextView)findViewById(R.id.t);
                Score s = list.get(elementobd);
                ss.setText(s.getName());
                t.setText(s.getScore()+"");
                elementobd++;
            }if(elementobd == 10 && elementobd<list.size()){
                final TextView u = (TextView)findViewById(R.id.u);
                final TextView v = (TextView)findViewById(R.id.v);
                Score s = list.get(elementobd);
                u.setText(s.getName());
                v.setText(s.getScore()+"");
                elementobd++;
            }if(elementobd == 11 && elementobd<list.size()){
                final TextView w = (TextView)findViewById(R.id.w);
                final TextView x = (TextView)findViewById(R.id.x);
                Score s = list.get(elementobd);
                w.setText(s.getName());
                x.setText(s.getScore()+"");
                elementobd++;
            }if(elementobd == 12 && elementobd<list.size()){
                final TextView y = (TextView)findViewById(R.id.y);
                final TextView z = (TextView)findViewById(R.id.z);
                Score s = list.get(elementobd);
                y.setText(s.getName());
                z.setText(s.getScore()+"");
                elementobd++;
            }
        }}
}