package com.habitdark.myapplication.Fragment;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.habitdark.myapplication.Activity.MainActivity;
import com.habitdark.myapplication.DAO.HistoryDAO;
import com.habitdark.myapplication.Entity.History;
import com.habitdark.myapplication.R;
import com.habitdark.myapplication.callback.CallBack;
import com.habitdark.myapplication.Adapter.HistoryAdapter;
import com.habitdark.myapplication.utilities.Common;

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