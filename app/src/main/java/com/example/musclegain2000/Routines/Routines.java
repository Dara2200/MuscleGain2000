package com.example.musclegain2000.Routines;

import java.util.List;

public class Routines {



    String name;
    List<String> workouts;
    int imageResource;

    public Routines(String name, List<String> workouts, int image) {


        this.name=name;
        this.workouts=workouts;
        this.imageResource=image;

    }

    public Routines(String names, int image) {

        this.name=names;
        this.imageResource=image;

    }
    public Routines() {
    }

    public Routines(String s) {
        this.name=s;

    }

    public Routines(String editTextValue, List<String> listViewData) {
        this.name=editTextValue;
        this.workouts=listViewData;
    }


    public List<String> getWorkouts() {
        return workouts;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getImageResource() {
        return imageResource;
    }

    public void setWorkouts(List<String> workouts) {
        this.workouts = workouts;
    }

    public void setImageResource(int imageResource) {
        this.imageResource = imageResource;
    }
}


