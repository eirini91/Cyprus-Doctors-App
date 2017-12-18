package com.eirinitelevantou.drcy.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.eirinitelevantou.drcy.DrApp;
import com.eirinitelevantou.drcy.R;
import com.eirinitelevantou.drcy.adapter.DoctorAdapter;
import com.eirinitelevantou.drcy.model.Doctor;
import com.eirinitelevantou.drcy.util.ProjectUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.eirinitelevantou.drcy.activity.DetailsActivity.KEY_DOCTOR_ID;

public class SearchResultsActivity extends BaseActivity implements DoctorAdapter.OnDoctorClickedListener {

    public static final String BUNDLE_KEY_SEARCH_TERM = "kSearchTerm";
    public static final String BUNDLE_KEY_MIN_RATING = "kMinRating";
    public static final String BUNDLE_KEY_CITIES = "kCities";
    public static final String BUNDLE_KEY_SPECIALTIES = "kSpecialties";
    public static final String BUNDLE_KEY_SECTOR = "kSector";
    public static final String BUNDLE_KEY_FAVOURITES = "kFavourites";
    public static final String BUNDLE_KEY_TOP = "kTop";

    @BindView(R.id.txt_empty)
    TextView txtEmpty;

    private String searchTearm;
    private int minRating;
    private ArrayList<Integer> cities;
    private ArrayList<Integer> specialties;
    ArrayList<Doctor> doctors = new ArrayList<>();
    private int sector;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    private DoctorAdapter adapter;
    private boolean isFavourite;
    private boolean isTop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_specialty);
        ButterKnife.bind(this);
        setTitle(getString(R.string.results));

        searchTearm = getIntent().getStringExtra(BUNDLE_KEY_SEARCH_TERM);
        minRating = getIntent().getIntExtra(BUNDLE_KEY_MIN_RATING, 0);
        cities = getIntent().getIntegerArrayListExtra(BUNDLE_KEY_CITIES);
        specialties = getIntent().getIntegerArrayListExtra(BUNDLE_KEY_SPECIALTIES);
        sector = getIntent().getIntExtra(BUNDLE_KEY_SECTOR, -1);
        isFavourite = getIntent().getBooleanExtra(BUNDLE_KEY_FAVOURITES, false);
        isTop = getIntent().getBooleanExtra(BUNDLE_KEY_TOP, false);

        adapter = new DoctorAdapter(this, this, doctors);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
        populateData();
    }

    private void populateData() {
        ArrayList<Doctor> allDoctors = DrApp.getInstance().getDoctors();
        if (!isTop) {
            for (Doctor doctor : allDoctors) {
                boolean add = true;
                if (!isFavourite) {

                    if (searchTearm != null && searchTearm.length() > 0) {
                        if (!(doctor.getName().toLowerCase().contains(searchTearm.toLowerCase()))) {
                            add = false;
                        }
                    }

                    if (cities != null && (cities.size() > 0 || cities.size() == 5)) {
                        if (!cities.contains(doctor.getCity())) {
                            add = false;
                        }
                    }

                    if (specialties != null && specialties.size() > 0) {
                        List<Integer> common = new ArrayList<>(specialties);
                        common.retainAll(doctor.getSpecialities());
                        if (common.size() == 0) {
                            add = false;
                        }
                    }

                    if (sector >= 0) {
                        if (!(doctor.getSector() == sector)) {
                            add = false;

                        }
                    }

                    if (minRating > 0) {
                        if ((doctor.getRating() < minRating || doctor.getRating().isNaN())) {
                            add = false;

                        }
                    }

                } else {
                    if (!doctor.getFavourite()) {
                        add = false;

                    }
                }
                if (add) {
                    doctors.add(doctor);
                }

            }
        } else {
            doctors.addAll(ProjectUtils.getTop20(allDoctors, this));
        }

        if (doctors.size() == 0) {
            txtEmpty.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
        } else {
            txtEmpty.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
        }
        adapter.notifyDataSetChanged();

    }

    @Override
    public void onDoctorClicked(int doctorId) {
        Intent intent = new Intent(this, DetailsActivity.class);
        intent.putExtra(KEY_DOCTOR_ID, doctorId);
        startActivity(intent);
    }
}
