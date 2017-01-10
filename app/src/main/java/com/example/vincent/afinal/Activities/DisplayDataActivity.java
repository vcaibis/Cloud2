package com.example.vincent.afinal.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import com.example.vincent.afinal.R;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

//Classe pas utilisée dans la version final

public class DisplayDataActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_data);
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
}
