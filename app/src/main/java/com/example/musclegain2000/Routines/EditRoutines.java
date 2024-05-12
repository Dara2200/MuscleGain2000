package com.example.musclegain2000.Routines;

import static android.content.ContentValues.TAG;

import android.content.res.TypedArray;

import android.os.Bundle;
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
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;
import com.example.musclegain2000.R;
import com.example.musclegain2000.workout.HomeAdapter;
import com.example.musclegain2000.workout.Workout;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class EditRoutines extends Fragment implements AdapterView.OnItemSelectedListener, EditRoutineses {
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
    Button delete;

    FrameLayout frameLayout_edit;
    FrameLayout frameLayout_detail;

    ArrayList<String> names3 = new ArrayList<>();
    ArrayList<String> names = new ArrayList<>();
    Routines routines;
    Details_Routines detailsRoutines;

    public EditRoutines() {
        // Required empty public constructor
    }

    public EditRoutines(Routines routines, Details_Routines detailsRoutines) {

        this.routines= routines;
        this.detailsRoutines= detailsRoutines;

        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View v = inflater.inflate(R.layout.fragment_routines_edit, container, false);

        button2 = v.findViewById(R.id.cancel2_edit);

        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        TextView textView;
        delete = v.findViewById(R.id.delete);
        button = v.findViewById(R.id.Save_edit);
        editText = v.findViewById(R.id.editname);
        listView = v.findViewById(R.id.lista_edit);
        listView2 = v.findViewById(R.id.lista2_edit);
        bottomNavigationView = getActivity().findViewById(R.id.bottomNavigationView);
        viewPager2 = getActivity().findViewById(R.id.viewPager);
        // Inflate the layout for this fragment
        searchView = v.findViewById(R.id.searchView3);


        RItemsData = new ArrayList<>();
        RAdapter = new RoutinesAdapter(this.getActivity(), RItemsData);


        frameLayout_edit = getActivity().findViewById(R.id.fragment_edits);
        frameLayout_detail =getActivity().findViewById(R.id.fragment_details);
        mItemsData = new ArrayList<>();
        mAdapter = new HomeAdapter(this.getActivity(), mItemsData);

        FirebaseFirestore mFirestore = FirebaseFirestore.getInstance();
        mItem = mFirestore.collection("Workout");
        mItems = mFirestore.collection("Routines");


        names = queryData();

        adapter = new ArrayAdapter<>(this.getActivity(),
                R.layout.textcenter, R.id.center_text, names);
        textView = v.findViewById(R.id.Routinesedit_title);
        textView.setText(routines.getName());
        listView.setAdapter(adapter);
        if (routines.getWorkouts()!= null) {
            names3.addAll(routines.getWorkouts());
        }
        adapter2 = new ArrayAdapter<>(this.getActivity(),
                R.layout.textcenter, R.id.center_text, names3);

        listView2.setAdapter(adapter2);


        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Query query = mItems.whereEqualTo("name", routines.getName());
// Execute the query
                query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                // Delete each document found by the query
                                document.getReference().delete()
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                // Document successfully deleted
                                                Log.d(TAG, "DocumentSnapshot successfully deleted!");
                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                // Handle any errors
                                                Log.w(TAG, "Error deleting document", e);
                                            }
                                        });


                            }
                        } else {
                            // Handle any errors
                            Log.w(TAG, "Error getting documents.", task.getException());
                        }
                    }
                });

                frameLayout_edit.setVisibility(View.INVISIBLE);

               viewPager2.setVisibility(View.VISIBLE);
               bottomNavigationView.setVisibility(View.VISIBLE);
                RAdapter.notifyDataSetChanged();
            }


                // Notify the adapter of the change.




        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadData();
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                frameLayout_edit.setVisibility(View.INVISIBLE);

                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_edits, new EditRoutines( routines, detailsRoutines))
                        .commit();

                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_details, new Details_Routines(routines))
                        .commit();

                frameLayout_detail.setVisibility(View.VISIBLE);
            }
        });

        listView2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = names3.get(position);


                for (int i = 0; i < names3.size(); i++) {
                    if (names3.get(i).equals(selectedItem)) {
                        names3.remove(i);
                        adapter2.notifyDataSetChanged();
                    }
                }

            }
        });

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

        int counter0 = 0;
        int counter1=0;
        boolean volt = false;
        // Get data from UI elements
        String editTextValue = editText.getText().toString().trim();
        List<String> listViewData = names3;
        Routines upload;
       TypedArray image =  getResources().obtainTypedArray(R.array.workouts_image);
        upload = new Routines();
        if (routines.getImageResource()==0) {
            upload.setImageResource(image.getResourceId(0,0));
        }else {
            upload.setImageResource(routines.getImageResource());
        }

        if (editTextValue.isEmpty() || editTextValue.equals(routines.getName())) {
        }else {
            counter0++;
            volt=true;
            upload.setName(editTextValue);
        }
    if (routines.getWorkouts() !=null) {
        if (names3!=null) {
            if (!routines.getWorkouts().equals(names3)) {
                counter1++;
                volt=true;
                upload.setWorkouts(names3);
            }
        }else {
            counter1++;
            volt=true;
           upload.setWorkouts(null);
        }
    } else if (routines.getWorkouts()== null && names3!=null) {
        counter1++;
        volt=true;
        upload.setWorkouts(names3);
    }

        if (volt) {
            if (counter0==0) {
                upload.setName(routines.getName());
            }else if (counter1==0) {
                upload.setWorkouts(routines.workouts);
            }

           Query query = mItems.whereEqualTo("name", routines.getName());
// Execute the query
            Routines finalUpload = upload;
            query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
               @Override
               public void onComplete(@NonNull Task<QuerySnapshot> task) {
                   if (task.isSuccessful()) {
                       for (QueryDocumentSnapshot document : task.getResult()) {
                           Map<String, Object> data = new HashMap<>();
                           data.put("name", finalUpload.getName());
                           data.put("workouts", finalUpload.getWorkouts());
                           data.put("imageResource", finalUpload.getImageResource());
                           // Delete each document found by the query
                           document.getReference().update(data)
                                   .addOnSuccessListener(new OnSuccessListener<Void>() {
                                       @Override
                                       public void onSuccess(Void aVoid) {
                                           // Document successfully deleted
                                           Log.d(TAG, "DocumentSnapshot successfully deleted!");
                                       }
                                   })
                                   .addOnFailureListener(new OnFailureListener() {
                                       @Override
                                       public void onFailure(@NonNull Exception e) {
                                           // Handle any errors
                                           Log.w(TAG, "Error deleting document", e);
                                       }
                                   });
                       }
                   } else {
                       // Handle any errors
                       Log.w(TAG, "Error getting documents.", task.getException());
                   }
               }
           });
       }else{
           upload= routines;
        }



        mItems.get().addOnSuccessListener(queryDocumentSnapshots -> {
            // Notify the adapter of the change.

            RAdapter.notifyDataSetChanged();

        });
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_details, new Details_Routines(upload))
                .commit();

        frameLayout_edit.setVisibility(View.INVISIBLE);
        frameLayout_detail.setVisibility(View.VISIBLE);
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_edits, new EditRoutines(routines,detailsRoutines))
                .commit();


    }



    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}