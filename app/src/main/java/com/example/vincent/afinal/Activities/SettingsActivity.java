package com.example.vincent.afinal.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;

import com.example.vincent.afinal.Database.AnswerMethods;
import com.example.vincent.afinal.Database.QuestionMethods;
import com.example.vincent.afinal.Database.UserMethods;
import com.example.vincent.afinal.R;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

//Pas grand chose à dire ici, il y a simplement 3 boutons permettant de supprimer toutes les données de chaques tables

public class SettingsActivity extends AppCompatActivity {
    AnswerMethods answerMethods;
    QuestionMethods questionMethods;
    UserMethods userMethods;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
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

    public void delete_all_users(View view) {
        userMethods = new UserMethods(this);
        userMethods.deleteAllUsers();
    }

    public void delete_all_questions(View view) {
        questionMethods = new QuestionMethods(this);
        questionMethods.deleteAllQuestions();
    }

    public void delete_all_answers(View view) {
        answerMethods = new AnswerMethods(this);
        answerMethods.deleteAllAnswers();
    }
}
