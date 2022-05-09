package com.android.myapplication.DAO;

import androidx.annotation.NonNull;

import com.android.myapplication.Entity.History;
import com.android.myapplication.callback.CallBack;
import com.android.myapplication.utilities.Common;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.FirebaseDatabase;

public class HistoryDAO {
    private FirebaseDatabase firebaseDatabase;
    private static HistoryDAO instance = null;

    private HistoryDAO() {}

    public static HistoryDAO getInstance() {
        if (instance == null) {
            instance = new HistoryDAO();
        }

        return instance;
    }

    public void addHistory(String uId, History history, CallBack<Boolean> callBack) {
        this.firebaseDatabase = FirebaseDatabase.getInstance();

        this.firebaseDatabase.getReference(Common.HISTORY).child(uId).push().setValue(history)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                callBack.onCallBack(task.isSuccessful());
            }
        });
    }

}
