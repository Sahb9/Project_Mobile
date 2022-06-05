package com.habitdark.myapplication.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.habitdark.myapplication.DAO.AccountDAO;
import com.habitdark.myapplication.R;
import com.habitdark.myapplication.callback.CallBack;
import com.habitdark.myapplication.utilities.Common;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class LoginActivity extends AppCompatActivity {
    //
    SharedPreferences sharedPreferences;
    EditText txtName, txtPassword;
    Button btnLogin;
    TextView txtForget;
    AccountDAO accountDAO = new AccountDAO();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //for changing status bar icon colors
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }

        setContentView(R.layout.activity_login_new);
        addControl();
        addEvents();
        processEmailAndPassword();

        // Lazy login


//        Handler handler = new Handler();
//        handler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                nextActivity();
//            }
//        },2000);
    }


    void processEmailAndPassword() {
        sharedPreferences = getSharedPreferences("dataLogin", MODE_PRIVATE);
        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("data_Register");
        if (bundle != null) {
            String emailRegister = bundle.getString("email");
            String passwordRegister = bundle.getString("password");
            txtName.setText(emailRegister);
            txtPassword.setText(passwordRegister);
        } else // nếu ko có dữ liệu từ bên đăng ký chuyển qua
        {
            //  lấy giá trị preference || Gán giá trị bth hoặc khi mới chạy sẽ mặc định là chuỗi rỗng
            txtName.setText(sharedPreferences.getString("email", ""));
            txtPassword.setText(sharedPreferences.getString("password", ""));
        }

    }

    void addControl() {
        txtName = findViewById(R.id.editTextName);
        txtPassword = findViewById(R.id.editTextPassword);
        btnLogin = findViewById(R.id.cirLoginButton);
        txtForget = findViewById(R.id.textViewForgotPassWord);
    }

    void addEvents() {
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = txtName.getText().toString().trim();
                String password = txtPassword.getText().toString().trim();
                accountDAO.SignIn(email, password, new CallBack<Boolean>() {
                    @Override
                    public void onCallBack(Boolean callback) {
                        if (callback) {
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString("email", email);
                            editor.putString("password", password);
                            editor.commit();

                            FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

                            if (firebaseUser != null) {
                                Common.uID = firebaseUser.getUid();
                            }

                            // Sign in success, update UI with the signed-in user's information
                            Toast.makeText(LoginActivity.this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(intent);
                            //finishAffinity();
                        } else {
                            Toast.makeText(LoginActivity.this, "Đăng nhập không thành công", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

        txtForget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, ForgetPasswordActivity.class));
                overridePendingTransition(R.anim.slide_in_right, R.anim.stay);
            }
        });
    }

    private void nextActivity() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            // User is signed in
            Toast.makeText(this, "Dang nhap thanh cong", Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        } else {
            // No user is signed in
            Intent intent = new Intent(this, RegisterActivity.class);//Main dùng tạm để đăng nhập
            startActivity(intent);
            //Toast.makeText(this, "Dang xuat thanh cong", Toast.LENGTH_SHORT).show();
        }
        finish();
    }

    public void onLoginClick(View View) {
        startActivity(new Intent(this, RegisterActivity.class));
        overridePendingTransition(R.anim.slide_in_right, R.anim.stay);

    }
}
