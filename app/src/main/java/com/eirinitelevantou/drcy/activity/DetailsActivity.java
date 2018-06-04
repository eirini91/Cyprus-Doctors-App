package com.eirinitelevantou.drcy.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.apt7.rxpermissions.Permission;
import com.apt7.rxpermissions.PermissionObservable;
import com.contentful.java.cma.CMACallback;
import com.contentful.java.cma.CMAClient;
import com.contentful.java.cma.model.CMAEntry;
import com.eirinitelevantou.drcy.R;
import com.eirinitelevantou.drcy.adapter.ReviewAdapter;
import com.eirinitelevantou.drcy.model.Doctor;
import com.eirinitelevantou.drcy.model.Review;
import com.eirinitelevantou.drcy.model.User;
import com.eirinitelevantou.drcy.util.PrefsHelper;
import com.eirinitelevantou.drcy.util.ProjectUtils;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.thefinestartist.finestwebview.FinestWebView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.observers.DisposableObserver;
import io.realm.Realm;
import io.realm.RealmResults;

import static com.eirinitelevantou.drcy.util.ProjectUtils.capitalize;

public class DetailsActivity extends BaseActivity implements OnMapReadyCallback, ReviewAdapter.OnReviewClickedListener {

    public static String KEY_DOCTOR_ID = "kDoctorId";

    Doctor doctor;
    @BindView(R.id.name)
    TextView name;
    @BindView(R.id.specialty)
    TextView specialty;
    @BindView(R.id.locate_me)
    ImageView locateMe;
    @BindView(R.id.call)
    LinearLayout call;
    @BindView(R.id.web)
    LinearLayout web;
    @BindView(R.id.txt_sex)
    TextView txtSex;
    @BindView(R.id.txt_sector)
    TextView txtSector;
    @BindView(R.id.txt_uni)
    TextView txtUni;
    @BindView(R.id.txt_tel)
    TextView txtTel;
    @BindView(R.id.txt_web)
    TextView txtWeb;
    @BindView(R.id.txt_address)
    TextView txtAddress;
    LatLng latLng;
    @BindView(R.id.rating)
    TextView rating;
    @BindView(R.id.rating_bar)
    RatingBar ratingBar;
    @BindView(R.id.people_count)
    TextView peopleCount;
    @BindView(R.id.five_progress)
    ProgressBar fiveProgress;
    @BindView(R.id.five_people_count)
    TextView fivePeopleCount;
    @BindView(R.id.five_layout)
    LinearLayout fiveLayout;
    @BindView(R.id.four_progress)
    ProgressBar fourProgress;
    @BindView(R.id.four_people_count)
    TextView fourPeopleCount;
    @BindView(R.id.four_layout)
    LinearLayout fourLayout;
    @BindView(R.id.three_progress)
    ProgressBar threeProgress;
    @BindView(R.id.three_people_count)
    TextView threePeopleCount;
    @BindView(R.id.three_layout)
    LinearLayout threeLayout;
    @BindView(R.id.two_progress)
    ProgressBar twoProgress;
    @BindView(R.id.two_people_count)
    TextView twoPeopleCount;
    @BindView(R.id.two_layout)
    LinearLayout twoLayout;
    @BindView(R.id.one_progress)
    ProgressBar oneProgress;
    @BindView(R.id.one_people_count)
    TextView onePeopleCount;
    @BindView(R.id.one_layout)
    LinearLayout oneLayout;
    @BindView(R.id.ratings_layout)
    LinearLayout ratingsLayout;
    @BindView(R.id.write_a_review)
    Button writeAReview;
    @BindView(R.id.reviews_list)
    RecyclerView reviewsList;
    ProgressDialog progressDialog;
    @BindView(R.id.txt_description)
    TextView txtDescription;
    @BindView(R.id.desc_layout)
    LinearLayout descLayout;

    private ReviewAdapter adapter;
    ArrayList<Review> reviews;

    boolean alreadyRated = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        ButterKnife.bind(this);

