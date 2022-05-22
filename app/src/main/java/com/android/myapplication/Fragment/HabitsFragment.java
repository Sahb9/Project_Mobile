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

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.android.myapplication.Activity.MainActivity;
import com.android.myapplication.DAO.HabitDAO;
import com.android.myapplication.Entity.Alarm;
import com.android.myapplication.Entity.Habit;
import com.android.myapplication.R;
import com.android.myapplication.callback.CallBack;
import com.android.myapplication.service.AlarmReceiver;
import com.android.myapplication.entitys.HabitAdapter;
import com.android.myapplication.utilities.Common;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

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
    private Intent intent;

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

        //Lazy dialog
        showDialog();

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

    public Intent getMyIntent(Context context, Class<?> cls) {
        if (this.intent == null) {
            this.intent = new Intent(context, cls);
        }
        return this.intent;
    }

    private String FormatDate(String format, Date date) {
        return new SimpleDateFormat(format).format(date);
    }

    private PendingIntent handlePendingIntent(Context context, Intent intent) {
        return PendingIntent.getBroadcast(context, 1, intent, 0);
    }

    private void updateUI(Calendar calendar, TextView textView) {
        String dateText = FormatDate("dd/MM/yyyy HH:mm:ss", calendar.getTime());
        textView.setText(dateText);
    }

    private void handlePressed(AppCompatButton appCompatButton, CallBack<Boolean> callBack) {
        appCompatButton.setSelected(!appCompatButton.isSelected());
        callBack.onCallBack(appCompatButton.isSelected());

        Log.d(Common.TAG_LOG, "handlePressed: " + appCompatButton.isSelected());
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void starAlarm(Calendar calendar) {
        AlarmManager alarmManager = (AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);
        PendingIntent pendingIntent = null;

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.Q) {
            pendingIntent = handlePendingIntent(getActivity(),
                    getMyIntent(getActivity(), AlarmReceiver.class));
        }

        //Intent intent = new Intent(MainActivity.this, AlarmReceiver.class);

        if (calendar.before(Calendar.getInstance())) {
            calendar.add(Calendar.DATE, 1);
        }

        alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
    }

    private void handleSetTime(Alarm alarm, TextView textView) {
        Calendar calendar = Calendar.getInstance();

        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onTimeSet(TimePicker timePicker, int i, int i1) {
                calendar.set(Calendar.HOUR_OF_DAY, i);
                calendar.set(Calendar.MINUTE, i1);
                calendar.set(Calendar.SECOND, 0);

                String dateText = null;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    dateText = FormatDate("dd/MM/yyyy HH:mm:ss", calendar.getTime());
                }

                Log.d(Common.TAG_LOG, "onTimeSet: " + dateText);

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    updateUI(calendar, textView);
                }
                //starAlarm(calendar);
                alarm.setId(new Random().nextInt(Integer.MAX_VALUE));
                alarm.setHour(calendar.get(Calendar.HOUR_OF_DAY));
                alarm.setMinute(calendar.get(Calendar.MINUTE));

                alarm.handleAlarm(getActivity(), calendar);
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

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference();

        Alarm alarm = new Alarm();
        AppCompatButton btnMon, btnTue, btnWed, btnThu, btnFri, btnSat, btnSun;

        btnMon = dialog.findViewById(R.id.btn_mon);
        btnTue = dialog.findViewById(R.id.btn_tue);
        btnWed = dialog.findViewById(R.id.btn_wed);
        btnThu = dialog.findViewById(R.id.btn_thu);
        btnFri = dialog.findViewById(R.id.btn_fri);
        btnSat = dialog.findViewById(R.id.btn_sat);
        btnSun = dialog.findViewById(R.id.btn_sun);
        EditText editTextNameHabit = dialog.findViewById(R.id.edit_name_habit);
        EditText editTextTargetHabit = dialog.findViewById(R.id.edit_target_habit);
        TextView txtTimePicker = dialog.findViewById(R.id.reminder_habit);
        Button btnCancel = dialog.findViewById(R.id.btn_cancel);
        Button btnSave = dialog.findViewById(R.id.btn_save);

        btnMon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handlePressed(btnMon, new CallBack<Boolean>() {
                    @Override
                    public void onCallBack(Boolean value) {
                        alarm.setMonday(value);
                    }
                });
            }
        });

        btnTue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handlePressed(btnTue, new CallBack<Boolean>() {
                    @Override
                    public void onCallBack(Boolean value) {
                        alarm.setTuesday(value);
                    }
                });
            }
        });

        btnWed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handlePressed(btnWed, new CallBack<Boolean>() {
                    @Override
                    public void onCallBack(Boolean value) {
                        alarm.setWednesday(value);
                    }
                });
            }
        });

        btnThu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handlePressed(btnThu, new CallBack<Boolean>() {
                    @Override
                    public void onCallBack(Boolean value) {
                        alarm.setThursday(value);
                    }
                });
            }
        });

        btnFri.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handlePressed(btnFri, new CallBack<Boolean>() {
                    @Override
                    public void onCallBack(Boolean value) {
                        alarm.setFriday(value);
                    }
                });
            }
        });

        btnSat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handlePressed(btnSat, new CallBack<Boolean>() {
                    @Override
                    public void onCallBack(Boolean value) {
                        alarm.setSaturday(value);
                    }
                });
            }
        });

        btnSun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handlePressed(btnSun, new CallBack<Boolean>() {
                    @Override
                    public void onCallBack(Boolean value) {
                        alarm.setSunday(value);
                    }
                });
            }
        });

        txtTimePicker.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View view) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    handleSetTime(alarm, txtTimePicker);
                }
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HabitDAO habitDAO = HabitDAO.getInstance();
                Habit habit = new Habit();

                habit.setName(editTextNameHabit.getText().toString());

                int numTarget = Integer.parseInt(editTextTargetHabit.getText().toString());

                Log.d(Common.TAG_LOG, "onClick: Dialog add habit target-" + numTarget);

                habit.setTarget(numTarget);
                habit.setAlarm(alarm);

                Log.d(Common.TAG_LOG, "onClick: Dialog add habit / id-" + Common.uID);

                habitDAO.addHabit(Common.uID, habit, new CallBack<Boolean>() {
                    @Override
                    public void onCallBack(Boolean callback) {
                        if (callback) {
                            Toast.makeText(getActivity(), "Save habit is success", Toast.LENGTH_SHORT).show();

                            Log.d(Common.TAG_LOG, "onClick: " + alarm.isMonday() + "/hour: " + alarm.getHour());
                        } else {
                            Toast.makeText(getActivity(), "Save habit is fail", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

                dialog.cancel();
            }
        });

        dialog.show();
    }

    private void addEvent() {
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog();
            }
        });
    }
}