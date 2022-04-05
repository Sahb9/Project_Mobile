package com.android.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
    TextView txtTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txtTime = findViewById(R.id.textViewTime);
        Calendar calendar = Calendar.getInstance();
        txtTime.setText(calendar.getTime() + "\n");
        txtTime.append(calendar.get(Calendar.DATE)+"\n");
        txtTime.append(calendar.get(Calendar.MONTH)+"\n");// tháng đúng phải +1
        txtTime.append(calendar.get(Calendar.YEAR)+"\n");//

        //format ngày tháng năm
        SimpleDateFormat dinhDangNgay = new SimpleDateFormat("dd/MM/yyyy");
        txtTime.append(dinhDangNgay.format(calendar.getTime()));

        txtTime.append(calendar.get(Calendar.HOUR)+"\n");// định dạng 12h
        txtTime.append(calendar.get(Calendar.HOUR_OF_DAY)+"\n");// định dạng 24h

        SimpleDateFormat dinhDangGio = new SimpleDateFormat("hh:mm:ss ");
        txtTime.append(dinhDangGio.format(calendar.getTime()));


    }
}