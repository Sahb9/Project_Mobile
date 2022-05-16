package com.android.myapplication.entitys;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.android.myapplication.R;

public class HabitHomeItemsAdapter extends RecyclerView.Adapter<HabitHomeItemsAdapter.HabitHomeItemsAdapterViewHolder>{

    public HabitHomeItemsAdapter()
    {

    }
    @Override
    public void onBindViewHolder(@NonNull HabitHomeItemsAdapterViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }


    @NonNull
    @Override
    public HabitHomeItemsAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_habit,parent,false);

        return new HabitHomeItemsAdapterViewHolder(view);
    }

    public class HabitHomeItemsAdapterViewHolder extends RecyclerView.ViewHolder
    {
        TextView txtTen,txtNgay,txtHinhAnh;

        public HabitHomeItemsAdapterViewHolder(@NonNull View itemView) {
            super(itemView);
            txtTen = itemView.findViewById(R.id.texthabitdate);
            txtNgay =itemView.findViewById(R.id.texthabitname);
            txtHinhAnh = itemView.findViewById(R.id.texthabitimg);
        }
    }
}
