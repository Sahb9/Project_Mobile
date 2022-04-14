package com.android.myapplication.DAO;

import android.database.Cursor;
import android.widget.Toast;

import com.android.myapplication.LoginActivity;
import com.android.myapplication.Models.Database;

public class LoginDAO {
    Database database;

    private boolean checkLogin(String username , String password)
    {
        Cursor dataCongViec = database.GetData("SELECT * FROM Account WHERE userName = '"+ username+ "' and passWord= '"+password+"'");

        if(dataCongViec.getCount()>0)
        {
            //Toast.makeText(LoginActivity.this, "đăng ký thành công", Toast.LENGTH_SHORT).show();
            return true;
        }
        return false;
    }
}
