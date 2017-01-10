package com.example.vincent.afinal.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import com.example.vincent.afinal.R;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class HomeActivity extends AppCompatActivity {

    //Pas grand chose à commenter ici, cette classe sert à la navigation et ne possède donc que des methodes permettant de passer d'une activité à une autre via des intent

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
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

    public void toQuestions(View view) {
        Intent intent = new Intent(HomeActivity.this,ListQuestionsActivity.class);
        startActivity(intent);
    }

    public void toSettings(View view) {
        Intent intent = new Intent(HomeActivity.this,SettingsActivity.class);
        startActivity(intent);
    }

    public void showUsers(View view) {
        Intent intent = new Intent(HomeActivity.this,ShowUsersActivity.class);
        startActivity(intent);
    }

    public void showQuestions(View view) {
        Intent intent = new Intent(HomeActivity.this,ShowQuestionsActivity.class);
        startActivity(intent);
    }

    public void showAnswers(View view) {
        Intent intent = new Intent(HomeActivity.this,ShowAnswersActivity.class);
        startActivity(intent);
    }

    public void UpdateData(View view) {
        Intent intent = new Intent(HomeActivity.this,UpdateDataActivity.class);
        startActivity(intent);
    }

    public void toLanguage(View view) {
        Intent intent = new Intent(HomeActivity.this,LanguageActivity.class);
        startActivity(intent);
    }
}
