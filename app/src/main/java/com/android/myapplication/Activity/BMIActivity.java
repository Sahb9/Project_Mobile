package com.android.myapplication.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.myapplication.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class BMIActivity extends AppCompatActivity{
    EditText editTextDate,edtAge,edtHeight,edtWeigh;
    TextView txtIdeal,txtBMI,txtFat;
    Button btnBack,btnResult;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bmiactivity);
        //
//        DarkModePrefManager darkModePrefManager = new DarkModePrefManager(this);
//        darkModePrefManager.setDarkMode(!darkModePrefManager.isNightMode());
//        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
//        recreate();
        //
        addControl();
        addEvent();

    }
    public void addEvent()
    {
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(BMIActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        editTextDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ChonNgay();
            }
        });
        btnResult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int tuoi = Integer.parseInt(edtAge.getText().toString()) ;
                double chieucao = Double.parseDouble(edtHeight.getText().toString())  ;
                double cangnang = Double.parseDouble(edtWeigh.getText().toString())  ;
                //String resultBMI = ;
                txtBMI.setText( String.valueOf(TinhBMI(cangnang,chieucao)));
                txtFat.setText( String.valueOf(TinhFat(cangnang,chieucao,tuoi)));
                txtIdeal.setText(String.valueOf(tinhChieuCaoLyTuong(chieucao)));


            }
        });
    }
    public void addControl()
    {
        editTextDate = findViewById(R.id.editTextDate);
        btnBack = findViewById(R.id.buttonBack);
        edtAge = findViewById(R.id.editTextAge);
        edtHeight = findViewById(R.id.editTextHeigh);
        edtWeigh = findViewById(R.id.editTextWeigh);
        txtIdeal = findViewById(R.id.textViewIdeal_input);
        txtBMI =findViewById(R.id.textViewBMI);
        txtFat = findViewById(R.id.textViewFat_input);
        btnResult = findViewById(R.id.buttonResult);
    }
    private void ChonNgay()
    {
        // xóa khi bấm tiếp
        int length = editTextDate.getText().length();
        if (length > 0) {
            //Xóa khi bấm tiếp
            editTextDate.getText().delete(0, length);
        }
        //Select real-time
        Calendar calendar = Calendar.getInstance();

        //

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(android.widget.DatePicker datePicker, int i, int i1, int i2) {
                calendar.set(i,i1,i2);
                SimpleDateFormat dinhDangNgay = new SimpleDateFormat("dd/MM/yyyy");
                editTextDate.append(dinhDangNgay.format(calendar.getTime()));

            }
        },2021,calendar.get(Calendar.MONTH+1),calendar.get(Calendar.DATE));
        datePickerDialog.show();
    }
    double TinhBMI(double cannang,double chieucao)
    {
        double rate =  cannang / ((chieucao/100)*2);
        return Math.round(rate * 10) / 10 ;
    }
    double TinhFat(double cannang,double chieucao,int tuoi)
    {
        double rate = (1.2* TinhBMI(cannang,chieucao)) + (0.23*tuoi) -10.8- 5.4;
        return Math.round(rate * 10) / 10;
    }
    double tinhChieuCaoLyTuong(double chieucao)
    {
        double rate = ((chieucao-100)*9)/10;

        return Math.round(rate * 10) / 10;
    }
}