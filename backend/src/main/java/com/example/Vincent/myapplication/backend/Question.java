package com.example.Vincent.myapplication.backend;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.Ref;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

/**
 * Created by Vincent on 03.11.2016.
 */

@Entity
public class Question {

    @Id
    @GeneratedValue(
        strategy = GenerationType.IDENTITY
    )
    Long Id;
    private String title;

    public Question() {

    }

    public Question(String title) {
        this.title = title;
    }

    public long getId() {
        return Id;
    }

    public void setId(Long Id) {
        this.Id = Id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
