package com.example.vincent.afinal.Activities;

import android.os.AsyncTask;
import android.util.Log;


import com.example.vincent.myapplication.backend.questionApi.model.Question;
import com.example.vincent.myapplication.backend.questionApi.QuestionApi;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Vincent on 07.01.2017.
 */

public class EndpointsAsyncTask_Question extends AsyncTask<Void,Void,List<Question>>{

    private static QuestionApi questionApi = null;
    private static final String TAG = EndpointsAsyncTask_Question.class.getName();
    private Question question;

    EndpointsAsyncTask_Question(){}

    EndpointsAsyncTask_Question(Question question){ this.question = question; }

    @Override
    protected List<Question> doInBackground(Void... params) {
        if(questionApi == null){
            QuestionApi.Builder builder = new QuestionApi.Builder(AndroidHttp.newCompatibleTransport(),
                    new AndroidJsonFactory(),null)
                    .setRootUrl("https://questionboard-155021.appspot.com/_ah/api/");
            questionApi = builder.build();
        }

        try {
            if(question != null){
                questionApi.insert(question).execute();
                Log.i(TAG,"insert question");
            }
            return questionApi.list().execute().getItems();
        } catch (IOException e) {
            Log.e(TAG, e.toString());
            return new ArrayList<Question>();
        }
    }

    @Override
    protected void onPostExecute(List<Question> result) {
        if(result != null){
            for(Question question : result){
                Log.i(TAG, "Title : " + question.getTitle());
            }
        }
    }
}
