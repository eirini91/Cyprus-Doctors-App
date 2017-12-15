package com.eirinitelevantou.drcy.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.apt7.rxpermissions.Permission;
import com.apt7.rxpermissions.PermissionObservable;
import com.eirinitelevantou.drcy.R;
import com.eirinitelevantou.drcy.model.Doctor;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.thefinestartist.finestwebview.FinestWebView;

import java.io.IOException;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.observers.DisposableObserver;
import io.realm.Realm;

import static com.eirinitelevantou.drcy.util.ProjectUtils.capitalize;

public class DetailsActivity extends BaseActivity implements OnMapReadyCallback {

    public static String KEY_DOCTOR_ID = "kDoctorId";

    Doctor doctor;
    @BindView(R.id.name)
    TextView name;
    @BindView(R.id.specialty)
    TextView specialty;
    @BindView(R.id.locate_me)
    ImageView locateMe;
    @BindView(R.id.call)
    LinearLayout call;
    @BindView(R.id.web)
    LinearLayout web;
    @BindView(R.id.txt_sex)
    TextView txtSex;
    @BindView(R.id.txt_sector)
    TextView txtSector;
    @BindView(R.id.txt_uni)
    TextView txtUni;
    @BindView(R.id.txt_tel)
    TextView txtTel;
    @BindView(R.id.txt_web)
    TextView txtWeb;
    @BindView(R.id.txt_address)
    TextView txtAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        ButterKnife.bind(this);

