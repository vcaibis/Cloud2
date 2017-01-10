package com.example.vincent.afinal.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.vincent.afinal.Objects.Answer;
import com.example.vincent.afinal.Objects.Question;

import java.util.ArrayList;
import java.util.List;

import javax.crypto.spec.DHGenParameterSpec;

/**
 * Created by Vincent on 03.11.2016.
 */

public class QuestionMethods {
    private SQLiteDatabase database;
    private DBHelper dbHelper;
    private Context context;
    private String allColumns[] = {DBHelper.COLUMN_QUESTION_ID,DBHelper.COLUMN_QUESTION_TITLE};

    public QuestionMethods(Context context){
        this.context = context;
        dbHelper = new DBHelper(context);
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

    //Methode utilisée pour créer une noubelle question
    public Question createQuestion(String title){
        ContentValues values = new ContentValues();
        values.put(DBHelper.COLUMN_QUESTION_TITLE,title);

        long insertID = database.insert(DBHelper.TABLE_QUESTIONS,null,values);

        Cursor cursor = database.query(DBHelper.TABLE_QUESTIONS, allColumns,DBHelper.COLUMN_QUESTION_ID + " = " + insertID,null,null,null,null);
        cursor.moveToFirst();

        Question newQuestion = cursorToQuestion(cursor);
        cursor.close();
        return newQuestion;
    }

    public  void deleteAllQuestions(){
        database.delete(DBHelper.TABLE_QUESTIONS,null,null);
        database.close();
    }

    //Methode utilisée pour supprimer une question et toutes les réponses qui lui sont liées
    public void deleteQuestion(Question question) {
        long id = question.getId();
        //Supprimes les réponses don't l'ID de question correspont à l'ID de cette question
        AnswerMethods answerMethods = new AnswerMethods(context);
        List<Answer> listAnswer = answerMethods.getAnswersOfQuestion(id);
        //si la liste de réponse n'est pas vide ou null, on la parcours et on supprime tout
        if (listAnswer != null && !listAnswer.isEmpty()) {
            for (Answer answer : listAnswer) {
                answerMethods.deleteAnswer(answer);
            }
        }
        database.delete(DBHelper.TABLE_QUESTIONS, DBHelper.COLUMN_QUESTION_ID + " = " + id, null);
    }

    //Methode utilisée pour récupérer toutes les questions de la table dans une liste
    public List<Question> getAllQuestions() {
        List<Question> listQuestions = new ArrayList<>();

        Cursor cursor = database.query(DBHelper.TABLE_QUESTIONS, allColumns, null, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();

            //While it's not at the end of the table, it keeps adding content to the listQuestions
            while (!cursor.isAfterLast()) {
                Question question = cursorToQuestion(cursor);
                listQuestions.add(question);
                cursor.moveToNext();
            }
            cursor.close();
        }
        return listQuestions;
    }

    //Méthode utilisée pour récupérer une question par son ID
    public Question getQuestionById(long id) {
        Cursor cursor = database.query(DBHelper.TABLE_QUESTIONS, allColumns,
                DBHelper.COLUMN_QUESTION_ID + " = ?",
                new String[] { String.valueOf(id) }, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }

        Question question = cursorToQuestion(cursor);
        return question;
    }

    public Question cursorToQuestion(Cursor cursor) {
        Question question = new Question();
        question.setId(cursor.getLong(0));
        question.setTitle(cursor.getString(1));
        return question;
    }

    //Parcours toutes les lignes de la table Questions
    public Cursor getAllRows(){
        Cursor cursor = database.query(dbHelper.TABLE_QUESTIONS,allColumns,null,null,null,null,null);
        if (cursor != null){
            cursor.moveToNext();
        }
        return cursor;
    }

    //Se positionne sur une ligne précise
    public Cursor getRow(long rowId){
        String where = DBHelper.COLUMN_QUESTION_ID + "=" + rowId;
        Cursor cursor = database.query(true,DBHelper.TABLE_QUESTIONS,allColumns,where,null,null,null,null,null);
        if (cursor != null){
            cursor.moveToFirst();
        }
        return cursor;
    }

    //Met à jour une ligne précise dont on précise l'ID
    public boolean updateRow(long rowId, String title){
        String where = DBHelper.COLUMN_QUESTION_ID + "=" + rowId;
        ContentValues contentValues = new ContentValues();
        contentValues.put(DBHelper.COLUMN_QUESTION_TITLE,title);
        return database.update(DBHelper.TABLE_QUESTIONS,contentValues,where,null) !=0;
    }
}