package com.android.myapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.myapplication.DAO.AccountDAO;
import com.android.myapplication.Models.Database;
import com.google.android.material.navigation.NavigationView;


public class LoginActivity extends AppCompatActivity {

    SharedPreferences sharedPreferences;
    EditText txtName,txtPassword;
    Button btnLogin;
    Database database;
    AccountDAO accountDAO;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        accountDAO=new AccountDAO(this,null,null,1);
        accountDAO.processDatabase(this);
        //for changing status bar icon colors
        if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.M){
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
        setContentView(R.layout.activity_login_new);
        txtName = findViewById(R.id.editTextName);
        txtPassword = findViewById(R.id.editTextPassword);
        btnLogin = findViewById(R.id.cirLoginButton);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username= txtName.getText().toString().trim();
                String password= txtPassword.getText().toString().trim();
                if(accountDAO.checkLogin(username,password))
                {
                    Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                    Toast.makeText(LoginActivity.this,"Dăng nhập thành công",Toast.LENGTH_LONG).show();

                    startActivity(intent);
                }
                else
                {
                    Toast.makeText(LoginActivity.this,"Dăng nhập thất bại ",Toast.LENGTH_LONG).show();

                }


            }
        });
    }
    private void checkLogin(String username , String password)
    {
        Cursor dataCongViec = database.GetData("SELECT * FROM Account WHERE userName = '"+ username+ "' and passWord= '"+password+"'");

        if(dataCongViec.getCount()>0)
        {
            Toast.makeText(LoginActivity.this, "đăng ký thành công", Toast.LENGTH_SHORT).show();

        }
    }
    public void onLoginClick(View View){
        startActivity(new Intent(this,RegisterActivity.class));
        overridePendingTransition(R.anim.slide_in_right,R.anim.stay);

    }

}
