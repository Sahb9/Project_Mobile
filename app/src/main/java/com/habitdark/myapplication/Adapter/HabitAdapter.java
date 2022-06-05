package com.habitdark.myapplication.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.habitdark.myapplication.DAO.HabitDAO;
import com.habitdark.myapplication.Entity.Habit;
import com.habitdark.myapplication.R;
import com.habitdark.myapplication.callback.CallBack;
import com.habitdark.myapplication.utilities.Common;

import java.util.List;

public class HabitAdapter extends BaseAdapter {
    private Context context;
    private int layout;
    private List<Habit> habitList;
    private int index;

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
        this.index = i;
        Habit habit = this.habitList.get(this.index);
        HabitAdapter _this = this;

        LayoutInflater layoutInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = layoutInflater.inflate(this.layout, null);

        TextView textViewName = view.findViewById(R.id.habit_name);
        TextView textViewProgress = view.findViewById(R.id.txt_habit_progress);
        ProgressBar progressBarHabit = view.findViewById(R.id.progress_circular);
        ImageButton btnDelete = view.findViewById(R.id.btn_delete);

        float progressResult = ((float) habit.getCurrent() / habit.getTarget()) * 100;
        int progress = (int) progressResult;

        String progressText = "";
        progressText = progress + "%";

        textViewName.setText(habit.getName());
        textViewProgress.setText(progressText);
        progressBarHabit.setProgress(progress);

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(view.getContext());
                final String DIALOG_TITLE = "Are you sure?";
                final String POSITIVE_BTN_TEXT = "Delete";
                final String NEGATIVE_BTN_TEXT = "Dismiss";
                final String SUCCESS = "Delete ";
                final String FAILURE = "Some thing wrong";

                alertDialogBuilder.setTitle(DIALOG_TITLE);

                alertDialogBuilder.setPositiveButton(POSITIVE_BTN_TEXT, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        HabitDAO habitDAO = HabitDAO.getInstance();

                        habitDAO.deleteHabit(Common.uID, habit, new CallBack<Boolean>() {
                            @Override
                            public void onCallBack(Boolean callback) {
                                if (callback) {
                                    habitList.remove(index);
                                    _this.notifyDataSetChanged();
                                    Toast.makeText(view.getContext(), SUCCESS + habit.getName(), Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(view.getContext(), FAILURE, Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

                    }
                });

                alertDialogBuilder.setNegativeButton(NEGATIVE_BTN_TEXT, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });

                alertDialogBuilder.create();
                alertDialogBuilder.show();
            }
        });

        return view;
    }
}
