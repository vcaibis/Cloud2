package com.example.vincent.afinal.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;

import com.example.vincent.afinal.Database.DBHelper;
import com.example.vincent.afinal.Database.UserMethods;
import com.example.vincent.afinal.Objects.User;
import com.example.vincent.afinal.R;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity {

    private UserMethods userMethods;
    private EditText et_username;
    private EditText et_password;
    private String username;
    private String password;
    private Boolean correctUser;
    com.example.vincent.myapplication.backend.userApi.model.User userbackend = new com.example.vincent.myapplication.backend.userApi.model.User();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
    }

    public void registerUser(View view) {
        initViews();
        Intent intent = new Intent(RegisterActivity.this,LoginActivity.class);
        //Si le nom d'utilisateur est déjà présent dans la base de données, affiche un toast pour en informer l'utlisateur
        if(correctUser){
            Toast.makeText(RegisterActivity.this,R.string.username_taken,Toast.LENGTH_LONG).show();
            //Si l'un des champs est vide, affiche un toast pour en informer l'utlisateur
        }else if (username.equals("") || password.equals("")){
            Toast.makeText(RegisterActivity.this,R.string.empty_fields_message,Toast.LENGTH_LONG).show();
            //Si tout est bon, on ajoute le nouvel utilisateur à la base de donnée
        }else {
            userMethods.addUser(new User(username,password));
            new EndpointsAsyncTask_User(userbackend).execute();
            startActivity(intent);
        }
    }

    public void initViews(){
        userMethods = new UserMethods(this);
        et_username = (EditText) findViewById(R.id.et_register_username);
        et_password = (EditText) findViewById(R.id.et_register_password);
        username = et_username.getText().toString();
        password = et_password.getText().toString();
        userbackend.setUserName(username);
        userbackend.setPassword(password);
        correctUser = userMethods.userExists(username);
    }
}
