package com.example.musclegain2000.workout;

import android.content.res.TypedArray;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.SearchView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.example.musclegain2000.R;
import com.example.musclegain2000.Starting_Page;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;

public class Home_fragment extends Fragment {

    private static final String LOG_TAG = Starting_Page.class.getName();

    private FirebaseFirestore mFirestore;


    private CollectionReference mItem;
    private int gridNumber = 1;
    private HomeAdapter mAdapter;
    private FirebaseUser user;
    private ArrayList<Workout> mItemsData;
    private RecyclerView mRecyclerView2;

    FrameLayout frameLayout_detail;
    ViewPager2 viewPager2;
    BottomNavigationView bottomNavigationView;
    private SearchView searchView;


    public Home_fragment(){
        // require a empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            Log.d(LOG_TAG, "Authenticated user!");

        } else {
            Log.d(LOG_TAG, "Unauthenticated user!");
            getActivity().finish();
        }

/**
        View v = inflater.inflate(R.layout.fragment_routines,
                container, false);
        viewPager2 = getActivity().findViewById(R.id.viewPager);
        mRecyclerView = v.findViewById(R.id.recyclerView);
        // Set the Layout Manager.
        mRecyclerView.setLayoutManager(new GridLayoutManager(
                this.getContext(), gridNumber));

        mItemsData = new ArrayList<>();
        mAdapter = new RoutinesAdapter(this.getActivity(), mItemsData);
        mRecyclerView.setAdapter(mAdapter);


        mFirestore = FirebaseFirestore.getInstance();
        mItems = mFirestore.collection("Routines");
        queryData();


**/
        View v = inflater.inflate(R.layout.home_fragment,
                container, false);

        viewPager2 = getActivity().findViewById(R.id.viewPager);
        mRecyclerView2 = v.findViewById(R.id.recyclerView2);
        // Set the Layout Manager.
        mRecyclerView2.setLayoutManager(new GridLayoutManager(
                this.getContext(), gridNumber));

        searchView = v.findViewById(R.id.searchView);

        mItemsData = new ArrayList<>();
        mAdapter = new HomeAdapter(this.getActivity(), mItemsData);
        mFirestore = FirebaseFirestore.getInstance();
        mRecyclerView2.setAdapter(mAdapter);

        mItem = mFirestore.collection("Workout");
        queryData();

        mAdapter.setOnItemClickListener(new HomeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                String title = mItemsData.get(position).name;


                for (Workout workout : mItemsData) {
                    if (workout.getName().equals(title)) {

                        frameLayout_detail = getActivity().findViewById(R.id.fragment_details);
                        getActivity().getSupportFragmentManager().beginTransaction()
                                .replace(R.id.fragment_details, new Details_workout(workout))
                                .commit();
                        frameLayout_detail.setVisibility(View.VISIBLE);
                        viewPager2.setVisibility(View.INVISIBLE);
                        bottomNavigationView = getActivity().findViewById(R.id.bottomNavigationView);
                        bottomNavigationView.setVisibility(View.INVISIBLE);

                    }
                }

            }

        });



        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                mAdapter.getFilter().filter(newText);
                return false;
            }
        });

        // Inflate the layout for this fragment
        return v;
    }


    private void initializeData() {
        // Get the resources from the XML file.
        String[] name = getResources().getStringArray(R.array.w_name);
        String[] type = getResources().getStringArray(R.array.w_type);
        for (int i = 0; i < getResources().getStringArray(R.array.w_name).length; i++) {
            Workout workout = new Workout(name[i], type[i]);
            mItem.add(workout);
        }

        Log.d(LOG_TAG,"Nem tom mivan");
    }

    private void queryData() {
        mItemsData.clear();

        mItem.get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
            for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                Workout item = document.toObject(Workout.class);
                mItemsData.add(item);

            }
            if (mItemsData.isEmpty()) {
                Log.d(LOG_TAG,"Nem tom mivan");
                initializeData();
                queryData();
            }
            // Notify the adapter of the change.
            mAdapter.notifyDataSetChanged();
        });
    }

    public ArrayList<Workout> getmItemsData() {
        return mItemsData;
    }
}