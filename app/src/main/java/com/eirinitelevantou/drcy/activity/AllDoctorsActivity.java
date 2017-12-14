package com.eirinitelevantou.drcy.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.eirinitelevantou.drcy.DrApp;
import com.eirinitelevantou.drcy.R;
import com.eirinitelevantou.drcy.adapter.DoctorAdapter;

import butterknife.BindView;

import static com.eirinitelevantou.drcy.activity.DetailsActivity.KEY_DOCTOR_ID;

public class AllDoctorsActivity extends BaseActivity implements DoctorAdapter.OnDoctorClickedListener {

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    private DoctorAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_specialty);
        setTitle(getString(R.string.all_docs));
        adapter = new DoctorAdapter(this,this,  DrApp.getInstance().getDoctors());
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

    }

    @Override
    public void onDoctorClicked(int doctorId) {
        Intent intent = new Intent(this, DetailsActivity.class);
        intent.putExtra(KEY_DOCTOR_ID, doctorId);
        startActivity(intent);
    }
}
