package com.eirinitelevantou.drcy;

import android.app.Application;

import com.contentful.java.cda.CDAClient;
import com.eirinitelevantou.drcy.model.Doctor;
import com.eirinitelevantou.drcy.model.DoctorList;
import com.eirinitelevantou.drcy.model.Specialty;
import com.eirinitelevantou.drcy.util.Prefs;
import com.eirinitelevantou.drcy.util.PrefsHelper;
import com.facebook.appevents.AppEventsLogger;
import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;

/**
 * Created by Eirini Televantou on 12/11/2017.
 * televantou91@gmail.com
 * For Castleton Technology PLC
 */

public class DrApp extends Application {
    ArrayList<Doctor> doctors = new ArrayList<>();
    private List<Specialty> specialtyArrayList = new ArrayList<>();

    private static DrApp drApp;

    private CDAClient client;

    public static DrApp getInstance() {
        return drApp;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Realm.init(this);
        AppEventsLogger.activateApp(this);
        RealmConfiguration config = new RealmConfiguration.Builder()
                .deleteRealmIfMigrationNeeded()
                .build();
        Realm.setDefaultConfiguration(config);
        Prefs.initPrefs(getApplicationContext());

        if (PrefsHelper.isFirstRun()) {
            Realm.getDefaultInstance().executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    realm.delete(Doctor.class);
                    PrefsHelper.setFirstRun(false);
                }
            });
        }

        drApp = this;
        setDoctors();
        setSpecialties();

        client = CDAClient.builder()
                .setSpace("0igz66otnsb3")
                .setToken("49ac1564b9cf146bedea212f09e59499940419d65d383d28087da33449ffef7c")
                .build();

    }

    public CDAClient getClient() {
        return client;
    }

    public void setClient(CDAClient client) {
        this.client = client;
    }

    private void setSpecialties() {

        String[] specialties = getResources().getStringArray(R.array.specialties);
        int[] specialtiesId = getResources().getIntArray(R.array.specialties_ints);
        ArrayList<Integer> drawables = new ArrayList<>();
        drawables.add(R.drawable.vascular_surgery);
        drawables.add(R.drawable.blood_sample);
        drawables.add(R.drawable.x_ray);
        drawables.add(R.drawable.x_ray);
        drawables.add(R.drawable.x_ray);
        drawables.add(R.drawable.allergy);
        drawables.add(R.drawable.syringe);
        drawables.add(R.drawable.intestines);
        drawables.add(R.drawable.stethoscope);
        drawables.add(R.drawable.scalpel);
        drawables.add(R.drawable.old_man);
        drawables.add(R.drawable.derma);
        drawables.add(R.drawable.stethoscope);
        drawables.add(R.drawable.microbe);
        drawables.add(R.drawable.stethoscope);
        drawables.add(R.drawable.stethoscope);
        drawables.add(R.drawable.lungs);
        drawables.add(R.drawable.stethoscope);
        drawables.add(R.drawable.microbe);
        drawables.add(R.drawable.mirror);
        drawables.add(R.drawable.stethoscope);
        drawables.add(R.drawable.heart);
        drawables.add(R.drawable.heart);
        drawables.add(R.drawable.bacteria);
        drawables.add(R.drawable.breast);
        drawables.add(R.drawable.bacteria);
        drawables.add(R.drawable.brain);
        drawables.add(R.drawable.brain);
        drawables.add(R.drawable.scalpel);
        drawables.add(R.drawable.kidney);
        drawables.add(R.drawable.kneecap);
        drawables.add(R.drawable.uterus);
        drawables.add(R.drawable.scalpel);
        drawables.add(R.drawable.eye);
        drawables.add(R.drawable.stethoscope);
        drawables.add(R.drawable.stethoscope);
        drawables.add(R.drawable.stethoscope);
        drawables.add(R.drawable.stethoscope);
        drawables.add(R.drawable.girl);
        drawables.add(R.drawable.girl);
        drawables.add(R.drawable.girl);
        drawables.add(R.drawable.scalpel);
        drawables.add(R.drawable.lungs);
        drawables.add(R.drawable.blood_sample);
        drawables.add(R.drawable.knee);
        drawables.add(R.drawable.articulation);
        drawables.add(R.drawable.stethoscope);
        drawables.add(R.drawable.crutches);
        drawables.add(R.drawable.scalpel);
        drawables.add(R.drawable.scalpel);
        drawables.add(R.drawable.scalpel);
        drawables.add(R.drawable.brain);
        drawables.add(R.drawable.otoscope);

        for (int i = 0; i < specialties.length; i++) {
            Specialty specialty = new Specialty(specialtiesId[i], specialties[i], drawables.get(i));
            specialtyArrayList.add(specialty);
        }

    }

    public ArrayList<Doctor> getDoctors() {
        return doctors;
    }

    private void setDoctors() {
        RealmResults<Doctor> doctorRealmResults = Realm.getDefaultInstance().where(Doctor.class).findAll();

        if (doctorRealmResults == null || doctorRealmResults.size() == 0) {
            doctors = new Gson().fromJson(loadJSONFromAsset(), DoctorList.class).getDoctors();

            for (Doctor doctor : doctors) {
                doctor.setRating(0.0);
            }
            Realm.getDefaultInstance().executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    realm.copyToRealmOrUpdate(doctors);
                }
            });
        } else {
            doctors.addAll(doctorRealmResults);
        }
    }

    public void setDoctors(ArrayList<Doctor> doctors) {
        this.doctors = doctors;
    }

    public String loadJSONFromAsset() {
        String json = null;
        try {
            InputStream is = getAssets().open("drjson.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }

    public List<Specialty> getSpecialtyArrayList() {
        return specialtyArrayList;
    }

    public void setSpecialtyArrayList(List<Specialty> specialtyArrayList) {
        this.specialtyArrayList = specialtyArrayList;
    }
}
