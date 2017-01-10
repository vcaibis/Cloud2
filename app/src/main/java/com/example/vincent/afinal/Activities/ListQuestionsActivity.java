package com.example.vincent.afinal.Activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.vincent.afinal.Adapter.ListQuestionsAdapter;
import com.example.vincent.afinal.Database.QuestionMethods;
import com.example.vincent.afinal.Objects.Question;
import com.example.vincent.afinal.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Vincent on 03.11.2016.
 */

public class ListQuestionsActivity extends AppCompatActivity implements OnItemClickListener, OnItemLongClickListener, OnClickListener{

    public static final int REQUEST_CODE_ADD_QUESTION = 42;
    public static final String EXTRA_ADDED_QUESTION = "extra_key_added_question";

    private ListView listViewQuestions;
    private TextView tvEmptyListQuestions;
    private Button btnAddQuestion;
    private ListQuestionsAdapter adapter;
    private List<Question> listQuestions;
    private QuestionMethods questionMethods;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_questions);

        initViews();

        //Remplissage de la liste de questions grâce à la methode getAllQuestions()
        listQuestions = questionMethods.getAllQuestions();
        //Si elle n'est pas vide ou null, création d'un custom adapter et on l'applique sur la liste
        if(listQuestions != null || !listQuestions.isEmpty()){
            adapter = new ListQuestionsAdapter(this,listQuestions);
            listViewQuestions.setAdapter(adapter);
            //Sinon on affiche le message par défaut quand il n'y a aucune questions
        }else{
            tvEmptyListQuestions.setVisibility(View.VISIBLE);
            listViewQuestions.setAdapter(adapter);
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


    public void initViews(){
        this.questionMethods = new QuestionMethods(this);
        this.listViewQuestions = (ListView) findViewById(R.id.list_questions);
        this.tvEmptyListQuestions = (TextView) findViewById(R.id.tv_empty_list_questions);
        this.btnAddQuestion = (Button) findViewById(R.id.btn_add_question);
        this.listViewQuestions.setOnItemClickListener(this);
        this.listViewQuestions.setOnItemLongClickListener(this);
        this.btnAddQuestion.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //Si l'id correspond à celui du bouton d'ajout de questions, on passe sur l'activity d'ajout de réponse en délivrant le résultat
            case R.id.btn_add_question:
                Intent intent = new Intent(this, AddQuestionActivity.class);
                startActivityForResult(intent, REQUEST_CODE_ADD_QUESTION);
                break;

            default:
                break;
        }
    }

    @Override
    //Methode utilisée pour récupérer le résultat précédent
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        //Si le requestCode est le même qu'avant
        if (requestCode == REQUEST_CODE_ADD_QUESTION){
            //Si le résultat d'activité est un succès
            if (resultCode == RESULT_OK){
                //Si les données de l'intent ne sont pas nulles, on crée un objet Question
                if(data != null){
                    Question createdQuestion = (Question) data.getSerializableExtra(EXTRA_ADDED_QUESTION);
                    //Si l'ibject n'est pas null et si la liste ne l'est pas non plus, on crée une nouvelle liste de questions
                    if(createdQuestion != null){
                        if(listQuestions != null){
                            listQuestions = new ArrayList<Question>();
                        }
                        //On ajoute la question crée précédement à la liste
                        listQuestions.add(createdQuestion);

                        if(adapter == null){
                            if(listViewQuestions.getVisibility() != View.VISIBLE){
                                listViewQuestions.setVisibility(View.VISIBLE);
                                tvEmptyListQuestions.setVisibility(View.GONE);
                            }

                            adapter = new ListQuestionsAdapter(this, listQuestions);
                            listViewQuestions.setAdapter(adapter);
                        }else{
                            adapter.setItems(listQuestions);
                            adapter.notifyDataSetChanged();
                        }
                    }
                }
            }
        }else{
            super.onActivityResult(requestCode,resultCode,data);
        }
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        questionMethods.close();
    }

    @Override
    //Quand on click sur une question de la liste, ça passe sur la liste de réponse correspondant à l'ID de la question clickée
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Question clickedQuestion = adapter.getItem(position);
        Intent intent = new Intent(this,ListAnswersActivity.class);
        intent.putExtra(ListAnswersActivity.EXTRA_SELECTED_QUESTION_ID, clickedQuestion.getId());
        startActivity(intent);
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        Question clickedQuestion = adapter.getItem(position);
        showDeleteDialogConfirmation(clickedQuestion);
        return true;
    }

    private void showDeleteDialogConfirmation(final Question clickedQuestion) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

        alertDialogBuilder.setTitle(R.string.delete);
        alertDialogBuilder.setMessage(R.string.confirmation_messages + clickedQuestion.getTitle());
        //Défini le comportement du bouton oui
        alertDialogBuilder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                //Supprime la question
                if (questionMethods != null) {
                    questionMethods.deleteQuestion(clickedQuestion);
                    listQuestions.remove(clickedQuestion);
                    //Si la liste est vide après la suppression, affiche le message de base
                    if (listQuestions.isEmpty()) {
                        listViewQuestions.setVisibility(View.GONE);
                        tvEmptyListQuestions.setVisibility(View.VISIBLE);
                    }
                    adapter.setItems(listQuestions);
                    adapter.notifyDataSetChanged();
                }

                dialog.dismiss();
                Toast.makeText(ListQuestionsActivity.this, R.string.question_delete, Toast.LENGTH_LONG).show();
            }
        });
        //Défini le comportement du bouton non
        alertDialogBuilder.setNeutralButton(android.R.string.no, new DialogInterface.OnClickListener(){

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }
}
