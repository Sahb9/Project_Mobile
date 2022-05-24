package com.android.myapplication.DAO;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.android.myapplication.Entity.Habit;
import com.android.myapplication.callback.CallBack;
import com.android.myapplication.utilities.Common;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HabitDAO {
    private final String CLASS_NAME = HabitDAO.class.getSimpleName();
    private FirebaseDatabase firebaseDatabase;
    private static HabitDAO instance = null;

    private HabitDAO() {
        this.firebaseDatabase = FirebaseDatabase.getInstance();
    }

    public static HabitDAO getInstance() {
        if (instance == null) {
            instance = new HabitDAO();
        }

        return instance;
    }

    public void addHabit(String uId, Habit habit, CallBack<Boolean> callBack) {
        habit.setCurrent(0);

        this.firebaseDatabase.getReference(Common.HABIT).child(uId).push().setValue(habit)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        callBack.onCallBack(task.isSuccessful());
                    }
                });
    }

    public void getHabits(String uId, CallBack<Habit> callBack) {
        this.firebaseDatabase.getReference(Common.HABIT).child(uId).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Habit habit = snapshot.getValue(Habit.class);

                if (habit.getIdHabit() == null) {
                    habit.setIdHabit(snapshot.getKey());

                    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
                    firebaseDatabase.getReference(Common.HABIT).child(uId).child(snapshot.getKey()).setValue(habit);
                }

                callBack.onCallBack(habit);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void getHabitsByDayOfWeek(String uId, CallBack<Habit> callBack, int valueDay) {
        Boolean[] boolArray = new Boolean[9];
        Arrays.fill(boolArray, Boolean.FALSE);
        this.firebaseDatabase.getReference(Common.HABIT).child(uId).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Habit habit = snapshot.getValue(Habit.class);
                //boolArray[valueDay]=true;

                if (habit.getIdHabit() == null) {
                    habit.setIdHabit(snapshot.getKey());

                    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
                    firebaseDatabase.getReference(Common.HABIT).child(uId).child(snapshot.getKey()).setValue(habit);
                }
                boolean[] boolArray = new boolean[9];
                boolArray[1] = habit.getAlarm().isMonday();
                boolArray[2] = habit.getAlarm().isTuesday();
                boolArray[3] = habit.getAlarm().isWednesday();
                boolArray[4] = habit.getAlarm().isThursday();
                boolArray[5] = habit.getAlarm().isFriday();
                boolArray[6] = habit.getAlarm().isSaturday();
                boolArray[7] = habit.getAlarm().isSunday();
                if (boolArray[valueDay] == true) {
                    callBack.onCallBack(habit);
                }
                // callBack.onCallBack(habit);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void updateHabit(String uId, Habit habit, CallBack<Boolean> callBack) {
        this.firebaseDatabase.getReference(Common.HABIT).child(uId).child(habit.getIdHabit()).setValue(habit)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        callBack.onCallBack(task.isSuccessful());
                    }
                });
    }

    public void deleteHabit(String uId, Habit habit, CallBack<Void> callBack) {
        this.firebaseDatabase.getReference(Common.HABIT).child(uId).child(habit.getIdHabit()).removeValue(new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                callBack.onCallBack(null);
            }
        });
    }
}
