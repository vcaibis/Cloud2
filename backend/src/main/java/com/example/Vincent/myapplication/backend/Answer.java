package com.example.Vincent.myapplication.backend;

import com.googlecode.objectify.Ref;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

/**
 * Created by Vincent on 03.11.2016.
 */

@Entity
public class Answer{

    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    Long Id;
    String content;
    Ref<Question> question;

    public Answer() {

    }

    public Question getQuestion() {
        return question.get();
    }

    public void setQuestion(Question question) {
        this.question = Ref.create(question);
    }

    public Answer(String content) {
        this.content = content;
    }

    public long getId() {
        return Id;
    }

    public void setId(Long Id) {
        this.Id = Id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    }
