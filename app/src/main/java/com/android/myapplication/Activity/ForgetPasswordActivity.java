package com.android.myapplication.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.myapplication.DAO.AccountDAO;
import com.android.myapplication.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ForgetPasswordActivity extends AppCompatActivity {
    Button btnForgot;
    TextView txtemail;
    AccountDAO accountDAO = new AccountDAO();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);
        addControl();
        addEvent();
    }

    void addControl() {
        btnForgot = findViewById(R.id.ForgotButton);
        txtemail= findViewById(R.id.editTextEmailForgot);
    }

    void addEvent() {
        btnForgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email= txtemail.getText().toString().trim();
                accountDAO.sendNewPasswordbyEmail(email);
                Toast.makeText(ForgetPasswordActivity.this, "Your Password has been sent", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void onLoginClick(View view){
        startActivity(new Intent(this,LoginActivity.class));
        overridePendingTransition(R.anim.slide_in_left,android.R.anim.slide_out_right);
    }
}