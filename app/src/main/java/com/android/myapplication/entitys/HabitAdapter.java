package com.android.myapplication.entitys;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.android.myapplication.Entity.Habit;
import com.android.myapplication.R;
import com.android.myapplication.utilities.Common;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class HabitAdapter extends BaseAdapter {
    private Context context;
    private int layout;
    private List<Habit> habitList;
    private int[] themes = {R.drawable.gradient_button1, R.drawable.gradient_button2, R.drawable.gradient_button3};
    private Random random = new Random();

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
        HabitAdapterHolder habitAdapterHolder;
        Habit habit = this.habitList.get(i);

        if (view == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = layoutInflater.inflate(this.layout, null);

            habitAdapterHolder = new HabitAdapterHolder();

            habitAdapterHolder.linearLayout = view.findViewById(R.id.habit_item);
            habitAdapterHolder.textViewName = view.findViewById(R.id.habit_name);
            habitAdapterHolder.textViewProgress = view.findViewById(R.id.txt_habit_progress);
            habitAdapterHolder.progressBarHabit = view.findViewById(R.id.progress_circular);
            habitAdapterHolder.position = this.random.nextInt(3);

            float progressResult = ((float) habit.getCurrent() / habit.getTarget()) * 100;

            habitAdapterHolder.progress = (int) progressResult;

            view.setTag(habitAdapterHolder);
        } else {
            habitAdapterHolder = (HabitAdapterHolder) view.getTag();
        }

        habitAdapterHolder.linearLayout.setBackgroundResource(this.themes[habitAdapterHolder.position]);
        habitAdapterHolder.textViewName.setText(habit.getName());

        habitAdapterHolder.textViewProgress.setText(habitAdapterHolder.progress + "%");
        habitAdapterHolder.progressBarHabit.setProgress(habitAdapterHolder.progress);

        return view;
    }
}
