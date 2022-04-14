package com.android.myapplication.Others;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.android.myapplication.DatePicker;
import com.android.myapplication.R;

public class Login extends AppCompatActivity {
    EditText txtName,txtPassword;
    Button btnLogin;
    CheckBox checkResult;
    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //định vị
        Anhxa();
        //Xử lý
        sharedPreferences = getSharedPreferences("dataLogin",MODE_PRIVATE);
        //  lấy giá trị preference || Gán giá trị bth hoặc khi mới chạy sẽ mặc định là chuỗi rỗng hoặc check false
        txtName.setText(sharedPreferences.getString("taikhoan",""));
        txtPassword.setText(sharedPreferences.getString("matkhau",""));
        checkResult.setChecked(sharedPreferences.getBoolean("checked",false));


        //


        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username= txtName.getText().toString().trim();
                String password= txtPassword.getText().toString().trim();
                if(username.equals("anhtuan") && password.equals("1234"))
                {

                    Intent intent = new Intent(Login.this, DatePicker.class);
                    if(checkResult.isChecked())
                    {
                        Toast.makeText(Login.this,"Dăng nhập thành công",Toast.LENGTH_LONG).show();
                        SharedPreferences.Editor editor =  sharedPreferences.edit();
                        editor.putString("taikhoan",username);
                        editor.putString("matkhau",password);
                        editor.putBoolean("checked",true);
                        editor.commit();
                    }
                    else
                    {
                        //Toast.makeText(Login.this,"Dăng nhập thành công nhưng chưa remember",Toast.LENGTH_LONG).show();

                        SharedPreferences.Editor editor =  sharedPreferences.edit();
                        editor.remove("taikhoan");
                        editor.remove("matkhau");
                        editor.remove("checked");
                        startActivity(intent);
                        editor.commit();
                    }
                    startActivity(intent);

                }
            }
        });
    }
    void Anhxa()
    {
        txtName = (EditText) findViewById(R.id.editTextTextName);
        txtPassword = (EditText) findViewById(R.id.editTextTextPassword);
        btnLogin = (Button) findViewById(R.id.buttonLogin);
        checkResult = (CheckBox) findViewById(R.id.checkBoxResult);

    }
}