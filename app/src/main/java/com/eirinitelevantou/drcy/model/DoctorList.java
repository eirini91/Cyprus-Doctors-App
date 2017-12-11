package com.eirinitelevantou.drcy.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by eirini.televantou on 07/12/2017.
 */

public class DoctorList {


    @SerializedName("doctors")
    private ArrayList<Doctor> doctors;

    public ArrayList<Doctor> getDoctors() {
        return doctors;
    }

    public void setDoctors(ArrayList<Doctor> doctors) {
        this.doctors = doctors;
    }
}
