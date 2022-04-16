package com.android.myapplication.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.myapplication.DAO.AccountDAO;
import com.android.myapplication.R;


public class LoginActivity extends AppCompatActivity {
    //
    SharedPreferences sharedPreferences;
    EditText txtName,txtPassword;
    Button btnLogin;

    AccountDAO accountDAO = new AccountDAO();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
                if(accountDAO.checkLogin(LoginActivity.this,username,password))
                {
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
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

    public void onLoginClick(View View){
        startActivity(new Intent(this, RegisterActivity.class));
        overridePendingTransition(R.anim.slide_in_right,R.anim.stay);

    }


}
