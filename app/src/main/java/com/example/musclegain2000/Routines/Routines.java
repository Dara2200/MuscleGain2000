package com.example.musclegain2000.Routines;

public class Routines {
    public Routines() {
    }

    String name;
            String [] workouts;
            int imageResource;

    public Routines(String name, String [] workouts, int imageResource ) {


        name=this.name;
        workouts=this.workouts;
        imageResource= this.imageResource;

    }

    public Routines(String name, int resourceId) {

        this.name=name;
        this.imageResource=resourceId;

    }

    public String[] getWorkouts() {
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

   }
