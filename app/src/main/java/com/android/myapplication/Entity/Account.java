package com.android.myapplication.Entity;

public class Account {
    private String phone;
    private String email;
    private String userName;
    private String passWord;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Account(String phone, String email, String userName, String passWord) {
        this.phone = phone;
        this.email = email;
        this.userName = userName;
        this.passWord = passWord;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }
}
