package com.example.deremate.data.model;

public class User {


    private String username;
    private String password;

    public User(String password, String username) {
        this.username = username;
        this.password = password;
    }
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }



}
