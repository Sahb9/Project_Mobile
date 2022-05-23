package com.android.myapplication.entitys;

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

public class HabitHomeItemsAdapter extends RecyclerView.Adapter<HabitHomeItemsAdapter.HabitHomeItemsAdapterViewHolder>{

    HabitDAO habitDAO;
    public HabitHomeItemsAdapter()
    {
        habitDAO = new HabitDAO();
    }
    @Override
    public void onBindViewHolder(@NonNull HabitHomeItemsAdapterViewHolder holder, int position) {
        //


        //
        holder.txtHinhAnh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.txtHinhAnh.setImageResource(R.drawable.exclamationmark);
            }
        });
    }

    @Override
    public int getItemCount() {

        return habitDAO.getHabitList().size();
    }


    @NonNull
    @Override
    public HabitHomeItemsAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_habit,parent,false);

        return new HabitHomeItemsAdapterViewHolder(view);
    }

    public class HabitHomeItemsAdapterViewHolder extends RecyclerView.ViewHolder
    {
        TextView txtNgay;
        ImageView txtHinhAnh;
        public HabitHomeItemsAdapterViewHolder(@NonNull View itemView) {
            super(itemView);

            txtNgay =itemView.findViewById(R.id.textHabitTitle);
            txtHinhAnh = itemView.findViewById(R.id.texthabitimg);

        }
    }

}
