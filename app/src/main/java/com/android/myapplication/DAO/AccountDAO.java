package com.android.myapplication.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;

import androidx.annotation.Nullable;

import com.android.myapplication.Models.Database;
import com.android.myapplication.Models.Database2;

public class AccountDAO extends SQLiteOpenHelper {
    Database database;

    public AccountDAO(@Nullable Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
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
                "phone varchar(200),\n"+
                "email varchar(200)\n" +
                ")");
    }
    public boolean checkLogin(String username , String password)
    {
        //Database2 database2 = null;

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

        database.QueryData("INSERT INTO Account VALUES(null,'"+username+"','"+password+"',null,'"+phoneNumber+"','"+email+"')");




    }

    public void ADD_User(String username,String password,String phoneNumber,String email)
    {
        SQLiteDatabase database = getWritableDatabase();
        String sql = "INSERT INTO Account VALUES(null,?,?,null,?,?)";
        SQLiteStatement statement= database.compileStatement(sql);// biên dịch chuỗi insert
        statement.clearBindings(); // phân tích dữ liệu xong thì xóa
        statement.bindString(1,username);
        statement.bindString(2,password);
        //statement.bindBlob(3,hinh);
        statement.bindString(4,phoneNumber);
        statement.bindString(5,email);
        //execute statement
        statement.executeInsert();

    }

    public void ADD_User2(String username,String password,String phoneNumber,String email)
    {
        // gọi db
        Database2 database2 = new Database2();
        database2.processCopy();
        ContentValues row = new ContentValues();
        row.put("userName",username);
        row.put("passWord",password);
        row.put("phone",phoneNumber);
        row.put("email",email);


        database2.database.insert("Account","userName,passWord,phone,email",row);





    }
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
