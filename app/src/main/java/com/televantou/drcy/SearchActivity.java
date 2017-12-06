package com.televantou.drcy;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.roughike.swipeselector.SwipeItem;
import com.roughike.swipeselector.SwipeSelector;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SearchActivity extends AppCompatActivity {
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);

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
                // The first argument is the value for that item, and should in most cases be unique for the
                // current SwipeSelector, just as you would assign values to radio buttons.
                // You can use the value later on to check what the selected item was.
                // The value can be any Object, here we're using ints.
                new SwipeItem(0, ">1", null),
                new SwipeItem(1, ">2", null),
                new SwipeItem(2, ">3", null),
                new SwipeItem(2, ">4", null),
                new SwipeItem(2, "5", null)

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
}


