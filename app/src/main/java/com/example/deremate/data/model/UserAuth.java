package com.example.deremate.data.model;

public class UserAuth {
    private String access_token;
    private String role;

    public String getAccess_token() {
        return access_token;
    }

    public String getRole() {
        return role;
    }

    public UserAuth(String access_token, String role) {
        this.access_token = access_token;
        this.role = role;
    }



}
