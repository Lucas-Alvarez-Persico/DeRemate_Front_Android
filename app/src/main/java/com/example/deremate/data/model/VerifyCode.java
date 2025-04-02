package com.example.deremate.data.model;

public class VerifyCode {
    private String username;
    private String code;


    public VerifyCode(String code, String username) {
        this.code = code;
        this.username = username;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
    public String getUsername() {
        return username;
    }

}
