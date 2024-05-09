package com.example.musclegain2000.Routines;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.musclegain2000.R;
import com.google.firebase.auth.FirebaseAuth;


public class Details_Routines extends Fragment implements AdapterView.OnItemSelectedListener {
    private FirebaseAuth mAuth;

    Routines routines;

    public Details_Routines(Routines routines) {

        this.routines= routines;
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.routines_detail,container,false);

            mAuth = FirebaseAuth.getInstance();

        TextView textView = v.findViewById(R.id.RoutinesName);
        textView.setText(routines.name);
        return v;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}