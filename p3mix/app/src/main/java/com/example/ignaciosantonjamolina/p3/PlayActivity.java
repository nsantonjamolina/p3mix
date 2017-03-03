package com.example.ignaciosantonjamolina.p3;

import android.content.Intent;
import android.content.res.Resources;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
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
    List<Question> QuestionList;

    Button buttonAnswer1;
    Button buttonAnswer2;
    Button buttonAnswer3;
    Button buttonAnswer4;
    TextView euros;
    TextView numberQ;
    TextView quesionText;
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
                        buttonAnswer1.setClickable(false);
                        buttonAnswer1.setBackgroundColor(0xFFFFFFFF); // blanco
                        break;
                    case 2:
                        buttonAnswer2.setClickable(false);
                        buttonAnswer1.setBackgroundColor(0xFFFFFFFF);
                        break;
                    case 3:
                        buttonAnswer3.setClickable(false);
                        buttonAnswer1.setBackgroundColor(0xFFFFFFFF);
                        break;
                    case 4:
                        buttonAnswer4.setClickable(false);
                        buttonAnswer1.setBackgroundColor(0xFFFFFFFF);
                        break;
                }
                switch (Integer.parseInt(q.getFifty2())) {
                    case 1:
                        buttonAnswer1.setClickable(false);
                        buttonAnswer1.setBackgroundColor(0xFFFFFFFF);
                        break;
                    case 2:
                        buttonAnswer2.setClickable(false);
                        buttonAnswer2.setBackgroundColor(0xFFFFFFFF);
                        break;
                    case 3:
                        buttonAnswer3.setClickable(false);
                        buttonAnswer3.setBackgroundColor(0xFFFFFFFF);
                        break;
                    case 4:
                        buttonAnswer4.setClickable(false);
                        buttonAnswer4.setBackgroundColor(0xFFFFFFFF);
                        break;
                }
                break;
            case R.id.abandon:
                amount = prize[qn];
                addScore(Integer.parseInt(amount));
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

        euros = (TextView) findViewById(R.id.euros);
        numberQ = (TextView) findViewById(R.id.numberQ);
        quesionText = (TextView) findViewById(R.id.questionText);

        QuestionList = generateQuestionList();

        qn = 1;

        euros.setText(prize[qn]);
        numberQ.setText(String.valueOf(qn));
        q = QuestionList.get(qn - 1);
        quesionText.setText(q.getText());
        buttonAnswer1.setText(q.getAnswer1());
        buttonAnswer2.setText(q.getAnswer2());
        buttonAnswer3.setText(q.getAnswer3());
        buttonAnswer4.setText(q.getAnswer4());
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

    void acierto(int button) {

         resetButtons();

        if (qn == prize.length - 1) {
            qn = 1;
            amount = "1000000";
            addScore(Integer.parseInt(amount));

        } else {
            // sigo jugando
            qn++;
            euros.setText(prize[qn]);
            numberQ.setText(Integer.toString(qn));
            q = QuestionList.get(qn - 1);
            quesionText.setText(q.getText());

            buttonAnswer1.setText(q.getAnswer1());
            buttonAnswer2.setText(q.getAnswer2());
            buttonAnswer3.setText(q.getAnswer3());
            buttonAnswer4.setText(q.getAnswer4());
        }
    }

    void fallo() {
        amount = "0";
        if (qn >= 6) amount = "1000";
        if (qn >= 11) amount = "32000";
        resetButtons();
        qn = 1;
        addScore(Integer.parseInt(amount));
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

        buttonAnswer1.setClickable(true);
        buttonAnswer2.setClickable(true);
        buttonAnswer3.setClickable(true);
        buttonAnswer4.setClickable(true);
    }

    public List<Question> generateQuestionList() {
        List<Question> list = new ArrayList<Question>();
        Question q = null;

        q = new Question(
                "1",
                "Which is the Sunshine State of the US?",
                "North Carolina",
                "Florida",
                "Texas",
                "Arizona",
                "2",
                "2",
                "2",
                "1",
                "4"
        );
        list.add(q);

        q = new Question(
                "2",
                "Which of these is not a U.S. state?",
                "New Hampshire",
                "Washington",
                "Wyoming",
                "Manitoba",
                "4",
                "4",
                "4",
                "2",
                "3"
        );
        list.add(q);

        q = new Question(
                "3",
                "What is Book 3 in the Pokemon book series?",
                "Charizard",
                "Island of the Giant Pokemon",
                "Attack of the Prehistoric Pokemon",
                "I Choose You!",
                "3",
                "2",
                "3",
                "1",
                "4"
        );
        list.add(q);

        q = new Question(
                "4",
                "Who was forced to sign the Magna Carta?",
                "King John",
                "King Henry VIII",
                "King Richard the Lion-Hearted",
                "King George III",
                "1",
                "3",
                "1",
                "2",
                "3"
        );
        list.add(q);

        q = new Question(
                "5",
                "Which ship was sunk in 1912 on its first voyage, although people said it would never sink?",
                "Monitor",
                "Royal Caribean",
                "Queen Elizabeth",
                "Titanic",
                "4",
                "4",
                "4",
                "1",
                "2"
        );
        list.add(q);

        q = new Question(
                "6",
                "Who was the third James Bond actor in the MGM films? (Do not include &apos;Casino Royale&apos;.)",
                "Roger Moore",
                "Pierce Brosnan",
                "Timothy Dalton",
                "Sean Connery",
                "1",
                "3",
                "3",
                "2",
                "3"
        );
        list.add(q);

        q = new Question(
                "7",
                "Which is the largest toothed whale?",
                "Humpback Whale",
                "Blue Whale",
                "Killer Whale",
                "Sperm Whale",
                "4",
                "2",
                "2",
                "2",
                "3"
        );
        list.add(q);

        q = new Question(
                "8",
                "In what year was George Washington born?",
                "1728",
                "1732",
                "1713",
                "1776",
                "2",
                "2",
                "2",
                "1",
                "4"
        );
        list.add(q);

        q = new Question(
                "9",
                "Which of these rooms is in the second floor of the White House?",
                "Red Room",
                "China Room",
                "State Dining Room",
                "East Room",
                "2",
                "2",
                "2",
                "3",
                "4"
        );
        list.add(q);

        q = new Question(
                "10",
                "Which Pope began his reign in 963?",
                "Innocent III",
                "Leo VIII",
                "Gregory VII",
                "Gregory I",
                "2",
                "1",
                "2",
                "3",
                "4"
        );
        list.add(q);

        q = new Question(
                "11",
                "What is the second longest river in South America?",
                "Parana River",
                "Xingu River",
                "Amazon River",
                "Rio Orinoco",
                "1",
                "1",
                "1",
                "2",
                "3"
        );
        list.add(q);

        q = new Question(
                "12",
                "What Ford replaced the Model T?",
                "Model U",
                "Model A",
                "Edsel",
                "Mustang",
                "2",
                "4",
                "4",
                "1",
                "3"
        );
        list.add(q);

        q = new Question(
                "13",
                "When was the first picture taken?",
                "1860",
                "1793",
                "1912",
                "1826",
                "4",
                "4",
                "4",
                "1",
                "3"
        );
        list.add(q);

        q = new Question(
                "14",
                "Where were the first Winter Olympics held?",
                "St. Moritz, Switzerland",
                "Stockholm, Sweden",
                "Oslo, Norway",
                "Chamonix, France",
                "4",
                "1",
                "4",
                "2",
                "3"
        );
        list.add(q);

        q = new Question(
                "15",
                "Which of these is not the name of a New York tunnel?",
                "Brooklyn-Battery",
                "Lincoln",
                "Queens Midtown",
                "Manhattan",
                "4",
                "4",
                "4",
                "1",
                "3"
        );
        list.add(q);

        return list;
    }

    public void addScore(int amount){
        //SettingsActivity.et
        //EditText etName = (EditText)findViewById(R.id.etName);
       // String name = etName.toString();
         String name = "Paquito";
        try {
            PuntuacionesSQLiteHelper.getInstance(this).addScore(name, amount);
        } catch(SQLiteException e){
            e.printStackTrace();
        }

    }


}
