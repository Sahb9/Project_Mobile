package com.android.myapplication.Fragment;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentStatePagerAdapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.android.myapplication.Activity.MainActivity;
import com.android.myapplication.DAO.HabitDAO;
import com.android.myapplication.DAO.HistoryDAO;
import com.android.myapplication.Entity.Habit;
import com.android.myapplication.Entity.History;
import com.android.myapplication.R;
import com.android.myapplication.callback.CallBack;
import com.android.myapplication.entitys.HabitAdapter;
import com.android.myapplication.entitys.HabitHomeItemsAdapter;
import com.android.myapplication.entitys.HistoryAdapter;
import com.android.myapplication.utilities.Common;

import java.util.ArrayList;


public class HistoryFragment extends Fragment {
    public Context context;
    private MainActivity mainActivity;
    private ListView listViewHistory;
    public ArrayList<History> historyArrayList;
    private HistoryAdapter historyAdapter;
    public HistoryFragment(Context context) {
        this.context = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_history, container, false);
        mainActivity = (MainActivity) getActivity();
        init(view);
        initListView();
        setListView(this.historyArrayList,this.historyAdapter);

        return view;
    }
    private void init(View view) {
        this.listViewHistory = view.findViewById(R.id.items_history);
        this.historyArrayList = new ArrayList<>();
    }
    private void initListView() {
        this.historyAdapter = new HistoryAdapter(getActivity(), this.historyArrayList);
        this.listViewHistory.setAdapter(this.historyAdapter);
    }
    private void setListView(ArrayList<History> historyArrayList, HistoryAdapter historyAdapter) {
        HistoryDAO historyDAO = HistoryDAO.getInstance();
        historyDAO.getHistory(Common.uID, new CallBack<History>() {
            @Override
            public void onCallBack(History callback) {
                historyArrayList.add(callback);
                historyAdapter.notifyDataSetChanged();
            }
        });
    }
}