package com.android.myapplication.DAO;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;

import com.android.myapplication.Models.DatabaseActivity;
import com.android.myapplication.Models.Database;

public class AccountDAO {

    public boolean checkLogin(Activity activity,String username , String password)
    {
        //Database2 database2 = null;
        DatabaseActivity.sqLiteDatabase= Database.initDatabase(activity,DatabaseActivity.DBName);
        Cursor cursor = DatabaseActivity.sqLiteDatabase.rawQuery("SELECT * FROM Account WHERE userName = ? and passWord= ?",new String[]{username,password});
        if(cursor.getCount() !=0)
        {
            return true;
        }
        return false;
    }
    public void ADD_User2(Activity activity, String username, String password, String phoneNumber, String email)
    {
        // gọi db
        //Database2 database2 = new Database2();
        // database2.processCopy();
        // cach 3
        DatabaseActivity.sqLiteDatabase= Database.initDatabase(activity,DatabaseActivity.DBName);

        ContentValues row = new ContentValues();
        row.put("userName",username);
        row.put("passWord",password);
        row.put("phone",phoneNumber);
        row.put("email",email);

        DatabaseActivity.sqLiteDatabase.insert("Account","userName,passWord,phone,email",row);
        // insert("Account","userName,passWord,phone,email",row);

    }

}
