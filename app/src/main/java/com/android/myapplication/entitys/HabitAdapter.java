package com.android.myapplication.entitys;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.myapplication.Entity.Habit;
import com.android.myapplication.Fragment.HabitsFragment;
import com.android.myapplication.R;

import org.w3c.dom.Text;

import java.util.List;

public class HabitAdapter extends BaseAdapter {
    private Context context;
    private int layout;
    private List<Habit> habitList;

    public HabitAdapter(Context context, int layout, List<Habit> habitList) {
        this.context = context;
        this.layout = layout;
        this.habitList = habitList;
    }

    // Remember method getCount, that important
    // Adapter know many item to render
    @Override
    public int getCount() {
        return this.habitList.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater layoutInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = layoutInflater.inflate(this.layout, null);

        TextView textViewName = (TextView) view.findViewById(R.id.habit_name);
        TextView textViewProgress = view.findViewById(R.id.txt_habit_progress);
        ProgressBar progressBarHabit = view.findViewById(R.id.progress_circular);

        Habit habit = this.habitList.get(i);

        textViewName.setText(habit.getName());
        textViewProgress.setText("50%");
        progressBarHabit.setProgress(50);

        return view;
    }
}
