package com.android.myapplication.entitys;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.android.myapplication.DAO.HabitDAO;
import com.android.myapplication.Entity.Habit;
import com.android.myapplication.R;

import java.util.List;

public class HabitHomeItemsAdapter extends RecyclerView.Adapter<HabitHomeItemsAdapter.HabitHomeItemsAdapterViewHolder> {
    private LayoutInflater layoutInflater;
    private List<Habit> habitList;

    public HabitHomeItemsAdapter() {}

    public HabitHomeItemsAdapter(LayoutInflater layoutInflater, List<Habit> habitList) {
        this.layoutInflater = layoutInflater;
        this.habitList = habitList;
    }

    @Override
    public void onBindViewHolder(@NonNull HabitHomeItemsAdapterViewHolder holder, int position) {
        Habit habit = this.habitList.get(position);

        holder.txtName.setText(habit.getName());

        holder.txtHinhAnh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.txtHinhAnh.setImageResource(R.drawable.exclamationmark);
            }
        });
    }

    @Override
    public int getItemCount() {
        return this.habitList.size();
    }


    @NonNull
    @Override
    public HabitHomeItemsAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.view_habit, parent, false);

        return new HabitHomeItemsAdapterViewHolder(view);
    }

    public class HabitHomeItemsAdapterViewHolder extends RecyclerView.ViewHolder {
        TextView txtName;
        ImageView txtHinhAnh;

        public HabitHomeItemsAdapterViewHolder(@NonNull View itemView) {
            super(itemView);

            txtName = itemView.findViewById(R.id.textHabitTitle);
            txtHinhAnh = itemView.findViewById(R.id.texthabitimg);

        }
    }
}
