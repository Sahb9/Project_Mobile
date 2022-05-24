package com.android.myapplication.Fragment;

import android.app.Dialog;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
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

import com.android.myapplication.Activity.MainActivity;
import com.android.myapplication.DAO.HabitDAO;
import com.android.myapplication.DAO.HistoryDAO;
import com.android.myapplication.Entity.Habit;
import com.android.myapplication.Entity.History;
import com.android.myapplication.Models.CalendarAdapter;
import com.android.myapplication.R;
import com.android.myapplication.callback.CallBack;
import com.android.myapplication.entitys.HabitHomeItemsAdapter;
import com.android.myapplication.service.DateService;
import com.android.myapplication.utilities.Common;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment implements CalendarAdapter.OnItemListener {
    private final String CLASS_NAME = HomeFragment.class.getSimpleName();

    private TextView monthYearText;
    private RecyclerView calendarRecyclerView, items_habit;
    HabitHomeItemsAdapter habitHomeItemsAdapter;
    private MainActivity mainActivity;
    private LocalDate selectedDate;
    Button btnprevious, btnnext;
    private List<Habit> habitList = new ArrayList<>();

    public HomeFragment() {

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mainActivity = (MainActivity) getActivity();
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

    private boolean allowAddHabit(Habit habit, int dayOfWeek) {
        switch (dayOfWeek) {
            case 1:
                return habit.getAlarm().isMonday();
            case 2:
                return habit.getAlarm().isTuesday();
            case 3:
                return habit.getAlarm().isWednesday();
            case 4:
                return habit.getAlarm().isThursday();
            case 5:
                return habit.getAlarm().isFriday();
            case 6:
                return habit.getAlarm().isSaturday();
            case 7:
                return habit.getAlarm().isSunday();
            default:
                return false;
        }
    }

    private void setListDayOfWeek(HabitHomeItemsAdapter habitHomeAdapterParam, List<Habit> habitListParam, int dayOfWeek) {
        HabitDAO habitDAO = HabitDAO.getInstance();

        habitDAO.getHabits(Common.uID, new CallBack<Habit>() {
            @Override
            public void onCallBack(Habit callback) {
                if (allowAddHabit(callback, dayOfWeek)) {
                    habitListParam.add(callback);
                    habitHomeAdapterParam.notifyDataSetChanged();
                }
            }
        });
    }

    private void setUpSchedule() {
        this.selectedDate = LocalDate.now();
        setMonthView(this.habitHomeItemsAdapter);
    }

    private void initWidgets(View view) {
        calendarRecyclerView = view.findViewById(R.id.calendarRecyclerView_home);
        monthYearText = view.findViewById(R.id.monthYearTV_home);
        items_habit = view.findViewById(R.id.items_habit);
        btnprevious = view.findViewById(R.id.btn_fragmenthome_previous);
        btnnext = view.findViewById(R.id.btn_fragmenthome_next);

        this.items_habit.setLayoutManager(new LinearLayoutManager(getContext()));
        this.habitHomeItemsAdapter = new HabitHomeItemsAdapter(getLayoutInflater(), this.habitList);
        this.items_habit.setAdapter(this.habitHomeItemsAdapter);
    }

    private void setUpItemsHabit() {
        //LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        //items_habit.setLayoutManager(linearLayoutManager);
        //items_habit.setFocusable(false);
        //items_habit.setNestedScrollingEnabled(false);

        btnprevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                previousMonthAction_home(view);
            }
        });
        btnnext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nextMonthAction(view);
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void setMonthView(HabitHomeItemsAdapter habitHomeAdapterParam) {
        monthYearText.setText(monthYearFromDate(this.selectedDate));
        ArrayList<String> daysInMonth = daysInMonthArray(this.selectedDate);

        CalendarAdapter calendarAdapter = new CalendarAdapter(daysInMonth, new CalendarAdapter.OnItemListener() {
            @Override
            public void onItemClick(int position, String dayText) {
                habitList.clear();
                habitHomeAdapterParam.notifyDataSetChanged();

                //String message = "Selected Date " + dayText + " " + monthYearFromDate(selectedDate);
                if (!dayText.equals("")) {
                    int valueDate = Integer.parseInt(dayText);
                    int val = DateService.findDayofWeek(valueDate, Common.MONTH, Common.YEAR);

                    setListDayOfWeek(habitHomeAdapterParam, habitList, val);
                    Toast.makeText(mainActivity, "-" + val, Toast.LENGTH_LONG).show();
                }
            }
        });

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

        Common.MONTH = yearMonth.getMonth();
        Common.YEAR = yearMonth.getYear();
        for (int i = 1; i <= 42; i++) {
            if (i <= dayOfWeek || i > daysInMonth + dayOfWeek) {
                daysInMonthArray.add("");
            } else {
                daysInMonthArray.add(String.valueOf(i - dayOfWeek));
            }
        }

        return daysInMonthArray;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private String monthYearFromDate(LocalDate date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM yyyy");
        return date.format(formatter);
    }

    // nút tăng thêm tháng
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void previousMonthAction_home(View view) {
        this.selectedDate = this.selectedDate.minusMonths(1);
        setMonthView(this.habitHomeItemsAdapter);
    }

    // nút trừ thêm tháng
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void nextMonthAction(View view) {
        this.selectedDate = this.selectedDate.plusMonths(1);
        setMonthView(this.habitHomeItemsAdapter);
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

    @Override
    public void onItemClick(int position, String dayText) {
        if (!dayText.equals("")) {

        }
    }
}