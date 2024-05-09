package com.example.musclegain2000.workout;

import android.media.Image;

public class Workout {

    String name;
    String type;
    int image;

    public Workout() {
    }

    public Workout(String name, String type, int image) {
        this.name = name;
        this.type = type;
        this.image = image;
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
    public int getImage() {
        return image;
    }

}
