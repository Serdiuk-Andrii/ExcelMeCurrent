package com.example.excelme;

import android.graphics.drawable.Drawable;

public class Exercise {
    public String eType;
    public String eComplexity;
    public String eGender;
    public String eExercise;
    public int eTime;
    public String eDescription;
    private final Drawable eResultImage;

    public Exercise(String eType, String eComplexity, String eGender, String eExercise, int eTime, String eDescription, Drawable rResultImage) {
        this.eType = eType;
        this.eComplexity = eComplexity;
        this.eGender = eGender;
        this.eExercise = eExercise;
        this.eTime = eTime;
        this.eDescription = eDescription;
        this.eResultImage = rResultImage;
    }

    public String geteType() {
        return eType;
    }

    public String geteComplexity() {
        return eComplexity;
    }

    public String geteGender() {
        return eGender;
    }

    public String geteExercise() {
        return eExercise;
    }

    public int geteTime() {
        return eTime;
    }

    public String geteDescription() {
        return eDescription;
    }

    public Drawable geteResultImage() {
        return eResultImage;
    }

    @Override
    public String toString() {
        return "Exercise{" +
                "eType='" + eType + '\'' +
                ", eComplexity='" + eComplexity + '\'' +
                ", eGender='" + eGender + '\'' +
                ", eExercise='" + eExercise + '\'' +
                ", eTime=" + eTime +
                ", eDescription='" + eDescription + '\'' +
                +'}';
    }
}
