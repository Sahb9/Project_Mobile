package com.android.myapplication.Fragment;

import android.app.Dialog;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.myapplication.Activity.ScheduleActivity;
import com.android.myapplication.DAO.HistoryDAO;
import com.android.myapplication.Entity.History;
import com.android.myapplication.Models.CalendarAdapter;
import com.android.myapplication.R;
import com.android.myapplication.callback.CallBack;
import com.android.myapplication.entitys.HabitHomeItemsAdapter;
import com.android.myapplication.utilities.Common;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class HomeFragment extends Fragment {
    private TextView monthYearText;
    private RecyclerView calendarRecyclerView,items_habit;
    HabitHomeItemsAdapter habitHomeItemsAdapter;
    private LocalDate selectedDate;
    public HomeFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initWidgets(view);
        setUpSchedule();
        setUpItemsHabit();
    }
    private void setUpSchedule()
    {
        this.selectedDate = LocalDate.now();
        setMonthView();
    }
    private void initWidgets(View view) {
        calendarRecyclerView = view.findViewById(R.id.calendarRecyclerView);
        monthYearText = view.findViewById(R.id.monthYearTV);
        items_habit = view.findViewById(R.id.items_habit);
    }
    private void setUpItemsHabit()
    {
        habitHomeItemsAdapter = new HabitHomeItemsAdapter();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        items_habit.setLayoutManager(linearLayoutManager);
        items_habit.setFocusable(false);
        items_habit.setNestedScrollingEnabled(false);
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    private void setMonthView() {
        monthYearText.setText(monthYearFromDate(this.selectedDate));
        ArrayList<String> daysInMonth = daysInMonthArray(this.selectedDate);
        CalendarAdapter calendarAdapter = new CalendarAdapter(daysInMonth, getActivity() );
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getContext(), 7);

        calendarRecyclerView.setLayoutManager(layoutManager);
        calendarRecyclerView.setAdapter(calendarAdapter);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private ArrayList<String> daysInMonthArray(LocalDate date) {
        ArrayList<String> daysInMonthArray = new ArrayList<>();

        YearMonth yearMonth = YearMonth.from(date);

        int daysInMonth = yearMonth.lengthOfMonth();
        LocalDate firstOfMonth = this.selectedDate.withDayOfMonth(1);
        int dayOfWeek = firstOfMonth.getDayOfWeek().getValue();
        //set vào common
        Common.DAY = this.selectedDate.getDayOfMonth();
        Common.MONTH = yearMonth.getMonth();
        Common.YEAR = yearMonth.getYear();
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
    // nút tăng thêm tháng
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void previousMonthAction(View view) {
        this.selectedDate = this.selectedDate.minusMonths(1);
        setMonthView();
    }
    // nút trừ thêm tháng
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void nextMonthAction(View view) {
        this.selectedDate = this.selectedDate.plusMonths(1);
        setMonthView();
    }



    @RequiresApi(api = Build.VERSION_CODES.O)
    private String formatDateTime(String format, LocalDate localDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
        return localDate.format(formatter);
    }

    private void handleDialog(LocalDate paramLocalDate) {
        final Dialog dialog = new Dialog(getActivity());
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
                            Toast.makeText(getActivity(), "Save complete", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getActivity(), "Something wrong!!!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

        dialog.show();
    }
}