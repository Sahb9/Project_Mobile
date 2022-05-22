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
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class HabitDAO {
    private FirebaseDatabase firebaseDatabase;
    private ArrayList<Habit> habitList;
    private static  HabitDAO instance = null;

    private HabitDAO() {
        this.firebaseDatabase = FirebaseDatabase.getInstance();
        this.habitList = new ArrayList<Habit>();
    }

    public static HabitDAO getInstance() {
        if (instance == null) {
            instance = new HabitDAO();
        }

        return instance;
    }

    public ArrayList<Habit> getHabitList() {
        return habitList;
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

    public void getHabits(String uId,CallBack<Habit> callBack) {
        this.firebaseDatabase.getReference(Common.HABIT).child(uId).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Habit habit = snapshot.getValue(Habit.class);

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
}
