package com.example.vincent.afinal.Activities;

import android.os.AsyncTask;
import android.util.Log;


import com.example.vincent.myapplication.backend.userApi.UserApi;
import com.example.vincent.myapplication.backend.userApi.model.User;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Vincent on 07.01.2017.
 */

public class EndpointsAsyncTask_User extends AsyncTask<Void,Void,List<User>>{

    private static UserApi userApi = null;
    private static final String TAG = com.example.vincent.afinal.Activities.EndpointsAsyncTask_User.class.getName();
    private User user;

    EndpointsAsyncTask_User(){}
    EndpointsAsyncTask_User(User user){ this.user = user; }

    @Override
    protected List<User> doInBackground(Void... params) {
        if(userApi == null){
            UserApi.Builder builder = new UserApi.Builder(AndroidHttp.newCompatibleTransport(),
                    new AndroidJsonFactory(),null)
                    .setRootUrl("https://questionboard-155021.appspot.com/_ah/api/");
            userApi = builder.build();
        }

        try {
            if(user != null){
                userApi.insert(user).execute();
                Log.i(TAG,"insert user");
            }
            return userApi.list().execute().getItems();
        } catch (IOException e) {
            Log.e(TAG, e.toString());
            return new ArrayList<User>();
        }
    }

    @Override
    protected void onPostExecute(List<User> result) {
        if(result != null){
            for(User user : result){
                Log.i(TAG, "Username : " + user.getUserName() + " \nPassword : " + user.getPassword());
            }
        }
    }
}