        doctor = Realm.getDefaultInstance().where(Doctor.class).equalTo("Id", getIntent().getIntExtra(KEY_DOCTOR_ID, -1)).findFirst();
        if (doctor == null) {
            finish();
        }
        // Retrieve the content view that renders the map.
        // Get the SupportMapFragment and request notification
        // when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        populateViews();
    }

    private void populateViews() {
        setTitle(capitalize(doctor.getName()));
        if (!TextUtils.isEmpty(doctor.getTel())) {
            txtTel.setText(doctor.getTel());
            call.setEnabled(true);
            call.setAlpha(1f);

        } else {
            txtTel.setText("-");
            call.setEnabled(false);
            call.setAlpha(0.5f);

        }

        if (!TextUtils.isEmpty(doctor.getUni())) {
            txtUni.setText(capitalize(doctor.getUni()));

        } else {
            txtUni.setText("-");
        }

        if (!TextUtils.isEmpty(doctor.getAddress())) {
            txtAddress.setText(capitalize(doctor.getAddress()));

        } else {
            txtAddress.setText(doctor.getCityString(this));
        }

        if (!TextUtils.isEmpty(doctor.getWebsite())) {
            txtWeb.setText(doctor.getWebsite());
            web.setEnabled(true);
            web.setAlpha(1f);
        } else {
            txtWeb.setText("-");

            web.setEnabled(false);
            web.setAlpha(0.5f);

        }

        if (doctor.getSex() == 0) {
            txtSex.setText(R.string.male);
        } else if (doctor.getSex() == 1) {
            txtSex.setText(R.string.female);

        } else {
            txtSex.setText("-");

        }

        if (!TextUtils.isEmpty(doctor.getName())) {
            name.setText(capitalize(doctor.getName()));
        } else {
            name.setText("-");

        }
        if (!TextUtils.isEmpty(doctor.getSpeciality())) {
            specialty.setText(capitalize(doctor.getCommaSeparatedSpecialties()));
        } else {
            specialty.setText("-");

        }

        switch (doctor.getSector()) {
            case 0: {
                txtSector.setText(R.string.private_sector);
                break;
            }
            case 1: {
                txtSector.setText(R.string.public_sector);

                break;
            }
            case 2: {
                txtSector.setText(R.string.military);

                break;
            }
            case 3: {
                txtSector.setText(R.string.interchanging);

                break;
            }
            case 4: {
                txtSector.setText(R.string.specialty);
                break;
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){

        MenuItem edit_item = menu.add(0, 11223344, 0, R.string.favourites);
        edit_item.setIcon(doctor.getFavourite()?R.drawable.ic_heart_dark:R.drawable.ic_heart_white);
        edit_item.setShowAsActionFlags(MenuItem.SHOW_AS_ACTION_ALWAYS);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        switch (item.getItemId()) {
            case 11223344:
                Realm.getDefaultInstance().executeTransaction(new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {
                        doctor.setFavourite(!doctor.getFavourite());
                        realm.copyToRealmOrUpdate(doctor);

                        item.setIcon(doctor.getFavourite()?R.drawable.ic_heart_dark:R.drawable.ic_heart_white);
                    }
                });
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        LatLng sydney = getLocationFromAddress(doctor.getAddress());
        googleMap.addMarker(new MarkerOptions().position(sydney)
                .title(getString(R.string.doctor_location)));
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(sydney, 15.0f));

    }

    public LatLng getLocationFromAddress(String strAddress) {
        if (TextUtils.isEmpty(strAddress))
            strAddress = doctor.getCityString(this);

        Geocoder coder = new Geocoder(this);
        List<Address> address;
        LatLng p1 = null;

        try {
            // May throw an IOException
            address = coder.getFromLocationName(strAddress, 5);
            if (address == null || address.size()==0) {
                address = coder.getFromLocationName("Cyprus", 5);
            }
            Address location = address.get(0);
            location.getLatitude();
            location.getLongitude();

            p1 = new LatLng(location.getLatitude(), location.getLongitude());

        } catch (IOException ex) {

            ex.printStackTrace();
        }

        return p1;
    }

    @OnClick({R.id.locate_me, R.id.call, R.id.web})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.locate_me:
                break;
            case R.id.call:

                PermissionObservable.getInstance().checkThePermissionStatus(this, Manifest.permission.CALL_PHONE)
                        .subscribe(new DisposableObserver<Permission>() {
                            @SuppressLint("MissingPermission")
                            @Override
                            public void onNext(Permission permission) {
                                System.out.println("Permission Check : " + permission.getName() + " -- " + permission.getGranted());
                                if(permission.getGranted()==1) {
                                    Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + doctor.getTel()));
                                    startActivity(intent);
                                }else if(permission.getGranted()==0) {
                                    askForPermissions();
                                    Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + doctor.getTel()));
                                    startActivity(intent);
                                }else{
                                   Toast.makeText(DetailsActivity.this, R.string.call_permissions_required,Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onError(Throwable e) {

                            }

                            @Override
                            public void onComplete() {
                                System.out.println("DONE");
                                // on complete, dispose method automatically un subscribes the subscriber
                                dispose();
                            }
                        });




                break;
            case R.id.web:
                new FinestWebView.Builder(this).showIconBack(true).showIconMenu(true).
                        titleColor(ContextCompat.getColor(this,R.color.colorPrimaryDark)).
                        urlColorRes(android.R.color.white).iconDefaultColorRes(android.R.color.white).
                        toolbarColor(ContextCompat.getColor(this,(R.color.colorPrimary))).
                        statusBarColor(ContextCompat.getColor(this,(R.color.colorPrimaryDark))).
                        show(doctor.getWebsite());
                break;
        }
    }

    public void askForPermissions(){
        PermissionObservable.getInstance().request(this, Manifest.permission.CALL_PHONE)
                .subscribe(new DisposableObserver<Permission>() {

                    @SuppressLint("MissingPermission")
                    @Override
                    public void onNext(Permission permission) {
                        if(permission.getGranted()==1) {
                            Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + doctor.getTel()));
                            startActivity(intent);
                        }else
                        {
                            Toast.makeText(DetailsActivity.this, R.string.call_permissions_required,Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {
                        System.out.println("DONE");
                        // on complete, dispose method automatically un subscribes the subscriber
                        dispose();
                    }
                });
    }
}
