package com.android.myapplication.entitys;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.myapplication.Entity.Habit;
import com.android.myapplication.Entity.History;
import com.android.myapplication.R;

import java.util.List;

public class HistoryAdapter extends BaseAdapter {
    private Context context;
    private List<History> historyList;
    public  HistoryAdapter ()
    {

    }
    public HistoryAdapter(Context context, List<History> historyList) {
        this.context = context;
        this.historyList = historyList;
    }

    @Override
    public int getCount() {
        if (this.historyList != null && this.historyList.size() > 0) {
            return this.historyList.size();
        }
        return 0;
    }

    @Override
    public Object getItem(int i) {
        return this.historyList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        HistoryAdapterHolder historyAdapterHolder;
        History history = this.historyList.get(i);
        if (view == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = layoutInflater.inflate(R.layout.view_history, null); //items
            historyAdapterHolder = new HistoryAdapterHolder();

            historyAdapterHolder.txtSubject = view.findViewById(R.id.textInputSubject);
            historyAdapterHolder.txtDate = view.findViewById(R.id.textInputDate);
            historyAdapterHolder.txtCurrentTimes = view.findViewById(R.id.textInputCurrent);


            view.setTag(historyAdapterHolder);
        } else {
            historyAdapterHolder = (HistoryAdapter.HistoryAdapterHolder) view.getTag();
        }
        historyAdapterHolder.txtSubject.setText(history.getSubject());
        historyAdapterHolder.txtDate.setText(history.getDataTime());
        historyAdapterHolder.txtCurrentTimes.setText(String.valueOf(history.getCurrent()) );

        return view;
    }
    public class HistoryAdapterHolder {
        TextView txtSubject,txtCurrentTimes,txtDate;
    }
}
