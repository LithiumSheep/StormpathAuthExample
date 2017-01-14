package com.lithium.authproject;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.stormpath.sdk.Stormpath;
import com.stormpath.sdk.StormpathCallback;
import com.stormpath.sdk.models.Account;
import com.stormpath.sdk.models.StormpathError;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import timber.log.Timber;

public class DetailActivity extends AppCompatActivity {

    @BindView(R.id.text1)
    TextView email;
    @BindView(R.id.text2)
    TextView name;
    @BindView(R.id.text3)
    TextView status;
    @BindView(R.id.text4)
    TextView token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);

        Stormpath.getAccount(new StormpathCallback<Account>() {
            @Override
            public void onSuccess(Account account) {
                Timber.d("User is logged in with %s" ,account.getEmail());
                displayAccountDetails(account);
            }

            @Override
            public void onFailure(StormpathError error) {
                Timber.e(error.throwable(), "getAccount returned with error");
                Timber.e(error.developerMessage());
            }
        });
    }

    private void displayAccountDetails(Account account) {
        email.setText(account.getEmail());
        name.setText(account.getFullName());
        status.setText(account.getStatus());
        token.setText(Stormpath.getAccessToken());
    }

    @OnClick(R.id.button_logout)
    void logoutPressed() {
        Stormpath.logout();
        onBackPressed();
    }

    @Override
    public void onBackPressed() {
        Stormpath.logout();
        super.onBackPressed();
    }
}
