package com.eirinitelevantou.drcy.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.eirinitelevantou.drcy.R;
import com.eirinitelevantou.drcy.util.PrefsHelper;
import com.facebook.login.LoginManager;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

import static com.eirinitelevantou.drcy.activity.SearchResultsActivity.BUNDLE_KEY_FAVOURITES;

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
    }

    private void setupView() {
        if (PrefsHelper.isLoggedIn()) {
            txtLogout.setText(getString(R.string.logout));
            myReviews.setAlpha(1.0f);
            settings.setAlpha(1.0f);
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
                    GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(this);

                    Uri personPhoto = acct.getPhotoUrl();
                    Picasso.with(this)
                            .load(personPhoto)
                            .resize(150, 150)
                            .centerCrop()
                            .placeholder(R.drawable.ic_user_white)
                            .into(imageView);
                    userName.setText(acct.getDisplayName());
                    email.setText(acct.getEmail());
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
            settings.setAlpha(0.5f);

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

    @OnClick({R.id.side_search, R.id.all, R.id.top_doctors, R.id.favourites, R.id.my_reviews, R.id.settings, R.id.logout, R.id.info, R.id.search_category, R.id.search_area, R.id.search_rating, R.id.search})
    public void onViewClicked(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.top_doctors:
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
