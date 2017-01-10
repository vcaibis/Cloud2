package com.example.vincent.afinal.Objects;

import java.io.Serializable;

/**
 * Created by Vincent on 03.11.2016.
 */

public class Question implements Serializable{

    private static final long serialVersionUID = -7406082437623008161L;
    long Id;
    String title;

    public Question() {

    }

    public Question(String title) {
        this.title = title;
    }

    public long getId() {
        return Id;
    }

    public void setId(long Id) {
        this.Id = Id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
