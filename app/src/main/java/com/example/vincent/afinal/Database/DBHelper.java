package com.example.vincent.afinal.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Vincent on 03.11.2016.
 */

//Classe permettant la création de la base de donnée
public class DBHelper extends SQLiteOpenHelper{

    //Colonnes de la table User
    public static final String TABLE_USERS = "users";
    public static final String COLUMN_USER_ID = "_id";
    public static final String COLUMN_USERNAME = "username";
    public static final String COLUMN_PASSWORD = "password";

    //Colonnes de la table question
    public static final String TABLE_QUESTIONS = "questions";
    public static final String COLUMN_QUESTION_ID = "_id";
    public static final String COLUMN_QUESTION_TITLE = "title";

    //Colonnes de la table réponse
    public static final String TABLE_ANSWERS = "answers";
    public static final String COLUMN_ANSWER_ID = COLUMN_QUESTION_ID;
    public static final String COLUMN_ANSWER_CONTENT = "content";
    public static final String COLUMN_ANSWER_QUESTION_ID = "question_id";

    //Nom de la base de donnée et son numéro de version
    private static final String DATABASE_NAME = "PROJECT.DB";
    private static final int DATABASE_VERSION = 1;

    //Requête pour créer la table User
    private static final String CREATE_USER_TABLE_QUERY = "CREATE TABLE " + TABLE_USERS + "("
            + COLUMN_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COLUMN_USERNAME + " TEXT NOT NULL, "
            + COLUMN_PASSWORD + " TEXT NOT NULL " + ");";

    //Requête pour créer la table question
    private static final String CREATE_QUESTION_TABLE_QUERY = "CREATE TABLE " + TABLE_QUESTIONS + "("
            + COLUMN_QUESTION_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COLUMN_QUESTION_TITLE + " TEXT NOT NULL " + ");";

    //Requête pour créer la table réponse
    private static final String CREATE_ANSWER_TABLE_QUERY = "CREATE TABLE " + TABLE_ANSWERS + "("
            + COLUMN_ANSWER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COLUMN_ANSWER_CONTENT + " TEXT NOT NULL, "
            + COLUMN_ANSWER_QUESTION_ID + " INTEGER NOT NULL " + ");";

    public DBHelper(Context context)
    {
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //Execute toutes les requetes
        db.execSQL(CREATE_USER_TABLE_QUERY);
        db.execSQL(CREATE_QUESTION_TABLE_QUERY);
        db.execSQL(CREATE_ANSWER_TABLE_QUERY);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //Drop toutes les tables et en crée de nouvelles
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_QUESTIONS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ANSWERS);
        onCreate(db);
    }
}
