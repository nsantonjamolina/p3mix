package com.example.ignaciosantonjamolina.p3;


import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.util.Log;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class PostFriendsAsyncTask extends AsyncTask<String, Void, Void> {

    // Reference to the activity that launched the asynchronous task
    private SettingsActivity parent = null;
    SharedPreferences sharedPreferences;


    public void setParent(SettingsActivity parent) {this.parent = parent;}



    /*
        Access the web service in a background thread
    */
    @Override
    protected Void doInBackground(String... friend){
        setParent(this.parent);

        //String body =  "name=juan&friend_name="+friend[0]+"a&format=json"; ;
        String body =  "name="+friend[1]+"&friend_name="+friend[0];

        // Build the URI to access the web service at https://wwtbamandroid.appspot.com/rest/friends
        Uri.Builder uriBuilder = new Uri.Builder();
        uriBuilder.scheme("https");
        uriBuilder.authority("wwtbamandroid.appspot.com");
        uriBuilder.appendPath("rest");
        uriBuilder.appendPath("friends");

        Score result = null;
        try {
            // Creates a new URL from the URI
            URL url = new URL(uriBuilder.build().toString());

            // Get a connection to the web service
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.setRequestMethod("POST");
            // Open an output channel to send the body for POST requests
            connection.setDoOutput(true);
            OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream());

            writer.write(body);
            Log.d("XXXXXXXXXXXXXXXXXXXXXXX", "envío el body"+body);
            writer.flush();
            writer.close();
            Log.d("XXXXXXXXXXXXXXXXXXXXXXX", String.format("%d",connection.getResponseCode()));

            // Close the connection
            connection.disconnect();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;

    }

    /*
        Update the interface of activity that launched the asynchronous task

    @Override
    protected void onPostExecute(Score param) {
        // Quotation received
        if (param != null) {
            this.parent.gotScore(param.getName(), param.getScore());
        }
        // No response received
        else {
            this.parent.resetScore();
        }
        super.onPostExecute(param);
    }
    */

}
