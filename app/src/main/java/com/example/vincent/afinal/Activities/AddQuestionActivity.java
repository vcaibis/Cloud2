package com.example.vincent.afinal.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.vincent.afinal.Database.QuestionMethods;
import com.example.vincent.afinal.Objects.Question;
import com.example.vincent.afinal.R;

/**
 * Created by Vincent on 03.11.2016.
 */

public class AddQuestionActivity extends AppCompatActivity implements OnClickListener {

    private EditText etQuestionTitle;
    private Button btnAdd;
    private QuestionMethods questionMethods;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_question);

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

    private void initViews(){
        this.etQuestionTitle = (EditText) findViewById(R.id.et_add_question_title);
        this.btnAdd = (Button) findViewById(R.id.btn_addQuestion);
        this.btnAdd.setOnClickListener(this);
        this.questionMethods = new QuestionMethods(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            //Si l'ID du bouton de question est récupéré, le contenu de l'edit text est stockà dans une variable
            case R.id.btn_addQuestion:
                Editable question_title = etQuestionTitle.getText();
                //Puis si la variable n'est pas vide ou null, un object Question est créé grâce à la méthode CreateQuestion
                if (!TextUtils.isEmpty(question_title)){

                    //Cloud
                    com.example.vincent.myapplication.backend.questionApi.model.Question question = new com.example.vincent.myapplication.backend.questionApi.model.Question();
                    question.setTitle(question_title.toString());
                    new EndpointsAsyncTask_Question(question).execute();

                    //Local
                    Question createdQuestion = questionMethods.createQuestion(question_title.toString());
                    Intent intent = new Intent();
                    //Transmet les données à l'activité ListQuestion via la methode putExtra
                    intent.putExtra(ListQuestionsActivity.EXTRA_ADDED_QUESTION,createdQuestion);
                    //Gestion du cycle de vie de l'activité
                    setResult(RESULT_OK, intent);
                    finish();
                    //Si l'edit text est vide, alors on affiche un toast pour en informer l'utilisateur
                }else{
                    Toast.makeText(this,R.string.empty_fields_message, Toast.LENGTH_LONG).show();
                }
                break;
            default:
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        questionMethods.close();
    }
}
