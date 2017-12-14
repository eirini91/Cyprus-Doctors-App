package com.eirinitelevantou.drcy.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.eirinitelevantou.drcy.DrApp;
import com.eirinitelevantou.drcy.R;
import com.eirinitelevantou.drcy.adapter.SpecialtyAdapter;
import com.eirinitelevantou.drcy.model.Specialty;

import java.util.ArrayList;

import butterknife.BindView;

public class SpecialtyActivity extends BaseActivity implements SpecialtyAdapter.OnSpecialtyClickedListener {

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    private SpecialtyAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_specialty);
        setTitle(getString(R.string.specialty));
        adapter = new SpecialtyAdapter(this,this, DrApp.getInstance().getSpecialtyArrayList());
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);



    }

    @Override
    public void onSpecialtyClicked(Specialty specialty) {
        ArrayList<Integer> category = new ArrayList<>();
        Intent intent = new Intent(this,SearchResultsActivity.class);
        category.add(specialty.getId());
        intent.putExtra(SearchResultsActivity.BUNDLE_KEY_SPECIALTIES, category);
        startActivity(intent);
    }
}
