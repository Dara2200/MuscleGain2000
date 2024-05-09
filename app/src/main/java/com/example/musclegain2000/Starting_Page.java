package com.example.musclegain2000;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.FragmentContainerView;
import androidx.viewpager2.widget.ViewPager2;

import com.example.musclegain2000.Routines.EditRoutines;
import com.example.musclegain2000.Routines.MakeRoutines;
import com.example.musclegain2000.Routines.RoutinesAdapter;
import com.example.musclegain2000.Routines.StartRoutines;
import com.example.musclegain2000.Routines.Details_Routines;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class Starting_Page extends AppCompatActivity
{
    private static final String LOG_TAG = Starting_Page.class.getName();

    ViewPagerAdapter viewPagerAdapter;
    ViewPager2 viewPager2;
      FrameLayout frameLayout;
    FrameLayout frameLayout2;
    FrameLayout frameLayout3;
      Button BSelectImage;
      ImageView IVPreviewImage;

    int SELECT_PICTURE = 200;


    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_starting_page);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        bottomNavigationView
                = findViewById(R.id.bottomNavigationView);
        frameLayout = findViewById(R.id.fragment_container);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, new MakeRoutines())
                .commit();
        viewPager2 = findViewById(R.id.viewPager);
        viewPagerAdapter = new ViewPagerAdapter(this);
        viewPager2.setAdapter(viewPagerAdapter);
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                int id = menuItem.getItemId();
                if (id == R.id.home) {
                    viewPager2.setCurrentItem(0);
                }
                if (id == R.id.workouts) {
                    viewPager2.setCurrentItem(1);
                }
                if (id == R.id.settings) {
                    viewPager2.setCurrentItem(2);
                }
                return false;
            }
        });

    }



    public void onActivityResult(int requestCode, int resultCode, Intent data){
            super.onActivityResult(requestCode, resultCode, data);

            if (resultCode == RESULT_OK) {

                // compare the resultCode with the
                // SELECT_PICTURE constant
                if (requestCode == SELECT_PICTURE) {
                    // Get the url of the image from data
                    Uri selectedImageUri = data.getData();
                    if (null != selectedImageUri) {
                        // update the preview image in the layout
                        IVPreviewImage.setImageURI(selectedImageUri);
                    }
                }
            }


viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {

    @Override
    public void onPageSelected(int position) {
switch (position) {
    case 0:
        bottomNavigationView.getMenu().findItem(R.id.home).setChecked(true);
        break;
    case 1:
        bottomNavigationView.getMenu().findItem(R.id.workouts).setChecked(true);
        break;
    case 2:
        bottomNavigationView.getMenu().findItem(R.id.settings).setChecked(true);
}
        super.onPageSelected(position);
    }
});

  }
      public void addWorkoutGroup(View view) {

          frameLayout.setVisibility(View.VISIBLE);
          viewPager2.setVisibility(View.INVISIBLE);
          bottomNavigationView.setVisibility(View.INVISIBLE);
      }


    public void imageChooser(View view) {
        BSelectImage = frameLayout.findViewById(R.id.BSelectImage);
        IVPreviewImage = frameLayout.findViewById(R.id.IVPreviewImage);
        // create an instance of the
        // intent of the type image
        Intent i = new Intent();
        i.setType("image/*");
        i.setAction(Intent.ACTION_GET_CONTENT);

        // pass the constant to compare it
        // with the returned requestCode
        startActivityForResult(Intent.createChooser(i, "Select Picture"), SELECT_PICTURE);

    }

    public void Start(View view) {
        Intent intent = new Intent(this, StartRoutines.class);
        startActivity(intent);
    }

    public void logout(View view) {

        SaveSharedPreference.clearUserName(this);
        finish();

    }

    public void cancel2(View view) {

        frameLayout.setVisibility(View.INVISIBLE);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, new MakeRoutines())
                .commit();
        viewPager2.setVisibility(View.VISIBLE);
        bottomNavigationView.setVisibility(View.VISIBLE);
    }
}