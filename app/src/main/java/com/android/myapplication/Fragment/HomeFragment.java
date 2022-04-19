package com.android.myapplication.Fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.android.myapplication.Activity.BMIActivity;
import com.android.myapplication.Activity.MainActivity;
import com.android.myapplication.Activity.ScheduleActivity;
import com.android.myapplication.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    public Context context;
    Fragment fragment;
    public HomeFragment(Context context) {
        // Required empty public constructor
        this.context=context;
    }
    public HomeFragment() {

    }
    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
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
    RelativeLayout layout_schedule,layout_fitness,layout_timeManagement,layout_Eating,layout_BMI,layout_reading;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
         View view= inflater.inflate(R.layout.fragment_home, container, false);
         addControl(view);
            addEvent();
         return view ;
    }
    void addControl(View view)
    {
        layout_schedule = view.findViewById(R.id.layout_schedule);
        layout_fitness = view.findViewById(R.id.layout_fitness);
        layout_timeManagement=view.findViewById(R.id.layout_timeManagement);
        layout_Eating = view.findViewById(R.id.layout_eating);
        layout_BMI = view.findViewById(R.id.layout_BMI);
        layout_reading = view.findViewById(R.id.layout_reading);
    }
    void addEvent()
    {
        layout_schedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ScheduleActivity.class);
                //Toast.makeText(MainActivity.this, "Schedule", Toast.LENGTH_SHORT).show();
                startActivity(intent);
            }
        });
        layout_BMI.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, BMIActivity.class);
                //Toast.makeText(MainActivity.this,"Dăng nhập thành công",Toast.LENGTH_LONG).show();
                startActivity(intent);
            }
        });
    }
}