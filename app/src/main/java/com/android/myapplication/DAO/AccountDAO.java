package com.android.myapplication.DAO;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;

import androidx.annotation.Nullable;

import com.android.myapplication.Models.Database;

public class AccountDAO extends SQLiteOpenHelper {
    Database database;

    public AccountDAO(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

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

    private  void addUSer(String username,String email,byte[] hinh,String phoneNumber,String password)
    {
        //String username = edtUSerName.getText().toString();
        //Hình
        //
        //String email = edtEmail.getText().toString();
        //String phoneNumber = edtPhoneNumber.getText().toString();
        //String password = edtPassword.getText().toString();
        database.QueryData("INSERT INTO Account VALUES(null,'"+username+"','"+password+"',null,'"+phoneNumber+"','"+email+"')");




    }

    public void ADD_User(String username,String password,byte[] hinh,String phoneNumber,String email)
    {
        SQLiteDatabase database = getWritableDatabase();
        String sql = "INSERT INTO Dovat VALUES(null,?,?,?,?,?)";
        SQLiteStatement statement= database.compileStatement(sql);// biên dịch chuỗi insert
        statement.clearBindings(); // phân tích dữ liệu xong thì xóa
        statement.bindString(1,username);
        statement.bindString(2,password);
        statement.bindBlob(3,hinh);
        statement.bindString(4,phoneNumber);
        statement.bindString(4,email);
        //execute statement
        statement.executeInsert();

    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
