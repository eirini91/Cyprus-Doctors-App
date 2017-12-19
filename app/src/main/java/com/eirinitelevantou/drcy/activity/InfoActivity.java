package com.eirinitelevantou.drcy.activity;

import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.widget.TextView;

import com.eirinitelevantou.drcy.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class InfoActivity extends BaseActivity {

    @BindView(R.id.terms)
    TextView terms;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        ButterKnife.bind(this);
        setTitle(getString(R.string.info_and_terms));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            terms.setText(Html.fromHtml(getString(R.string.terms_long),Html.FROM_HTML_MODE_COMPACT));
        }

        else
        {
            terms.setText(Html.fromHtml(getString(R.string.terms_long)));
        }
    }
}
