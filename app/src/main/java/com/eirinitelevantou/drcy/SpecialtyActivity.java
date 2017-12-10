package com.eirinitelevantou.drcy;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.eirinitelevantou.drcy.adapter.SpecialtyAdapter;
import com.eirinitelevantou.drcy.model.Specialty;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class SpecialtyActivity extends BaseActivity {

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    private List<Specialty> movieList = new ArrayList<>();
    private SpecialtyAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_specialty);
        setTitle(getString(R.string.specialty));
        adapter = new SpecialtyAdapter(this, movieList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

        prepareData();
    }

    private void prepareData() {

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
            Specialty specialty = new Specialty(specialtiesId[i],specialties[i], drawables.get(i));
            movieList.add(specialty);
        }


        adapter.notifyDataSetChanged();
    }
}
