package com.eirinitelevantou.drcy.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.eirinitelevantou.drcy.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RegisterActivity extends AppCompatActivity {
    private static final String TAG = "RegisterActivity";
    private FirebaseAuth firebaseAuth;

    @BindView(R.id.username)
    AutoCompleteTextView username;
    @BindView(R.id.email)
    AutoCompleteTextView email;
    @BindView(R.id.password)
    EditText password;
    @BindView(R.id.confirm_password)
    EditText confirmPassword;
    @BindView(R.id.email_sign_up_button)
    Button emailSignUpButton;
    @BindView(R.id.fb)
    Button fb;
    @BindView(R.id.google)
    Button google;

    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        firebaseAuth = FirebaseAuth.getInstance();
    }

    @OnClick({R.id.email_sign_up_button, R.id.email_login_button,})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.email_sign_up_button:
                progressDialog = ProgressDialog.show(RegisterActivity.this, "",
                        "Loading. Please wait...", true);
                validateFields();

                break;
            case R.id.email_login_button:
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                break;

        }
    }

    private void validateFields() {
        String displayName = username.getText().toString();
        String emailText = email.getText().toString();
        String passwordText = password.getText().toString();
        String confirmPasswordText = confirmPassword.getText().toString();

        boolean isDataOk =true;
        if(TextUtils.isEmpty(displayName)){
            username.setError(getString(R.string.emtpy_field_warning));
            isDataOk = false;
        }else if(displayName.length()<6){
            username.setError(getString(R.string.username_short_error));
            isDataOk = false;

        }
        if(TextUtils.isEmpty(emailText)){
            email.setError(getString(R.string.emtpy_field_warning));
            isDataOk = false;

        }else if(!emailText.contains("@")|| !emailText.contains(".")){
            isDataOk = false;
            email.setError(getString(R.string.email_invalid_error));

        }
        if(TextUtils.isEmpty(passwordText)){
            password.setError(getString(R.string.emtpy_field_warning));
            isDataOk = false;

        }else if(passwordText.length()<6){
            isDataOk = false;
            password.setError(getString(R.string.password_short_error));

        }
        if(TextUtils.isEmpty(confirmPasswordText)){
            confirmPassword.setError(getString(R.string.emtpy_field_warning));
            isDataOk = false;

        }else if(!confirmPasswordText.equals(passwordText)){
            isDataOk = false;
            confirmPassword.setError(getString(R.string.password_missmatch_error));

        }


        if(isDataOk) {
            createUser();
        }else{
            progressDialog.dismiss();
        }
    }

    private void createUser() {
        firebaseAuth.createUserWithEmailAndPassword(email.getText().toString(), password.getText().toString())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser user = firebaseAuth.getCurrentUser();

                            if (user != null) {
                                UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                        .setDisplayName(username.getText().toString()).build();
                                user.updateProfile(profileUpdates).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        sendEmailVerification();
                                    }
                                });

                            }
                        } else {
                            MaterialDialog dialog = new MaterialDialog.Builder(RegisterActivity.this)
                                    .title(R.string.auth_failed)
                                    .content(task.getException().getLocalizedMessage())
                                    .positiveText(android.R.string.ok)
                                    .show();
                            progressDialog.dismiss();

                        }

                    }
                });
    }

    private void sendEmailVerification() {

        final FirebaseUser user = firebaseAuth.getCurrentUser();
        user.sendEmailVerification()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        if (task.isSuccessful()) {

                            MaterialDialog dialog = new MaterialDialog.Builder(RegisterActivity.this)
                                    .title(getString(R.string.verification_email_sent) + user.getEmail())
                                    .content(R.string.please_check_emails_to_verify)
                                    .positiveText(R.string.login)
                                    .neutralText(R.string.resend_email)
                                    .cancelable(false)
                                    .onPositive(new MaterialDialog.SingleButtonCallback() {
                                        @Override
                                        public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                            Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                            startActivity(intent);
                                        }
                                    })
                                    .onNeutral(new MaterialDialog.SingleButtonCallback() {
                                        @Override
                                        public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                            sendEmailVerification();
                                        }
                                    })
                                    .show();


                        } else {
                            Log.e(TAG, "sendEmailVerification", task.getException());
                            Toast.makeText(RegisterActivity.this,
                                    "Failed to send verification email.",
                                    Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();

                        }
                    }
                });
    }

}
