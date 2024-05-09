package com.example.musclegain2000.Routines;

import android.content.res.TypedArray;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import java.util.ArrayList;

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
import com.google.firebase.firestore.QueryDocumentSnapshot;

public class RoutinesFragment extends Fragment {
    private int gridNumber = 1;

    private ArrayList<Routines> mItemsData;
    private RecyclerView mRecyclerView;
    private static final String LOG_TAG = Starting_Page.class.getName();
    private FirebaseUser user;
    private FirebaseFirestore mFirestore;
    FrameLayout frameLayout4;
    ViewPager2 viewPager2;
    private CollectionReference mItems;

    BottomNavigationView bottomNavigationView;
    private RoutinesAdapter mAdapter;

    public RoutinesFragment() {
        // require a empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            Log.d(LOG_TAG, "Authenticated user!");

        } else {
            Log.d(LOG_TAG, "Unauthenticated user!");
            getActivity().finish();
        }

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


        mAdapter.setOnItemClickListener(new RoutinesAdapter.OnItemClickListener() {

            @Override
            public void onItemClick(int position) {
                String title = mItemsData.get(position).name;

                for (Routines routine : mItemsData) {
                    if (routine.getName().equals(title)) {

                        frameLayout4 = getActivity().findViewById(R.id.fragment_container4);
                        getActivity().getSupportFragmentManager().beginTransaction()
                                .replace(R.id.fragment_container4, new Details_Routines(routine))
                                .commit();
                        frameLayout4.setVisibility(View.VISIBLE);
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
        // Get the resources from the XML file.
        String[] itemname = getResources()
                .getStringArray(R.array.workouts_name);
        TypedArray itemsImageResources =
                getResources().obtainTypedArray(R.array.workouts_image);

        Log.d(LOG_TAG,"Nem tom mivan");
        // Clear the existing data (to avoid duplication)
       // mItemsData.clear();
                for (int i=0;i<itemname.length;i++ ) {
                   Routines workouts = new Routines(
                            itemname[i],
                            itemsImageResources.getResourceId(i,0));

                    mItems.add(workouts);

            }

                itemsImageResources.recycle();
            // Notify the adapter of the change.

    }

    private void queryData() {
        mItemsData.clear();

                mItems.get().addOnSuccessListener(queryDocumentSnapshots -> {
                    for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                        Routines item = document.toObject(Routines.class);
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
}