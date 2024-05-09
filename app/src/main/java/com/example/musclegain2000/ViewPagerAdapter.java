package com.example.musclegain2000;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.musclegain2000.Routines.RoutinesFragment;
import com.example.musclegain2000.workout.Home_fragment;

public class ViewPagerAdapter extends FragmentStateAdapter {
    public ViewPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
      switch (position) {

          case 0: return new Home_fragment();
          case 1: return new RoutinesFragment();
          case 2: return new SettingsFragment();
          default: return new Home_fragment();
      }

    }

    @Override
    public int getItemCount() {
        return 3;
    }





}
