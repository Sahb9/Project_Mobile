package com.android.myapplication.Entity;

public class Account {
    private int Id;

    private byte[] Hinh;
    private String email;
    private String userName;
    private String passWord;

    public Account(int id, byte[] hinh, String email, String userName, String passWord) {
        Id = id;
        Hinh = hinh;
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

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }



    public byte[] getHinh() {
        return Hinh;
    }

    public void setHinh(byte[] hinh) {
        Hinh = hinh;
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
