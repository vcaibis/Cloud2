
package com.example.vincent.afinal.Activities;


import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.vincent.afinal.Database.DBHelper;
import com.example.vincent.afinal.Database.UserMethods;
import com.example.vincent.afinal.R;

public class LoginActivity extends AppCompatActivity {

    private UserMethods userMethods;
    private EditText et_username;
    private EditText et_password;
    private String username;
    private String password;
    private Boolean correctUser;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    public void loginUser(View view) {
        initViews();
        if(correctUser){
            Intent intent = new Intent(LoginActivity.this,HomeActivity.class);
            startActivity(intent);
            //Si l'un des champs est vide, affiche un toast afin d'informer l'utilisateur
        }else if(username.equals("") || password.equals("")){
            Toast.makeText(LoginActivity.this,R.string.empty_fields_message,Toast.LENGTH_LONG).show();
            //Si les informations transmisent de correspondent à rien dans la base de donnée, affiche un toast afin d'informer l'utilisateur
        }else {
            Toast.makeText(LoginActivity.this,R.string.invalid_user_password,Toast.LENGTH_LONG).show();
        }
    }

    public void initViews(){
        userMethods = new UserMethods(this);
        et_username = (EditText) findViewById(R.id.et_username);
        et_password = (EditText) findViewById(R.id.et_password);
        username = et_username.getText().toString();
        password = et_password.getText().toString();
        //Retourne une valeur true ou false en fonction de si les informations transmisent correspondent à une ligne dans la base de donnée
        correctUser = userMethods.validateUser(username,password);
    }

    public void toRegister(View view) {
        Intent intent = new Intent(LoginActivity.this,RegisterActivity.class);
        startActivity(intent);
    }
}
