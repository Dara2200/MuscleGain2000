package com.example.musclegain2000.Routines;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.fragment.app.Fragment;

import com.example.musclegain2000.R;
import com.google.firebase.auth.FirebaseAuth;


public class EditRoutines extends Fragment implements AdapterView.OnItemSelectedListener {
    Spinner spinner;
    private FirebaseAuth mAuth;

    public EditRoutines() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View v = inflater.inflate(R.layout.fragment_make_routines,container,false);


        // Inflate the layout for this fragment
        return v;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}