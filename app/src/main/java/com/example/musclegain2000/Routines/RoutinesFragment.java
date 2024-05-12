package com.example.musclegain2000.Routines;

import android.content.res.TypedArray;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;
import com.example.musclegain2000.R;
import com.example.musclegain2000.Starting_Page;
import com.example.musclegain2000.ViewPagerAdapter;
import com.example.musclegain2000.workout.HomeAdapter;
import com.example.musclegain2000.workout.Workout;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import java.util.ArrayList;

public class RoutinesFragment extends Fragment {



    int counter = 0;
    private ArrayList<Routines> mItemsData;
    private static final String LOG_TAG = Starting_Page.class.getName();
    FrameLayout frameLayout_details;
    ViewPager2 viewPager2;
    private CollectionReference mItems;

    FirebaseFirestore mFirestore;

    BottomNavigationView bottomNavigationView;
    private RoutinesAdapter mAdapter;
    ViewPagerAdapter viewPagerAdapter;

    public RoutinesFragment() {
        // require a empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            Log.d(LOG_TAG, "Authenticated user!");

        } else {
            Log.d(LOG_TAG, "Unauthenticated user!");
            getActivity().finish();
        }

        View v = inflater.inflate(R.layout.fragment_routines,
                container, false);
        viewPager2 = getActivity().findViewById(R.id.viewPager);
        RecyclerView mRecyclerView = v.findViewById(R.id.recyclerView);
        // Set the Layout Manager.
        int gridNumber = 1;
        mRecyclerView.setLayoutManager(new GridLayoutManager(
                this.getContext(), gridNumber));





        mItemsData = new ArrayList<>();
        mAdapter = new RoutinesAdapter(this.getActivity(),mItemsData);
        mRecyclerView.setAdapter(mAdapter);
        mFirestore = FirebaseFirestore.getInstance();
        mItems = mFirestore.collection("Routines");
        queryData();







        mAdapter.setOnItemClickListener(new RoutinesAdapter.OnItemClickListener() {

            @Override
            public void onItemClick(int position) {

                String title = mItemsData.get(position).name;

                for (Routines routine : mItemsData) {
                    if (routine.getName().equals(title)) {

                        frameLayout_details = getActivity().findViewById(R.id.fragment_details);
                        getActivity().getSupportFragmentManager().beginTransaction()
                                .replace(R.id.fragment_details, new Details_Routines(routine))
                                .commit();
                        frameLayout_details.setVisibility(View.VISIBLE);
                        viewPager2.setVisibility(View.INVISIBLE);
                        bottomNavigationView = getActivity().findViewById(R.id.bottomNavigationView);
                        bottomNavigationView.setVisibility(View.INVISIBLE);

                    }
                }
            }
        });
        return v;
    }
    private void initializeData() {

        counter++;
        // Get the resources from the XML file.
        String[] name = getResources().getStringArray(R.array.workouts_name);
        TypedArray image =  getResources().obtainTypedArray(R.array.workouts_image);
        for (int i = 0; i < getResources().getStringArray(R.array.workouts_image).length; i++) {
            Routines routines = new Routines(name[i], image.getResourceId(i,0));
            mItems.add(routines);
        }
        image.recycle();
        Log.d(LOG_TAG,"Nem tom mivan");
    }

    public void refreshData() {
        queryData();
        mAdapter.notifyDataSetChanged();
    }

    private void queryData() {
        mItemsData.clear();



        mItems.orderBy("name", Query.Direction.ASCENDING)
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                        Routines item = document.toObject(Routines.class);
                        boolean add = false;
                        for (int i = 0; i < mItemsData.size(); i++) {
                            if (item.getName().equals(mItemsData.get(i).getName())) {
                               add = true;
                            }
                        }
                        if (!add) {
                            mItemsData.add(item);
                        }
                }
            if (mItemsData.isEmpty() && counter==0) {
                Log.d(LOG_TAG,"Nem tom mivan");
                initializeData();
                queryData();
            }
            // Notify the adapter of the change.
            mAdapter.notifyDataSetChanged();
        });

    }

    @Override
    public void onResume() {
        super.onResume();
        queryData();
        mAdapter.notifyDataSetChanged();
    }


    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        queryData();
        mAdapter.notifyDataSetChanged();
    }


}