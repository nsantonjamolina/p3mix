package com.example.ignaciosantonjamolina.p3;

/**
 * Created by ignaciosantonjamolina on 2/3/17.
 */
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class PuntuacionesSQLiteHelper extends SQLiteOpenHelper {

    //Sentencia SQL para crear la tabla de puntuaciones
    String sqlCreate = "CREATE TABLE Puntuaciones (id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT NOT NULL, score INTEGER NOT NULL)";

    // Singleton pattern to centralize access to the database
    private static PuntuacionesSQLiteHelper instance;

    public PuntuacionesSQLiteHelper(Context contexto) {
        super(contexto, "database_file", null, 1);
    }

    public synchronized static PuntuacionesSQLiteHelper getInstance(Context context) {
        if (instance == null) {
            instance = new PuntuacionesSQLiteHelper (context.getApplicationContext());
        }
        return instance;
    }



    @Override
    public void onCreate(SQLiteDatabase db) {
        //Se ejecuta la sentencia SQL de creación de la tabla
        db.execSQL(sqlCreate);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int versionAnterior, int versionNueva) {

        //Se elimina la versión anterior de la tabla
        db.execSQL("DROP TABLE IF EXISTS Usuarios");

        //Se crea la nueva versión de la tabla
        db.execSQL(sqlCreate);
    }


    /*
    *   Get List<Scores> with all the scores
    * */

    public List<Score> getScores(){
        List<Score> result = new ArrayList<>();
        Score item;

        //Get access to the db in read mode
        SQLiteDatabase db = getReadableDatabase();
        //Query the table to get the text and author for all existing entries
        Cursor cursor = db.query("Puntuaciones", new String[]{"name", "score"},null,null, null, null, null);
        //Go through the resulting cursor
        while (cursor.moveToNext()) {
            // Create a Quotation object for the given entry in the database
            item = new Score(cursor.getString(0),cursor.getInt(1));
            // Add the object to the result list
            result.add(item);
        }
        // Close cursor and database helper
        cursor.close();
        db.close();

        return result;
    }


    /*
        Insert a new score
     */
    public void addScore(String name, int score){
        // Get access to the db in write mode
        SQLiteDatabase db = getWritableDatabase();
        // Insert the new score
        ContentValues values = new ContentValues();
        values.put("name", name);
        values.put("score", score);
        db.insert("Puntuaciones", null, values);
        //Close the db helper
    }

    /*
        Remove all entries from the database
    */
    public void clearAllScores() {

        // Get access to the database in write mode
        SQLiteDatabase database = getWritableDatabase();
        // Delete all entries from the table
        database.delete("Puntuaciones", null, null);
        // Close the database helper
        database.close();
    }

    /*
        Delete a Score from the database given an id
    */
    public void deleteQuotation(int id) {

        // Get access to the database in write mode
        SQLiteDatabase database = getWritableDatabase();
        // Delete from the table any entry with the given id
        database.delete("Puntuaciones", "id=?", new String[]{""+id+""});
        // Close the database helper
        database.close();
    }



}