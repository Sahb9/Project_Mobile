package com.android.myapplication;

public class Account {
    private int Id;
    private String Ten;
    private byte[] Hinh;
    private String userName;
    private String passWord;

    public Account(int id, String ten, byte[] hinh, String userName, String passWord) {
        Id = id;
        Ten = ten;
        Hinh = hinh;
        this.userName = userName;
        this.passWord = passWord;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getTen() {
        return Ten;
    }

    public void setTen(String ten) {
        Ten = ten;
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
