package com.habitdark.myapplication.Fragment;

import android.app.Dialog;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.habitdark.myapplication.Activity.MainActivity;
import com.habitdark.myapplication.DAO.HabitDAO;
import com.habitdark.myapplication.DAO.HistoryDAO;
import com.habitdark.myapplication.Entity.Habit;
import com.habitdark.myapplication.Entity.History;
import com.habitdark.myapplication.Adapter.CalendarAdapter;
import com.habitdark.myapplication.R;
import com.habitdark.myapplication.callback.CallBack;
import com.habitdark.myapplication.Adapter.HabitHomeItemsAdapter;
import com.habitdark.myapplication.service.DateService;
import com.habitdark.myapplication.utilities.Common;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoField;
import java.util.ArrayList;

public class HomeFragment extends Fragment implements CalendarAdapter.OnItemListener {
    private final String CLASS_NAME = HomeFragment.class.getSimpleName();
    private TextView monthYearText;
    private RecyclerView calendarRecyclerView;
    HabitHomeItemsAdapter habitHomeItemsAdapter;
    private MainActivity mainActivity;
    private LocalDate selectedDate;
    Button btnprevious, btnnext;
    private ListView listViewHabit;
    private HabitHomeItemsAdapter habitAdapter;
    public ArrayList<Habit> habitArrayList;
    public ArrayList<History> historyArrayList;
    public HomeFragment() {

    }

    //cái này chạy sau
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }
    //cái này chạy trc
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        mainActivity = (MainActivity) getActivity();
        //getListHisory(historyArrayList);
        initWidgets(view);
        setUpSchedule();
        setUpItemsHabit();
        init(view);
        initListView();
        //setListView(this.habitArrayList, this.habitAdapter);
        //Set theo ngày
        setListViewByDayOfWeek(this.habitArrayList, this.habitAdapter);
        return view;
    }

    private void init(View view) {

        this.listViewHabit = view.findViewById(R.id.items_habit);
        this.habitArrayList = new ArrayList<>();
        this.historyArrayList = new ArrayList<>();
        getListHisory(this.historyArrayList);
    }

    private void initListView() {
        this.habitAdapter = new HabitHomeItemsAdapter(getActivity(), this.habitArrayList,this.historyArrayList);
        this.listViewHabit.setAdapter(this.habitAdapter);
    }
    //check 1 element
    private void getListHisory(ArrayList<History> historyArrayList) {
        HistoryDAO historyDAO = HistoryDAO.getInstance();
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseDatabase.getReference(Common.HISTORY).child(Common.uID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot temp : snapshot.getChildren()) {
                    History history = temp.getValue(History.class);
                    historyArrayList.add(history);
                   //System.out.println("size of DAO "+ historyArrayList.size());

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
       // System.out.println("size of after DAO "+ historyArrayList.size());

    }
    private void setListView(ArrayList<Habit> habitsParam, HabitHomeItemsAdapter habitAdapterParam) {
        HabitDAO habitDAO = HabitDAO.getInstance();

        habitDAO.getHabits(Common.uID, new CallBack<Habit>() {
            @Override
            public void onCallBack(Habit callback) {
                habitsParam.add(callback);
                habitAdapterParam.notifyDataSetChanged();
            }
        });
    }

    private void setListViewByDayOfWeek(ArrayList<Habit> habitsParam, HabitHomeItemsAdapter habitAdapterParam) {

        HabitDAO habitDAO = HabitDAO.getInstance();
        LocalDate datefix = LocalDate.of(Common.YEAR, Common.MONTH, Common.DAY_OF_MONTH);
        int kt=1;
        for (History items : historyArrayList) {
            String dateString= items.getDataTime();
            String[] cutText = dateString.split("/");
            int ngay= Integer.parseInt(cutText[0]);
            int thang= Integer.parseInt(cutText[1]);
            int nam= Integer.parseInt(cutText[2]);
            LocalDate date2 = LocalDate.of(nam, thang, ngay);
            if((date2.equals(datefix)) )
            {
                kt=0;
            }
        }
        habitDAO.getHabitsByDayOfWeek(Common.uID, new CallBack<Habit>() {
            @Override
            public void onCallBack(Habit callback) {
                habitsParam.add(callback);
                habitAdapterParam.notifyDataSetChanged();
            }
        }, Common.DAY_OF_WEEK,kt);
    }

    private void setUpSchedule() {
        this.selectedDate = LocalDate.now();
        //Save for the first time presenting screen
        Common.DAY_OF_WEEK= this.selectedDate.getDayOfWeek().getValue();
        setMonthView();
    }

    private void initWidgets(View view) {
        calendarRecyclerView = view.findViewById(R.id.calendarRecyclerView_home);
        monthYearText = view.findViewById(R.id.monthYearTV_home);
        btnprevious = view.findViewById(R.id.btn_fragmenthome_previous);
        btnnext = view.findViewById(R.id.btn_fragmenthome_next);
    }

    private void setUpItemsHabit() {

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
    private void setMonthView() {
        monthYearText.setText(monthYearFromDate(this.selectedDate));
        ArrayList<String> daysInMonth = daysInMonthArray(this.selectedDate);

        CalendarAdapter calendarAdapter = new CalendarAdapter(daysInMonth, new CalendarAdapter.OnItemListener() {
            // Press certain dates
            @Override
            public void onItemClick(int position, String dayText) {
                if (!dayText.equals("")) {
                    int valueDateOf = Integer.parseInt(dayText);
                    int val = DateService.findDayofWeek(valueDateOf, Common.MONTH, Common.YEAR);
                    Common.DAY_OF_WEEK = val;
                    Common.DAY_OF_MONTH = valueDateOf;
                    habitArrayList.clear();
                    habitAdapter.notifyDataSetChanged();
                    setListViewByDayOfWeek(habitArrayList, habitAdapter);
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
        Common.DAY_OF_MONTH = this.selectedDate.getDayOfMonth();
        Common.MONTH_OF_YEAR = yearMonth.get(ChronoField.MONTH_OF_YEAR);
        Common.MONTH = yearMonth.getMonth(); // get month by String (Month)
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

    @Override
    public void onItemClick(int position, String dayText) {
        if (!dayText.equals("")) {

        }
    }


}