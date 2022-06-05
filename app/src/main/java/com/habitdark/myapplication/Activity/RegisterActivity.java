package com.habitdark.myapplication.Activity;

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

import androidx.appcompat.app.AppCompatActivity;

import com.habitdark.myapplication.DAO.AccountDAO;
import com.habitdark.myapplication.R;
import com.habitdark.myapplication.callback.CallBack;

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

                accountDAO.AddUserAuth(email, password, new CallBack<Boolean>() {
                    @Override
                    public void onCallBack(Boolean callback) {
                        if(callback) {
                            accountDAO.addInformationUser(phoneNumber, email, username, password);

                            Toast.makeText(RegisterActivity.this, "Dang ky thanh cong", Toast.LENGTH_SHORT).show();
                            // Chuyển dữ liệu qua bên login khi đk xong
                            Bundle bundle = new Bundle();
                            bundle.putString("email",email);
                            bundle.putString("password",password);

                            //intent data
                            Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                            intent.putExtra("data_Register",bundle);
                            startActivity(intent);
                        }
                    }
                });
            }
        });
    }

    public void onLoginClick(View view) {
        startActivity(new Intent(this,LoginActivity.class));
        overridePendingTransition(R.anim.slide_in_left,android.R.anim.slide_out_right);
    }

}
