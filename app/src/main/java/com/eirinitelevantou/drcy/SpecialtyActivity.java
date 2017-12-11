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
    private SpecialtyAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_specialty);
        setTitle(getString(R.string.specialty));
        adapter = new SpecialtyAdapter(this, DrApp.getInstance().getSpecialtyArrayList());
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

    }



}
