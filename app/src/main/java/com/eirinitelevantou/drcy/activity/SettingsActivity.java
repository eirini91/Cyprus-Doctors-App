package com.eirinitelevantou.drcy.activity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import com.eirinitelevantou.drcy.R;
import com.eirinitelevantou.drcy.util.PrefsHelper;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class SettingsActivity extends BaseActivity {
    @BindView(R.id.img_user)
    CircleImageView imgUser;
    @BindView(R.id.txt_username)
    TextView txtUsername;
    @BindView(R.id.email)
    TextView email;
    @BindView(R.id.switch_anonymous)
    Switch switchAnonymous;

    @BindView(R.id.btn_save)
    Button btnSave;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        ButterKnife.bind(this);
        setTitle(R.string.settings);

        firebaseAuth = FirebaseAuth.getInstance();

        if(PrefsHelper.isLoggedIn()){
            switch (PrefsHelper.getLoggedInType()){
                case PrefsHelper.AUTH_TYPE_FIREBASE:{
                    FirebaseUser user = firebaseAuth.getCurrentUser();
                    txtUsername.setText(user.getDisplayName());
                    email.setText(user.getEmail());
                    Picasso.with(this)
                            .load(firebaseAuth.getCurrentUser().getPhotoUrl())
                            .resize(150, 150)
                            .centerCrop()
                            .placeholder(R.drawable.ic_user_white)
                            .into(imgUser);
                    break;
                }
                case PrefsHelper.AUTH_TYPE_GOOGLE:{
                    FirebaseUser user = firebaseAuth.getCurrentUser();
                    txtUsername.setText(user.getDisplayName());
                    email.setText(user.getEmail());
                    Picasso.with(this)
                            .load(firebaseAuth.getCurrentUser().getPhotoUrl())
                            .resize(150, 150)
                            .centerCrop()
                            .placeholder(R.drawable.ic_user_white)
                            .into(imgUser);
                    break;
                }
                case PrefsHelper.AUTH_TYPE_FACEBOOK:{
                    FirebaseUser user = firebaseAuth.getCurrentUser();
                    txtUsername.setText(user.getDisplayName());
                    email.setText(user.getEmail());
                    Picasso.with(this)
                            .load(firebaseAuth.getCurrentUser().getPhotoUrl())
                            .resize(150, 150)
                            .centerCrop()
                            .placeholder(R.drawable.ic_user_white)
                            .into(imgUser);
                    break;
                }
            }
        }


        switchAnonymous.setChecked(PrefsHelper.isUserAlwaysAnonymous());
        switchAnonymous.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                PrefsHelper.setUserAlwaysAnonymous(isChecked);
            }
        });

    }
}
