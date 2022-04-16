package com.android.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.myapplication.Others.Login;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class DatePicker extends AppCompatActivity {

    EditText editText;
    Button btnBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_date_picker);
        editText = findViewById(R.id.editTextDate);
        btnBack = findViewById(R.id.buttonBack);

        //events
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DatePicker.this, Login.class);
                startActivity(intent);
            }
        });

        editText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ChonNgay();
            }
        });
    }
    private void ChonNgay()
    {
        // xóa khi bấm tiếp
        int length = editText.getText().length();
        if (length > 0) {
            //Xóa khi bấm tiếp
            editText.getText().delete(0, length);
        }
        //Select real-time
        Calendar calendar = Calendar.getInstance();

        //

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(android.widget.DatePicker datePicker, int i, int i1, int i2) {
                calendar.set(i,i1,i2);
                SimpleDateFormat dinhDangNgay = new SimpleDateFormat("dd/MM/yyyy");
                editText.append(dinhDangNgay.format(calendar.getTime()));

            }
        },2021,calendar.get(Calendar.MONTH+1),calendar.get(Calendar.DATE));
        datePickerDialog.show();
    }
}