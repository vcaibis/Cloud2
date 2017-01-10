package com.example.vincent.afinal.Activities;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import com.example.vincent.afinal.Database.AnswerMethods;
import com.example.vincent.afinal.Database.DBHelper;
import com.example.vincent.afinal.Database.QuestionMethods;
import com.example.vincent.afinal.R;

public class ShowAnswersActivity extends AppCompatActivity {

    ListView listView;
    AnswerMethods answerMethods;
    SimpleCursorAdapter simpleCursorAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_answers);
        populateListView();
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

    public void populateListView(){

        answerMethods = new AnswerMethods(getBaseContext());
        Cursor cursor = answerMethods.getAllRows();

        //Tableau utilisés pour le "mapping" des colonnes de la table et des edit text
        String fromFieldNames[] = new String[]{DBHelper.COLUMN_ANSWER_ID,DBHelper.COLUMN_ANSWER_CONTENT,DBHelper.COLUMN_ANSWER_QUESTION_ID};
        int toViewIDs[] = new int[]{R.id.tv_answer_ID, R.id.tv_answer_CONTENT,R.id.tv_answer_QUESTION_ID};

        //Création d'un adapteur de base d'android studio et on l'applique sur la listview
        simpleCursorAdapter = new SimpleCursorAdapter(getBaseContext(),R.layout.answer_item_layout,cursor,fromFieldNames,toViewIDs,0);
        listView = (ListView)findViewById(R.id.listview_answers);
        listView.setAdapter(simpleCursorAdapter);
    }
}
