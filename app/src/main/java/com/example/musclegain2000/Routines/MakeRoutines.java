package com.example.musclegain2000.Routines;

import android.content.res.TypedArray;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentContainerView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.musclegain2000.R;
import com.example.musclegain2000.Starting_Page;
import com.example.musclegain2000.workout.Details_workout;
import com.example.musclegain2000.workout.HomeAdapter;
import com.example.musclegain2000.workout.Home_fragment;
import com.example.musclegain2000.workout.Workout;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;


public class MakeRoutines extends Fragment implements AdapterView.OnItemSelectedListener, MakeRoutinesiter {
    private FirebaseAuth mAuth;

    private ArrayAdapter<String> adapter;
    ViewPager2 viewPager2;

    BottomNavigationView bottomNavigationView;
    private ArrayAdapter<String> adapter2;

    private HomeAdapter mAdapter;

    private ArrayList<String> names2 = new ArrayList<>();
    private ArrayList<Workout> mItemsData;
    private ListView listView;
    private ListView listView2;

    EditText editText;

    ImageView imageView;

    private FirebaseFirestore mFirestore;

    private CollectionReference mItem;
    private SearchView searchView;

    ImageView initialDrawable;

    ArrayList<String> names3 = new ArrayList<>();



    public MakeRoutines() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View v = inflater.inflate(R.layout.fragment_make_routines, container, false);


        mAuth = FirebaseAuth.getInstance();

        imageView = v.findViewById(R.id.IVPreviewImage);
        editText = v.findViewById(R.id.givename);
        listView = v.findViewById(R.id.lista);
        listView2 = v.findViewById(R.id.lista2);
        bottomNavigationView = getActivity().findViewById(R.id.bottomNavigationView);
        viewPager2 = getActivity().findViewById(R.id.viewPager);
        // Inflate the layout for this fragment
        initialDrawable = imageView;
        searchView = v.findViewById(R.id.searchView2);


        mItemsData = new ArrayList<>();


        mAdapter = new HomeAdapter(this.getActivity(), mItemsData);
        mFirestore = FirebaseFirestore.getInstance();
        mItem = mFirestore.collection("Workout");


        ArrayList<String> names;
        names = queryData();

        adapter = new ArrayAdapter<>(this.getActivity(),
                android.R.layout.simple_list_item_1, names);

        listView.setAdapter(adapter);

        adapter2 = new ArrayAdapter<>(this.getActivity(),
                android.R.layout.simple_list_item_1, names3);

        listView2.setAdapter(adapter2);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Get the clicked item
                String selectedItem = names.get(position);

                names3.add(selectedItem);
                adapter2.notifyDataSetChanged();
                listView.setVisibility(View.INVISIBLE);
                searchView.setQuery("", false);


                // Display a toast with the clicked item
                // Here you can perform any other actions with the clicked item
                // For example, if you want to open a new activity or perform some other action
                // based on the clicked item, you can do it here.
            }

        });


        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                getFilter().filter(newText);

                return true;
            }
        });

        return v;
    }

    @Override
    public Filter getFilter() {
        return WorkoutFilter;
    }


    private final Filter WorkoutFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            ArrayList<String> filteredList = new ArrayList<>();
            FilterResults results = new FilterResults();
            ArrayList<String> filteredListname = new ArrayList<>();
            for (int i = 0; i < mItemsData.size(); i++) {

                filteredListname.add(mItemsData.get(i).getName());

            }

            if (charSequence == null || charSequence.length() <= 2) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                listView.setVisibility(View.INVISIBLE);
                                listView2.setVisibility(View.VISIBLE);
                            }
                        });
                    }
                }).start();


                results.count = 0;
                results.values = null;


            } else {
                if (charSequence.length() >= 3) {

                    String filterPattern = charSequence.toString().toLowerCase().trim();
                    for (String item : filteredListname) {
                        if (item.toLowerCase().contains(filterPattern)) {
                            filteredList.add(item);
                        }
                    }

                    results.count = filteredList.size();
                    results.values = filteredList;

                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    listView.setVisibility(View.VISIBLE);
                                    listView2.setVisibility(View.INVISIBLE);
                                }
                            });
                        }
                    }).start();

                }
            }

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            names2.clear();
            if (results != null && results.values instanceof ArrayList<?>) {
                ArrayList<String> filteredValues = (ArrayList<String>) results.values;
                names2.addAll(filteredValues);
            }
            adapter.clear();
            adapter.addAll(names2);
            adapter.notifyDataSetChanged();

        }
    };

    public final void runOnUiThread(Runnable action) {
        getActivity().runOnUiThread(action);
    }


    private void initializeData() {
            // Get the resources from the XML file.
            String[] name = getResources().getStringArray(R.array.w_name);
            String[] type = getResources().getStringArray(R.array.w_type);
            TypedArray image = getResources().obtainTypedArray(R.array.w_image);
            for (int i = 0; i < getResources().getStringArray(R.array.w_name).length; i++) {
                Workout workout = new Workout(name[i], type[i], image.getResourceId(i, 0));
                mItem.add(workout);
            }

            image.recycle();
        }

        private ArrayList<String> queryData() {
            mItemsData.clear();
            ArrayList<String> names = new ArrayList<>();

            mItem.get().addOnSuccessListener(queryDocumentSnapshots -> {
                for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                    Workout item = document.toObject(Workout.class);
                    mItemsData.add(item);
                    names.add(item.getName());

                }
                if (mItemsData.isEmpty()) {

                    initializeData();
                    queryData();
                }
                // Notify the adapter of the change.
                mAdapter.notifyDataSetChanged();
            });
            return names;
        }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }


}