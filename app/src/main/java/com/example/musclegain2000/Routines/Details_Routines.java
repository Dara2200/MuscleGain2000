package com.example.musclegain2000.Routines;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.example.musclegain2000.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;


public class Details_Routines extends Fragment implements AdapterView.OnItemSelectedListener {
    private FirebaseAuth mAuth;

    Routines routines;
    private ArrayAdapter<String> adapter;
    BottomNavigationView bottomNavigationView;
    ViewPager2 viewPager2;

    FrameLayout frameLayoutedit;

    FrameLayout frameLayoutdetails;
    ListView listView;
    Button button;

    Button cancel;
    TextView textView;


    public Details_Routines() {

        // Required empty public constructor
    }
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

        cancel = v.findViewById(R.id.cancel_Rdetail);

        button = v.findViewById(R.id.Edit);
textView = v.findViewById(R.id.RoutinesName);
        textView.setText(routines.getName());
        listView = v.findViewById(R.id.Routine_detaillist);
        frameLayoutdetails = this.getActivity().findViewById(R.id.fragment_details);

        frameLayoutedit = getActivity().findViewById(R.id.fragment_edits);
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_edits, new EditRoutines(this.routines, this))
                .commit();


        viewPager2 = getActivity().findViewById(R.id.viewPager);

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                frameLayoutdetails = getActivity().findViewById(R.id.fragment_details);

                frameLayoutdetails.setVisibility(View.INVISIBLE);
                viewPager2.setVisibility(View.VISIBLE);
                bottomNavigationView = getActivity().findViewById(R.id.bottomNavigationView);
                bottomNavigationView.setVisibility(View.VISIBLE);

            }
        });
        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {


            frameLayoutdetails.setVisibility(View.INVISIBLE);
            frameLayoutedit.setVisibility(View.VISIBLE);

            }
        });

        adapter = new ArrayAdapter<>(this.getActivity(),
                R.layout.textcenter, R.id.center_text, routines.workouts);

if (routines.getWorkouts() != null) listView.setAdapter(adapter);

        return v;
    }




    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}