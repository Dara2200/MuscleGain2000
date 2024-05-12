package com.example.musclegain2000;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.example.musclegain2000.Routines.MakeRoutines;
import com.example.musclegain2000.Routines.RoutinesFragment;
import com.example.musclegain2000.Routines.StartingRoutineActivity;
import com.example.musclegain2000.workout.StartingWorkoutActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;


public class Starting_Page extends AppCompatActivity
{
    MakeRoutines makeRoutines ;
    ViewPagerAdapter viewPagerAdapter;
    ViewPager2 viewPager2;
      FrameLayout frameLayout;
      FrameLayout frameLayoutedit;

      Button button,button2;

      ImageView IVPreviewImage;

    int SELECT_PICTURE = 200;
    Fragment routinesfragment;


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

        button2 = this.findViewById(R.id.cancel2);

        button = this.findViewById(R.id.Save);

        bottomNavigationView
                = findViewById(R.id.bottomNavigationView);
        frameLayoutedit = findViewById(R.id.fragment_edits);
        frameLayout = findViewById(R.id.fragment_makeRoutines);


        viewPager2 = findViewById(R.id.viewPager);
        viewPagerAdapter = new ViewPagerAdapter(this);
        viewPager2.setAdapter(viewPagerAdapter);

        bottomNavigationView.setOnItemSelectedListener(menuItem -> {
            int id = menuItem.getItemId();
            if (id == R.id.home) {
                viewPager2.setCurrentItem(0);
            }
            if (id == R.id.routines) {
                viewPager2.setCurrentItem(1);

            }
            if (id == R.id.settings) {
                viewPager2.setCurrentItem(2);
            }
            return false;
        });
        Fragment fragment = viewPagerAdapter.routinesFragment;
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_makeRoutines, makeRoutines = new MakeRoutines(fragment))
                .commit();

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
        bottomNavigationView.getMenu().findItem(R.id.Routines_title).setChecked(true);

        RoutinesFragment fragment = viewPagerAdapter.routinesFragment;
        if (fragment instanceof RoutinesFragment) {
            // If the fragment is YourFragment, refresh its data
            fragment.refreshData();
        }
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


    public void logout(View view) {

        SaveSharedPreference.clearUserName(this);
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void Start(View view) {
        Intent intent = new Intent(this, StartingRoutineActivity.class);
        startActivity(intent);
    }

    public void Start_w(View view) {
        Intent intent = new Intent(this, StartingWorkoutActivity.class);
        startActivity(intent);
    }
}