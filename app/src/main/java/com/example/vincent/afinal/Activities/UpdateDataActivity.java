package com.example.vincent.afinal.Activities;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.vincent.afinal.Database.AnswerMethods;
import com.example.vincent.afinal.Database.QuestionMethods;
import com.example.vincent.afinal.Database.UserMethods;
import com.example.vincent.afinal.R;

public class UpdateDataActivity extends AppCompatActivity {

    UserMethods userMethods;
    QuestionMethods questionMethods;
    AnswerMethods answerMethods;
    EditText userID, userName, userPassword;
    EditText questionID, questionTitle;
    EditText answerID, answerContent, answerQuestionID;
    String username,password,title,content;
    long userId,questionId,answerId,answer_question_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_data);
        initViews();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch(id){
            case R.id.action_home :
                Intent toHome = new Intent(this,HomeActivity.class);
                startActivity(toHome);
                return true;
            case R.id.action_questions :
                Intent toQuestions = new Intent(this,ListQuestionsActivity.class);
                startActivity(toQuestions);
                return true;
            case R.id.action_settings :
                Intent toSettings = new Intent(this,SettingsActivity.class);
                startActivity(toSettings);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void initViews(){
        this.userID = (EditText) findViewById(R.id.tv_id_user);
        this.userName = (EditText) findViewById(R.id.tv_name_user);
        this.userPassword = (EditText) findViewById(R.id.tv_password_user);
        this.questionID = (EditText)findViewById(R.id.et_update_question_id);
        this.questionTitle = (EditText) findViewById(R.id.et_update_question_title);
        this.answerID = (EditText) findViewById(R.id.et_update_answer_id);
        this.answerContent = (EditText) findViewById(R.id.et_update_answer_content);
        this.answerQuestionID = (EditText) findViewById(R.id.et_update_answer_question_id);
    }

    //Methode utilisée pour mettre à jour la ligne souhaitée dans la table USER
    //On situe la ligne grâce à l'ID et si il elle est présente, on la met à jour avec le contenu des edit text
    public void updateUserData(View view) {
        userMethods = new UserMethods(this);
        userId = Integer.parseInt(userID.getText().toString());
        Cursor cursor = userMethods.getRow(userId);
        if (cursor.moveToFirst()){
            username = userName.getText().toString();
            password = userPassword.getText().toString();
            userMethods.updateRow(userId,username,password);
        }
        cursor.close();
    }

    //Methode utilisée pour mettre à jour la ligne souhaitée dans la table QUESTION
    //On situe la ligne grâce à l'ID et si il elle est présente, on la met à jour avec le contenu des edit text
    public void UpdateQuestionData(View view) {
        questionMethods = new QuestionMethods(this);
        questionId = Integer.parseInt(questionID.getText().toString());
        Cursor cursor = questionMethods.getRow(questionId);
        if (cursor.moveToFirst()){
            title = questionTitle.getText().toString();
            questionMethods.updateRow(questionId,title);
        }
        cursor.close();
    }

    //Methode utilisée pour mettre à jour la ligne souhaitée dans la table ANSWER
    //On situe la ligne grâce à l'ID et si il elle est présente, on la met à jour avec le contenu des edit text
    public void updateAnswerData(View view) {
        answerMethods = new AnswerMethods(this);
        answerId = Integer.parseInt(answerID.getText().toString());
        answer_question_id = Integer.parseInt(answerQuestionID.getText().toString());
        Cursor cursor = answerMethods.getRow(answerId);
        if (cursor.moveToFirst()){
            content = answerContent.getText().toString();
            answerMethods.updateRow(answerId,content,answer_question_id);
        }
        cursor.close();
    }
}
