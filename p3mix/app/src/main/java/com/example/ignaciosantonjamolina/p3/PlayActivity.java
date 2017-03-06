package com.example.ignaciosantonjamolina.p3;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by ignaciosantonjamolina on 28/2/17.
 */

public class PlayActivity extends AppCompatActivity {

    static int qn;
    String prize[] = {"0", "100", "200", "300", "500",
            "1000", "2000", "4000", "8000", "16000", "32000", "64000",
            "125000", "250000", "500000", "1000000"};
    Question q = null;
    List<Question> questionList;

    SharedPreferences prefs;
    SharedPreferences sharedPreferences;

    Button buttonAnswer1;
    Button buttonAnswer2;
    Button buttonAnswer3;
    Button buttonAnswer4;
    Button buttonPlay;
    Button buttonQuit;
    TextView euros;
    TextView numberQ;
    TextView quesionText;
    PutScoreAsyncTask task;

    String amount;  // dinero conseguido

    public boolean onCreateOptionsMenu(Menu menu) {
        // Generate the Menu object from the XML resource file
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.play_menu, menu);
        //getMenuInflater().inflate(R.menu.example_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.fifty:
                Log.i("ActionBar", "fifty");
                switch (Integer.parseInt(q.getFifty1())) {
                    case 1:
                        buttonAnswer1.setVisibility(View.INVISIBLE);
                        buttonAnswer1.setBackgroundColor(0xFFFFFFFF); // blanco
                        break;
                    case 2:
                        buttonAnswer2.setVisibility(View.INVISIBLE);
                        buttonAnswer1.setBackgroundColor(0xFFFFFFFF);
                        break;
                    case 3:
                        buttonAnswer3.setVisibility(View.INVISIBLE);
                        buttonAnswer1.setBackgroundColor(0xFFFFFFFF);
                        break;
                    case 4:
                        buttonAnswer4.setVisibility(View.INVISIBLE);
                        buttonAnswer1.setBackgroundColor(0xFFFFFFFF);
                        break;
                }
                switch (Integer.parseInt(q.getFifty2())) {
                    case 1:
                        buttonAnswer1.setVisibility(View.INVISIBLE);
                        buttonAnswer1.setBackgroundColor(0xFFFFFFFF);
                        break;
                    case 2:
                        buttonAnswer2.setVisibility(View.INVISIBLE);
                        buttonAnswer2.setBackgroundColor(0xFFFFFFFF);
                        break;
                    case 3:
                        buttonAnswer3.setVisibility(View.INVISIBLE);
                        buttonAnswer3.setBackgroundColor(0xFFFFFFFF);
                        break;
                    case 4:
                        buttonAnswer4.setVisibility(View.INVISIBLE);
                        buttonAnswer4.setBackgroundColor(0xFFFFFFFF);
                        break;
                }
                break;

            case R.id.audience:
                changeButtonColor(Integer.parseInt(q.getPhone()),0xFFFFFF00); //yellow
                break;

            case R.id.call:
                changeButtonColor(Integer.parseInt(q.getPhone()),0xFFFF9900); //orange
                break;
            //return super.onOptionsItemSelected(item);

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //prefs = this.getPreferences(Context.MODE_WORLD_READABLE);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        /*
        This method is executed when the activity is created to populate the ActionBar with actions
     */


        // Set the user interface layout for this Activity
        // The layout file is defined in the project res/layout/activity_play.xml file
        setContentView(R.layout.activity_play);

        buttonAnswer1 = (Button) findViewById(R.id.answer1);
        buttonAnswer2 = (Button) findViewById(R.id.answer2);
        buttonAnswer3 = (Button) findViewById(R.id.answer3);
        buttonAnswer4 = (Button) findViewById(R.id.answer4);
        buttonPlay = (Button) findViewById(R.id.play);
        buttonQuit = (Button) findViewById(R.id.quit);

