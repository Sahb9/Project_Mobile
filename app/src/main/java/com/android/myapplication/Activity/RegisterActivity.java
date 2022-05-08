package com.android.myapplication.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.android.myapplication.DAO.AccountDAO;
import com.android.myapplication.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


public class RegisterActivity extends AppCompatActivity {
    Button btnRegister;
    EditText edtUSerName,edtEmail,edtPhoneNumber,edtPassword;
    AccountDAO accountDAO = new AccountDAO();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        changeStatusBarColor();
        addControl();
        addEvent();
    }

    public void addControl() {
        btnRegister = findViewById(R.id.RegisterButton);
        edtUSerName = findViewById(R.id.editTextName);
        edtEmail = findViewById(R.id.editTextEmail);
        edtPhoneNumber = findViewById(R.id.editTextMobile);
        edtPassword = findViewById(R.id.editTextPassword);
    }

    private void changeStatusBarColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            //window.setStatusBarColor(Color.TRANSPARENT);
            window.setStatusBarColor(getResources().getColor(R.color.register_bk_color));
        }
    }

    private void addEvent() {
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = edtUSerName.getText().toString().trim();
                String email = edtEmail.getText().toString().trim();
                String phoneNumber = edtPhoneNumber.getText().toString().trim() ;
                String password = edtPassword.getText().toString().trim();

                Log.d("Register", "onClick: " + email);

                boolean isAddUser = accountDAO.AddUserAuth(email, password, RegisterActivity.this);

                if(isAddUser) {
                    accountDAO.addInformationUser(phoneNumber,email,username,password);
                    Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                    startActivity(intent);
                }
                //Toast.makeText(RegisterActivity.this, "Dang ky thanh cong", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void onLoginClick(View view) {
        startActivity(new Intent(this,LoginActivity.class));
        overridePendingTransition(R.anim.slide_in_left,android.R.anim.slide_out_right);
    }

}