        doctor = Realm.getDefaultInstance().where(Doctor.class).equalTo("Id", getIntent().getIntExtra(KEY_DOCTOR_ID, -1)).findFirst();
        if (doctor == null) {
            finish();
        }
        // Retrieve the content view that renders the map.
        // Get the SupportMapFragment and request notification
        // when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        populateViews();

        reviews = new ArrayList<>();
        RealmResults<Review> reviewRealmList = Realm.getDefaultInstance().where(Review.class).equalTo("DoctorId", doctor.getId()).equalTo("hide", false).findAll();
        reviews.addAll(reviewRealmList);
        adapter = new ReviewAdapter(this, this, reviews);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        reviewsList.setLayoutManager(mLayoutManager);
        reviewsList.setItemAnimator(new DefaultItemAnimator());
        reviewsList.setAdapter(adapter);

        updateUi();
    }

    private void updateUi() {
        User user = ProjectUtils.getCurrentUser(this);

        if(user!=null) {
            Review results = Realm.getDefaultInstance().where(Review.class).equalTo("Id", user.getUserId() + "-" + doctor.getId()).findFirst();

            if (results == null) {
                writeAReview.setAlpha(1.0f);

            } else {
                writeAReview.setAlpha(0.5f);
                alreadyRated = true;
            }
            double average = Realm.getDefaultInstance().where(Review.class).equalTo("DoctorId", doctor.getId()).average("Rating");

            long allReviewsCount = Realm.getDefaultInstance().where(Review.class).equalTo("DoctorId", doctor.getId()).count();
            long fiveReviewsCount = Realm.getDefaultInstance().where(Review.class).equalTo("DoctorId", doctor.getId()).equalTo("Rating", 5.0).count();
            long fourReviewsCount = Realm.getDefaultInstance().where(Review.class).equalTo("DoctorId", doctor.getId()).equalTo("Rating", 4.0).count();
            long threeReviewsCount = Realm.getDefaultInstance().where(Review.class).equalTo("DoctorId", doctor.getId()).equalTo("Rating", 3.0).count();
            long twoReviewsCount = Realm.getDefaultInstance().where(Review.class).equalTo("DoctorId", doctor.getId()).equalTo("Rating", 2.0).count();
            long oneReviewsCount = Realm.getDefaultInstance().where(Review.class).equalTo("DoctorId", doctor.getId()).equalTo("Rating", 1.0).count();

            int progress1 = oneReviewsCount == 0 ? 0 : (int) ((allReviewsCount * 100.0f) / oneReviewsCount);
            int progress2 = twoReviewsCount == 0 ? 0 : (int) ((allReviewsCount * 100.0f) / twoReviewsCount);
            int progress3 = threeReviewsCount == 0 ? 0 : (int) ((allReviewsCount * 100.0f) / threeReviewsCount);
            int progress4 = fourReviewsCount == 0 ? 0 : (int) ((allReviewsCount * 100.0f) / fourReviewsCount);
            int progress5 = fiveReviewsCount == 0 ? 0 : (int) ((allReviewsCount * 100.0f) / fiveReviewsCount);
            ratingBar.setRating(Float.valueOf(String.valueOf(average)));
            peopleCount.setText(String.format("%d", allReviewsCount));
            rating.setText(average + "");
            fivePeopleCount.setText(fiveReviewsCount + "");
            fourPeopleCount.setText(fourReviewsCount + "");
            threePeopleCount.setText(threeReviewsCount + "");
            twoPeopleCount.setText(twoReviewsCount + "");
            onePeopleCount.setText(oneReviewsCount + "");
            oneProgress.setProgress(progress1);
            twoProgress.setProgress(progress2);
            threeProgress.setProgress(progress3);
            fourProgress.setProgress(progress4);
            fiveProgress.setProgress(progress5);
        }
    }

    private void populateViews() {
        setTitle(capitalize(doctor.getName()));
        if (!TextUtils.isEmpty(doctor.getTel())) {
            txtTel.setText(doctor.getTel());
            call.setEnabled(true);
            call.setAlpha(1f);

        } else {
            txtTel.setText("-");
            call.setEnabled(false);
            call.setAlpha(0.5f);

        }

        if (!TextUtils.isEmpty(doctor.getUni())) {
            txtUni.setText(capitalize(doctor.getUni()));

        } else {
            txtUni.setText("-");
        }

        if (!TextUtils.isEmpty(doctor.getAddress())) {
            txtAddress.setText(capitalize(doctor.getAddress()+", " +doctor.getCityInGreek(this)));

        } else {
            txtAddress.setText(doctor.getCityString(this));
        }


        if (!TextUtils.isEmpty(doctor.getDesc())) {
            txtDescription.setText((doctor.getDesc()));
            descLayout.setVisibility(View.VISIBLE);

        }


        if (!TextUtils.isEmpty(doctor.getWebsite())) {
            txtWeb.setText(doctor.getWebsite());
            web.setEnabled(true);
            web.setAlpha(1f);
        } else {
            txtWeb.setText("-");

            web.setEnabled(false);
            web.setAlpha(0.5f);

        }

        if (doctor.getSex() == 0) {
            txtSex.setText(R.string.male);
        } else if (doctor.getSex() == 1) {
            txtSex.setText(R.string.female);

        } else {
            txtSex.setText("-");

        }

        if (!TextUtils.isEmpty(doctor.getName())) {
            name.setText(capitalize(doctor.getName()));
        } else {
            name.setText("-");

        }
        if (!TextUtils.isEmpty(doctor.getSpeciality())) {
            specialty.setText(capitalize(doctor.getCommaSeparatedSpecialties()));
        } else {
            specialty.setText("-");

        }

        switch (doctor.getSector()) {
            case 0: {
                txtSector.setText(R.string.private_sector);
                break;
            }
            case 1: {
                txtSector.setText(R.string.public_sector);

                break;
            }
            case 2: {
                txtSector.setText(R.string.military);

                break;
            }
            case 3: {
                txtSector.setText(R.string.interchanging);

                break;
            }
            case 4: {
                txtSector.setText(R.string.specialty);
                break;
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuItem edit_item = menu.add(0, 11223344, 0, R.string.favourites);
        edit_item.setIcon(doctor.getFavourite() ? R.drawable.ic_heart_dark : R.drawable.ic_heart_white);
        edit_item.setShowAsActionFlags(MenuItem.SHOW_AS_ACTION_ALWAYS);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        switch (item.getItemId()) {
            case 11223344:
                Realm.getDefaultInstance().executeTransaction(new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {
                        doctor.setFavourite(!doctor.getFavourite());
                        realm.copyToRealmOrUpdate(doctor);

                        item.setIcon(doctor.getFavourite() ? R.drawable.ic_heart_dark : R.drawable.ic_heart_white);
                    }
                });
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        LatLng sydney = getLocationFromAddress(doctor.getAddress());
        if (sydney != null) {
            googleMap.addMarker(new MarkerOptions().position(sydney)
                    .title(getString(R.string.doctor_location)));
            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(sydney, 15.0f));
        }
    }

    public LatLng getLocationFromAddress(String strAddress) {
        if (TextUtils.isEmpty(strAddress))
            strAddress = doctor.getCityString(this);

        Geocoder coder = new Geocoder(this);
        List<Address> address;
        latLng = null;

        try {
            // May throw an IOException
            address = coder.getFromLocationName(strAddress, 5);
            if (address == null || address.size() == 0) {
                address = coder.getFromLocationName("Cyprus", 5);
            }
            Address location = address.get(0);
            location.getLatitude();
            location.getLongitude();

            latLng = new LatLng(location.getLatitude(), location.getLongitude());

        } catch (IOException ex) {

            ex.printStackTrace();
        }

        return latLng;
    }

    @OnClick({R.id.locate_me, R.id.call, R.id.web})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.locate_me:

                Intent intent = new Intent(Intent.ACTION_VIEW,
                        Uri.parse("http://maps.google.com/maps?daddr=" + latLng.latitude + "," + latLng.longitude));
                startActivity(intent);
                break;
            case R.id.call:

                PermissionObservable.getInstance().checkThePermissionStatus(this, Manifest.permission.CALL_PHONE)
                        .subscribe(new DisposableObserver<Permission>() {
                            @SuppressLint("MissingPermission")
                            @Override
                            public void onNext(Permission permission) {
                                System.out.println("Permission Check : " + permission.getName() + " -- " + permission.getGranted());
                                if (permission.getGranted() == 1) {
                                    Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + doctor.getTel()));
                                    startActivity(intent);
                                } else if (permission.getGranted() == 0) {
                                    askForPermissions();
                                    Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + doctor.getTel()));
                                    startActivity(intent);
                                } else {
                                    Toast.makeText(DetailsActivity.this, R.string.call_permissions_required, Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onError(Throwable e) {

                            }

                            @Override
                            public void onComplete() {
                                System.out.println("DONE");
                                // on complete, dispose method automatically un subscribes the subscriber
                                dispose();
                            }
                        });

                break;
            case R.id.web:
                new FinestWebView.Builder(this).showIconBack(true).showIconMenu(true).
                        titleColor(ContextCompat.getColor(this, R.color.colorPrimaryDark)).
                        urlColorRes(android.R.color.white).iconDefaultColorRes(android.R.color.white).
                        toolbarColor(ContextCompat.getColor(this, (R.color.colorPrimary))).
                        statusBarColor(ContextCompat.getColor(this, (R.color.colorPrimaryDark))).
                        show(doctor.getWebsite());
                break;
        }
    }

    public void askForPermissions() {
        PermissionObservable.getInstance().request(this, Manifest.permission.CALL_PHONE)
                .subscribe(new DisposableObserver<Permission>() {

                    @SuppressLint("MissingPermission")
                    @Override
                    public void onNext(Permission permission) {
                        if (permission.getGranted() == 1) {
                            Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + doctor.getTel()));
                            startActivity(intent);
                        } else {
                            Toast.makeText(DetailsActivity.this, R.string.call_permissions_required, Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {
                        System.out.println("DONE");
                        // on complete, dispose method automatically un subscribes the subscriber
                        dispose();
                    }
                });
    }

    @OnClick(R.id.write_a_review)
    public void onViewClicked() {

        if (PrefsHelper.isLoggedIn() && !alreadyRated) {
            MaterialDialog dialog = new MaterialDialog.Builder(this)
                    .title(R.string.write_a_review)
                    .customView(R.layout.layout_review_dialog, true)
                    .neutralText(R.string.cancel)
                    .positiveText(R.string.post)
                    .onPositive(new MaterialDialog.SingleButtonCallback() {
                        @Override
                        public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                            progressDialog = ProgressDialog.show(DetailsActivity.this, "",
                                    "Loading. Please wait...", true);
                            View view = dialog.getCustomView();
                            final RatingBar ratingBar = view.findViewById(R.id.rating_bar);
                            final EditText reviewEditText = view.findViewById(R.id.review);
                            final Switch switchAnonymous = view.findViewById(R.id.switch_anonymous);
                            final CMAClient client =
                                    new CMAClient
                                            .Builder()
                                            .setAccessToken("CFPAT-7ecfa998a067662e9bb0b147f3387afd77e66fc02d09e24dff1b5b60f2fb5d46")
                                            .build();

                            final User currentUser = ProjectUtils.getCurrentUser(DetailsActivity.this);
                            final CMAEntry entry =
                                    new CMAEntry()
                                            .setField("id", "en-US", currentUser.getUserId() + "-" + doctor.getId())
                                            .setField("userName", "en-US", currentUser.getUserName())
                                            .setField("review", "en-US", reviewEditText.getText().toString())
                                            .setField("userId", "en-US", currentUser.getUserId())
                                            .setField("isAnonymised", "en-US", switchAnonymous.isChecked())
                                            .setField("rating", "en-US", ratingBar.getRating())
                                            .setField("doctorId", "en-US", doctor.getId())
                                            .setField("userImageUrl", "en-US", currentUser.getImageUrl()).setId(currentUser.getUserId() + "-" + doctor.getId());
                            client.entries()
                                    .async()
                                    .create(
                                            "0igz66otnsb3",
                                            "review",
                                            entry,
                                            new CMACallback<CMAEntry>() {
                                                @Override
                                                protected void onSuccess(CMAEntry entry) {

                                                    client
                                                            .entries()
                                                            .async()
                                                            .publish(
                                                                    entry,
                                                                    new CMACallback<CMAEntry>() {
                                                                        @Override
                                                                        protected void onSuccess(CMAEntry result) {
                                                                            // Successfully published.
                                                                            Toast.makeText(DetailsActivity.this, getString(R.string.review_added), Toast.LENGTH_SHORT).show();
                                                                            progressDialog.dismiss();

                                                                            Realm.getDefaultInstance().executeTransaction(new Realm.Transaction() {
                                                                                @Override
                                                                                public void execute(Realm realm) {
                                                                                    Review review = new Review(currentUser.getUserId() + "-" + doctor.getId(), reviewEditText.getText().toString(), currentUser.getUserId(), switchAnonymous.isChecked(), Double.valueOf(String.valueOf(ratingBar.getRating())), doctor.getId(), currentUser.getUserName(), currentUser.getImageUrl());
                                                                                    realm.copyToRealmOrUpdate(review);
                                                                                    reviews.add(review);
                                                                                    adapter.notifyDataSetChanged();
                                                                                    updateUi();
                                                                                }
                                                                            });
                                                                        }

                                                                        @Override
                                                                        protected void onFailure(RuntimeException exception) {
                                                                            // An error occurred! Inform the user.
                                                                            new AlertDialog.Builder(DetailsActivity.this)
                                                                                    .setTitle("Contentful Error")
                                                                                    .setMessage("Could not publish an entry." +
                                                                                            "\n\nReason: " + exception.toString())
                                                                                    .show();
                                                                            progressDialog.dismiss();

                                                                            super.onFailure(exception);
                                                                        }
                                                                    }
                                                            );
                                                }

                                                @Override
                                                protected void onFailure(RuntimeException exception) {
                                                    // An error occurred! Inform the user.
                                                    new AlertDialog.Builder(DetailsActivity.this)
                                                            .setTitle("Contentful Error")
                                                            .setMessage("Could not create an entry." +
                                                                    "\n\nReason: " + exception.toString())

                                                            .show();
                                                    progressDialog.dismiss();
                                                    super.onFailure(exception);
                                                }
                                            }
                                    );
                        }
                    })
                    .onNeutral(new MaterialDialog.SingleButtonCallback() {
                        @Override
                        public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                            dialog.dismiss();
                        }
                    })
                    .show();

        } else {
            if (alreadyRated) {
                Toast.makeText(this, R.string.already_rated, Toast.LENGTH_SHORT).show();

            } else {
                Toast.makeText(this, R.string.login_to_leave_a_review, Toast.LENGTH_SHORT).show();

            }
        }
    }

    @Override
    public void onReviewClicked(Review review) {
        LayoutInflater layoutInflater = LayoutInflater.from(this);

        View v = layoutInflater.inflate(R.layout.layout_full_review_dialog, null);
        TextView username = v.findViewById(R.id.username);
        RatingBar ratingBar = v.findViewById(R.id.rating_bar);
        TextView reviewLarge = v.findViewById(R.id.review);

        if (review.getUserName() != null && !review.getAnonymised())
            username.setText((review.getUserName()));
        else {
            username.setText(R.string.anonymous);
        }

        ratingBar.setRating(Float.valueOf(String.valueOf(review.getRating())));
        reviewLarge.setText(review.getReview());
        MaterialDialog dialog = new MaterialDialog.Builder(this)
                .title(R.string.review)
                .customView(v, true)
                .positiveText(android.R.string.ok)
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        dialog.dismiss();
                    }
                })
                .show();
    }

}
