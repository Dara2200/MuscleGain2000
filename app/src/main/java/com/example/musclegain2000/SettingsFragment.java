package com.example.musclegain2000;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.Button;

import java.io.*;
import androidx.fragment.app.Fragment;

public class SettingsFragment extends Fragment {



    Button button;


    public SettingsFragment(){
        // require a empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.settings_fragment, container, false);
    }
}