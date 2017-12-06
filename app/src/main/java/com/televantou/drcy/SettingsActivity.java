package com.televantou.drcy;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.roughike.swipeselector.SwipeItem;
import com.roughike.swipeselector.SwipeSelector;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        //settings code
        SwipeSelector swipeSelector = (SwipeSelector) findViewById(R.id.swipe_city);
        swipeSelector.setItems(
                // The first argument is the value for that item, and should in most cases be unique for the
                // current SwipeSelector, just as you would assign values to radio buttons.
                // You can use the value later on to check what the selected item was.
                // The value can be any Object, here we're using ints.
                new SwipeItem(0, getString(R.string.nicosia), null),
                new SwipeItem(1, getString(R.string.limassol), null),
                new SwipeItem(2, getString(R.string.larnaca), null),
                new SwipeItem(2, getString(R.string.paphos), null),
                new SwipeItem(2, getString(R.string.famagusta), null)

        );

        swipeSelector = (SwipeSelector) findViewById(R.id.swipe_language);
        swipeSelector.setItems(
                // The first argument is the value for that item, and should in most cases be unique for the
                // current SwipeSelector, just as you would assign values to radio buttons.
                // You can use the value later on to check what the selected item was.
                // The value can be any Object, here we're using ints.
                new SwipeItem(0, getString(R.string.english), null),
                new SwipeItem(1, getString(R.string.greek), null)

        );
    }
}
