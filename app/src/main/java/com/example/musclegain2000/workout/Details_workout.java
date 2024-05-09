package com.example.musclegain2000.workout;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.musclegain2000.R;
import com.example.musclegain2000.workout.Workout;
import com.google.firebase.auth.FirebaseAuth;

public class Details_workout extends Fragment implements AdapterView.OnItemSelectedListener{

    private FirebaseAuth mAuth;
    Workout workout;

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
        return v;
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
