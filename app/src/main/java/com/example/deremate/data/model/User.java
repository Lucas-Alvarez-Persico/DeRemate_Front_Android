package com.example.deremate.data.model;

public class User {

    private String username;
    private String password;
    private String role;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }
    public String getUsername() {
        return username;
    }
    public String getPassword() {
        return password;
    }
    public void setRole(String role){this.role = role;}
    public String getRole(){return role; }
    public void setUsername(String username) {
        this.username = username;
    }



}
