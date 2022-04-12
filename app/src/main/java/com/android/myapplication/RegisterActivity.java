package com.android.myapplication;

import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;

import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


public class RegisterActivity extends AppCompatActivity {
    Button btnRegister;
    Database database;
    EditText edtName,edtEmail,edtPhoneNumber,edtPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        changeStatusBarColor();
        addControl();
        processDatabase();
//        database.QueryData("DROP TABLE Account ");
        addEvent();


    }

    private void addEvent() {
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    public void addControl()
    {
        btnRegister = findViewById(R.id.RegisterButton);
        edtName = findViewById(R.id.editTextName);
        edtEmail = findViewById(R.id.editTextEmail);
        edtPhoneNumber = findViewById(R.id.editTextMobile);
        edtPassword = findViewById(R.id.editTextPassword);
    }
    public void processDatabase()
    {
        database = new Database(this,"Project.sqlite",null,1);

        database.QueryData("CREATE TABLE IF NOT EXISTS Account(\n" +
                "Id INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                "userName varchar(200),\n" +
                "passWord varchar(200),\n" +
                "Hinh BLOB,\n"+
                "phone int,\n"+
                "email varchar(200)\n" +
                ")");
    }
    private  void addUSer()
    {
        String username = edtName.getText().toString();
        //Hình
        //
        String email = edtEmail.getText().toString();
        String phoneNumber = edtPhoneNumber.getText().toString();
        String password = edtPassword.getText().toString();
        database.QueryData("INSERT INTO Account VALUES(null,'"+username+"','"+password+"',null,'"+phoneNumber+"','"+email+"')");




    }
    private void GetData(String username , String password)
    {
        Cursor dataCongViec = database.GetData("SELECT * FROM Account WHERE userName = '"+ username+ "' and passWord= '"+password+"'");
        //arrayCongViec.clear();//Xóa mãng trc khi cập nhật dữ liệu
        while (dataCongViec.moveToNext())
        {
            int id = dataCongViec.getInt(0);
            String ten = dataCongViec.getString(1);
            //Toast.makeText(this,ten,Toast.LENGTH_SHORT).show();
            //arrayCongViec.add( new CongViec(id,ten) );
        }
        //adapter.notifyDataSetChanged();
    }
    private void changeStatusBarColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
//            window.setStatusBarColor(Color.TRANSPARENT);
            window.setStatusBarColor(getResources().getColor(R.color.register_bk_color));
        }
    }

    public void onLoginClick(View view){
        startActivity(new Intent(this,LoginActivity.class));
        overridePendingTransition(R.anim.slide_in_left,android.R.anim.slide_out_right);

    }



}
