package com.eirinitelevantou.drcy.activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.contentful.java.cma.CMACallback;
import com.contentful.java.cma.CMAClient;
import com.contentful.java.cma.model.CMAEntry;
import com.eirinitelevantou.drcy.R;
import com.eirinitelevantou.drcy.adapter.MyReviewAdapter;
import com.eirinitelevantou.drcy.model.Doctor;
import com.eirinitelevantou.drcy.model.Review;
import com.eirinitelevantou.drcy.model.User;
import com.eirinitelevantou.drcy.util.PrefsHelper;
import com.eirinitelevantou.drcy.util.ProjectUtils;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;
import io.realm.RealmResults;

import static com.eirinitelevantou.drcy.activity.DetailsActivity.KEY_DOCTOR_ID;

public class MyReviewsActivity extends BaseActivity implements MyReviewAdapter.OnReviewClickedListener {

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.txt_empty)
    TextView txtEmpty;
    private MyReviewAdapter adapter;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_specialty);
        ButterKnife.bind(this);
        setTitle(getString(R.string.my_reviews));
        RealmResults<Review> reviewRealmList = Realm.getDefaultInstance().where(Review.class).equalTo("UserId", ProjectUtils.getCurrentUser(this).getUserId()).findAll();
        ArrayList<Review> reviews = new ArrayList<>();
        reviews.addAll(reviewRealmList);
        if(reviews.size()>0){
            txtEmpty.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
        }else{
            txtEmpty.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);

        }
        adapter = new MyReviewAdapter(this, this, reviews);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

    }

    @Override
    public void onReviewClicked(final Review review) {
        LayoutInflater layoutInflater = LayoutInflater.from(this);
        final Doctor doctor = Realm.getDefaultInstance().where(Doctor.class).equalTo("Id", review.getDoctorId()).findFirst();
        View v = layoutInflater.inflate(R.layout.layout_full_review_dialog, null);
        TextView username = v.findViewById(R.id.username);
        RatingBar ratingBar = v.findViewById(R.id.rating_bar);
        TextView reviewLarge = v.findViewById(R.id.review);
        username.setText(doctor.getName());
        ratingBar.setRating(Float.valueOf(String.valueOf(review.getRating())));
        reviewLarge.setText(review.getReview());
        MaterialDialog dialog = new MaterialDialog.Builder(this)
                .title(R.string.review)
                .customView(v, true)
                .positiveText(R.string.view_doctor)
                .neutralText(android.R.string.ok)
                .negativeText(R.string.edit_review)
                .onNegative(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        dialog.dismiss();
                        updateEntry(review.getId(), review.getDoctorId(), review.getReview(), Float.valueOf(String.valueOf(review.getRating())),review.getAnonymised());
                    }
                })
                .onNeutral(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        dialog.dismiss();
                    }
                })
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        dialog.dismiss();
                        Intent intent = new Intent(MyReviewsActivity.this, DetailsActivity.class);
                        intent.putExtra(KEY_DOCTOR_ID, doctor.getId());
                        startActivity(intent);
                    }
                })
                .show();
    }

    public void updateEntry(final String entryId, final int doctorId, String review, float rating, Boolean anonymous) {

        if (PrefsHelper.isLoggedIn()) {
            LayoutInflater layoutInflater = LayoutInflater.from(this);
            View v = layoutInflater.inflate(R.layout.layout_review_dialog, null);
            final EditText reviewEditText = v.findViewById(R.id.review);
            reviewEditText.setText(review);
            RatingBar ratingBar = v.findViewById(R.id.rating_bar);
            Switch anonymousS = v.findViewById(R.id.switch_anonymous);
            anonymousS.setChecked(anonymous);
            ratingBar.setRating(rating);

            MaterialDialog dialog = new MaterialDialog.Builder(this)
                    .title(R.string.write_a_review)
                    .customView(v, true)
                    .neutralText(R.string.cancel)
                    .positiveText(R.string.post)
                    .onPositive(new MaterialDialog.SingleButtonCallback() {
                        @Override
                        public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                            progressDialog = ProgressDialog.show(MyReviewsActivity.this, "",
                                    "Loading. Please wait...", true);
                            View view = dialog.getCustomView();
                            final RatingBar ratingBar = view.findViewById(R.id.rating_bar);
                            final EditText reviewEditText = view.findViewById(R.id.review);
                            final Switch switchAnonymous = view.findViewById(R.id.switch_anonymous);
                            final User currentUser = ProjectUtils.getCurrentUser(MyReviewsActivity.this);

                            // Create the Contentful client.
                            final CMAClient client =
                                    new CMAClient
                                            .Builder()
                                            .setAccessToken("CFPAT-7ecfa998a067662e9bb0b147f3387afd77e66fc02d09e24dff1b5b60f2fb5d46")
                                            .build();

                            client
                                    .entries()
                                    .async()
                                    .fetchOne(
                                            "0igz66otnsb3",
                                            currentUser.getUserId() + "-" + doctorId,
                                            new CMACallback<CMAEntry>() {
                                                @Override
                                                protected void onSuccess(CMAEntry result) {

                                                    result.setField("id", "en-US", currentUser.getUserId() + "-" + doctorId);
                                                    result.setField("userName", "en-US", currentUser.getUserName());
                                                    result.setField("review", "en-US", reviewEditText.getText().toString());
                                                    result.setField("userId", "en-US", currentUser.getUserId());
                                                    result.setField("isAnonymised", "en-US", switchAnonymous.isChecked());
                                                    result.setField("rating", "en-US", ratingBar.getRating());

                                                    result.setField("doctorId", "en-US", doctorId);
                                                    result.setField("userImageUrl", "en-US", currentUser.getImageUrl());

                                                    client.entries()
                                                            .async()
                                                            .update(
                                                                    result,
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
                                                                                                    Toast.makeText(MyReviewsActivity.this, (getString(R.string.review_updated)), Toast.LENGTH_SHORT).show();
                                                                                                    progressDialog.dismiss();

                                                                                                    Realm.getDefaultInstance().executeTransaction(new Realm.Transaction() {
                                                                                                        @Override
                                                                                                        public void execute(Realm realm) {
                                                                                                            Review review = new Review(currentUser.getUserId() + "-" + doctorId, reviewEditText.getText().toString(), currentUser.getUserId(), switchAnonymous.isChecked(), Double.valueOf(String.valueOf(ratingBar.getRating())), doctorId, currentUser.getUserName(), currentUser.getImageUrl());
                                                                                                            realm.copyToRealmOrUpdate(review);
                                                                                                            adapter.notifyDataSetChanged();

                                                                                                        }
                                                                                                    });
                                                                                                }

                                                                                                @Override
                                                                                                protected void onFailure(RuntimeException exception) {
                                                                                                    // An error occurred! Inform the user.
                                                                                                    new AlertDialog.Builder(MyReviewsActivity.this)
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
                                                                            new AlertDialog.Builder(MyReviewsActivity.this)
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

                                                @Override
                                                protected void onFailure(RuntimeException exception) {

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

        }
    }

}
