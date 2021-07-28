package com.example.ngay_27_7_2021.model;

import java.util.ArrayList;
import java.util.stream.Stream;

import io.reactivex.rxjava3.annotations.NonNull;

public class User  {
    private int userId, id;
    private String title,body;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public User(int userId, int id, String title, String body) {
        this.userId = userId;
        this.id = id;
        this.title = title;
        this.body = body;
    }

    public User() {
    }

    @Override
    public String toString() {
        return "\nUser{\n" +
                "userId=" + userId +
                ", \nid=" + id +
                ", \ntitle='" + title + '\'' +
                ", \nbody='" + body + '\'' +
                '}' + "\n-------------------------\n";
    }

}
