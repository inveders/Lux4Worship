package com.inved.lux4worship.controller.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.ErrorCodes;
import com.firebase.ui.auth.IdpResponse;
import com.google.android.material.snackbar.Snackbar;
import com.inved.lux4worship.R;
import com.inved.lux4worship.base.BaseActivity;
import com.inved.lux4worship.firebase.UserHelper;
import com.inved.lux4worship.utils.ManageChurchPreferences;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.OnClick;

public class ConnexionActivity extends BaseActivity {




    //FOR DESIGN
    @BindView(R.id.coordinatorLayout)
    CoordinatorLayout coordinatorLayout;
    @BindView(R.id.login_facebook_button)
    Button facebookLogin;
    @BindView(R.id.login_google_button)
    Button googleLogin;

    Animation animation;
    //FOR DATA
    private static final int RC_SIGN_IN = 123;

    @Override
    public int getFragmentLayout() {
        return R.layout.activty_connexion;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        animation = AnimationUtils.loadAnimation(this, R.anim.fadein);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        this.handleResponseAfterSignIn(requestCode, resultCode, data);
    }

    @Override
    protected void onResume() {
        super.onResume();
        // this.updateUIWhenGoogleResuming();
    }


    // --------------------
    // ACTIONS
    // --------------------

    @OnClick(R.id.login_facebook_button)
    public void onClickFacebookLoginButton() {
        facebookLogin.startAnimation(animation);
        if (this.isCurrentUserLogged()) {
            this.startMainActivity();
        } else {
            this.startFacebookSignInActivity();
        }
    }

    @OnClick(R.id.login_google_button)
    public void onClickGoogleLoginButton() {
        googleLogin.startAnimation(animation);
        if (this.isCurrentUserLogged()) {

            this.startMainActivity();
        } else {
            this.startGoogleSignInActivity();
        }
    }


    // --------------------
    // NAVIGATION
    // --------------------

    private void startFacebookSignInActivity() {
        startActivityForResult(
                AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setTheme(R.style.LoginTheme)
                        .setAvailableProviders(
                                Collections.singletonList(new AuthUI.IdpConfig.FacebookBuilder().build())) // FACEBOOK
                        .setIsSmartLockEnabled(false, true)
                        .setLogo(R.drawable.ic_logo_appli_foreground)
                        .build(),
                RC_SIGN_IN);
    }

    private void startGoogleSignInActivity() {
        startActivityForResult(
                AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setTheme(R.style.LoginTheme)
                        .setAvailableProviders(
                                Collections.singletonList(new AuthUI.IdpConfig.GoogleBuilder().build()))//GOOGLE
                        .setIsSmartLockEnabled(false, true)
                        .setLogo(R.drawable.ic_logo_appli_foreground)
                        .build(),
                RC_SIGN_IN);
    }

    private void startMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);

        startActivity(intent);
    }


    // --------------------
    // UI
    // --------------------

    private void showSnackBar(CoordinatorLayout coordinatorLayout, String message) {
        Snackbar.make(coordinatorLayout, message, Snackbar.LENGTH_SHORT).show();
    }


    // --------------------
    // UTILS
    // --------------------


    private void handleResponseAfterSignIn(int requestCode, int resultCode, Intent data) {

        IdpResponse response = IdpResponse.fromResultIntent(data);

        if (requestCode == RC_SIGN_IN) {
            //  Log.d("Debago", "MainActivity : SIGNIN");
            if (resultCode == RESULT_OK) { // SUCCESS
                showSnackBar(this.coordinatorLayout, getString(R.string.connection_succeed));

                this.createUserInFirestore();
                this.startMainActivity();
                finish();

            } else { // ERRORS
                if (response == null) {
                    showSnackBar(this.coordinatorLayout, getString(R.string.error_authentication_canceled));
                } else if (Objects.requireNonNull(response.getError()).getErrorCode() == ErrorCodes.NO_NETWORK) {
                    showSnackBar(this.coordinatorLayout, getString(R.string.error_no_internet));
                } else if (response.getError().getErrorCode() == ErrorCodes.UNKNOWN_ERROR) {
                    showSnackBar(this.coordinatorLayout, getString(R.string.error_unknown_error));
                }
            }
        }
    }


    private void createUserInFirestore() {

        if (this.getCurrentUser() != null) {

            String urlPicture = (this.getCurrentUser().getPhotoUrl() != null) ? this.getCurrentUser().getPhotoUrl().toString() : null;
            String firstname = this.getCurrentUser().getDisplayName();
            String lastname = null;
            String uid = this.getCurrentUser().getUid();
            List<String> instruments = null;
            Boolean admin = false;
            String churchId = ManageChurchPreferences.getChurchId(this,ManageChurchPreferences.KEY_CHURCH_ID_DATA);
            String churchName = ManageChurchPreferences.getChurchName(this,ManageChurchPreferences.KEY_CHURCH_NAME_DATA);
            String churchAddress = ManageChurchPreferences.getChurchAddress(this,ManageChurchPreferences.KEY_CHURCH_ADDRESS_DATA);

            UserHelper.createUser(uid, firstname, lastname, urlPicture, instruments, admin, churchId, churchName, churchAddress).addOnFailureListener(this.onFailureListener());

        }
    }

}
