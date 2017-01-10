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

import com.example.vincent.afinal.Adapter.ListAnswersAdapter;
import com.example.vincent.afinal.Database.AnswerMethods;
import com.example.vincent.afinal.Objects.Answer;
import com.example.vincent.afinal.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Vincent on 03.11.2016.
 */

public class ListAnswersActivity extends AppCompatActivity implements OnItemLongClickListener, OnItemClickListener, OnClickListener{

    public static final int REQUEST_CODE_ADD_ANSWER = 42;
    public static final String EXTRA_ADDED_ANSWER = "extra_key_added_answer";
    public static final String EXTRA_SELECTED_QUESTION_ID = "extra_key_selected_question_id";

    private ListView listViewAnswers;
    private TextView tv_EmptyListAnswers;
    private Button btnAddAnswer;
    private ListAnswersAdapter adapter;
    private List<Answer> listAnswers;
    private AnswerMethods answerMethods;
    private long questionId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_answers);

        initViews();

        //Récupération de l'intent de la précédente activité (putExtra)
        Intent intent = getIntent();
        //Si elle n'est pas null, son Id est stocké dans la variable questionId
        if (intent != null){
            this.questionId = intent.getLongExtra(EXTRA_SELECTED_QUESTION_ID,-1);
        }
        //Si la valeur n'est pas -1 (chiffre par défaut),
        if(questionId != -1){
            //Remplissage de la liste de réponse correspondant à l'ID de la question
            listAnswers = answerMethods.getAnswersOfQuestion(questionId);
            //Si la liste n'est pas vide ou null, on créer un custom adapter et on l'applique à la liste
            if (listAnswers != null && !listAnswers.isEmpty()){
                adapter = new ListAnswersAdapter(this, listAnswers);
                listViewAnswers.setAdapter(adapter);
                //S'il n'y a pas de réponse pour le moment, affiche le message par défaut demandant à l'utilisateur d'en créer une
            }else{
                tv_EmptyListAnswers.setVisibility(View.VISIBLE);
                listViewAnswers.setVisibility(View.GONE);
            }
        }
    }

    private void initViews(){
        this.listViewAnswers = (ListView) findViewById(R.id.list_answers);
        this.tv_EmptyListAnswers = (TextView) findViewById(R.id.tv_empty_list_answers);
        this.btnAddAnswer = (Button) findViewById(R.id.btn_add_answer);
        this.listViewAnswers.setOnItemClickListener(this);
        this.listViewAnswers.setOnItemLongClickListener(this);
        this.btnAddAnswer.setOnClickListener(this);
        answerMethods = new AnswerMethods(this);
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //Si l'id correspond à celui du bouton d'ajout de réponse, on passe sur l'activity d'ajout de réponse en délivrant le résultat
            case R.id.btn_add_answer:
                Intent intent = new Intent(this, AddAnswerActivity.class);
                startActivityForResult(intent, REQUEST_CODE_ADD_ANSWER);
                break;

            default:
                break;
        }
    }

    @Override
    //Methode utilisée pour récupérer le résultat précédent
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //Si le requestCode est le même qu'avant
        if(requestCode == REQUEST_CODE_ADD_ANSWER){
            //Si le résultat d'activité est un succès
            if (resultCode == RESULT_OK){
                //Si la liste de réponse n'est pas vide ou null, alors on créer une nouvelle liste de réponses
                if (listAnswers == null || !listAnswers.isEmpty()){
                    listAnswers = new ArrayList<Answer>();
                }
                //Si la classe métier pour les réponses est null, on la crée
                if (answerMethods == null){
                    answerMethods = new AnswerMethods(this);
                }
                //Récupération des réponses dont l'ID de question correspond à la question sélectionnée précédement
                listAnswers = answerMethods.getAnswersOfQuestion(questionId);
                //Si l'adapteur est null, on crée un custom adapter et on l'applique à la liste de réponses
                if (adapter == null){
                    adapter = new ListAnswersAdapter(this, listAnswers);
                    listViewAnswers.setAdapter(adapter);
                    //Si la liste n'est pas visible, on enlève le message demandant de créer une nouvelle question et la replace par la liste
                    if (listViewAnswers.getVisibility() != View.VISIBLE){
                        tv_EmptyListAnswers.setVisibility(View.GONE);
                        listViewAnswers.setVisibility(View.VISIBLE);
                    }
                }else{
                    adapter.setItems(listAnswers);
                    adapter.notifyDataSetChanged();
                }
            }
        }else {
            super.onActivityResult(requestCode,resultCode,data);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        answerMethods.close();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Answer clickedAnswer = adapter.getItem(position);
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        Answer clickedAnswer = adapter.getItem(position);
        showDeleteDialogConfirmation(clickedAnswer);
        return true;
    }

    public void showDeleteDialogConfirmation(final Answer answer){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

        alertDialogBuilder.setTitle(R.string.delete);
        alertDialogBuilder.setMessage(R.string.confirmation_message + answer.getContent() + " ?");
        //Défini le comportement du bouton oui
        alertDialogBuilder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //Supprime la réponse
                if(answerMethods != null){
                    answerMethods.deleteAnswer(answer);
                    listAnswers.remove(answer);
                    //Si la liste est vide après la suppression, affiche le message de base
                    if (listAnswers.isEmpty()){
                        listViewAnswers.setVisibility(View.GONE);
                        tv_EmptyListAnswers.setVisibility(View.VISIBLE);
                    }
                    adapter.setItems(listAnswers);
                    adapter.notifyDataSetChanged();
                }
                dialog.dismiss();
                Toast.makeText(ListAnswersActivity.this, R.string.answer_delete,Toast.LENGTH_LONG).show();
            }
        });

        //Défini le comportement du bouton non
        alertDialogBuilder.setNeutralButton(android.R.string.no, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }
}
