package com.eirinitelevantou.drcy;

import android.os.Bundle;

public class CityListActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_list);
        setTitle(getString(R.string.city));
    }
}
