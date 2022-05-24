package com.android.myapplication.entitys;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.android.myapplication.DAO.HabitDAO;
import com.android.myapplication.Entity.Habit;
import com.android.myapplication.R;
import com.android.myapplication.callback.CallBack;
import com.android.myapplication.utilities.Common;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;
import java.util.Random;

public class HabitHomeItemsAdapter extends BaseAdapter {
    private Context context;

    private List<Habit> habitList;
    private int[] themes = {R.drawable.gradient_button1, R.drawable.gradient_button2, R.drawable.gradient_button3};
    private Random random = new Random();

    public HabitHomeItemsAdapter(Context context, List<Habit> habitList) {
        this.context = context;
        this.habitList = habitList;
    }

    public HabitHomeItemsAdapter() {

    }

    // Remember method getCount, that important
    // Adapter know many item to render
    @Override
    public int getCount() {
        if (this.habitList != null && this.habitList.size() > 0) {
            return this.habitList.size();
        }
        return 0;
    }

    @Override
    public Object getItem(int i) {
        return this.habitList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    public class HabitHomeAdapterHolder {
        ImageView img_items;
        TextView txtTitle;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        HabitHomeAdapterHolder habitAdapterHolder;

        Habit habit = this.habitList.get(i);

        if (view == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = layoutInflater.inflate(R.layout.view_habit, null); //items
            habitAdapterHolder = new HabitHomeAdapterHolder();

            habitAdapterHolder.txtTitle = view.findViewById(R.id.textHabitTitle);
            habitAdapterHolder.img_items = view.findViewById(R.id.textHabitImage);

            view.setTag(habitAdapterHolder);
        } else {
            habitAdapterHolder = (HabitHomeAdapterHolder) view.getTag();
        }
        habitAdapterHolder.txtTitle.setText(habit.getName());
        habitAdapterHolder.img_items.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                habitAdapterHolder.img_items.setImageResource(R.drawable.check);
            }
        });
        return view;
    }
}
