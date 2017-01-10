package com.example.vincent.afinal.Objects;

/**
 * Created by Vincent on 03.11.2016.
 */

public class User {

    long id;
    String userName;
    String password;

    public User(){

    }

    public User(String username, String password){
        this.userName = username;
        this.password = password;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
