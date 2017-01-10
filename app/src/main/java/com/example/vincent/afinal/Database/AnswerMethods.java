package com.example.vincent.afinal.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.vincent.afinal.Objects.Answer;
import com.example.vincent.afinal.Objects.Question;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Vincent on 03.11.2016.
 */

public class AnswerMethods {
    private Context context;
    private SQLiteDatabase database;
    private DBHelper dbHelper;
    private String allColumns[] = {DBHelper.COLUMN_ANSWER_ID,DBHelper.COLUMN_ANSWER_CONTENT,DBHelper.COLUMN_ANSWER_QUESTION_ID};

    //Constructeur
    public AnswerMethods(Context context){
        dbHelper = new DBHelper(context);
        this.context = context;
        open();
    }

    //Ouvre la base de donnée
    public void open(){
        database = dbHelper.getWritableDatabase();
    }

    //Ferme la base de donnée
    public void close(){
        dbHelper.close();
    }

    //Methode utilisée pour créer une nouvelle réponse à une question
    public Answer createAnswer(String content, long questionId){
        ContentValues values = new ContentValues();
        values.put(DBHelper.COLUMN_ANSWER_CONTENT,content);
        values.put(DBHelper.COLUMN_ANSWER_QUESTION_ID,questionId);

        long insertId = database.insert(DBHelper.TABLE_ANSWERS,null,values);
        Cursor cursor = database.query(DBHelper.TABLE_ANSWERS, allColumns, DBHelper.COLUMN_ANSWER_ID + " = " + insertId,null,null,null,null);
        cursor.moveToFirst();

        Answer newAnswer = cursorToAnswer(cursor);
        cursor.close();
        return newAnswer;
    }

    public  void deleteAllAnswers(){
        database.delete(DBHelper.TABLE_ANSWERS,null,null);
        database.close();
    }

    public void deleteAnswer(Answer answer){
        long id = answer.getId();
        database.delete(DBHelper.TABLE_ANSWERS, DBHelper.COLUMN_ANSWER_ID + " = " + id,null);
    }

    //Méthode utilisée pour récupérer toutes les réponses à une question donnée par son ID
    public List<Answer> getAnswersOfQuestion(long questionId){
        List<Answer> listAnswers = new ArrayList<Answer>();

        Cursor cursor = database.query(DBHelper.TABLE_ANSWERS,allColumns,DBHelper.COLUMN_QUESTION_ID + " = ?",
                new String[] {String.valueOf(questionId)},null,null,null);
        cursor.moveToFirst();

        //While there's still answer with the same id as the question, it keeps adding content to the listAnswer
        while (!cursor.isAfterLast()){
            Answer answer = cursorToAnswer(cursor);
            listAnswers.add(answer);
            cursor.moveToNext();
        }
        cursor.close();
        return listAnswers;
    }

    public Answer cursorToAnswer(Cursor cursor){
        Answer answer = new Answer();
        answer.setId(cursor.getLong(0));
        answer.setContent(cursor.getString(1));

        //Récupère la question par son ID
        long questionId = cursor.getLong(2);
        QuestionMethods questionMethods = new QuestionMethods(context);
        Question question = questionMethods.getQuestionById(questionId);
        if (question != null){
            answer.setQuestion(question);
        }
        return answer;
    }

    //Parcours toutes les lignes de la table Answers
    public Cursor getAllRows(){
        Cursor cursor = database.query(dbHelper.TABLE_ANSWERS,allColumns,null,null,null,null,null);
        if (cursor != null){
            cursor.moveToNext();
        }
        return cursor;
    }

    //Se positionne sur une ligne précise
    public Cursor getRow(long rowId){
        String where = DBHelper.COLUMN_ANSWER_ID + "=" + rowId;
        Cursor cursor = database.query(true,DBHelper.TABLE_ANSWERS,allColumns,where,null,null,null,null,null);
        if (cursor != null){
            cursor.moveToFirst();
        }
        return cursor;
    }

    //Met à jour une ligne précise dont on précise l'ID
    public boolean updateRow(long rowId, String content, long questionId){
        String where = DBHelper.COLUMN_ANSWER_ID + "=" + rowId;
        ContentValues contentValues = new ContentValues();
        contentValues.put(DBHelper.COLUMN_ANSWER_CONTENT,content);
        contentValues.put(DBHelper.COLUMN_ANSWER_QUESTION_ID,questionId);
        return database.update(DBHelper.TABLE_ANSWERS,contentValues,where,null) !=0;
    }
}
