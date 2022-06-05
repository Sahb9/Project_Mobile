package com.android.myapplication.DAO;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.android.myapplication.Entity.Habit;
import com.android.myapplication.Entity.History;
import com.android.myapplication.callback.CallBack;
import com.android.myapplication.utilities.Common;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class HistoryDAO {
    private FirebaseDatabase firebaseDatabase;
    private static HistoryDAO instance = null;
    public List<History> historyList ;
    private HistoryDAO() {
        this.firebaseDatabase = FirebaseDatabase.getInstance();
    }

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
    public void getHistory(String uId, CallBack<History> callBack) {

        this.firebaseDatabase.getReference(Common.HISTORY).child(uId).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                History history = snapshot.getValue(History.class);
                callBack.onCallBack(history);
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
       // System.out.println("size of after DAO "+ historyList.size());

    }
    public ArrayList<History> getListHistory(String uId, CallBack<History> callBack) {
        ArrayList<History> historyListTemp =  new ArrayList<History>();
        this.firebaseDatabase.getReference(Common.HISTORY).child(uId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot temp : snapshot.getChildren()) {
                    History history = temp.getValue(History.class);
                    historyListTemp.add(history);
                  //  callBack.onCallBack(history);
                  //  System.out.println("size of DAO "+ historyListTemp.size());

                }
               // System.out.println("size of after DAO "+ historyListTemp.size());

              //  return historyListTemp;
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return historyListTemp;
    }
//    public void addHistory(String uId, History history, CallBack<Boolean> callBack) {
//
//
//        this.firebaseDatabase.getReference(Common.HABIT).child(uId).push().setValue(habit)
//                .addOnCompleteListener(new OnCompleteListener<Void>() {
//                    @Override
//                    public void onComplete(@NonNull Task<Void> task) {
//                        callBack.onCallBack(task.isSuccessful());
//                    }
//                });
//    }

}
