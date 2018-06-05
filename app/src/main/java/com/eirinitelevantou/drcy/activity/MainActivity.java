package com.eirinitelevantou.drcy.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.contentful.vault.SyncConfig;
import com.contentful.vault.Vault;
import com.eirinitelevantou.drcy.DrApp;
import com.eirinitelevantou.drcy.R;
import com.eirinitelevantou.drcy.model.CFSpace;
import com.eirinitelevantou.drcy.model.Doctor;
import com.eirinitelevantou.drcy.model.Review;
import com.eirinitelevantou.drcy.model.ReviewCF;
import com.eirinitelevantou.drcy.util.PrefsHelper;
import com.facebook.login.LoginManager;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import io.realm.Realm;

import static com.eirinitelevantou.drcy.activity.SearchResultsActivity.BUNDLE_KEY_FAVOURITES;
import static com.eirinitelevantou.drcy.activity.SearchResultsActivity.BUNDLE_KEY_TOP;

public class MainActivity extends BaseActivity {

    @BindView(R.id.star)
    ImageView star;
    @BindView(R.id.search_top)
    RelativeLayout searchTop;
    @BindView(R.id.category)
    ImageView category;
    @BindView(R.id.search_category)
    RelativeLayout searchCategory;
    @BindView(R.id.area)
    ImageView area;
    @BindView(R.id.search_area)
    RelativeLayout searchArea;
    @BindView(R.id.rating)
    ImageView rating;
    @BindView(R.id.search_rating)
    RelativeLayout searchRating;
    @BindView(R.id.search_image)
    ImageView searchImage;
    @BindView(R.id.search_text)
    TextView searchText;
    @BindView(R.id.search)
    RelativeLayout search;
    @BindView(R.id.nav_view)
    NavigationView navView;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;
    @BindView(R.id.imageView)
    CircleImageView imageView;
    @BindView(R.id.user_name)
    TextView userName;
    @BindView(R.id.email)
    TextView email;
    @BindView(R.id.top_doctors)
    LinearLayout topDoctors;
    @BindView(R.id.side_search)
    LinearLayout sideSearch;
    @BindView(R.id.all)
    LinearLayout all;
    @BindView(R.id.favourites)
    LinearLayout favourites;
    @BindView(R.id.first_divider)
    View firstDivider;
    @BindView(R.id.my_reviews)
    LinearLayout myReviews;
    @BindView(R.id.settings)
    LinearLayout settings;
    @BindView(R.id.logout)
    LinearLayout logout;

    @BindView(R.id.info)
    LinearLayout info;
    @BindView(R.id.user_layout)
    LinearLayout userLayout;
    @BindView(R.id.txt_logout)
    TextView txtLogout;

    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        firebaseAuth = FirebaseAuth.getInstance();

