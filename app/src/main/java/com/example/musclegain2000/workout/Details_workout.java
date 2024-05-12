package com.example.musclegain2000.workout;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.example.musclegain2000.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class Details_workout extends Fragment implements AdapterView.OnItemSelectedListener{

    private FirebaseAuth mAuth;
    Workout workout;
    FrameLayout frameLayout_detail;
    BottomNavigationView bottomNavigationView;
    ViewPager2 viewPager2;
    Button cancel;
    Button start;

    public Details_workout(Workout workout) {

        this.workout= workout;
        // Required empty public constructor
    }



    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.workout_detail,container,false);

        mAuth = FirebaseAuth.getInstance();

        TextView textView = v.findViewById(R.id.WorkoutName_d);
        textView.setText(workout.name);
        cancel = v.findViewById(R.id.cancel_wdetail);
        start = v.findViewById(R.id.Start_w);
        viewPager2 = getActivity().findViewById(R.id.viewPager);

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    Intent intent = new Intent(getContext(), StartingWorkoutActivity.class);
                    startActivity(intent);

            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                frameLayout_detail = getActivity().findViewById(R.id.fragment_details);

                frameLayout_detail.setVisibility(View.INVISIBLE);
                viewPager2.setVisibility(View.VISIBLE);
                bottomNavigationView = getActivity().findViewById(R.id.bottomNavigationView);
                bottomNavigationView.setVisibility(View.VISIBLE);

            }
        });
        return v;
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
