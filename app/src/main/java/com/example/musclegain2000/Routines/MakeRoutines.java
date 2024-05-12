package com.example.musclegain2000.Routines;

import android.content.res.TypedArray;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager2.widget.ViewPager2;
import com.example.musclegain2000.R;
import com.example.musclegain2000.workout.HomeAdapter;
import com.example.musclegain2000.workout.Workout;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import java.util.ArrayList;

import java.util.List;


public class MakeRoutines extends Fragment implements AdapterView.OnItemSelectedListener, MakeRoutinesiter {
    private ArrayAdapter<String> adapter;
    ViewPager2 viewPager2;

    Button button2;

    BottomNavigationView bottomNavigationView;
    private ArrayAdapter<String> adapter2;

    private HomeAdapter mAdapter;
    private RoutinesAdapter RAdapter;

    private ArrayList<String> names2 = new ArrayList<>();
    private ArrayList<Workout> mItemsData;
    private ArrayList<Routines> RItemsData;
    private ListView listView;
    private ListView listView2;
    EditText editText;

    private CollectionReference mItem;
    private SearchView searchView;
    private CollectionReference mItems;
    Button button;
    TextView listtext;

    FrameLayout frameLayout;

    List<String> names3 = new ArrayList<>();
    List<String> names = new ArrayList<>();
    Fragment fragment;

    public MakeRoutines() {
        // Required empty public constructor
    }
    public MakeRoutines(Fragment fragment) {
        this.fragment=fragment;
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View v = inflater.inflate(R.layout.fragment_make_routines, container, false);

button2 = v.findViewById(R.id.cancel2);
        listtext = getActivity().findViewById(R.id.center_text);
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        button = v.findViewById(R.id.Save);
        editText = v.findViewById(R.id.givename);
        listView = v.findViewById(R.id.lista);
        listView2 = v.findViewById(R.id.lista2);
        bottomNavigationView = getActivity().findViewById(R.id.bottomNavigationView);
        viewPager2 = getActivity().findViewById(R.id.viewPager);
        // Inflate the layout for this fragment
        searchView = v.findViewById(R.id.searchView2);


        RItemsData = new ArrayList<>();
        RAdapter = new RoutinesAdapter(this.getActivity(), RItemsData);


        frameLayout = getActivity().findViewById(R.id.fragment_makeRoutines);
        mItemsData = new ArrayList<>();
        mAdapter = new HomeAdapter(this.getActivity(), mItemsData);

        FirebaseFirestore mFirestore = FirebaseFirestore.getInstance();
        mItem = mFirestore.collection("Workout");
        mItems = mFirestore.collection("Routines");


        names = queryData();

        adapter = new ArrayAdapter<>(this.getActivity(),
                R.layout.textcenter, R.id.center_text, names);

        listView.setAdapter(adapter);

        ArrayAdapter<String> ad = new ArrayAdapter<>(this.getActivity(), R.layout.textcenter, R.id.center_text, names3);

        listView2.setAdapter(ad);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadData();
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                frameLayout.setVisibility(View.INVISIBLE);
                viewPager2.setVisibility(View.VISIBLE);
                bottomNavigationView.setVisibility(View.VISIBLE);
                  getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_makeRoutines, new MakeRoutines())
                        .commit();


            }
        });



        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Get the clicked item
                String selectedItem = names.get(position);

                names3.add(selectedItem);
                ad.notifyDataSetChanged();
                listView.setVisibility(View.INVISIBLE);
                searchView.setQuery("", false);


                // Display a toast with the clicked item
                // Here you can perform any other actions with the clicked item
                // For example, if you want to open a new activity or perform some other action
                // based on the clicked item, you can do it here.
            }

        });



        searchView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (names.isEmpty()) names = queryData();

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



        private ArrayList<String> queryData() {
            mItemsData.clear();
            ArrayList<String> names = new ArrayList<>();

            mItem.get().addOnSuccessListener(queryDocumentSnapshots -> {
                for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                    Workout item = document.toObject(Workout.class);
                    mItemsData.add(item);
                    names.add(item.getName());

                }
                // Notify the adapter of the change.
                mAdapter.notifyDataSetChanged();
            });
            return names;
        }

    public void uploadData() {
        RItemsData.clear();
        // Get data from UI elements
        String editTextValue = editText.getText().toString().trim();
        List<String> listViewData = names3;


        if (editTextValue.isEmpty() || listViewData.isEmpty()) {
            Toast.makeText(getActivity(),"You have empty boxes", Toast.LENGTH_LONG).show();
            return;
        }

        Routines routines;
        TypedArray image =  getResources().obtainTypedArray(R.array.workouts_image);
             routines = new Routines(
                    editTextValue,
                    listViewData,image.getResourceId(0,0));
        mItems.add(routines);



            mItems.get().addOnSuccessListener(queryDocumentSnapshots -> {
                // Notify the adapter of the change.
            RAdapter.notifyDataSetChanged();
            });

        frameLayout.setVisibility(View.INVISIBLE);
        viewPager2.setVisibility(View.VISIBLE);
        bottomNavigationView.setVisibility(View.VISIBLE);

        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_makeRoutines, new MakeRoutines())
                .commit();


    }



    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}