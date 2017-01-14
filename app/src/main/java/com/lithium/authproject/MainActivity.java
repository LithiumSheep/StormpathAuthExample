package com.lithium.authproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;

import com.stormpath.sdk.Provider;
import com.stormpath.sdk.Stormpath;
import com.stormpath.sdk.StormpathCallback;
import com.stormpath.sdk.StormpathConfiguration;
import com.stormpath.sdk.models.Account;
import com.stormpath.sdk.models.RegistrationForm;
import com.stormpath.sdk.models.StormpathError;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import timber.log.Timber;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.et_email)
    EditText emailField;
    @BindView(R.id.et_password)
    EditText passwordField;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        printBuildVariant();
        checkStormpathAuthStatus();
    }

    private void printBuildVariant() {
        Timber.d("BuildVariant is %s", BuildConfig.BUILD_TYPE);
    }

    private void checkStormpathAuthStatus() {
        Stormpath.getAccount(new StormpathCallback<Account>() {
            @Override
            public void onSuccess(Account account) {
                Timber.d("There is a logged in user");
                goToDetailActivity();
            }

            @Override
            public void onFailure(StormpathError error) {
                Timber.e(error.message());
                Timber.d("There is no logged in user");
            }
        });
    }

    @OnClick(R.id.button_login)
    void loginPressed() {
        Timber.d("login user");
        String email = emailField.getText().toString();
        String password = passwordField.getText().toString();
        loginUserWithCallback(email, password);
    }

    @OnClick(R.id.button_register)
    void registerPressed() {
        Timber.d("register user");
        String email = emailField.getText().toString();
        String password = passwordField.getText().toString();
        RegistrationForm registrationForm = new RegistrationForm(email, password);
        registerUserWithCallback(registrationForm);
    }

    @OnClick(R.id.button_facebook)
    void facebookPressed() {
        loginWithFacebookCallback();
    }

    @OnClick(R.id.button_google)
    void googlePressed() {
        loginWithGoogleCallback();
    }

    private void loginUserWithCallback(String email, String password) {
        Stormpath.login(email, password, new StormpathCallback<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Timber.d("Login SUCCESS");
                goToDetailActivity();
            }

            @Override
            public void onFailure(StormpathError error) {
                Timber.d("LOGIN FAILURE %s", error.message());
            }
        });
    }

    /**
     * TODO: Sanitize inputs - what happens with empty, null, only spaces, etc.
     */
    private void registerUserWithCallback(RegistrationForm form) {
        Stormpath.register(form, new StormpathCallback<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Timber.d("Registration SUCCESS");
                goToDetailActivity();
            }

            @Override
            public void onFailure(StormpathError error) {
                Timber.d("REGISTER FAILURE %s", error.message());
            }
        });
    }

    private void loginWithFacebookCallback() {
        Stormpath.loginWithProvider(Provider.FACEBOOK, this, new StormpathCallback<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                goToDetailActivity();
                Timber.d("FACEBOOK AUTH SUCCESS");
            }

            @Override
            public void onFailure(StormpathError error) {
                Timber.d("FACEBOOK FAILURE %s", error.message());
            }
        });
    }

    private void loginWithGoogleCallback() {
        Stormpath.loginWithProvider(Provider.GOOGLE, this, new StormpathCallback<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                goToDetailActivity();
                Timber.d("GOOGLE AUTH SUCCESS");
            }

            @Override
            public void onFailure(StormpathError error) {
                Timber.d("GOOGLE FAILURE %s", error.message());
            }
        });
    }

    private void goToDetailActivity() {
        startActivity(new Intent(this, DetailActivity.class));
    }
}
