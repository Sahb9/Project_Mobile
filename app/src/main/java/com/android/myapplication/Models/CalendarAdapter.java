package com.android.myapplication.Models;

import android.content.Context;
import android.os.storage.OnObbStateChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.android.myapplication.R;
import com.android.myapplication.utilities.Common;

import java.util.ArrayList;

public class CalendarAdapter extends RecyclerView.Adapter<CalendarAdapter.CalendarViewHolder> {


    private final ArrayList<String> daysOfMonth;
    // private final OnItemListener onItemListener;
    public Context context;


    public CalendarAdapter(ArrayList<String> daysOfMonth, Context context) {
        this.daysOfMonth = daysOfMonth;
        this.context = context;

    }

    //@NonNull
    //@Override
    public CalendarViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.calendar_cell, parent, false);
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        layoutParams.height = (int) (parent.getHeight() * 0.09);// set kích thước layout

        return new CalendarViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CalendarViewHolder holder, int position) {
        holder.dayOfMonth.setText(daysOfMonth.get(position));
        holder.getAdapterPosition();
        holder.layoutSchedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // if(!dayText.equals("")) {

                String message = "Selected Date " + holder.getAdapterPosition() + "  "+Common.MONTH +"  "+ Common.YEAR + " ";
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show();

                // handleDialog(this.selectedDate);
                //  }
            }
        });


    }

    @Override
    public int getItemCount()
    {
        return daysOfMonth.size();
    }


    public class CalendarViewHolder extends RecyclerView.ViewHolder  {
        public final TextView dayOfMonth;
        ConstraintLayout layoutSchedule;


        public CalendarViewHolder(@NonNull View itemView) {
            super(itemView);

            layoutSchedule = itemView.findViewById(R.id.layout_schedule_test);
            dayOfMonth = itemView.findViewById(R.id.cellDayText);


        }


    }

}
