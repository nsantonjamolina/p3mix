package com.example.ignaciosantonjamolina.p3;


import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.util.Log;

import com.example.ignaciosantonjamolina.p3.Score;
import com.example.ignaciosantonjamolina.p3.ScoreActivity;
import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

public class GetScoresAsyncTask extends AsyncTask<String, Void, ScorePojo> {

    // Reference to the activity that launched the asynchronous task
    private ScoreActivity parent = null;

    public void setParent(ScoreActivity parent) {
        this.parent = parent;
    }


    /*
        Access the web service in a background thread
    */
    @Override
    protected ScorePojo doInBackground(String... nom) {


        // Check SharedPreference to get the selected HTTP request method
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this.parent);
        //String httpMethod = sharedPrefs.getString("prefs_http_method", "GET");

        // Build the URI to access the web service at https://wwtbamandroid.appspot.com/rest/friends
        Uri.Builder uriBuilder = new Uri.Builder();
        uriBuilder.scheme("https");
        uriBuilder.authority("wwtbamandroid.appspot.com");
        uriBuilder.appendPath("rest");
        uriBuilder.appendPath("highscores");

        // Parameters should be included in the URI for GET requests
        uriBuilder.appendQueryParameter("name", nom[0]);
        //sharedPrefs.getString("nombre", "Paco"));
        uriBuilder.appendQueryParameter("format", "json");

        ScorePojo result = null;
       // Score resultsc = null;
        try {
            // Creates a new URL from the URI
            URL url = new URL(uriBuilder.build().toString());
            Log.d("XXXXXXXXXXXXXXXXXX", url.toString());
            // Get a connection to the web service
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setDoInput(true);

            // Process the response if it was successful (HTTP_OK = 200)
            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                Log.i("responde connection", connection.getResponseCode()+"");
                // Open an input channel to receive the response from the web service
                InputStreamReader isr = new InputStreamReader(connection.getInputStream());
                BufferedReader br = new BufferedReader(isr);
                String lectura = br.readLine();
                Log.i("LECTURA", lectura);
                // Create a Gson object through a GsonBuilder to process the response
                Gson gson = new Gson();
                try {
                    // Deserializes the JSON response into a Score object
                    result = gson.fromJson(lectura, ScorePojo.class);
                } catch (JsonSyntaxException | JsonIOException e) {
                    e.printStackTrace();
                }
                // Close the input channel
                isr.close();
                return result;
            }

            // Close the connection
            connection.disconnect();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Return the received Score object

        return result;

    }

    /*
        Update the interface of activity that launched the asynchronous task
    */
    @Override
    protected void onPostExecute(ScorePojo param) {
        // Score received
        if (param != null) {
            Scores [] sc1 = param.getScores();
            Score sc = new Score(sc1[0].getName(),Integer.parseInt(sc1[0].getScoring()));
            this.parent.gotScore(sc);
        }
        // No response received
        else {
            this.parent.resetScore();
        }
        super.onPostExecute(param);
    }


}
