package com.android.myapplication.DAO;

import android.content.Context;

import com.android.myapplication.Models.Database;

public class RegisterDAO {
    Database database;
    public void processDatabase(Context context)
    {
        database = new Database(context,"Project.sqlite",null,1);

        database.QueryData("CREATE TABLE IF NOT EXISTS Account(\n" +
                "Id INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                "userName varchar(200),\n" +
                "passWord varchar(200),\n" +
                "Hinh BLOB,\n"+
                "phone int,\n"+
                "email varchar(200)\n" +
                ")");
    }
    private  void addUSer(String username,String email,String phoneNumber,String password)
    {
        //String username = edtUSerName.getText().toString();
        //Hình
        //
        //String email = edtEmail.getText().toString();
        //String phoneNumber = edtPhoneNumber.getText().toString();
        //String password = edtPassword.getText().toString();
        database.QueryData("INSERT INTO Account VALUES(null,'"+username+"','"+password+"',null,'"+phoneNumber+"','"+email+"')");




    }
}
