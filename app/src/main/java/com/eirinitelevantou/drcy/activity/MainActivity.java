package com.eirinitelevantou.drcy.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.eirinitelevantou.drcy.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

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
    ImageView imageView;
    @BindView(R.id.user_name)
    TextView userName;
    @BindView(R.id.email)
    TextView email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

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

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.main, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }



    @OnClick({R.id.side_search,R.id.all, R.id.top_doctors, R.id.favourites, R.id.my_reviews, R.id.settings, R.id.logout, R.id.info,R.id.search_category, R.id.search_area, R.id.search_rating, R.id.search})
    public void onViewClicked(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.top_doctors:
                break;
            case R.id.favourites:
                break;
            case R.id.my_reviews:
                break;
            case R.id.settings:
                 intent = new Intent(this, SettingsActivity.class);
                startActivity(intent);
                break;
            case R.id.logout:
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
                intent = new Intent(this, AllDoctorsActivity.class);
                startActivity(intent);
                break;
        }
    }
}