        setupView();
        fetchReviews();

//        StrictMode.ThreadPolicy policy = new
//                StrictMode.ThreadPolicy.Builder().permitAll().build();
//        StrictMode.setThreadPolicy(policy);
//        StringBuilder total = new StringBuilder();
//
//        for(int i=1; i<2236; i++){
//            // Request a string response from the provided URL.
//            URL url = null;
//            try {
//                url = new URL("http://www.cypriahealth.com/viewdoctors/" +i);
//
//            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//// 2. Open InputStream to connection
//            conn.connect();
//            InputStream in = conn.getInputStream();
//// 3. Download and decode the string response using builder
//            StringBuilder stringBuilder = new StringBuilder();
//            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
//            String line;
//            while ((line = reader.readLine()) != null) {
//                stringBuilder.append(line);
//            }
//                String title = StringUtils.substringBetween(String.valueOf(stringBuilder), "<body>", "</body>");
//                total.append(title);
//            } catch (MalformedURLException e) {
//                e.printStackTrace();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//
//        Log.d(TAG, "onCreate: "+total);
    }

    private static final String TAG = "MainActivity";

    private void fetchReviews() {
        if(isNetworkAvailable()){
        Vault vault = Vault.with(this, CFSpace.class);
        vault.requestSync(SyncConfig.builder().setClient(DrApp.getInstance().getClient()).build());
        List<ReviewCF> reviewCFList = vault.fetch(ReviewCF.class).all();

        Realm.getDefaultInstance().executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                io.realm.RealmResults<Review> results = realm.where(Review.class).findAll();
                results.deleteAllFromRealm();

            }
        });

        for (ReviewCF reviewCF : reviewCFList) {
            final Review review = new Review(reviewCF);
            Realm.getDefaultInstance().executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    realm.copyToRealmOrUpdate(review);
                }
            });
        }

        updateDoctors(DrApp.getInstance().getDoctors());
}
    }

    private boolean isNetworkAvailable() {
    android.net.ConnectivityManager manager =
            (android.net.ConnectivityManager) getSystemService(android.content.Context.CONNECTIVITY_SERVICE);
    android.net.NetworkInfo networkInfo = manager.getActiveNetworkInfo();
    boolean isAvailable = false;
    if (networkInfo != null && networkInfo.isConnected()) {
        // Network is present and connected
        isAvailable = true;
    }
    return isAvailable;
}

    public void updateDoctors(ArrayList<Doctor> allDoctors) {
        for (final Doctor doctor : allDoctors) {

            final double average = Realm.getDefaultInstance().where(Review.class).equalTo("DoctorId", doctor.getId()).equalTo("hide", false).average("Rating");

            if(doctor.getRating()!=average && !Double.isNaN(average))
            Realm.getDefaultInstance().executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    doctor.setRating(average);
                }
            });
        }
    }

    private void setupView() {
        if (PrefsHelper.isLoggedIn()) {
            txtLogout.setText(getString(R.string.logout));
            myReviews.setAlpha(1.0f);
//            settings.setAlpha(1.0f);
            userLayout.setVisibility(View.VISIBLE);

            switch (PrefsHelper.getLoggedInType()) {

                case PrefsHelper.AUTH_TYPE_FIREBASE: {
                    userName.setText(firebaseAuth.getCurrentUser().getDisplayName());
                    email.setText(firebaseAuth.getCurrentUser().getEmail());
                    Picasso.with(this)
                            .load(firebaseAuth.getCurrentUser().getPhotoUrl())
                            .resize(150, 150)
                            .centerCrop()
                            .placeholder(R.drawable.ic_user_white)
                            .into(imageView);
                    break;
                }
                case PrefsHelper.AUTH_TYPE_GOOGLE: {
                    userName.setText(firebaseAuth.getCurrentUser().getDisplayName());
                    email.setText(firebaseAuth.getCurrentUser().getEmail());
                    Picasso.with(this)
                            .load(firebaseAuth.getCurrentUser().getPhotoUrl())
                            .resize(150, 150)
                            .centerCrop()
                            .placeholder(R.drawable.ic_user_white)
                            .into(imageView);
                    break;
                }
                case PrefsHelper.AUTH_TYPE_FACEBOOK: {
                    userName.setText(firebaseAuth.getCurrentUser().getDisplayName());
                    email.setText(firebaseAuth.getCurrentUser().getEmail());
                    Picasso.with(this)
                            .load(firebaseAuth.getCurrentUser().getPhotoUrl())
                            .resize(150, 150)
                            .centerCrop()
                            .placeholder(R.drawable.ic_user_white)
                            .into(imageView);
                    break;
                }
            }

        } else {
            txtLogout.setText(getString(R.string.login));
            userLayout.setVisibility(View.GONE);
            myReviews.setAlpha(0.5f);
//            settings.setAlpha(0.5f);
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @OnClick({R.id.side_search, R.id.all, R.id.top_doctors, R.id.search_top, R.id.favourites, R.id.my_reviews, R.id.settings, R.id.logout, R.id.info, R.id.search_category, R.id.search_area, R.id.search_rating, R.id.search})
    public void onViewClicked(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.top_doctors:
            case R.id.search_top:
                intent = new Intent(this, SearchResultsActivity.class);
                intent.putExtra(BUNDLE_KEY_TOP, true);
                startActivity(intent);
                break;
            case R.id.favourites:
                intent = new Intent(this, SearchResultsActivity.class);
                intent.putExtra(BUNDLE_KEY_FAVOURITES, true);
                startActivity(intent);
                break;
            case R.id.my_reviews:
                if (!PrefsHelper.isLoggedIn()) {
                    Toast.makeText(this, R.string.not_logged_in, Toast.LENGTH_SHORT).show();
                } else {
                    intent = new Intent(this, MyReviewsActivity.class);
                    startActivity(intent);
                }
                break;
            case R.id.settings:
                if (!PrefsHelper.isLoggedIn()) {
                    Toast.makeText(this, R.string.not_logged_in, Toast.LENGTH_SHORT).show();
                } else {
                    intent = new Intent(this, SettingsActivity.class);
                    startActivity(intent);
                }

                break;
            case R.id.logout:
                if (PrefsHelper.isLoggedIn()) {
                    MaterialDialog dialog = new MaterialDialog.Builder(MainActivity.this)
                            .title(R.string.logout)
                            .content(R.string.sure_logout)
                            .positiveText(R.string.logout)
                            .negativeText(R.string.cancel)
                            .onPositive(new MaterialDialog.SingleButtonCallback() {
                                @Override
                                public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                    logout();
                                }
                            })
                            .show();

                } else {
                    intent = new Intent(this, LoginActivity.class);
                    startActivity(intent);
                }

                break;
            case R.id.info:
                intent = new Intent(this, InfoActivity.class);
                startActivity(intent);
                break;
            case R.id.search_category:
                intent = new Intent(this, SpecialtyActivity.class);
                startActivity(intent);
                break;
            case R.id.search_area:
                intent = new Intent(this, CityListActivity.class);
                startActivity(intent);
                break;
            case R.id.search_rating:
                LayoutInflater layoutInflater = LayoutInflater.from(this);

                View v = layoutInflater.inflate(R.layout.layout_review_search, null);
                LinearLayout oneStar = v.findViewById(R.id.one_star);
                LinearLayout twoStar = v.findViewById(R.id.two_star);
                LinearLayout threeStar = v.findViewById(R.id.three_star);
                LinearLayout fourStar = v.findViewById(R.id.four_star);
                LinearLayout fiveStar = v.findViewById(R.id.five_star);



             final   MaterialDialog dialog = new MaterialDialog.Builder(this)
                        .title(R.string.search_by_rating)
                        .neutralText(R.string.cancel)

                        .onNeutral(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                dialog.dismiss();
                            }
                        })

                        .customView(v, true)
                        .show();
                View.OnClickListener onClickListener = new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(MainActivity.this, SearchResultsActivity.class);

                        switch (v.getId()){
                            case R.id.one_star:{
                                intent.putExtra(SearchResultsActivity.BUNDLE_KEY_MIN_RATING,1);

                                break;
                            }
                            case R.id.two_star:{
                                intent.putExtra(SearchResultsActivity.BUNDLE_KEY_MIN_RATING,2);

                                break;
                            }
                            case R.id.three_star:{
                                intent.putExtra(SearchResultsActivity.BUNDLE_KEY_MIN_RATING,3);

                                break;
                            }
                            case R.id.four_star:{
                                intent.putExtra(SearchResultsActivity.BUNDLE_KEY_MIN_RATING,4);

                                break;
                            }
                            case R.id.five_star:{
                                intent.putExtra(SearchResultsActivity.BUNDLE_KEY_MIN_RATING,5);

                                break;
                            }

                        }
                        dialog.dismiss();
                        startActivity(intent);

                    }
                };
                oneStar.setOnClickListener(onClickListener);
                twoStar.setOnClickListener(onClickListener);
                threeStar.setOnClickListener(onClickListener);
                fourStar.setOnClickListener(onClickListener);
                fiveStar.setOnClickListener(onClickListener);
                break;
            case R.id.side_search:
            case R.id.search:
                intent = new Intent(this, SearchActivity.class);
                startActivity(intent);
                break;
            case R.id.all:
                intent = new Intent(this, SearchResultsActivity.class);
                startActivity(intent);
                break;
        }
    }

    private void logout() {
        if (PrefsHelper.isLoggedIn()) {

            switch (PrefsHelper.getLoggedInType()) {
                case PrefsHelper.AUTH_TYPE_FIREBASE: {
                    PrefsHelper.setLoggedIn(false, -1);
                    PrefsHelper.setUserAlwaysAnonymous(false);
                    firebaseAuth.signOut();
                    setupView();
                    break;
                }
                case PrefsHelper.AUTH_TYPE_GOOGLE: {

                    GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                            .requestEmail()
                            .build();
                    GoogleSignInClient googleSignInClient = GoogleSignIn.getClient(this, gso);
                    googleSignInClient.signOut()
                            .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    // ...
                                }
                            });
                    PrefsHelper.setLoggedIn(false, -1);
                    PrefsHelper.setUserAlwaysAnonymous(false);

                    firebaseAuth.signOut();
                    setupView();

                    break;
                }
                case PrefsHelper.AUTH_TYPE_FACEBOOK: {
                    PrefsHelper.setLoggedIn(false, -1);
                    PrefsHelper.setUserAlwaysAnonymous(false);
                    firebaseAuth.signOut();

                    LoginManager.getInstance().logOut();
                    setupView();

                    break;
                }
            }

        }
    }
}
