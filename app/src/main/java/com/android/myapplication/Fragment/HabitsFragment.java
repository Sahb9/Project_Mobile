package com.android.myapplication.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.myapplication.Entity.Habit;
import com.android.myapplication.R;
import com.android.myapplication.entitys.HabitAdapter;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HabitsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HabitsFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private ListView listViewHabit;
    private ArrayList<Habit> habits;
    private HabitAdapter habitAdapter;

    public HabitsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HabitsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HabitsFragment newInstance(String param1, String param2) {
        HabitsFragment fragment = new HabitsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_habits, container, false);
        init(view);
        setListView();

        return view;
    }

    private void init(View view) {
        this.listViewHabit = view.findViewById(R.id.list_item_habit);
        this.habits = new ArrayList<>();

        this.habits.add(new Habit("Mẫu list view cho habit"));
        this.habits.add(new Habit("Kiểm tra giao diện UI"));
        this.habits.add(new Habit("Chỉnh sửa item cho habit list"));
        this.habits.add(new Habit("Kiểm tra lần nữa"));
        this.habits.add(new Habit("Thêm thuộc tính cho lớp habit"));
        this.habits.add(new Habit("Kiểm tra giao diện danh sách"));
        this.habits.add(new Habit("Thêm các thông tin các trường hợp cho danh sách"));
        this.habits.add(new Habit("Tạo dialog để thêm habit"));
    }

    private void setListView() {
        this.habitAdapter = new HabitAdapter(getActivity(), R.layout.element_habit, this.habits);

        this.listViewHabit.setAdapter(this.habitAdapter);
    }
}