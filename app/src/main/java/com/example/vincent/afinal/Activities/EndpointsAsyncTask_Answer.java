package com.example.vincent.afinal.Activities;

import android.os.AsyncTask;
import android.util.Log;


import com.example.vincent.myapplication.backend.answerApi.AnswerApi;
import com.example.vincent.myapplication.backend.answerApi.model.Answer;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Vincent on 07.01.2017.
 */

public class EndpointsAsyncTask_Answer extends AsyncTask<Void,Void,List<Answer>>{

    private static AnswerApi answerApi = null;
    private static final String TAG = EndpointsAsyncTask_Answer.class.getName();
    private Answer answer;

    EndpointsAsyncTask_Answer(){}

    EndpointsAsyncTask_Answer(Answer answer){ this.answer = answer; }

    @Override
    protected List<Answer> doInBackground(Void... params) {
        if(answerApi == null){
            AnswerApi.Builder builder = new AnswerApi.Builder(AndroidHttp.newCompatibleTransport(),
                    new AndroidJsonFactory(),null)
                    .setRootUrl("https://questionboard-155021.appspot.com/_ah/api/");
            answerApi = builder.build();
        }

        try {
            if(answer != null){
                answerApi.insert(answer).execute();
                Log.i(TAG,"insert answer");
            }
            return answerApi.list().execute().getItems();
        } catch (IOException e) {
            Log.e(TAG, e.toString());
            return new ArrayList<Answer>();
        }
    }

    @Override
    protected void onPostExecute(List<Answer> result) {
        if(result != null){
            for(Answer answer : result){
                Log.i(TAG, "Content : " + answer.getContent());
            }
        }
    }
}
