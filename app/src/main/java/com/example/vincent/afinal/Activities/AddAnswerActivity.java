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
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.vincent.afinal.Adapter.SpinnerQuestionsAdapter;
import com.example.vincent.afinal.Database.AnswerMethods;
import com.example.vincent.afinal.Database.QuestionMethods;
import com.example.vincent.afinal.Objects.Answer;
import com.example.vincent.afinal.Objects.Question;
import com.example.vincent.afinal.R;

import java.util.List;

/**
 * Created by Vincent on 03.11.2016.
 */

public class AddAnswerActivity extends AppCompatActivity implements OnClickListener, OnItemSelectedListener {

    private EditText etContent;
    private Spinner spinnerQuestion;
    private Button btnAdd;
    private QuestionMethods questionMethods;
    private AnswerMethods answerMethods;
    private Question selectedQuestion;
    private SpinnerQuestionsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_answer);

        initViews();
        //Création d'une liste de question et on la rempli avec la methode getAllQuestions() qui retourne une liste de question
        List<Question> listQuestions = questionMethods.getAllQuestions();
        //Si cette liste de questions n'est pas null, on défini un spinner contenant la liste de question de manière à permettre à l'utilisateur de choisir à quelle question il souhaite répondre
        if (listQuestions != null){
            adapter = new SpinnerQuestionsAdapter(this, listQuestions);
            spinnerQuestion.setAdapter(adapter);
            spinnerQuestion.setOnItemSelectedListener(this);
        }
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
        this.etContent = (EditText) findViewById(R.id.et_add_answer_content);
        this.spinnerQuestion = (Spinner) findViewById(R.id.spinner_questions);
        this.btnAdd = (Button) findViewById(R.id.btn_addAnswer);
        this.btnAdd.setOnClickListener(this);
        this.questionMethods = new QuestionMethods(this);
        this.answerMethods = new AnswerMethods(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            //Si l'ID du bouton de réponse est récupéré, le contenu de l'edit text est stockà dans une variable
            case R.id.btn_addAnswer:
                Editable answer_content = etContent.getText();
                selectedQuestion = (Question) spinnerQuestion.getSelectedItem();
                //Puis si la variable n'est pas vide ou null, un object Answer est créé grâce à la méthode CreateAnswer
                if(!TextUtils.isEmpty(answer_content) || selectedQuestion != null){

                    //Cloud
                    com.example.vincent.myapplication.backend.answerApi.model.Answer answer = new com.example.vincent.myapplication.backend.answerApi.model.Answer();
                    answer.setContent(answer_content.toString());
                    new EndpointsAsyncTask_Answer(answer).execute();

                    //Local
                    Answer createdAnswer = answerMethods.createAnswer(answer_content.toString(),selectedQuestion.getId());
                    Intent intent = new Intent();
                    //Transmet les données à l'activité ListAnswer via la methode putExtra
                    intent.putExtra(ListAnswersActivity.EXTRA_ADDED_ANSWER,createdAnswer);
                    //Gestion du cycle de vie de l'activité
                    setResult(RESULT_OK);
                    finish();
                    //Si l'edit text est vide, alors on affiche un toast pour en informer l'utilisateur
                }else{
                    Toast.makeText(this, R.string.empty_fields_message, Toast.LENGTH_LONG).show();
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

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        selectedQuestion = adapter.getItem(position);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
