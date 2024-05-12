package com.example.musclegain2000.workout;


public class Workout {

    String name;
    String type;

    public Workout() {
    }

    public Workout(String name, String type) {
        this.name = name;
        this.type = type;

    }



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public String getType() {
        return type;
    }


}
