package com.eirinitelevantou.drcy.activity;

import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.eirinitelevantou.drcy.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BaseActivity extends AppCompatActivity {
    @Nullable
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @Nullable
    @BindView(R.id.title)
    TextView txtTitle;
    @Nullable
    @BindView(R.id.logo)
    ImageView logo;

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        ButterKnife.bind(this);
        if(toolbar!=null)
            setSupportActionBar(toolbar);
        if(!(this instanceof MainActivity)){
            if(getSupportActionBar()!=null) {
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                getSupportActionBar().setDisplayShowHomeEnabled(true);

            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void setTitle(CharSequence title) {
        if(txtTitle!=null){
            txtTitle.setText(title);
            txtTitle.setVisibility(View.VISIBLE);
            logo.setVisibility(View.GONE);
        }else{
            super.setTitle(title);
        }
    }

    @Override
    public void setTitle(int titleId) {
        if(txtTitle!=null){
            txtTitle.setText(getString(titleId));
            txtTitle.setVisibility(View.VISIBLE);
            logo.setVisibility(View.GONE);
        }else{
            super.setTitle(titleId);
        }
    }
}
