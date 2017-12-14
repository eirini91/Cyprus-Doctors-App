package com.eirinitelevantou.drcy.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.eirinitelevantou.drcy.DrApp;
import com.eirinitelevantou.drcy.R;
import com.eirinitelevantou.drcy.adapter.SpecialtyAdapter;
import com.eirinitelevantou.drcy.model.Specialty;
import com.roughike.swipeselector.SwipeItem;
import com.roughike.swipeselector.SwipeSelector;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SearchActivity extends BaseActivity {
    boolean larnacaSelected = true;
    boolean nicosiaSelected = true;
    boolean limassolSelected = true;
    boolean paphosSelected = true;
    boolean famagustaSelected = true;

    @BindView(R.id.cyprus)
    ImageView cyprus;
    @BindView(R.id.larnaca)
    ImageView larnaca;
    @BindView(R.id.famagusta)
    ImageView famagusta;
    @BindView(R.id.limasol)
    ImageView limasol;
    @BindView(R.id.nicosia)
    ImageView nicosia;
    @BindView(R.id.paphos)
    ImageView paphos;
    @BindView(R.id.txt_nicosia)
    TextView txtNicosia;
    @BindView(R.id.txt_larnaca)
    TextView txtLarnaca;
    @BindView(R.id.txt_limassol)
    TextView txtLimassol;
    @BindView(R.id.text_famagusta)
    TextView txtFamagusta;
    @BindView(R.id.text_paphos)
    TextView txtPaphos;
    @BindView(R.id.name)
    EditText name;
    @BindView(R.id.swipe_rating)
    SwipeSelector swipeRating;
    @BindView(R.id.txt_speciality)
    TextView txtSpeciality;
    @BindView(R.id.layout_speciality)
    LinearLayout layoutSpeciality;
    @BindView(R.id.search)
    Button search;
    @BindView(R.id.swipe_sector)
    SwipeSelector swipeSector;
    @BindView(R.id.icon_specialty)
    ImageView iconSpecialty;

    Specialty selectedSpecialty;
    MaterialDialog specialtyDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);

        setTitle(getString(R.string.search));
        larnaca.setDrawingCacheEnabled(true);
        larnaca.setOnTouchListener(changeColorListener);

        limasol.setDrawingCacheEnabled(true);
        limasol.setOnTouchListener(changeColorListener);

        nicosia.setDrawingCacheEnabled(true);
        nicosia.setOnTouchListener(changeColorListener);

        paphos.setDrawingCacheEnabled(true);
        paphos.setOnTouchListener(changeColorListener);

        famagusta.setDrawingCacheEnabled(true);
        famagusta.setOnTouchListener(changeColorListener);

        txtLarnaca.setOnClickListener(onClickListener);
        txtPaphos.setOnClickListener(onClickListener);
        txtNicosia.setOnClickListener(onClickListener);
        txtLimassol.setOnClickListener(onClickListener);
        txtFamagusta.setOnClickListener(onClickListener);

        swipeRating.setItems(
                new SwipeItem(0, ">0", null),

                new SwipeItem(1, ">1", null),
                new SwipeItem(2, ">2", null),
                new SwipeItem(3, ">3", null),
                new SwipeItem(4, ">4", null),
                new SwipeItem(5, "5", null)

        );
        swipeSector.setItems(
                new SwipeItem(-1, getString(R.string.all_sectors), getString(R.string.all_expl)),

                new SwipeItem(0, getString(R.string.private_sector), getString(R.string.private_expl)),
                new SwipeItem(1, getString(R.string.public_sector), getString(R.string.public_exp)),
                new SwipeItem(2, getString(R.string.military), getString(R.string.military_expl)),
                new SwipeItem(3, getString(R.string.interchanging), getString(R.string.inter_expl)),
                new SwipeItem(4, getString(R.string.specialty), getString(R.string.specialty_expl))

        );
    }

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.txt_larnaca: {
                    larnacaClicked();
                    break;
                }
                case R.id.txt_limassol: {
                    limassolClicked();
                    break;
                }
                case R.id.text_paphos: {
                    paphosClicked();
                    break;
                }
                case R.id.txt_nicosia: {
                    nicosiaClicked();
                    break;
                }
                case R.id.text_famagusta: {
                    famagustaClicked();
                    break;
                }
            }
        }
    };

    private final View.OnTouchListener changeColorListener = new View.OnTouchListener() {

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            if (event.getAction() == MotionEvent.ACTION_UP) {
                Bitmap bmp = Bitmap.createBitmap(v.getDrawingCache());
                int color = bmp.getPixel((int) event.getX(), (int) event.getY());
                if (color == Color.TRANSPARENT) {
                    switch (v.getId()) {
                        case R.id.larnaca: {

                            break;
                        }
                        case R.id.limasol: {
                            famagusta.dispatchTouchEvent(event);

                            break;
                        }
                        case R.id.paphos: {
                            nicosia.dispatchTouchEvent(event);

                            break;
                        }
                        case R.id.nicosia: {
                            limasol.dispatchTouchEvent(event);
                            break;
                        }
                        case R.id.famagusta: {
                            larnaca.dispatchTouchEvent(event);

                            break;
                        }
                    }
                    return false;
                } else {
                    switch (v.getId()) {
                        case R.id.larnaca: {
                            larnacaClicked();
                            break;
                        }
                        case R.id.limasol: {
                            limassolClicked();
                            break;
                        }
                        case R.id.paphos: {
                            paphosClicked();
                            break;
                        }
                        case R.id.nicosia: {
                            nicosiaClicked();
                            break;
                        }
                        case R.id.famagusta: {
                            famagustaClicked();
                            break;
                        }
                    }

                    return true;
                }
            }
            return true;

        }
    };

    private void larnacaClicked() {
        updateView(larnacaSelected, txtLarnaca, larnaca, R.drawable.larnaca, R.drawable.larnaca_hi);
        larnacaSelected = !larnacaSelected;
    }

    private void limassolClicked() {
        updateView(limassolSelected, txtLimassol, limasol, R.drawable.limasol, R.drawable.limasol_hi);
        limassolSelected = !limassolSelected;
    }

    private void paphosClicked() {
        updateView(paphosSelected, txtPaphos, paphos, R.drawable.paphos, R.drawable.paphos_hi);
        paphosSelected = !paphosSelected;
    }

    private void nicosiaClicked() {
        updateView(nicosiaSelected, txtNicosia, nicosia, R.drawable.nicosia, R.drawable.nicosia_hi);
        nicosiaSelected = !nicosiaSelected;
    }

    private void famagustaClicked() {
        updateView(famagustaSelected, txtFamagusta, famagusta, R.drawable.famagusta, R.drawable.famagusta_hi);
        famagustaSelected = !famagustaSelected;
    }

    private void updateView(boolean selected, TextView textView, ImageView imageView, int loDrawable, int hiDrawable) {

        if (selected) {
            textView.setAlpha(0.5f);
            imageView.setImageDrawable(ContextCompat.getDrawable(SearchActivity.this, loDrawable));
        } else {
            textView.setAlpha(1f);

            imageView.setImageDrawable(ContextCompat.getDrawable(SearchActivity.this, hiDrawable));
        }
    }

    @OnClick(R.id.search)
    public void onViewClicked() {

        ArrayList<Integer> city = new ArrayList<>();
        Intent intent = new Intent(this, SearchResultsActivity.class);

        if (nicosiaSelected)
            city.add(0);
        if (limassolSelected)
            city.add(1);
        if (larnacaSelected)
            city.add(2);
        if (paphosSelected)
            city.add(3);
        if (famagustaSelected)
            city.add(4);

        if (name.getText().length() > 0) {
            intent.putExtra(SearchResultsActivity.BUNDLE_KEY_SEARCH_TERM, name.getText().toString());
        }

        if(selectedSpecialty!=null){
            ArrayList<Integer>specialties =  new ArrayList<>();
            specialties.add(selectedSpecialty.getId());
            intent.putExtra(SearchResultsActivity.BUNDLE_KEY_SPECIALTIES, specialties);

        }
        intent.putExtra(SearchResultsActivity.BUNDLE_KEY_MIN_RATING, (int) swipeRating.getSelectedItem().value);
        intent.putExtra(SearchResultsActivity.BUNDLE_KEY_SECTOR, (int) swipeSector.getSelectedItem().value);

        intent.putExtra(SearchResultsActivity.BUNDLE_KEY_CITIES, city);
        startActivity(intent);

    }

    @OnClick(R.id.layout_speciality)
    public void onSpecialtyClicked() {

        Specialty specialty = new Specialty(1000, "All", R.drawable.ic_list);
        List<Specialty> specialtyArrayList = new ArrayList<>();
        specialtyArrayList.add(specialty);
        specialtyArrayList.addAll(DrApp.getInstance().getSpecialtyArrayList());
        SpecialtyAdapter specialtyAdapter = new SpecialtyAdapter(this, new SpecialtyAdapter.OnSpecialtyClickedListener() {
            @Override
            public void onSpecialtyClicked(Specialty specialty) {

                if(specialty.getId()!=1000){
                    selectedSpecialty = specialty;
                }

                txtSpeciality.setText(specialty.getName());
                iconSpecialty.setImageDrawable(ContextCompat.getDrawable(SearchActivity.this,specialty.getDrawable()));
                if(specialtyDialog!=null)
                specialtyDialog.dismiss();

            }
        }, specialtyArrayList);

        MaterialDialog.Builder builder =    new MaterialDialog.Builder(this)
                .title(getString(R.string.specialty))
                .adapter(specialtyAdapter, null);

        specialtyDialog = builder.build();
        specialtyDialog.show();
    }
//    public static final String BUNDLE_KEY_SPECIALTIES = "kSpecialties";

}


