package com.eirinitelevantou.drcy.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.eirinitelevantou.drcy.R;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class CityListActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_list);
        ButterKnife.bind(this);
        setTitle(getString(R.string.city));
    }

    @OnClick({R.id.nicosia_layout, R.id.limassol_layout, R.id.larnaca_layout, R.id.paphos_layout, R.id.famagusta_layout})
    public void onViewClicked(View view) {
        ArrayList<Integer> city = new ArrayList<>();
        Intent intent = new Intent(this,SearchResultsActivity.class);
        switch (view.getId()) {
            case R.id.nicosia_layout:
                city.add(0);
                break;
            case R.id.limassol_layout:
                city.add(1);

                break;
            case R.id.larnaca_layout:
                city.add(2);
                break;
            case R.id.paphos_layout:
                city.add(3);

                break;
            case R.id.famagusta_layout:
                city.add(4);

                break;
        }

        intent.putExtra(SearchResultsActivity.BUNDLE_KEY_CITIES, city);
        startActivity(intent);
    }
}
