package com.android.myapplication.DAO;

import androidx.annotation.NonNull;

import com.android.myapplication.Entity.Habit;
import com.android.myapplication.callback.CallBack;
import com.android.myapplication.utilities.Common;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.FirebaseDatabase;

public class HabitDAO {
    private FirebaseDatabase firebaseDatabase;
    private static  HabitDAO instance = null;

    public static HabitDAO getInstance() {
        if (instance == null) {
            instance = new HabitDAO();
        }

        return instance;
    }

    private HabitDAO() {
        this.firebaseDatabase = FirebaseDatabase.getInstance();
    }

    public void addHabit(String uId, Habit habit, CallBack<Boolean> callBack) {
        this.firebaseDatabase.getReference(Common.HABIT).child(uId).push().setValue(habit)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        callBack.onCallBack(task.isSuccessful());
                    }
                });
    }
}
