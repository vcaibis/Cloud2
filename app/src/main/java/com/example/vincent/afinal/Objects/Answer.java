package com.example.vincent.afinal.Objects;

import java.io.Serializable;

/**
 * Created by Vincent on 03.11.2016.
 */

public class Answer implements Serializable{

    private static final long serialVersionUID = -7406082437623008161L;
    long Id;
    String content;
    Question question;

    public Answer() {

    }

    public Answer(String content) {
        this.content = content;
    }

    public long getId() {
        return Id;
    }

    public void setId(long Id) {
        this.Id = Id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }
}
