package com.android.myapplication.Fragment;

import android.app.AlarmManager;
import android.app.Dialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.android.myapplication.Entity.Habit;
import com.android.myapplication.R;
import com.android.myapplication.service.AlarmReceiver;
import com.android.myapplication.entitys.HabitAdapter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HabitsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
@RequiresApi(api = Build.VERSION_CODES.Q)
public class HabitsFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private AppCompatButton btnAdd;
    private ListView listViewHabit;
    private ArrayList<Habit> habits;
    private HabitAdapter habitAdapter;

    public HabitsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HabitsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HabitsFragment newInstance(String param1, String param2) {
        HabitsFragment fragment = new HabitsFragment();
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_habits, container, false);

        init(view);
        setListView();
        addEvent();

        return view;
    }

    private void init(View view) {
        this.listViewHabit = view.findViewById(R.id.list_item_habit);
        this.btnAdd = view.findViewById(R.id.btn_add_habit);

        this.habits = new ArrayList<>();

        this.habits.add(new Habit("Mẫu list view cho habit"));
        this.habits.add(new Habit("Kiểm tra giao diện UI"));
        this.habits.add(new Habit("Chỉnh sửa item cho habit list"));
        this.habits.add(new Habit("Kiểm tra lần nữa"));
        this.habits.add(new Habit("Thêm thuộc tính cho lớp habit"));
        this.habits.add(new Habit("Kiểm tra giao diện danh sách"));
        this.habits.add(new Habit("Thêm các thông tin các trường hợp cho danh sách"));
        this.habits.add(new Habit("Tạo dialog để thêm habit"));
    }

    private void setListView() {
        this.habitAdapter = new HabitAdapter(getActivity(), R.layout.element_habit, this.habits);

        this.listViewHabit.setAdapter(this.habitAdapter);
    }

    private void addEvent() {
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog();
            }
        });
    }

    private void handlePressed(AppCompatButton appCompatButton) {
        appCompatButton.setSelected(!appCompatButton.isSelected());
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void SelectRemind(TextView textView) {
        Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DATE);
        int month = calendar.get(Calendar.MONTH);
        int year = calendar.get(Calendar.YEAR);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        AlarmManager alarmManager = (AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);

        Intent intent = new Intent(getActivity(), AlarmReceiver.class);

        TimePickerDialog timePickerDialog = new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int i, int i1) {
                SimpleDateFormat simpleFormatter = new SimpleDateFormat("dd/MM/yyyy HH:mm");
                calendar.set(year, month, day, i, i1);

                textView.setText(simpleFormatter.format(calendar.getTime()));

                PendingIntent pendingIntent =
                        PendingIntent.getBroadcast(getActivity(), 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

                alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
            }
        }, hour, minute, true);

        timePickerDialog.show();
    }

    private void showDialog() {
        final Dialog dialog = new Dialog(getActivity());

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.habit_add_dialog);

        Window window = dialog.getWindow();
        if (window == null) {
            return;
        }

        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        AppCompatButton btnMon, btnTue, btnWed, btnThu, btnFri, btnSat, btnSun;

        btnMon = dialog.findViewById(R.id.btn_mon);
        btnTue = dialog.findViewById(R.id.btn_tue);
        btnWed = dialog.findViewById(R.id.btn_wed);
        btnThu = dialog.findViewById(R.id.btn_thu);
        btnFri = dialog.findViewById(R.id.btn_fri);
        btnSat = dialog.findViewById(R.id.btn_sat);
        btnSun = dialog.findViewById(R.id.btn_sun);
        TextView txtTimePicker = dialog.findViewById(R.id.reminder_habit);
        Button btnCancel = dialog.findViewById(R.id.btn_cancel);
        Button btnSave = dialog.findViewById(R.id.btn_save);

        btnMon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handlePressed(btnMon);
            }
        });

        btnTue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handlePressed(btnTue);
            }
        });

        btnWed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handlePressed(btnWed);
            }
        });

        btnThu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handlePressed(btnThu);
            }
        });

        btnFri.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handlePressed(btnFri);
            }
        });

        btnSat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handlePressed(btnSat);
            }
        });

        btnSun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handlePressed(btnSun);
            }
        });

        txtTimePicker.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View view) {
                SelectRemind(txtTimePicker);
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "Save habit", Toast.LENGTH_SHORT).show();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }
}