        euros = (TextView) findViewById(R.id.euros);
        numberQ = (TextView) findViewById(R.id.numberQ);
        quesionText = (TextView) findViewById(R.id.questionText);


        questionList = generateQuestionList();


        for (int i=0; i<questionList.size(); i++)
            Log.d("XXXX", questionList.get(i).getText());

        qn = 1;

        euros.setText(prize[qn]);
        numberQ.setText(String.valueOf(qn));
        q = questionList.get(qn-1);
        quesionText.setText(q.getText());
        buttonAnswer1.setText(q.getAnswer1());
        buttonAnswer2.setText(q.getAnswer2());
        buttonAnswer3.setText(q.getAnswer3());
        buttonAnswer4.setText(q.getAnswer4());

        buttonPlay.setVisibility(View.INVISIBLE);
        buttonQuit.setVisibility(View.INVISIBLE);

    }


    public void clickAnswer1(View view) {
        if (Integer.parseInt(q.getRight()) == 1) acierto(1);
        else fallo();
    }

    public void clickAnswer2(View view) {
        if (Integer.parseInt(q.getRight()) == 2) acierto(2);
        else fallo();
    }

    public void clickAnswer3(View view) {
        if (Integer.parseInt(q.getRight()) == 3) acierto(3);
        else fallo();
    }

    public void clickAnswer4(View view) {
        if (Integer.parseInt(q.getRight()) == 4) acierto(4);
        else fallo();
    }

    public void clickPlay(View view) {
        qn++;
        euros.setText(prize[qn]);
        numberQ.setText(Integer.toString(qn));
        q = questionList.get(qn -1);
        quesionText.setText(q.getText());
        resetButtons();
        buttonAnswer1.setText(q.getAnswer1());
        buttonAnswer2.setText(q.getAnswer2());
        buttonAnswer3.setText(q.getAnswer3());
        buttonAnswer4.setText(q.getAnswer4());
        buttonPlay.setVisibility(View.INVISIBLE);
        buttonQuit.setVisibility(View.INVISIBLE);

    }

    public void clickQuit(View view) {

        amount = prize[qn];
        addScore(qn);
        Score sc =new Score(sharedPreferences.getString("nombre","juan"),Integer.parseInt(amount));
        task = new PutScoreAsyncTask();
        //task.setParent(this);
        task.execute();
        finish();
    }

    void acierto(int button) {

        buttonPlay.setVisibility(View.VISIBLE);
        buttonQuit.setVisibility(View.VISIBLE);

        changeButtonColor(button, Color.GREEN);
        if (qn == prize.length - 1) {
            amount = "1000000";
            addScore(qn);
            Score sc =new Score(sharedPreferences.getString("nombre","juan"),Integer.parseInt(amount));
            task = new PutScoreAsyncTask();
            //task.setParent(this);
            task.execute();
            finish();
        }
    }

    void fallo() {
        amount = "0";
        if (qn >= 6) amount = "1000";
        if (qn >= 11) amount = "32000";
        addScore(qn);
        Score sc =new Score(sharedPreferences.getString("nombre","juan"),Integer.parseInt(amount));
        task = new PutScoreAsyncTask();
        //task.setParent(this);
        task.execute();
        finish();
    }

    private void changeButtonColor(int button, int color){
        switch (button) {
            case 1:
                buttonAnswer1.setBackgroundColor(color);
                break;
            case 2:
                buttonAnswer2.setBackgroundColor(color);
                break;
            case 3:
                buttonAnswer3.setBackgroundColor(color);
                break;
            case 4:
                buttonAnswer4.setBackgroundColor(color);
                break;
        }
    }

    private void resetButtons() {

        buttonAnswer1.setBackgroundColor(0xFFDDDDDD);
        buttonAnswer2.setBackgroundColor(0xFFDDDDDD);
        buttonAnswer3.setBackgroundColor(0xFFDDDDDD);
        buttonAnswer4.setBackgroundColor(0xFFDDDDDD);

        buttonAnswer1.setVisibility(View.VISIBLE);
        buttonAnswer2.setVisibility(View.VISIBLE);
        buttonAnswer3.setVisibility(View.VISIBLE);
        buttonAnswer4.setVisibility(View.VISIBLE);
    }



    public List<Question> generateQuestionList() {

        List<Question> list = new ArrayList<Question>();


        try {
            InputStream fis = getResources().openRawResource(R.raw.questions);
            BufferedReader reader = new BufferedReader(new InputStreamReader(fis));
            XmlPullParser parser = XmlPullParserFactory.newInstance().newPullParser();
            parser.setInput(reader);
            int eventType = parser.getEventType();
            EditText target = null;
int i=0;
             while (eventType != XmlPullParser.END_DOCUMENT) {
                Question qu = new Question();
                if (eventType== XmlPullParser.START_TAG)
                        if ("question".equals(parser.getName())) {
                           qu.answer1 =    parser.getAttributeValue(null, "answer1");
                           qu.answer2 =    parser.getAttributeValue(null, "answer2");
                           qu.answer3 =    parser.getAttributeValue(null, "answer3");
                           qu.answer4 =    parser.getAttributeValue(null, "answer4");
                           qu.audience=    parser.getAttributeValue(null, "audience");
                           qu.fifty1  =    parser.getAttributeValue(null, "fifty1");
                           qu.fifty2  =    parser.getAttributeValue(null, "fifty2");
                           qu.number  =    parser.getAttributeValue(null, "number");
                           qu.phone   =    parser.getAttributeValue(null, "phone");
                           qu.right   =    parser.getAttributeValue(null, "right");
                           qu.text    =    parser.getAttributeValue(null, "text");
                           list.add(qu);
                }
                 parser.next();
                 eventType = parser.getEventType();
            }
            reader.close();
        }
        catch(XmlPullParserException e){
            e.printStackTrace();
        }
        catch(FileNotFoundException e){
            e.printStackTrace();
        }
        catch(IOException e){
            e.printStackTrace();
        }


        return list;

    }


    public void addScore(int qn){
        //SettingsActivity.et
        //EditText etName = (EditText)findViewById(R.id.etName);
       // String name = etName.toString();

        int score = Integer.parseInt(prize[qn]);
        //String name = prefs.getString("nombre", "Alias");
        //String name = prefs.getString("nombre","Nacho");
        String name = sharedPreferences.getString("nombre", "Alias");
        Log.i("NOMBRE", name);
        try {
            PuntuacionesSQLiteHelper.getInstance(this).addScore(name, score);
        } catch(SQLiteException e){
            e.printStackTrace();
        }

    }

/*
private void readQuestionXML() {
    try {
        FileInputStream fis = openFileInput("Question.xml");
        InputStreamReader reader = new InputStreamReader(fis);
        Log.d("XXXXXXXXXXXXXXXXXX", "READQUESTION");
        XmlPullParser parser = XmlPullParserFactory.newInstance().newPullParser();
        parser.setInput(reader);
        int eventType = parser.getEventType();
        EditText target = null;
   //     while (eventType != XmlPullParser.END_DOCUMENT) {
            switch (eventType) {
                case XmlPullParser.START_TAG:
                    if ("answer1".equals(parser.getName())) {
                        Log.d("xml", String.format("answer1", parser.getAttributeValue(null, "Answer1")));
                    }
                    break;
                case XmlPullParser.TEXT:
                    Log.d("text", parser.getText());
                    break;
            }
            parser.next();
            eventType = parser.getEventType();
     //   }
        reader.close();
    } catch (XmlPullParserException e) {
        e.printStackTrace();
    } catch (FileNotFoundException e) {
        e.printStackTrace();
    } catch (IOException e) {
        e.printStackTrace();
    }
}
*/


}

