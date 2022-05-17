package com.android.myapplication.Fragment;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.myapplication.Activity.BMIActivity;
import com.android.myapplication.Activity.MainActivity;
import com.android.myapplication.Activity.ScheduleActivity;
import com.android.myapplication.DAO.HistoryDAO;
import com.android.myapplication.Entity.History;
import com.android.myapplication.Models.CalendarAdapter;
import com.android.myapplication.R;
import com.android.myapplication.callback.CallBack;
import com.android.myapplication.utilities.Common;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */

public class HomeFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    public Context context;
    Fragment fragment;
    public HomeFragment(Context context) {
        // Required empty public constructor
        this.context=context;
    }
    public HomeFragment() {}
    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }

    RelativeLayout layout_schedule, layout_fitness, layout_timeManagement, layout_Eating, layout_BMI, layout_reading;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
         View view = inflater.inflate(R.layout.fragment_home, container, false);
         //addControl(view);
        addEvent();
        //initWidgets();
        this.selectedDate = LocalDate.now();
        setMonthView();
         return view ;
    }

    void addControl(View view) {
//        layout_schedule = view.findViewById(R.id.layout_schedule);
//        layout_fitness = view.findViewById(R.id.layout_fitness);
//        layout_timeManagement = view.findViewById(R.id.layout_timeManagement);
//        layout_Eating = view.findViewById(R.id.layout_eating);
//        layout_BMI = view.findViewById(R.id.layout_BMI);
//        layout_reading = view.findViewById(R.id.layout_reading);
        //
        calendarRecyclerView = view.findViewById(R.id.calendarRecyclerView);
        monthYearText = view.findViewById(R.id.monthYearTV);

    }

    void addEvent() {
//        layout_schedule.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(context, ScheduleActivity.class);
//                //Toast.makeText(MainActivity.this, "Schedule", Toast.LENGTH_SHORT).show();
//                startActivity(intent);
//            }
//        });
//
//        layout_BMI.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(context, BMIActivity.class);
//                //Toast.makeText(MainActivity.this,"Dăng nhập thành công",Toast.LENGTH_LONG).show();
//                startActivity(intent);
//            }
//        });
    }


    private TextView monthYearText;
    private RecyclerView calendarRecyclerView;
    private LocalDate selectedDate;

//    @RequiresApi(api = Build.VERSION_CODES.O)
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_schedule);
//
//        initWidgets();
//        this.selectedDate = LocalDate.now();
//        setMonthView();
//    }

//    private void initWidgets() {
//        calendarRecyclerView = findViewById(R.id.calendarRecyclerView);
//        monthYearText = findViewById(R.id.monthYearTV);
//    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void setMonthView() {
        ScheduleActivity scheduleActivity = new ScheduleActivity();
        monthYearText.setText(monthYearFromDate(this.selectedDate));
        ArrayList<String> daysInMonth = daysInMonthArray(this.selectedDate);

        //CalendarAdapter calendarAdapter = new CalendarAdapter(daysInMonth, scheduleActivity.onItemListener);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(context.getApplicationContext(), 7);

        calendarRecyclerView.setLayoutManager(layoutManager);
        //calendarRecyclerView.setAdapter(calendarAdapter);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private ArrayList<String> daysInMonthArray(LocalDate date) {
        ArrayList<String> daysInMonthArray = new ArrayList<>();

        YearMonth yearMonth = YearMonth.from(date);

        int daysInMonth = yearMonth.lengthOfMonth();
        LocalDate firstOfMonth = this.selectedDate.withDayOfMonth(1);
        int dayOfWeek = firstOfMonth.getDayOfWeek().getValue();

        for(int i = 1; i <= 42; i++) {
            if(i <= dayOfWeek || i > daysInMonth + dayOfWeek) {
                daysInMonthArray.add("");
            } else {
                daysInMonthArray.add(String.valueOf(i - dayOfWeek));
            }
        }

        return  daysInMonthArray;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private String monthYearFromDate(LocalDate date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM yyyy");
        return date.format(formatter);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void previousMonthAction(View view) {
        this.selectedDate = this.selectedDate.minusMonths(1);
        setMonthView();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void nextMonthAction(View view) {
        this.selectedDate = this.selectedDate.plusMonths(1);
        setMonthView();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
//    @Override
    public void onItemClick(int position, String dayText) {
        if(!dayText.equals("")) {
            String message = "Selected Date " + dayText + " " + monthYearFromDate(this.selectedDate);
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show();

            handleDialog(this.selectedDate);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private String formatDateTime(String format, LocalDate localDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
        return localDate.format(formatter);
    }

    private void handleDialog(LocalDate paramLocalDate) {

        final Dialog dialog = new Dialog(this.context);
        //dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.habit_add_dialog);

        Button btnCancel = dialog.findViewById(R.id.btn_cancel);
        Button btnSave = dialog.findViewById(R.id.btn_save);

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.cancel();
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View view) {
                History history = History.getInstance();
                HistoryDAO historyDAO = HistoryDAO.getInstance();

                history.setDataTime(formatDateTime("dd/MM/yyyy", paramLocalDate));
                history.setSubject(Common.SCHEDULE);

                historyDAO.addHistory(Common.uID, history, new CallBack<Boolean>() {
                    @Override
                    public void onCallBack(Boolean callback) {
                        if (callback) {
                           // Toast.makeText(ScheduleActivity.this, "Save complete", Toast.LENGTH_SHORT).show();
                        } else {
                          //  Toast.makeText(ScheduleActivity.this, "Something wrong!!!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

        dialog.show();
    }


}